package Exercici_JDBC1;/*
Created by: Gusito
Date: 17/03/2021
Description: 
*/

public class Producte {
    //Atributos
    int ID;
    String Nombre;
    double precio;
    int stock;

    //Constructor de la clase
    public Producte(int ID,String Nombre, double precio,int stock){
        this.ID=ID;
        this.Nombre=Nombre;
        this.precio=precio;
        this.stock=stock;
    }

    //Getters & Setters
    public int getID() {
        return ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public double getPrecio() {
        return precio;
    }
    public int getStock() { return stock; }

    //Metodo para retornar la información del producto como String.
    @Override
    public String toString() {
        return ID+"\t"+Nombre+"\t"+precio+"\t"+"\t"+stock;
    }
}
