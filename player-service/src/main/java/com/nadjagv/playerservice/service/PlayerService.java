package com.nadjagv.playerservice.service;


import com.nadjagv.playerservice.domain.Player;
import com.nadjagv.playerservice.exception.AlreadyExistsException;
import com.nadjagv.playerservice.exception.NotFoundException;
import com.nadjagv.playerservice.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player findPlayerById(Long id){
        return playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found."));
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public void createPlayer(Player player) {
        if (!playerRepository.existsByEmail(player.getEmail())) {
            playerRepository.save(player);

        } else {
            throw new AlreadyExistsException(String.format("Player with email '%s' already exists", player.getEmail()));
        }
    }

    public void updatePlayer(Player player) {
        Player existing = playerRepository.findByEmail(player.getEmail());
        if (existing!= null) {
            Player updated = Player.builder()
                    .id(existing.getId())
                    .email(player.getEmail())
                    .dateOfBirth(player.getDateOfBirth())
                    .firstName(player.getFirstName())
                    .lastName(player.getLastName())
                    .paymentType(player.getPaymentType())
                    .paid(player.getPaid())
                    .build();
            playerRepository.save(updated);
         } else {
            throw new AlreadyExistsException(String.format("Player with email '%s' already exists", player.getEmail()));
        }
    }

    public void deletePlayerByEmail(String email) {
        if (playerRepository.existsByEmail(email)) {
            playerRepository.deleteByEmail(email);
        } else {
            throw new AlreadyExistsException(String.format("Player with email '%s' not exists", email));
        }
    }

    public void deletePlayerById(Long id) {
        Player player = playerRepository.getById(id);
        if (player != null) {
            playerRepository.deleteById(id);
        } else {
            throw new AlreadyExistsException(String.format("Player with id '%d' does not exist", id));
        }
    }
}
