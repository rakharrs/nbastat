package nba.com.obj;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import dbObject.Dbobj;
import dbObject.MyConnexion;
import nba.com.stat.Statistique;
import nba.util.other.Action;

public class Joueur extends Dbobj{
    String idJoueur;
    String nomJoueur;
    String idEquipe;

    public Joueur(){

        initSuper();
    }

    public static Joueur getJoueur(Connection con, String idjoueur) throws Exception {
        ArrayList<Object> joueurs = MyConnexion.sqltoArray(con, new Joueur(), "select * from joueur where idjoueur = '"+idjoueur+"'");
        Joueur j = (Joueur) joueurs.get(0);
        return j;
    }
    public Joueur(String nomjoueur, String idequipe){

        initSuper();

        setNomJoueur(nomjoueur);

        setIdEquipe(idequipe);

    }
    public Joueur(String idjoueur, String nomjoueur, String idequipe){

        initSuper();

        setIdJoueur(idjoueur);

        setNomJoueur(nomjoueur);

        setIdEquipe(idequipe);

    }

    public void make(Connection con, int action, String idMatches, int point) throws Exception{
        Statistique stat = new Statistique();
        stat.setIdAction(action);
        stat.setIdJoueur(getIdJoueur());
        stat.setIdMatches(idMatches);
        stat.setIdStatistique("default");
        stat.setPoint(point);
        stat.setTime(new Timestamp(System.currentTimeMillis()));

        stat.insert("statistique", con);
    }

    public int getTotalOff_Rebounds(String idMatches){
        int val = 0;
        try{
            Connection con = MyConnexion.getConnection();
            String predicat = "idMatches = '"+ idMatches +"' and idJoueur = '"+ getIdJoueur() +"' and idaction = "+Action.REBOND_OFF;
            val = MyConnexion.getfirstInt(con, "statistique", "count(idaction)", predicat);

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public int getTotalDEF_Rebounds(String idMatches){
        int val = 0;
        try{
            Connection con = MyConnexion.getConnection();
            String predicat = "idMatches = '"+ idMatches +"' and idJoueur = '"+ getIdJoueur() +"' and idaction = "+Action.REBOND_DEF;
            val = MyConnexion.getfirstInt(con, "statistique", "count(idaction)", predicat);

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public int getTotalPASSE_D(String idMatches){
        int val = 0;
        try{
            Connection con = MyConnexion.getConnection();

            String predicat = "idMatches = '"+ idMatches +"' and idJoueur = '"+ getIdJoueur() +"' and idaction = "+Action.PASSE_D;
            val = MyConnexion.getfirstInt(con, "statistique", "count(idaction)", predicat);

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public int getTotalShoot(String idMatches){
        int val = 0;
        try{
            Connection con = MyConnexion.getConnection();

            String predicat = "idMatches = '"+ idMatches +"' and idJoueur = '"+ getIdJoueur() +"' and idaction = "+Action.SHOOT;
            val = MyConnexion.getfirstInt(con, "statistique", "count(idaction)", predicat);

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public int getMissed_Shoot(String idMatches) throws Exception{
        int val = 0;
        try{
            Connection con = MyConnexion.getConnection();

            String predicat = "idMatches = '"+ idMatches +"' and idJoueur = '"+ getIdJoueur() +"' and idaction = "+Action.SHOOT+" and point = 0;";
            val = MyConnexion.getfirstInt(con, "statistique", "count(idaction)", predicat);

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }

    public int getSuccess_Shoot(String idMatches){
        int total = 0; int miss = 0;
        try{
            Connection con = MyConnexion.getConnection();

            total = getTotalShoot(idMatches);
            miss = getMissed_Shoot(idMatches);

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return (total - miss);
    }

    public int getTotalPoints(String idMatches){
        int val = 0;
        try{
            Connection con = MyConnexion.getConnection();
            String predicat = "idMatches = '"+idMatches+"' and idJoueur ='"+getIdJoueur()+"'";
            val = MyConnexion.getfirstInt(con, "statistique", "sum(point)", predicat);

            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return val;
    }

    public void setIdEquipe(String idEquipe) {
        this.idEquipe = idEquipe;
    }
    public void setIdJoueur(String idJoueur) {
        this.idJoueur = idJoueur;
    }
    public void setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
    }

    public String getIdJoueur() {
        return idJoueur;
    }
    public String getIdEquipe() {
        return idEquipe;
    }
    public String getNomJoueur() {
        return nomJoueur;
    }

    @Override
    public void initSuper() {
        setPrefix("JOU");
        setPkl(7);
        setGetSequence("joueurSeq");

    }

}
