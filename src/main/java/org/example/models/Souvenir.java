package org.example.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Souvenir implements Serializable {
    private String name;
    private LocalDate dateOfCreation;

    private int price;

    private Maker maker;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Maker getMaker() {
        return maker;
    }

    public void setMaker(Maker maker) {
        this.maker = maker;
    }

    public Souvenir(String name, LocalDate dateOfCreation, int price, Maker maker) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.price = price;
        this.maker = maker;
    }

    @Override
    public String toString() {
        return "Souvenir{" +
                "name='" + name + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", price=" + price +
                ", maker=" + maker +
                '}';
    }

    public void showInfo() {
        System.out.println("-------------------------------");
        System.out.println("Souvenir's name - " + getName());
        System.out.println("Souvenir's date of creation - "
                + getDateOfCreation().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("Souvenir's price - " + getPrice() + "$");
        System.out.println("Maker info:");
        maker.showInfo();
        System.out.println("-------------------------------");
    }
}
