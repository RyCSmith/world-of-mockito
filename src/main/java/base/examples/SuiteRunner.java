package base.examples;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {
	ArgumentCaptors.class,
	CustomArgumentMatchers.class,
	InvocationListenerOnMock.class,
	InvocationListenerOnSpy.class,
	MockThrowingException.class,
	SpysAndMocksSyntax.class
})
public class SuiteRunner
{

}
