package com.boyarskycompany.src.controllers.entities.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zandr on 18.10.2016.
 */
public class RecordsIdsTuple<T> {
    private List<T> recordList;
    private HashMap<Field, Long> idMap;

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public HashMap<Field, Long> getIdMap() {
        return idMap;
    }

    public void setIdMap(HashMap<Field, Long> idMap) {
        this.idMap= idMap;
    }

    public RecordsIdsTuple(List<T> recordList, HashMap<Field, Long> idMap) {
        this.recordList = recordList;
        this.idMap = idMap;
    }
}
