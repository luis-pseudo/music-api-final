package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.EReaction;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
  Optional<Reaction> findByDescription(EReaction description);
}

