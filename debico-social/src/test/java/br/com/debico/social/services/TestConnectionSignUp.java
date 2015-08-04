package br.com.debico.social.services;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.social.spring.SocialConfig;
import br.com.debico.test.spring.AbstractUnitTest;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class })
public class TestConnectionSignUp extends AbstractUnitTest {

    @Inject
    private ConnectionSignUp connectionSignUp;

    @Test
    public void testExecute() {
        UserProfile userProfile = new UserProfileBuilder()
                .setEmail("pparker@oscorp.com.br").setFirstName("Peter")
                .setLastName("Parker").setName("Peter Parker")
                .setUsername("pparker").build();

        Connection<?> connection = Mockito.mock(Connection.class);
        Mockito.when(connection.fetchUserProfile()).thenReturn(userProfile);
        Mockito.when(connection.getKey()).thenReturn(
                new ConnectionKey("facebook", "1010271"));

        String userId = connectionSignUp.execute(connection);

        assertThat(userId, notNullValue());
        assertThat(Integer.parseInt(userId), is(greaterThan(0)));
    }

    @Test
    public void testUsuarioSemEmail() {
        UserProfile userProfile = new UserProfileBuilder()
                .setEmail(null).setFirstName("Peter")
                .setLastName("Parker").setName("Peter Parker")
                .setUsername("pparker").build();

        Connection<?> connection = Mockito.mock(Connection.class);
        Mockito.when(connection.fetchUserProfile()).thenReturn(userProfile);
        Mockito.when(connection.getKey()).thenReturn(
                new ConnectionKey("facebook", "1010271"));

        String userId = connectionSignUp.execute(connection);

        assertThat(userId, notNullValue());
        assertThat(Integer.parseInt(userId), is(greaterThan(0)));
    }

}
