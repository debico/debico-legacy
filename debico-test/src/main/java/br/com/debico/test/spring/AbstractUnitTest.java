package br.com.debico.test.spring;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("dev")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractUnitTest {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractUnitTest.class);

	public AbstractUnitTest() {

	}
	
	@BeforeClass
	public static void initialize() {
		System.setProperty("spring.profiles.active", "dev");
	}

	/**
	 * 
	 * @param proxy
	 * @param targetClass
	 * @return
	 * @throws Exception
	 * @see <a
	 *      href="http://www.techper.net/2009/06/05/how-to-acess-target-object-behind-a-spring-proxy/">How
	 *      To Acess Target Object Behind a Spring Proxy</a>
	 */
	@SuppressWarnings({ "unchecked" })
	protected final <T> T getTargetObject(Object proxy, Class<T> targetClass)
			throws Exception {
		if (AopUtils.isJdkDynamicProxy(proxy)) {
			return (T) ((Advised) proxy).getTargetSource().getTarget();
		} else {
			return (T) proxy; // expected to be cglib proxy then, which is
								// simply a specialized class
		}
	}

}
