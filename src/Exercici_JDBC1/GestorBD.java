package Exercici_JDBC1;/*
Created by: Gusito
Date: 12/03/2021
Description: 
*/

import java.util.*;
import java.sql.*;

public class GestorBD {
    Connection conn;

    public GestorBD() throws Exception{
        String userName = "sqljava";
        String password = "soncladeragusty";
        String url = "jdbc:MySQL://localhost:3306/gestioEncarrecs";
        conn = DriverManager.getConnection (url, userName, password);
    }
    public void tancar() throws Exception{
        conn.close();
    }

    public int obtenirNouIDClient() throws Exception{
        //Cercar ID Maxim
        Statement cercaMaxId = conn.createStatement();
        ResultSet r = cercaMaxId.executeQuery("SELECT MAX(ID) FROM CLIENTS");
        if(r.next()) return (1 + r.getInt(1));
        else return 1;
    }
    public int obtenirNouIDEncarrec() throws Exception{
        //Cercar ID Maxim
        Statement cercaMaxId = conn.createStatement();
        ResultSet r = cercaMaxId.executeQuery("SELECT MAX(ID) FROM ENCARRECS");
        if(r.next()) return (1 + r.getInt(1));
        else return 1;
    }
    public int obtenirNouIDProducte() throws Exception{
        //Cercar ID Maxim
        Statement cercaMaxId = conn.createStatement();
        ResultSet oNP = cercaMaxId.executeQuery("SELECT MAX(ID) FROM PRODUCTES");
        if(oNP.next()) return (1 + oNP.getInt(1));
        else return 1;
    }
    public List<Client> cercarClient(String nom) throws Exception{
        Statement cerca = conn.createStatement();
        ResultSet Cc = cerca.executeQuery("SELECT * FROM CLIENTS WHERE NOM='" + nom + "'");
        LinkedList<Client> llista = new LinkedList<>();
        while (Cc.next()){
            llista.add(new Client(Cc.getInt("ID"),Cc.getString("NOM"),Cc.getString("APOSTAL"),Cc.getString("AELECTRONICA"),Cc.getString("TELEFON")));
        }
        return llista;
    }

    public void afegirClient(Client aC) throws Exception{
        Statement update = conn.createStatement();
        String valors = aC.getId()+", '"+aC.getNom()+"','"+aC.getApostal()+"','"+aC.getAelectronica()+"','"+aC.getTelefon()+"'";
        update.executeUpdate("INSERT INTO CLIENTS VALUES (" + valors + ")");
    }
    public List<Encarrec> cercarEncarrec(int ID) throws Exception{
        Statement cerca= conn.createStatement();
        ResultSet r = cerca.executeQuery("SELECT * FROM ENCARRECS WHERE IDCLIENT='"+ID+"'");
        LinkedList<Encarrec> llista=new LinkedList<>();
        while (r.next()){
            llista.add(new Encarrec(r.getInt("ID"),r.getDate("DATA"),r.getInt("IDCLIENT")));
        }
        return llista;
    }
    public List<Producte> llistarProductes() throws Exception{
        Statement cerca = conn.createStatement();
        ResultSet lP = cerca.executeQuery("SELECT * FROM PRODUCTES");
        LinkedList<Producte> llista = new LinkedList<>();
        while (lP.next()){
            llista.add(new Producte(lP.getInt("ID"), lP.getString("NOM"),lP.getDouble("PREU"),lP.getInt("STOCK")));
        }
        return llista;
    }

    public void reabastecerProducto(String nom,int numQuant) throws SQLException {
        Statement update = conn.createStatement();
        update.execute("UPDATE PRODUCTES SET STOCK=STOCK+" + numQuant + " WHERE NOM='" + nom + "'" );
    }
    public void afegirEncarrec(Encarrec aE) throws Exception{
        Statement update = conn.createStatement();
        String valors = aE.getID()+", '"+aE.getFecha()+"','"+aE.getIdCliente()+"'";
        update.executeUpdate("INSERT INTO ENCARRECS VALUES (" + valors + ")");
    }
    public void borrarEncarrec(int ID) throws Exception{
        Statement update = conn.createStatement();
        update.executeUpdate("DELETE FROM ENCARRECS WHERE ID ='" + ID + "'");
    }

    public void afegirProducte(Producte aP) throws Exception{
        Statement update = conn.createStatement();
        String valors = aP.getID()+", '"+aP.getNombre()+"','"+aP.getPrecio()+"','"+aP.getStock()+"'";
        update.executeUpdate("INSERT INTO PRODUCTES VALUES (" + valors + ")");
    }

    public void updateProducteEncarrec(int producto, int numQuant) throws SQLException {
        Statement update = conn.createStatement();
        update.execute("UPDATE PRODUCTES SET STOCK=STOCK-" + numQuant + " WHERE ID='" + producto + "'" );
    }
    public void afegirEncProd(int idEncarrec, String producte, int quantitat) throws Exception{
        int idProducte=devolverIDProducto(producte);
        Statement update= conn.createStatement();
        String valors = idEncarrec+",'"+idProducte+"','"+quantitat+"'";
        update.executeUpdate("INSERT INTO ENCARRECSPRODUCTES VALUE("+valors+")");
        updateProducteEncarrec(idProducte,quantitat);
    }

    public int devolverIDProducto(String Nombre) throws SQLException{
        int idProducte = 0;
        Statement cerca = conn.createStatement();
        ResultSet rs= cerca.executeQuery("SELECT * FROM PRODUCTES WHERE NOM='" + Nombre + "'");
        while (rs.next()) {
            idProducte = rs.getInt("ID");
        }
        return idProducte;
    }

    //Convertir el tipo Date para ser usado en el mySQL
    public Timestamp conversor(java.util.Date fecha){
        Timestamp fechaSQL=new Timestamp(fecha.getTime());
        return fechaSQL;
    }

}
