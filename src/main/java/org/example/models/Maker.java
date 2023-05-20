package org.example.models;

import java.io.Serializable;
import java.util.Objects;

public class Maker implements Serializable {
    private String name;
    private String country;

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public Maker(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maker maker = (Maker) o;
        return Objects.equals(name, maker.name) && Objects.equals(country, maker.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }

    public void showInfo() {
        System.out.println("Maker's name - " + getName());
        System.out.println("Maker's country - " + getCountry());
    }
}
