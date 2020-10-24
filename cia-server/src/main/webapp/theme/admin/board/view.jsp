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
        
        <div class="paddingbox" style="padding-top: 20px;">
						<div class="clearfix" style="text-align: right;">
							<span class="b_txt">프로젝트 &gt; 상세보기</span>
						</div>
						<div>
							
							<div class="clearfix" style="padding: 20px 0px 20px 0px; text-align: right;">
								  
								<a class="btn white" href="./list"><i class="fa fa-bars"></i> 목록</a>
                                <a class="btn blue" href="./input?idx=${boardInfo.idx}">수정</a>
<!--                                 <a class="btn white" style="cursor: pointer;" onclick="checkDel();"><i class="fa fa-trash-alt"></i> 삭제</a> -->

							</div>
							
							<table class="table_style2 view">
                                <tbody>
                                <tr>
									<td class="b w1 pc-open">분류</td>
									<td colspan="7"><div class="m-open-block m_title">분류</div>${boardInfo.menuName}</td>
                                </tr> 
                                <tr>
									<td class="b w1 pc-open">태그</td>
									<td colspan="7"><div class="m-open-block m_title">태그</div>${boardInfo.tags}</td>
                                </tr>
								<tr>
									<td class="b w1 pc-open">제목</td>
									<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">제목</div>${boardInfo.title}</td>
								</tr>
								<tr>
									<td class="b w1 pc-open">등록일</td>
									<td colspan="7"><div class="m-open-block m_title">등록일</div>${boardInfo.regdate} (최종수정일:${boardInfo.moddate})</td>
								</tr>
								<tr>
									<td class="b w1 pc-open">작성자</td>
									<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">작성자</div>${boardInfo.writer}</td>
								</tr>
								<tr>
									<td class="b w1 pc-open">조회</td>
									<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">조회</div>${boardInfo.views}</td>
								</tr>
                                <tr>
                                    <td class="b w1 pc-open">내용</td>
                                    <td colspan="7">${boardInfo.note}</td>
                                </tr>
								
							</tbody>
							</table>
							
							<div class="clearfix" style="padding: 20px 0px 20px 0px; text-align: right;">
								  
								<a class="btn white" href="./list"><i class="fa fa-bars"></i> 목록</a>
                                <a class="btn blue" href="./input?idx=${boardInfo.idx}">수정</a>
<!--                                 <a class="btn white" style="cursor: pointer;" onclick="checkDel();"><i class="fa fa-trash-alt"></i> 삭제</a> -->

							</div>
							
							
							
							
						</div>
						
						
						
					</div>
        
    </div>
</div>	










