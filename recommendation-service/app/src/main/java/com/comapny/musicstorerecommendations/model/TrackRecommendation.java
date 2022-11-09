package com.comapny.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "track_recommendation")
public class TrackRecommendation {

    @Id
    @Column(name = "track_recommendation_id   ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer TrackRecommendationID;

    @NotNull(message = "You must supply a value for track_id  ")
    @Column(name = "track_id  ")
    private Integer TrackID;

    @NotNull(message = "You must supply a value for user_id")
    @Column(name = "user_id")
    private int userID;

    @NotNull(message = "You must supply a value for liked")
    private boolean liked;

    public TrackRecommendation() {
    }

    public TrackRecommendation(Integer trackRecommendationID, Integer trackID, int userID, boolean liked) {
        TrackRecommendationID = trackRecommendationID;
        TrackID = trackID;
        this.userID = userID;
        this.liked = liked;
    }

    public Integer getTrackRecommendationID() {
        return TrackRecommendationID;
    }

    public void setTrackRecommendationID(Integer trackRecommendationID) {
        TrackRecommendationID = trackRecommendationID;
    }

    public Integer getTrackID() {
        return TrackID;
    }

    public void setTrackID(Integer trackID) {
        TrackID = trackID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackRecommendation trackRecommendation = (TrackRecommendation) o;
        return userID == trackRecommendation.userID && liked == trackRecommendation.liked && Objects.equals(TrackRecommendationID, trackRecommendation.TrackRecommendationID) && Objects.equals(TrackID, trackRecommendation.TrackID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TrackRecommendationID, TrackID, userID, liked);
    }

    @Override
    public String toString() {
        return "TrackRecommendation{" +
                "TrackRecommendationID=" + TrackRecommendationID +
                ", TrackID=" + TrackID +
                ", userID=" + userID +
                ", liked=" + liked +
                '}';
    }
}
