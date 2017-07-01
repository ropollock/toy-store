package models;

import java.util.List;

public class Session {
    private String token;
    private List<String> permissions;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Session{" +
                "token='" + token + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
