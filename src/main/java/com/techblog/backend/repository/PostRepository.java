package com.techblog.backend.repository;

import com.techblog.backend.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> getPostsByAuthorId(Long authorId);
}
