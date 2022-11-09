package com.comapny.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "album_recommendation")
public class AlbumRecommendation {

    @Id
    @Column(name = "album_recommendation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer AlbumRecommendationID;

    @NotNull(message = "You must supply a value for user_id")
    @Column(name = "user_id")
    private int userID;

    @NotNull(message = "You must supply a value for album_id")
    @Column(name = "album_id")
    private Integer albumID;

    @NotNull(message = "You must supply a value for liked")
    private boolean liked;

    public AlbumRecommendation() {
    }

    public AlbumRecommendation(Integer albumRecommendationID, int userID, Integer albumID, boolean liked) {
        AlbumRecommendationID = albumRecommendationID;
        this.userID = userID;
        this.albumID = albumID;
        this.liked = liked;
    }

    public Integer getAlbumRecommendationID() {
        return AlbumRecommendationID;
    }

    public void setAlbumRecommendationID(Integer albumRecommendationID) {
        AlbumRecommendationID = albumRecommendationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Integer getAlbumID() {
        return albumID;
    }

    public void setAlbumID(Integer albumID) {
        this.albumID = albumID;
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
        AlbumRecommendation albumRecommendation = (AlbumRecommendation) o;
        return userID == albumRecommendation.userID && albumID == albumRecommendation.albumID && liked == albumRecommendation.liked && Objects.equals(AlbumRecommendationID, albumRecommendation.AlbumRecommendationID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(AlbumRecommendationID, userID, albumID, liked);
    }

    @Override
    public String toString() {
        return "AlbumRecommendation{" +
                "AlbumRecommendationID=" + AlbumRecommendationID +
                ", userID=" + userID +
                ", albumID=" + albumID +
                ", liked=" + liked +
                '}';
    }
}
