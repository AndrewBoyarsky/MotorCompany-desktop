package com.boyarskycompany.mcis.persistence;

import com.boyarskycompany.mcis.controllers.ConstructReportController;
import com.boyarskycompany.mcis.run.MainApplicationEntry;
import com.boyarskycompany.mcis.util.DoubleTuple;
import com.boyarskycompany.mcis.util.EntityClassUtil;
import com.boyarskycompany.mcis.util.HibernateUtil;
import com.boyarskycompany.mcis.util.StageRegister;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by zandr on 28.09.2016.
 */
public class GenericDAOImpl<T> implements GenericDAO<T> {
    private static final Logger log = LogManager.getLogger(GenericDAOImpl.class.getName());
    private Class<T> entityClass;

    public GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void add(T record) {
        Thread thread = new Thread(() -> {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();
                try {
                    session.save(record);
                }
                catch (Exception e) {
                    log.error("Cannot add record: " + record.toString(), e);
                    return;
                }
                tx.commit();
                log.info("Record: " + record.toString() + " was successfully pushed into database");
            }
            catch (Exception e) {
                log.error("Error during doing operation add(T record) record: " + record.toString(), e);
            }
        }, "Thread for adding record: " + record.toString());
        thread.start();
    }

    @Override
    public T getRecordByID(long id) {
        T rec = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            try {
                rec = session.get(entityClass, id);
            }
            catch (Exception e) {
                log.error("Cannot get record by id = " + id, e);
                return null;
            }
            log.info("Record was pulled by id: " + id + " \n" + rec.toString());
        }
        catch (Exception e) {
            log.error("Error during doing operation getRecordById(long id) id = " + id, e);
        }
        return rec;
    }

    @Override
    public void update(T record) {
        Thread thread = new Thread(() -> {
            try (Session session = HibernateUtil.getSessionFactory().openSession();) {
                Transaction tx = session.beginTransaction();
                try {
                    session.merge(record);
                }
                catch (Exception e) {
                    log.error("Cannot update record: " + record.toString() + " in database", e);
                    return;
                }
                tx.commit();
                log.info("Record: " + record.toString() + " was successfully updated in database");
            }
            catch (Exception e) {
                log.error("Error during doing operation update(T record) record = " + record.toString(), e);
            }
        }, "Thread for updating record: " + record.toString());
        thread.start();
    }

    @Override
    public void deleteRecord(T record) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            try {
                session.delete(record);
            }
            catch (Exception e) {
                log.error("Cannot delete record: " + record.toString() + " from database", e);
                return;
            }
            tx.commit();
            log.info("Record: " + record.toString() + " was removed");
        }
        catch (Exception e) {
            log.error("Error during doing operation deleteRecord(T record) record: " + record.toString(), e);
        }
    }

    @Override
    public List<T> getRecordList() {
        List<T> list = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Query<T> query = null;
            try {
                query = session.createQuery(" from " + entityClass.getName(), entityClass);
            }
            catch (Exception e) {
                log.error("Cannot get list of records for  Entity class: " + entityClass.getName(), e);
                return null;
            }
            list = query.list();
            log.info("Records for Entity class: " + entityClass.getName() + ", size of List: " + list.size());
        }
        catch (Exception e) {
            log.error("Error during doing operation getRecordList for Entity class: " + entityClass.getName(), e);
        }
        return list;
    }

    public <S> DoubleTuple<ArrayList<T>, HashMap<Field, Long>> getBoundedRecordsWithParentRecord(Class<S> parentClass, S parentRecord) {
        ArrayList records = null;
        Query<T> query = null;
        DoubleTuple<ArrayList<T>, HashMap<Field, Long>> tuple = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            Class primaryKeyClass = null;
            HashMap<Field, Long> ids = new HashMap<Field, Long>();
            try {
                primaryKeyClass = Class.forName("com.boyarskycompany.mcis.entities." + parentClass.getSimpleName() + "PK");
                log.debug("PK Class for EntityClass: " + parentClass.getName() + " is " + primaryKeyClass);
                ArrayList<Field> idFields = new ArrayList<Field>(Arrays.asList(primaryKeyClass.getDeclaredFields()));
                log.debug("IdFields from PK Class: " + idFields.toString());
                ArrayList<Field> allFields = new ArrayList<>(Arrays.asList(parentClass.getDeclaredFields()));
                log.debug("ALLFields from parentClass: " + allFields.toString());
                for (int i = 0; i < idFields.size(); i++) {
                    Field idField = idFields.get(i);
                    String fieldName = idField.getName();
                    log.trace("IdField from PK class " + primaryKeyClass.getName() + " is a " + fieldName);
                    for (int j = 0; j < allFields.size(); j++) {
                        Field field = allFields.get(j);
                        if (field.getName().equalsIgnoreCase(fieldName)) {
                            field.setAccessible(true);
                            Long l = (Long) field.get(parentRecord);
                            ids.put(field, l);
                            log.debug("Matched field: " + field.getName() + " with value = " + l);
                        }
                    }
                }
            }
            catch (ClassNotFoundException e) {
                Field idField = null;
                String fieldName = "id" + EntityClassUtil.getSimpleClassName(parentClass);
                try {
                    idField = parentClass.getDeclaredField(fieldName);
                    idField.setAccessible(true);
                    Long l = (Long) idField.get(parentRecord);
                    ids.put(idField, l);
                    log.debug("idField for entity class " + parentClass.getName() + " with value = " + l);
                }
                catch (NoSuchFieldException e1) {
                    log.error("Cannot find field with name " + fieldName + " in the class " + parentClass.getName());
                }
            }
            String condition = " ";
            Iterator<Map.Entry<Field, Long>> iterator = ids.entrySet().iterator();
            log.debug("Id fields and their value: \n" + ids.toString());
            while (iterator.hasNext()) {
                Map.Entry pair = iterator.next();
                Long l = (Long) pair.getValue();
                String s = ((Field) pair.getKey()).getName() + " = " + l + " ";
                if (iterator.hasNext()) {
                    s += "AND ";
                }
                condition += s;
            }
            log.debug("Condition string " + condition);
            String queryString = "from " + entityClass.getCanonicalName() + " where " + condition;
            log.debug("Query string = " + queryString);
            try {
                query = session.createQuery(queryString);
                records = (ArrayList) query.list();
                log.debug("Retrieved records: " + records.toString());
                tuple = new DoubleTuple<ArrayList<T>, HashMap<Field, Long>>(records, ids);
            }
            catch (Exception e) {
                log.error("Error with SQL syntax, query string = " + queryString, e);
            }
        }
        catch (IllegalAccessException e) {
            log.error("Error occurred with access to the class field, Entity Class = " + parentClass.getName(), e);
            return null;
        }
        return tuple;
    }

    public ObservableList<T> getObservableListRecords() {
        List<T> list = getRecordList();
        ObservableList<T> observableList = FXCollections.observableArrayList();
        observableList.addAll(list);
        return observableList;
    }

    public Long getMaxId(boolean isLast) {
        List<T> list = getRecordList();
        if (list == null || list.size() == 0) {
            log.debug("Instances of entity class " + entityClass.getName() + " aint exist.");
            return 0l;
        }
        Method getter = null;
        String getterName = "getId" + EntityClassUtil.getSimpleClassName(entityClass);
        log.debug("Getter name for entity class " + entityClass.getName() + " = " + getterName);
        try {
            getter = entityClass.getDeclaredMethod(getterName);
        }
        catch (NoSuchMethodException e) {
            log.error("Method " + getterName + " isnt exist.", e);
            return null;
        }
        getter.setAccessible(true);
        T maxRecord = null;
        Long maxId = null;
        if (isLast) {
            maxRecord = list.get(list.size() - 1);
            try {
                maxId = (Long) getter.invoke(maxRecord);
            }
            catch (IllegalAccessException e) {
                log.error("Cannot get access to method " + getterName + " ! ", e);
            }
            catch (InvocationTargetException e) {
                log.error("Error with method " + getterName + " invocation.", e);
            }
        } else {
            ArrayList<Long> idList = new ArrayList<Long>((int) Math.ceil(list.size() * 1.5));
            Method finalGetter = getter;
            list.forEach(record -> {
                Long id = null;
                try {
                    id = (Long) finalGetter.invoke(record);
                }
                catch (IllegalAccessException e) {
                    log.error("Cannot get access to method " + getterName + " ! ", e);
                }
                catch (InvocationTargetException e) {
                    log.error("Error with method " + getterName + " invocation.", e);
                }
                idList.add(id);
            });
            maxId = Collections.max(idList);
        }
        log.debug("Max id for class " + entityClass.getName() + " = " + maxId);
        return maxId;
    }

    public void createReport(final String jrxmlSource, String queryString) {
        log.debug("Query string for Jasper report:" + queryString);
        Runnable runnable = () -> {
            try (Session session = HibernateUtil.getSessionFactory().openSession();) {
                session.doWork(connection -> {
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlSource);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashedMap(),
                                new JRResultSetDataSource(resultSet));
                        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                        jasperViewer.setVisible(true);
                        log.info("Report was constructed.");
                    }
                    catch (JRException e) {
                        log.error("Error with construction report.", e);
                    }
                    catch (SQLException e) {
                        log.error("Error with sql syntax.", e);
                    }
                });
            }
            catch (Exception e) {
                log.error("Unexpected error!", e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void createReport(final String jrxmlSource) {
        createReport(jrxmlSource, "select * from " + EntityClassUtil.getLowerCaseVariableName(entityClass));
    }

    public void createReportViaConnection(final String jrxmlSource) throws IOException {
        log.info("Report source file: " + jrxmlSource);
        Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/reportConstructScene.fxml"), MainApplicationEntry.getLanguageResourceBundle()));
        ProgressBar progressBar = ConstructReportController.getProgressBar();
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setScene(scene);
        Task task = new Task<Void>() {
            private void interruptConstructionReport() {
                log.info("Report construction was interrupted!");
                throw new RuntimeException("Thread has been interupted");
            }

            @Override
            protected Void call() throws Exception {
                progressBar.setProgress(0.1);
                try (Session session = HibernateUtil.getSessionFactory().openSession();) {
                    session.doWork(connection -> {
                        try {
                            progressBar.setProgress(0.2);
                            if (isCancelled()) {interruptConstructionReport();}
                            JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream(jrxmlSource));
                            progressBar.setProgress(0.3);
                            if (isCancelled()) {interruptConstructionReport();}
                            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
                            progressBar.setProgress(0.6);
                            if (isCancelled()) {interruptConstructionReport();}
                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashedMap(),
                                    connection);
                            progressBar.setProgress(0.8);
                            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                            progressBar.setProgress(0.90);
                            if (isCancelled()) {interruptConstructionReport();}
                            progressBar.setStyle("-fx-progress-color: greenyellow");
                            jasperViewer.setVisible(true);
                            progressBar.setProgress(1);
                            log.info("Report was constructed.");
                        }
                        catch (JRException e) {
                            log.error("Error with construction report.", e);
                        }
                        catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    });
                }
                catch (Exception e) {
                    log.error("Unexpected error!", e);
                }
                return null;
            }
        };
        task.setOnSucceeded(e -> stage.close());
        task.setOnCancelled(e -> {
            stage.close();
            task.cancel(true);
        });
        Thread thread = new Thread(task);
        stage.getIcons().add(new Image("images/reportConstructIcon.png"));
        stage.setTitle(MainApplicationEntry.getLanguageResourceBundle().getString("reportConstructTitle"));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        thread.start();
        StageRegister.register(stage);
        stage.setOnCloseRequest(e -> {
            if (task.isRunning()) {
                task.cancel(true);
            }
            stage.close();
        });
    }
}
