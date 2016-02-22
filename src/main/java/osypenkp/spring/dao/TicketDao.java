package osypenkp.spring.dao;

import osypenkp.spring.pojo.Event;
import osypenkp.spring.pojo.Ticket;
import osypenkp.spring.pojo.User;

import java.util.List;

/**
 * Created by pooh on 19.02.2016.
 *
 */
public interface TicketDao {

    int create (Ticket item);
    Ticket get(int id);

    List<Ticket> findAll();
    List<Integer> bookedSeats(Event event);
    List<Ticket> ticketsForUser(User user);

}