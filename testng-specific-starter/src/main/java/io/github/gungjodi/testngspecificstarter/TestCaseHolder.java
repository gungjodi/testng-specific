package io.github.gungjodi.testngspecificstarter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gungjodi
 * @version $Id: TestCaseHolder.java, v1.0 2023‐07‐23 17.47 gungjodi Exp $$
 */
public class TestCaseHolder {
    public static Set<String>    methodDependencies = new HashSet<>();
    static        List<String> allTestsName          = new ArrayList<>();

    public static void addCaseNameToAll(String caseName) {
        allTestsName.add(caseName);
    }

    public static void checkDuplicateCaseNames() {
        Set<String> duplicates = allTestsName.stream()
                .filter(i -> Collections.frequency(allTestsName, i) > 1)
                .collect(Collectors.toSet());
        if (!duplicates.isEmpty()) {
            throw new RuntimeException("Duplicated AllureId/methodName found: "+duplicates);
        }
    }
}

