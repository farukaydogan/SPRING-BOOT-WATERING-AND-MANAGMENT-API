package com.humidty.arge.exceptions;


import com.humidty.arge.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ServiceExceptionHandlerAspect {

    private final EmailService emailService;

//TODO BAD CREDENTIAL OLARAK GERI DONMUYOR

    @AfterThrowing(pointcut = "execution(* com.humidty.arge.helper..*(..))", throwing = "ex")
    public void logAndReportException(Exception ex) throws Throwable {
        System.out.println("Hata yakalandı: " + ex.getMessage());
       emailService.sendEmail(ex.getMessage(),"Hata Yakalandi");
    }


//    @AfterThrowing(pointcut = "execution(* com.humidty.arge.controller..*(..))", throwing = "ex")
//    public void logAndReportException2(Exception ex) throws Throwable {
//        System.out.println("Hata yakalandı: " + ex.getMessage());
//        emailService.sendEmail(ex.getMessage(),"Hata Yakalandi");
//    }

    @AfterThrowing(pointcut = "execution(* com.humidty.arge.auth..*(..))", throwing = "ex")
    public void logAndReportException3(Exception ex) throws Throwable {
        System.out.println("Hata yakalandı: " + ex.getMessage());
        emailService.sendEmail(ex.getMessage(),"Hata Yakalandi");
    }

}