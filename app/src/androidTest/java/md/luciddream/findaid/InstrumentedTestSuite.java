package md.luciddream.findaid;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({LocationReadWriteTest.class,
        ExampleInstrumentedTest.class})
public class InstrumentedTestSuite {
}
