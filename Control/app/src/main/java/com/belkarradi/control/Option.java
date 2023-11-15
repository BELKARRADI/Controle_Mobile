package com.belkarradi.control;

public class Option {

    private int id;
    private String name;

    public Option(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name; // Pour afficher le nom dans la liste déroulante
    }
}
