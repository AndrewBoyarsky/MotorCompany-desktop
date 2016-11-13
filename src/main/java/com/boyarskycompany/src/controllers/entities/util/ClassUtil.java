package com.boyarskycompany.src.controllers.entities.util;

/**
 * Created by zandr on 18.10.2016.
 */
public class ClassUtil {
    public static String getSimpleLowerCaseClassName(Class clazz) {
        String className = getSimpleClassName(clazz);
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        return className;
    }
    public static String getSimpleClassName(Class clazz) {
        String className = clazz.getSimpleName();
        className = className.substring(0, className.indexOf("Entity"));
        return className;
    }
    public static Class loadClassFromIdField(String fieldName) {
        fieldName = fieldName.substring(2).toLowerCase();
        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            return Class.forName("com.boyarskycompany.src.entities." + fieldName + "Entity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class loadClassFromCollectionField(String fieldName) {
        try {
            return Class.forName("com.boyarskycompany.src.entities." + fieldName.substring(0, 1).toUpperCase() +
                    fieldName.substring(1, fieldName.length() -1) + "Entity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
