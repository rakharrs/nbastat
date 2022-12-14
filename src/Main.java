import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import dbObject.Dbobj;
import dbObject.Default;
import dbObject.Dept;
import dbObject.MyConnexion;
import formAPI.display.Formulaire;
import nba.com.obj.Equipe;
import nba.com.obj.Matches;
import nba.com.stat.Possession;
import nba.com.stat.possessionTime;
import nba.util.listener.MakeMatchListener;
import nba.util.other.Action;

public class Main{
    public static void main(String[] args) throws Exception{
        /*Possession pos = new Possession("MAT0003");

        possessionTime[] times = pos.getIntervalleStat();
        for (int i = 0; i < times.length; i++) {
            System.out.println(times[i].getIdEquipe()+" et "+times[i].getValue());
        }
        possessionTime[] offs = pos.offsetInterval();

        for (int i = 0; i < offs.length; i++) {
            System.out.println(offs[i].getIdEquipe()+" and "+offs[i].getValue());
        }

        System.out.println("equipe 1 " + pos.getPossessionPercent("EQP0001")+"%");
        System.out.println("equipe 2 " +pos.getPossessionPercent("EQP0002")+"%");
        //System.out.println(53.84615384615385 + pos.getPossessionPercent("EQP0001"));

        //double a = 13000 + 8000 + 5000 + 7000 + 32000 + 5000 + 24000 + 10000 + 19000 + 4000 + 59000 + 6000;
        //double b = a / 1000;

        //double a1 = 13000 + 8000 + 5000 + 7000 + ;
        //double a1 = 
        /*for (int i = 0; i < pos.getStatjoueurs().size(); i++) {
            System.out.println(pos.getStatjoueurs().get(i).getIdjoueur());
        }*/

        //long a = pos.getActionTimeByPlayer("JOU0001");

        //System.out.println("eqp2 " + pos.getPossessionPercent("EQP0002")+"%");
        //System.out.println("eqp3 " +pos.getPossessionPercent("EQP0003")+"%");

        //pos.getIntervalleStat();*/

        


        JFrame jf = new JFrame();
        Formulaire formulaire = new Formulaire(new Matches());
        jf.add(formulaire);

        Connection co = MyConnexion.getConnection();
        ArrayList<Object> t = MyConnexion.sqltoArray(co, new Equipe(), "select * from equipe");

        jf.setSize(500, 500);

        formulaire.getButton().addMouseListener(new MakeMatchListener(jf, formulaire));

        formulaire.changeVisibility(0, false);
        //formulaire.setDefaultText(0, new Matches().constructPk());

        String[] idequipe = new String[t.size()];
        String[] nomequipe = new String[t.size()];

        for (int i = 0; i < t.size(); i++) {
            idequipe[i] = ((Equipe)t.get(i)).getIdEquipe();
            nomequipe[i] = ((Equipe)t.get(i)).getNomEquipe();
        }

        formulaire.setToDropdownlist(1, nomequipe, idequipe);
        formulaire.setToDropdownlist(2, nomequipe, idequipe);

        co.close();

        jf.setDefaultCloseOperation(3);
        jf.setVisible(true);
    }
}