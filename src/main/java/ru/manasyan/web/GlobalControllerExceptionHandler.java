package ru.manasyan.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.manasyan.util.exception.ErrorInfo;

import javax.servlet.http.HttpServletRequest;

import static ru.manasyan.util.ValidationUtil.logAndGetErrorInfo;
import static ru.manasyan.util.exception.ErrorType.*;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(Exception.class)
    public ErrorInfo defaultHandleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(log, req, e, true, APP_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorInfo wrongRequest(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(log, req, e, true, WRONG_REQUEST);
    }




}
