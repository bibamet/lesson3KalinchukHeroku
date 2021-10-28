package com.example.lesson1.service;

import com.example.lesson1.data.PlayerRepository;
import com.example.lesson1.data.WeaponRepository;
import com.example.lesson1.dto.PlayerDTO;
import com.example.lesson1.dto.ResponseDTO;
import com.example.lesson1.entity.PlayerEntity;
import com.example.lesson1.entity.PlayerStatus;
import com.example.lesson1.entity.WeaponEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final WeaponRepository weaponRepository;

    public PlayerEntity getPlayerById(Long id) {
        log.info("getPlayerById.in - searching player {}", id);
        Optional<PlayerEntity> byId = playerRepository.findById(id);
        if (byId.isPresent()) {
            log.info("getPlayerById.out - found player {}", id);
            return byId.get();
        } else {
            log.error("getPlayerById.out - not found player {}", id);
            throw new EntityNotFoundException("Entity with given id not found!");
        }
    }

    public ResponseEntity<ResponseDTO> findPlayersByStatus(PlayerStatus status) {
        try {
            List<PlayerDTO> collect = playerRepository.findAll().stream()
                    .filter(playerEntity -> status.isVal() != playerEntity.isTerminated())
                    .map(this::map)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ResponseDTO.builder().result(collect).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public PlayerDTO map(PlayerEntity playerEntity) {
        return PlayerDTO.builder()
                .nickname(playerEntity.getNickName())
                .id(playerEntity.getId())
                .description(playerEntity.getProfileInfo()).build();
    }

    @PostConstruct
    public void testUpdate() {
        playerRepository.save(PlayerEntity.builder()
                        .nickName("bibka-pipka")
                        .profileInfo("test")
                        .terminated(false)
                        .weapons(Collections.singletonList(WeaponEntity.builder()
                                        .broken(false)
                                        .damage(666)
                                        .detailedName("Automat Kalashnikova 47")
                                        .name("AK-47")
                                        .range(1000)
                                .build()))
                .build());
    }

//    @PostConstruct
    public void testUpload() {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(1L);
        Optional<WeaponEntity>  weaponEntity = weaponRepository.findById(1L);

        Integer test = 5;

    }

}
