/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.discounts;

import osypenkp.spring.pojo.Event;
import osypenkp.spring.pojo.Ticket;
import osypenkp.spring.pojo.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by pooh on 08.02.2016.
 *
 */
public class BirthdayDiscountCase implements DiscountCase {

    float percent;

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    @Override
    public float getDiscount(User user, Event event, List<Ticket> tickets) {
        if (user != null && event != null) {
            Date dateBirth = user.getDateBirth();
            Date dateEvent = event.getDateEvent();
            SimpleDateFormat df = new SimpleDateFormat("dd.MM");
            if (df.format(dateBirth).equals(df.format(dateEvent))) {
                return percent;
            }
        }
        return 0;
    }
}
