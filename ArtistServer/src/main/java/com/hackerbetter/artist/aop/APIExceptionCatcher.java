package com.hackerbetter.artist.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hackerbetter.artist.annotation.CatchException;
import com.hackerbetter.artist.util.Response;

@Aspect
public class APIExceptionCatcher {
    private Logger logger = LoggerFactory.getLogger(APIExceptionCatcher.class);
    
    @Around("@annotation(catchException)")
    public Object exceptionHandler(ProceedingJoinPoint pjp,CatchException catchException) throws Throwable {
        try {
            return pjp.proceed();
        } catch(IllegalArgumentException e) {
            return Response.paramError(e.getMessage());
        } catch (Throwable e) {
            logger.error(catchException.errorMessage(), e);
            return Response.fail(catchException.errorMessage());
        }
    }
}
