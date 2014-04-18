package AuthenticationApi;

import javax.persistence.*;

@Entity
@Table(name = "USER_LOGIN")
public class UserLoginData
{
    @Id
    @GeneratedValue
    private long id;
    private String email;
    private String password;

    public UserLoginData() {
    }

    public UserLoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
