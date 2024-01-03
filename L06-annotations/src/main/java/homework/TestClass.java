package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

@SuppressWarnings("unused")
public class TestClass {

    @Before
    public static void before() {
        System.out.println("---------");
        System.out.println("Before");
    }

    @Test
    public void m1() { // Test should pass
        System.out.println("test1");
    }

    public void m2() {}

    @Test
    public void m3() { // Test should fail
        System.out.println("test3");
        throw new RuntimeException();
    }

    public void m4() {}

    public void m6() {}

    @Test
    public void m7() { // Test should pass
        System.out.println("test7");
    }

    public void m8() {}

    @Test
    private void m9() {
        System.out.println("test9");
    }

    @After
    public void after() {
        System.out.println("After");
    }
}
