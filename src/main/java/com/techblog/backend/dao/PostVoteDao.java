package com.techblog.backend.dao;

import com.techblog.backend.model.Post;
import com.techblog.backend.model.PostVote;
import com.techblog.backend.model.PostVotePK;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.PostVoteRepository;
import com.techblog.backend.types.vote.PostVoteType;
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

  public void deleteVote(PostVote vote) {
    postVoteRepository.delete(vote);
  }

  public void deleteVoteByPK(Long postId, Long userId) {
    postVoteRepository.deleteById(new PostVotePK(postId, userId));
  }

  public PostVote createVote(Post post, User user, PostVoteType voteType) {
    return postVoteRepository.save(new PostVote(post, user, voteType));
  }
}
