/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.pojo;

import java.util.Random;

/**
 * Created by pooh on 20.02.2016.
 *
 */

public class Ticket{

    private int id;
    public String name;
    private Event event;
    private User user;
    private int seat;
    private float price;
    
    public Ticket(Event event, User user, int seat, float price) {
        Random random = new Random();
        this.id = random.nextInt();
        this.name = "";
        this.event = event;
        this.user = user;
        this.seat = seat;
        this.price = price;
    }

    public Ticket(int id, String name, Event event, User user, int seat, float price) {
        if(id<=0)
        {
            Random random = new Random();
            id=random.nextInt();
        }
        this.id = id;
        this.name = name;
        this.event = event;
        this.user = user;
        this.seat = seat;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Event getEvent() {
        return event;
    }

    public int getSeat() {
        return seat;
    }

    public User getUser() {
        return user;
    }

    public float getPrice() {
        return price;
    }
    
    
    
}
