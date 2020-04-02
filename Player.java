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
        Barco barco ,barcoAdversario ;
        Barril barril ;
        Punto coordSeleccion = null ,coordDisparo=null;
        double minAdversario;
        ArrayList <Barco> listBarco  = null;
        ArrayList <Barril> listBarril = null;
        ArrayList <Mina> listMina = null ;
        
        // game loop
        while (true) {
            int myShipCount = in.nextInt(); // the number of remaining ships
            int entityCount = in.nextInt(); // the number of entities (e.g. ships, mines or cannonballs)
            listBarco = new ArrayList();
            listBarril = new ArrayList();
            listMina = new ArrayList();
            
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int x = in.nextInt();
                int y = in.nextInt();
                int arg1 = in.nextInt();
                int arg2 = in.nextInt();
                int arg3 = in.nextInt();
                int arg4 = in.nextInt();

 
                
                if(entityType.equals("SHIP")){
                   
                    
                     listBarco.add(new Barco(entityId,x,y,arg1,arg2,arg3,arg4));
                    
                }
                else{

                    if(entityType.equals("BARREL")){
                        
                    listBarril.add(new Barril(entityId,arg1,x,y));
                    
                    }
                    if(entityType.equals("MINE")){
                        
                        
                            listMina.add(new Mina(entityId,x,y));
                        }
                }
               //System.err.println(""+entityId+" "+entityType+" "+x+" "+y+" "+arg1+" "+arg2+" "+arg3+" "+arg4);
                
            }
            
            //System.err.print(listBarco.size());
            
            for (int i = 0; i < myShipCount; i++) {

                 // Write an action using System.out.println()
                 // To debug: System.err.println("Debug messages...");
                // System.out.println("MOVE 10 9");// tengo esta linea pero no me visualiza en codingame
               barco = listBarco.get(0);
               barcoAdversario = listBarco.get(1);
               minAdversario = auxBarcoAdversario(barcoAdversario,listBarril);
               coordSeleccion = heuristica(barco,listBarril);
               
               if(coordSeleccion !=null){
                   
                int x2 = coordSeleccion.getPunto_X();
                int y2 = coordSeleccion.getPunto_Y();
                System.out.println("MOVE "+x2+" "+y2); // Any valid action, such as "WAIT" or "MOVE x y"
             
               }
               else{
                  // System.err.println("coordSeleccion es null ");
                   if(minAdversario<10 ){ 
                       Punto c = heuristica(barcoAdversario,listBarril);
                       int x4 = c.getPunto_X();
                       int y4 = c.getPunto_Y();
                       System.out.println("FIRE"+x4+" "+y4);
                       
                     //  double minShip = auxBarcoAdversario(barco,listBarril);
                     //  coordDisparo = heuristica(barcoAdversario,listBarril,minShip);
                            //  if(coordDisparo!=null){
                              //     int x3 = coordDisparo.getPunto_X();
                              //     int y3 = coordDisparo.getPunto_Y();
                              //     System.out.println("FIRE "+x3+" "+y3);
                             //   }
                             //   else{
                             //       System.out.println("WAIT");
                            //        }
                       
                       }
                        
                  // else{
                  // System.out.println("WAIT");
                   
                 //  }
             
            }
        }
    }
      
    } 
    public  static Punto heuristica(Barco b ,ArrayList <Barril> listBarril){
        
        
        Punto solucion =null;
        double min = 1200000000;
        double aux =0;
        Barril r = null;
        Barril optimo =null ;
        Punto puntoOptimo = null;
        
        for(int i=0;i<listBarril.size();i++){
            
             r = listBarril.get(i);
             aux = b.distancia(r);
             if(aux<min ){
                 
                 min = aux ;
                 optimo = r ;
                 puntoOptimo = r.getCoordenada();
                 }
                 
            
            }
         
         return puntoOptimo;
        
        }
             
        public  static double auxBarcoAdversario(Barco b ,ArrayList <Barril> listBarril){
        
        
        Punto solucion =null;
        double min = 12000;
        double aux =0;
        Barril r = null;
        Barril optimo =null ;
        
        for(int i=0;i<listBarril.size();i++){
            
             r = listBarril.get(i);
             aux = b.distancia(r);
             if(aux<min){
                 
                 min = aux ;
                 optimo = r ;
                 }
            
            }
         
         return min;
        
        }
         public  static Punto coordenadaDisparo(Barco b ,ArrayList <Barril> listBarril){
        
        
        Punto disparo =null;
        double min = 12000;
        double aux =0;
        Barril r = null;
        Barril optimo =null ;
        
        for(int i=0;i<listBarril.size();i++){
            
             r = listBarril.get(i);
             aux = b.distancia(r);
             if(aux<min){
                 
                 min = aux ;
                 disparo= r.getCoordenada() ;
                 
                 }
            
            }
         
         return disparo;
        
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
    public int diferencia_X(int x1){
        
        return (x-x1);
    }
        
    public int diferencia_Y(int y1){
            
            return (y-y1);
    }
 }
 
class Entidad{
    
    private int id ;
    private Punto coordenada ;
    private int cantidad_ron  ;
    
public Entidad(int d ,int x ,int y ,int cant_ron ){
        
      id = d;
      coordenada = new Punto (x,y);
      cantidad_ron = cant_ron;
        
}
public Entidad(int ide ,int x ,int y){
    
    id = ide ;
    coordenada = new Punto(x,y);
    }
public int getId(){
    
    return id ;
    }
public Punto getCoordenada(){
    
    return coordenada;
    }
public int  getCantidad_Ron(){
    
    return cantidad_ron;
    }    
    
public void setCoordenada(Punto p){
    
    coordenada = p ;
    
    }    
public void setCantidad_Ron(int ron){
    
    cantidad_ron = ron ;
    
    }  
    public double distancia(Entidad a ){
        
        Punto coordenada_a = a.getCoordenada() ;
        double dif_X = coordenada.diferencia_X(coordenada_a.getPunto_X());
        double dif_Y = coordenada.diferencia_Y(coordenada_a.getPunto_Y());
        
        double valorAux = Math.pow(dif_X,2)+Math.pow(dif_Y,2);
        double valor = Math.sqrt(valorAux);
        return valor;
        }
   
    
    }
class Barco extends Entidad{

    private int  orientacion ;
    private int velocidad ;
    private int controla_barco ;

    public Barco(int d,int x,int y,int arg1,int arg2,int arg3,int arg4){
        super(d,x,y,arg3);
        orientacion = arg1;
        velocidad = arg2 ;
        controla_barco = arg4 ;

    }
   
    public int getOrientacion(){

        return orientacion ;
    }
    public int getVelocidad(){

        return velocidad ;
    }
    public int controla_barco(){

        return controla_barco ;
    }
 
    public void setOrientacion(int o){
       orientacion = o ;
    }
    public void setVelocidad(int v){
     
         velocidad = v ;

    }

    public void setControla_barco(int b){

        controla_barco = b;
    }
}
class Barril extends Entidad{

 private int aux;

public Barril(int d ,int x1 ,int y1,int arg1){
    
    super(d,x1,y1,arg1);
    aux = 0 ;
  
 }
 
}
class Mina extends Entidad{
  
    
public Mina(int ide ,int x ,int y){
        
        super(ide,x,y);
        
      }
        
 
    }