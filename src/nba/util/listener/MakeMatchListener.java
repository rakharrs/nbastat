package nba.util.listener;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.sound.midi.Synthesizer;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import dbObject.Default;
import dbObject.MyConnexion;
import formAPI.display.Formulaire;
import formAPI.inc.Dropdownlist;
import nba.com.display.MatchFrame;
import nba.com.obj.Equipe;
import nba.com.obj.Matches;
import org.postgresql.util.PSQLException;

public class MakeMatchListener implements MouseInputListener{
    JFrame frame;
    Formulaire form;

    public MakeMatchListener(JFrame frame, Formulaire formulaire){
        setFrame(frame);
        setForm(formulaire);
        
    }

    public void setForm(Formulaire form) {
        this.form = form;
    }
    public Formulaire getForm() {
        return form;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    public JFrame getFrame() {
        return frame;
    }

    public String getParameter(int index) throws Exception{

        String val = null;

        if(getForm().getChamps().get(index).getComponent().getClass() == Dropdownlist.class){

            Dropdownlist ddl = (Dropdownlist) getForm().getChamps().get(index).getComponent();
            int i = ddl.getSelectedIndex();
            val = ddl.getValues()[i];

        }else{
            throw new Exception("Invalid champ");
        }

        return val;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        try {
            Connection con = MyConnexion.getConnection();

            String idEquipe1 = getParameter(1);
            String idEquipe2 = getParameter(2);
            Matches match = new Matches(idEquipe1, idEquipe2);

            match.setIdMatches(match.constructPk());

            match.insert("matches", con);

            con.commit();
            con.close();

            MatchFrame mf = new MatchFrame(match);
            mf.setSize(500, 500);
            mf.setVisible(true);

            getFrame().dispose();
            
        }catch (PSQLException ex){
            JOptionPane.showMessageDialog(new JFrame(), "Vérifier les données Saisie\n " +
                    "par exemple :\n" +
                    " equipe 1 doit être différent de equipe 2");
        }
        catch (Exception exc) {
            JOptionPane.showMessageDialog(new JFrame(), exc.getMessage());
            exc.printStackTrace();
        }
        
        
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
    
}
