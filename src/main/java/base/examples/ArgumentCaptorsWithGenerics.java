package base.examples;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Just shows an example of using @Captor to create an ArgumentCaptor that is type-parameterized.
 * When using ArgumentCaptor.of(class) we would not be able to specify arg type.
 */
public class ArgumentCaptorsWithGenerics {

    static class ThingThatTakesAList<T> {

        public void acceptList(List<T> typedList) {
            // Do nothing.
        }
    }

    @Mock private ThingThatTakesAList<Integer> thingThatTakesAList;
    @Captor private ArgumentCaptor<List<Integer>> intListArgCaptor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCapturingTypedList() {
        List<Integer> intList = new ArrayList<>();
        thingThatTakesAList.acceptList(intList);
        verify(thingThatTakesAList).acceptList(intListArgCaptor.capture());
        assertEquals(intList, intListArgCaptor.getValue());
    }
}
