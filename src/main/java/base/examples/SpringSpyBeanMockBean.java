package base.examples;

import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/xml-context.xml")
public class SpringSpyBeanMockBean
{
//	/*
//	 * Pull in a class that exists in our
//	 * applicationContext if we need access to it.
//	 */
//	@Autowired
//	private SomeClassA classA;
//
//	/*
//	 * Create a mock of SomeClassB and place it in
//	 * our application context.
//	 *
//	 * If an instance already exists in the application
//	 * context, it will be overwritten. If not, this will
//	 * just be added.
//	 *
//	 * The latter is useful if SomeClassA autowires
//	 * an instance of SomeClassB but xml-context.xml
//	 * is a partial context and doesn't actually
//	 * contain a SomeClassB bean.
//	 *
//	 * @Qualifier can be used to if more than one of
//	 * type in context.
//	 */
//	@MockBean
//	private SomeClassB classB;
//
//
//	/*
//	 * Pull out a bean that exists in the application
//	 * context and add spying to it.
//	 *
//	 * @Qualifier can be used to if more than one of
//	 * type in context.
//	 */
//	@SpyBean
//	private SomeClassC classC;
//
//
//
//	static class SomeClassA {}
//	static class SomeClassB {}
//	static class SomeClassC {}

}
