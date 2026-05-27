package com.taskflow.project_service.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor
        implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        long startTime =
                System.currentTimeMillis();

        request.setAttribute(
                "startTime",
                startTime
        );

        System.out.println(
                "[PROJECT INTERCEPTOR] Controller request started: "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI()
        );

        return true;
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable Exception ex) {

        Long startTime =
                (Long) request.getAttribute(
                        "startTime"
                );

        if (startTime != null) {

            long duration =
                    System.currentTimeMillis()
                            - startTime;

            System.out.println(
                    "[PROJECT INTERCEPTOR] Controller request completed: "
                            + request.getMethod()
                            + " "
                            + request.getRequestURI()
                            + " status="
                            + response.getStatus()
                            + " duration="
                            + duration
                            + "ms"
            );
        }
    }
}