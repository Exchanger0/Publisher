package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.CommentFull;
import com.exchanger.publisher.dto.CommentMini;
import com.exchanger.publisher.dto.PostMini;
import com.exchanger.publisher.model.Comment;
import com.exchanger.publisher.model.User;
import com.exchanger.publisher.service.CommentService;
import com.exchanger.publisher.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentsController {
    private final CommentService commentService;
    private final PostService postService;

    private final static Logger LOGGER = LoggerFactory.getLogger(CommentsController.class);

    @Autowired
    public CommentsController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping
    @ResponseBody
    public List<CommentFull> getCommnetsByFieldId(
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
            @RequestParam("field") String field,
            @RequestParam("id") long id) {
        LOGGER.info("Received a GET request to url: /comments");
        LOGGER.info("start={} filed={} id={}", start, field, id);

        List<Comment> comments = new ArrayList<>();
        switch (field.toLowerCase()) {
            case "id" -> comments.add(commentService.findById(id));
            case "postid" -> comments = commentService.findTopLevelByPostId(id, PageRequest.of(start, amount));
            case "parentid" -> comments = commentService.findAllByParentId(id, PageRequest.of(start, amount));
        }

        return comments.stream().map(CommentFull::new).toList();
    }

    @GetMapping("/create")
    public String getCreateCommentForm(Model model, @RequestParam("postId") long postId, @RequestParam("parentId") long parentId) {
        LOGGER.info("Received a GET request to url: /comments/create?postId={}&parentId={}", postId, parentId);

        CommentFull newComment = new CommentFull();
        newComment.setPost(new PostMini(postService.findById(postId)));
        if (parentId != -1) {
            newComment.setParent(new CommentMini(commentService.findById(parentId)));
        }
        model.addAttribute("comment", newComment);

        return "comments/create";
    }

    @PostMapping
    public String createComment(@ModelAttribute("comment") CommentFull commentDto, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a POST request to url: /comments");
        LOGGER.info("postId={} parentId={}", commentDto.getPost().getId(), commentDto.getParent() == null ? null : commentDto.getParent().getId());

        Comment comment = new Comment(commentDto.getContent(), user,
                postService.findById(commentDto.getPost().getId()),
                commentDto.getParent() == null ? null : commentService.findById(commentDto.getParent().getId()));
        commentService.save(comment);

        return "redirect:/posts/" + comment.getPost().getId();
    }
}
