package communityhousemodel;

public class UserAccountModel {
    private int userId;
    private int persionId;
    private String username;
    private String phoneNumber;
    private String password;
    private int type;

    public UserAccountModel(){}

    public UserAccountModel(int persionId, String username, String phoneNumber, String password, int type) {
        this.persionId = persionId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.type = type;
    }

    public UserAccountModel(int userId, int persionId, String username, String phoneNumber, String password, int type) {
        this.userId = userId;
        this.persionId = persionId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPersionId() {
        return persionId;
    }

    public void setPersionId(int persionId) {
        this.persionId = persionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
