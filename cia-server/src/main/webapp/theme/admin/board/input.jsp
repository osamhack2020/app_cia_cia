<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
       
<c:set var="APP_SITE_DOMAIN"><spring:eval expression="@siteinfo['app.domain']"></spring:eval></c:set>  
<c:set var="APP_SITE_NAME"><spring:eval expression="@siteinfo['app.sitename']"></spring:eval></c:set>  
<c:set var="APP_SITE_URL"><spring:eval expression="@siteinfo['app.siteurl']"></spring:eval></c:set>
<c:set var="IMGUR_CLIENT_ID"><spring:eval expression="@siteinfo['app.upload.imgur.id']"></spring:eval></c:set>
<c:set var="GOOGLE_ANALY_ID"><spring:eval expression="@siteinfo['app.google.analytics.id']"></spring:eval></c:set>
<c:set var="RESOURCES_VER"><spring:eval expression="@siteinfo['app.resource.ver']"></spring:eval></c:set>

<div class="contents_wrapper">
    <div class="layer">
        <div class="headline">게시판 관리</div>
        <div class="paddingbox">
        <!-- SSS -->
        <form id="inputfrm">
            
            <div style="text-align: right; padding-bottom: 15px;" class="clearfix">
                <span style="float: left;line-height: 43px;" class="b_txt">게시판 등록 </span>
                <a class="btn blue inputfrmsm" style="cursor: pointer;"><i class="fa fa-check"></i> 저장</a>
                <a class="btn" href="./list"><i class="fa fa-ban"></i> 취소</a>
            </div>

            <div>
				
				<c:if test="${boardInfo.idx != null}">
					<input type="hidden" name="idx" value="${boardInfo.idx}"/>
				</c:if>
				
                <div class="frm_style bd clearfix">
                    <div class="r tit"><div class="desc_name">분류</div></div>
                    <div class="r cont">
                        <select name="menuIdx" class="selbox" style="width: 300px;" data-idx="${boardInfo.menuIdx}">  
                        </select>
                    </div>
                </div>
                
                <div class="frm_style bd clearfix">
                    <div class="r tit"><div class="desc_name">게시물 제목</div></div>
                    <div class="r cont">
                        <input class="txtbox" type="text" name="title" style="width: 100%;" value="${boardInfo.title}" placeholder="150자 이내"/>
                    </div>
                </div>	
                
                <div class="frm_style bd clearfix">
                    <div class="r tit"><div class="desc_name">태그</div></div>
                    <div class="r cont">
                        <input class="txtbox" type="text" name="tags" style="width: 100%;" value="${boardInfo.tags}" placeholder="공백없이 ,(콤마)로 구분해서 입력"/>
                    </div>
                </div>	
                
                <div class="frm_style bd clearfix">
                    <div class="r tit"><div class="desc_name">게시물 내용</div></div>
                    <div class="r cont">
                        <textarea id="editor" style="height: 637px;box-sizing: border-box;" name="note">${boardInfo.note}</textarea>
                    </div>
                </div>	

                <div style="text-align: right;">
                    <a class="btn blue inputfrmsm" style="cursor:pointer;"><i class="fa fa-check"></i> 저장</a>
                    <a class="btn" href="./list"><i class="fa fa-ban"></i> 취소</a>
                </div>

            </div>
            
            
        </form>
        <!-- EEE -->       
        </div>
    </div>
</div>	

<script>
function prevCheckFrm() {
	var writer = $("input[name='title']").val();
    if(writer.trim() == "") {
        alert("제목을 입력하세요.");
        return false;
    }
    $("#editor").val(CKEDITOR.instances.editor.getData());
    var note = $("textarea[name='note']").val();
    if(note.trim() == "") {
        alert("내용을 입력하세요.");
        return false;
    }   
    var menuIdx = $("select[name=menuIdx]").val();
	if(menuIdx == '') {
		alert("메뉴를 선택하세요.");
        return false;
	}
	return true;
}
</script>

<c:if test="${boardInfo.idx != null}">
<script>
function checkFrm() {
    
	if(!prevCheckFrm()) {
		return;
	}

	var data = $("#inputfrm").serializeObject();	
	$.ajax({ 
        type : "put",
        dataType : "json",
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify(data),
        url : "/api/boards/${boardInfo.idx}",	           
        success : function(data){
            console.log(data);
            location.href = "./list";
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });
}
</script>
</c:if>
<c:if test="${boardInfo.idx == null}">
<script>
function checkFrm() {

	if(!prevCheckFrm()) {
		return;
	}

	var data = $("#inputfrm").serializeObject();	
	$.ajax({ 
        type : "post",
        dataType : "json",
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify(data),
        url : "/api/boards",	           
        success : function(data){
            console.log(data);
            location.href = "./list";
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });

    /*
    var imgValues = $("input[name=img]").val();
    if ( typeof imgValues == "undefined" || imgValues == null || imgValues ==""){
        imgValues = '';
    } else {
        imgValues = imgValues.trim();
    }
    $.each($(".imglist01 .imgprec"), function(idx, val){
        //console.log(val);
        var src = $(val).find("img").attr("src");
        console.log(src);

        if(imgValues == '') {
            imgValues = src;
        }else {
            imgValues += ',' + src;
        }

    });

    $("input[name=img]").val(imgValues);
    console.log("result: " + $("input[name=img]").val());*/

}
</script>
</c:if>

<script>
$(document).ready(function(){
    CKEDITOR.config.height = 500; 

    CKEDITOR.replace('editor', {
        startupFocus : false,  // 자동 focus 사용할때는  true
        //skin: 'moonocolor',
        //customConfig : '/ckeditor/config.js', //커스텀설정js파일위치
        //filebrowserUploadUrl: '/ckeditor/upload.php?type=Files',
        //filebrowserImageUploadUrl: '/upload_editor.php?type=Images'
        //filebrowserFlashUploadUrl: '/ckeditor/upload.php?type=Flash'
    });
    
    $(".inputfrmsm").click(function(){checkFrm();});
});
    
    
function loadBoardMenus() {
    $.ajax({ 
        type : "GET",
        dataType : "json",
        url : "/api/boards/menus",	           
        success : function(data){
            var c = "";
            var t = "${boardInfo.menuIdx}";
            console.log(t);
            c += "<option value=''>메뉴 선택</option>";
            $(data.list).each(function(i,d){
                console.log(d);
                c += '<option value="'+d.idx+'" '+(t==d.idx?"selected='selected'":"")+'>'+d.nm+'</option>';       
            });
            $("#inputfrm select[name=menuIdx]").append(c); 
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
    
}
loadBoardMenus();




/*

function initFileEvt() {
    var inputs = document.querySelectorAll( '.inputfile' );
    Array.prototype.forEach.call( inputs, function( input )
    {
        var label	 = input.nextElementSibling,
            labelVal = label.innerHTML;

        input.addEventListener( 'change', function( e )
        {
            var fileName = e.target.value.split( '\\' ).pop();

            var form = $('#fuploadfrm')[0];
            var formData = new FormData(form);
            formData.append("attach_file", this.files[0]);
            formData.append("project_seq", '0');


            tmp = $(this).attr("data-pre");

            $.ajax({
                url: './img_upload_project.php',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                success: function(result){

                    result = result.trim();

                    //$("."+tmp+"_p").css("display", "inline-block");
                    //$("."+tmp+"_p").attr("src", result);
                    //$("input[name="+tmp+"]").val(result);

                    var addHtml = '';
                    addHtml += '<div class="imgprec">';
                    addHtml += '<a class="x" onclick="delT(this)"></a>';
                    addHtml += '<img class="img_p" src="'+result+'"/>';
                    addHtml += '</div></div>';



                    $(".imglist01").append(addHtml);

                }
            });


            //if( fileName )
                //label.querySelector( 'span' ).innerHTML = fileName;
            //else
               // label.innerHTML = labelVal;


        });
    });
}
initFileEvt();

function delT(i, seq) {

    $.ajax({
        url: './img_delete_project.php',
       // contentType: false,
        type : "POST",
        //dataType: "json",
        data: {
            'project_img_seq' : seq
        },
        type: 'POST',
        success: function(result){
            $(i).parent().detach();
        },
        error: function() {
            alert("error");
        }
    });


}*/
</script>






