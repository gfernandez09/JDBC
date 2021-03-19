package Exercici_JDBC1;/*
Created by: Gusito
Date: 18/03/2021
Description: 
*/

public class EncarrecProducte {
    //Atributos
    int IDEncarrec;
    int IDProducte;
    int numArticles;

    //Constructor
    public EncarrecProducte(int IDEncarrec, int IDProducte, int numArticles) {
        this.IDEncarrec = IDEncarrec;
        this.IDProducte = IDProducte;
        this.numArticles = numArticles;
    }

    public int getIDEncarrec() {
        return IDEncarrec;
    }

    public int getIDProducte() {
        return IDProducte;
    }

    public int getQuantitat() {
        return numArticles;
    }

    //Metodo para retornar la información del encargo como String.
    @Override
    public String toString() {
        return IDEncarrec+"\t"+IDProducte+"\t"+numArticles;
    }
}
