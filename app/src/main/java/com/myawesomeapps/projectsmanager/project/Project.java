package com.myawesomeapps.projectsmanager.project;

public class Project {
    private String mName;
    private long mId;

    public Project(String name, long id) {
        mName = name;
        mId = id;
    }

    void setName(String name) {
        mName = name;
    }

    String getName() {
        return mName;
    }

    void setId(long id) {
        mId = id;
    }

    long getId() {
        return mId;
    }
}
