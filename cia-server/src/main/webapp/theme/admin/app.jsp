<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
       
<c:set var="RESOURCES_VER"><spring:eval expression="@siteinfo['app.resource.ver']"></spring:eval></c:set>






















<style>
.ll {text-align:left !important;}
/* #list_target_1 td {text-align:left !important;} */
#list_target_1 td.cc {text-align:center !important; font-weight: bold;}
.gr {
	width: 1100px; margin: 50px auto 120px auto;
}
.gr .it {
	width: 500px; height: 500px; float:left;
}
.gr .it.r {
	float: right;
}
</style>
<div class="contents_wrapper">
    <div class="layer">
        <div class="headline">카테고리별 수강율</div>
        <div class="paddingbox">
            <div>         
            	<div class="clearfix gr">
            		<div class="it">            		
	            		<canvas id="stat1" width="400" height="400"></canvas>
	            		<div style="padding-top: 20px; text-align: center;">
	            			<strong style="font-size: 24px;color:#aaa;"><스터디 수강율></strong>
	            		</div>
	            	</div>
	            	<div class="it r">	           
	            		<canvas id="stat2" width="400" height="400"></canvas>
	            		<div style="padding-top: 20px; text-align: center;">
	            			<strong style="font-size: 24px;color:#aaa;"><강의 수강율></strong>
	            		</div>
	            	</div>	         
            	</div>
            </div>
        </div>
    </div>
</div>	
   
<div class="contents_wrapper">
    <div class="layer">
        <div class="headline">카테고리별 점유율</div>
        <div class="paddingbox">
            <div>
            	<div class="clearfix gr">
            		<div class="it ">
	            		<canvas id="stat3" width="400" height="400"></canvas>
	            		<div style="padding-top: 20px; text-align: center;">
	            			<strong style="font-size: 24px;color:#aaa;"><스터디 점유율></strong>
	            		</div>
	            	</div>
	            	<div class="it r">
	            		<canvas id="stat4" width="400" height="400"></canvas>
	            		<div style="padding-top: 20px; text-align: center;">
	            			<strong style="font-size: 24px;color:#aaa;"><강의 점유율></strong>
	            		</div>
	            	</div>	         
            	</div>
            </div>
        </div>

    </div>
</div>	

<div class="contents_wrapper">
    <div class="layer">
        <div class="headline">가입</div>
        <div class="paddingbox">
            <div>          
            	<div class="clearfix gr">
            		<div class="it ">
	            		<div style="padding-bottom: 20px;">
	            			<strong style="font-size: 24px; color: #aaa;">가입경로 비율</strong>
	            		</div>
	            		<canvas id="stat5" width="400" height="400"></canvas>
	            	</div>
	            	<div class="it r">
	            		<div style="padding-bottom: 20px;">
	            			<strong style="font-size: 24px; color: #aaa;">시기별 가입자수</strong>
	            		</div>
	            		<canvas id="stat6" width="400" height="400"></canvas>
	            	</div>	         
            	</div>       
            </div>
        </div>
    </div>
</div>	


<div class="contents_wrapper">
    <div class="layer">

        <div class="headline">회원 관리</div>

        <div class="paddingbox">

            <div>

                <form id="searchfrm" action="" method="get" onsubmit="return false;">
                    <div class="normal_search_group clearfix" style="margin-top: 20px;">
                        <!-- <select name="menuIdx" data-name="menuIdx" class="selbox h43" style="width: 180px;" onchange="searchfrmFilterChange(this)">
                            <option value="">전체 메뉴</option>
                        </select> -->
                        <!-- <select name="order" class="selbox h43" style="width: 180px;" onchange="searchfrmFilterChange(this)">
                            <option value="">날짜 오래된순</option>
                            <option value="start_date_desc">날짜 최신순</option>
                            <option value="corder_desc">랭크순</option>
                        </select> -->
                        <input class="txtbox h43" type="text" name="query" placeholder="ID,이름,연락처,이메일로 검색" value="" style="width: 300px; margin-right: 10px;background: transparent;border-radius: 5px; border-color: #666;"/>
                        <a class="btn" onclick="searchfrmKeyChange()" style="cursor: pointer;"><i class="fa fa-search" style="font-size: 15px;"></i> 검색</a>
                    </div>	
                </form>
                <script>
                
                </script>


                <div class="clearfix" style="padding: 20px 0px; text-align: right;">

                    <span class="b_txt" style="float: left; color: #eee;">전체 : <strong class="data_total"></strong>건</span>

                    <a href="" class="btn white" style="margin-right: 5px; width: 122px;"><i class="fa fa-bars"></i>&nbsp;&nbsp;전체목록</a>
<!--                     <a href="/coldbrew/pg/user/input" class="btn blue2" style="margin-right: 5px; width: 132px;">게시물 등록</a> -->
<!--                     <a class="btn" style=" width: 122px;cursor: pointer; " onclick="deleteByCheckbox()">선택삭제</a> -->

                </div>

				
				<style>
				.hipro {display: none;}
				</style>

                <table class="table_style">
                    <thead>
                        <tr>
<!--                             <th style="width: 40px;"><input class="dims_checkbox all" type="checkbox"/></th> -->
                            <th class="pc-open" style="width: 50px;">번호</th>
                            <th style="width: 100px;">가입일</th>
                        
                            <th>이름</th>
                            <th>이메일</th>
                            <th>연락처</th>  
<!--                             <th style="width: 100px;">마지막접속</th> -->
<!--                             <th style="width: ;">기타</th> -->
                        </tr>
                    </thead>
                    <tbody id="list_target_1">

                    </tbody>
                </table>
                <div class="pg_wrapper">
                    <div class="pg clearfix">

                        <!-- <a class="numb" href="?page=1"><i class="fa fa-chevron-left"></i></a>
                        <a class="numb sel">1</a> 
                        <a class="numb ">2</a> 
                        <a class="numb ">3</a>      
                        <a class="numb ">4</a> 
                        <a class="numb ">5</a> 
                        <a class="numb" href="?page=2"><i class="fa fa-chevron-right"></i></a> -->

                    </div>
                </div>



            </div>



        </div>



    </div>
</div>	

<script>

// var g_menuIdx = null;
var g_query = null;

function searchfrmKeyChange(i) {
	
	// query
	var query = $("#searchfrm input[name=query]").val();
	if(query.trim() == '') {g_query = null;}
	else { g_query = query; }
	
// 	// menuIdx
// 	var menuIdx = $("#searchfrm select[name=menuIdx]").val();
// 	if(menuIdx=='') {g_menuIdx = null;}
// 	else { g_menuIdx = menuIdx; }

	loadUserList(1);
}
function searchfrmFilterChange(i) {
	
	var val = $(i).val();
	var nm = $(i).attr("name");
	if(val == '') { val =  null; }
	
// 	menuIdx
// 	if(nm == "menuIdx") { g_menuIdx = val; }
	
	// query
	var query = $("#searchfrm input[name=query]").val();
	if(query.trim() == '') {g_query = null;}
	else { g_query = query; }
	
	loadUserList(1);
}


    
function loadUserList(page) {
	
    $.ajax({ 
        type : "GET",
        dataType : "json",
        data : {
            "page" : page,
            "q" : g_query
        },
        url : "/api/users/all",	           
        success : function(data){           
            var c = "";
//             console.log(data.list);
            $(data.list).each(function(i,d){
                c += '<tr>';
//                 c += '<td><input type="checkbox" name="seq" class="dims_checkbox item_dims_checkbox" value="'+d.idx+'"/></td>';
                c += '<td style="text-align: center;">'+(d.idx)+'</td>';
                c += '<td>'+d.regdate.split(" ")[0]+'</td>';  
                c += '<td style="text-align:left;">';
                c += '<a class="hipro_cursor" href="javascript:openF('+d.idx+')" style="position: relative;">';
                
//                 c += '<div class="hipro" style="position: absolute; top: -100px; right: -380px; width: 390px; height: 90px; padding: 10px; background: #f1f1f1; border-radius: 5px; box-shadow: 3px 3px 14px 4px rgba(153, 155, 168, 0.3);">';
//                 	c += '<span class="profileCss" style="margin-right: 10px; width: 90px; height: 90px;background-image: url('+d.img1+'), url(/theme/admin/resources/img/profile.png)"></span>';
//                 	c += '<span class="profileCss" style="margin-right: 10px; width: 90px; height: 90px;background-image: url('+d.img2+'), url(/theme/admin/resources/img/profile.png)"></span>';
//                 	c += '<span class="profileCss" style="margin-right: 10px; width: 90px; height: 90px;background-image: url('+d.img3+'), url(/theme/admin/resources/img/profile.png)"></span>';
//                 	c += '<span class="profileCss" style="width: 90px; height: 90px;background-image: url('+d.img4+'), url(/theme/admin/resources/img/profile.png)"></span>';
//                 c += '</div>';
                 
                c += '<span class="profileCss" style="background-image: url('+d.img+'), url(/theme/admin/resources/img/profile.png)"></span> ';
                
                c += (d.enabled==true?d.name:('<span style="color:red">'+d.name+'</span>'))+' <i style="color: #959595;" class="far fa-window-restore"></i>';
                c += '</a></td>';
                c += '<td>'+d.email+'</td>';
                c += '<td>'+d.phonenm+'</td>';
//                 c += '<td>'+d.findate+'</td>';
                
            });
            $("#list_target_1").html(c);
            
            c = "";
//             console.log(data.pageNav);
            var pn = data.pageNav;
            $(".data_total").text(pn.totalRowCount);
            if(pn.startPageNum > pn.pageBlockCount) {
            	c += '<a class="numb" onclick="loadUserList('+(1)+')"><i class="fa fa-chevron-left"></i></a>';
            	c += '<a class="numb" onclick="loadUserList('+(pn.startPageNum-1)+')"><i class="fa fa-chevron-left"></i></a>';
            }
            for(var i=pn.startPageNum;i<=pn.endPageNum;i++) {
            	c += '<a class="numb '+(i==pn.pageNum?"sel":"")+'" onclick="loadUserList('+i+')">'+i+'</a>';
            }
            if(pn.endPageNum < pn.totalPageCount) {
            	c += '<a class="numb" onclick="loadUserList('+(pn.endPageNum+1)+')"><i class="fa fa-chevron-left"></i></a>';
            	c += '<a class="numb" onclick="loadUserList('+(pn.totalPageCount)+')"><i class="fa fa-chevron-left"></i></a>';
            }
            $(".pg_wrapper > .pg").html(c);
            
              
            $(".hipro_cursor").hover(function(){
            
            	$(this).find(".hipro").css("display", "block");
            }, function(){
            	$(this).find(".hipro").css("display", "none");
            });
          	
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
}    
loadUserList(1);    
 
$(".dims_checkbox.all").on("click",function(){
    var state = $(this).prop('checked');	
    $(".dims_checkbox").prop('checked', state);
});

function deleteByCheckbox() {
    if($(".item_dims_checkbox:checked").length==0){
        alert("삭제할 항목을 선택해주세요.");
        return;
    }

    var count = $(".item_dims_checkbox:checked").length;
    if(window.confirm('선택된 '+count+'개의 항목을 삭제하시겠습니까?','')){
    	var arr = new Array();
        $.each($(".item_dims_checkbox:checked"), function(idx,val){
        	arr.push($(val).val());
        });
        $.ajax({ 
            type : "DELETE",
            contentType : "application/json; charset=utf-8",
            data : JSON.stringify(arr),
            url : "/api/users/list",           
            error : function(e1,e2,e3){
                console.log(e1);console.log(e2);console.log(e3);
            },
            success : function(){
            	$(".dims_checkbox.all").prop('checked', false);
            	loadUserList(1, null);
            }  
        });	
    }
}
</script>



<script>

/* 

var myChart = new Chart(document.getElementById('stat6').getContext('2d'), {
    type: 'bar',
    data: {
        labels: ['유튜브', '네이버', '페이스북', '카카오', '다음', '블로그'],
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
}); */
</script>	
					
<script type="text/javascript" src="/theme_admin/stat.js"></script>



