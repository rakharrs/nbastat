package nba.com.stat;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import dbObject.MyConnexion;
import nba.com.display.MatchFrame;
import nba.com.entit.Match;
import nba.com.entit.Team;
import nba.com.obj.Joueur;
import nba.com.obj.Matches;
import nba.util.other.Action;
import nba.util.other.NbdAction;

public class Detail implements Serializable{
    Match match;
    boolean paused = false;
    Joueur possession = null;
    Joueur lastpossession = null;
    NbdAction nextAction = NbdAction.PASSE;
    boolean nextIsRebound = false;
    MatchFrame frame;

    public Detail(Matches matches, MatchFrame frame) throws Exception{

        setMatch(new Match(matches.getIdMatches()));

        setFrame(frame);
        
        setPossession(getMatch().getTeam1().getJoueur(0));

        possessionRecup();
    }

    public void possessionRecup(){
        Statistique stat = new Statistique();
        stat.setIdStatistique("default");
        stat.setIdAction(Action.GET_PASSE);
        stat.setIdJoueur(getPossession().getIdJoueur());
        stat.setTime(new Timestamp(System.currentTimeMillis()));
        stat.setIdMatches(getMatch().getMatches().getIdMatches());

        try {
            Connection con = MyConnexion.getConnection();
            
            stat.insert("statistique", con);
            con.commit();
            con.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Statistique getLastStat() throws Exception{
        //Statistique stat = new Statistique();
        Connection con = MyConnexion.getConnection();
        ArrayList<Object> l = MyConnexion.sqltoArray(con, new Statistique(), "select * from statistique where idMatches = '"+getIdMatches()+"' order by idstatistique desc");
        
        con.close();
        if(l.size()<=0){
            return null;
        }
        return (Statistique)l.get(0);
        
    }

    /// Stat tsy comptena ny action pause
    public Statistique getLastStatNoPause() throws Exception{
        //Statistique stat = new Statistique();
        Connection con = MyConnexion.getConnection();
        ArrayList<Object> l = MyConnexion.sqltoArray(con, new Statistique(), "select * " +
                "from statistique where idMatches = '"+getIdMatches()+"' " +
                "and idaction != 8 and idaction != 9 " +
                "order by idstatistique desc");

        con.close();
        if(l.size()<=0){
            return null;
        }

        return (Statistique)l.get(0);
    }
    public int lastAction() throws Exception{
        Statistique laststat = getLastStatNoPause();

        if(laststat == null){

            throw new Exception("Aucun donnée de statistique présent");
        }

        return laststat.getIdAction();
    }

    public boolean isLasAction(int action) throws Exception{
        if(lastAction() == action){
            return true;
        }
        return false;
    }

    public Team getOpponent(Team team){
        if(isPlaying(team.getIdEquipe())){
            if(getMatch().getTeam1().equals(team) || getMatch().getTeam1() == team){
    
                return getMatch().getTeam2();
            }

            return getMatch().getTeam1();
        }
        return null;
    }
    public Team getOpponent(String idEquipe){
        if(isPlaying(idEquipe)){
            if(getMatch().getTeam1().getIdEquipe().equals(idEquipe)){
                return getMatch().getTeam2();
            }else{
                return getMatch().getTeam1();
            }
        }
        return null;
    }

    public void enableAllButton(boolean flag){
        getFrame().getPanel1().enableAllButtons(flag);
        getFrame().getPanel2().enableAllButtons(flag);
        
    }

    public static String getLastIdMatches() throws Exception{
        Connection con = MyConnexion.getConnection();
        //MyConnexion.sqltoArray(con, new Statistique(), "select idmatches from statistique group by idmatches order by idmatches desc limit 1");
        String val = MyConnexion.getfirstString(con, "select idmatches from statistique group by idmatches order by idmatches desc limit 1");
        con.close();
        return val;
    }

    public boolean isPlaying(String idEquipe){
        if(getMatch().getTeam1().getIdEquipe().equals(idEquipe) || getMatch().getTeam2().getIdEquipe().equals(idEquipe)){

            return true;
        }

        return false;
    }


    public Joueur getLastpossession() {
        return lastpossession;
    }
    public Match getMatch() {
        return match;
    }
    public NbdAction getNextAction() {
        return nextAction;
    }
    public Joueur getPossession() {
        return possession;
    }

    public void setLastpossession(Joueur lastpossession) {
        this.lastpossession = lastpossession;
    }
    public void setMatch(Match match) {
        this.match = match;
    }
    public void setNextAction(NbdAction nextAction) {
        this.nextAction = nextAction;
    }
    public void setNextIsRebound(boolean nextIsRebound) {
        this.nextIsRebound = nextIsRebound;
    }
    public void setPossession(Joueur possession) {
        this.possession = possession;
    }public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public String getIdMatches(){
        return getMatch().getMatches().getIdMatches();
    }
    public void setFrame(MatchFrame frame) {
        this.frame = frame;
    }
    public MatchFrame getFrame() {
        return frame;
    }public boolean isPaused() {
        return paused;
    }
    
    

    
}
