package io.github.gungjodi.testngspecificstarter.listeners;

import io.github.gungjodi.testngspecificstarter.TestCaseHolder;
import org.apache.commons.lang3.StringUtils;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gungjodi
 * @version $Id: ISuiteListener.java, v1.0 2023‐07‐23 17.03 gungjodi Exp $$
 */
public class SuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite){
        if (StringUtils.isEmpty(System.getProperty("testRunId"))) {
            System.setProperty("testRunId", "RUN"+(new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date()));
        }

        if (!Boolean.parseBoolean(System.getProperty("skipDuplicateCasesCheck"))) {
            TestCaseHolder.checkDuplicateCaseNames();
        }
    }
}

