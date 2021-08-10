package org.example.model;

public class CreateObjectResponse {
    int returnCode;
    String message;

    public CreateObjectResponse(int returnCode, String message) {
        this.returnCode = returnCode;
        this.message = message;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
