package entity;

/**
 * Created by Administrator on 2016/7/20.
 */
public class User {

    private int userId;//数据库中为user_id，驼峰转换
    private String username;
    private String password;
    private String email;

    public User(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "User{userId=" + userId + ", username=" + username + '}';
    }
}
