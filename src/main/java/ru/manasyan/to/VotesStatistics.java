package ru.manasyan.to;

public class VotesStatistics {

    private Integer restaurant;
    private Long votes;

    public VotesStatistics(Integer restaurant, Long votes) {
        this.restaurant = restaurant;
        this.votes = votes;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public Long getVotes() {
        return votes;
    }

}
