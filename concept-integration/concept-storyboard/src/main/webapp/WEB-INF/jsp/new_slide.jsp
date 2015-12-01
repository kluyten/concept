<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <jsp:include page="/WEB-INF/jsp/headers.jsp"></jsp:include>
        <script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
        <script src="${pageContext.request.contextPath}/js/common.js"></script>
        <script type="text/javascript">

            function fCheckEmpty(myInput) {
                if ($(myInput).val()) {
                    $(myInput).parent().removeClass('has-error has-feedback');
                }
            }

            $(document).ready(function () {
                $("#slide_name").parent().find('.glyphicon-remove').hide();
                $("#idPreviewButton").click(function () {
                    $("#newSlideForm").attr("action", "preview");
                    $("#newSlideForm").attr("target", "_blank");
                    $("#newSlideForm").submit();
                });

                $("#idSaveButton").click(function () {
                    if (!$("#slide_name").val()) {
                        $("#slide_name").parent().addClass('has-error has-feedback');
                    } else {
                        var myVal = document.getElementById("idIframe").contentWindow.svgcontent.innerHTML;
                        myVal = '<svg width="640" height="480" xmlns="http://www.w3.org/2000/svg">' + myVal + '</svg>';
                        $("#content").val(myVal);
                        $("#newSlideForm").attr("action", "save");
                        $("#newSlideForm").submit();
                    }
                });
            <c:if test="${requestScope.request_slide != null}">
                $("#slide_name").val('<c:out value="${requestScope.request_slide.slideName}"/>');
                $("#idSlide").val('<c:out value="${requestScope.request_slide.id}"/>');
            </c:if>
            });
            function initEditor(myEditor) {
                var myNewDraw = '<svg width="640" height="480" xmlns="http://www.w3.org/2000/svg"></svg>';
            <c:if test="${requestScope.request_slide != null}">
                myNewDraw = '${requestScope.request_slide.slideText}';
                console.log(myNewDraw);
            </c:if>
                myEditor.loadFromString(myNewDraw);
            }
		function fUpdateImage(myUrl) {
			var inputPopUp = $('#idIframe').contents().find('#dialog_buttons input[type=text]');
			var inputToolBar = $('#idIframe').contents().find('#tool_image_url > #image_url');
			if (inputPopUp.is(":visible")) {
				inputPopUp.val(myUrl);
			}
			if (inputToolBar.is(":visible")) {
				inputToolBar.val(myUrl);
				inputToolBar.focus();
			}
		}			
        </script>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jsp/bar.jsp"></jsp:include>
            <div class="container">
		<div style="width: 100%;">
                    <div class="row">
				<div class="col-sm-3">
                            <h3>
                            <c:if test="${requestScope.is_update != null}">
                                <fmt:message key="newslide.title.update" />
                            </c:if>				
                            <c:if test="${requestScope.is_update == null}">
                                <fmt:message key="newslide.title.new" />
                            </c:if>
                        </h3>
                    </div>
                    <div class="col-sm-3" style="margin-top: 20px; margin-bottom: 10px;">
                        <form role="form" id="newSlideForm" method="post">
                            <input type="hidden" id="idSlide" name="idSlide"/>
                            <input type="hidden" id="content" name="content"/>
                            <!-- COnCEPT ProjectId -->
                            <input type="hidden" id="pid" name="pid" value="<%= request.getParameter("pid")%>"/>
                            <!-- COnCEPT UserId -->
                            <input type="hidden" id="uid" name="uid" value="<%= request.getParameter("uid")%>"/>
                            <div class="form-inline">
                                <label for="slidename">Name&nbsp;&nbsp;</label> 
                                <input class="form-control" id="slide_name" name="slide_name" placeholder="Enter slide name" onchange="fCheckEmpty(this);">
                            </div>
                        </form>
                    </div>
                    <div class="col-sm-2" style="margin-top: 20px; margin-bottom: 10px;">
                        <button id="idSaveButton" type="button" class="btn btn-primary btn-md btn-block">
                            <span class="glyphicon glyphicon-floppy-disk"></span> Save
                        </button>
                    </div>
                </div>
            </div>		
        </div>
	<div style="text-align: center;">
		<table width="100%">
			<tr align="left">
				<td width="980px" style=" text-align: left;" >
					<iframe src="/storyboard/svg-edit-2.6/svg-editor.html" id="idIframe" width="960" height="550"></iframe>
				</td>
				<td style="text-align: left; vertical-align: top; padding-left:20px; padding-right:40px;">
					<h3><fmt:message key="newslide.images" /></h3>
					<ul class="list-group" style="width: 280px;">
						<c:if test="${empty sketches}">
							No sketches for this project 
						</c:if>
						<c:forEach var="mySketch" items="${sketches}" varStatus="loop">
							<c:if test="${loop.index%3==0}">
								<li class="list-group-item">
							</c:if>
								<img width="60" id="${mySketch.id}" onclick="fUpdateImage('${mySketch.url}');" style="cursor: pointer; margin-right: 20px">
								<script type="text/javascript">
									var svgContent = '${mySketch.imgInBase64}';
									var imgSrc = 'data:image/jpg;base64,' + svgContent;
									$("#${mySketch.id}").attr('src', imgSrc);
								</script>
							<c:if test="loop.index%3==0">
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</td>
			</tr>
		</table>
	</div>
	<c:if test="${requestScope.request_new_slide_error != null}">
		<div class="alert alert-danger" id="divError">
			<c:out value="${requestScope.request_new_slide_error}"></c:out>
		</div>
	</c:if>        
    </body>
</html>