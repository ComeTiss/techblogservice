package com.techblog.backend.dao;

import com.techblog.backend.model.Post;
import com.techblog.backend.model.PostVote;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.PostRepository;
import com.techblog.backend.repository.UserRepository;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostDao {

  @Autowired PostVoteDao postVoteDao;
  @Autowired PostRepository postRepository;
  @Autowired UserRepository userRepository;

  public Post updatePost(LinkedHashMap requestData) {
    String title = requestData.get("title").toString();
    String description = requestData.get("description").toString();
    Long postId = Long.valueOf(requestData.get("id").toString());

    Post currentPost = postRepository.getOne(postId);
    currentPost.setDescription(description);
    currentPost.setTitle(title);
    currentPost.setUpdatedAt(Instant.now());
    return postRepository.save(currentPost);
  }

  public Optional<Post> createPost(LinkedHashMap requestData) {
    String title = requestData.get("title").toString();
    String description = requestData.get("description").toString();
    Object authorId = requestData.get("authorId");
    if (authorId == null) {
      return Optional.empty();
    }
    User author = userRepository.getOne(Long.valueOf(authorId.toString()));
    if (author == null) {
      return Optional.empty();
    }
    Post postCreated = postRepository.save(new Post(title, description, author));
    return Optional.of(postCreated);
  }

  public Optional<List<Post>> getPostsWithFilters(LinkedHashMap filters) {
    List<Post> posts = new ArrayList<>();
    if (filters != null) {
      Long authorId = Long.valueOf(filters.get("authorId").toString());
      posts = postRepository.getPostsByAuthorId(authorId);
    } else {
      postRepository.findAll();
    }
    return getPostsWithVotes(posts);
  }

  public Optional<List<Post>> deletePostsByIds(List<Long> postIds) {
    List<Post> postsToDelete = postRepository.findAllById(postIds);
    if (postsToDelete.isEmpty()) {
      return Optional.empty();
    }
    postRepository.deleteInBatch(postsToDelete);
    return Optional.of(postsToDelete);
  }

  private Optional<List<Post>> getPostsWithVotes(List<Post> posts) {
    List<Post> postsWithVotes = new ArrayList<>(posts);
    List<Long> postIds = postsWithVotes.stream().map(Post::getId).collect(Collectors.toList());
    Optional<HashMap<Long, List<PostVote>>> votesMapOpt = postVoteDao.getPostsVotesMap(postIds);
    if (votesMapOpt.isPresent() && !votesMapOpt.get().isEmpty()) {
      postsWithVotes.forEach(
          post -> post.setVotes(new HashSet<>(votesMapOpt.get().get(post.getId()))));
    }
    return Optional.of(postsWithVotes);
  }
}
