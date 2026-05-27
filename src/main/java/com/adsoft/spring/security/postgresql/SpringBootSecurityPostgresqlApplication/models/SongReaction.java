package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table( name = "song_reactions",
          uniqueConstraints = { 
          @UniqueConstraint(columnNames = {"user_id", "song_id"}
          ),
      
        }
)

public class SongReaction {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
 
   @Column(name = "reaction_id")
   Long reactionId;

   public Long getReactionId() {
    return reactionId;
}

   public void setReactionId(Long reactionId) {
    this.reactionId = reactionId;
   }

   @Column(name = "user_id")
   Long userId;

    public Long getUserId() {
    return userId;
}

   public void setUserId(Long userId) {
    this.userId = userId;
   }

    @Column(name = "song_id")
    Long songId;

  public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

  public Long getId() {
    return id;
}

   public void setId(Long id) {
    this.id = id;
   }

  
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.userId = user.getId();
        this.user = user;
    }

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    Song song;

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.songId = song.getId();
        this.song = song;
    }

    @ManyToOne
    @MapsId("reactionId")
    @JoinColumn(name = "reaction_id")
    Reaction reaction;

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reactionId = reaction.getId();
        this.reaction = reaction;
    }

}
