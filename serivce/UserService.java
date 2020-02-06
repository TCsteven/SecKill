package com.ygc.miaosha.serivce;

import com.ygc.miaosha.dao.UserDao;
import com.ygc.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }
}
