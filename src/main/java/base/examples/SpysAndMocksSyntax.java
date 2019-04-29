package base.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.stubbing.answers.AnswerFunctionalInterfaces;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class SpysAndMocksSyntax
{
	/*
	 * Showing different, equivalent ways of creating Mocks and Spys.
	 * (with the exception of mockedClassB which is showing how to use withSettings(). 
	 */
	
	
	@Mock
	MockedClass mockedClassA;
	
	// setting defaults for when a method is not stubbed explicitly
	MockedClass mockedClassB = mock(MockedClass.class, withSettings()
			.defaultAnswer(AnswerFunctionalInterfaces.toAnswer(x -> x)) // setting default response to no argument methods
			.defaultAnswer(AnswerFunctionalInterfaces.toAnswer((x, y) -> x))); // setting default response to single argument methods
			// also see the functional interfaces themselves for design on a anonymous class - that's what they're creating

			// *also see MockWithInterface to see how to use withSettings to add an interface implementation to a mock.
	
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
		
		// stubbing a method of the mock to return a new list with a single 
		// integer of value 5 each time
		//
		// this differs from thenReturn in that thenAnswer will execute this
		// code each time whereas thenReturn would return a reference to the
		// same single mock object
		when(mockedClassA.getSomeList()).thenAnswer(new Answer<List<Integer>>() {
			public List<Integer> answer(InvocationOnMock invocation) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(5);
				return list;
			}
		});
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
	
	@Test
	public void testThenAnswerOnMock() {
		List<Integer> responseOne = mockedClassA.getSomeList();
		List<Integer> responseTwo = mockedClassA.getSomeList();
		
		assertEquals(1, responseOne.size());
		assertEquals(new Integer(5), responseOne.get(0));
		
		assertEquals(1, responseTwo.size());
		assertEquals(new Integer(5), responseTwo.get(0));
		
		assertFalse(responseOne == responseTwo);
	}
	
	/**
	 * Use of any() to verify argument here, differs from any()
	 * above in that it takes a class. For our case this doesn't matter
	 * because we only have one method named multiplyByTen(). 
	 * 
	 * If we were to uncomment to overloaded OuterClass.multipleByTen(Double)
	 * this syntax would be required in order to use any().
	 */
	@Test 
	public void testRestrictAnyMatchByClass() {
		spyClassA.multiplyByTen(1);
		verify(spyClassA, times(1)).multiplyByTen(any(Integer.class));
	}
 	
	static class OuterClass {
		private MockedClass mockClass;
		
		public void voidMethod() {
			mockClass.test();
		}
		
		public Integer multiplyByTen(Integer input) {
			return input * 10;
		}
		
//		public Double multiplyByTen(Double input) {
//			return input * 10;
//		}
	}
	
	static class MockedClass {
		public void test() {
			// do nothing
		}
		
		public int get10() {
			return 10;
		}
		
		public List<Integer> getSomeList() {
			return new ArrayList<Integer>();
		}
	}
}
