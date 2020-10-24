package com.hs.app.jwt.vo;

import org.springframework.http.HttpStatus;

public class Result {
	
	public static final String SUCCESS_MESSAGE = "성공";
    public static final String SERVER_ERROR_MESSAGE = "실패";

    private HttpStatus statusCode;
    private String message;
    private Object data;
    private int totalCount;

    public Result(){}

    public static Result successInstance(){
        return new Result().success();
    }

    public Result success(){
        statusCode = HttpStatus.OK;
        message = SUCCESS_MESSAGE;
        return this;
    }

    public Result fail(){
        statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        message = SERVER_ERROR_MESSAGE;
        return this;
    }

    public Result setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    
    public Result setData(Object data) {
    	this.data = data;
    	return this;
    }
    
    public int getStatusCode() {
        return statusCode.value();
    }

	@Override
	public String toString() {
		return "Result [statusCode=" + statusCode + ", message=" + message + ", data=" + data + ", totalCount="
				+ totalCount + "]";
	}
	 
    
    
    
	

    
}
