package com.techblog.backend.model;

import com.techblog.backend.types.vote.PostVoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_votes")
@IdClass(PostVote.class)
public class PostVote implements Serializable {

  @Id
  @JoinColumn
  @ManyToOne
  private Post post;

  @Id
  @JoinColumn
  @ManyToOne
  private User user;

  private PostVoteType vote;

  public PostVote(Post post, User user) {
    this.post = post;
    this.user = user;
  }
}
