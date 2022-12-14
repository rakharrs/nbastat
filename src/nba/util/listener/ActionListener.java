package nba.util.listener;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.SQLException;

import dbObject.MyConnexion;
import nba.com.entit.Team;
import nba.com.stat.Detail;
import nba.util.Exception.LastActionException;
import nba.util.other.Action;
import nba.util.other.NbdAction;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class ActionListener implements KeyListener{
    Detail detail;

    public ActionListener(Detail detail){
        System.out.println("ACTION");
        setDetail(detail);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(getDetail().isPaused()){
            return;
        }
        
        System.out.println("typed: "+e.getKeyChar());
        Connection con;
        try {
            con = MyConnexion.getConnection();
            String idmat = getDetail().getIdMatches();

            if(getDetail().isLasAction(Action.REBOND_DEF)){

                throw new LastActionException("Tir impossible");
            }

            if(e.getKeyChar() == 't' || e.getKeyCode() == KeyEvent.VK_T){

                System.out.println("T");

                checkPASSE_D(con, idmat);
                niceShoot(con, idmat, 2);


            }else if(e.getKeyChar() == 'r' || e.getKeyCode() == KeyEvent.VK_R){
                
                System.out.println("R");
                getDetail().getPossession().make(con, Action.SHOOT, idmat, 0);
                    
            }else if(e.getKeyChar() == 'f' || e.getKeyCode() == KeyEvent.VK_F){

                System.out.println("F");

                checkPASSE_D(con, idmat);
                niceShoot(con, idmat, 3);

            }

            con.commit();
            con.close();
        }catch (LastActionException e2){

            JOptionPane.showMessageDialog(new JFrame(), e2.getMessage());
            System.out.println(e2);
        } catch (Exception e1) {

            JOptionPane.showMessageDialog(new JFrame(), e1.getMessage());
            e1.printStackTrace();
        }
    }

    public void checkPASSE_D(Connection con, String idMatches) throws Exception{
        if(getDetail().getLastpossession().getIdEquipe().equals(getDetail().getPossession().getIdEquipe()) &&
                getDetail().getLastStatNoPause().getIdAction() == Action.GET_PASSE){

            getDetail().getLastpossession().make(con, Action.PASSE_D, idMatches, 0);

        }
    }

    public void niceShoot(Connection con, String idMatches, int point) throws Exception{

        getDetail().getPossession().make(con, Action.SHOOT, idMatches, point);

        getDetail().setLastpossession(getDetail().getPossession());

        Team posOpponent = getDetail().getOpponent(getDetail().getPossession().getIdEquipe());

        getDetail().setPossession(posOpponent.getJoueur(0));
        getDetail().possessionRecup();

        getDetail().getFrame().setTitle(getDetail().getPossession().getNomJoueur());
    }

    public String getOpponent(){

        return null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}
