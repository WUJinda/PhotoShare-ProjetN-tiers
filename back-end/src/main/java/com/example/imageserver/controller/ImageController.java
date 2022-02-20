package com.example.imageserver.controller;

import com.example.imageserver.BaseResponse;
import com.example.imageserver.dao.CommentRepository;
import com.example.imageserver.dao.ImageRepository;
import com.example.imageserver.dao.UserRepository;
import com.example.imageserver.entity.Comment;
import com.example.imageserver.entity.Image;
import com.example.imageserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("image")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("upload")
    public BaseResponse upload(MultipartFile[] file, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        List<Image> images = new ArrayList<>();

        File dir = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "uploads/");

        for (MultipartFile f : file) {
            String fileName = UUID.randomUUID() + ".jpg";
            File newFile = new File(dir, fileName);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }

            f.transferTo(newFile);

            Image image = new Image();
            image.setName(f.getOriginalFilename());
            image.setFile("/upload/"  + fileName);
            image.setUserId(user.getId());
            images.add(image);
        }

        imageRepository.save(images);

        return BaseResponse.ok();
    }

    @GetMapping("list")
    public BaseResponse list(HttpSession session) {
        User user = userRepository.getOne(((User) session.getAttribute("user")).getId());
        List<Image> images = user.getImages();
        return BaseResponse.ok(images);
    }

    @PostMapping("addComment")
    public BaseResponse addComment(@RequestBody  Comment comment) {
        comment.setCreateTime(new Date());
        commentRepository.save(comment);
        return BaseResponse.ok();
    }

    @GetMapping("getComments")
    public BaseResponse getComment(Integer id) {
        return BaseResponse.ok(commentRepository.findByImageId(id));
    }


    @PostMapping("update")
    public BaseResponse update(Integer imageId,MultipartFile file) throws IOException {
        File dir = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "uploads/");
        String fileName = UUID.randomUUID() + ".jpg";
        File newFile = new File(dir, fileName);
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        file.transferTo(newFile);

        Image image = imageRepository.getOne(imageId);
        image.setName(file.getOriginalFilename());
        image.setFile("/upload/"  + fileName);

        imageRepository.save(image);

        return BaseResponse.ok();
    }

    @PostMapping("delete")
    public BaseResponse delete(Integer imageId) {
        imageRepository.delete(imageId);
        return BaseResponse.ok();
    }


}
