package com.boyarskycompany.mcis.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.boyarskycompany.mcis.run.MainApplicationEntry.getLanguageResourceBundle;

/**
 * Created by zandr on 18.10.2016.
 */
public class EntityClassUtil {
    private static final Logger log = LogManager.getLogger(EntityClassUtil.class.getName());

    /**
     * This method set first letter of simple entity class name to lower case and returns variable name from
     * entity class simple name.
     *
     * Example: entityClass = com.mycompany.myproj.entities.UserdataEntity,
     * method will return "userdata" — lower case variable name of entity class
     *
     * @param entityClass Hibernate Entity class with or without suffix "Entity"
     * @return variable name of entity class
     * @see EntityClassUtil#getSimpleClassName(Class)
     */
    public static String getLowerCaseVariableName(Class entityClass) {
        String className = getSimpleClassName(entityClass);
        className = className.substring(0, 1).toLowerCase() +  className.substring(1);
        log.debug("Entity class " + entityClass.getName() + " has variable name: " + className);
        return className;
    }

    /**
     * This method returns simple name of entity class without package prefix
     * and "Entity" suffix, if it exist.
     * @param entityClass — Hibernate Entity class with or without suffix "Entity"
     * @return simple entity class name without suffix "Entity"
     * @see Class#getSimpleName()
     */
    public static String getSimpleClassName(Class entityClass) {
        String className = entityClass.getSimpleName();
        try {
            className = className.substring(0, className.lastIndexOf("Entity"));
        }
        catch (Exception e) {
            log.debug("Entity class name does not include suffix Entity");
        }
        return className;
    }

    /**
     * This method loads and returns entity class from it own
     * id field
     *
     * For example, idFieldName = "idUserdata", method will return
     * loaded class com.boyarskycompany.mcis.entities.UserdataEntity |
     * com.boyarskycompany.mcis.entities.Userdata
     *
     * @param idFieldName id field name from entity class
     * @return loaded entity class
     * @throws ClassNotFoundException if entity class com.boyarskycompany.mcis.entities.{entityClassName}[Entity]
     * are not found
     */
    public static Class loadEntityClassFromIdFieldName(String idFieldName) throws ClassNotFoundException {
        String entityClassName = idFieldName.substring(2).toLowerCase();
        entityClassName = entityClassName.substring(0, 1).toUpperCase() + entityClassName.substring(1);
        try {
            return Class.forName("com.boyarskycompany.mcis.entities." + entityClassName + "Entity");
        } catch (ClassNotFoundException e) {
            log.debug("Entity class name does not include suffix Entity");
            return Class.forName("com.boyarskycompany.mcis.entities." + entityClassName);
        }
    }

    /**
     * returns loaded entity class from simple entity class name.
     *
     * For example, entityClassName = UserdataEntity | UserData,
     * method will returns class with name = com.boyarskycompany.mcis.entities.UserdataEntity
     * | com.boyarskycompany.mcis.entities.UserData
     * @param entityClassName simple entity class name (for example, UserdataEntity | UserData)
     * @return loaded entity class
     * @throws ClassNotFoundException if class com.boyarskycompany.mcis.entities.{entityClassName} are not found
     */
    public static Class loadClassFromSimpleEntityClassName(String entityClassName) throws ClassNotFoundException {
        return Class.forName("com.boyarskycompany.mcis.entities." + entityClassName);
    }

    /**
     * This method returns double tuple, that contains list of all entities in
     * the session factory and list of database table names, that according to entities
     * in the same position of the list
     * @return double tuple, that include list of entity names and list of table names
     */
    public static DoubleTuple<List<String>, List<String>> getTableAndEntityNames() {
        ResourceBundle resourceBundle = getLanguageResourceBundle();
        SessionFactory factory = HibernateUtil.getSessionFactory();
        List<String> entityList = new ArrayList<String>();
        List<String> tableList = new ArrayList<String>();
        factory.getMetamodel().getEntities().forEach(entityType -> {
            String entityName = entityType.getName();
            entityList.add(entityName);
            String tableName = entityName.toLowerCase();
            try {
                tableName = entityName.substring(0, entityName.lastIndexOf("Entity")).toLowerCase();
            }
            catch (Exception e) {
                log.debug("Entity name does not contain \"Entity\" suffix");
            }
            tableList.add(resourceBundle.getString(tableName));
        });
        DoubleTuple<List<String>, List<String>> ev = new DoubleTuple<List<String>, List<String>>(tableList, entityList);
        return ev;
    }
    public static Class loadClassFromCollectionField(String fieldName) {
        try {
            return Class.forName("com.boyarskycompany.mcis.entities." + fieldName.substring(0, 1).toUpperCase() +
                    fieldName.substring(1, fieldName.length() -1) + "Entity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
