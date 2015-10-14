package com.epam.hem.tests.entities;

/**
 * Created by Pavel_Shcherbakov1 on 2015-10-09
 */
public class Project {
    public static Project DEFAULT_USER = new Project();

    private String name = "projectName";

    private String notes = "projectNotes";
    private String model = "projectModel";

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Project() {
    }

    public Project(String name, String notes, String model) {
        this.name = name;
        this.notes = notes;
        this.model = model;
    }
}
