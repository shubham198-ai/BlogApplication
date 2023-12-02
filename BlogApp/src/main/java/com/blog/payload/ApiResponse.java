package com.blog.payload;

public class ApiResponse {
private String message;
private boolean Success;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public boolean isSuccess() {
	return Success;
}
public void setSuccess(boolean success) {
	Success = success;
}
public ApiResponse() {
	super();
	// TODO Auto-generated constructor stub
}
public ApiResponse(String message, boolean success) {
	super();
	this.message = message;
	Success = success;
}

}
