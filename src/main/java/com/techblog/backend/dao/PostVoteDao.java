package com.techblog.backend.dao;

import com.techblog.backend.model.Post;
import com.techblog.backend.model.PostVote;
import com.techblog.backend.model.PostVotePK;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.PostVoteRepository;
import com.techblog.backend.types.vote.PostVoteType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostVoteDao {

  @Autowired PostVoteRepository postVoteRepository;

  public void getVotesByUserId(Long userId) {}

  public Optional<PostVote> getVoteByPK(Long postId, Long userId) {
    return postVoteRepository.findById(new PostVotePK(postId, userId));
  }

  public Optional<List<PostVote>> getVotesByPostIds(List<Long> ids) {
    List<PostVote> result = postVoteRepository.findByPostIdIn(ids);
    if (result == null || result.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(result);
  }

  public Optional<HashMap<Long, List<PostVote>>> getPostsVotesMap(List<Long> postIds) {
    Optional<List<PostVote>> votesOptional = getVotesByPostIds(postIds);
    return votesOptional.map(postVotes -> setPostVotesMap(postVotes, postIds));
  }

  public void deleteVote(PostVote vote) {
    postVoteRepository.delete(vote);
  }

  public void deleteVoteByPK(Long postId, Long userId) {
    postVoteRepository.deleteById(new PostVotePK(postId, userId));
  }

  public PostVote createVote(Post post, User user, PostVoteType voteType) {
    return postVoteRepository.save(new PostVote(post, user, voteType));
  }

  private HashMap<Long, List<PostVote>> setPostVotesMap(List<PostVote> votes, List<Long> postIds) {
    HashMap<Long, List<PostVote>> votesMap = new HashMap<>();
    postIds.forEach(id -> votesMap.put(id, new ArrayList<>()));
    votes.forEach(
        vote -> {
          Long postId = vote.getPost().getId();
          List<PostVote> postVotes = votesMap.get(postId);
          postVotes.add(vote);
          votesMap.put(postId, postVotes);
        });
    return votesMap;
  }
}
