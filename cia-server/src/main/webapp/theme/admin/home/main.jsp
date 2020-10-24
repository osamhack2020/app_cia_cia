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


<form onsubmit="return false;">
	<input id="email" type="text"/>
	<input id="password" type="password"/>
	<input type="submit" onclick="loginFunc()"/>
</form>

<script>

var pw = "heesung20201";

console.log(calcMD5(pw));


function loginFunc() {   
    $.ajax({ 
        type : "POST",
        dataType : "JSON",
        data : {
            "email" : $("#email").val(),
            "password" : $("#password").val()
        },
        async: false,
        url : "/api/users/signin",	            
        success : function(data, txt, resp){
        	console.log(data);
			if(data.statusCode==200) {
				console.log(resp.getResponseHeader("Authorization"));
				cookie.setCookie("HSID", resp.getResponseHeader("Authorization"), 1);			
			}else{
				alert("Error"); 
			}	
        }, 
        error : function(e1, e2, e3){
        	alert("Error");   
        } 
    });
}
</script>






