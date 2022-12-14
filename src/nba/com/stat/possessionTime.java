package nba.com.stat;

public class possessionTime{
    public double value;
    public String idJoueur;
    public String idEquipe;

    public possessionTime(double v, String idj, String ideq){
        setValue(v);
        //System.out.println(getValue());
        setIdJoueur(idj);
        //System.out.println(getIdJoueur());
        setIdEquipe(ideq);
    }

    public String getIdJoueur() {
        return idJoueur;
    }
    public double getValue() {
        return value;
    }
    public String getIdEquipe() {
        return idEquipe;
    }

    public void setIdJoueur(String idJoueur) {
        this.idJoueur = idJoueur;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public void setIdEquipe(String idEquipe) {
        this.idEquipe = idEquipe;
    }

}
