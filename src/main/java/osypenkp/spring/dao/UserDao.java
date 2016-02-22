package osypenkp.spring.dao;

import osypenkp.spring.pojo.User;

/**
 * Created by pooh on 19.02.2016.
 *
 */

public interface UserDao {
    int create (User item);
    User get(int id);
}