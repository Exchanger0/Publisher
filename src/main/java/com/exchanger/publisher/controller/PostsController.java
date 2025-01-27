package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.PostDto;
import com.exchanger.publisher.dto.PostMainData;
import com.exchanger.publisher.model.Post;
import com.exchanger.publisher.model.User;
import com.exchanger.publisher.model.key.LDVID;
import com.exchanger.publisher.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/posts")
public class PostsController {

    private final PostService postService;
    private final LikeService likeService;
    private final DislikeService dislikeService;
    private final ViewsService viewsService;

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public PostsController(PostService postService, LikeService likeService, DislikeService dislikeService,
                           ViewsService viewsService) {
        this.postService = postService;
        this.likeService = likeService;
        this.dislikeService = dislikeService;
        this.viewsService = viewsService;
    }

    @GetMapping
    public String getPostsPage() {
        LOGGER.info("Received a GET request to url: /posts");

        return "posts/posts";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<PostMainData> getPostsData(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                             @RequestParam(value = "amount", required = false, defaultValue = "20") int amount) throws JsonProcessingException {
        LOGGER.info("Received a GET request to url: /posts/data");
        LOGGER.info("start={}, amount={}", start, amount);

        List<Post> posts = postService.findAll(PageRequest.of(start, amount));

        return posts.stream().map(PostMainData::new).toList();
    }

    @GetMapping("/{postId}")
    public String getPost(Model model, @PathVariable("postId") long postId, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a GET request to url: /posts/{}", postId);

        Post post = postService.findById(postId);

        boolean liked = false;
        boolean disliked = false;

        if (user != null) {
            viewsService.view(postId, user.getId());
            post = postService.save(post);
            liked = post.getLikes().stream().anyMatch(like -> like.getId().getUserId() == user.getId());
            disliked = post.getDislikes().stream().anyMatch(dislike -> dislike.getId().getUserId() == user.getId());
        }


        model.addAttribute("post", new PostDto(post));
        model.addAttribute("liked", liked);
        model.addAttribute("disliked", disliked);

        return "posts/post";
    }

    @PostMapping("/{postId}/like")
    @ResponseBody
    public Map<String, Object> likePost(@PathVariable("postId") long postId, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a POST request to url: /posts/{}/like", postId);
        LOGGER.info("Post id={}, user id={}", postId, user.getId());

        Post p1 = postService.findById(postId);

        boolean success = false;
        if (!dislikeService.existsById(new LDVID(postId, user.getId()))) {
            success = likeService.like(postId, user.getId());
            postService.refresh(p1);
        }

        return Map.of("likes", p1.getLikes().size(), "success", success);
    }

    @PostMapping("/{postId}/dislike")
    @ResponseBody
    public Map<String, Object> dislikePost(@PathVariable("postId") long postId, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a POST request to url: /posts/{}/dislike", postId);
        LOGGER.info("Post id={}, user id={}", postId, user.getId());

        Post p1 = postService.findById(postId);

        boolean success = false;
        if (!likeService.existsById(new LDVID(postId, user.getId()))) {
            success = dislikeService.dislike(postId, user.getId());
            postService.refresh(p1);
        }

        return Map.of("dislikes", p1.getDislikes().size(), "success", success);
    }

    @GetMapping("/create")
    public String getCreatePostForm(Model model) {
        LOGGER.info("Received a GET request to url: /posts/create");

        model.addAttribute("post", new Post());

        return "posts/create";
    }

    @PostMapping
    public String createPost(@RequestParam("title") String title, @RequestParam("tags") String tags,
                             @RequestParam("content") String content, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a POST request to url: /posts");

        Post post = new Post(user, null, title, content, LocalDate.now(), List.of(tags.split(" ")));
        postService.save(post);

        LOGGER.info("Success create new post");

        return "redirect:/posts";
    }
}
