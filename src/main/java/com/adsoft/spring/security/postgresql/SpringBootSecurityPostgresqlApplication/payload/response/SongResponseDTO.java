package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Song;

public class SongResponseDTO {
  private Long id;
  private String song;
  private String imageUrl;
  private UserResponseDTO postedBy;

  public SongResponseDTO(Song song) {
    this.id = song.getId();
    this.song = song.getSong();
    this.imageUrl = song.getImageUrl();
    this.postedBy = new UserResponseDTO(song.getPostedBy());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSong() {
    return song;
  }

  public void setSong(String song) {
    this.song = song;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public UserResponseDTO getPostedBy() {
    return postedBy;
  }

  public void setPostedBy(UserResponseDTO postedBy) {
    this.postedBy = postedBy;
  }
}
