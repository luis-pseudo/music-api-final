package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.controllers;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Song;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.MessageResponse;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.SongResponseDTO;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.SongRepository;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.UserRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/songs")

public class SongController {

    @Autowired
    private SongRepository SongRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public Page<SongResponseDTO> getSong(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId  );

        Page<Song> songs = SongRepository.findAll(pageable);
        return songs.map(SongResponseDTO::new);
    }
  
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public Song createSong(@Valid @RequestBody Song Song) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId  );


        User user = getValidUser(userId);
        System.out.println("user");

        System.out.println(user);
        Song mySong = new Song(Song.getSong());
        mySong.setPostedBy(user);
        mySong.setImageUrl(Song.getImageUrl());
        SongRepository.save(mySong);

        return mySong;
    }

    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Optional<Song> songOpt = SongRepository.findById(id);
        if (songOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Canción no encontrada."));
        }

        Song song = songOpt.get();
        boolean isOwner = song.getPostedBy() != null && userId.equals(song.getPostedBy().getUsername());
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if (!isOwner && !isAdmin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse("Error: No tienes permisos para eliminar esta canción."));
        }

        SongRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Canción eliminada con éxito."));
    }
}
