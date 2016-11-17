package com.boyarskycompany.src.controllers.entities.util;

import com.boyarskycompany.src.controllers.LogController;
import com.boyarskycompany.src.run.Main;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Created by zandr on 17.11.2016.
 */
public class TimeThread {
    private Thread thread;
    private Task task;
    private boolean isSuspended = false;
    private boolean isInterrupted = false;
    private TextArea textArea;
    private Date iniDate = new Date();
    private ResourceBundle res;

    public TimeThread() {
        this(null, null);
    }

    public TimeThread(TextArea textArea, ResourceBundle res) {
        this.res = res;
        this.textArea = textArea;
        configureTimeShowing();
    }

    private void configureTimeShowing() {
        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!isInterrupted()) {
                    synchronized (this) {
                        while (isSuspended) {
                            System.out.println("Thread is suspended");
                            wait();
                        }
                    }
                        System.out.println("Thread is working");
                        ResourceBundle resources = Main.getResLan();
                        Date sessionDuration = new Date(new Date().getTime() - iniDate.getTime() + 22 * 3600 * 1000);
                        textArea.setText(resources.getString("sessionDuration") + ": " + new SimpleDateFormat("HH:mm:ss").format(sessionDuration)
                                + " " + resources.getString("userLabel") + ": " + LogController.getCurrentUser().getUserName());
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                System.out.println("Thread has been stopped");
                return null;
            }
        };
        thread = new Thread(task);
        thread.setDaemon(true);
    }
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public ResourceBundle getRes() {
        return res;
    }

    public void setRes(ResourceBundle res) {
        this.res = res;
    }
    public Date getIniDate() {
        return iniDate;
    }

    public void setIniDate(Date iniDate) {
        this.iniDate = iniDate;
    }

    public Thread getThread() {

        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
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
