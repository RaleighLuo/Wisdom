package com.gkzxhn.wisdom.entity;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class PayRecordEntity {
    private long date;
    private String id;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        if (id != null && !id.equals("null"))
            this.id = id;
    }
}
