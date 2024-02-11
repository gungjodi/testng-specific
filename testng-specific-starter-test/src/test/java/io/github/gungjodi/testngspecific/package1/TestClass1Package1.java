package io.github.gungjodi.testngspecific.package1;

import io.qameta.allure.AllureId;
import org.testng.annotations.Test;

public class TestClass1Package1 {
    @Test(description = "Class1Package1.test1", groups = {"class1", "method1"})
    public void p1c1test1() {
        System.out.println("Class1Package1.test1");
    }

    @Test(description = "Class1Package1.test2")
    public void p1c1test2() {
        System.out.println("Class1Package1.test2");
    }

    @Test(description = "Test using AllureID", groups = {"class1", "method3"})
    @AllureId("TEST_CASE_ALLURE_001")
    public void allureIdTest() {
        System.out.println("TEST_CASE_ALLURE_001");
    }

    @Test(description = "Test using AllureID", groups = {"class1", "method3"})
    @AllureId("TEST_CASE_ALLURE_002")
    public void allureIdTest2() {
        System.out.println("TEST_CASE_ALLURE_002");
    }
}
