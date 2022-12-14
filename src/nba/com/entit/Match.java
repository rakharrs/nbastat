package nba.com.entit;

import nba.com.obj.Matches;

public class Match {
    Matches matches;
    Team Team1;
    Team Team2;

    public Match(String idMatches) throws Exception{

        setMatches(Matches.getMatches(idMatches));

        setTeam1(new Team(getMatches().getIdEquipe1()));

        setTeam2(new Team(getMatches().getIdEquipe2()));
    }

    
    
    public Team getTeam1() {
        return Team1;
    }
    public Team getTeam2() {
        return Team2;
    }
    public Matches getMatches() {
        return matches;
    }
    public void setTeam1(Team Team1) {
        this.Team1 = Team1;
    }
    public void setTeam2(Team Team2) {
        this.Team2 = Team2;
    }
    public void setMatches(Matches matches) {
        this.matches = matches;
    }
}
