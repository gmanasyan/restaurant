package ru.manasyan.web;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return "Error Exception";
    }

    private ModelAndView logAndGetExceptionView(HttpServletRequest req, Exception e, boolean logException, String msg) {
        //Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logException, errorType);
        ModelAndView mav = new ModelAndView("exception/exception");
        mav.addObject("typeMessage", e.getCause().toString());
        return mav;
    }





}
