package dbObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Dbobj extends MyConnexion{
    String prefix = null;
    int pkl=7;  /// Primary key length
    String getSequence = null; /// Fonction plsql maka anle sequence
    
    public int update(Connection con, Object value, String columnLabel, String tabname) throws Exception{
        String reference = "where ";
        String[] attributes = getAttribute(this);

        for(int i = 0; i < attributes.length; ++i){
            Object attrb = this.getClass().getMethod("get"+toUpperFirstChar(attributes[i])).invoke(this);
            reference += attributes[i]+"=";
            if(attrb.getClass() == Date.class){
                reference+="TO_DATE('"+attrb+"','YYYY-MM-DD') ";
            }else if(attrb.getClass() == String.class){
                reference+="'"+attrb+"' ";
            }else{
                reference+=attrb+" ";
            }
            if(i+1<attributes.length){
                reference += "and "; 
            }
        }

        return update(value, columnLabel, tabname, reference, con);
    }

    public abstract void initSuper();

    public void insert(String tabName, Connection con) throws Exception{
        insert(this, con, tabName);
    }

    public String constructPk() throws Exception{
        String val = new String();
        String pkSeq = buildSequenceNb();
        System.out.println(pkSeq);
        String prefix = getPrefix();
        if(getPrefix()==null){
            throw new Exception("No buildable primary key");
        }

        val+=prefix;
        val+=pkSeq;

        return val;
    }

    public String buildSequenceNb() throws Exception{
        String pkSeq = new String();

        Connection con = getConnection();
        String seq = getSequence(con);
        System.out.println(getPkl()-prefix.length()-seq.length());
        con.close();
        
        for (int i = 0; i < getPkl()-prefix.length()-seq.length(); i++) {
            pkSeq+="0";
        }
        for (int i = 0; i < seq.length(); i++) {
            pkSeq += seq.charAt(i);
        }
        System.out.println(pkSeq);
        return pkSeq;
    }

    public String getSequence(Connection con) throws Exception{
        if(getGetSequence()==null){
            throw new Exception("No buildable primary key");
        }
        Statement st = con.createStatement();
        String dbName = con.getMetaData().getDatabaseProductName();
        ResultSet rs = null;
        String val = null;
        switch (dbName) {
            case "PostgreSQL":
                rs = st.executeQuery("SELECT nextval('"+getGetSequence()+"') as nextval");
                System.out.println("ok");
                break;

            default:
                System.out.println("rjl");
                rs = st.executeQuery("SELECT "+ getGetSequence() + "as nextval from dual");
                break;
            }

        rs.next();
        val = rs.getString("nextval");
        rs.close(); st.close();

        return val;
    }

    public void historized(Connection con, String action, String tabname) throws Exception{
        String val = getAttValue();
        Date datehist = new Date(System.currentTimeMillis());

        History hist = new History(tabname, action, datehist, val);
        hist.setIDHIST(hist.constructPk());
        
        hist.insert("Historique", con);
    }

    public String getAttValue(String reference, Connection con){
        String val="";
        return null;
    }

    public String getAttValue() throws Exception{
        String val ="";

        String[] attributes = getAttribute(this);

        for(int i = 0; i < attributes.length; ++i){
            Object attrb = this.getClass().getMethod("get"+toUpperFirstChar(attributes[i])).invoke(this);
            val += attributes[i]+"="+attrb+";";
        }

        return val;
    }
    
    public String getGetSequence() {
        return getSequence;
    }
    public int getPkl() {
        return pkl;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setGetSequence(String getSequence) {
        this.getSequence = getSequence;
    }
    public void setPkl(int pkl) {
        this.pkl = pkl;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
