import io.github.gungjodi.testngspecificstarter.SpecificTestProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitTest {
    @Test
    public void getSpecificTestList() {
        SpecificTestProvider.parseSpecificTests();
        String[] expected = { "package=io.github.gungjodi.testngspecificstarter",
                              "class=io.github.gungjodi.testngspecificstarter.ExampleTest",
                              "groups=groups1,groups2", "testSum",
                              "TEST_CASE_ID_001" };
        Assert.assertEqualsNoOrder(SpecificTestProvider.getSpecificTests().toArray(), expected);
    }
    @Test
    public void getExcludedTestList() {
        SpecificTestProvider.parseExcludedTests();
        String[] expected = { "testMethodExclude", "TEST_CASE_ID_002" };
        Assert.assertEqualsNoOrder(SpecificTestProvider.getExcludedTests().toArray(), expected);
    }
}