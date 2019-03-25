package com.example.springbootdemo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public String defaultErrorHandler(HttpServletRequest request, Model model, Exception e) throws Exception {
        model.addAttribute("error", e);
        model.addAttribute("url", request.getRequestURL());
        model.addAttribute("status",HttpStatus.BAD_REQUEST);
        return "error/403";
    }
//    protected ResponseEntity<Object> errorHandle(Exception e, WebRequest request) {
//        return handleExceptionInternal(e, e.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
}
