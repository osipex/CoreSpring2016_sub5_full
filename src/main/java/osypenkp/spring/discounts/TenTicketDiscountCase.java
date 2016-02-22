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

public class TenTicketDiscountCase implements DiscountCase {

    float percent;

    @Override
    public float getPercent() {
        return percent;
    }

    @Override
    public void setPercent(float percent) {
        this.percent = percent;
    }

    @Override
    public float getDiscount(User user, Event event, List<Ticket> tickets) {
        if (tickets != null && user != null) {
           int countTickets = 0;
            for (Ticket ticket : tickets) {
                if (ticket.getUser()!=null && ticket.getUser().equals(user)) {
                    countTickets++;
                    if (countTickets > 10) {
                        return percent;
                    }
                }
            }
        }
        return 0;
    }

}
