/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.pojo;

import java.util.Collection;

/**
 * Created by pooh on 08.02.2016.
 *
 */
public class Auditorium {

    private int id;
    public String name;
    private int countSeats;
    private Collection<Integer> vipSeats;

    public Auditorium(int id, String name, int countSeats, Collection<Integer> vipSeats) {
        this.id = id;
        this.name = name;
        this.countSeats = countSeats;
        this.vipSeats = vipSeats;
    }
    
    public Collection<Integer> getVipSeats() {
        return vipSeats;
    }

    public int getCountSeats() {
        return countSeats;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    
    
}
