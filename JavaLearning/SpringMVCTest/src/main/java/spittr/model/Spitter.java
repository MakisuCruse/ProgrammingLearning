package spittr.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by makisucruse on 16/9/29.
 */
public class Spitter {
    @NotNull
    private String email;
    @NotNull
    @Size(min = 5, max = 30)
    private String username;
    @NotNull
    @Size(min = 6, max = 20)
    private String password;

    public Spitter() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
