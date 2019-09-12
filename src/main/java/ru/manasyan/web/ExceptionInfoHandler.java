package ru.manasyan.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.manasyan.util.ValidationUtil;
import ru.manasyan.util.exception.ErrorInfo;
import ru.manasyan.util.exception.ErrorType;
import ru.manasyan.util.exception.IllegalRequestDataException;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static ru.manasyan.util.exception.ErrorType.*;
import static ru.manasyan.util.ValidationUtil.logAndGetErrorInfo;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {

    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

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

        log.error("{} - {}", VALIDATION_ERROR.getErrorCode(), details);
        return new ErrorInfo(req.getRequestURL(), VALIDATION_ERROR, VALIDATION_ERROR.getErrorCode(), details);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(log, req, e, false, VALIDATION_ERROR);
    }

   @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootMsg = ValidationUtil.getRootCause(e).getMessage();
        log.error("{} - {}", DATA_ERROR.getErrorCode(), rootMsg);
        return new ErrorInfo(req.getRequestURL(), DATA_ERROR, DATA_ERROR.getErrorCode(), new String[]{rootMsg});
    }


}
