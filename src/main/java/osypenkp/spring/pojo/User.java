/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.pojo;

import java.util.Date;

/**
 * Created by pooh on 20.02.2016.
 *
 */
public class User{

    private int id;
    public String name;
    private Date dateBirth;

    public User(int id, String name, Date dateBirth, int state) {
        this.id = id;
        this.name = name;
        this.dateBirth = dateBirth;
        
    }

    public int getId() {
        return id;
    }

    
    public Date getDateBirth() {
        return dateBirth;
    }

    public String getName() {
        return name;
    }
    
    
}
