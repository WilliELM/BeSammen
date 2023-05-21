package data;

import androidx.annotation.NonNull;

public class UserToFirebase {
    private String name;
    private String diagnose;

    public UserToFirebase(String name, String diagnose) {
        this.name = name;
        this.diagnose = diagnose;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }
}
