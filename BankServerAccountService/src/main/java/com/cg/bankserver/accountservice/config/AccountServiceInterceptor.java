package com.cg.bankserver.accountservice.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cg.bankserver.accountservice.dto.DepositDTO;
import com.cg.bankserver.accountservice.exceptions.AccountDetailsNotFoundException;
import com.cg.bankserver.accountservice.query.services.AccountQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AccountServiceInterceptor implements HandlerInterceptor {

    @Autowired private AccountQueryService queryService;
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws AccountDetailsNotFoundException {
        try {
            int customerId = Integer.parseInt(request.getHeader("userid"));
            byte[] inputStreamBytes = StreamUtils.copyToByteArray(request.getInputStream());
            DepositDTO jsonRequest = new ObjectMapper().readValue(inputStreamBytes, DepositDTO.class);
            queryService.getAccountByAccountNumber(jsonRequest.getAccountNumber());
            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
