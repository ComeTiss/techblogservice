package com.techblog.backend.repository;

import com.techblog.backend.model.PostVote;
import com.techblog.backend.model.PostVotePK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, PostVotePK> {

  List<PostVote> findByPostIdIn(List<Long> postIds);
}
