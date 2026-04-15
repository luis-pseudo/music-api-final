package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.controllers;
import java.util.Optional;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Tweet;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.TweetResponseDTO;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.UserRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.TweetRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")

public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    @Transactional(readOnly = true)
    public Page<TweetResponseDTO> getTweet(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId  );

        Page<Tweet> tweets = tweetRepository.findAll(pageable);
        return tweets.map(TweetResponseDTO::new);
    }
  
  @PostMapping("/create")
  public Tweet createTweet(@Valid @RequestBody Tweet tweet) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId  );


        User user = getValidUser(userId);
        System.out.println("user");

        System.out.println(user);
        Tweet myTweet = new Tweet(tweet.getTweet());
        myTweet.setPostedBy(user);
        tweetRepository.save(myTweet);

        return myTweet;
  }

    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }
}
