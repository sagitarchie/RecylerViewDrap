package org.archie.app.recyclerviewdragdrop;


import java.io.Serializable;

/**
 * app info bean
 * Created by archie on 15/12/26.
 */
public class AppInfo {
    private int id;
    //app name
    private String title;
    //app packageName
    private String packageName;

    public AppInfo(int id, String title, String packageName) {
        this.id = id;
        this.title = title;
        this.packageName = packageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
