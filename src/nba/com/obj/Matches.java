package nba.com.obj;

import java.sql.Connection;
import java.util.ArrayList;

import dbObject.Dbobj;
import dbObject.MyConnexion;

public class Matches extends Dbobj{
    String idMatches;
    String idEquipe1;
    String idEquipe2;
    
    public Matches(){
        initSuper();
    }

    public Matches(String idequipe1, String idequipe2){
        initSuper();
        setIdEquipe1(idequipe1);
        setIdEquipe2(idequipe2);
    }

    public Matches(String idmatches, String idequipe1, String idequipe2){
        initSuper();
        setIdMatches(idmatches);
        setIdEquipe1(idequipe1);
        setIdEquipe2(idequipe2);
    }

    public static Matches getMatches(String idMatches) throws Exception{

        Connection con = MyConnexion.getConnection();
        System.out.println("select * from Matches where idMatches ="+idMatches);
        ArrayList<Object> match = MyConnexion.sqltoArray(con, new Matches(), "select * from Matches where idMatches = '"+idMatches+"'");

        if(match.size() != 1){
    
            throw new Exception("invalid idMatches");
    
        }
    
       Matches val = (Matches) match.get(0);
       return val;
        
    }

    public void setIdMatches(String idMatches) {
        this.idMatches = idMatches;
    }
    public void setIdEquipe1(String idEquipe1) {
        this.idEquipe1 = idEquipe1;
    }
    public void setIdEquipe2(String idEquipe2) {
        this.idEquipe2 = idEquipe2;
    }

    public String getIdMatches() {
        return idMatches;
    }
    public String getIdEquipe1() {
        return idEquipe1;
    }
    public String getIdEquipe2() {
        return idEquipe2;
    }


    @Override
    public void initSuper() {
        setPkl(7);   
        setGetSequence("matchesSeq");
        setPrefix("MAT");
    }
    
}
