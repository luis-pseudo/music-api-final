package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.controllers;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Reaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Song;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.SongReaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.request.SongReactionRequest;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.ReactionRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.SongReactionRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.SongRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.UserRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reactions")

public class SongReactionController {

    @Autowired
    private SongReactionRepository SongReactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SongRepository SongRepository;
    @Autowired
    private ReactionRepository reactionRepository;


  @GetMapping("/all")
    public Page<SongReaction> getSong(Pageable pageable) {
        return SongReactionRepository.findAll(pageable);
    }
  
  @PostMapping("/create")
  @PreAuthorize("IsAuthenticated()")
  public SongReaction createReaction(@Valid @RequestBody SongReactionRequest SongReaction) {
        System.out.println("Songid : " + SongReaction.getSongId()  );
        System.out.println("reactiontid : " + SongReaction.getReactionId()  );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId  );


        User user = getValidUser(userId);
        System.out.println("user");

        System.out.println(user);

        SongReaction mySongReaction = new SongReaction();
        Song mySong = getValidSong(SongReaction.getSongId());
        System.out.println("object Song : " );
        System.out.println(mySong );


        Reaction myReaction = getValidReaction(SongReaction.getReactionId());
        System.out.println("object reaction : "   );
        System.out.println( myReaction );

        //mySongReaction.setUserId(user.getId());
        //mySongReaction.setSongId(mySong.getId());
        //mySongReaction.setReactionId(myReaction.getId());
        
        mySongReaction.setUser(user);
        mySongReaction.setSong(mySong);
        mySongReaction.setReaction(myReaction);
        
        System.out.println("Song reaction : "   );
        System.out.println( mySongReaction.getReactionId());
                System.out.println( mySongReaction.getSongId());

                        System.out.println( mySongReaction.getUserId());


        SongReactionRepository.save(mySongReaction);

        return mySongReaction;
  }

    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }

    private Song getValidSong(Long SongId) {
        Optional<Song> SongOpt = SongRepository.findById(SongId);
        if (!SongOpt.isPresent()) {
            throw new RuntimeException("Song not found");
        }
        return SongOpt.get();
    }

    private Reaction getValidReaction(Long reactionId) {
        Optional<Reaction> reactionOpt = reactionRepository.findById(reactionId);
        if (!reactionOpt.isPresent()) {
            throw new RuntimeException("Reaction not found");
        }
        return reactionOpt.get();
    }

}
