package ru.manasyan.model;

import java.util.Date;

public class Votes extends AbstractBaseEntity {

    private String user_id;

    private String restaurant_id;

    private Date date_time;

    public Votes(Integer id, String user_id, String restaurant_id, Date date_time) {
        super(id);
        this.user_id = user_id;
        this.restaurant_id = restaurant_id;
        this.date_time = date_time;
    }
}
