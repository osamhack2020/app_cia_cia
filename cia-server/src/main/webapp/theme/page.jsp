<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
    
	<meta charset="UTF-8"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	
	<title>Collective Intelligence in Army 2020</title> 
	
	<link rel="shortcut icon" href="/resources/favicon.ico" type="image/x-icon"/> 

	<!-- normalize -->
	<link type="text/css" rel="stylesheet" href="/theme_admin/normalize.css"/>
	
	<script type="text/javascript" src="/common_${RESOURCES_VER}/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/common_${RESOURCES_VER}/js/jquery.form.js"></script>
	
	<!-- fontawesome -->
	<link rel="stylesheet" href="/common_${RESOURCES_VER}/fontawesome/css/all.min.css"/>
    <script src="/common_${RESOURCES_VER}/fontawesome/js/fontawesome.min.js"></script> 
    
	<script type="text/javascript" src="/common_${RESOURCES_VER}/js/Chart.js"></script>
	
	<!-- global -->
	<link type="text/css" rel="stylesheet" href="/theme_admin/global.css"/>
	<script type="text/javascript" src="/theme_admin/global.js"></script>
	
</head>  
<body>
	<div id="global_navi"></div>
		
	<section id="wrapper">
		
		<header class="header clearfix" style="height: 68px;">
					
			<a href="/stat/basic"><strong class="logo" style="font-size: 30px; font-weight: 800; line-height: 68px;">CIA Statistics</strong></a>
			
			<div class="pc-open">   
				
<!-- 				<span class="bar">&nbsp;</span> -->
				<a href="/" class="b">Home</a>
				
			</div>
			<div class="m-open-block">
				<a class="ico" id="navi1"><i class="fa fa-bars"></i></a>
				<a class="ico none_hover" id="navi2"><i class="fa fa-ellipsis-h"></i></a>
			</div>
			
		</header>
		
		<!-- #####################################################################[컨테이너 시작] -->
		<section class="container clearfix">	
			
            <div class="sub_navi_wrapper">
				<div class="box" style="height: 100%;">
					<h2>Dashboard</h2>
					<ul class="sub_navi">
						
						<li class="c_g">Statistics</li>

						<li><a href="/stat/basic">Category</a></li>
						
						
						
						
					
					</ul>
				</div>
			</div>  
			
            
			
			<footer class="footer">
				Copyright ⓒ Collective Intelligence in Army 2020.All rights reserved.
			</footer>	
		</section>
		<!-- #####################################################################[컨테이너 끝] -->
	</section>
    
</body>
</html>  