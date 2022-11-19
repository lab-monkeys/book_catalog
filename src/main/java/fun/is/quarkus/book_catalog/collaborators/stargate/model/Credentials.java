package fun.is.quarkus.book_catalog.collaborators.stargate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
  * User credentials for authenticating
 **/
public record Credentials(String username, String password)  {}