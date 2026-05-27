package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table( name = "songs")

public class Song {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @NotBlank
  @Size(max = 140)
  private String song;

  @Size(max = 500)
  private String imageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "posted_by", referencedColumnName = "id")
  private User postedBy;



  public User getPostedBy() {
    return postedBy;
  }

  public void setPostedBy(User postedBy) {
    this.postedBy = postedBy;
  }

  public Song() {
  }

  public  Song (String song) {
    this.song = song;
  }

  // getters and setters

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

  @OneToMany(mappedBy = "song")
  Set<SongReaction> likes;

  public Set<SongReaction> getLikes() {
    return likes;
  }

  public void setLikes(Set<SongReaction> likes) {
    this.likes = likes;
  }



}
