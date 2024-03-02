package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"java:S106"})
public class TestRunner {

    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    public static void main(String[] args) {

        Class<?> testClass = TestClass.class;
        runTests(testClass);
    }

    private static void runTests(Class<?> clazz) {

        int allTests = 0;
        int passed = 0;
        int failed = 0;
        int notRun = 0;

        var declaredMethods = clazz.getDeclaredMethods();
        var beforeMethodList = getAnnotatedMethodList(declaredMethods, Before.class);
        var afterMethodList = getAnnotatedMethodList(declaredMethods, After.class);
        var testMethodList = getAnnotatedMethodList(declaredMethods, Test.class);

        for (Method method : testMethodList) {
            allTests++;
            try {
                Object o = Class.forName(clazz.getName()).getConstructor().newInstance();
                invokeMethods(beforeMethodList, o);
                method.invoke(o);
                invokeMethods(afterMethodList, o);
                passed++;
            } catch (IllegalAccessException e) {
                System.out.println("Invalid @Test: " + method);
                notRun++;
            } catch (InvocationTargetException e) {
                System.out.println("Failed @Test: " + method);
                failed++;
            } catch (NoSuchMethodException | InstantiationException | ClassNotFoundException e) {
                log.error("Error running test: {}", method.getName());
            }
        }

        log.info("***Statistics***");
        log.info("Tests: {}", allTests);
        log.info("Passes {}: ", passed);
        log.info("Failed: {}", failed);
        log.info("Not run: {}", notRun);
    }

    private static List<Method> getAnnotatedMethodList(
            Method[] declaredMethods, Class<? extends Annotation> annotationClass) {
        List<Method> methodList = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(annotationClass)) {
                methodList.add(declaredMethod);
            }
        }
        return methodList;
    }

    private static void invokeMethods(List<Method> methodList, Object o)
            throws InvocationTargetException, IllegalAccessException {
        if (!methodList.isEmpty()) {
            for (Method method : methodList) {
                method.invoke(o);
            }
        }
    }
}
