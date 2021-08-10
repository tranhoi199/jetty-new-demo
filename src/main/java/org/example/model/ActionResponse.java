package org.example.model;

public class ActionResponse {
    int returnCode;
    String action;
    String message;

    public ActionResponse(int returnCode, String action, String message) {
        this.returnCode = returnCode;
        this.action = action;
        this.message = message;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
