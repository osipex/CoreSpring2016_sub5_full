/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.services;

import osypenkp.spring.discounts.DiscountCase;
import osypenkp.spring.pojo.Event;
import osypenkp.spring.pojo.Ticket;
import osypenkp.spring.pojo.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by pooh on 20.02.2016.
 *
 */

public class DiscountService {

    private Collection<DiscountCase> listDiscounts;

    public DiscountService(Collection<DiscountCase> listDiscounts) {
        this.listDiscounts = listDiscounts;
    }


    public DiscountService() {
    }

    public Collection<DiscountCase> getListDiscounts() {
        return listDiscounts;
    }

    public void setListDiscounts(Collection<DiscountCase> listDiscounts) {
        this.listDiscounts = listDiscounts;
    }

    public float verifyDiscount(User user, Event event, List<Ticket> tickets) {
        if (user != null) {
            List<Float> percentDiscount = new CopyOnWriteArrayList<>();
            for (DiscountCase discount : listDiscounts) {
                percentDiscount.add(discount.getDiscount(user, event, tickets));
            }
            Collections.sort(percentDiscount);
            return percentDiscount.get(percentDiscount.size() - 1);
        }
        return 0;
    }

}
