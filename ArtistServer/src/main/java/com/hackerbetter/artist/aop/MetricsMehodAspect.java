package com.hackerbetter.artist.aop;

import static com.codahale.metrics.MetricRegistry.name;

import com.hackerbetter.artist.servlet.ArtistServlet;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
import com.hackerbetter.artist.servlet.MetricsAdminServletContextListener;

@Aspect
public class MetricsMehodAspect {
	private static final Logger logger=LoggerFactory.getLogger(MetricsMehodAspect.class);

    @Around("@annotation(timer)")
    public Object addTimer(ProceedingJoinPoint pjp,Timed timer) throws Throwable {
        Timer responses = MetricsAdminServletContextListener.REGISTRY.timer(name(ArtistServlet.class, timer.name()));
        final Timer.Context context = responses.time();
        try {
            return pjp.proceed();
        }catch (Throwable e) {
        	logger.error(e.getMessage(), e);
        }finally{
            context.stop();
        }
        return null;
    }
}
