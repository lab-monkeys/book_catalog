package fun.is.quarkus.book_catalog.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import fun.is.quarkus.book_catalog.collaborators.stargate.api.AuthApi;

@ApplicationScoped
public class BookInfoService {
    
    @Inject
    AuthApi stargateAuth;

}
