package model;

public class Matchup {
    public Team team1;
    public Team team2;

    public Matchup(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getMatchup(){
        String team1Name = team1.playerNamesProperty().get();
        String team2Name = team2.playerNamesProperty().get();
        if (team1.teamNamesProperty() != null) {
            team1Name = team1.teamNamesProperty().get();
        }

        if (team2.teamNamesProperty() != null) {
            team2Name = team2.teamNamesProperty().get();
        }
        return team1Name + " vs " + team2Name;
    }

    public String getTeam1() {
        String team1Name = team1.playerNamesProperty().get();
        if (team1.teamNamesProperty() != null) {
            team1Name = team1.teamNamesProperty().get();
        }
        return team1Name;
    }

    public String getTeam2() {
        String team2Name = team2.playerNamesProperty().get();
        if (team2.teamNamesProperty() != null) {
            team2Name = team2.teamNamesProperty().get();
        }
        return team2Name;
    }

    public Team getTeam1Team() { return this.team1; }

    public Team getTeam2Team() { return this.team2; }
}