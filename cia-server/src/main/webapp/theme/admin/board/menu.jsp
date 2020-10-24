<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
       
<c:set var="APP_SITE_DOMAIN"><spring:eval expression="@siteinfo['app.domain']"></spring:eval></c:set>  
<c:set var="APP_SITE_NAME"><spring:eval expression="@siteinfo['app.sitename']"></spring:eval></c:set>  
<c:set var="APP_SITE_URL"><spring:eval expression="@siteinfo['app.siteurl']"></spring:eval></c:set>  
<c:set var="CURRENT_REQ_URL">${APP_SITE_URL}${requestScope['javax.servlet.forward.request_uri']}<c:if test='${requestScope["javax.servlet.forward.query_string"]!=null}'>?${requestScope['javax.servlet.forward.query_string']}</c:if></c:set>
<c:set var="IMGUR_CLIENT_ID"><spring:eval expression="@siteinfo['app.upload.imgur.id']"></spring:eval></c:set>
<c:set var="GOOGLE_ANALY_ID"><spring:eval expression="@siteinfo['app.google.analytics.id']"></spring:eval></c:set>
<c:set var="RESOURCES_VER"><spring:eval expression="@siteinfo['app.resource.ver']"></spring:eval></c:set>

<div class="contents_wrapper">
    <div class="layer">

        <div class="headline">게시판 메뉴 관리</div>

        <div class="paddingbox">

            <div>
            	
            	<form id="inputfrm" name="inputfrm" action="" method="get" onsubmit="return false;">
                    <div class="normal_search_group clearfix" style="margin-top: 20px;">                
                    	<input class="txtbox h43" type="text" name="corder" placeholder="순서" value="1" style="width: 50px; margin-right: 10px;"/>      
                        <input class="txtbox h43" type="text" name="nm" placeholder="메뉴 이름을 입력" value="" style="width: 300px; margin-right: 10px;"/>
                        <a id="insertbtn" class="btn blue2" style="margin-right: 5px; width: 132px; cursor:pointer;">메뉴 등록</a>
                    </div>	
                </form>

                <div class="clearfix" style="padding: 20px 0px; text-align: right;">
                    <span class="b_txt" style="float: left;">전체 : <strong class="data_total"></strong>건</span>
                    <a class="btn" style=" width: 122px;cursor: pointer; " onclick="deleteByCheckbox()">선택삭제</a>
                </div>



                <table class="table_style">
                    <thead>
                        <tr>
                            <th style="width: 40px;"><input class="dims_checkbox all" type="checkbox"/></th>
                            <th class="pc-open" style="width: 50px;">번호</th>
                            <th style="width: 50px;">순서</th>
                            <th>메뉴 이름</th>
                            <th style="width: 100px;">TYPE</th>
                            <th style="width: 70px;">총 게시물 수</th>
                            <th style="width: 70px;">총 조회수</th>
                            <th style="width: ;">Update</th>
                        </tr>
                    </thead>
                    <tbody id="list_target_1">

                    </tbody>
                </table>
                
                <!-- 
                <div class="pg_wrapper">
                    <div class="pg clearfix">

                        <a class="numb" href="?page=1"><i class="fa fa-chevron-left"></i></a>
                        <a class="numb sel">1</a> 
                        <a class="numb ">2</a> 
                        <a class="numb ">3</a>      
                        <a class="numb ">4</a> 
                        <a class="numb ">5</a> 
                        <a class="numb" href="?page=2"><i class="fa fa-chevron-right"></i></a>

                    </div>
                </div> 
                -->


            </div>



        </div>



    </div>
</div>	



<script>

// 목록
function loadBoardMenus() {
    $.ajax({ 
        type : "GET",
        dataType : "json",
        //contentType : "application/json; charset=utf-8",
        data : {addInfo : true},
        url : "/api/boards/menus",	           
        success : function(data){
            var c = "";
            console.log(data);
            $(data.list).each(function(i,d){               
                c += '<tr>';
                c += '<td><input type="checkbox" name="idx" class="dims_checkbox item_dims_checkbox" value="'+d.idx+'"/></td>';
                c += '<td class="pc-open">'+(i+1)+'</td>';
                c += '<td><input class="txtbox h43" type="text" name="corder" value="'+d.corder+'" style="width: 100%;"/></td>';
                c += '<td style="text-align:left;">';
                c += '<input class="txtbox h43" type="text" name="nm" value="'+d.nm+'" style="width: 100%;"/>';
                c += '</td>';
                c += '<td style="text-align:left;">';
                c += '<input class="txtbox h43" type="text" name="type" value="'+(d.type!=null?d.type:"")+'" style="width: 100%;"/>';
                c += '</td>';
                c += '<td>'+(d.dataPostCount!=null?d.dataPostCount:"")+'</td>';
                c += '<td>'+(d.dataViewCount!=null?d.dataViewCount:"")+'</td>';
                c += '<td>';
                    c += ' <a class="btn blue h38" onclick="smPut(this)" style="cursor:pointer;"><i class="fa fa-check"></i> 수정</a> ';
                c += '</td>';
            });
            $("#list_target_1").html(c);
            $(".data_total").text(data.list.length);
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
    
}
loadBoardMenus();
 
// 삭제 
$(".dims_checkbox.all").on("click",function(){
    var state = $(this).prop('checked');	
    $(".dims_checkbox").prop('checked', state);
});
function deleteByCheckbox() {
    if($(".item_dims_checkbox:checked").length==0){
        alert("삭제할 상품을 선택해주세요.");
        return;
    }
    var count = $(".item_dims_checkbox:checked").length;
    if(window.confirm('선택된 '+count+'개의 상품을 삭제하시겠습니까?','')){
    	var arr = new Array();
    	$.each($(".item_dims_checkbox:checked"), function(idx,val){
    		arr.push($(val).val());
        });
       
        $.ajax({ 
            type : "DELETE",
            contentType : "application/json; charset=utf-8",
            data : JSON.stringify(arr),
            url : "/api/boards/menus",           
            error : function(e1,e2,e3){
                console.log(e1);console.log(e2);console.log(e3);
            },
            success : function(){
            	$(".dims_checkbox.all").prop('checked', false);
            	loadBoardMenus();
            }  
        });	
    }
}

// 등록
function smPost() {
    var corder = $("#inputfrm input[name=corder]").val();
	if(corder == '') {
		alert("순서를 입력하세요.");
        return;
	}
	var nm = $("#inputfrm input[name=nm]").val();
	if(nm == '') {
		alert("메뉴명을 입력하세요.");
        return;
	}
	
    var data = $("#inputfrm").serializeObject();	
    console.log(data);
    
	$.ajax({ 
        type : 'post',
        contentType : "application/json; charset=utf-8",
       	data : JSON.stringify(data),
        url : "/api/boards/menus",	           
        success : function(){
			loadBoardMenus();
			$("#inputfrm input[name=nm]").val('');
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });
}
$("#insertbtn").click(function(){ smPost(); });

// 수정
function smPut(i) {
	var tr = $(i).parents("tr");
    var corder = $(tr).find("input[name=corder]").val();
	if(corder == '') {
		alert("순서를 입력하세요.");
        return;
	}
	var nm = $(tr).find("input[name=nm]").val();
	if(nm == '') {
		alert("메뉴명을 입력하세요.");
        return;
	}
	var idx = $(tr).find("input[name=idx]").val();
	var type = $(tr).find("input[name=type]").val();
	
	$.ajax({ 
        type : "PUT",
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify({
			"nm" : nm,
	     	"corder" : corder,
	     	"type" : type
		}),      
        url : "/api/boards/menus/"+idx,	           
        success : function(){
			loadBoardMenus();
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });
}
</script>






