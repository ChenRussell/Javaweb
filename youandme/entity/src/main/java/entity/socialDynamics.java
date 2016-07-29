package entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/7/23.
 */
public class SocialDynamics {

    private int dynamicsId;
    private int userId;
    private String dynamicsText;
    private String dynamicsFile;
    private Timestamp createTime;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public SocialDynamics() {
    }

    public int getDynamicsId() {
        return dynamicsId;
    }

    public void setDynamicsId(int dynamicsId) {
        this.dynamicsId = dynamicsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDynamicsText() {
        return dynamicsText;
    }

    public void setDynamicsText(String dynamicsText) {
        this.dynamicsText = dynamicsText;
    }

    public String getDynamicsFile() {
        return dynamicsFile;
    }

    public void setDynamicsFile(String dynamicsFile) {
        this.dynamicsFile = dynamicsFile;
    }

    @Override
    public String toString() {
        return "socialDynamics{" +
                "dynamicsId=" + dynamicsId +
                ", userId=" + userId +
                ", dynamicsText='" + dynamicsText + '\'' +
                ", dynamicsFile='" + dynamicsFile + '\'' +
                ", createTime=" + createTime +
                ", user=" + user +
                '}';
    }
}
