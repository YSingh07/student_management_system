package model;

public class User {

    private long userId;
    private String username;
    private String password;
    private String role;
    private String name;

    public User(long userId, String username,
                String password, String role,
                String name) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
}