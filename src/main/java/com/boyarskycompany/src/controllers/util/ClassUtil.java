package com.boyarskycompany.src.controllers.util;

import com.boyarskycompany.src.controllers.database.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.boyarskycompany.src.run.Main.getResLan;

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

    public static Class loadClassFromEntityName(String entityName) throws ClassNotFoundException {
        return Class.forName("com.boyarskycompany.src.entities." + entityName);
    }
    public static DoubleTuple<List<String>, List<String>> getTablesNames() {
        ResourceBundle resourceBundle = getResLan();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        List<String> entityList = new ArrayList<String>();
        List<String> tableList = new ArrayList<String>();
        factory.getMetamodel().getEntities().forEach(entityType -> {
            String entityName = entityType.getName();
            entityList.add(entityName);
            String tableName = entityName.substring(0, 1).toLowerCase() +
                    entityName.substring(1, entityName.indexOf("Entity"));
            tableList.add(resourceBundle.getString(tableName));
        });
        DoubleTuple<List<String>, List<String>> ev = new DoubleTuple<List<String>, List<String>>(tableList, entityList);
        return ev;
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
