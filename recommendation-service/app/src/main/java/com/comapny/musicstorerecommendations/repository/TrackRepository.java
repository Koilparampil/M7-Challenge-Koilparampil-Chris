package com.comapny.musicstorerecommendations.repository;

import com.comapny.musicstorerecommendations.model.TrackRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<TrackRecommendation, Integer> {

}

