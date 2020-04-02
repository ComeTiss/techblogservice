package com.techblog.backend.datafetchers;

import com.techblog.backend.dao.PostVoteDao;
import com.techblog.backend.model.Post;
import com.techblog.backend.model.PostVote;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.PostRepository;
import com.techblog.backend.repository.UserRepository;
import com.techblog.backend.types.vote.PostVoteType;
import com.techblog.backend.types.vote.VoteResponse;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.LinkedHashMap;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostVotesDataFetcher implements DataFetcher<PostVote> {

  @Autowired PostRepository postRepository;
  @Autowired UserRepository userRepository;
  @Autowired PostVoteDao postVoteDao;

  public VoteResponse mutateVote(DataFetchingEnvironment environment) {
    VoteResponse response = new VoteResponse();
    try {
      LinkedHashMap requestData = environment.getArgument("request");
      Long postId = Long.valueOf(requestData.get("postId").toString());
      Long authorId = Long.valueOf(requestData.get("authorId").toString());
      PostVoteType vote = PostVoteType.valueOf(requestData.get("vote").toString());

      Post post = postRepository.getOne(postId);
      if (post == null) {
        return response;
      }
      User user = userRepository.getOne(authorId);
      if (user == null) {
        return response;
      }
      Optional<PostVote> currentVote = postVoteDao.getVoteByPK(postId, authorId);
      currentVote.ifPresent(postVoteDao::deleteVote);
      response.setVote(postVoteDao.createVote(post, user, vote));
    } catch (Exception e) {
      log.error(String.valueOf(e));
    }
    return response;
  }

  public VoteResponse deleteVote(DataFetchingEnvironment environment) {
    VoteResponse response = new VoteResponse();
    try {
      LinkedHashMap requestData = environment.getArgument("request");
      Long postId = Long.valueOf(requestData.get("postId").toString());
      Long authorId = Long.valueOf(requestData.get("authorId").toString());
      postVoteDao.deleteVoteByPK(postId, authorId);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return response;
  }

  @Override
  public PostVote get(DataFetchingEnvironment environment) throws Exception {
    return null;
  }
}
