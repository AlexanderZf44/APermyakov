package ru.apermyakov.mapping.car;

public class Transmission {

    private int id;

    private String type;

    private String description;

    public Transmission() {
    }

    public Transmission(int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}