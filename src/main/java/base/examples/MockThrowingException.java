package base.examples;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class MockThrowingException
{
	MockedClass mockedClass = mock(MockedClass.class);
	
	@InjectMocks
	OuterClass outerClass = new OuterClass();
	
	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		doThrow(new GFYException()).when(mockedClass).test();
	}
	
	@Test(expected = GFYException.class)
	public void test() {
		outerClass.methodThatCantHandleExceptionFromMock();
	}
	
	@Test
	public void testTwo() {
		outerClass.methodThatCanHandleExceptionFromMock();
		Mockito.verify(mockedClass, Mockito.times(1)).test();
	}
	
	static class OuterClass {
		private MockedClass mockClass;
		
		public void methodThatCantHandleExceptionFromMock() {
			mockClass.test();
		}
		
		public void methodThatCanHandleExceptionFromMock() {
			try {
				mockClass.test();
			} catch (Exception e) {}
		}
	}
	
	static class MockedClass {
		public void test() {
			// this method doesn't throw an exception
			// the mock is going to make it do so
		}
	}
	
	static class GFYException extends RuntimeException {
		public GFYException() {
			super("gfy");
		}
	}
}
