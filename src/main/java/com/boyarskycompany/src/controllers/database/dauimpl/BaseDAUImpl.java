package com.boyarskycompany.src.controllers.database.dauimpl;

import com.boyarskycompany.src.controllers.ConstructReportController;
import com.boyarskycompany.src.controllers.database.HibernateUtil;
import com.boyarskycompany.src.controllers.database.dau.BaseDAU;
import com.boyarskycompany.src.controllers.entities.util.ClassUtil;
import com.boyarskycompany.src.controllers.entities.util.RecordsIdsTuple;
import com.boyarskycompany.src.run.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;
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
public class BaseDAUImpl<T> implements BaseDAU<T> {
    private Class<T> cl;

    public BaseDAUImpl(Class<T> cl) {
        this.cl = cl;
    }

    @Override
    public void add(T record) {
        Thread thread = new Thread(() -> {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                Transaction tx = session.beginTransaction();
                session.save(record);
                tx.commit();
            }
            finally {
                session.close();
            }
        });
        thread.start();
    }

    @Override
    public T getRecordByID(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T rec = null;
        try {
            Transaction tx = session.beginTransaction();
            rec = session.get(cl, id);
        }
        finally {
            session.close();
        }
        return rec;
    }

    @Override
    public void update(T record) {
        Thread thread = new Thread(() -> {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                Transaction tx = session.beginTransaction();
                session.merge(record);
                tx.commit();
            }
            finally {
                session.close();
            }
        });
        thread.start();
    }

    @Override
    public void deleteRecord(T record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.delete(record);
            tx.commit();
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<T> getListRecords() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<T> query = null;
        List<T> list = null;
        try {
            query = session.createQuery(" from " + cl.getName(), cl);
            list = query.list();
        }
        finally {
            session.close();
        }
        return list;
    }

    @Override
    public <S> RecordsIdsTuple<T> getBoundedRecords(Class<S> parentClass, S parentRecord) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List records = null;
        Query<T> query = null;
        RecordsIdsTuple<T> tuple = null;
        try {
            Transaction tx = session.beginTransaction();
            Class primaryKeyClass = null;
            HashMap<Field, Long> ids = new HashMap<Field, Long>();
            try {
                primaryKeyClass = Class.forName("com.boyarskycompany.src.entities." + parentClass.getSimpleName() + "PK");

                ArrayList<Field> idFields = new ArrayList<Field>(Arrays.asList(primaryKeyClass.getDeclaredFields()));
                ArrayList<Field> allFields = new ArrayList<>(Arrays.asList(parentClass.getDeclaredFields()));
                for (int i = 0; i < idFields.size(); i++) {
                    Field idField = idFields.get(i);
                    String fieldName = idField.getName();
                    for (int j = 0; j < allFields.size(); j++) {
                        Field field = allFields.get(j);
                        if (field.getName().equalsIgnoreCase(fieldName)) {
                            field.setAccessible(true);
                            Long l = (Long) field.get(parentRecord);
                            ids.put(field, l);
                        }
                    }
                }
            }
            catch (ClassNotFoundException e) {
                Field idField = null;
                try {
                    idField = parentClass.getDeclaredField("id" + ClassUtil.getSimpleClassName(parentClass));
                }
                catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                }
                idField.setAccessible(true);
                Long l = (Long) idField.get(parentRecord);
                ids.put(idField, l);
            }
            String condition = " ";
            Iterator<Map.Entry<Field, Long>> iterator = ids.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pair = iterator.next();
                Long l = (Long) pair.getValue();
                String s = ((Field) pair.getKey()).getName() + " = " + l + " ";
                if (iterator.hasNext()) {
                    s += "AND ";
                }
                condition += s;
            }
            String queryString = "from " + cl.getCanonicalName() + " where " + condition;
            query = session.createQuery(queryString);
            records = query.list();
            tuple = new RecordsIdsTuple<T>(records, ids);
            //            S record = session.get(parentClass, (Serializable) idClass.cast(idClassInstance));
            //            String className = cl.getSimpleName();
            //            className = className.substring(0, className.indexOf("Entity"));
            //            Method getRecordsListMethod = parentClass.getMethod("get" + className + "s");
            //            getRecordsListMethod.setAccessible(true);
            //            records = (Collection<T>) getRecordsListMethod.invoke(record);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return tuple;
    }

    public ObservableList<T> getObservableListRecords() {
        List<T> list = getListRecords();
        ObservableList<T> observableList = FXCollections.observableArrayList();
        observableList.addAll(list);
        return observableList;
    }

    public Long getMaxId(boolean isLast) {
        List<T> list = getListRecords();
        if (list.size() == 0) {
            return 0l;
        }
        Method getter = null;
        try {
            getter = cl.getDeclaredMethod("getId" + ClassUtil.getSimpleClassName(cl));
        }
        catch (NoSuchMethodException e) {
            return null;
        }
        getter.setAccessible(true);
        T rec = null;
        Long l = null;
        if (isLast) {
            rec = list.get(list.size() - 1);
            try {
                l = (Long) getter.invoke(rec);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            ArrayList<Long> idList = new ArrayList<Long>((int) Math.ceil(list.size() * 1.5));
            Method finalGetter = getter;
            list.forEach(item -> {
                Long id = null;
                try {
                    id = (Long) finalGetter.invoke(item);
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                idList.add(id);
            });
            l = Collections.max(idList);
        }
        return l;
    }

    public void createReport(final String jrxmlSource, String queryString) {
        Runnable runnable = () -> {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.doWork(connection -> {
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlSource);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashedMap(),
                                new JRResultSetDataSource(resultSet));
                        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                        jasperViewer.setVisible(true);
                    }
                    catch (JRException e) {
                        e.printStackTrace();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

            }
            finally {
                session.close();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void createReport(final String jrxmlSource) {
        createReport(jrxmlSource, "select * from " + ClassUtil.getSimpleLowerCaseClassName(cl));
    }

    public void createReportViaConnection(final String jrxmlSource) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/constructIndicator.fxml"), Main.getResLan()));
        ProgressBar progressBar = ConstructReportController.getProgressBar();
        Task task = new Task() {
            @Override
            protected Void call() throws Exception {
                Session session = HibernateUtil.getSessionFactory().openSession();
                progressBar.setProgress(0.1);
                try {
                    session.doWork(connection -> {
                        try {
                            progressBar.setProgress(0.2);
                            JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream(jrxmlSource));
                            progressBar.setProgress(0.3);
                            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
                            progressBar.setProgress(0.6);
                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashedMap(),
                                    connection);
                            progressBar.setProgress(0.8);
                            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                            progressBar.setProgress(0.95);
                            progressBar.setStyle("-fx-progress-color: greenyellow");
                            jasperViewer.setVisible(true);
                            progressBar.setProgress(1);
                        }
                        catch (JRException e) {
                            e.printStackTrace();
                        }
                    });

                }
                finally {
                    session.close();
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> thread.interrupt());
        stage.centerOnScreen();
        stage.show();
        task.setOnSucceeded(e -> {
            progressBar.setProgress(0d);
            stage.close();
        });
        thread.start();
    }
}
