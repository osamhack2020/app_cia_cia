
$.fn.serializeObject = function(){
    var obj = {};

    $.each( this.serializeArray(), function(i,o){
        var n = o.name,
        v = o.value;

        obj[n] = obj[n] === undefined ? v
            : $.isArray( obj[n] ) ? obj[n].concat( v )
            : [ obj[n], v ];
    });

    return obj;
};

function isMobile() {
	var UserAgent = navigator.userAgent;
	// 모바일 일때
	if (UserAgent.match(/iPhone|iPod|iPad|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || UserAgent.match(/LG|SAMSUNG|Samsung/) != null){
		return true;
	}
	return false;
}

function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}



function initSubNaviHeight() {
	
	if($(".contents_wrapper").height()>$(".sub_navi_wrapper").height()) {
		$(".sub_navi_wrapper").css("height", $(".contents_wrapper").height()+"px");
	}else{
		$(".contents_wrapper").css("height", $(".sub_navi_wrapper").height()+"px");
	}
	
	
}

//부드럽게 레이어 이동
function moveLayer(idName) {
	var scrollPosition;
	
	if(!$("#header").hasClass("nonefix")) {
		scrollPosition = $("#"+idName).offset().top - 85;
	}else{
		scrollPosition = $("#"+idName).offset().top;
	}
	
	$("html, body").animate({scrollTop: scrollPosition}, 250);   
}


// 클립보드 복사 기능
function copy_to_clipboard(txtId) {
	var copyText = document.getElementById(txtId);
	copyText.select();
	document.execCommand("Copy"); 
}


function closePop() {
	$(".background_black_cover").css("display", "none");
	$(".popup_layer").css("display", "none");
}

function getPriceWithComma(values){
	values = parseInt(values);  
	values = String(values);
	return values.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

$(document).ready(function(){
	

	$.each($(".amt"), function(idx,val){
		$(val).text(getPriceWithComma($(val).text())); 	
	});
	
		
	$("#mobile_menu .tab_group .tab").click(function(){
		var numb = $(this).attr("data-numb");
		$("#mobile_menu .tab").removeClass("sel");   
		$("#mobile_menu .tab_numb"+numb).addClass("sel");
		$("#mobile_menu .tab_content").css("display", "none");
		$("#mobile_menu .tab_content"+numb).css("display", "block");
	});
	
		
		// 초기상태
		function initHeaderShape() {
			$(".header").removeClass("bd"); 
		}
		// 스크롤 했을때
		function hasScrolled() { 
			$(".header").addClass("bd"); 
		}
		
		var didScrollTop;
		var initHeader;
		var didScroll;  
		// 스크롤시에 사용자가 스크롤했다는 것을 알림 
		$(window).scroll(function(event){ 
			
			var st = $(this).scrollTop();
			if(st>50) { didScroll = true; }
			if(st<=50) { initHeader = true; }
			
			var footerTop = $(".footer").offset().top;
			if((footerTop-1500)<=st){didScrollTop = true;}
			else {didScrollTop = false;} 
			
		}); 
		// hasScrolled()를 실행하고 didScroll 상태를 재설정 
		setInterval(function() { if (didScroll) { hasScrolled(); didScroll = false; } }, 50); 
		setInterval(function() { if (initHeader) { initHeaderShape(); initHeader = false; } }, 50); 
		
		
		var isActSlide = false;
		
		
		
		//Open the menu
		jQuery("#navi2").click(function () {
			
			if(isActSlide == false) {isActSlide = true;}
			else { return; }

			jQuery('#wrapper').css('min-height', jQuery(window).height());
			jQuery('#mobile_menu').css('z-index', "100");
			jQuery('#mobile_menu').css('opacity', 1);

			$(".container").css("width", $('#wrapper').width()+"px");

			//display a layer to disable clicking and scrolling on the content while menu is shown
			jQuery('#contentLayer').css('display', 'block');
			jQuery('#contentLayer').css('right', '75%');

			//disable all scrolling on mobile devices while menu is shown
			//jQuery('#mobile_menu').bind('touchmove', function (e) { e.preventDefault() });
			//jQuery('#mobile_menu').bind('touchmove', function (e) { e.preventDefault() });
			//jQuery('#wrapper').unbind('touchmove', function (e) { e.preventDefault() });
			
			$("#wrapper").css("position", "relative");
			$(".container").css("position", "absolute");
			$(".container").css("right", "0px");   
			$(".container").css("left", "auto");   
			
			$(".container").css("padding", "23px 10px 10px 10px");   
			$(".header").css("position", "static");
			$("html, body").css("overflow", "hidden"); 
			$('#wrapper').css("overflow-y", "hidden");
			$(".logo").css('opacity', 0);
			
			//set margin for the whole container with a jquery UI animation
			jQuery("#wrapper").animate({"marginRight": ["75%", 'easeOutExpo']}, {
				duration: 700
			});
			jQuery("#mobile_menu").animate({"width": ["75%", 'easeOutExpo']}, {
				duration: 700,
				complete: function () {
					isActSlide = false;
				}
			});
			
			$("#contentLayer").addClass("navi2");

		});  
	
		
		//Open the menu 
		jQuery("#navi1").click(function () {
			
			if(isActSlide == false) {isActSlide = true;}
			else { return; }

			jQuery('#wrapper').css('min-height', jQuery(window).height());
			jQuery('.global_navi').css('z-index', "100");
			jQuery('.global_navi').css('opacity', 1);
			
			$(".container").css("width", $('#wrapper').width()+"px");

			//display a layer to disable clicking and scrolling on the content while menu is shown
			jQuery('#contentLayer').css('display', 'block');
			jQuery('#contentLayer').css('left', '75px');

			//disable all scrolling on mobile devices while menu is shown
			//jQuery('.global_navi').bind('touchmove', function (e) { e.preventDefault() });
			
			
			$("#wrapper").css("position", "static");
			$(".container").css("position", "static");
			$(".container").css("right", "auto");   
			$(".container").css("left", "0px");   
			
			$(".container").css("padding", "23px 10px 10px 10px");   
			$(".header").css("position", "static");
			$("html, body").css("overflow", "hidden"); 
			$('#wrapper').css("overflow-y", "hidden");
		
			
			//set margin for the whole container with a jquery UI animation
			jQuery("#wrapper").animate({"marginLeft": ["75px", 'easeOutExpo']}, {
				duration: 700
			});
			jQuery(".global_navi").animate({"width": ["75px", 'easeOutExpo']}, {
				duration: 700,
				complete: function () {
					isActSlide = false;
				}
			});
			
			$("#contentLayer").addClass("navi1");
			
			

		});

		//close the menu
		jQuery("#contentLayer, #nav1_close, #nav2_close, #nav3_close").click(function () {
			
			if(isActSlide == false) {isActSlide = true;}
			else { return; }
			
			
			$("#contentLayer").css("display","none");
			$("#contentLayer").css("left", "auto");
			$("#contentLayer").css("right", "auto");
			
			if($("#contentLayer").hasClass("navi1")) {
				
				//enable all scrolling on mobile devices when menu is closed
				jQuery('#wrapper').unbind('touchmove');
				
				//set margin for the whole container back to original state with a jquery UI animation
				jQuery("#wrapper").animate({"marginLeft": ["0", 'easeOutExpo']}, {
					duration: 350,
					
					complete: function () {
						
						$("#wrapper").css("position", "relative");
						$(".container").css("position", "static");
						$(".container").css("width", "auto");   
						$(".container").css("padding", "73px 10px 10px 10px");   
						$(".header").css("position", "fixed");
						$("html, body").css("overflow", "visible"); 
						
					}
				});
				jQuery(".global_navi").animate({"width": ["0px", 'easeOutExpo']}, {
					duration: 350,
					complete: function () {
						$('.global_navi').css('opacity', 0);
						$('.global_navi').css('z-index', 0);
						
						isActSlide = false;
					}
				});
				
				$("#contentLayer").removeClass("navi1");
				
			}else if($("#contentLayer").hasClass("navi2")) {
				
				//enable all scrolling on mobile devices when menu is closed
				jQuery('#wrapper').unbind('touchmove');
				
				$(".logo").css('opacity', 1);
				
				//set margin for the whole container back to original state with a jquery UI animation
				jQuery("#wrapper").animate({"marginRight": ["0", 'easeOutExpo']}, {
					duration: 350,
					
					complete: function () {
						
						$("#wrapper").css("position", "relative");
						$(".container").css("position", "static");
						$(".container").css("width", "auto");   
						$(".container").css("padding", "73px 10px 10px 10px");   
						$(".header").css("position", "fixed");
						$("html, body").css("overflow", "visible"); 
						
					}
				});
				jQuery("#mobile_menu").animate({"width": ["0%", 'easeOutExpo']}, {
					duration: 350,
					complete: function () {
						isActSlide = false;
					}
				});
				
				$("#contentLayer").removeClass("navi2");
			}
			else if($("#contentLayer").hasClass("navi3")) {
				
				//enable all scrolling on mobile devices when menu is closed
				jQuery('#wrapper').unbind('touchmove');
				
				//set margin for the whole container back to original state with a jquery UI animation
				jQuery("#wrapper").animate({"marginRight": ["0", 'easeOutExpo']}, {
					duration: 350,
					
					complete: function () {
						
						$("#wrapper").css("position", "relative");
						$(".container").css("position", "static");
						$(".container").css("width", "auto");   
						$(".container").css("padding", "73px 10px 10px 10px");   
						$(".header").css("position", "fixed");
						$("html, body").css("overflow", "visible"); 
						
						$(".logo").css('opacity', 1);
						
					}
				});
				jQuery("#global_navi2").animate({"width": ["0%", 'easeOutExpo']}, {
					duration: 350,
					complete: function () {
						isActSlide = false;
					}
				});
				
				$("#contentLayer").removeClass("navi3");
			}
			
			
		});
		
	
	
	
	initSubNaviHeight();
	$(window).resize(function(){ initSubNaviHeight(); });


});

// 아래로 19.09.18 추가 
function initGlobalNavi(c) {
	
	if(c=="pc") {
		$(".global_navi").css("width", "90px");
		$(".global_navi").css("background", "#324c98");
		$(".global_navi").css("height", "100%");
		$(".global_navi").css("top", "0px");
		$(".global_navi").css("left", "0px");
		$(".global_navi").css("z-index", "100");
		$(".global_navi").css("opacity", "1");
		
		
	}else{
		$(".global_navi").css("width", "0px");
		$(".global_navi").css("z-index", "0");
//		$(".global_navi").css("overflow-y", "scroll");
		$(".global_navi").css("opacity", "0");
		
	}
	
	$("#wrapper").css("position", "relative");
	$("#wrapper").css("margin-left", "0px");
	$(".container").css("position", "static");
	$(".container").css("width", "auto");   
	$(".container").css("padding", "73px 10px 10px 10px");   
	$(".header").css("position", "fixed");
	$("html, body").css("overflow", "visible"); 
	
}

function initMobileMenu(c) {
	$(".mobile_menu").css("width", "0px");
	$(".mobile_menu").css("z-index", "0"); 
	$(".mobile_menu").css("opacity", "0");
	$("#contentLayer").css("right", "0px");
	$("#contentLayer").css("display", "none");
	$("#wrapper").css("margin-right", "0px");
	$("#wrapper").css("overflow", "visible");
	
	$("#wrapper").css("position", "relative");
	$(".container").css("position", "static");
	$(".container").css("width", "auto");   
	$(".container").css("padding", "73px 10px 10px 10px");   
	$(".header").css("position", "fixed");
	$("html, body").css("overflow", "visible"); 
	$(".logo").css("opacity", 1); 
	
	
}

var is_curr_de = $(window).width()>1100?"pc":"mobile";

$(window).resize(function(){
	
	var w = $(window).width();
	if(w>1100 && is_curr_de == "mobile") {
		initGlobalNavi("pc");
		initMobileMenu("pc");
		is_curr_de = "pc";
	}else if(w <= 1100 && is_curr_de == "pc"){
		initGlobalNavi("mobile");
		initMobileMenu("mobile");
		is_curr_de = "mobile";
	}
	
});

function getPriceWithComma(values){
	values = parseInt(values);  
	values = String(values);
    return values.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}
function adjustAmountComma(className) {
	$("."+className).each(function(){
		var data = $(this).text();
		console.log(data.indexOf(","));
		if(data.indexOf(",") == -1) {
			data = getPriceWithComma(data);
			$(this).text(data);
		}
	});
}

//[쿠키관련]
var cookie = {
	
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
	
}