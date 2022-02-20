package com.example.imageserver.controller;


import com.example.imageserver.BaseResponse;
import com.example.imageserver.dao.UserRepository;
import com.example.imageserver.entity.Comment;
import com.example.imageserver.entity.Image;
import com.example.imageserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("user")
@RestController
public class Logincontroller {

    @Autowired
    UserRepository userRepository;

    @PostMapping("login")
    public BaseResponse login(@RequestBody User user, HttpSession session) throws Exception {
        User u = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (u != null) {
            session.setAttribute("user", u);
        } else {
            throw new Exception("账号或密码错误!");
        }
        return BaseResponse.ok(u);
    }

    @GetMapping("info")
    public BaseResponse info(String name,String comment, HttpSession session) {
        User user = userRepository.getOne(((User) session.getAttribute("user")).getId());
        if (!StringUtils.isEmpty(name)){
            List<Image> images = user.getImages().stream().filter(image -> image.getName().contains(name)).collect(Collectors.toList());
            user.setImages(images);
        }
        if (!StringUtils.isEmpty(comment)) {
            List<Image> images = user.getImages().stream().filter(image -> {
                for (Comment imageComment : image.getComments()) {
                    if (imageComment.getRemark().contains(comment)) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());
            user.setImages(images);
        }
        return BaseResponse.ok(user);
    }

    @PostMapping("logout")
    public BaseResponse logout(HttpSession session) {
        session.setAttribute("user", null);
        return BaseResponse.ok();
    }


    @PostMapping("add")
    public BaseResponse register(@RequestBody User user) throws Exception {
        userRepository.save(user);
        return BaseResponse.ok();
    }



}
