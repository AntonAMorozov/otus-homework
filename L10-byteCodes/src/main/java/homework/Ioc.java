package homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    public static TestLogging createTestLogging() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[] {TestLogging.class}, handler);
    }

    public static class DemoInvocationHandler implements InvocationHandler {

        private final TestLogging testLogging;

        DemoInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var invocationHandler = Proxy.getInvocationHandler(proxy);
            if (invocationHandler instanceof DemoInvocationHandler demoInvocationHandler) {
                Method declaredMethod = demoInvocationHandler
                        .testLogging
                        .getClass()
                        .getDeclaredMethod(method.getName(), method.getParameterTypes());
                if (declaredMethod.isAnnotationPresent(Log.class)) {
                    logger.info("executed method:{}, param: {}", method.getName(), args);
                }
            }
            return method.invoke(testLogging, args);
        }
    }
}
