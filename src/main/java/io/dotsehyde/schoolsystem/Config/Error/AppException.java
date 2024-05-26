package io.dotsehyde.schoolsystem.Config.Error;

public class AppException extends RuntimeException{
    public int statusCode;
    public String message;

    public AppException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
