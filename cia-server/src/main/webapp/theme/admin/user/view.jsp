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
        <div class="headline">회원 관리</div>
        
        <div class="paddingbox" style="padding-top: 20px;">
			
			<div class="clearfix" style="text-align: right;">
				<span class="b_txt">회원 &gt; 상세보기</span>
			</div>
			
			<div>
				
				<div class="clearfix" style="padding: 20px 0px 20px 0px; text-align: right;">								  
					<a class="btn white" href="./list"><i class="fa fa-bars"></i> 목록</a>
					<a class="btn blue" href="./input?idx=${userInfo.idx}">수정</a>
				</div>
				
				<table class="table_style2 view">
                <tbody>
                	<tr>
						<td class="b w1 pc-open">IDX</td>
						<td colspan="7"><div class="m-open-block m_title">IDX</div>${userInfo.idx}</td>
                	</tr>
                	<tr>
						<td class="b w1 pc-open">권한목록</td>
						<td colspan="7"><div class="m-open-block m_title">권한목록</div>
							
							${userInfo.authList}
							
							&nbsp;&nbsp;&nbsp;
							<input class="txtbox h43" type="text" name="authName" placeholder="'ROLE_' 빼고 입력" style="width: 150px; margin-right: 10px;" autocomplete="off"/>
                     				<a class="btn blue2" onclick="workAuth('post')" style="cursor: pointer;"><i class="fa fa-search" style="font-size: 15px;"></i> 추가</a>
							<a class="btn red" onclick="workAuth('delete')" style="cursor: pointer;"><i class="fa fa-search" style="font-size: 15px;"></i> 삭제</a>
						</td>
					</tr>
					<tr>
						<td class="b w1 pc-open" colspan="8">회원 기본정보</td>
					</tr>
					<tr>
						<td class="b w1 pc-open">Condition</td>
						<td colspan="7"><div class="m-open-block m_title">Condition</div>
							
							${userInfo.id}
							
								<c:if test="${userInfo.enabled==true}"><span style="font-size: 12px; color: blue">(정상 회원입니다)</span></c:if>
								<c:if test="${userInfo.enabled==false}"><span style="font-size: 12px; color: red">(탈퇴 회원입니다)</span></c:if>
							
							
							&nbsp;&nbsp;&nbsp;
							<c:if test="${userInfo.enabled==true}">
								<a class="btn" onclick="workEnable(false)" style="cursor: pointer; vertical-align: middle">탈퇴처리</a>
							</c:if>
							<c:if test="${userInfo.enabled==false}">
								<a class="btn blue2" onclick="workEnable(true)" style="cursor: pointer; vertical-align: middle">회원전환</a>
							</c:if> 
						</td>
					</tr>
               		<tr>
						<td class="b w1 pc-open">PASSWORD</td>
						<td colspan="7"><div class="m-open-block m_title">PASSWORD</div>
						
							<input class="txtbox h43" type="password" name="password" placeholder="변경 비밀번호 입력" value="" style="width: 300px; margin-right: 10px;"/>
                     		<a class="btn blue2" onclick="workPassword()" style="cursor: pointer;"><i class="fa fa-search" style="font-size: 15px;"></i> 비밀번호 변경</a>
						</td>
                    </tr>
                    <tr>
						<td class="b w1 pc-open">가일입</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">가입일</div>${userInfo.regdate} (최종접속일:${userInfo.findate})</td>
					</tr> 
					 <tr>
						<td class="b w1 pc-open">최종수정일</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">최종수정일</div>${userInfo.moddate}</td>
					</tr> 
                    <tr>
						<td class="b w1 pc-open">이름</td>
						<td colspan="7"><div class="m-open-block m_title">이름</div>${userInfo.name}</td>
                    </tr>
					<tr>
						<td class="b w1 pc-open">이메일</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">이메일</div>${userInfo.email}</td>
					</tr>
					<tr>
						<td class="b w1 pc-open">연락처</td>
						<td colspan="7"><div class="m-open-block m_title">연락처</div>${userInfo.phonenm}</td>
					</tr>
					<tr>
						<td class="b w1 pc-open">성별</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">성별</div>${userInfo.gender}</td>
					</tr>
					<tr>
						<td class="b w1 pc-open">캐시 잔액</td>
						<td colspan="7"><div class="m-open-block m_title">캐시</div>
							<span class="amountClass">${userInfo.cashPoint==null?0:userInfo.cashPoint}</span> point
							&nbsp;&nbsp;&nbsp; 
							<input class="txtbox h43" type="number" name="point" placeholder="금액 입력" value="" style="width: 100px; margin-right: 10px;"/>
                     				<a class="btn blue2" onclick="workCash('add')" style="cursor: pointer;"><i class="fa fa-search" style="font-size: 15px;"></i> 선물</a>
							<a class="btn blue2" onclick="workCash('use')" style="cursor: pointer;"><i class="fa fa-search" style="font-size: 15px;"></i> 차감</a>
						</td>
					</tr>
					<tr>
						<td class="b w1 pc-open" colspan="8">알바회원 추가정보</td>
					</tr>
					<!-- 알바 -->
					<tr>
						<td class="b w1 pc-open">프로필</td>
						<td colspan="7" style="font-weight: bold;">
							<div class="m-open-block m_title">프로필</div>
							
							<span class="profileCss" style="width: 70px; height: 70px; background-image: url(${userInfo.img1}), url(/theme/basic/resources/img/profile.png);"></span>
							
							<span class="profileCss" style="width: 70px; height: 70px; background-image: url(${userInfo.img2}), url(/theme/basic/resources/img/profile.png);"></span>
							
							<span class="profileCss" style="width: 70px; height: 70px; background-image: url(${userInfo.img3}), url(/theme/basic/resources/img/profile.png);"></span>
						
							<span class="profileCss" style="width: 70px; height: 70px; background-image: url(${userInfo.img4}), url(/theme/basic/resources/img/profile.png);"></span>
							
							<a class="btn blue2" id="profileImgBtn" style="cursor: pointer; vertical-align: middle; margin-left: 10px;"><i class="fa fa-file" style="font-size: 15px;"></i> 프로필 변경</a>
							<form id="filefrm" enctype="multipart/form-data" method="post" style="display: none !important;">
								<input type="file"/>
							</form>
						</td>
					</tr>
					<tr>
						<td class="b w1 pc-open">지역</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">지역</div>${userInfo.region}</td>
					</tr>
					<tr>
						<td class="b w1 pc-open">생년월일</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">생년월일</div>${userInfo.birthdate} (${userInfo.age}세)</td>
					</tr>
					<%-- <tr>
						<td class="b w1 pc-open">희망급여</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">희망급여</div>${userInfo.wantPay}</td>
					</tr> --%>
					<tr>
						<td class="b w1 pc-open">타수</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">타수</div>${userInfo.tasu}</td>
					</tr>
                    <tr>
                        <td class="b w1 pc-open">소개</td>
                        <td colspan="7">${userInfo.intro}</td>
                    </tr>                   
					<%-- <tr>
						<td class="b w1 pc-open">가격</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">가격</div>${userInfo.amount}</td>
					</tr> --%>
					<%-- <tr>
						<td class="b w1 pc-open">프로인증</td>
						<td colspan="7" style="font-weight: bold;"><div class="m-open-block m_title">프로인증</div>${userInfo.proFlag}</td>
					</tr> --%>
				</tbody>
				</table>
				
				<div class="clearfix" style="padding: 20px 0px 20px 0px; text-align: right;">					
					<a class="btn white" style="cursor: pointer;" onclick="deleteUser();"><i class="fa fa-trash-alt"></i> 회원 삭제</a>  
					<a class="btn white" href="./list"><i class="fa fa-bars"></i> 목록</a>
                    <a class="btn blue" href="./input?idx=${userInfo.idx}">수정</a>            
				</div>
				
				<div class="clearfix" style="padding: 20px 0px; text-align: right;">

                    <span class="b_txt" style="float: left;"><strong>캐시내역 </strong>(<strong class="data_total"></strong>건)</span>

                </div>
                <table class="table_style">
                    <thead>
                        <tr>
                            <th class="pc-open" style="width: 50px;">번호</th>
                            <th style="width: 100px;">날짜</th>
                            <th style="width: 120px;">회원이름</th>
                            <th>획득 금액</th>
                            <th>사용 금액</th>
                            <th>잔여 포인트</th>
                            <th>메모</th>                         
                        </tr>
                    </thead>
                    <tbody id="list_target_1">

                    </tbody>
                </table>
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
				
				
			</div>
		</div>
    </div>
</div>	


<script>

$(document).ready(function(){
	
	$("#profileImgBtn").click(function(){
		$("#filefrm input[type=file]").click();
	});  
	
	$("#filefrm input[type=file]").change(function(){
		
		var file = this.files[0];  	

		form = new FormData();
		form.append('image', file); 
		$.ajax({					
			xhr: function(){
				var xhr = new window.XMLHttpRequest();
				//Upload progress
				xhr.upload.addEventListener("progress", function(evt){
					if (evt.lengthComputable) {								
						var p = parseInt( (evt.loaded / evt.total * 100), 10)+"%";
						console.log("upload:"+p);
					}
				}, false);  
				//Download progress
				xhr.addEventListener("progress", function(evt){
					if (evt.lengthComputable) {	
						var d = parseInt( (evt.loaded / evt.total * 100), 10)+"%";
						console.log("download:"+d);
					} 
				}, false);
				return xhr;
			},  					
			url: '/file/upload',				
			//headers: { Authorization: IMGUR_CLIENT_ID },				
			type: 'POST',				
			data: form,				
			cache: false,					
			contentType: false, 					
			processData: false					
		}).always(function(data){
			
			console.log(data);
			$(".profileCss").css("background-image", "url("+data+")");
			workProfile(data);
			
		}); 
	
	});
	
});

function workProfile(url) {
	$.ajax({ 
	    type : 'PUT',
	    contentType : "application/json; charset=utf-8",
	    data : JSON.stringify({
	    	"idx" : ${userInfo.idx},
	    	"img" : url
	    }),
	    url : "/api/users/profile",           
	    error : function(e1,e2,e3){
	        console.log(e1);console.log(e2);console.log(e3);
	    },
	    success : function(){
	    	location.reload();
	    }  
	});	
}

function workCash(actionVal) {
	
	var point = $("input[name=point]").val();
	if(point == '') {
		alert("포인트 금액을 입력하세요");
		return;
	}
	
	var addPoint = 0;
	var usePoint = 0;
	
	if(actionVal=='use') {
		usePoint = point;
	}else {
		addPoint = point;
	}
	
	$.ajax({ 
	    type : 'POST',
	    contentType : "application/json; charset=utf-8",
	    data : JSON.stringify({
	    	"userIdx" : ${userInfo.idx},
	    	"addPoint" : addPoint,
	    	"usePoint" : usePoint
	    }),
	    url : "/api/cash",           
	    error : function(e1,e2,e3){
	        console.log(e1);console.log(e2);console.log(e3);
	    },
	    success : function(){
	    	location.reload();
	    }  
	});	
}

function deleteUser() {
	
	if(window.confirm('한번 삭제하면 다시 복구할 수 없습니다. 정말로 회원을 삭제하시겠습니까?','')){
		$.ajax({ 
		    type : 'DELETE',
		    contentType : "application/json; charset=utf-8",
		    /* data : JSON.stringify({
		    	"idx" : ${userInfo.idx}
		    }), */
		    url : "/api/users/${userInfo.idx}",           
		    error : function(e1,e2,e3){
		        console.log(e1);console.log(e2);console.log(e3);
		    },
		    success : function(){
		    	location.reload();
		    }  
		});	
	}
}

function workPassword() {
	
	var password = $("input[name=password]").val();
	if(password == '') {
		alert("변경할 패스워드를 입력하세요");
		return;
	}
	
	$.ajax({ 
	    type : 'PUT',
	    contentType : "application/json; charset=utf-8",
	    data : JSON.stringify({
	    	"idx" : ${userInfo.idx},
	    	"password" : password
	    }),
	    url : "/api/users/password",           
	    error : function(e1,e2,e3){
	        console.log(e1);console.log(e2);console.log(e3);
	    },
	    success : function(){
	    	location.reload();
	    }  
	});	
}

// withdraw
function workEnable(eFlag) {
	
	$.ajax({ 
	    type : 'PUT',
	    contentType : "application/json; charset=utf-8",
	    data : JSON.stringify({
	    	"idx" : ${userInfo.idx},
	    	"eFlag" : eFlag
	    }),
	    url : "/api/users/enable",           
	    error : function(e1,e2,e3){
	        console.log(e1);console.log(e2);console.log(e3);
	    },
	    success : function(){
	    	location.reload();
	    }  
	});	
}

function workAuth(mtd) {
	
	if(mtd!="post"&&mtd!="delete") {
		alert("지원되지 않는 접근입니다.");
		return;
	}
	
	var authName = $("input[name=authName]").val();
	if(authName == '') {
		alert("권한 명칭을 입력하세요. (ROLE_은 빼고 입력)");
		return;
	}
	
	$.ajax({ 
	    type : mtd,
	    contentType : "application/json; charset=utf-8",
	    data : JSON.stringify({
	    	"idx" : ${userInfo.idx},
	    	"authName" : authName
	    }),
	    url : "/api/users/auth",           
	    error : function(e1,e2,e3){
	        console.log(e1);console.log(e2);console.log(e3);
	    },
	    success : function(){
	    	location.reload();
	    }  
	});	
}

function loadCash(page) {
	
    $.ajax({ 
        type : "GET",
        dataType : "json",
        data : {
            "page" : page
        },
        url : "/api/cash",	           
        success : function(data){           
            var c = "";
            console.log(data.list);
            $(data.list).each(function(i,d){
                
            	c += '<tr>';
                c += '<td>'+(i+1)+'</td>';
                c += '<td>'+d.regdate+'</td>';
                c += '<td>'+d.userName+'</td>';
                c += '<td class="amountClass">'+d.addPoint+'</td>';
                c += '<td class="amountClass">'+d.usePoint+'</td>';
                c += '<td class="amountClass">'+d.amount+'</td>';
                c += '<td>'+d.memo+'</td>';
            	c += '</tr>';
                
            });
            $("#list_target_1").html(c);
            
            adjustAmountComma('amountClass');
            
            c = "";
            console.log(data.pageNav);
            var pn = data.pageNav;
            $(".data_total").text(pn.totalRowCount);
            if(pn.startPageNum > pn.pageBlockCount) {
            	c += '<a class="numb" onclick="loadCash('+(1)+')"><i class="fa fa-chevron-left"></i></a>';
            	c += '<a class="numb" onclick="loadCash('+(pn.startPageNum-1)+')"><i class="fa fa-chevron-left"></i></a>';
            }
            for(var i=pn.startPageNum;i<=pn.endPageNum;i++) {
            	c += '<a class="numb '+(i==pn.pageNum?"sel":"")+'" onclick="loadCash('+i+')">'+i+'</a>';
            }
            if(pn.endPageNum < pn.totalPageCount) {
            	c += '<a class="numb" onclick="loadCash('+(pn.endPageNum+1)+')"><i class="fa fa-chevron-left"></i></a>';
            	c += '<a class="numb" onclick="loadCash('+(pn.totalPageCount)+')"><i class="fa fa-chevron-left"></i></a>';
            }
            $(".pg_wrapper > .pg").html(c);
          	
        }, 
        error : function(e1,e2,e3){
            console.log(e1);console.log(e2);console.log(e3);
        } 
    });	
}    
loadCash(1);    


adjustAmountComma('amountClass');

</script>









