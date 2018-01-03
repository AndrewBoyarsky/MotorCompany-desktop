package com.boyarskycompany.mcis.util;

/**
 * Created by zandr on 12.11.2016.
 */
public class User {
    private String name;
    private String password;
    private String privileges;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + "****" + '\'' +
                ", privileges='" + privileges + '\'' +
                '}';
    }

    public User() {
    }

    public User(String name, String password, String privileges) {
        this.name = name;
        this.password = password;
        this.privileges = privileges;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        if (!password.equals(user.password)) return false;
        return privileges.equals(user.privileges);

    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + privileges.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
