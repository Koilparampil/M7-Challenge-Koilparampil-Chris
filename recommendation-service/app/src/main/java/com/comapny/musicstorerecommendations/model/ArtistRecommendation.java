package com.comapny.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "artist_recommendation ")
public class ArtistRecommendation {

    @Id
    @Column(name = "artist_recommendation_id ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ArtistRecommendationID;

    @NotNull(message = "You must supply a value for artist_id")
    @Column(name = "artist_id")
    private Integer ArtistID;

    @NotNull(message = "You must supply a value for user_id")
    @Column(name = "user_id")
    private int userID;

    @NotNull(message = "You must supply a value for liked")
    private boolean liked;

    public ArtistRecommendation() {
    }

    public ArtistRecommendation(Integer artistRecommendationID, Integer artistID, int userID, boolean liked) {
        ArtistRecommendationID = artistRecommendationID;
        ArtistID = artistID;
        this.userID = userID;
        this.liked = liked;
    }

    public Integer getArtistRecommendationID() {
        return ArtistRecommendationID;
    }

    public void setArtistRecommendationID(Integer artistRecommendationID) {
        ArtistRecommendationID = artistRecommendationID;
    }

    public Integer getArtistID() {
        return ArtistID;
    }

    public void setArtistID(Integer artistID) {
        ArtistID = artistID;
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
        ArtistRecommendation artistRecommendation = (ArtistRecommendation) o;
        return userID == artistRecommendation.userID && liked == artistRecommendation.liked && Objects.equals(ArtistRecommendationID, artistRecommendation.ArtistRecommendationID) && Objects.equals(ArtistID, artistRecommendation.ArtistID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ArtistRecommendationID, ArtistID, userID, liked);
    }

    @Override
    public String toString() {
        return "ArtistRecommendation{" +
                "ArtistRecommendationID=" + ArtistRecommendationID +
                ", ArtistID=" + ArtistID +
                ", userID=" + userID +
                ", liked=" + liked +
                '}';
    }
}
