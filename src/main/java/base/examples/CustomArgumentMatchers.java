package base.examples;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;

public class CustomArgumentMatchers
{

	// 2 is the perfect size listttttttt
	
	// match any non-null lists with > 2 elements
	static class IsLongList implements ArgumentMatcher<Collection<? extends Integer>> {
		@Override
		public boolean matches(Collection<? extends Integer> list)
		{
			return list != null && list.size() > 2;
		}
	}
	
	// match any non-null lists with 0 or 1 elements
	static class IsShortList implements ArgumentMatcher<Collection<? extends Integer>> {
		@Override
		public boolean matches(Collection<? extends Integer> list)
		{
			return list != null && list.size() < 2;
		}
	}
	
	List<Integer> shortList = Lists.newArrayList(1);
	List<Integer> longList = Lists.newArrayList(1, 2, 3);
	
	List<Integer> list = new ArrayList<>();
	List<Integer> spyList = spy(list);
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test() {
		for (int i = 0; i < 5; i++) {
			spyList.addAll(shortList);
			spyList.addAll(longList);
		}
		
		assertEquals(20, spyList.size());
		verify(spyList, times(5)).addAll(argThat(new IsLongList()));
		verify(spyList, times(5)).addAll(argThat(new IsShortList()));
	}
	
}
