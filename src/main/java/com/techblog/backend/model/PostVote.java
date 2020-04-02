package com.techblog.backend.model;

import com.techblog.backend.types.vote.PostVoteType;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post_votes")
public class PostVote implements Serializable {

  @EmbeddedId private PostVotePK id = new PostVotePK();

  @JoinColumn
  @ManyToOne
  @MapsId("post_id")
  private Post post;

  @JoinColumn
  @ManyToOne
  @MapsId("user_id")
  private User user;

  private PostVoteType vote;

  public PostVote(Post post, User user) {
    this.post = post;
    this.user = user;
  }

  public PostVote(Post post, User user, PostVoteType voteType) {
    this.post = post;
    this.user = user;
    this.vote = voteType;
  }
}
