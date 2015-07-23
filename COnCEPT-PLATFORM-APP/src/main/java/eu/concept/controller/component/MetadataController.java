package eu.concept.controller.component;

import eu.concept.repository.concept.domain.Component;
import eu.concept.repository.concept.domain.Metadata;
import eu.concept.repository.concept.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Christos Paraskeva <ch.paraskeva at gmail dot com>
 */
@Controller
public class MetadataController {

    @Autowired
    MetadataService metadataService;

    /*
     *  GET Methods 
     */
    @RequestMapping(value = "/metadata/{metadata_id}", method = RequestMethod.GET)
    public String fetchMetadataByID(Model model, @PathVariable long metadata_id) {
        model.addAttribute("metadataContent", metadataService.fetchMetadataById(metadata_id));
        System.out.println("Metadata description: " + metadataService.fetchMetadataById(metadata_id).getDescription());
        return "metadata :: sidebar-metadata";
    }

//    @RequestMapping(value = "/ba_app", method = RequestMethod.GET)
//    public String ba_app(Model model) {
//        List<ProjectOp> projects = projectServiceOp.findProjectsByUserId(getCurrentUser().getId());
//        model.addAttribute("projects", projects);
//        model.addAttribute("currentUser", getCurrentUser());
//        if (!model.containsAttribute("briefanalysis")) {
//            BriefAnalysis ba = new BriefAnalysis();
//            
//            model.addAttribute("briefanalysis", new BriefAnalysis());
//        } else {
//            
//        }
//        return "ba_app";
//    }
//    
    @RequestMapping(value = "/metadata", method = RequestMethod.GET)
    public String metadataPage(Model model, @RequestParam(value = "cid", defaultValue = "0", required = false) int cid, @RequestParam(value = "component", defaultValue = "", required = false) String component) {

        Metadata metadata = metadataService.fetchMetadataByCidAndComponent(cid, component);
        if (null == metadata) {
            metadata = new Metadata(null, cid, "", "", "", null);
            metadata.setComponent(new Component(component));
            metadataService.storeMetadata(metadata);
        }
        model.addAttribute("metadata", metadata);
        return "metadata :: sidebar-metadata ";
    }

    /*
     *  POST Methods 
     */
//    @RequestMapping(value = "/ba_app", method = RequestMethod.POST)
//    public String createBriefAnalysis(@RequestParam(value = "projectID", defaultValue = "0", required = false) int projectID, Model model) {
//        model.addAttribute("projectID", projectID);
//        return ba_app(model);
//    }
}