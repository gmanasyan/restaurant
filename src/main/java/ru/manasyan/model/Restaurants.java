package ru.manasyan.model;

import java.util.Date;

public class Restaurants extends AbstractBaseEntity {

    private String name;

    private Date date_time;

    private Date menu_published;

    public Restaurants (Integer id, String name, Date date_time, Date menu_published) {
        super(id);
        this.name = name;
        this.date_time = date_time;
        this.menu_published = menu_published;
    }
}
