package base.examples;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class MockWithInterface {

    public interface InterfaceThing {
        int obtainValue();
    }

    public static class InnerClass {
        void call() {
            // Do nothing.
        }
    }

    public static class OuterClass {
        private InnerClass innerClass;

        public OuterClass(InnerClass innerClass) {
            this.innerClass = innerClass;
        }

        public void callDependentOnInterface() {
            if (innerClass instanceof InterfaceThing) {
                innerClass.call();
            }
        }

        public int obtainValueFromInterface() {
            if (!(innerClass instanceof InterfaceThing)) {
                return -1;
            }
            return ((InterfaceThing) innerClass).obtainValue();
        }
    }

    @Mock private InnerClass innerClassNoInterface;

    private InnerClass innerClassWithInterface =
            mock(InnerClass.class, withSettings().extraInterfaces(InterfaceThing.class));

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /** Checking method invocations that will be based on whether or not interface is implemented. */
    @Test
    public void callDependentOnInterface_doesNotImplementInterfaceThing() {
        OuterClass outerClass = new OuterClass(innerClassNoInterface);
        outerClass.callDependentOnInterface();
        verify(innerClassNoInterface, times(0)).call();
    }

    @Test
    public void callDependentOnInterface_implementsInterfaceThing() {
        OuterClass outerClass = new OuterClass(innerClassWithInterface);
        outerClass.callDependentOnInterface();
        verify(innerClassWithInterface, times(1)).call();
    }

    /** Shows stubbing an interface method on the mock using casting and {@code verify}'ing it. */
    @Test
    public void stubAnInterfaceMethod() {
        when(((InterfaceThing) innerClassWithInterface).obtainValue()).thenReturn(1);

        OuterClass outerClass = new OuterClass(innerClassWithInterface);
        assertEquals(1, outerClass.obtainValueFromInterface());

        verify(((InterfaceThing) innerClassWithInterface), times(1)).obtainValue();
    }
}
