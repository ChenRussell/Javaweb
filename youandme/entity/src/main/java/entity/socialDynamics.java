package entity;

/**
 * Created by Administrator on 2016/7/23.
 */
public class socialDynamics {

    private int dynamicsId;
    private int userId;
    private String dynamicsText;
    private String dynamicsFile;

    public socialDynamics() {
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
                '}';
    }
}
