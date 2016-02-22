/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.aspects;


import osypenkp.spring.pojo.Event;

import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by pooh on 20.02.2016.
 *
 */

@Aspect
public class CounterAspect {
    
    private boolean callTimeInterval;

    private Map<String, Integer> counter;

    public CounterAspect(boolean callTimeInterval) {
        this.callTimeInterval = callTimeInterval;
    }
    
     public Map<String, Integer> getCounter() {
        return counter;
    }

    @Pointcut("execution(* osypenkp.spring.services.BookingService.bookTicket(..))")
    private void allBookTicketMethods() {
    }

    @AfterReturning("allBookTicketMethods()")
    public void count(JoinPoint joinPoint) {
        if (counter == null) {
            counter = new HashMap<>();
        }
        addCounter("total tickets sold");

        Object[] args = joinPoint.getArgs();

        if (args != null)
        {
            //particular movie tickets count
            if(args[0] != null) {
             String nameEvent = "event: "+((Event) args[0]).getName();
             addCounter(nameEvent);
            }
            //discounted price count
            if(args[1] != null) {
             addCounter("personal price");
            }        
        }
    }

    private void addCounter(String key) {
        if (!counter.containsKey(key)) {
            counter.put(key, 0);
        }
        counter.put(key, counter.get(key) + 1);
    }
}
