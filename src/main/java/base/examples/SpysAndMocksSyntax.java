package base.examples;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.withSettings;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.stubbing.answers.AnswerFunctionalInterfaces;

public class SpysAndMocksSyntax
{

	@Mock
	MockedClass mockedClassA;
	
	// setting defaults for when a method is not stubbed explicitly
	MockedClass mockedClassB = mock(MockedClass.class, withSettings()
			.defaultAnswer(AnswerFunctionalInterfaces.toAnswer(x -> x)) // setting default response to no argument methods
			.defaultAnswer(AnswerFunctionalInterfaces.toAnswer((x, y) -> x))); // setting default response to single arugment methods
			// also see the functional interfaces themselves for design on a anonymous class - that's what they're creating
	
	MockedClass mockedClassC = mock(MockedClass.class);
	
	
	@Spy
	OuterClass spyClassA = new OuterClass();
	
	@InjectMocks // this can be used on any of the spies created here - particular below not calling any methods using mock so not needed on spyClassA  
	OuterClass spyClassB = spy(OuterClass.class);
	
	OuterClass cl = new OuterClass();
	OuterClass spyClassC = spy(cl);
	
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		// stub a method in a mock
		when(mockedClassA.get10()).thenReturn(500);
		
		// stub a method in a spy
		doReturn(500).when(spyClassA).multiplyByTen(any());
	}
	
	@Test
	public void verifyOnSpy() {
		// call some method on the spy
		spyClassA.multiplyByTen(1);
		spyClassA.multiplyByTen(2);
		
		// verify those things we expected happened
		verify(spyClassA).multiplyByTen(1);
		verify(spyClassA).multiplyByTen(2);
		
		// verify the method was called the number of times we expected
		verify(spyClassA, times(2)).multiplyByTen(any());
	}
	
	
	
	static class OuterClass {
		private MockedClass mockClass;
		
		public void voidMethod() {
			mockClass.test();
		}
		
		public Integer multiplyByTen(Integer input) {
			return input * 10;
		}
	}
	
	static class MockedClass {
		public void test() {
			// do nothing
		}
		
		public int get10() {
			return 10;
		}
	}
}
