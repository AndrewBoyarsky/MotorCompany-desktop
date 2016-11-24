package com.boyarskycompany.src.controllers.database.dau;


import java.util.List;

/**
 * Created by zandr on 26.09.2016.
 */
public interface BaseDAU<T> {
    public void add(T record);

    public T getRecordByID(long id);

    public void update(T record);

    public void deleteRecord(T record);

    public List<T> getListRecords();
}
