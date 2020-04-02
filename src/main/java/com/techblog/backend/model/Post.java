package com.techblog.backend.model;

import com.techblog.backend.types.vote.PostVoteType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;
  private String description;
  private Instant createdAt;
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "authorId", referencedColumnName = "id")
  private User author;

  @OneToMany(mappedBy = "post")
  private Set<PostVote> votes = new HashSet<>();

  public Post(String title, String description, User author) {
    this.title = title;
    this.description = description;
    this.createdAt = Instant.now();
    this.author = author;
  };

  public PostVote addVote(User user, PostVoteType voteType) {
    PostVote newVote = new PostVote(this, user, voteType);
    this.votes.add(newVote);
    return newVote;
  }

  public boolean removeVote(User user, PostVoteType voteType) {
    return this.votes.remove(new PostVote(this, user, voteType));
  }
}
