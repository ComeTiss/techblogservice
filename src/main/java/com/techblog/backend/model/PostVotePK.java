package com.techblog.backend.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PostVotePK implements Serializable {
  private Long post_id;
  private Long user_id;
}
