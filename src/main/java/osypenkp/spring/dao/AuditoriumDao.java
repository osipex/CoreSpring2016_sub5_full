package osypenkp.spring.dao;

import osypenkp.spring.pojo.Auditorium;

/**
 * Created by pooh on 19.02.2016.
 *
 */

public interface AuditoriumDao {

    int create (Auditorium item);
    Auditorium get(int id);

}