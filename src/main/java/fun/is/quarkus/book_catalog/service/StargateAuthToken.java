package fun.is.quarkus.book_catalog.service;

import java.time.Duration;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import fun.is.quarkus.book_catalog.collaborators.stargate.api.AuthApi;
import fun.is.quarkus.book_catalog.collaborators.stargate.model.Credentials;
import fun.is.quarkus.book_catalog.collaborators.stargate.model.Token;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;

@Singleton
public class StargateAuthToken {
    
    final Logger LOG = Logger.getLogger(StargateAuthToken.class);

    @RestClient
    AuthApi stargateAuth;

    @ConfigProperty(name = "stargate.auth.user")
    private String stargateUser;

    @ConfigProperty(name = "stargate.auth.pw")
    private String stargatePw;

    private String authToken;
    Credentials stargateCreds = null;

    void startUp(@Observes StartupEvent startupEvent) {
        stargateCreds = new Credentials(stargateUser, stargatePw);
    }
    
    @Scheduled(every = "{stargate.token_renew}")
    public void authenticate() {
        stargateAuth.createToken(stargateCreds).ifNoItem().after(Duration.ofMillis(1000)).failWith(new Exception("Request Timeout - Authentication")).subscribe().with(reply -> setToken(reply), fail -> handleFailure(fail));
    }

    private void setToken(Response reply) {
        this.authToken = reply.readEntity(Token.class).authToken();
        LOG.info("Token: " + this.authToken);
    }

    private void handleFailure(Throwable error) {
        error.printStackTrace();
    }

    public String getAuthToken() {
        return this.authToken;
    }
}
