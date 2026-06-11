import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(CalculatorTest.class)
@IncludeTags("positive")
public class PositiveTestsSuite {
}
