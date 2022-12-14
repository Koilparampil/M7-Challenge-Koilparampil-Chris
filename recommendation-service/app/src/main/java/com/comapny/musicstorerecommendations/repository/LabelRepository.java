package com.comapny.musicstorerecommendations.repository;

import com.comapny.musicstorerecommendations.model.LabelRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<LabelRecommendation, Integer> {
}
