$(document).ready(function () {
    loadFragment();
});

var initTreegrid = function initTreegrid() {
    $('.tree').treegrid({
        initialState: 'collapsed'
    });
}

var initAddCategory = function initAddCategory() {
    $("#hasParent").click(function () {
        if (!$("#hasParent").is(':checked')) {
            $("#parentCategory").val('');
            $("#parentCategoryID").val('');
            $("#parentCategory").attr("disabled", true);
        } else {
            $("#parentCategory").removeAttr("disabled");
        }
    });

    var projectID = $("#projectID").val();

    $("#parentCategory").autocomplete({
        minLength: 1,
        source: function (request, response) {
            jQuery.ajax({
                featureClass: "P",
                style: "full",
                maxRows: 12,
                url: "/conceptRest/api/category/search",
                dataType: "jsonp",
                contentType: "text/html; charset=UTF-8",
                encoding: "UTF-8",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Content-Type", "text/html;charset=utf-8");
                },
                data: {
                    format: "json",
                    keyword: request.term,
                    projectID: projectID,
                    operation: "autocomplete"
                },
                success: function (data) {
                    if (data.total === 0) {
                        // Do nothing
                    } else {

                        response(jQuery.map(jQuery.makeArray(data.values), function (item) {

                            return {
                                label: item.value,
                                value: item.value,
                                id: item.id
                            }
                        }));
                    }

                },
                error: function () {

                }
            });
        },
        select: function (event, ui) {
            jQuery("#parentCategory").val(ui.item.value);
            jQuery("#parentCategoryID").val(ui.item.id);
            return false;
        }

    });
}


var checkForTreeGrid = function checkForTreeGrid() {

    var treegrid = $("#treeGridHTML").val();
    if (treegrid !== '') {
        $("#treeGrid").append(treegrid);

        $('.tree').treegrid({
            initialState: 'collapsed'
        });
    }

    //Hide Taxonomy add/edit buttons
    if (!$("#taxonomyAlert").length) {
        $("#add-button-taxonomy").hide();
        $(".model-edit").hide();
        $(".model-delete").hide();
    }

    if ($("#model-name").length) {
        $("#pm-taxonomy-form").hide();
    }

}

function loadFragment() {

    var projectID = $("#projectID").val();
    $("#modelFragment").load("/category/fragment/" + projectID, checkForTreeGrid);
}

function addCategoryToModel() {
    var projectID = $("#projectID").val();
    $("#modelFragment").load("/category/fragment/" + projectID + "?action=add", initAddCategory);
}

function editCategory(categoryID) {
    var projectID = $("#projectID").val();
    $("#modelFragment").load("/category/fragment/" + projectID + "?action=edit&id=" + categoryID, initAddCategory);
}

function deleteCategory(categoryID) {
    var projectID = $("#projectID").val();
    $("#modelFragment").load("/category/delete?id=" + categoryID + "&pid=" + projectID, checkForTreeGrid);
}