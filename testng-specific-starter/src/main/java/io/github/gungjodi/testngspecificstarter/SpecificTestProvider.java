package io.github.gungjodi.testngspecificstarter;

import io.github.gungjodi.testngspecificstarter.commons.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.ITestNGMethod;

import java.io.File;
import java.util.*;

/**
 * @author gungjodi
 * @version $Id: SpecificTestProvider.java, v1.0 2023‐08‐19 03.33 gungjodi Exp $$
 */
public class SpecificTestProvider {
    private static final Set<String> specificTests = new HashSet<>();
    private static final Set<String> excludedTests = new HashSet<>();

    /**
     * parse specific tests to be run from specificTests.list file
     * or from maven argLine -DspecificTests
     * prioritize the argline values
     */
    public static void parseSpecificTests() {
        if (StringUtils.isNotEmpty(System.getProperty("specificTests"))) {
            specificTests.addAll(Arrays.asList(StringUtils.stripAll(System.getProperty("specificTests").split(";"))));
            return;
        }

        try {
            File specificTestFile = FileUtils.getFileFromResource("specificTests.list");
            Scanner scanner = new Scanner(Objects.requireNonNull(specificTestFile));
            while (scanner.hasNextLine()) {
                String specificTest = scanner.nextLine();
                if (!StringUtils.startsWithAny(specificTest.trim(), "/", "#") && StringUtils.isNotEmpty(specificTest)) {
                    if (specificTest.endsWith(".Java") || specificTest.endsWith(".java")) {
                        specificTest = specificTest.replaceAll(".java", "");
                        specificTest = specificTest.replaceAll(".Java", "");
                        specificTest = specificTest.replaceAll("/", ".");
                    }
                    specificTests.add(specificTest.trim());
                }
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * parse excluded tests from excludedTests.list
     * or from maven argLine -DexcludedTests
     * prioritize the argline values
     */
    public static void parseExcludedTests() {
        if (StringUtils.isNotEmpty(System.getProperty("excludedTests"))) {
            excludedTests.addAll(Arrays.asList(StringUtils.stripAll(System.getProperty("excludedTests").split(";"))));
            return;
        }

        try {
            File excludedTestsFile = FileUtils.getFileFromResource("excludedTests.list");
            Scanner scanner = new Scanner(Objects.requireNonNull(excludedTestsFile));
            while (scanner.hasNextLine()) {
                String excludedTest = scanner.nextLine();
                if (!StringUtils.startsWithAny(excludedTest.trim(), "/", "#") && StringUtils.isNotEmpty(excludedTest)) {
                    excludedTests.add(excludedTest.trim());
                }
            }
            scanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<String> getSpecificTests() {
        if (specificTests.isEmpty()) {
            parseSpecificTests();
        }
        return specificTests;
    }

    public static Set<String> getExcludedTests() {
        if (excludedTests.isEmpty()) {
            parseExcludedTests();
        }
        return excludedTests;
    }

    public static boolean isIncluded(ITestNGMethod method) {
        boolean isIncluded = false;
        String packageName = method.getRealClass().getPackage().getName();
        String className = method.getRealClass().getName();
        List<String> groups = Arrays.asList(method.getGroups());

        if (groups.contains("skipped") || groups.contains("obsolete") || !method.getEnabled()) {
            return false;
        }

        String caseName = TestNGMethodParser.getCaseNameFromTestNGMethod(method);

        if (!specificTests.isEmpty()) {
            for(String specificTest : specificTests) {
                if (specificTest.startsWith("package=")) {
                    if (packageName.contains(specificTest.replace("package=", "").trim())) {
                        isIncluded = true;
                        break;
                    }
                }
                if (specificTest.startsWith("class=")) {
                    if (className.equals(specificTest.replace("class=", "").trim())) {
                        isIncluded = true;
                        break;
                    }
                }
                if (specificTest.startsWith("groups=")) {
                    String[] specificGroups = StringUtils.stripAll(specificTest.replace("groups=", "").split(","));
                    for (String specificGroup : specificGroups) {
                        if (groups.contains(specificGroup)) {
                            isIncluded = true;
                            break;
                        }
                    }
                }
                if (StringUtils.equalsAny(specificTest, caseName)) {
                    isIncluded = true;
                    break;
                }
            }
        }

        if (!excludedTests.isEmpty()) {
            for(String excludedTest : excludedTests) {
                if (excludedTest.startsWith("package=")) {
                    if (packageName.equals(excludedTest.replace("package=", "").trim())) {
                        isIncluded = false;
                        break;
                    }
                }
                if (excludedTest.startsWith("class=")) {
                    if (className.equals(excludedTest.replace("class=", "").trim())) {
                        isIncluded = false;
                        break;
                    }
                }
                if (excludedTest.startsWith("groups=")) {
                    String[] specificGroups = StringUtils.stripAll(excludedTest.replace("groups=", "").split(","));
                    for (String specificGroup : specificGroups) {
                        if (groups.contains(specificGroup)) {
                            isIncluded = false;
                            break;
                        }
                    }
                }
                if (StringUtils.equalsAny(excludedTest, caseName)) {
                    isIncluded = false;
                    break;
                }
            }
        }

        return isIncluded;
    }
}

