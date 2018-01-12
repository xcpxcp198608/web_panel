package com.wiatec.panel.common.result;

/**
 * @author patrick
 */

public enum  EnumResult {

    /**
     * response json result
     */
    SUCCESS(200, "Successfully"),
    ERROR_RE_LOGIN(401, " Login time session expired, please login to your account again."),
    ERROR_UNAUTHORIZED(400, "Unauthorized"),
    ERROR_NO_FOUND(404, "Resource not exists"),
    ERROR_SERVER_EXCEPTION(500, "Server exception"),

    ERROR_WRONG_PARAM_FORMAT(6001, "Request param format incorrect"),
    ERROR_USERNAME_EXISTS(6002, "Username already exists"),
    ERROR_USERNAME_FORMAT(6002, "Username format error"),
    ERROR_PASSWORD_FORMAT(6002, "Password format error"),
    ERROR_USERNAME_NOT_EXISTS(6003, "Username not exists"),
    ERROR_USERNAME_PASSWORD_NO_MATCH(6004, "Username and password not match"),
    ERROR_USERNAME_MAC_NO_MATCH(6004, "Username and mac not match"),
    ERROR_USER_NO_ACTIVATE(6004, "No activate"),
    ERROR_SSN_EXISTS(6005, "SSN is exists"),
    ERROR_EMAIL_EXISTS(6006, "Email is exists"),
    ERROR_EMAIL_NOT_EXISTS(6006, "Email not exists"),
    ERROR_EMAIL_NOT_MATCH(6006, "Email not match with register email"),
    ERROR_DEVICE_ALREADY_REGISTER(6007, "This device has been registered"),
    ERROR_DEVICE_NO_REGISTER(6008, "This device is not registered"),
    ERROR_DEVICE_NO_CHECK_IN(6008, "This device is not check in"),
    ERROR_DEVICE_LIMITED(6008, "This device is limited"),
    ERROR_DEVICE_USING(6009, "The device is using"),
    ERROR_KEY_INCORRECT(6010, "The key is incorrect"),
    ERROR_KEY_DEACTIVATE(6011, "The key is not activate"),
    ERROR_OUT_EXPIRATION(6012, "Out expiration date"),
    ERROR_TOKEN_NOT_EXISTS(6012, "Token not exists"),
    ERROR_MAC_FORMAT(6012, "Mac address format error"),


    ERROR_AUTHORIZE(9001, "Authorize communication exception"),
    ;

    private int code;
    private String message;

    EnumResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
