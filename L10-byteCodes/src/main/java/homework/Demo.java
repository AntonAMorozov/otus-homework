package homework;

public class Demo {

    public static void main(String[] args) {

        TestLoggingImpl testLoggingImpl = new TestLoggingImpl();
        TestLogging testLogging = (TestLogging) Ioc.createTestLogging(testLoggingImpl);
        testLogging.calculation(6); // логирование не вызывается
        testLogging.calculation(6, 7); // логирование вызывается

        TestLoggingImpl2 testLoggingImpl2 = new TestLoggingImpl2();
        TestLogging testLogging2 = (TestLogging) Ioc.createTestLogging(testLoggingImpl2);
        testLogging2.calculation(6); // логирование вызывается
        testLogging2.calculation(6, 7); // логирование не вызывается
    }
}
