package com.swords.controller.response;

public class LoginResponse {

    private boolean success = false;
    private String error = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        if (this.error.isEmpty()) {
            this.error = error;
        }
    }
}