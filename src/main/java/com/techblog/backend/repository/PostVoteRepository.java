package com.techblog.backend.repository;

import com.techblog.backend.model.PostVote;
import com.techblog.backend.model.PostVotePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoteRepository extends JpaRepository<PostVote, PostVotePK> {}
