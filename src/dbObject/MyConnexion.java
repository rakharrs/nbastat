package dbObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;

public class MyConnexion {

    public static Connection getConnectionOracle() throws Exception{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection=DriverManager.getConnection("jdbc:oracle:thin:@192.168.43.111:1521:orcl", "daniel", "daniel");
        connection.setAutoCommit(false);
        return connection;
    }
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        //Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nbastat?user=postgres&password=pixel");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nba2?user=postgres&password=pixel");
        connection.setAutoCommit(false);
        return connection;
    }

    public static int getfirstInt(Connection con, String table, String retour, String predicat) throws Exception{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT "+ retour +" FROM " + table + " WHERE " + predicat);
        rs.next();

        int val = rs.getInt(1);

        return val;
    }

    public static String getfirstString(Connection con, String request) throws Exception{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(request);
        rs.next();

        String val = rs.getString(1);

        return val;
    }

    public static String toUpperFirstChar(String str){
        String tmp = str.substring(0,1);
        str=str.substring(1);
        tmp=tmp.toUpperCase();
        str = tmp.concat(str);
        return str;
    }

    public static String[] getAttribute(Object object){
        Field[] fields = object.getClass().getDeclaredFields();
        String[] AttributesName = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            AttributesName[i]=fields[i].getName();
        }

        return AttributesName;
    }

    public static Class<?>[] getFieldType(Object object){
        Field[] fields = object.getClass().getDeclaredFields();
        Class<?>[] fieldsType = new Class<?>[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldsType[i] = fields[i].getType();
        }
        return fieldsType;
    }

    public static String[] getFieldTypeName(Class<?>[] fieldsType){
        String[] val = new String[fieldsType.length];
        for (int i = 0; i < fieldsType.length; i++) {
            String[] fieldTypeNames = fieldsType[i].getTypeName().split("\\.");
            try {
                String fieldTypeName = fieldTypeNames[fieldTypeNames.length-1];
                val[i] = fieldTypeName;

            } catch (Exception e) {
                val[i]=fieldsType[i].getTypeName();
            }
        }
        return val;
    }

    public static String[] getFieldTypeName(Object obj){
        Class<?>[] fieldsType = getFieldType(obj);
        String[] val = new String[fieldsType.length];
        for (int i = 0; i < fieldsType.length; i++) {
            String[] fieldTypeNames = fieldsType[i].getTypeName().split(".");
            String fieldTypeName = fieldTypeNames[fieldTypeNames.length-1];
            val[i] = fieldTypeName;
        }
        return val;
    }

    public static ArrayList<Object> sqltoArray(Object obj, ResultSet rs) throws SQLException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String[] fieldName = getAttribute(obj);
        ArrayList<Object> val = new ArrayList<>();
        Class<?>[] fieldType = getFieldType(obj);
        String[] fieldTypeName = getFieldTypeName(fieldType);
        while(rs.next()){
            Object t = obj.getClass().getConstructor().newInstance();
            for (int i = 0; i < fieldType.length; i++) {
                Method getArg = rs.getClass().getMethod("get"+toUpperFirstChar(fieldTypeName[i]) , String.class);
                getArg.setAccessible(true);
                Object getRsField = getArg.invoke(rs,fieldName[i]);
                Method setField=t.getClass().getMethod("set"+toUpperFirstChar(fieldName[i]), fieldType[i]);
                setField.setAccessible(true);
                setField.invoke(t, getRsField);
            }
            val.add(t);
            t=null;
        }

        return val;
    }

    public static ArrayList<Object> sqltoArray(Connection con, Object obj, String request) throws Exception{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(request);

        ArrayList<Object> val = MyConnexion.sqltoArray(obj, rs);

        return val;
    }

    public ArrayList<Object> select(String reference, String tabname) throws Exception{
        ArrayList<Object> val = new ArrayList<>();
        Connection con = getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM "+ tabname +" WHERE " + reference);
        val = sqltoArray(this,rs);
        con.commit();
        rs.close();
        st.close();
        con.close();
        return val;
    }

    public static ArrayList<Object> select(Object obj, String reference, String tabname) throws Exception{
        ArrayList<Object> val = new ArrayList<>();
        Connection con = getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tabname + " WHERE "  + reference);
        val = sqltoArray(obj,rs);
        con.commit();
        rs.close();
        st.close();
        con.close();
        return val;
    }

    public static void insert(Object obj, Connection con, String tabName) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException{
        Statement st = con.createStatement();
        String[] attributes = getAttribute(obj);
        String into = TabToString(",", attributes);
        String insert = "INSERT INTO "+tabName+"("+into+") VALUES (";
        for (int i = 0; i < attributes.length; i++) {

            Object attrb = obj.getClass().getMethod("get"+toUpperFirstChar(attributes[i])).invoke(obj);
            if(attrb == null){

                System.out.println("DMSKGHJGDSJH : \t" + "get"+toUpperFirstChar(attributes[i]));
            }

            if(attrb.getClass()==Date.class){
                insert+="TO_DATE('"+attrb+"','YYYY-MM-DD')";
            }else if(attrb.getClass()==String.class && !((String)attrb).equals("default")){
                insert+="'"+attrb+"'";
            }else if(attrb.getClass()==Timestamp.class){
                insert+="TO_TIMESTAMP('"+attrb+"','YYYY-MM-DD HH24:MI:SS')";
            }else{
                insert+=attrb;
            }
            if(i < attributes.length-1){
                insert+=",";
            }
        }
        insert+=")";
        System.out.println(insert);
        int test = st.executeUpdate(insert);
        System.out.println(test);
        st.close();
    }

    public static String TabToString(String regex,String... str){
        String val = new String();
        for (int i = 0; i < str.length; i++) {
            val += str[i];
            if(i < str.length - 1){
                val+=regex;

            }
        }
        return val;
    }

    public static int update(Object value, String columnLabel,String tabName, String reference, Connection con) throws SQLException{
        Statement st = con.createStatement();
        String update = "update "+tabName+" set "+columnLabel+" = ";
        if(value.getClass() == Date.class){
            update+="TO_DATE('"+value+"','YYYY-MM-DD')";
        }else if(value.getClass() == String.class){
            update+="'"+value+"'";
        }else{
            update+=value;
        }
        update+= " "+reference;

        //ResultSet rs = st.executeQuery(update);
        //rs.close();
        return st.executeUpdate(update);
    }

    public static void update(Object[] values, String[] columnLabels,String tabName, String reference, Connection con) throws SQLException{
        for (int i = 0; i < values.length; i++) {
            update(values[i], columnLabels[i], tabName, reference, con);
        }
    }

}