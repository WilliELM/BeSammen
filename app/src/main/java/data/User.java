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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getGodkendtPassword() {
        return godkendtPassword;
    }

    public void setGodkendtPassword(String godkendtPassword) {
        this.godkendtPassword = godkendtPassword;
    }
}
