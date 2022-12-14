package nba.com.display;

import javax.swing.JButton;
import javax.swing.JFrame;

import nba.com.entit.Team;
import nba.com.obj.Matches;
import nba.com.stat.Detail;
import nba.util.component.PauseButton;
import nba.util.listener.ActionListener;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.*;

public class MatchFrame extends JFrame{

    Team[] teams = new Team[2];

    Matches match;

    TeamPanel panel1;

    TeamPanel panel2;

    Detail detail;

    JButton button;
    

    

    public MatchFrame(Matches match) throws Exception{

        setLayout(new GridLayout(1,3));

        setDefaultCloseOperation(3);

        setMatch(match);

        setDetail(new Detail(getMatch(), this));
        setFocusable(true);

        //this.addKeyListener(new ActionListener(this.getDetail()));

        setTitle(getDetail().getPossession().getNomJoueur());
        
        initTeams();
        initPanels();
        
    }
    public MatchFrame(String idMatches) throws Exception{

        setLayout(new GridLayout(2,1));

        setDefaultCloseOperation(3);

        setMatch(Matches.getMatches(idMatches));

        initTeams();
        initPanels();
    }

    public void initTeams() throws Exception{
        
        Team team1 = new Team(getMatch().getIdEquipe1());
        Team team2 = new Team(getMatch().getIdEquipe2());

        getTeams()[0] = team1;
        getTeams()[1] = team2;
    }

    public void initPanels(){

        setPanel1(new TeamPanel(getTeams()[0], getDetail()));
        setPanel2(new TeamPanel(getTeams()[1], getDetail()));

        add(getPanel1());

        //setButton(new PauseButton(getDetail()));
        setButton(new JButton("VS"));

        add(getButton());
        add(getPanel2());
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }
    public void setMatch(Matches match) {
        this.match = match;
    }
    public void setPanel1(TeamPanel panel1) {
        this.panel1 = panel1;
    }
    public void setPanel2(TeamPanel panel2) {
        this.panel2 = panel2;
    }public void setDetail(Detail detail) {
        this.detail = detail;
    }public void setButton(JButton button) {
        this.button = button;
    }

    public Team[] getTeams() {
        return teams;
    }
    public Matches getMatch() {
        return match;
    }
    public TeamPanel getPanel1() {
        return panel1;
    }
    public TeamPanel getPanel2() {
        return panel2;
    }public Detail getDetail() {
        return detail;
    }public JButton getButton() {
        return button;
    }
}
