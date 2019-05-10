package com.example.android.cricfuzz;

public class Model {

    String id,team1,team2,matchType,matchStatus,date;

    public Model(String id, String team1, String team2, String matchType, String matchStatus, String date) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.matchType = matchType;
        this.matchStatus = matchStatus;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getMatchType() {
        return matchType;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public String getDate() {
        return date;
    }
}
//model class for recycler view
