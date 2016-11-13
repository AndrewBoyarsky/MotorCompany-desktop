package com.boyarskycompany.src.privacy;


import com.boyarskycompany.src.controllers.LogController;
import com.boyarskycompany.src.controllers.LoginData;
import com.boyarskycompany.src.run.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by zandr on 16.09.2016.
 */
public class Users {

    public static void addnewUser(UserBean user) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
       long i = Long.parseLong(prefs.get("number", "0"));
        if (i == 0) {
            prefs.put("number", "1");
            i++;
        } else {
            prefs.put("number", "" + (++i));
        }
        prefs.put("user" + i, user.getUserName());
        prefs.put("password" + i, user.getUserPassword());
        prefs.put("privileges" + i, user.getUserPrivileges());
    }

    public static void deleteUser(UserBean user) {
        Preferences preferences = Preferences.userNodeForPackage(Main.class);
        LoginData loginData = LogController.login(user.getUserName(), user.getUserPassword());
        preferences.remove("user" + loginData.getNumber());
        preferences.remove("password" + loginData.getNumber());
        preferences.remove("privileges" + loginData.getNumber());
    }
    public static void changeUser(String user, String newName, String password, String newPassword, String newPrivileges) {
        LoginData loginData = LogController.login(user, password);
        if (loginData.isValidated()) {
            Preferences prefs = Preferences.userNodeForPackage(Main.class);
            prefs.put("user" + loginData.getNumber(), newName);
            prefs.put("password" + loginData.getNumber(), newPassword);
            prefs.put("privileges" + loginData.getNumber(), newPrivileges);
        }
    }

    public static void deleteAllUsers() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        try {
            prefs.clear();
        }
        catch (BackingStoreException e) {
            System.out.println("Delete error. Try again later or call administrator.");
        }
    }

    public static void createDefaultUsers() throws BackingStoreException {
        deleteAllUsers();
        Users.addnewUser(new UserBean("Admin", "finalfullpowers", "Admin"));
        Users.addnewUser(new UserBean("User", "lowpossibilities", "User"));
        Users.addnewUser(new UserBean("Manager", "mediumpossibilities", "Manager"));
        Preferences preferences = Preferences.userNodeForPackage(Main.class);
        System.out.println(Arrays.toString(preferences.keys()));
        for (String s : preferences.keys()) {
            System.out.println(preferences.get(s, "-2"));

        }
    }

    public static List<UserBean> getAllUsers() {
        Preferences preferences = Preferences.userNodeForPackage(Main.class);
        ArrayList<UserBean> userList = null;
        int number = Integer.parseInt(preferences.get("number", "0"));
        if (number == 0) return userList;
        else {
            userList = new ArrayList<>();
            for (long i = 1; i <= number; i++) {
                UserBean user = new UserBean(preferences.get("user" + i, "0"), preferences.get("password" + i, "0"), preferences.get("privileges" + i, "0"));
                if (user.getUserName().equals("0")) {
                    continue;
                }
                userList.add(user);
            }
        }
        return userList;
    }
}