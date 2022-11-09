package com.comapny.musicstorerecommendations.repository;

import com.comapny.musicstorerecommendations.model.AlbumRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumRecommendation, Integer> {
}
