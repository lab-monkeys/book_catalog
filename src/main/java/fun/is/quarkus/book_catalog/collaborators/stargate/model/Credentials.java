package fun.is.quarkus.book_catalog.collaborators.stargate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
  * User credentials for authenticating
 **/
public class Credentials  {

    /**
      * User credentials for authenticating
     **/
    private String username;
    /**
      * User credentials for authenticating
     **/
    private String password;

    /**
    * Username
    * @return username
    **/
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     **/
    public void setUsername(String username) {
        this.username = username;
    }

    public Credentials username(String username) {
        this.username = username;
        return this;
    }

    /**
    * Password
    * @return password
    **/
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     **/
    public void setPassword(String password) {
        this.password = password;
    }

    public Credentials password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Create a string representation of this pojo.
     **/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Credentials {\n");

        sb.append("    username: ").append(toIndentedString(username)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}