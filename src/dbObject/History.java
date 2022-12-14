package dbObject;

import java.sql.Date;

public class History extends Dbobj{
    String IDHIST;
    String TAB;
    String ACTION;
    Date DATEHIST;
    String VAL;

    public History(String tab, String action, Date datehist, String val){
        setACTION(action);
        setDATEHIST(datehist);
        setTAB(tab);
        setVAL(val);
        super.setPkl(7);
        super.setPrefix("HST");
        super.setGetSequence("GETHISTSEQ");
    }

/* Getter & setters */
    public String getIDHIST() {
        return IDHIST;
    }
    public String getTAB() {
        return TAB;
    }
    public String getACTION() {
        return ACTION;
    }
    public Date getDATEHIST() {
        return DATEHIST;
    }
    public String getVAL() {
        return VAL;
    }
    public void setIDHIST(String iDHIST) {
        IDHIST = iDHIST;
    }
    public void setTAB(String tAB) {
        TAB = tAB;
    }
    public void setACTION(String aCTION) {
        ACTION = aCTION;
    }
    public void setDATEHIST(Date dATEHIST) {
        DATEHIST = dATEHIST;
    }
    public void setVAL(String vAL) {
        VAL = vAL;
    }

    @Override
    public void initSuper() {

    }
}