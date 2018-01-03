package com.boyarskycompany.mcis.util;


import com.boyarskycompany.mcis.run.MainApplicationEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by zandr on 16.09.2016.
 */
public class UserUtil {
    private static final Logger log = LogManager.getLogger(UserUtil.class.getName());

    public static void addNewUser(User user) {
        Preferences userStorage = Preferences.userNodeForPackage(MainApplicationEntry.class);
        long userNumber = Long.parseLong(userStorage.get("number", "0"));
        log.debug("User number = " + userNumber);
        if (userNumber == 0) {
            userStorage.put("number", "1");
            userNumber++;
        } else {
            userStorage.put("number", "" + (++userNumber));
        }
        userStorage.put("user" + userNumber, user.getName());
        userStorage.put("password" + userNumber, user.getPassword());
        userStorage.put("privileges" + userNumber, user.getPrivileges());
        log.info("New user is created. " + user);
    }

    public static void deleteUser(User user) {
        Preferences userStorage = Preferences.userNodeForPackage(MainApplicationEntry.class);
        long userNumber = userStorage.getLong("number", -1);
        if (userNumber <= 0) {
            log.error("There are no users in the storage!");
            return;
        } else {
            for (long i = 1; i <= userNumber; i++) {
                if (userStorage.get("user" + i, "0").equals(user.getName())) {
                    userStorage.remove("user" + i);
                    userStorage.remove("password" + i);
                    userStorage.remove("privileges" + i);
                    break;
                }
            }
        }
        log.warn("User: " + user + " was removed.");
    }

    public static void updateUser(User oldUser, User newUser) {
        boolean isValid = isValid(oldUser);
        Preferences userStorage = Preferences.userNodeForPackage(MainApplicationEntry.class);
        long userNumber = userStorage.getLong("number", -1);
        if (userNumber <= 0) {
            log.error("There are no users in the storage!");
        } else {
            if (isValid) {
                for (long i = 1; i <= userNumber; i++) {
                    if (userStorage.get("user" + i, "-1").equals(oldUser.getName())) {
                        userStorage.put("user" + i, newUser.getName());
                        userStorage.put("password" + i, newUser.getPassword());
                        userStorage.put("privileges" + i, newUser.getPrivileges());
                        log.warn("User " + oldUser + " was successfully replaced with User " + newUser);
                        break;
                    }
                }

            } else {
                log.error("User " + oldUser + " is not valid.");
            }
        }
    }

    private static void removeAllUsers() {
        log.warn("Beginning of the removing all users.");
        Preferences userStorage = Preferences.userNodeForPackage(MainApplicationEntry.class);
        try {
            userStorage.clear();
        }
        catch (BackingStoreException e) {
            log.error("Removing was failed. ", e);
        }
        log.info("All users were successfully removed.");
    }

    public static void createDefaultUsers() {
        log.info("Creating default users...");
        removeAllUsers();
        UserUtil.addNewUser(new User("Admin", "finalfullpowers", "Admin"));
        UserUtil.addNewUser(new User("User", "lowpossibilities", "User"));
        UserUtil.addNewUser(new User("Manager", "mediumpossibilities", "Manager"));
        Preferences userStorage = Preferences.userNodeForPackage(MainApplicationEntry.class);
        int counter = 0;
        try {
            for (String s : userStorage.keys()) {
                String userData = userStorage.get(s, "-2");
                if (counter == 3) {
                    counter = 0;
                    log.warn("DEFAULT User: ");
                }
                if (counter == 0) {
                    log.info("username = " + userData);
                } else if (counter == 1) {
                    log.info("password = " + userData);
                } else if (counter == 2) {
                    log.info("privileges = " + userData);
                }
                counter++;
            }
        }
        catch (BackingStoreException e) {
            log.error("User backing store error. ", e);
        }
    }

    public static List<User> getAllUsers() {
        Preferences preferences = Preferences.userNodeForPackage(MainApplicationEntry.class);
        ArrayList<User> userList = new ArrayList<>();
        int userNumber = Integer.parseInt(preferences.get("number", "0"));
        log.debug("All user number = " + userNumber);
        if (userNumber == 0) return null;
        else {
            for (long i = 1; i <= userNumber; i++) {
                User user = new User(preferences.get("user" + i, "-1"), preferences.get("password" + i, "-1"), preferences.get("privileges" + i, "-1"));
                if (user.getName().equals("-1") || user.getPassword().equals("-1") || user.getPrivileges().equals("-1")) {
                    log.error("User " + i + "is not valid. " + user);
                    continue;
                }
                userList.add(user);
            }
        }
        log.debug("All users: " + userList.toString());
        return userList;
    }

    public static boolean isValid(User user) {
        List<User> users = getAllUsers();
        for (User userBean : users) {
            if (userBean.getName().equals(user.getName()) && userBean.getPassword().equals(user.getPassword())) {
                user.setPrivileges(userBean.getPrivileges());
                return true;
            }
        }
        return false;
    }
}