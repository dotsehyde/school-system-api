package io.dotsehyde.schoolsystem.Config.Error;

public class ErrorModel {
    public int statusCode;
    public String message;

    public ErrorModel(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
