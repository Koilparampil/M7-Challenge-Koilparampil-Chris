package com.comapny.musicstorerecommendations.repository;

import com.comapny.musicstorerecommendations.model.ArtistRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistRecommendation, Integer> {
}
