/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pooh on 08.02.2016.
 *
 */
public class Event {

    private int id;
    public String name;
    private Date dateEvent;
    private float basePrice;
    private Auditorium auditorium;

    public Event(int id, String name, Date dateEvent, float basePrice, Auditorium auditorium) {
        this.id = id;
        this.name = name;
        this.dateEvent = dateEvent;
        this.basePrice = basePrice;
        this.auditorium = auditorium;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    @Override
    public String toString() {
       return name + " (" + auditorium.name + " " + new SimpleDateFormat("HH:mm dd.MM.yyyy").format(dateEvent) + ")";
    }
    
    
}
