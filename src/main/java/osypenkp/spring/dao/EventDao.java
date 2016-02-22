package osypenkp.spring.dao;

import osypenkp.spring.pojo.Event;

/**
 * Created by pooh on 19.02.2016.
 *
 */

public interface EventDao {

    int create(Event item);
    Event get(int id);
}
