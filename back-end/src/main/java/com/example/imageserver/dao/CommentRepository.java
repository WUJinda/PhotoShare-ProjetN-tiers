package com.example.imageserver.dao;

import com.example.imageserver.entity.Comment;
import com.example.imageserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByImageId(Integer id);

}
