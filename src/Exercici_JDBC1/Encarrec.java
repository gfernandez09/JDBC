package Exercici_JDBC1;/*
Created by: Gusito
Date: 17/03/2021
Description: 
*/
import java.util.Date;

public class Encarrec {
    //Atributos
    int ID;
    java.util.Date fecha;
    int idCliente;

    //Constructor de la clase
    public Encarrec(int ID, java.util.Date fecha, int idCliente){
        this.ID=ID;
        this.fecha=fecha;
        this.idCliente=idCliente;
    }

    //Getters & Setters
    public int getID() {
        return ID;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    //Metodo para retornar la información del encargo como String.
    @Override
    public String toString() {
        return ID+"\t"+fecha+"\t"+idCliente;
    }
}