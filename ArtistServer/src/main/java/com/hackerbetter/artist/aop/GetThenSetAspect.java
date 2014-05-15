package com.hackerbetter.artist.aop;

import com.hackerbetter.artist.annotation.Memcachable;
import com.hackerbetter.artist.cache.CacheService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Configurable
public class GetThenSetAspect {
	private static final Logger logger= LoggerFactory.getLogger(GetThenSetAspect.class);

    @Autowired
    private CacheService cacheService;

    private static Map<String, String[]> parameterNameCaches = new ConcurrentHashMap<String, String[]>();

    private static ExpressionParser parser = new SpelExpressionParser();

    private static LocalVariableTableParameterNameDiscoverer parameterNameDiscovere = new LocalVariableTableParameterNameDiscoverer();

    @Around("@annotation(getThenSet)")
    public Object setCacheWhenGetIsNull(ProceedingJoinPoint pjp,
            Memcachable getThenSet) throws Throwable {
        try {
            String key = getKey(getThenSet.key(), pjp);
            Object value = cacheService.get(key);
            if (value != null) {
                return value;
            }
            value = pjp.proceed();
            if (!StringUtils.isEmpty(getThenSet.condition())
                    && isConditionPassing(getThenSet.condition(), value)) {
                cacheService.set(key, value);
            }
            return value;
        }
        catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }
        return "";
    }

    private String getKey(String template, ProceedingJoinPoint joinPoint) {
        // get method parameter name
        String methodLongName = joinPoint.getSignature().toLongString();
        String[] parameterNames = parameterNameCaches.get(methodLongName);
        if (parameterNames == null) {
            Method method = getMethod(joinPoint);
            parameterNames = parameterNameDiscovere.getParameterNames(method);
            parameterNameCaches.put(methodLongName, parameterNames);
        }

        // add args to expression context
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        if (args.length == parameterNames.length) {
            for (int i = 0, len = args.length; i < len; i++)
                context.setVariable(parameterNames[i], args[i]);
        }

        Expression expression = parser.parseExpression(template);
        String value = expression.getValue(context, String.class);
        return value;
    }

    private boolean isConditionPassing(String condtion, Object returnValue) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("returnValue", returnValue);

        Expression expression = parser.parseExpression(condtion);
        return expression.getValue(context, boolean.class);
    }

    /**
     * 获取当前执行的方法
     * @param joinPoint
     * @return
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        String methodLongName = joinPoint.getSignature().toLongString();
        Method[] methods = joinPoint.getStaticPart().getSignature().getDeclaringType().getMethods();
        Method method = null;
        for (int i = 0, len = methods.length; i < len; i++) {
            if (methodLongName.equals(methods[i].toString())) {
                method = methods[i];
                break;
            }
        }
        return method;
    }

}
