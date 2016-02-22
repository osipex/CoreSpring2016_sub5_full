package osypenkp.spring.dao;

import osypenkp.spring.pojo.Event;
import osypenkp.spring.pojo.Ticket;
import osypenkp.spring.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Created by pooh on 19.02.2016.
 *
 */
@Repository
public class TicketDaoImpl implements TicketDao {

    JdbcTemplate jdbcTemplate;
    UserDao users;
    EventDao events;

    public TicketDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setUsers(UserDao users) {
        this.users = users;
    }

    public void setEvents(EventDao events) {
        this.events = events;
    }

    @Override
    public List<Integer> bookedSeats(Event event) {
        List<Integer> events = jdbcTemplate.query(
                "select seat from tickets where idevent = ?",
                new Object[]{event.getId()},
                new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs,
                            int rowNum) throws SQLException {
                        return rs.getInt("seat");
                    }
                });
        return events;
    }

    @Override
    public List<Ticket> ticketsForUser(final User user) {
        Integer iduser = (user != null) ? user.getId() : -1; //-1 - не зарегистрированный покупатель
        List<Ticket> tickets = jdbcTemplate.query(
                "select * from tickets where iduser = ?",
                new Object[]{iduser},
                new RowMapper<Ticket>() {
                    public Ticket mapRow(ResultSet rs,
                            int rowNum) throws SQLException {
                        Event event = events.get(rs.getInt("idevent"));
                        return new Ticket(rs.getInt("id"), rs.getString("name"), event, user, rs.getInt("seat"), rs.getFloat("price"));
                    }
                });
        return tickets;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = jdbcTemplate.query(
                "select * from tickets",
                new RowMapper<Ticket>() {
                    public Ticket mapRow(ResultSet rs,
                            int rowNum) throws SQLException {
                        Event event = events.get(rs.getInt("idevent"));
                        User user = users.get(rs.getInt("iduser"));
                        return new Ticket(rs.getInt("id"), rs.getString("name"), event, user, rs.getInt("seat"), rs.getFloat("price"));
                    }
                });
        return tickets;
    }
    
    @Override
    public int create(final Ticket item) {
        final String sql = "insert into tickets (name, seat, price, idevent, iduser) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        pst.setString(1, item.getName());
                        pst.setInt(2, item.getSeat());
                        pst.setFloat(3, item.getPrice());
                        pst.setInt(4, item.getEvent().getId());
                        if (item.getUser() != null)
                            pst.setInt(5, item.getUser().getId());
                        else
                            pst.setInt(5, -1); //-1 id of user that not booked

                        return pst;
                    }
                },
                keyHolder);
        int key = (int) keyHolder.getKey();
        return key;
    }

    @Override
    public Ticket get(int id) {
        Ticket ticket = jdbcTemplate.queryForObject(
                "select * from tickets where id = ?",
                new Object[]{id},
                new RowMapper<Ticket>() {
                    public Ticket mapRow(ResultSet rs,
                            int rowNum) throws SQLException {
                        User user = users.get(rs.getInt("iduser"));
                        Event event = events.get(rs.getInt("idevent"));
                        Ticket newTicket = new Ticket(rs.getInt("id"), rs.getString("name"), event, user, rs.getInt("seat"), rs.getFloat("price"));
                        return newTicket;
                    }
                });
        return ticket;
    }
}
