package base.examples;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.withSettings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.MockitoCore;
import org.mockito.listeners.InvocationListener;
import org.mockito.listeners.MethodInvocationReport;

public class InvocationListenerOnSpy
{

	static boolean exceptionOccurred = false;
	
	/*
	 * Going to be called each time a non-private method is 
	 * called on the class it's set on.
	 */
	static class TestInvoListener implements InvocationListener {

		@Override
		public void reportInvocation(MethodInvocationReport methodInvocationReport)
		{
			System.out.println("I'm being called");
			if (methodInvocationReport.getInvocation().toString().equals("injectClass.exceptionThrower();")
				&& methodInvocationReport.getThrowable() != null) {
				exceptionOccurred = true;	
			}
		}
	}
	
	InjectClass cl = new InjectClass();
	
	/*
	 * Constructing our @InjectMocks spy manually so we can set a listener
	 * on it. This code is basically what happens when we call Mockito.spy(Class<T>).
	 */
	@InjectMocks
	InjectClass injectClass = new MockitoCore().mock(InjectClass.class, withSettings()
                .spiedInstance(cl)
                .defaultAnswer(Answers.CALLS_REAL_METHODS)
                .invocationListeners(new TestInvoListener()));
	
	/**
	 * Here we want to see if exceptionThrower(), which is never called directly
	 * from the test and whose output is in try/catch within test()
	 * throws an exception during test execution.
	 */
	@Test
	public void test() {
		injectClass.test();
		assertTrue(exceptionOccurred);
	}
	
	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	static class InjectClass {
		
		public void test() {
			try {
				exceptionThrower();
			}
			catch (Exception e) {
				// do nothing
			}
		}
		
		protected void exceptionThrower() {
			throw new RuntimeException("injectClass.exceptionThrower");
		}
	}
}
