package data;

public class User {

    public String username;
    public String eMail;
    public String godkendtPassword;

    public User () {

    }

    public User(String username, String eMail, String godkendtPassword) {
        this.username = username;
        this.eMail = eMail;
        this.godkendtPassword = godkendtPassword;
    }
}
