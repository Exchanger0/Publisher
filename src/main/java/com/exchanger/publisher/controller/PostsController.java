package com.exchanger.publisher.controller;

import com.exchanger.publisher.dto.GroupMini;
import com.exchanger.publisher.dto.PostFull;
import com.exchanger.publisher.dto.PostMini;
import com.exchanger.publisher.model.*;
import com.exchanger.publisher.model.key.LDVID;
import com.exchanger.publisher.service.*;
import jakarta.persistence.EntityNotFoundException;
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
    private final UserGroupService userGroupService;
    private final GroupService groupService;

    private final static Logger LOGGER = LoggerFactory.getLogger(PostsController.class);

    @Autowired
    public PostsController(PostService postService, LikeService likeService, DislikeService dislikeService,
                           ViewsService viewsService, UserGroupService userGroupService, GroupService groupService) {
        this.postService = postService;
        this.likeService = likeService;
        this.dislikeService = dislikeService;
        this.viewsService = viewsService;
        this.userGroupService = userGroupService;
        this.groupService = groupService;
    }

    @GetMapping
    public String getPostsPage() {
        LOGGER.info("Received a GET request to url: /posts");

        return "posts/posts";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<PostMini> getPostsData(
            @RequestParam(value = "start", required = false, defaultValue = "0") int start,
            @RequestParam(value = "amount", required = false, defaultValue = "20") int amount,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "groupId", required = false) Integer groupId) {
        LOGGER.info("Received a GET request to url: /posts/data");
        LOGGER.info("start={}, amount={}, title={}, tag={}, groupId={}", start, amount, title, tag, groupId);

        List<Post> posts;
        posts = postService.findBy(title, tag, groupId, PageRequest.of(start, amount));

        return posts.stream().map(PostMini::new).toList();
    }

    @GetMapping("/{postId}")
    public String getPost(Model model, @PathVariable("postId") long postId, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a GET request to url: /posts/{}", postId);

        Post post = postService.findById(postId);
        if (post == null) {
            throw new EntityNotFoundException("Post with id=" + postId + " not found");
        }

        boolean liked = false;
        boolean disliked = false;

        if (user != null) {
            viewsService.view(postId, user.getId());
            post = postService.save(post);
            liked = post.getLikes().stream().anyMatch(like -> like.getId().getUserId() == user.getId());
            disliked = post.getDislikes().stream().anyMatch(dislike -> dislike.getId().getUserId() == user.getId());
        }

        model.addAttribute("post", new PostFull(post));
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
    public String getCreatePostForm(Model model, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a GET request to url: /posts/create");

        List<UserGroup> userGroups = userGroupService.findAllByIdUserIdAndRoleIn(user.getId(), List.of(UserRole.CREATOR
                , UserRole.ADMIN, UserRole.WRITER));
        List<Group> groups = groupService.findAllById(userGroups.stream().map(ug -> ug.getId().getGroupId()).toList());
        model.addAttribute("groups", groups.stream().map(GroupMini::new).toList());

        return "posts/create";
    }

    @PostMapping
    public String createPost(@RequestParam("title") String title, @RequestParam("tags") String tags,
                             @RequestParam("content") String content, @RequestParam("group") long groupId,
                             @AuthenticationPrincipal User user) {
        LOGGER.info("Received a POST request to url: /posts");


        Post post = new Post(user, groupService.findById(groupId), title, content, LocalDate.now(), List.of(tags.split(" ")));
        postService.save(post);

        LOGGER.info("Success create new post");

        return "redirect:/posts";
    }

    @GetMapping("/{postId}/edit")
    public String getEditPostForm(Model model, @PathVariable("postId") long postId, @AuthenticationPrincipal User user) {
        LOGGER.info("Received a GET request to url: /posts/{}/edit", postId);

        Post post = postService.findById(postId);
        if (post.getAuthor().equals(user)) {
            model.addAttribute("post", new PostMini(post));
            return "posts/edit";
        } else {
            model.addAttribute("message", "Access denied");
            return "error";
        }
    }

    @PutMapping("/{postId}")
    public String updatePost(@PathVariable("postId") long postId, @RequestParam("title") String title,
                             @RequestParam("tags") String tags,
                             @RequestParam("content") String content) {
        LOGGER.info("Received a PUT request to url: /posts/{}", postId);

        Post post = postService.findById(postId);
        post.setTitle(title);
        post.setTags(List.of(tags.split(" ")));
        post.setContent(content);
        postService.save(post);

        return "redirect:/account";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") long postId) {
        LOGGER.info("Received a DELETE request to url: /posts/{}", postId);

        postService.deleteById(postId);

        return "redirect:/account";
    }
}
