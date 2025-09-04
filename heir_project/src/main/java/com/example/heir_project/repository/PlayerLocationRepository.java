package com.example.heir_project.repository;

import com.example.heir_project.entity.PlayerLocation;
import com.example.heir_project.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerLocationRepository extends JpaRepository<PlayerLocation, Long> {

    // playerId로 위치 정보 조회
    Optional<PlayerLocation> findByPlayerId(Integer playerId);

    // player Entity로 위치 정보 조회
    Optional<PlayerLocation> findByPlayer(Players player);

    // 특정 씬에 있는 모든 플레이어 조회
    @Query("SELECT pl FROM PlayerLocation pl WHERE pl.sceneName = :sceneName")
    List<PlayerLocation> findAllBySceneName(@Param("sceneName") String sceneName);

    // 특정 씬의 특정 캠프파이어에 있는 플레이어 조회
    @Query("SELECT pl FROM PlayerLocation pl WHERE pl.sceneName = :sceneName AND pl.campFire = :campFire")
    List<PlayerLocation> findBySceneNameAndCampFire(@Param("sceneName") String sceneName, @Param("campFire") Integer campFire);
}