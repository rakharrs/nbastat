package nba.com.display;

import java.util.ArrayList;

import javax.swing.JPanel;

import nba.com.entit.Team;
import nba.com.obj.Joueur;
import nba.com.stat.Detail;
import nba.util.component.PlayerButton;
import nba.util.listener.PlayerListener;

public class TeamPanel extends JPanel{
    Team team;
    PlayerButton[] playerButtons = new PlayerButton[5];
    Detail detail;

    public TeamPanel(){
        
    }

    public TeamPanel(Team team, Detail detail){

        setTeam(team);

        setDetail(detail);

        initPan();
    }

    public TeamPanel(String idEquipe, Detail detail) throws Exception{

        setTeam(new Team(idEquipe));

        setDetail(detail);

        initPan();
    }

    public void enableAllButtons(boolean flag){
        for (int i = 0; i < playerButtons.length; i++) {
            playerButtons[i].setEnabled(flag);
        }
    }

    public void initPan(){

        initPlayerButtons();

        addPlayerButtons();

        drawPanel();

        setVisible(true);
    }

    private void initPlayerButtons(){
        Joueur[] joueurs = getTeam().getJoueurs();

        for (int i = 0; i < getPlayerButtons().length; i++) {

            getPlayerButtons()[i] = new PlayerButton(joueurs[i], getDetail());
        }

    }

    public void addPlayerButtons(){

        for (int i = 0; i < getPlayerButtons().length; i++) {

            add(getPlayerButtons()[i]);
        }
    }

    public void drawPanel(){

        int dx = getWidth()/5, dy = (getHeight())/100, 

        height = (getHeight()*15)/100, width = (getWidth()*25)/100, 
        
        count = 0;

        for (int i = 0; i < getPlayerButtons().length; i++) {
            if(i%2==0){

                dy += (height+10); dx = getWidth()/5; 
                
                count = 0;

                if (i==4) {

                    dx = (getWidth()/5) + 150;
                }
            }

            getPlayerButtons()[i].setBounds(dx + (count * 300), dy, width, height);

            count++;
        }
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    public Team getTeam() {
        return team;
    }
    public PlayerButton[] getPlayerButtons() {
        return playerButtons;
    }
    public void setPlayerButtons(PlayerButton[] playerButtons) {
        this.playerButtons = playerButtons;
    }
    public void setDetail(Detail detail) {
        this.detail = detail;
    }
    public Detail getDetail() {
        return detail;
    }


    
}
