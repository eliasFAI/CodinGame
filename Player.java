import java.util.*;
import java.io.*;
import java.math.*;


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        Barco barco ;
        Barril barril ;
       // ArrayList <Barco> listBarco = new ArrayList();
       // ArrayList <Barril> listBarril = new ArrayList();
        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();

                ArrayList <Barco> listBarco = new ArrayList();
                ArrayList <Barril> listBarril = new ArrayList();

                if(entityType.equals("SHIP")){
                
                    barco = new Barco(entityId,x,y,arg1,arg2,arg3,arg4);
                    listBarco.add(barco);
                    
                }
                else{

                    if(entityType.equals("BARREL")){
                    barril = new Barril(entityId,arg1,x,y);
                    listaBarril.add(barril);
                    }
                }
                System.err.println(""+entityId+" "+entityType+" "+x+" "+y+" "+arg1+" "+arg2+" "+arg3+" "+arg4);
            }
            for (int i = 0; i < myShipCount; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");
               // System.out.println("MOVE 10 9");// tengo esta linea pero no me visualiza en codingame
               barco = listBarco.get(0);
              // barco.getCoordenada_X
             System.out.println("MOVE barco.getCoordenada_X barco.getCoordenada_Y  "); // Any valid action, such as "WAIT" or "MOVE x y"
            }
        }
    }
}
class Punto{

    private int x ;
    private int y ; 

    public Punto(int x1 ,int y1 ){

        x = x1;
        y = y1;
    }
    public int getPunto_X(){
        return x;
    }
    public int getPunto_Y(){

        return y ;
    }
    public void setPunto_X(int x1){
      x = x1;

    }
    public void setPunto_Y(int y1){

        y = y1;
    }
 }
 

class Barco{

    private int id ;
    private int  orientacion ;
    private Punto coordenada ;
    private int velocidad ;
    private int stock_ron ;
    private int controla_barco ;

    public Barco(int i,int x,int y ,int arg1,int arg2,int arg3,int arg4){
      
        id = i ;
        coordenada = new Punto(x,y);
        orientacion = arg1;
        velocidad = arg2 ;
        stock_ron = arg3 ;
        controla_barco = arg4 ;

    }
    public int getCoordenada_X(){

        return coordenada.getPunto_X ;
    }
    public int getCoordenada_Y(){

        return coordenada.getPunto_Y ;
    }
    public Punto getCoordenadaPar(){

      return coordenada ;
    }
    public int getOrientacion(){

        return orientacion ;
    }
    public int getVelocidad(){

        return velocidad ;
    }
    public int getStock_ron(){

        return stock_ron;
    }
    public int controla_barco(){

        return controla_barco ;
    }
    public void setCoordenadaPar(Punto p){
     
          coordenada = p ;

    }
    public void setOrientacion(int o){
       orientacion = o ;
    }
    public void setVelocidad(int v){
     
         velocidad = v ;

    }
    public void setStock_ron(int r){

       stock_ron = r;
    }
    public void setControla_barco(int b){

        controla_barco = b;
    }
}
class Barril{

private int id ;
private int cantidad_ron ;
private Punto coordenada ;

public Barril(int id ,int arg1,int x1 ,int y1){
   id = id ;
  cantidad_ron = arg1 ;
  coordenada = new Punto(x1,y1);
}
public int getCoordenada(){

    return coordenada;
}
public void setCoordenada(Punto p){

  coordenada= p;
  
}
public int id(){

    return id ;
}
public int getcantidad_ron(){

    return cantidad_ron ;
}
public void setId(int id){
    id = id ;
}
public void setcantidad_ron(int r){

   cantidad_ron = r ;
}

}
