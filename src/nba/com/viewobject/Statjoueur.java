package nba.com.viewobject;

import java.security.Timestamp;

import dbObject.Dbobj;

public class Statjoueur extends Dbobj{

    //String idStatistique;
    String idmatches;
    String idjoueur;
    String nomjoueur;
    String idaction;
    String point;
    String time;
    String idequipe;

    public Statjoueur(){

    }

    public void setIdaction(String idAction) {
        this.idaction = idAction;
    }
    public void setIdjoueur(String idjoueur) {
        this.idjoueur = idjoueur;
    }
    public void setIdmatches(String idmatches) {
        this.idmatches = idmatches;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setPoint(String point) {
        this.point = point;
    }
    public void setNomjoueur(String nomJoueur) {
        this.nomjoueur = nomJoueur;
    }
    public void setIdequipe(String idequipe) {
        this.idequipe = idequipe;
    }

    public String getIdAction() {
        return idaction;
    }
    public String getIdjoueur() {
        return idjoueur;
    }
    public String getIdmatches() {
        return idmatches;
    }
    public String getNomJoueur() {
        return nomjoueur;
    }
    public String getTime() {
        return time;
    }
    public String getPoint() {
        return point;
    }
    public String getIdequipe() {
        return idequipe;
    }

    @Override
    public void initSuper() {
        
    }
    
}
