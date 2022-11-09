package com.comapny.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "label_recommendation ")
public class LabelRecommendation {

    @Id
    @Column(name = "label_recommendation_id  ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer LabelRecommendationID;

    @NotNull(message = "You must supply a value for label_id ")
    @Column(name = "label_id ")
    private Integer LabelID;

    @NotNull(message = "You must supply a value for user_id")
    @Column(name = "user_id")
    private Integer userID;

    @NotNull(message = "You must supply a value for liked")
    private boolean liked;

    public LabelRecommendation() {
    }

    public LabelRecommendation(Integer labelRecommendationID, Integer labelID, int userID, boolean liked) {
        LabelRecommendationID = labelRecommendationID;
        LabelID = labelID;
        this.userID = userID;
        this.liked = liked;
    }

    public Integer getLabelRecommendationID() {
        return LabelRecommendationID;
    }

    public void setLabelRecommendationID(Integer labelRecommendationID) {
        LabelRecommendationID = labelRecommendationID;
    }

    public Integer getLabelID() {
        return LabelID;
    }

    public void setLabelID(Integer labelID) {
        LabelID = labelID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
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
        LabelRecommendation labelRecommendation = (LabelRecommendation) o;
        return userID == labelRecommendation.userID && liked == labelRecommendation.liked && Objects.equals(LabelRecommendationID, labelRecommendation.LabelRecommendationID) && Objects.equals(LabelID, labelRecommendation.LabelID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LabelRecommendationID, LabelID, userID, liked);
    }

    @Override
    public String toString() {
        return "LabelRecommendation{" +
                "LabelRecommendationID=" + LabelRecommendationID +
                ", LabelID=" + LabelID +
                ", userID=" + userID +
                ", liked=" + liked +
                '}';
    }
}
