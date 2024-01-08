package homework;

public class Demo {

    public static void main(String[] args) {

        var testLogging = (TestLogging) Ioc.createTestLogging(new TestLoggingImpl());
        testLogging.calculation(6); // логирование не вызывается
        testLogging.calculation(6, 7); // логирование вызывается

        var testLogging2 = (TestLogging) Ioc.createTestLogging( new TestLoggingImpl2());
        testLogging2.calculation(6); // логирование вызывается
        testLogging2.calculation(6, 7); // логирование не вызывается
    }
}
