package homework;

@SuppressWarnings("java:S106")
public class TestLoggingImpl implements TestLogging {

    public void calculation(int param) {
        System.out.println("test1");
    }

    @Log
    public void calculation(int param, int param2) {
        System.out.println("test2");
    }
}
