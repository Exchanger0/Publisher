package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.CommentDto;
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
    public List<CommentDto> getCommnetsByFieldId(
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

        return comments.stream().map(CommentDto::new).toList();
    }

    @GetMapping("/create")
    public String getCreateCommentForm(Model model, @RequestParam("postId") long postId, @RequestParam("parentId") long parentId) {
        LOGGER.info("Received a GET request to url: /comments/create?postId={}&parentId={}", postId, parentId);

        model.addAttribute("postId", postId);
        model.addAttribute("postTitle", postService.findById(postId).getTitle());
        if (parentId != -1) {
            model.addAttribute("parentComment", new CommentDto(commentService.findById(parentId)));
        }


        return "comments/create";
    }

    @PostMapping
    public String createComment(@RequestParam("postId") long postId,
                                @RequestParam(value = "parentId", required = false, defaultValue = "-1") long parentId,
                                @RequestParam("content") String content, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a POST request to url: /comments");
        LOGGER.info("postId={} parentId={}", postId, parentId);

        Comment comment = new Comment(content, user, postService.findById(postId),
                parentId == -1 ? null : commentService.findById(parentId));
        commentService.save(comment);

        return "redirect:/posts/" + postId;
    }
}
