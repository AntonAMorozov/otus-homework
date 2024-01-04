package homework;

public class Demo {

    public static void main(String[] args) {

        TestLogging testLogging = Ioc.createTestLogging();
        testLogging.calculation(6); // логирование не вызывается
        testLogging.calculation(6, 7); // логирование вызывается
    }
}
