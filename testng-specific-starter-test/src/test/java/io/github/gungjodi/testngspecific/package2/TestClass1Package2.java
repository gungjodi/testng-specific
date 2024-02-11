package io.github.gungjodi.testngspecific.package2;

import org.testng.annotations.Test;

public class TestClass1Package2 {
    @Test(description = "TestClass1Package2.test1", groups = {"class1", "method1"})
    public void c1p2test1() {
        System.out.println("TestClass1Package2.test1");
    }

    @Test(description = "TestClass1Package2.test2", groups = {"class1", "method2"})
    public void c1p2test2() {
        System.out.println("TestClass1Package2.test2");
    }
}
