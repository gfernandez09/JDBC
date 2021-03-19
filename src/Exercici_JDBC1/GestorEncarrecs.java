package Exercici_JDBC1;/*
Created by: Gusito
Date: 12/03/2021
Description: 
*/
import java.io.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class GestorEncarrecs {
    GestorBD gestor;
    BufferedReader entrada;
    Connection conn;

    public static void main(String[] args) throws Exception {
        GestorEncarrecs gbd = new GestorEncarrecs();
        gbd.start();
    }
    public GestorEncarrecs() throws Exception{
        gestor = new GestorBD();
        entrada = new BufferedReader(new InputStreamReader(System.in));
    }
    public void start() throws Exception{
        int opcio;
        while (0 !=(opcio = menuPrincipal())){
            try{
                switch (opcio){
                    case 1:
                        cercarClient();
                        break;
                    case 2:
                        afegirClient();
                        break;
                    case 3:
                        afegirProducte();
                        break;
                    case 4:
                        llistarProductes();
                        break;
                    case 5:
                        afegirEncarrec();
                        break;
                    case 6:
                        borrarEncarrec();
                        break;
                    case 7:
                        cercarEncarrec();
                        break;
                    case 8:
                        reabastecerProducto();
                        break;
                    default: mostrarDades("Opció incorrecta\n");
                }
            } catch (Exception ex){
                mostrarDades("S'ha produit un error: " + ex + "\n");
            }
        }
        gestor.tancar();
    }

    private int menuPrincipal() throws Exception {
        String menu = "\nQuina acció vols realitzar?\n" + "[0] Sortir\n"+"[1] Cercar client\n" + "[2] Afegir client\n" + "[3] Afegir Producte\n"
                + "[4] Llistar Productes\n" + "[5] Afegir Encarrec\n" + "[6] Borrar Encarrec\n" + "[7] Cercar Encarrec \n" + "[8] Abastir Producte \n" +"Opció>";
        String lin = entrarDades(menu);
        try { int opcio = Integer.parseInt(lin); return opcio; }
        catch (Exception ex) { return -1; }

    }
    private String entrarDades(String pregunta) throws IOException {
        mostrarDades(pregunta);
        return entrarDades();
    }
    private String entrarDades() throws IOException {
        String linia = entrada.readLine();
        if ("".equals(linia)) return null;
        return linia;
    }
    private void mostrarDades(String dades) throws IOException {
        System.out.print(dades);
    }
    //Cercar un client d'acord al seu nom
    private void cercarClient() throws Exception {
        String nom = entrarDades("Introdueix el nom del client: ");
        if (null == nom) return;
        List<Client> llista = gestor.cercarClient(nom);
        Iterator it = llista.iterator();
        mostrarDades("Els clients trobats amb aquest nom son:\n−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−\n");
        while(it.hasNext()) {
            Client c = (Client)it.next();
            mostrarDades(c.toString() + "\n");
        }
    }

    //Afegeix un nou client
    public void afegirClient() throws Exception{
        mostrarDades("Introdueix les seguents dades del nou client (deixa en blanc per sortir).\n");
        String nom = entrarDades("Nom: "); if (null == nom) return;
        String apostal = entrarDades("Adreça postal: "); if (null == apostal) return;
        String aelectronica = entrarDades("E-mail: "); if (null == aelectronica) return;
        String telefon = entrarDades("Telefon: "); if (null == telefon) return;
        int id = gestor.obtenirNouIDClient();
        gestor.afegirClient(new Client(id,nom,apostal,aelectronica,telefon));
        mostrarDades("Operació completada satisfactòriament.\n");
    }
    /*Llistar tots els productes disponibles*/
    private void llistarProductes() throws Exception {
        List<Producte> llista = gestor.llistarProductes();
        Iterator it = llista.iterator();
        mostrarDades("Els productes trobats son:\n−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−\n");
        System.out.println("ID"+"\t"+"Nombre"+"\t"+"Precio"+"\t"+"Stock");
        while(it.hasNext()) {
            Producte c = (Producte) it.next();
            mostrarDades(c.toString() + "\n");
        }
    }
    /*Cercar encarrec a partir del seu ID*/
    private void cercarEncarrec() throws Exception {
        int ID=Integer.parseInt(entrarDades("Introdueix l'identificador del client per trobar l'encarrec: "));
        if (0==ID) return;
        List<Encarrec> llista=gestor.cercarEncarrec(ID);
        Iterator it= llista.listIterator();
        mostrarDades("Els encarrecs trobats amb aquest ID son:\n−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−\n");
        System.out.println("ID"+"\t"+"Fecha"+"\t"+"ID Cliente");
        while (it.hasNext()){
            Encarrec e=(Encarrec) it.next();
            mostrarDades(e.toString()+"\n");
        }
    }
    /*Afegir un Encarrec y també, afegir els productes del encarrec a la taula EncarrecProducte*/
    public void afegirEncarrec() throws Exception{
        mostrarDades("Introdueix dades del nou encarrec (deixa en blac per sortir.)\n");
        int ID = gestor.obtenirNouIDEncarrec();
        //Le damos formato para poder introducir bien la fehca en la base de datos.
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fecha =format.parse(entrarDades("Fecha: ")); if (null==fecha) return;
        int IDCliente =Integer.parseInt(entrarDades("Introdueix el identificador del client: ")); if (0 == IDCliente) return;
        gestor.afegirEncarrec(new Encarrec(ID,gestor.conversor(fecha),IDCliente));
        afegirEncarrecProducte(ID);
        mostrarDades("Operació completada satisfactoriament.\n");
    }
    /*Borrar Encarrec segon el seu ID*/
    public void borrarEncarrec() throws Exception {
        int ID=Integer.parseInt(entrarDades("Introdueix l'identificador de l'encarrec: ")); if (0==ID) return;
        gestor.borrarEncarrecProducte(ID);
        gestor.borrarEncarrec(ID);
    }
    /*Afegir un nou producte*/
    public void afegirProducte() throws Exception{
        mostrarDades("Introdueix les seguents dades del nou client (deixa en blanc per sortir).\n");
        int ID = gestor.obtenirNouIDProducte();
        String Nombre = entrarDades("Nombre del producto: "); if (null == Nombre) return;
        double Precio = Double.parseDouble(entrarDades("Precio del producto: ")); if (0==Precio) return;
        int Stock=Integer.parseInt(entrarDades("Stock del Producto: ")); if (0==Stock) return;
        gestor.afegirProducte(new Producte(ID,Nombre,Precio,Stock));
        mostrarDades("Operació completada satisfactoriament.\n");
    }
    /*Mètode per afegir el Encarrec Producte*/
    public void afegirEncarrecProducte(int IDEncarrec) throws Exception {
        int NumProductes = Integer.parseInt(entrarDades("Numero de productes per aficar dins l'encàrrec: ")); if (0==NumProductes) return;
        for (int i = 0; i < NumProductes ; i++) {
            String Nombre = entrarDades("Nom del producte: "); if (null == Nombre) return;
            int numQuant=Integer.parseInt(entrarDades("Introdueix la quantitat del producte: ")); if (0==numQuant) return;
            gestor.afegirEncProd(IDEncarrec,Nombre,numQuant);
        }
    }
    /*Mètode per abastir un producte*/
    public void reabastecerProducto()throws Exception{
        String nom = entrarDades("Nom del producte: "); if (null == nom) return;
        int numQuant=Integer.parseInt(entrarDades("Quantitat a abastir del producte: ")); if (0==numQuant) return;
        gestor.reabastecerProducto(nom,numQuant);
        mostrarDades("Operació completada satisfactoriament.\n");
        if(numQuant == 1){
            mostrarDades("S'ha abastit " + numQuant + " quantitat" + " del producte " + nom);
        }else{
            mostrarDades("S'han abastit " + numQuant + " quantitats" + " del producte " + nom);
        }

    }
}
