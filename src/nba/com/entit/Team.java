package nba.com.entit;

import java.sql.Connection;
import java.util.ArrayList;

import dbObject.MyConnexion;
import nba.com.obj.Equipe;
import nba.com.obj.Joueur;

public class Team {
    Equipe equipe;
    Joueur[] joueurs;

    public Team(){

    }
    public Team(Equipe equipe){

        setEquipe(equipe);

        setTeamPlayers();
    }
    public Team(String idEquipe) throws Exception{
        
        initEquipe(idEquipe);
            
        setTeamPlayers();
    }

    private void initEquipe(String idEquipe) throws Exception{

        Connection con = MyConnexion.getConnection();
        ArrayList<Object> team = MyConnexion.sqltoArray(con, new Equipe(), "select * from equipe where idequipe ='"+idEquipe+"'");

        if(team.size() != 1){

            throw new Exception("invalid idEquipe");

        }

        setEquipe((Equipe)team.get(0));
    }

    public void setTeamPlayers(){
        setJoueurs(getTeamPlayers());
    }

    public int getCurrentScore(int idMatches) throws Exception{
        Connection con = MyConnexion.getConnection();
        ArrayList<Object> team = MyConnexion.sqltoArray(con, new Equipe(), "select sum(point) from statistique where idequipe ='"+getIdEquipe()+"' and idMatches = "+idMatches);

        if(team.size() != 1)
            throw new Exception("No score || invalid");
        
        return (int) team.get(0);
    }

    public Joueur[] getTeamPlayers(){
        Joueur[] val = null;
        try {
            Connection con = MyConnexion.getConnection();
            ArrayList<Object> players = MyConnexion.sqltoArray(con, new Joueur(), 
            "select * from joueur where idequipe = '"+getEquipe().getIdEquipe()+"'");

            Joueur[] playersArray = new Joueur[players.size()];

            for (int i = 0; i < players.size(); i++) {
                playersArray[i] = (Joueur) players.get(i);
            }
            val = playersArray;
            
            
        } catch (Exception e) {
            System.out.println(e);
            
        }
        
        return val;

    }
    public int getScore(String idMatches){
        int val = -1;
        try{
            Connection con = MyConnexion.getConnection();
            val = MyConnexion.getfirstInt(con, "statjoueur", "sum(point)", "idMatches = '"+idMatches+"' and idEquipe = '"+ getEquipe().getIdEquipe() +"'");

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return val;
    }
    public String getIdEquipe(){

        return getEquipe().getIdEquipe();
    }
    public Joueur getJoueur(int index){
        return getJoueurs()[index];
    }
    public Equipe getEquipe() {
        return equipe;
    }
    public Joueur[] getJoueurs() {
        return joueurs;
    }
    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
    public void setJoueurs(Joueur[] joueurs) {
        this.joueurs = joueurs;
    }
    
}
