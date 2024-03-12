package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.model.request.CreateCommentRequest;

public interface CommentService {

    Comment createComment(String namevsid, long userId, CreateCommentRequest request);

    void deleteComment(long userId, long commentId);
}
