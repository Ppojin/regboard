package com.ppojin.regboard.advice;

import com.ppojin.regboard.advice.exception.CUserNotFoundException;
import com.ppojin.regboard.model.response.CommonResult;
import com.ppojin.regboard.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionAdvice {
    @Autowired private ResponseService responseService;
    @Autowired private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(
            HttpServletRequest request,
            Exception e){
        return responseService.getFailResult(
                Integer.parseInt(getMessage("unKnown.code")),
                getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(
            HttpServletRequest request,
            CUserNotFoundException e) {
        // 예외 처리의 메시지를 MessageSource에서 가져오도록 수정
        return responseService.getFailResult(
                Integer.parseInt(getMessage("userNotFound.code")),
                getMessage("userNotFound.msg"));
    }


    private String getMessage(String code){
        return getMessage(code, null);
    }

    private String getMessage(String code, Object[] args){
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
