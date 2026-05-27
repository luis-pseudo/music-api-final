package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.SongReaction;

@Repository
public interface SongReactionRepository extends JpaRepository<SongReaction, Long> {

}

