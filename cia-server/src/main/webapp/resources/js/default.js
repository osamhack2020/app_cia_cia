
 


/**
 * 작성자 : 정희성
 * @What
 * 		(주)컨플 자바스크립트/제이쿼리 관련 모듈 모음
 */
var conple = {
		
		// [에니메이션,동작관련]
		animate : {
			
			// 페이지 맨 위로 스크롤을 올린다.
			movePageTop : function() {
				var body = $("html, body");
			    body.stop().animate({scrollTop:0}, '500', 'swing', function() { 
			      // alert("Finished animating");
			    });
			}
			
		},
		
		// [쿠키관련]
		cookie : {
			
			// 저장된 쿠키를 가져온다.
			getCookie : function(name) {
				var Found = false
				var start, end
				var i = 0
				while(i <= document.cookie.length) {
					start = i
					end = start + name.length
					if(document.cookie.substring(start, end) == name) {
						Found = true
						break
					}
					i++
				}
				if(Found == true) {
					start = end + 1
					end = document.cookie.indexOf(";", start)
					if(end < start)
						end = document.cookie.length
					return document.cookie.substring(start, end)
				}
				return ""
			},
			// 쿠키를 저장한다.
			setCookie : function(name, value, expiredays){
				
				var endDate = new Date();
				endDate.setDate(endDate.getDate()+expiredays);
				document.cookie = name + "=" + escape(value) + "; path=/; expires=" + endDate.toGMTString() + ";";

			}
			
		},
		
		// [기본관련]
		basic : {
			
			// 유저에이전트가 PC인지 구분한다.
			isUserAgentPC : function(){				
				var UserAgent = navigator.userAgent;
				if (UserAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || UserAgent.match(/LG|SAMSUNG|Samsung/) != null){
					return false;
				}else{
					return true;
				}
			},
			
			// 금액을 콤마(,)포함시켜서 문자열로 리턴한다.
			getPriceWithComma : function(values){
				values = parseInt(values);  
				values = String(values);
			    return values.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
			},
			
			// 타켓문자가 포함된 문자를 모두 대체한다.
			replaceAll : function(str, target, replacement){
				return str.split(target).join(replacement);
			}
			
		},
	
		// [값추출관련]
		extract : {
			
			/*
			 * 유튜브 동영상의 썸네일이미지를 추출한다.
			 */
			getYoutubeSumnailSrc : function(srcValue){
				
				var vid = '';				
				try{					
					pattern = /www.youtube.com\/embed\/([\d\w]+)/;
					match = pattern.exec(srcValue)
					vid = match[1];					
				}catch(e){}
				
				return 'https://img.youtube.com/vi/'+vid+'/0.jpg';
			
			},
			/*
			 * 비메오 동영상의 썸네일이미지를 추출한다.
			 * @params
			 * 		srcValue : 비메오 iframe src 값
			 * @returns
			 * 		썸네일이미지주소
			 */
			getVimeoSumnailSrc : function(srcValue, successFunc, failFunc){
				 
				var vid = '';
				try{				
					pattern = /player.vimeo.com\/video\/([\d\w]+)/;
					match = pattern.exec(srcValue)
					vid = match[1];					
				}catch(e){}
				
				console.log('vid:'+vid);  
				
				var ajaxUrl = 'http://vimeo.com/api/v2/video/'+vid+'.json';
				$.ajax({
				    url: ajaxUrl,
				    dataType: "json"				    
				}).done(successFunc).fail(failFunc);   
 
			} 
			
		},
		
		// [리사이즈관련]
		resize 	: {
			
			// 자동높이 리사이징(IFRAME높이자동설정할때 사용) 
			// onload이벤트에 달것 , i는 this임
			autoResize : function(i){
				var iframeHeight=(i).contentWindow.document.body.scrollHeight;
				(i).height=iframeHeight+20;
			}
			
		},
		
		// [파일관련]
		file : {
			// 파일의 원본이름,크기(KB,MB),확장자를 리턴한다.
			parseFileInfo : function(file){
				var size = file.size;//파일크기
				var name = file.name;//원본파일이름
			    var ext = name.slice(name.lastIndexOf(".")+1).toLowerCase();// 확장자				
				var sizeStr = "";
				
			    var sizeKB = size/1024;
			    if(parseInt(sizeKB) > 1024){
			        var sizeMB = sizeKB/1024;
			        sizeStr = sizeMB.toFixed(2)+" MB";
			    }else{
			        sizeStr = sizeKB.toFixed(2)+" KB";  
			    }
			    
			    console.log("이름 : " + name + ", 확장자 : " + ext + ", 크기 : " + sizeStr);			    
			    var result = {
			    	"name" : name,
			    	"ext" : ext,
			    	"size" : sizeStr
			    };
			    return result;
			    
			},
			// IMGUR 파일업로드(파일데이터,진행률함수,콜백함수)
			uploadByImgur : function(file, callback){
				
			    form = new FormData();
				form.append('image', file); 
				$.ajax({					
					xhr: function(){
						var xhr = new window.XMLHttpRequest();
						//Upload progress
						xhr.upload.addEventListener("progress", function(evt){
							if (evt.lengthComputable) {				
								// 업로드진행률
								var p = parseInt( (evt.loaded / evt.total * 100), 10)+"%";
								console.log("업로드진행률:"+p);
							}
						}, false);  
						//Download progress
						xhr.addEventListener("progress", function(evt){
							if (evt.lengthComputable) {		
								// 다운로드진행률
								var d = parseInt( (evt.loaded / evt.total * 100), 10)+"%";
								console.log("다운로드진행률:"+d);
							} 
						}, false);
						return xhr;
					},  					
					url: 'https://api.imgur.com/3/image',				
					headers: { Authorization: IMGUR_CLIENT_ID },				
					type: 'POST',				
					data: form,				
					cache: false,					
					contentType: false, 					
					processData: false					
				}).always(callback);  
			} 

		},		
		// [폼관련]
		frm : {
			// 폼 객체 생성
			formCreate : function(nm,mt,at,tg) {
				var f = document.createElement("form");
				f.name = nm;
				f.method = mt;
				f.action = at;
				f.target = tg ? tg : "_self";
				return f; 
			},
			// 폼 인풋 생성
			formInput : function(f,n,v) {
				var i = document.createElement("input");
				i.type = "hidden";
				i.name = n;
				i.value = v;
				//f.appendChild(i);
				f.insertBefore(i, null);
				//f.insertBefore(i);
				return f;
			},
			// 폼 파일인풋 생성 
			formFileInput : function(f,n,v) {
				var i = document.createElement("input");
				i.type = "file";
				i.name = n;
				i.value = v;
				//f.appendChild(i);
				f.insertBefore(i, null);
				//f.insertBefore(i);
				return f;
			},
			// 폼 전송
			formSubmit : function(f) {
				document.body.appendChild(f);
				f.submit();
			} 
		},
		// [유효성검사]
		check : {
			// 널,공백,undefined 검사 
			isNotNull : function(v){			
			    if ( typeof v == "undefined" || v == null || v ==""){
			        return false;
			    }else {
			        return true;
			    }			 
			},
			// 이메일 유효성 검사
			isEmail : function(e){
				var regEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
				return regEmail.test(e);    
			}, 
			// 휴대전화 유효성 검사
			isCellPhone : function(p){
				var regPhone = /^((01[1|6|7|8|9])[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/;
				return regPhone.test(p); 
			},
			// 아이디 검사(영문소문자,숫자로만 이루어진)
			isId : function(e) {
				var idReg = /^[a-z]+[a-z0-9]{3,19}$/g;
		        if(!idReg.test(e)) {
		            console.log("아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다.");
		            return false;
		        }
		        return true;
			}

			
		},
		// [키이벤트관련]
		keyevent : {
			// 한글입력방지
			removeKorean : function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				//좌우 방향키, 백스페이스, 딜리트, 탭키에 대한 예외
		        if(keyID == 8 || keyID == 9 || keyID == 37 || keyID == 39 || keyID == 46 ) 
		        	return; 
		        else 
		        	event.target.value = event.target.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');  
			},
			// 오직숫자만
			onlyNumber : function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
					return;
				else
					return false;
			},
			// 숫자외모두입력방지
			removeChar : function(event){
				event = event || window.event;
				var keyID = (event.which) ? event.which : event.keyCode;
				if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
					return;
				else
					event.target.value = event.target.value.replace(/[^0-9]/g, "");
			}
		}
		
};