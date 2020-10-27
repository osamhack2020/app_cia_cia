<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<c:set var="RESOURCES_VER"><spring:eval expression="@siteinfo['app.resource.ver']"></spring:eval></c:set>

<!DOCTYPE html>
<html>
<head>
    
	<meta charset="UTF-8"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	
	<!-- 검색봇 차단 -->
	<meta name="robots" content="noindex">
	<meta name="googlebot" content="noindex">
	
	<title>Collective Intelligence in Army 2020</title> 
	
<!-- 	<link rel="shortcut icon" href="/resources/favicon.ico" type="image/x-icon"/>  -->

	<!-- normalize -->
	<link type="text/css" rel="stylesheet" href="/theme_admin/normalize.css"/>
	
	<script type="text/javascript" src="/common_${RESOURCES_VER}/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/common_${RESOURCES_VER}/js/jquery.form.js"></script>
	
	<script type="text/javascript" src="https://www.chartjs.org/samples/latest/utils.js"></script>
	
	
	
	
<%-- 	<script type="text/javascript" src="/common_${RESOURCES_VER}/editor/ckeditor/ckeditor.js"></script> --%>
<%-- 	<script type="text/javascript" src="/common_${RESOURCES_VER}/editor/editor.js"></script>  --%>
<%-- 	<script type="text/javascript" src="/common_${RESOURCES_VER}/js/md5.js"></script>  --%>
	 
	<!-- fontawesome -->
	<link rel="stylesheet" href="/common_${RESOURCES_VER}/fontawesome/css/all.min.css"/>
    <script src="/common_${RESOURCES_VER}/fontawesome/js/fontawesome.min.js"></script> 
    
	<script type="text/javascript" src="/common_${RESOURCES_VER}/js/Chart.js"></script>
	
	<!-- global -->
	<link type="text/css" rel="stylesheet" href="/theme_admin/global.css"/>
	<script type="text/javascript" src="/theme_admin/global.js"></script>
	
</head>  
<body>
	
	<style>  
.bg_black_cover { position: fixed; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5);  z-index: 9050; display: none; }
.pop_people { width: 600px; max-height: 80%; overflow-y: scroll; background-color: #eceff2;position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto; display: none;box-shadow: 0 10px 20px 0 rgba(0, 0, 0, 0.3);}
.pop_people .pop_in {width: 100%; position: relative; box-sizing: border-box; padding: 30px 40px;}
.pop_people .x {position: absolute; top: -46px; right: 0px; cursor: pointer;}
.pop_people .head {text-align: center; padding-bottom: 30px; border-bottom: 1px solid #fff;}
.pop_people .head .p_title { color: #24596e; font-size: 32px;}
.pop_people .head .p_desc {font-size: 16px; color: #2f2f2f; line-height: 32px; padding-top: 10px;}
.pop_people .head .committee {padding-bottom: 20px;}
.pop_people .head .committee img {float: left; width: 104px;}

.pop_people .head.citizen {padding-left: 216px; text-align: left; box-sizing: border-box; min-height: 206px; 
	background-image: url(img/citizen1.png);background-repeat: no-repeat; background-position: left bottom;}
.pop_people .head.citizen .p_title {padding-top: 20px;}
.pop_people .head.citizen.c1 {background-image: url(../img/citizen1.png);}
.pop_people .head.citizen.c2 {background-image: url(../img/citizen2.png);}
.pop_people .head.citizen.c3 {background-image: url(../img/citizen3.png);}
.pop_people .head.citizen.c4 {background-image: url(../img/citizen4.png);}
.pop_people .head.citizen.c5 {background-image: url(../img/citizen5.png);}


.pop_people .gg {padding-top: 30px; color: #2f2f2f;}
.pop_people .gg .tit {font-size: 16px; font-weight: bold;}
.pop_people .gg ul {display: block;}
.pop_people .gg ul li {display: block; font-size: 14px; padding-top: 12px;}

.conf_btn2 {
	height: 52px;
	border-radius: 26px;
	background-color: #222;
	box-shadow: 0 10px 20px 0 rgba(0, 0, 0, 0.3);
	display: inline-block; line-height: 50px; text-align: center;
	font-size: 16px; color: #fff; font-weight: bold;  
	cursor: pointer;
	box-sizing: border-box; max-width: 100%; width: 320px;
}

.pop_content .conf_btn2:hover,
.pop_content .conf_btn2:focus,
.pop_people .conf_btn2:hover,
.pop_people .conf_btn2:focus,
.acc_layer .conf_btn2:hover,
.acc_layer .conf_btn2:focus {
	background-color: #2a8eb5;
	box-shadow: 0 10px 20px 0 rgba(65, 172, 214, 0.3);
}
.circir {
	display: inline-block;
	width: 140px;
	height: 140px;  
	border: 1px solid #eee;
	border-radius: 50%;
	background-color: #000;
	background-position: center;
	background-size: cover;
	background-image: url(/theme_admin/img/profile.png);
}
.cirli {
	margin-top: 7px;
}
.cirli li {
	font-size: 16px;
	padding: 5px 0px;
} 
.cirli li strong {font-weight: normal; color: #555;}
.cirli li > span {
	color: #bbb;  
	display: inline-block;
	width: 100px;
}

</style>

<script>
function openF(idx) {
	$(".bg_black_cover").css("display", "block");
	$(".bg_black_cover").click(function(){
		$(".bg_black_cover").css("display", "none");
	});
	
	
	$.ajax({ 
        type : "GET",
        dataType : "json",
        /* data : {
            "page" : page,
            "q" : g_query
        }, */
        url : "/api/adm/user/"+idx,	           
        success : function(data){           
            
            //console.log(data);
            
            $(".hs1").html(data.user.regdate);
            $(".hs2").html(data.user.email);
            $(".hs3").html(data.user.name);
            $(".hs4").html(data.user.phonenm);
          	$(".circir").css("background-image", "url("+data.user.img+"), url(/theme_admin/img/profile.png)");  
          	
          	
          	var c = '';
          	$(data.list1).each(function(i,d){
                c += '<a class="card">';
                c += '<div class="img_area eff" style="background-image: url('+d.img+');"></div>';
                c += '<div class="desc_area">';
                c += '<div class="teacher_line">';
                c += '<div class="cir" style="background-image: url('+d.userImg+'), url(/theme_admin/img/profile.png);"></div>';
                c += '<div class="nm">'+d.userName+'</div>';
                c += '</div><div class="title_line">';
                c += d.title
                c += '</div></div></a>';             
            });
            $("#us_1").html(c);
            c = '';
          	$(data.list2).each(function(i,d){
                c += '<a class="card">';
                c += '<div class="img_area eff" style="background-image: url('+d.img+');"></div>';
                c += '<div class="desc_area">';
                c += '<div class="teacher_line">';
                c += '<div class="cir" style="background-image: url('+d.userImg+'), url(/theme_admin/img/profile.png);"></div>';
                c += '<div class="nm">'+d.userName+'</div>';
                c += '</div><div class="title_line">';
                c += d.title
                c += '</div></div></a>';             
            });
            $("#us_2").html(c);
          
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
}
function closeF() {
	$(".bg_black_cover").css("display", "none");
	
}





//

$.ajax({ 
    type : "GET",
    dataType : "json",
    data : {
        "limitCount" : 5
    },
    url : "/api/adm/class/recommend",	           
    success : function(data){           
      	var c = '';
      	$(data.list).each(function(i,d){
            c += '<a class="card">';
            c += '<div class="img_area eff" style="background-image: url('+d.img+');"></div>';
            c += '<div class="desc_area">';
            c += '<div class="teacher_line">';
            c += '<div class="cir" style="background-image: url('+d.userImg+'), url(/theme_admin/img/profile.png);"></div>';
            c += '<div class="nm">'+d.userName+'</div>';
            c += '</div><div class="title_line">';
            c += d.title
            c += '</div></div></a>';             
        });
        $("#us_3").html(c);
    }, 
    error : function(e1,e2,e3){
        console.log(e1);console.log(e2);console.log(e3);
    } 
});	
$.ajax({ 
    type : "GET",
    dataType : "json",
    data : {
        "limitCount" : 5
    },
    url : "/api/adm/study/recommend",	           
    success : function(data){           
      	var c = '';
      	$(data.list).each(function(i,d){  
            c += '<a class="card">';
            c += '<div class="img_area eff" style="background-image: url('+d.img+');"></div>';
            c += '<div class="desc_area">';
            c += '<div class="teacher_line">';
            c += '<div class="cir" style="background-image: url('+d.userImg+'), url(/theme_admin/img/profile.png);"></div>';
            c += '<div class="nm">'+d.userName+'</div>';
            c += '</div><div class="title_line">';
            c += d.title
            c += '</div></div></a>';             
        });
        $("#us_4").html(c);
    }, 
    error : function(e1,e2,e3){
        console.log(e1);console.log(e2);console.log(e3);
    } 
});	
</script>

<div class="bg_black_cover" >
	
	<div class="pop_people pop_people6" style="display: block;">
		<div class="pop_in">  
			<a class="x"><img src="/theme_admin/img/layer-close.png"></a>
			
			
			<div>
				
				<div style="padding-bottom: 0px;">
	      			<strong style="font-size: 24px;">회원 상세정보</strong>
	      		</div>
				<div class="clearfix" style="padding: 20px 0px 45px 0px;">
					<div style="float: left; width: 170px;">
						<span class="circir"></span>
					</div>
					<div style="float: left;">
						<ul class="cirli">
							<li>
								<span>가입일</span> 
								<strong class="hs1">2019.10.10</strong>
							</li>
							<li>
								<span>Email</span> 
								<strong class="hs2" style="font-size: 18px; color: #000; font-weight: bold;">qlcskfgml78@naver.com</strong> 
							</li>
							<li>
								<span>Name</span> 
								<strong class="hs3">정희성</strong>
							</li>
							<li>
								<span>PhoneNM</span> 
								<strong class="hs4">010-3232-2212</strong>
							</li>
						</ul>
					</div>
				</div>
			
			</div>
			
			<div>
				
				<div style="padding-bottom: 20px;">
	      			<strong style="font-size: 24px;">CIA AI 알고리즘(추천 강의/스터디)</strong>
	      		</div>
	      		<canvas id="proStat1" width="400" height="300"></canvas>
				
			</div>
			
			<div style="padding: 20px 0px;">
				<div style="padding: 20px 0px;">
	      			<strong style="font-size: 24px;">현재 수강중인 강의</strong>
	      		</div>
	      		<div class="overflow100">
	      			<div id="us_1" class="clearfix search_list standard">
		      		</div>
	      		</div>
	      		<div style="padding: 20px 0px;">
	      			<strong style="font-size: 24px;">현재 수강중인 스터디</strong>
	      		</div>
	      		<div class="overflow100">
	      			<div id="us_2" class="clearfix search_list standard">
		      			
		      		</div>
	      		</div>
			</div>
			
			<div style="padding: 20px 0px;">
				<div style="padding: 20px 0px;">
	      			<strong style="font-size: 24px;"><span style="color: red;">CIA AI</span> 알고리즘 추천 강의</strong>
	      		</div>
	      		<div class="overflow100">
	      			<div id="us_3" class="clearfix search_list standard">	      			
		      		</div>
	      		</div>
	      		<div style="padding: 20px 0px;">
	      			<strong style="font-size: 24px;"><span style="color: red;">CIA AI</span> 알고리즘 추천 스터디</strong>
	      		</div>
	      		<div class="overflow100">
	      			<div id="us_4" class="clearfix search_list standard">		      			
		      		</div>
	      		</div>
			</div>
			
			<div style="padding-top: 40px; text-align: center;">
				<a class="conf_btn2 eff" href="javascript:closeF();">Close</a>
			</div>
			
		</div>
	</div>
	
</div>
<script>
var randomScalingFactor = function() {
	return Math.round(Math.random() * 100);
};

var color = Chart.helpers.color;
var myChart = new Chart(document.getElementById('proStat1'), {
    type: 'radar',
	data: {
		labels: [['수강', '강의'], ['수강', '스터디'], '계급', '나이', '현재 관심도'],
		datasets: [{
			label: '나의 CIA점수',
			backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
			borderColor: window.chartColors.red,
			pointBackgroundColor: window.chartColors.red,
			data: [
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor()
			]
		}, {
			label: '평균 회원 CIA점수',
			backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
			borderColor: window.chartColors.blue,
			pointBackgroundColor: window.chartColors.blue,
			data: [
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor()
			]
		}]
	},
	options: {
		legend: {
			position: 'top',
		},
		title: {
			display: true,
			text: 'CIA AI 알고리즘(추천 강의/스터디)'
		},
		scale: {
			ticks: {
				beginAtZero: true
			}
		}
	}
});
</script>

	


	<div id="global_navi"></div>
		
	<section id="wrapper">
		
		<header class="header clearfix" style="height: 68px;">
					
			<a href="/stat/basic">
				<strong class="logo" style="font-size: 30px; font-weight: 800; line-height: 68px;">
					CIA Statistics
					<span style="font-size: 14px;">실시간 주요 통계 데이터 현황 모니터링 </span>
				</strong>
			</a>
			
			
		</header>
		
		<!-- #####################################################################[컨테이너 시작] -->
		<section class="container clearfix">	
			
            <!-- <div class="sub_navi_wrapper">
				<div class="box" style="height: 100%;">
					<h2>Dashboard</h2>
					<ul class="sub_navi">
						
						<li class="c_g">Statistics</li>

						<li><a href="/stat/basic">Category</a></li>
						
					</ul>
				</div>
			</div>   -->
			
            <tiles:insertAttribute name="wrap_content" />
            
			
			<footer class="footer">
				Copyright ⓒ Collective Intelligence in Army 2020.All rights reserved.
			</footer>	
		</section>
		<!-- #####################################################################[컨테이너 끝] -->
	</section>
    
</body>
</html>  