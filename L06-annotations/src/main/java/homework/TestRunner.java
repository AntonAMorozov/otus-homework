package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunner {

    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    public static void main(String[] args) {

        Class<?> testClass = TestClass.class;
        runTests(testClass);
    }

    private static void runTests(Class<?> clazz) {

        var declaredMethods = clazz.getDeclaredMethods();

        int allTests = 0;
        int passed = 0;
        int failed = 0;
        int notRun = 0;

        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Test.class)) {
                allTests++;
                try {
                    var o = Class.forName("homework.TestClass").getConstructor().newInstance();
                    before(declaredMethods);
                    declaredMethod.invoke(o);
                    after(declaredMethods);
                    passed++;
                } catch (IllegalAccessException e) {
                    System.out.println("Invalid @Test: " + declaredMethod);
                    notRun++;
                } catch (InvocationTargetException e) {
                    System.out.println("Failed @Test: " + declaredMethod);
                    failed++;
                } catch (NoSuchMethodException | InstantiationException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        log.info("***Statistics***");
        log.info("Tests: {}", allTests);
        log.info("Passes {}: ", passed);
        log.info("Failed: {}", failed);
        log.info("Not run: {}", notRun);
    }

    private static void before(Method[] declaredMethods)
            throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
                    InstantiationException {
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Before.class)) {
                Object o = Class.forName("homework.TestClass").getConstructor().newInstance();
                declaredMethod.invoke(o);
            }
        }
    }

    private static void after(Method[] declaredMethods)
            throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException,
                    InstantiationException {
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(After.class)) {
                Object o = Class.forName("homework.TestClass").getConstructor().newInstance();
                declaredMethod.invoke(o);
            }
        }
    }
}
