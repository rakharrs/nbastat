package nba.util.listener;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import dbObject.MyConnexion;
import nba.com.obj.Joueur;
import nba.com.stat.Detail;
import nba.com.stat.Statistique;
import nba.util.other.Action;

public class PlayerListener implements MouseInputListener{
    Joueur joueur;
    Detail detail;

    public PlayerListener(Joueur joueur, Detail detail){
        setJoueur(joueur);
        setDetail(detail);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(getDetail().isPaused()){
            return;
        }

        if(getDetail().getPossession() != getJoueur()){

            getDetail().setLastpossession(getDetail().getPossession());
            getDetail().setPossession(getJoueur());
    
            getDetail().getFrame().setTitle(getJoueur().getNomJoueur());

            try {
                Connection con = MyConnexion.getConnection();
                
                watchingFact(con);

                con.commit();
                con.close();
            } catch (Exception e1) {
 
                e1.printStackTrace();
            }

        }
    }

    public void watchingFact(Connection con) throws Exception{

        Statistique stat = new Statistique();
        stat.setIdJoueur(getDetail().getPossession().getIdJoueur());
        stat.setIdMatches(getDetail().getIdMatches());
        stat.setIdStatistique("default");

        Statistique recup = new Statistique();
        recup.setIdJoueur(getDetail().getPossession().getIdJoueur());
        recup.setIdMatches(getDetail().getIdMatches());
        recup.setIdStatistique("default");
    
        boolean flag = false;
        

        if(!getJoueur().getIdEquipe().equals(getDetail().getLastpossession().getIdEquipe())){

            if(getDetail().getLastStatNoPause() != null && getDetail().getLastStatNoPause().getIdAction() == Action.SHOOT){
                stat.setIdAction(Action.REBOND_DEF);

            }else{
                stat.setIdAction(Action.PASSE);
                recup.setIdAction(Action.INTERCEPTION);
                stat.setIdJoueur(getDetail().getLastpossession().getIdJoueur());
                flag = true;

            }

        }else{
            if(getDetail().getLastStatNoPause() != null && getDetail().getLastStatNoPause().getIdAction() == Action.SHOOT && getDetail().getLastStatNoPause().getPoint() == 0){
                stat.setIdAction(Action.REBOND_OFF);

            }else{
                
                stat.setIdAction(Action.PASSE);  
                recup.setIdAction(Action.GET_PASSE);
                stat.setIdJoueur(getDetail().getLastpossession().getIdJoueur());
                flag = true;
                //Statistique 
            }
        }


        stat.setTime(new Timestamp(System.currentTimeMillis()));

        stat.insert("statistique", con);
        if(flag){
            recup.setTime(new Timestamp(System.currentTimeMillis()));
            recup.insert("statistique", con);
            flag = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("ok");
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
        
    }

    public Joueur getJoueur() {
        return joueur;
    }
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }
    public Detail getDetail() {
        return detail;
    }
    public void setDetail(Detail detail) {
        this.detail = detail;
    }
    
}
