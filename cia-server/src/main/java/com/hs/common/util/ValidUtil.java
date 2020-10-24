package com.hs.common.util;

import java.util.regex.Pattern;

public class ValidUtil {
	
	/**
	 * 영문 소문자, 대문자, 숫자로만 이루어져있는지 확인한다.
	 * @param textInput
	 * @return
	 * 		true:성공, false:실패
	 */
	public static boolean checkInputOnlyNumberAndAlphabet(String textInput){
		if(textInput==null){
			return true;
		}
		char chrInput;
		for (int i = 0; i < textInput.length(); i++){
			chrInput = textInput.charAt(i); // 입력받은 텍스트에서 문자 하나하나 가져와서 체크
			if (chrInput >= 0x61 && chrInput <= 0x7A) {
			    // 영문(소문자) OK!
			}else if (chrInput >=0x41 && chrInput <= 0x5A) {
			    // 영문(대문자) OK!
			}else if (chrInput >= 0x30 && chrInput <= 0x39) {
			    // 숫자 OK!
			}else {
			    return false;   // 영문자도 아니고 숫자도 아님!
			}
		}
		return true;
	}
	
	// 이메일 유효성 검증
	public static boolean isEmail(String str) {
        if (str==null) return false;
        return Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",str.trim());
    } 
	
	public static void main(String[] args) {
		
		System.out.println( isCellPhone("01032751025"));
	}
	
	// 휴대전화번호 유효성 검증
	public static boolean isCellPhone(String str) {
		if(str==null) return false;
		return str.matches("(01[016789])-(\\d{4})");
	}

	// 정수인지 판별
	public static boolean isInteger(String val) {
		try {
			Integer.parseInt(val.trim());
			return true;
		} catch (Exception ec) {
			return false;
		}
	}

}
