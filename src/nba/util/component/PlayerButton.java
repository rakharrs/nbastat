package nba.util.component;

import javax.swing.JButton;

import nba.com.obj.Joueur;
import nba.com.stat.Detail;
import nba.util.listener.ActionListener;
import nba.util.listener.PlayerListener;

public class PlayerButton extends JButton{
    Joueur joueur;
    Detail detail;

    public PlayerButton(Joueur joueur, Detail detail){
        setJoueur(joueur);
        setDetail(detail);

        setText(getJoueur().getNomJoueur());

        addKeyListener(new ActionListener(getDetail()));
        addMouseListener(new PlayerListener(getJoueur(), getDetail()));
        setFocusable(true);
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
        setText(getJoueur().getNomJoueur());
    }

    public Joueur getJoueur() {
        return joueur;
    }
    
    public Detail getDetail() {
        return detail;
    }
    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}
