package nba.util.listener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import dbObject.MyConnexion;
import nba.com.stat.Detail;
import nba.com.stat.Statistique;
import nba.util.component.PauseButton;
import nba.util.other.Action;

public class PauseListener implements MouseInputListener{
    PauseButton button;
    Detail detail;

    public PauseListener(Detail detail, PauseButton b){
        setButton(b);
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!getButton().getDetail().isPaused()){
            try {
                if(getDetail().isLasAction(Action.SHOOT)){
                    JOptionPane.showMessageDialog(new JFrame(), "Choisissez d'abord un rebond");
                    return;

                }
                insertPAUSE_BEGIN();
    
                getButton().getDetail().setPaused(true);
                getButton().getDetail().enableAllButton(false);
                //getButton().getDetail();
                getButton().setText("PLAY");
                
                
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }else{
            try {
                insertPAUSE_END();
                
                getButton().getDetail().setPaused(false);
                getButton().getDetail().enableAllButton(true);
                getButton().setText("PAUSE");

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
        
    }
    
    public void insertPAUSE_BEGIN() throws Exception{
        Connection con = MyConnexion.getConnection();
        insertPAUSE_BEGIN(con);

        con.commit();
        con.close();
    }

    public void insertPAUSE_END() throws Exception{
        Connection con = MyConnexion.getConnection();
        insertPAUSE_END(con);

        con.commit();
        con.close();
    }

    public void insertPAUSE_BEGIN(Connection con) throws Exception{

        Statistique stat = new Statistique();
        stat.setIdStatistique("default");
        stat.setIdAction(Action.PAUSE_BEGIN);
        stat.setIdJoueur(getButton().getDetail().getPossession().getIdJoueur());
        stat.setIdMatches(getButton().getDetail().getIdMatches());
        stat.setTime(new Timestamp(System.currentTimeMillis()));
        stat.setPoint(0);

        stat.insert("statistique", con);
    }

    public void insertPAUSE_END(Connection con) throws Exception{
        Statistique stat = new Statistique();
        stat.setIdStatistique("default");
        stat.setIdAction(Action.PAUSE_END);
        stat.setIdJoueur(getButton().getDetail().getPossession().getIdJoueur());
        stat.setIdMatches(getButton().getDetail().getIdMatches());
        stat.setTime(new Timestamp(System.currentTimeMillis()));
        stat.setPoint(0);

        stat.insert("statistique", con);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        
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
    public void setButton(PauseButton button) {
        this.button = button;
    }
    public PauseButton getButton() {
        return button;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}
