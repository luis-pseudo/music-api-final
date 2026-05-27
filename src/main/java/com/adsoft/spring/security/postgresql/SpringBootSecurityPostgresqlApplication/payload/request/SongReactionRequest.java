package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.request;

public class SongReactionRequest {
  private Long songId;
  public Long getSongId() {
    return songId;
}

  public void setSongId(Long songId) {
    this.songId = songId;
  }

  private Long reactionId;

  public Long getReactionId() {
    return reactionId;
  }

  public void setReactionId(Long reactionId) {
    this.reactionId = reactionId;
  }


}