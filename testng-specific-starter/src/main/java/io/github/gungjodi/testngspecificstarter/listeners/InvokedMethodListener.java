package io.github.gungjodi.testngspecificstarter.listeners;

import io.github.gungjodi.testngspecificstarter.commons.AllureUtil;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * @author gungjodi
 * @version $Id: InvokedMethodListener.java, v1.0 2023‐08‐23 14.28 gungjodi Exp $$
 */
public class InvokedMethodListener implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isConfigurationMethod()) {
            method.getTestMethod().setDescription("configuration - " + testResult.getName());
        }
        if (method.isTestMethod()) {
            AllureUtil.updateCase(testResult);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            AllureUtil.updateCase(testResult);
        }
    }
}

