package com.techblog.backend.dao;

import com.techblog.backend.model.Post;
import com.techblog.backend.model.PostVote;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostDao {

  @Autowired PostVoteDao postVoteDao;

  public Optional<List<Post>> getPostsWithVotes(List<Post> posts) {
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
