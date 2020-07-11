package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class UserDaoService {

    private static ArrayList<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Lee", new Date(), "pass1", "901025-1010101"));
        users.add(new User(2, "Han", new Date(), "pass2", "901125-2020101"));
        users.add(new User(3, "Kin", new Date(), "pass3", "901225-3030101"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public User update(User user){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User findUser = iterator.next();
            if (findUser.getId() == user.getId()){
                findUser.setName(user.getName());
                findUser.setJoinDate(user.getJoinDate());
                return findUser;
            }
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
