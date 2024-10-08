package interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@Log
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethodEntry(InvocationContext context) throws Exception {
        Class<?> targetClass = getTargetClass(context.getTarget());
        Logger logger = LoggerFactory.getLogger(targetClass);

        logger.info("Entering method: {} of class: {}", context.getMethod().getName(), targetClass.getName());
        try {
            return context.proceed();
        } finally {
            logger.info("Exiting method: {} of class: {}", context.getMethod().getName(), targetClass.getName());
        }
    }

    private Class<?> getTargetClass(Object proxy) {
        Class<?> clazz = proxy.getClass();
        if (clazz.getName().contains("$$")) {
            return clazz.getSuperclass();
        }
        return clazz;
    }
}