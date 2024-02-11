package io.github.gungjodi.testngspecificstarter;

import io.qameta.allure.AllureId;
import org.testng.ITestNGMethod;

/**
 * @author gungjodi
 * @version $Id: TestNGMethodParser.java, v1.0 2023‐08‐21 01.19 gungjodi Exp $$
 */
public class TestNGMethodParser {
    public static String getCaseNameFromTestNGMethod(ITestNGMethod iTestNGMethod) {
        AllureId allureIdAnnotation = iTestNGMethod
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(AllureId.class);

        if (allureIdAnnotation == null) {
            return iTestNGMethod.getMethodName();
        }
        return allureIdAnnotation.value();
    }
}

