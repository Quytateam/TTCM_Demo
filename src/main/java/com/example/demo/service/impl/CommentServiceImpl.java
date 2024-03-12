package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Users;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.request.CreateCommentRequest;
import com.example.demo.reponsitory.CommentRepository;
import com.example.demo.reponsitory.UsersRepository;
import com.example.demo.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

    // @Autowired
    // private ComicRepository comicRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UsersRepository usersRepository;
    

    @Override
    public Comment createComment(String namevsid, long userId, CreateCommentRequest request){
        String[] parts = namevsid.split("-");
        String lastPart = parts[parts.length - 1];
        long comicId = Long.parseLong(lastPart);
        // Comic comic = comicRepository.getComic(comicId);
        Users user =  usersRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Not Found User With Id: " + userId));
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setComicId(comicId);
        comment.setComment(request.getComment());
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        comment.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public void deleteComment(long userId, long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("Not Found Comment With Id: " + commentId));
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Not Found User With Id: " + userId));
        if(comment.getUser().getId() == userId || user.getRole().getName().toString() == "ROLE_ADMIN"){
            commentRepository.delete(comment);
        }else{
            throw new BadRequestException("Không thuộc thẩm quyền");
        }
    }
}
