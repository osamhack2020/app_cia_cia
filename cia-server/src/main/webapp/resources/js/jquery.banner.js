$(function() {
    var f_nBnrCnt = 0; // jQBanner를 사용해서 화면에 출력되는 베너수

    $.f_varBanner = {
        defaults: {
            /* 내부적으로 쓰이는 변수 */
            objScr: null,   // 스크린 영역
            objBtn: null, // 버튼 영역
            objTimer: null, // 타이머
            nOrderNo: 999, // 현재 선택된 레이어
            nOrderNoBtn: -1, // 버튼 선택시 레이어

            /* 초기세팅이 가능한  필수 변수 */
            nWidth: 0, // 베너 스크린 영역 폭
            nHeight: 0, // 베너 스크린 영역 높이
            nCount: 0, // 베너 스크린 영역에 속해있는 레이어의 수
            isActType: "none",  // 베너 액션 타입 none,left,right,up,down,fade,page

            /* 초기세팅이 가능한  옵션 변수 */
            isShuffle: "N", // 베너 레이어들의 셔플 여부 Y:셔플,N:순차적
            isBtnAct: "mouseover",  // 버튼 작동방식 mouseover,click
            isBtnType: "img", // 버튼 타입 img,li           
            nStartUp: 300, // 스타트 시 첫 레이어의 FadeIn 속도
            nDelay: 5000, // 딜레이
            nSpeed: 500, // 액션시의 속도
            nSpeedFade: 300, // 페이드 관련 액션에서 버튼 작동시 속도의 교정값 [되도록 고정]
            isStartFade:"Y", // 스타트 FadeIn 효과 여부 [프로그램으로 스크린을 Display:block 처리할 경우 사용]
            isStartDelay:"N" // 스타트시 딜레이를 가지고 실행 [무거운 페이지의 경우 딜레이를 가지고 초기화]
        }
    };

    $.fn.jQBanner = function(defaults){
	    var config = $.extend({}, $.f_varBanner.defaults, defaults);
        var isIEChk = jsBrowserCheck();

        config.objScr = "#"+this.attr("id")+" .clsBannerScreen";
        config.objBtn = "#"+this.attr("id")+" .clsBannerButton";
        
        if (config.isStartDelay=="Y" && isIEChk==true) {
            /* 스타트 딜레이 처리 */
            if (f_nBnrCnt>=0 && f_nBnrCnt<=3) {
                setTimeout( function(){jsBanInit(config)}, f_nBnrCnt*100);
            } else if (f_nBnrCnt>3 && f_nBnrCnt<=6) {
                setTimeout( function(){jsBanInit(config)}, f_nBnrCnt*200);
            } else if (f_nBnrCnt>6 && f_nBnrCnt<=10) {
                setTimeout( function(){jsBanInit(config)}, f_nBnrCnt*300);
            } else if (f_nBnrCnt>10) {
                setTimeout( function(){jsBanInit(config)}, 400);
            }
            f_nBnrCnt++;
        } else {
            jsBanInit(config);
        }

        return this;
    };

    function jsBanInit(config) {
        var nPosFix;

        /* 베너 스크린 CSS 설정 */
        $(config.objScr).css({'position':'relative','height':config.nHeight+'px','width':config.nWidth+'px','overflow':'hidden'});
        
        /* 첫 레이어 선택 */
        config.nOrderNo = (config.nOrderNo==999) ? Math.floor(Math.random() * config.nCount):config.nOrderNo-1;

        /* 액션 타입에 따른 초기 세팅 */
        if (config.isActType == "left") {
            $(config.objScr+"> .s_item").each(function(i){
                nPosFix = (config.nOrderNo==i)?0:config.nWidth;
                $(this).css({'position':'absolute','top':'0','left':(-1*nPosFix)+'px'});
                if (!(config.nOrderNo==i && config.isStartFade=="N")) {
                    $(this).fadeIn(config.nStartUp);
                }
                $(this).attr("divno",i);
            });
        } else if (config.isActType == "right") {
            $(config.objScr+"> .s_item").each(function(i){
                nPosFix = (config.nOrderNo==i)?0:config.nWidth;             
                $(this).css({'position':'absolute','top':'0','left':nPosFix+'px'});
                if (!(config.nOrderNo==i && config.isStartFade=="N")) {
                    $(this).fadeIn(config.nStartUp);
                }
                $(this).attr("divno",i);
            });
        } else if (config.isActType == "up") {
            $(config.objScr+"> .s_item").each(function(i){
                nPosFix = (config.nOrderNo==i)?0:config.nHeight;
                $(this).css({'position':'absolute','top':(-1*nPosFix)+'px','left':'0'});
                if (!(config.nOrderNo==i && config.isStartFade=="N")) {
                    $(this).fadeIn(config.nStartUp);
                }
                $(this).attr("divno",i);
            });
        } else if (config.isActType == "down") {
            $(config.objScr+"> .s_item").each(function(i){
                nPosFix = (config.nOrderNo==i)?0:config.nHeight;
                $(this).css({'position':'absolute','top':nPosFix+'px','left':'0'});
                if (!(config.nOrderNo==i && config.isStartFade=="N")) {
                    $(this).fadeIn(config.nStartUp);
                }
                $(this).attr("divno",i);
            });
        } else if (config.isActType == "fade") {
            $(config.objScr+"> .s_item").each(function(i){
                if (config.nOrderNo==i) {
                    nPosFix = 0;
                    if (!(config.nOrderNo==i && config.isStartFade=="N")) {
                        $(this).fadeIn(config.nStartUp);
                    }
                } else {
                    nPosFix = config.nWidth;
                }
                $(this).css({'position':'absolute','top':'0','left':nPosFix+'px'});
                $(this).attr("divno",i);
            });
        } else if (config.isActType == "page") {
            $(config.objScr+"> .s_item").each(function(i){
                nPosFix = (config.nOrderNo==i)?0:config.nHeight;
                $(this).css({'position':'absolute','top':nPosFix+'px','left':'0'});
                if (!(config.nOrderNo==i && config.isStartFade=="N")) {
                    $(this).fadeIn(config.nStartUp);
                }
                $(this).attr("divno",i);
            });
        } else if (config.isActType == "none") {
            $(config.objScr+"> .s_item").each(function(i){
                if (config.nOrderNo==i) {
                    nPosFix = 0; 
                    if (!(config.nOrderNo==i && config.isStartFade=="N")) {
                        $(this).fadeIn(config.nStartUp);
                    }
                } else {
                    nPosFix = config.nWidth;
                }
                $(this).css({'position':'absolute','top':'0','left':nPosFix+'px'});
                $(this).attr("divno",i);
            });
        }

        /* 버튼 순번 설정 */
        $(config.objBtn+" "+config.isBtnType).each(function(i){ $(this).attr("btnno",i); $(this).css("cursor","hand") });

        /* 버튼 바인드 */
        jsBanBtnBind(config);
        
        /* 스크린 pause 바인드 */
        jsScrPauseBind(config);

        /* 베너 타이머 등록 및 액션 시작!!!! */
        if (config.isActType != "none" && config.isActType != "page") {
            config.objTimer = setTimeout( function(){jsBanTimer(config)}, config.nDelay);
        }
    };

    /* 배너 타이머 */
    function jsBanTimer(config) {
        /* 버튼 액션 순번의 초기화 */
        config.nOrderNoBtn = -1;

        jsBanAction(config);
        config.objTimer = setTimeout( function(){jsBanTimer(config)}, config.nDelay);
    };

    /* 배너 액션 처리 */
    function jsBanAction(config) {
        var nPageSel;
        var nSpeed = config.nSpeed;
        var nDivNoSel = config.nOrderNo;
        var nOrderNext = (config.nOrderNo+1)%(config.nCount);

        /* 'page' 액션일 경우 선택페이지 추출 */
        if (config.isActType == "page") {
            var nOrderMinus = config.nOrderNoBtn-config.nOrderNo;
            nPageSel = (nOrderMinus==1 || (nOrderMinus==(config.nCount-1)*-1)) ? 1 : 0;
        }   

        if (config.nOrderNoBtn >= 0) {
            config.nOrderNo = config.nOrderNoBtn;
            nOrderNext = config.nOrderNoBtn;
            nSpeed = (config.isActType=="fade" || config.isActType=="page" || config.isActType=="none") ? config.nSpeedFade : 0;
        } else {
            config.nOrderNo = ((config.nCount-1)==config.nOrderNo) ? nOrderNext : config.nOrderNo+1;
        }

        var nDivNoNext = nOrderNext;

        var objSelObj = $(config.objScr+" div[divno='"+nDivNoSel+"']");
        var objNextObj = $(config.objScr+" div[divno='"+nDivNoNext+"']");

        /* 액션에 의한 버튼 선택 */
        if (config.isActType != "page") {
            jsBanBtnRO(config,nOrderNext);
        }

        /* 실제 스크린 레이어들의 동작 처리 */
        if (config.isActType == "left") {
            objNextObj.css('left',(config.nWidth)+'px');
            objSelObj.animate({'left':(-1*config.nWidth)+'px'},nSpeed);
            objNextObj.animate({'left':'0'},nSpeed);
        } else if (config.isActType == "right") {
            objNextObj.css('left',(-1*config.nWidth)+'px');
            objSelObj.animate({'left':config.nWidth+'px'},nSpeed);
            objNextObj.animate({'left':'0'},nSpeed);
        } else if (config.isActType == "up") {
            objNextObj.css('top',config.nHeight+'px');
            objSelObj.animate({'top':(-1*config.nHeight)+'px'},nSpeed);
            objNextObj.animate({'top':'0'},nSpeed);
        } else if (config.isActType == "down") {
            objNextObj.css('top',(-1*config.nHeight)+'px');
            objSelObj.animate({'top':config.nHeight+'px'},nSpeed);
            objNextObj.animate({'top':'0'},nSpeed);
        } else if (config.isActType == "fade") {
            objSelObj.css({'left':config.nWidth+'px','display':'none'});
            objNextObj.css('left','0');
            objNextObj.fadeIn(nSpeed);
        } else if (config.isActType == "page") {
            if (nPageSel == 0) {
                objNextObj.css({'top':'0','left':config.nWidth+'px'});
                objSelObj.animate({'left':(-1*config.nWidth)+'px'},nSpeed);
                objNextObj.animate({'left':'0'},nSpeed);
            } else if (nPageSel == 1) {
                objNextObj.css({'top':'0','left':(-1*config.nWidth)+'px'});
                objSelObj.animate({'left':config.nWidth+'px'},nSpeed);
                objNextObj.animate({'left':'0'},nSpeed);
            }
        } else if (config.isActType == "none") {
            objSelObj.css({'left':config.nWidth+'px','display':'none'});
            objNextObj.css('left','0');
            objNextObj.fadeIn(nSpeed);
        }
    }

    /* 스크린 pause 바인드 */
    function jsScrPauseBind(config) {
        /* 스크린에 대한 처리 */
        $(config.objScr).mouseover(function(){
            clearTimeout(config.objTimer);
        });
        $(config.objScr).mouseout(function(){
            if (config.isActType != "none" && config.isActType != "page") {
                config.objTimer = setTimeout( function(){jsBanTimer(config)}, config.nDelay);
            }
        });     
    }
    
    /* 버튼 바인드 */
    function jsBanBtnBind(config) {
        /* 초기 버튼 설정 */
        if (config.isActType != "page") {
            jsBanBtnRO(config,config.nOrderNo);
        }
        
        /* 버튼에 대해 마우스 오버일 경우 처리 */
        if (config.isBtnAct=="mouseover") {
            /* 버튼에 대한 처리 */
            $(config.objBtn+" "+config.isBtnType).mouseover(function(){
                var nImgNo = $(this).attr("btnno");

                if (config.isActType != "none" && config.isActType != "page") {
                    clearTimeout(config.objTimer);
                }

                if (config.isActType == "page") {
                    if (nImgNo == 0) {
                        config.nOrderNoBtn = ((config.nOrderNo-1)<0) ? config.nCount-1 : config.nOrderNo-1;
                    } if (nImgNo == 1) {
                        config.nOrderNoBtn = ((config.nOrderNo+1)==config.nCount) ? (config.nOrderNo+1)%config.nCount : config.nOrderNo+1;
                    }
                    jsBanBtnRO(config,nImgNo);
                } else {
                    config.nOrderNoBtn = parseInt(nImgNo);
                }

                jsBanAction(config);
            });
            $(config.objBtn+" "+config.isBtnType).mouseout(function(){
                if (config.isActType != "none" && config.isActType != "page") {
                    config.objTimer = setTimeout( function(){jsBanTimer(config)}, config.nDelay);
                }
            });

        /* 버튼에 대해 클릭일 경우 처리 */
        } else if (config.isBtnAct=="click") {
            $(config.objBtn+" "+config.isBtnType).click(function(){
                var nImgNo = $(this).attr("btnno");
                
                if (config.isActType != "none" && config.isActType != "page") {
                    clearTimeout(config.objTimer);
                }

                if (config.isActType == "page") {
                    if (nImgNo == 0) {
                        config.nOrderNoBtn = ((config.nOrderNo-1)<0) ? config.nCount-1 : config.nOrderNo-1;
                    } if (nImgNo == 1) {
                        config.nOrderNoBtn = ((config.nOrderNo+1)==config.nCount) ? (config.nOrderNo+1)%config.nCount : config.nOrderNo+1;
                    }
                    jsBanBtnRO(config,nImgNo);
                } else {
                    config.nOrderNoBtn = parseInt(nImgNo);
                }
                
                jsBanAction(config);

                if (config.isActType != "none" && config.isActType != "page") {
                    config.objTimer = setTimeout( function(){jsBanTimer(config)}, config.nDelay);
                }
            });
        }
    };

    /* 버튼 롤오버 처리 */
    function jsBanBtnRO (config,nSel) {
        if (config.isBtnType=="img") {
            $(config.objBtn+" img").each(function(i){
                if (nSel==i) {
                    $(this).attr("src",$(this).attr("oversrc"));
                } else {
                    $(this).attr("src",$(this).attr("outsrc"));
                }
            });
            
        } else if (config.isBtnType=="li") {
            $(config.objBtn+" li").each(function(i){
                if (nSel==i) {
                    $(this).attr("class",$(this).attr("overclass"));
                } else {
                    $(this).attr("class",$(this).attr("outclass"));
                }
            });
            
        }
    }

    /* IE 체크 */
    function jsBrowserCheck() {
        appname = navigator.appName;
        useragent = navigator.userAgent;
    
        if(appname == "Microsoft Internet Explorer") appname = "IE";
        IE55 = (useragent.indexOf('MSIE 5.5')>0);  //5.5 버전
        IE6 = (useragent.indexOf('MSIE 6')>0);     //6.0 버전
        IE7 = (useragent.indexOf('MSIE 7')>0);     //7.0 버전
        IE8 = (useragent.indexOf('MSIE 8')>0);     //8.0 버전
    
        if(appname=="IE" && IE55 || IE6 || IE7 || IE8){
            return true
        }else{
            return false; 
        }
    }   
});