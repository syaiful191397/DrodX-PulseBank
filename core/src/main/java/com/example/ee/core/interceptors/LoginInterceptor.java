package com.example.ee.core.interceptors;



import com.example.ee.core.annotation.Login;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.xml.ws.BindingType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.logging.Logger;


@Interceptor
@Login
//@Priority(1)
public class LoginInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ic )throws Exception{

        System.out.println("LoginInterceptor - intercept start");

        Object proceed =  ic.proceed();
        System.out.println("LoginInterceptor  - intercept end");

        return  proceed;
    }
}
