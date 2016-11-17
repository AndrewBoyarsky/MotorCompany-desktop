package com.boyarskycompany.src.privacy;

/**
 * Created by zandr on 12.11.2016.
 */
public class UserBean {
    private String userName;
    private String userPassword;
    private String userPrivileges;


    public UserBean() {
    }

    public UserBean(String userName, String userPassword, String userPrivileges) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPrivileges = userPrivileges;
    }

    public UserBean(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBean userBean = (UserBean) o;

        if (!userName.equals(userBean.userName)) return false;
        if (!userPassword.equals(userBean.userPassword)) return false;
        return userPrivileges.equals(userBean.userPrivileges);

    }

    public String getUserPrivileges() {
        return userPrivileges;
    }

    public void setUserPrivileges(String userPrivileges) {
        this.userPrivileges = userPrivileges;
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + userPassword.hashCode();
        result = 31 * result + userPrivileges.hashCode();
        return result;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
