package com.test.tinyurl.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

import static com.test.tinyurl.util.Util.createID;

@Table("tinyurldata")
public class TinyURLData implements Serializable {
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

    public TinyURLData(String id, String longURL) {
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

    public static TinyURLData instance(int idLength, String longURL) {
        return new TinyURLData(createID(idLength), longURL);
    }
}
