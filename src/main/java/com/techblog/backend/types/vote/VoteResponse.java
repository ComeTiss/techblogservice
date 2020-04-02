package com.techblog.backend.types.vote;

import com.techblog.backend.model.PostVote;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteResponse {
    private PostVote vote;
}
