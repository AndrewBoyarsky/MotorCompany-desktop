package com.boyarskycompany.mcis.util;

import com.boyarskycompany.mcis.controllers.LoginController;
import com.boyarskycompany.mcis.run.MainApplicationEntry;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Created by zandr on 17.11.2016.
 */
public class ApplicationSession {
    private Thread thread;
    private Task task;
    private TextArea textArea;
    private ResourceBundle languageResourceBundle;
    private Date startDate = new Date();
    private boolean isSuspended = false;
    private boolean isInterrupted = false;

    public ApplicationSession() {
        this(null, null);
    }

    private static class ApplicationSessionSingleton {
        private static final ApplicationSession INSTANCE = new ApplicationSession();
    }
    private ApplicationSession(TextArea textArea, ResourceBundle languageResourceBundle) {
        this.languageResourceBundle = languageResourceBundle;
        this.textArea = textArea;
        configureTimeShowing();
    }

    public static ApplicationSession getInstance() {
        return ApplicationSessionSingleton.INSTANCE;
    }

    public void resetSessionTime() {
        suspend();
        startDate = new Date();
        resume();
    }

    private void configureTimeShowing() {
        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!isInterrupted()) {
                    synchronized (task) {
                        while (isSuspended) {
                            System.out.println("Application session is suspended");
                            wait();
                        }
                    }
                    ResourceBundle resources = MainApplicationEntry.getLanguageResourceBundle();
                    Date sessionDuration = new Date(new Date().getTime() - startDate.getTime() + 22 * 3600 * 1000);
                    textArea.setText(resources.getString("sessionDuration") + ": " + new SimpleDateFormat("HH:mm:ss").format(sessionDuration)
                            + " " + resources.getString("userLabel") + ": " + LoginController.getCurrentUser().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (InterruptedException e) {
                        System.err.println("Application session was unexpectedly interrupted!");
                        return null;
                    }
                }
                System.out.println("Application session was finished");
                return null;
            }
        };
        thread = new Thread(task);
        thread.setDaemon(true);
    }

    public ResourceBundle getLanguageResourceBundle() {
        return languageResourceBundle;
    }

    public void setLanguageResourceBundle(ResourceBundle languageResourceBundle) {
        this.languageResourceBundle = languageResourceBundle;
    }

    public Date getStartDate() {
        return startDate;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }


    public void start() {
        thread.start();
        System.out.println("Application session is started. Status OK.");
    }

    public void suspend() {
        isSuspended = true;
    }

    public void resume() {
        isSuspended = false;
        synchronized (task) {
            task.notify();
        }
    }

    public void stop() {
        isInterrupted = true;
    }
}
