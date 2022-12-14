package dbObject;
import java.sql.Connection;

public class Dept extends MyConnexion{
    int DEPTNO;
    String DNAME;
    String LOC;

    public Dept(){
        super();
    }

    
    public Dept(int deptno, String Dname, String loc){
        super();
        setDEPTNO(deptno);
        setDNAME(Dname);
        setLOC(loc);
    }

    public void save() throws Exception{
        Connection con = getConnection();
        insert(this, con, "dept");
        con.commit();
        con.close();
    }

    /*public void update(String columnLabel, Object value, String reference) throws Exception{
        Connection con = getConnection();
        update(value, columnLabel, "dept", reference, con);
        con.commit();
        con.close();
    }*/
    
    public int getDEPTNO() {
        return DEPTNO;
    }public String getDNAME() {
        return DNAME;
    }public String getLOC() {
        return LOC;
    }
    public void setDEPTNO(int dEPTNO) {
        DEPTNO = dEPTNO;
    }public void setDNAME(String dNAME) {
        DNAME = dNAME;
    }public void setLOC(String lOC) {
        LOC = lOC;
    }
}