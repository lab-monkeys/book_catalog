package fun.is.quarkus.book_catalog.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import fun.is.quarkus.book_catalog.collaborators.stargate.api.AuthApi;
import fun.is.quarkus.book_catalog.collaborators.stargate.model.Credentials;
import io.quarkus.scheduler.Scheduled;

@Singleton
public class StargateAuthToken {
    
    @Inject
    AuthApi stargateAuth;

    @ConfigProperty(name = "{stargate.auth.user}")
    String stargateUser;

    @ConfigProperty(name = "{stargate.auth.pw}")
    String stargatePw;

    String authToken;
    Credentials stargateCreds = new Credentials(stargateUser, stargatePw);

    @Scheduled(every = "{stargate.token.renew}")
    public void getAuthToken() {
        stargateAuth.createToken(stargateCreds);
    }
}
