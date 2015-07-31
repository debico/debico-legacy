package br.com.debico.social.services;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.social.spring.SocialConfig;
import br.com.debico.test.spring.AbstractUnitTest;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class })
public class TestConnectionSignUp extends AbstractUnitTest {

    @Inject
    private ConnectionSignUp connectionSignUp;

    @Test
    public void testExecute() {
	UserProfile userProfile = new UserProfile("616", "Peter Parker",
		"Peter", "Parker", "pparker@oscorp.com.br", "pparker");
	Connection<?> connection = Mockito.mock(Connection.class);
	Mockito.when(connection.fetchUserProfile()).thenReturn(userProfile);

	String userId = connectionSignUp.execute(connection);

	assertThat(userId, notNullValue());
	assertThat(Integer.parseInt(userId), is(greaterThan(0)));
    }

}
