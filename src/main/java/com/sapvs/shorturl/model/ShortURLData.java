package com.sapvs.shorturl.model;

import com.sapvs.shorturl.util.Util;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table("shorturldata")
public class ShortURLData implements Serializable {
    @PrimaryKey
    private String id;

    private String longURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public ShortURLData(String id, String longURL) {
        this.id = id;
        this.longURL = longURL;
    }

    @Override
    public String toString() {
        return "TinyURLHolder{" +
                "id='" + id + '\'' +
                ", longURL='" + longURL + '\'' +
                '}';
    }

    public static ShortURLData instance(int idLength, String longURL) {
        return new ShortURLData(Util.createID(idLength), longURL);
    }
}
