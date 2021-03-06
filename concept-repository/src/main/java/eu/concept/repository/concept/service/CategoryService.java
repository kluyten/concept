package eu.concept.repository.concept.service;

import eu.concept.repository.concept.dao.CategoryRepository;
import eu.concept.repository.concept.domain.Category;
import eu.concept.repository.concept.domain.ProjectCategory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author smantzouratos
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = Logger.getLogger(CategoryService.class.getName());

    public boolean storeCategory(Category category) {
        try {
            categoryRepository.save(category);
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return false;
        }
        return category.getId() > 0;
    }

    public boolean deleteCategory(Integer id) {
        try {
            categoryRepository.delete(id);
        } catch (Exception ex) {
            Logger.getLogger(CategoryService.class.getName()).severe(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteCategoryCustom(Integer id) {
        try {
            categoryRepository.deleteCategoryCustom(id);
        } catch (Exception ex) {
            Logger.getLogger(CategoryService.class.getName()).severe(ex.getMessage());
            return false;
        }
        return true;
    }

    public Category fetchCategoryById(Integer id) {
        return categoryRepository.findOne(id);
    }

    /**
     * Fetch a category from database , given a name
     *
     * @param name The name
     * @return A Category object (null if no category is found)
     */
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Find a list of categories from their parent ID
     *
     * @param categoryID
     * @return
     */
    public List<Category> findByParentID(Integer categoryID) {
        return categoryRepository.findByParentID(categoryID);
    }

    /**
     * Fetch all categories from database
     *
     * @return A List of Category objects
     */
    public List<Category> fetchAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findRootCategoryByProjectCategory(ProjectCategory projectCategory) {
        return categoryRepository.findRootCategoryByProjectCategory(projectCategory);
    }

    /**
     * This method is used by the auto-complete functionality to retrieve
     * Categories
     *
     * @param keyword
     * @param projectCategory
     *
     * @return A List of Category object
     */
    public List<Category> getCategoriesByKeyword(String keyword, ProjectCategory projectCategory) {

        List<Category> listOfCategories;
        if (keyword.matches("\\*+")) {
            int length = keyword.length();
            int page = 10 * (length - 1);
            Pageable pageable = new PageRequest(page, 10, new Sort(Sort.Direction.ASC, "name"));

            listOfCategories = categoryRepository.findAllCustom(projectCategory, pageable);
        } else {
            listOfCategories = categoryRepository.findFirst10ByNameOrderByNameAsc(projectCategory, keyword);

        }
        return listOfCategories;
    }

    /**
     * Constructs the tree grid of all Categories
     *
     * @param nodesInit
     * @return A String object
     *
     */
    public synchronized String constructTreeGrid(List<Category> nodesInit) {

        // Find Root Category
        List<Category> rootNode = nodesInit.stream().filter(node -> null == node.getParentID()).collect(Collectors.toList());

        List<Category> nodes = nodesInit.stream().filter(node -> null != node.getParentID()).collect(Collectors.toList());

        List<String> treeNodes = new ArrayList<>();

        List<Category> fathers = nodes.stream().filter(node -> node.isFather(rootNode.get(0).getId())).collect(Collectors.toList());

        fathers.forEach(father -> {
            // FatherNode
            treeNodes.add(constructParentNode(String.valueOf(father.getId()), father.getName()));
            // Remove current father class from nodes
            nodes.remove(father);

            List<Category> nodesList;
            do {

                nodesList = getChilds(father, new ArrayList<>(), nodes);

                for (Category tempCategory : nodesList) {
                    String key = constructChildNode(String.valueOf(tempCategory.getId()), tempCategory.getName(), String.valueOf(tempCategory.getParentID().getId()));
                    if (!treeNodes.contains(key)) {
                        treeNodes.add(key);
                    }
                }

                if (!nodesList.isEmpty()) {
                    nodes.remove(nodesList.get(nodesList.size() - 1));
                }

            } while (nodesList.isEmpty() == false);

        });

        return treeNodes.stream().collect(Collectors.joining());

    }

    public List<Category> getChilds(Category currentNode, List<Category> nodes, List<Category> categories) {

        for (Category tempCategory : categories) {
            if (tempCategory.getParentID().getId().compareTo(currentNode.getId()) == 0) {
                nodes.add(tempCategory);
                return getChilds(tempCategory, nodes, categories);
            }
        }
        return nodes;
    }

    /**
     * Constructs a parent Node at the Categories' Tree
     *
     * @param categoryID
     * @param name
     *
     * @return A String object
     */
    public String constructParentNode(String categoryID, String name) {
        String parentNodeHTML = "";

        parentNodeHTML += "<tr class='treegrid-" + categoryID + "'>";
        //parentNodeHTML += "<td>" + name + "</td><td><img src='../images/ICON_EDIT.png' width='32px;' height='32px;' onclick='editCategory(" + categoryID + ");' onmouseover='' style='cursor: pointer;'/> <img src='../images/ICON_DELETE.png' width='32px;' height='32px;' onclick='deleteCategory(" + categoryID + ");' onmouseover='' style='cursor: pointer;'/></td>";
        parentNodeHTML += "<td>" + name + "</td><td><button class='btn btn-xs btn-default model-edit' onclick='editCategory(" + categoryID + ")'>EDIT</button> <button class='btn btn-xs btn-danger model-delete' onclick='deleteCategory(" + categoryID + ")'>DELETE</button></td>";
        parentNodeHTML += "</tr>";

        return parentNodeHTML;
    }

    /**
     * Constructs a child Node at the Categories' Tree
     *
     * @param categoryID
     * @param name
     * @param parentID
     *
     * @return A String object
     */
    public String constructChildNode(String categoryID, String name, String parentID) {
        String childNodeHTML = "";

        childNodeHTML += "<tr class='treegrid-" + categoryID + " treegrid-parent-" + parentID + "'>";
        //parentNodeHTML += "<td>" + name + "</td><td><img src='../images/ICON_EDIT.png' width='32px;' height='32px;' onclick='editCategory(" + categoryID + ");' onmouseover='' style='cursor: pointer;'/> <img src='../images/ICON_DELETE.png' width='32px;' height='32px;' onclick='deleteCategory(" + categoryID + ");' onmouseover='' style='cursor: pointer;'/></td>";
        childNodeHTML += "<td>" + name + "</td><td><button class='btn btn-xs btn-default model-edit' onclick='editCategory(" + categoryID + ")'>EDIT</button> <button class='btn btn-xs btn-danger model-delete' onclick='deleteCategory(" + categoryID + ")'>DELETE</button></td>";
        childNodeHTML += "</tr>";

        return childNodeHTML;
    }

    //testing
    public String constructTaxonomyJSON(List<Category> listClazz) {

        //Remove "RootCategory"
        listClazz.remove(0);

        JSONArray jsonNodes = new JSONArray();

        listClazz.forEach(node -> {

            //Check if is father class
            if (node.isFather()) {
                JSONObject fatherNode = new JSONObject();
                fatherNode.put("label", node.getName());
                fatherNode.put("id", node.getId());
                jsonNodes.put(fatherNode);
                //continue;
            } else {
                Object father = findMyFather(jsonNodes, node.getParentID().getId());

                JSONObject childNode = new JSONObject();
                childNode.put("label", node.getName());
                childNode.put("id", node.getId());

                if (null != father) {

                    if (father instanceof JSONObject) {

                        JSONObject jsonFather = (JSONObject) father;

                        if (jsonFather.has("children")) {

                            ((JSONArray) jsonFather.get("children")).put(childNode);
                        } else {
                            JSONArray jsonArray = new JSONArray();
                            jsonArray.put(childNode);
                            jsonFather.put("children", jsonArray);

                        }

                    }

                }
            }

        });

        return jsonNodes.toString();
    }

    public Object findMyFather(Object jsonNode, int father_id) {

        //Terminate condition for recursive function
        if (jsonNode instanceof JSONObject && ((JSONObject) jsonNode).has("id") && (int) ((JSONObject) jsonNode).get("id") == father_id) {
            return jsonNode;
        }

        //Array condition
        if (jsonNode instanceof JSONArray) {
                        
            //Create an iterator to traverse all nodes
            Iterator<?> jsonIterator = ((JSONArray) jsonNode).iterator();
            while (jsonIterator.hasNext()) {
                Object foundObject = findMyFather((JSONObject) jsonIterator.next(), father_id);
                if (null != foundObject) {
                    return foundObject;
                }
            }
        }

        //Object
        if (jsonNode instanceof JSONObject && ((JSONObject) jsonNode).has("children")) {
            return findMyFather(((JSONObject) jsonNode).get("children"), father_id);
        }

        //Father is not in this node
        return null;
    }

}
