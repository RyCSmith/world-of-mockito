package base.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;


public class ArgumentCaptors
{

	List<Integer> list = new ArrayList<>();
	List<Integer> spyList = spy(list);
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Using captor when only one invocation of a given method will
	 * take place during code execution. 
	 */
	@Test
	public void test() {
		// call actual methods while using the spy or mock
		spyList.add(new Integer(5));
		spyList.addAll(0, Lists.newArrayList(new Integer(1)));
		
		// create an argument capture to get the value of integer in add()
		ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
		Mockito.verify(spyList).add(argumentCaptor.capture());

		assertNotNull(argumentCaptor.getValue());
		assertTrue(argumentCaptor.getValue() == 5);
		
		
		// create an arugument captor to capture the
		// collection passed in a call to addAll() at index 0
		ArgumentCaptor<Collection<? extends Integer>> argumentCaptorTwo = ArgumentCaptor.forClass(Collection.class);
		verify(spyList).addAll(eq(0), argumentCaptorTwo.capture());

		assertNotNull(argumentCaptorTwo.getValue());
		assertTrue(argumentCaptorTwo.getValue().size() == 1);

	}
	
	/**
	 * Using captor when multiple invocations of a given method will
	 * take place during code execution. 
	 */
	@Test
	public void testTwo() {
		spyList.add(new Integer(1));
		spyList.add(new Integer(2));
		spyList.add(new Integer(3));
		spyList.add(new Integer(4));
		spyList.add(new Integer(5));
		
		ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(spyList, times(5)).add(argumentCaptor.capture());
		
		List<Integer> capturedValues = argumentCaptor.getAllValues();
		assertEquals(5, capturedValues.size());
		
		Collections.sort(capturedValues);
		for (int i = 0; i < 5; i++) {
			assertEquals(new Integer(i + 1), capturedValues.get(i));
		}
	}
}
