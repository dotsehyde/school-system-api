package io.dotsehyde.schoolsystem.Config.Error;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex){
        List<ErrorModel> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(
                (err)->{
                    String fieldName = ((FieldError) err).getField();
                    String errorMessage = err.getDefaultMessage();
                    errors.add(new ErrorModel(400,fieldName+" "+ errorMessage));
                }
        );

     return ResponseEntity.status(errors.get(0).statusCode).body(errors.get(0));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex){
        ErrorModel err = new ErrorModel(
               ex.statusCode,
                ex.getMessage()
        );
        return ResponseEntity.status(err.statusCode).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex){
        ErrorModel err = new ErrorModel(
                500,
                ex.getMessage()
        );
        return ResponseEntity.status(err.statusCode).body(err);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException ex){
        ErrorModel err = new ErrorModel(
                500,
                ex.getMessage()
        );
        return ResponseEntity.status(err.statusCode).body(err);
    }


}
