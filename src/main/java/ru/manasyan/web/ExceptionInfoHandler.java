package ru.manasyan.web;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.manasyan.util.exception.ErrorInfo;
import javax.servlet.http.HttpServletRequest;

import static ru.manasyan.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ErrorInfo bindValidationError(HttpServletRequest req, Exception e) {
        BindingResult result = e instanceof BindException ?
                ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(fe -> "Error on field: " + fe.getField() +
                        " = " + fe.getRejectedValue() +
                        ". Error details: " + fe.getDefaultMessage())
                .toArray(String[]::new);

        return new ErrorInfo(req.getRequestURL(), VALIDATION_ERROR, VALIDATION_ERROR.getErrorCode(), details);
    }

}
