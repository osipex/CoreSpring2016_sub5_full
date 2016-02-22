package osypenkp.spring.main;

import osypenkp.spring.aspects.CounterAspect;
import osypenkp.spring.aspects.CountersDiscount;
import osypenkp.spring.aspects.DiscountAspect;
import osypenkp.spring.dao.AuditoriumDao;
import osypenkp.spring.dao.EventDao;
import osypenkp.spring.dao.TicketDao;
import osypenkp.spring.dao.UserDao;
import osypenkp.spring.pojo.Auditorium;
import osypenkp.spring.pojo.Event;
import osypenkp.spring.pojo.Ticket;
import osypenkp.spring.pojo.User;
import osypenkp.spring.services.BookingService;
import osypenkp.spring.services.DiscountService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by pooh on 20.02.2016.
 *
 */

public class AppDemo {

    private UserDao users;
    private AuditoriumDao auditoriums;
    private EventDao events;
    private TicketDao tickets;

    private DiscountService discountService;
    private BookingService bookingService;

    static ConfigurableApplicationContext ctx;
    static AppDemo appDemo;

    public AppDemo(UserDao users, AuditoriumDao auditoriums, EventDao events, TicketDao tickets) {
        this.users = users;
        this.auditoriums = auditoriums;
        this.events = events;
        this.tickets = tickets;
    }

    public DiscountService getDiscountService() {
        return discountService;
    }

    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void init() throws ParseException {

        //adding Auditoriums
        appDemo.auditoriums.create(new Auditorium(1, "Stage A", 35, Arrays.asList(new Integer[]{10,11,12,13,14,15})));
        appDemo.auditoriums.create(new Auditorium(2, "Stage B", 50, Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,10})));
        appDemo.auditoriums.create(new Auditorium(3, "Stage C", 40, Arrays.asList(new Integer[]{35,36,37,38,39,40})));

        //adding Events
        DateFormat dfEvent = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        appDemo.events.create(new Event(1, "Star Wars: Episode 7", dfEvent.parse("21.02.2016 9:00"), 200, appDemo.auditoriums.get(1)));
        appDemo.events.create(new Event(2, "Star Wars: Episode 7", dfEvent.parse("21.02.2016 13:00"), 200, appDemo.auditoriums.get(1)));
        appDemo.events.create(new Event(3, "Mission U.N.K.L.E", dfEvent.parse("21.02.2016 17:00"), 100, appDemo.auditoriums.get(1)));
        appDemo.events.create(new Event(4, "Mission U.N.K.L.E", dfEvent.parse("21.02.2016 10:00"), 100, appDemo.auditoriums.get(2)));
        appDemo.events.create(new Event(5, "Machete!", dfEvent.parse("21.02.2015 18:00"), 75, appDemo.auditoriums.get(0)));
        appDemo.events.create(new Event(6, "Walking Dead. The Movie", dfEvent.parse("21.02.2015 22:00"), 75, appDemo.auditoriums.get(2)));

        //adding Users
        DateFormat dfBirth = new SimpleDateFormat("dd.MM.yyyy");
        appDemo.users.create(new User(1, "Petro Osypenko", dfBirth.parse("09.02.1988"), 1));
        appDemo.users.create(new User(2, "Oksana Bobr", dfBirth.parse("01.05.2002"), 1));
        appDemo.users.create(new User(3, "Vova Motornyy", dfBirth.parse("05.11.1992"), 1));
        appDemo.users.create(new User(4, "Nastya Osypenko", dfBirth.parse("07.08.1990"), 1));
        appDemo.users.create(new User(5, "Alex Palamarenko", dfBirth.parse("05.07.1988"), 1));
    }

    public void generateTestDatabaseTickets(int ticketsCount) {
        Random rand = new Random();

        for (int i = 0; i <= ticketsCount; i++) {
            //randomly generates users
            User user = users.get(rand.nextInt(5));
            //random tickets purchase by users from rand.nextInt(n) events
            Event event = events.get(rand.nextInt(6));

            Auditorium a = auditoriums.get(event.getAuditorium().getId());
            int seat = 0;
            List<Integer> lockSeats = tickets.bookedSeats(event);
            do {
                seat = rand.nextInt(a.getCountSeats());
            } while (lockSeats != null && lockSeats.contains(seat));

            Set<Integer> seats = new HashSet<>();
            seats.add(seat);
            bookingService.bookTicket(event, user, seats);
        }
    }

    public static void main(String[] args) throws ParseException, IOException {

        ctx = new ClassPathXmlApplicationContext(new String[]{"spring-context.xml"});
        appDemo = (AppDemo) ctx.getBean("app");
        appDemo.init();
        appDemo.generateTestDatabaseTickets(100);

        System.out.println("\n -------------------------- Purchased Tickets -------------------------- ");
        int ticket = 0;
        List<Ticket> listTickets = appDemo.tickets.findAll();
        for (Ticket t : listTickets) {
            if (t.getUser() != null)
                System.out.println(++ticket + ". " + t.getEvent() + " - User: " + t.getUser().getName() + " - Seat #" + t.getSeat() + ",  ticket price: " + t.getPrice());
            else
                System.out.println(++ticket + ". " + t.getEvent() + " - User: " + null + " - Seat #" + t.getSeat() + ",  ticket price: " + t.getPrice());
        }


        System.out.println("\n -------------------------- User Stats -------------------------- ");
        ticket = 0;
        //get(0) - user ID
        List<Ticket> userTickets = appDemo.tickets.ticketsForUser(appDemo.users.get(0));
        for (Ticket t : userTickets) {
            if (t.getUser() != null)
                System.out.println(++ticket + ". " + t.getEvent() + " >> " + t.getUser().getName() + " - Seat #" + t.getSeat() + ",  ticket price: " + t.getPrice());
            else
                System.out.println(++ticket + ". " + t.getEvent() + " >> " + null + " - Seat #" + t.getSeat() + ",  ticket price: " + t.getPrice());
        }

        System.out.println("\n -------------------------- Counter Aspect Demo --------------------------");
        CounterAspect counterAspect = (CounterAspect) ctx.getBean("counterAspect");
        for (Map.Entry<String, Integer> e : counterAspect.getCounter().entrySet()) {
            System.out.print(e.getKey());
            System.out.println(" >> " + e.getValue() + " tickets purchased");
        }

        System.out.println("\n -------------------------- Discount Aspect Demo --------------------------");
        DiscountAspect discountAspect = (DiscountAspect) ctx.getBean("discountAspect");
        for (Map.Entry<String, CountersDiscount> e : discountAspect.getCounter().entrySet()) {
            CountersDiscount countersDiscount = e.getValue();
            System.out.print(e.getKey());
            System.out.println(" >> Total discount usages: " + countersDiscount.getCount() + ". Total discount amount " + countersDiscount.getSum());
        }

        System.in.read();

        ctx.close();
    }

}
