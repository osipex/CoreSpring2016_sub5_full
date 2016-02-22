/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osypenkp.spring.aspects;

import osypenkp.spring.pojo.Event;
import osypenkp.spring.pojo.User;
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
public class DiscountAspect {

 
    private Map<String, CountersDiscount> counter;
    
    public DiscountAspect() {
            counter = new HashMap<>();
    }

    public Map<String, CountersDiscount> getCounter() {
        return counter;
    }
    
    @Pointcut("execution(* osypenkp.spring.services.DiscountService.verifyDiscount(..))")
    private void allVerifyDiscountMethods() {
    }
     

    @AfterReturning(pointcut = "allVerifyDiscountMethods()", returning = "result")
    public void count(JoinPoint joinpoint, float result) {

        Object[] args = joinpoint.getArgs();
        if (args != null && args[1] != null && args[0] != null) {
            String nameUser = ((User) args[0]).getName();
            Event event = (Event) args[1];
            float sumDiscount = event.getBasePrice() * result / 100;
            if (sumDiscount != 0) {
                if (!counter.containsKey(nameUser)) {
                    counter.put(nameUser, new CountersDiscount(0, 0));
                }
                CountersDiscount old = counter.get(nameUser);
                CountersDiscount newCountersDiscount = new CountersDiscount(old.getCount() + 1, old.getSum() + sumDiscount);
                counter.put(nameUser, newCountersDiscount);

            }
        }
    }
}
