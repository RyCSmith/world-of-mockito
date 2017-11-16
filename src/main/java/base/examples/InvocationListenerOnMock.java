package base.examples;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.listeners.InvocationListener;
import org.mockito.listeners.MethodInvocationReport;

public class InvocationListenerOnMock
{
	static class TestInvoListener implements InvocationListener {
		@Override
		public void reportInvocation(MethodInvocationReport methodInvocationReport)
		{
			System.out.println("I'm being called");
		}
	}
	
	MockedClass mockedClass = mock(MockedClass.class, withSettings().invocationListeners(new TestInvoListener()));
	
	@InjectMocks
	InjectClass injectClass;

	@Test
	public void test() {
		injectClass.test();
		Mockito.verify(mockedClass, Mockito.times(1)).test();
	}
	
	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}
	
	static class InjectClass {
		private MockedClass mockClass;
		
		public void test() {
			mockClass.test();
		}
	}
	static class MockedClass {
		public void test() {
			// do nothing
		}
	}
}
