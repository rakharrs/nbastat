package nba.com.stat;

import java.sql.Timestamp;

import dbObject.Dbobj;

public class Statistique extends Dbobj{
    String idStatistique;
    String idMatches;
    String idJoueur;
    int idAction;
    
    int point = 0; 
    Timestamp time;

    public Statistique(){
        
    }
    
    public Statistique(String idstatistique, String idmatches, String idjoueur, int idaction, int point){
        setIdStatistique(idstatistique);
        setIdMatches(idmatches);
        setIdJoueur(idjoueur);
        setIdAction(idaction);
        setPoint(point);
    }

    public Statistique(String idstatistique, String idmatches, String idjoueur, int idaction){
        setIdStatistique(idstatistique);
        setIdMatches(idmatches);
        setIdJoueur(idjoueur);
        setIdAction(idaction);
    }

    public void setIdStatistique(String idStatistique) {
        this.idStatistique = idStatistique;
    }
    public void setIdMatches(String idMatches) {
        this.idMatches = idMatches;
    }
    public void setIdJoueur(String idJoueur) {
        this.idJoueur = idJoueur;
    }
    public void setIdAction(int idAction) {
        this.idAction = idAction;
    }
    public void setPoint(int point) {
        this.point = point;
    }

    public String getIdStatistique() {
        return idStatistique;
    }
    public String getIdMatches() {
        return idMatches;
    }
    public String getIdJoueur() {
        return idJoueur;
    }
    public int getIdAction() {
        return idAction;
    }
    public int getPoint() {
        return point;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
    public Timestamp getTime() {
        return time;
    }

    @Override
    public void initSuper() {
        
    }
    
}