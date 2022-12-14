package nba.com.obj;

import java.sql.Connection;
import java.util.ArrayList;

import dbObject.Dbobj;
import dbObject.MyConnexion;

public class Equipe extends Dbobj{
    String idEquipe;
    String nomEquipe;

    public Equipe(){

    }
    public Equipe(String nomEquipe){

        initSuper();

        setNomEquipe(nomEquipe);

    }
    public Equipe(String idequipe, String nomequipe){

        initSuper();

        setNomEquipe(nomequipe);

        setIdEquipe(idequipe);
        
    }

    
    public static Equipe getEquipe(String idEquipe) throws Exception{

        Connection con = MyConnexion.getConnection();
        ArrayList<Object> equipe = MyConnexion.sqltoArray(con, new Equipe(), "select * from equipe where idEquipe ="+idEquipe);

        if(equipe.size() != 1){
    
            throw new Exception("invalid idEquipe");
    
        }
    
       Equipe val = (Equipe) equipe.get(0);
       return val;
        
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }
    public void setIdEquipe(String idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }
    public String getIdEquipe() {
        return idEquipe;
    }

    @Override
    public void initSuper() {
        setPrefix("EQP");
        setPkl(7);
        setGetSequence("equipeSeq");
    }
    
}
