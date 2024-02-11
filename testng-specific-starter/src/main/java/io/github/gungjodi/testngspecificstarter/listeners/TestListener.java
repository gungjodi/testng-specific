package io.github.gungjodi.testngspecificstarter.listeners;

import io.github.gungjodi.testngspecificstarter.TestNGMethodParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);
    // When Test case get failed, this method is called.
    @Override
    public void onTestFailure(ITestResult iTestResult)
    {
        String caseName = TestNGMethodParser.getCaseNameFromTestNGMethod(iTestResult.getMethod());
        LOGGER.info("testcase {} status : Failed! {}", caseName, iTestResult.getThrowable().toString());
        LOGGER.info("reason : " + iTestResult.getThrowable());
        MDC.clear();
    }

    // When Test case get Skipped, this method is called.
    @Override
    public void onTestSkipped(ITestResult iTestResult)
    {
        String caseName = TestNGMethodParser.getCaseNameFromTestNGMethod(iTestResult.getMethod());
        LOGGER.info("testcase {} status : Skipped!", caseName);
        MDC.clear();
    }

    // When Test case get Started, this method is called.
    @Override
    public void onTestStart(ITestResult Result)
    {
        MDC.put("caseName", TestNGMethodParser.getCaseNameFromTestNGMethod(Result.getMethod()));
        LOGGER.info("==== Test Case Started ======");
        LOGGER.info("class : {}", Result.getTestClass().getName());
        LOGGER.info("name : {}", Result.getName());
        Object[] params = Result.getParameters();
        int lengthParams = params.length;
        if (lengthParams > 0) {
            LOGGER.info("parameter : ");
            for (Object param : params) {
                LOGGER.info("\t -" + param);
            }
        }

    }

    // When Test case get passed, this method is called.
    @Override
    public void onTestSuccess(ITestResult iTestResult)
    {
        String caseName = TestNGMethodParser.getCaseNameFromTestNGMethod(iTestResult.getMethod());
        LOGGER.info("testcase {} status : Passed!", caseName);
        MDC.clear();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub

    }
}
