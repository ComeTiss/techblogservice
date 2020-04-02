package com.techblog.backend.types.vote;

import com.techblog.backend.model.PostVote;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotesResponse {
  private List<PostVote> votes;

  public VotesResponse() {
    this.votes = new ArrayList<>();
  }
}
