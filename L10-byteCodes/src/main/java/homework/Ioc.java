package homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

        DemoInvocationHandler(Object clazz) {
            this.clazz = clazz;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var invocationHandler = (DemoInvocationHandler) Proxy.getInvocationHandler(proxy);
            Method declaredMethod =
                    invocationHandler.clazz.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (declaredMethod.isAnnotationPresent(Log.class)) {
                logger.info("executed method:{}, param: {}", method.getName(), args);
            }

            return method.invoke(clazz, args);
        }
    }
}
