package com.nadjagv.playerservice.repository;

import com.nadjagv.playerservice.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    boolean existsByEmail(String email);

    Player findByEmail(String email);

    void deleteByEmail(String email);
}
