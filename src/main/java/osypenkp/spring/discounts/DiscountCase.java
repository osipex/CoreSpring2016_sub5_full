/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.discounts;

import osypenkp.spring.pojo.Event;
import osypenkp.spring.pojo.Ticket;
import osypenkp.spring.pojo.User;

import java.util.List;

/**
 * Created by pooh on 08.02.2016.
 *
 */
public interface DiscountCase {

    float getDiscount(User user, Event event, List<Ticket> tickets);
    float getPercent();
    void setPercent(float percent);

}
