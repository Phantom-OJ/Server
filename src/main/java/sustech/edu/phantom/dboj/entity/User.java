package sustech.edu.phantom.dboj.entity;

import lombok.Data;

@Data
public class User {
    private Integer uid;
    private Integer gid;
    private String username;
    private String password;
    private String nickname;
    private char role;
    private String avatar;
    private boolean stateSave;
    private String state;
    private String lang;
    private boolean valid;

    public User() {

    }

    public User(RegisterForm registerForm) {
        this.username = registerForm.getUsername();
        this.password = registerForm.getPassword();
        this.nickname = registerForm.getNickname();
    }

    public User(LoginForm loginForm) {
        this.username = loginForm.getUsername();
        this.password = loginForm.getPassword();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public char getRole() {
        return role;
    }

    public void setRole(char role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isStateSave() {
        return stateSave;
    }

    public void setStateSave(boolean stateSave) {
        this.stateSave = stateSave;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", gid=" + gid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role=" + role +
                ", avatar='" + avatar + '\'' +
                ", stateSave=" + stateSave +
                ", state='" + state + '\'' +
                ", lang='" + lang + '\'' +
                ", valid=" + valid +
                '}';
    }
}
