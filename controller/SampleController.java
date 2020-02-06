package com.ygc.miaosha.controller;

import com.ygc.miaosha.domain.User;
import com.ygc.miaosha.redis.UserKey;
import com.ygc.miaosha.result.Result;
import com.ygc.miaosha.serivce.RedisService;
import com.ygc.miaosha.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","Lxy");
        return "hello";
    }


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> rdGet(){
        String value = redisService.get(UserKey.getById,"key2", String.class);
        return Result.success(value);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> rdSet(){
        boolean res = redisService.set(UserKey.getById, "key2", "sdad");
        return Result.success(res);
    }
}
