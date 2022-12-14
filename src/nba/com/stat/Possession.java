package nba.com.stat;

import java.sql.Connection;
import java.util.ArrayList;

import dbObject.MyConnexion;
import nba.com.obj.Joueur;
import nba.com.viewobject.Statjoueur;
import nba.util.other.Action;

import java.sql.Timestamp;

public class Possession {
    String idMatches;
    ArrayList<Statjoueur> statjoueurs;
    long timeOffset = 0;

    public Possession(){

    }
    public Possession(String idMatches) throws Exception{
        setIdMatches(idMatches);
        Connection con = MyConnexion.getConnection();
        ArrayList<Object> list = MyConnexion.sqltoArray(con, new Statjoueur(), 
            "select * from statjoueur where idMatches ='"+idMatches+"' and idaction != "+Action.PASSE_D+" and idaction != "+ Action.PAUSE_BEGIN +" and idaction != "+ Action.PAUSE_END +" order by idstatistique");

        ArrayList<Statjoueur> stats = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            stats.add((Statjoueur) list.get(i));
        }

        setStatjoueurs(stats);
        con.close();
    }

    public possessionTime[] getIntervalleStat() throws Exception{
        int size = getStatjoueurs().size()/2;
        System.out.println(size);
        possessionTime[] val = new possessionTime[size];

        int j = 0;
        for (int i = 0; i + 2< getStatjoueurs().size(); i+=2) {
            String j1 = getStatjoueurs().get(i).getIdjoueur();
            String e1 = getStatjoueurs().get(i).getIdequipe();

            String j2 = getStatjoueurs().get(i + 1).getIdjoueur();
            //System.out.println(j1 + " et "+j2);
            System.out.println("j1 "+j1);
            System.out.println("j2 "+j2);
            if(j1.equals(j2)){
                long interv = Timestamp.valueOf(getStatjoueurs().get(i + 1).getTime()).getTime() - Timestamp.valueOf(getStatjoueurs().get(i).getTime()).getTime();
                //System.out.println("I = "+interv);
                //System.out.println("equipe = "+e1);
                val[j] = new possessionTime(interv/1000, j1, e1);
            }else{
                throw new Exception("misy olana");
            }
            j++;
        }

        return val;
    }

    public ArrayList<Statjoueur> listOffset(String idMatches) throws Exception{
        Connection con = MyConnexion.getConnection();
        ArrayList<Object> list = MyConnexion.sqltoArray(con, new Statjoueur(), 
            "select * from statjoueur where idMatches = '"+idMatches+"' and idAction = "+Action.PAUSE_BEGIN+" or idMatches = '"+idMatches+"' and idAction = "+ Action.PAUSE_END +" order by idstatistique");
        
            ArrayList<Statjoueur> pauseStat = new ArrayList<>();

            long val = 0;

            for (int i = 0; i < list.size(); i++) {
    
                Statjoueur sj =(Statjoueur) list.get(i);
                pauseStat.add(sj);
            }

        return pauseStat;            
    }

    public possessionTime[] offsetInterval() throws  Exception{
        ArrayList<Statjoueur> list = listOffset(getIdMatches());
        System.out.println(getIdMatches());
        possessionTime[] val = new possessionTime[list.size()/2];
        int j = 0;
        for (int i = 0; i + 2 < list.size(); i+=2) {
            long interv = Timestamp.valueOf(list.get(i + 1).getTime()).getTime() - Timestamp.valueOf(list.get(i).getTime()).getTime();
            val[j] = new possessionTime(interv/1000, list.get(i).getIdjoueur(), list.get(i).getIdequipe());
            j++;
        }
        return val;
    }

    public long takeOffset() throws Exception{
        long val = 0;
        ArrayList<Statjoueur> list = listOffset(getIdMatches());

        for (int i = 0; i + 2< list.size(); i+=2) {
            long interv = Timestamp.valueOf(list.get(i + 1).getTime()).getTime() - Timestamp.valueOf(list.get(i).getTime()).getTime();

            val += (interv/1000);
        }

        return val;
    }

    public boolean hasOffset() throws Exception{
        ArrayList<Statjoueur> list = listOffset(getIdMatches());

        if(list.size() >= 0){
            return true;
        }
        return false;
    }

    public double getPlayerPlaytime(String idPlayer){
        double val = 0;
        try{
            possessionTime[] times = getIntervalleStat();
            for (possessionTime possessionTime : times) {
                if(possessionTime.getIdJoueur().equals(idPlayer)){
                    val += possessionTime.getValue();
                }
            }

            System.out.println("playerPlaytime "+val);
            val = val - getPlayerOffset(idPlayer);

        }catch (Exception toky){
            System.out.println(toky);
        }
        return val;
    }

    public double getPlayerOffset(String idPlayer) throws Exception{
        double val = 0;
        ArrayList<Statjoueur> list = listOffset(getIdMatches());

        for (int i = 0; i + 2< list.size(); i+=2) {
            if(list.get(i).getIdjoueur().equals(idPlayer)){

                double interv = Timestamp.valueOf(list.get(i + 1).getTime()).getTime() - Timestamp.valueOf(list.get(i).getTime()).getTime();

                val += (interv/1000);

            }
        }

        System.out.println("Offset "+val);
        return val;
    }

    public double getTeamPlaytime(String idEquipe) throws Exception{
        possessionTime[] times = getIntervalleStat();
        double val = 0;
        for (possessionTime possessionTime : times) {
            if(possessionTime.getIdEquipe().equals(idEquipe)){
                val += possessionTime.getValue();
            }
        }
        return val - getTeamOffset(idEquipe);
    }

    public double getTeamOffset(String idEquipe) throws Exception{
        long val = 0;
        ArrayList<Statjoueur> list = listOffset(getIdMatches());

        for (int i = 0; i + 2< list.size(); i+=2) {
            if(list.get(i).getIdequipe().equals(idEquipe)){
                long interv = Timestamp.valueOf(list.get(i + 1).getTime()).getTime() - Timestamp.valueOf(list.get(i).getTime()).getTime();

                val += (interv/1000);

            }
        }

        return val;
    }

    public double getPossessionPercent(String idEquipe) throws Exception{
        double val = 0;
        val = (getTeamPlaytime(idEquipe)*100.0)/getTotalPlaytime();

        return val;
    }

    public double getTotalPlaytime() throws Exception{
        possessionTime[] times = getIntervalleStat();
        double val = 0;
        for (possessionTime possessionTime : times) {
            val += possessionTime.getValue();
        }
        val = val - takeOffset();
        return val;
    }


    public Possession(ArrayList<Statjoueur> stats){
        setStatjoueurs(stats);
    }

    public void setStatjoueurs(ArrayList<Statjoueur> statjoueurs) {
        this.statjoueurs = statjoueurs;
    }
    public ArrayList<Statjoueur> getStatjoueurs() {
        return statjoueurs;
    }

    public void setIdMatches(String idMatches) {
        this.idMatches = idMatches;
    }
    public String getIdMatches() {
        return idMatches;
    }
    public long getTimeOffset() {
        return timeOffset;
    }
    public void setTimeOffset(long timeOffset) {
        this.timeOffset = timeOffset;
    }
    
}
