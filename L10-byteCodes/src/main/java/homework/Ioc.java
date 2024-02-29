package homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    public static Object createTestLogging(Object clazz) {
        InvocationHandler handler = new DemoInvocationHandler(clazz);
        return Proxy.newProxyInstance(
                Ioc.class.getClassLoader(), clazz.getClass().getInterfaces(), handler);
    }

    public static class DemoInvocationHandler implements InvocationHandler {

        private final Object clazz;

        private final Map<String, Object> cache = new HashMap<>();
        private final Map<String, Object> logCache = new HashMap<>();

        DemoInvocationHandler(Object clazz) {
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            String methodName = method.getName();
            String methodSignature = getMethodSignature(method);
            Method cachedMethod;

            String cacheKey = methodName + ":" + methodSignature;
            if (logCache.containsKey(cacheKey)) {
                logger.info("executed method:{}, param: {}", method.getName(), args);
                cachedMethod = (Method) logCache.get(cacheKey);
                return cachedMethod.invoke(clazz, args);
            } else if (cache.containsKey(cacheKey)) {
                cachedMethod = (Method) cache.get(cacheKey);
                return cachedMethod.invoke(clazz, args);
            } else {
                var invocationHandler = (DemoInvocationHandler) Proxy.getInvocationHandler(proxy);
                Method declaredMethod = invocationHandler
                        .clazz
                        .getClass()
                        .getDeclaredMethod(method.getName(), method.getParameterTypes());
                if (declaredMethod.isAnnotationPresent(Log.class)) {
                    logger.info("executed method:{}, param: {}", method.getName(), args);
                    declaredMethod.invoke(clazz, args);
                    return logCache.put(cacheKey, declaredMethod);

                } else {
                    declaredMethod.invoke(clazz, args);
                    return cache.put(cacheKey, declaredMethod);
                }
            }
        }

        private String getMethodSignature(Method method) {
            StringJoiner sj = new StringJoiner(",");
            for (Class<?> paramType : method.getParameterTypes()) {
                sj.add(paramType.getName());
            }
            return sj.toString();
        }
    }
}
