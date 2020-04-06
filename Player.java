import java.util.*;
import java.io.*;
import java.math.*;


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    
    public static void main(String args[]) {
    
     int [][][] vecinos = new int[2][6][2];

        vecinos[0][0][0] = 1;
        vecinos[0][0][1] = 0;
        
        vecinos[0][1][0] = 0;
        vecinos[0][1][1] = -1;
        
        vecinos[0][2][0] = -1;
        vecinos[0][2][1] = -1;
        
        vecinos[0][3][0] = -1;
        vecinos[0][3][1] =  0;
        
        vecinos[0][4][0] = -1;
        vecinos[0][4][1] = 1;
        
        vecinos[0][5][0] = 0;
        vecinos[0][5][1] = 1;
    
        vecinos[1][0][0] = 1;
        vecinos[1][0][1] = 0;
        
        vecinos[1][1][0] = 1;
        vecinos[1][1][1] = -1;
        
        vecinos[1][2][0] = 0;
        vecinos[1][2][1] = -1;
        
        vecinos[1][3][0] = -1;
        vecinos[1][3][1] = 0;
        
        vecinos[1][4][0] = 0;
        vecinos[1][4][1] = 1;
        
        vecinos[1][5][0] = 1;
        vecinos[1][5][1] = 1;
    

    

        Scanner in = new Scanner(System.in);
        Barco barco ,barcoAdversario ;
        Barril barril ;
        Punto coordSeleccion = null ,coordDisparo=null;
        double minAdversario;
        ArrayList <Barco> listBarco  = null;
        ArrayList <Barril> listBarril = null;
        ArrayList <Mina> listMina = null ;
        ArrayList <Entidad> listEsquina = new ArrayList();
        Punto posDisparar;
        int x4 ,y4 ;
        
        listEsquina.add( new Entidad(1,"esq1",0,0));
        listEsquina.add(new Entidad(2,"esq2",0,20));
        listEsquina.add(new Entidad(3,"esq3",22,0));
        listEsquina.add( new Entidad(4,"esq4",22,20));
        
         
        
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

               
                           /*
                 
                            Agregar Entidades a las Listas 
                 
                           */ 
                           if(entityType.equals("SHIP")){
                   
                    
                            listBarco.add(new Barco(entityId,entityType,x,y,arg1,arg2,arg3,arg4));
                    
                            }
                

                           if(entityType.equals("BARREL")){
                        
                            listBarril.add(new Barril(entityId,entityType,x,y,arg1));
                     
                           }
                           
                           if(entityType.equals("MINE")){
                        
                        
                            listMina.add(new Mina(entityId,entityType,x,y));
                           }
                              
                   }
               //System.err.println(""+entityId+" "+entityType+" "+x+" "+y+" "+arg1+" "+arg2+" "+arg3+" "+arg4);
                
            
            
            //System.err.print(listBarco.size());
            
            for (int i = 0; i < myShipCount; i++) {

                 // Write an action using System.out.println()
                 // To debug: System.err.println("Debug messages...");
                // System.out.println("MOVE 10 9");// tengo esta linea pero no me visualiza en codingame
                
                /*Recuperar barco */
               barco = listBarco.get(0);
               barcoAdversario = listBarco.get(1);
               
               /* la distancia minima de el barco adversario a al barril mas cercano */
               //minAdversario = distancia_Barril(barcoAdversario,listBarril);
               //Punto coordBarrel = posicionBarril_Cercano(barcoAdversario,listBarril);
               Par infoAdv =  distancia_Barril(barcoAdversario,listBarril);
               
               /*La heuristica corresponde con encontrar la coordenada optima para efectuar el movimiento del barco 
               Se evalua la distancia al barril mas cercano considerando la distancia del barco adversario al dicho barril */
               coordSeleccion = heuristica(barco,listBarril,infoAdv);
               
               System.err.println("velocidad "+barco.getVelocidad());
               System.err.println("posicion Actual "+barco.posicionActual());
               
               if(coordSeleccion !=null){
                barco.setDisparo(0);   
                int x2 = coordSeleccion.getPunto_X();
                int y2 = coordSeleccion.getPunto_Y();   
                boolean band = minasCercana(vecinos,x2 ,y2,barco.coordenaPar_Y() ,barco.getOrientacion(),listMina);
                int x10 = barco.getCoordenada().getPunto_X();
                int y10 = barco.getCoordenada().getPunto_Y();
                
                                          if(x10-2==0 || x10+2==22 || y10-2==0 || y10+2==20){
                                              
                                            cambiarOrientacion(barco , x10 , y10);
                                                                                  
                                          System.out.println("MOVE "+x2+" "+y2); // Any valid action, such as "WAIT" or "MOVE x y"
                           
                                      }
                                 else{ 
                
                                               if(band){
                    
                                                       barco.cambiarOrientacion(1);
                                                       System.out.println("MOVE "+x2+" "+y2); // Any valid action, such as "WAIT" or "MOVE x y"
                    
                                                       }
                                              else{
                        
                                                        boolean bandEnemigo = verificarBarcoEnemigo(vecinos,x2,y2,barco.coordenaPar_Y(),barco.getOrientacion(),barcoAdversario.getCoordenada());
                                                        if(bandEnemigo){
                                                       // System.out.println("WAIT");
                                                      // int x9 = barcoAdversario.getCoordenada().getPunto_X();
                                                      //  int y9 = barcoAdversario.getCoordenada().getPunto_Y();
                                                      posDisparar = posDisparo(barco ,barcoAdversario , listBarril);
                                                      // que hago cuando esty sin barril
                                                      x4 = posDisparar.getPunto_X();
                                                      y4 = posDisparar.getPunto_Y();
                                                      
                                                      
                                                      
                                                       
                                                           System.out.println("FIRE "+x4+" "+y4);
                                
                                                         }
                                                        else{
                                   
                                                       System.out.println("MOVE "+x2+" "+y2); // Any valid action, such as "WAIT" or "MOVE x y"
                                                       }    
                        
                                                   }
                 
                
                                   }
               }
               else{
                   
                   
                        // verifico si estoy en un borde 
                                                          int x7 = barco.getCoordenada().getPunto_X();
                                                          int y7 = barco.getCoordenada().getPunto_Y();
                                                           if(x7-2==0 || x7+2==22 || y7-2==0 || y7+2==20){
                                              
                                                                 cambiarOrientacion(barco , x7 , y7 );
                                                                 Punto m = barrilMasCercano(barco,listBarril);
                                                                 
                                                                 if(listBarril.size()!=0){
                                                                 int x8 = m.getPunto_X();//???
                                                                 int y8 = m.getPunto_Y();
                                                                 
                                                                 System.out.println("MOVE "+x8+" "+y8); // Any valid action, such as "WAIT" or "MOVE x y"
                                                                 
                                                                 }
                                                                 else{
                                                                     //moverme a la esquina mas cercana 
                                                                     // System.out.println("WAIT");
                                                                    // Punto coordEsq = esquinaMasCercana(barco,listEsquina);
                                                                     int x6 = barcoAdversario.getCoordenada().getPunto_X();
                                                                     int y6 = barcoAdversario.getCoordenada().getPunto_Y();
                                                                     if(barco.distancia(barcoAdversario)<10){
                                                                         
                                                                     System.out.println("FIRE "+x6+" "+y6);
                                                                     
                                                                     }
                                                                     else{
                                                                         
                                                                         System.out.println("WAIT");
                                                                         }
                                                                     
                                                                     }
                                                           }
                                                           else{
                                                               
                                                                if(barco.distancia(barcoAdversario)<10 ){
                       
                                                                 // consulto si es mi primer Disparo 
                                                                                          if(barco.getNumDisparo()==0 && listBarril.size()!=0){
                                                                                              
                                                                                              
                                                                                          //Punto c = coordenadaDisparo(barcoAdversario,listBarril);
                                                                                          posDisparar = posDisparo(barco ,barcoAdversario , listBarril);
                                                                                          // que hago cuando esty sin barril
                                                                                          x4 = posDisparar.getPunto_X();
                                                                                          y4 = posDisparar.getPunto_Y();
                                                                                         // x4 = x4/2;
                                                                                         // y4 = y4/2;
                                                                                          barco.setDisparo(1);
                                                                                          System.out.println("FIRE "+x4+" "+y4);
                       
                                                                                          }
                                                                                         else{
                           
                                                                                          barco.setDisparo(0);
                                                                                          Punto n = barrilMasCercano(barco,listBarril);
                                                                                          
                                                                                          if(listBarril.size()!=0){
                                                                                          int x12 = n.getPunto_X();
                                                                                          int y12 = n.getPunto_Y();
                                                                 
                                                                                          System.out.println("MOVE "+x12+" "+y12); // Any valid action, such as "WAIT" or "MOVE x y"
                                                                                          }
                                                                                          else{
                                                                                                 //System.out.println("WAIT");
                                                                                                 
                                                                                                 //Punto coordEs = esquinaMasCercana(barco,listEsquina);
                                                                                                 if(barco.distancia(barcoAdversario)<10){
                                                                                                 int x15 = barcoAdversario.getCoordenada().getPunto_X();
                                                                                                 int y15= barcoAdversario.getCoordenada().getPunto_Y();
                                                                      
                                                                                                 System.out.println("FIRE "+x15+" "+y15);
                                                                                                 
                                                                                                 }
                                                                                                 else{
                                                                                                     
                                                                                                     System.out.println("WAIT");
                                                                                                     }
                                                                                               
                                                                                                 
                                                                                              }
                           
                           
                                                                                           }
                                                                 }
                                                                 else{
                                                                     barco.setDisparo(0);
                                                                            
                                                                      System.out.println("WAIT");
                                                                                            
                                                                    }
                                                               
                                                               
                                                               
                                                               
                                                               }
                    
                       
                                             }
                                         
             }
        }
    }
      


public static void cambiarOrientacion(Barco barco ,int x2 ,int y2 ){
    
    
     
    
         if(x2-2==0){
                                       
                   if(barco.getOrientacion()==2){  barco.cambiarOrientacion(1);} 
                   if(barco.getOrientacion()==3){  barco.cambiarOrientacion(2);}
                   if(barco.getOrientacion()==4) {barco.cambiarOrientacion(5);}
                  }
                                   
        if(x2+2==22){
                                   
                  if(barco.getOrientacion()==1){  barco.cambiarOrientacion(2);} 
                  if(barco.getOrientacion()==0){  barco.cambiarOrientacion(5);}
                  if(barco.getOrientacion()==5) {barco.cambiarOrientacion(4);}
                                   
                  }   
                           
       if(y2-2==0){
                                       
                 if(barco.getOrientacion()==1){  barco.cambiarOrientacion(0);} 
                 if(barco.getOrientacion()==2){  barco.cambiarOrientacion(1);}
                 // if(barco.getOrientacion()==4) {barco.cambiarOrientacion(5);}
                }
                                   
       if(y2+2==20){
                                   
                if(barco.getOrientacion()==4){  barco.cambiarOrientacion(5);} 
                if(barco.getOrientacion()==5){  barco.cambiarOrientacion(0);}
                 // if(barco.getOrientacion()==5) {barco.cambiarOrientacion(4);}
                                   
               }  
    
   
    }
    public static boolean verificarBarcoEnemigo(int [][][]vecinos,int x ,int y ,int r ,int orientacion ,Punto coordAdversario){
        
        int posEnemigo_X = coordAdversario.getPunto_X();
        int posEnemigo_Y=  coordAdversario.getPunto_Y();
        
        int x1 = x+vecinos[r][orientacion][0];
        int y1 = y+vecinos[r][orientacion][1];
        x1 = x1+vecinos[r][orientacion][0];
        y1 = y1+vecinos[r][orientacion][1];
        
        return (x1==posEnemigo_X && y1==posEnemigo_Y);
        
        }
     
    public static boolean minasCercana(int [][][] vecinos,int x ,int y ,int r ,int orientacion,ArrayList <Mina> listMina){
        
    
    
         int x1 = x+vecinos[r][orientacion][0];
         int y1 = y+vecinos[r][orientacion][1];
         x1 = x1+vecinos[r][orientacion][0];
         y1 = y1+vecinos[r][orientacion][1];

        
        int i = 0;
        Mina m = null;
        boolean band = false;
        while((i<listMina.size() )&& (band==false)){
            
               m = listMina.get(i);
               if(m.getCoordenada().igualCoordenada(x1,y1)){
                   
                   
                   band = true ;
                   
                   }
            
            
            i++;
            }
        
        
        return band ;
        
        }
        
        
        
        
    public  static Punto heuristica(Barco b ,ArrayList <Barril> listBarril,double minAdversario){
        
        
        Punto solucion =null;
        double min = 1200000000;
        double aux =0;
        Barril r = null;
        Barril optimo =null ;
        Punto puntoOptimo = null;
        
        for(int i=0;i<listBarril.size();i++){
            
             r = listBarril.get(i);
             aux = b.distancia(r);
             if(aux<min && aux<minAdversario){
                 
                 min = aux ;
                 optimo = r ;
                 puntoOptimo = r.getCoordenada();
                 }
                 
            
            }
         
         return puntoOptimo;
        
        }
        public static Punto barrilMasCercano(Barco b ,ArrayList<Barril> listBarril){
            
            
            
        Punto solucion =null;
        double min = 1200000000;
        double aux =0;
        Barril r = null;
        Barril optimo =null ;
        Punto puntoOptimo = null;
        
        for(int i=0;i<listBarril.size();i++){
            
             r = listBarril.get(i);
             aux = b.distancia(r);
             if(aux<min){
                 
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
        
        
        public static Punto esquinaMasCercana(Barco barco,ArrayList<Entidad> listEsquina ){
          
            
            double min = 20000;
            Entidad m = null ;
            Punto coordenadaOptima = null;
            
            for(int i=0;i<listEsquina.size();i++){
                
                m = listEsquina.get(i);
                if(barco.distancia(m)<min){
                    
                    min = barco.distancia(m);
                    coordenadaOptima = m.getCoordenada();
                    
                    }
                
                }
            
            return coordenadaOptima ;
            
            }
            
            
/*


Realizar el calculo para ir al Barril Mas Cercano 

*/
 public  static Punto heuristica(Barco b ,ArrayList <Barril> listBarril,Par a){
        
        
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
         
         if(puntoOptimo!=null){
                                if(puntoOptimo.igualCoordenada(a.getCoordenada().getPunto_X(),a.getCoordenada().getPunto_Y())){
             
                                      if(min>a.getMinimo()){
                                                            puntoOptimo = null;
                                                           }
                                }
          }
         
         return puntoOptimo;
        
        }           
            
/*

Realizar un metodo que me permita calcular la posicion a disparar a mi barco enemigo 


*/            

public static  Punto posDisparo(Barco barco ,Barco barcoAdversario ,ArrayList <Barril> listBarril){
    
    
        Par band = distancia_Barril(barco,listBarril);
        double distancia = band.getMinimo();
        int x = barcoAdversario.getCoordenada().getPunto_X();
        int y = barcoAdversario.getCoordenada().getPunto_Y();
        
         
        Punto posicionDisparo = posOrientacion(x,y,barcoAdversario.getOrientacion(),distancia);
    
    
    
    return posicionDisparo ;
    
    }  
    
/*

 Realizar el calculo de la distancia entre entre un Barco y el Barril mas cercano 
 
*/
private static  Par distancia_Barril(Barco barco ,ArrayList <Barril> listBarril){
    
    Par band =null;
    Barril barrel ;
    Punto coord=null ;
    double min =30000;
      for(int i=0 ; i<listBarril.size();i++){
          
             barrel = listBarril.get(i);
             if(barco.distancia(barrel)<min){
                 
                 min = barco.distancia(barrel);
                 coord = barrel.getCoordenada();
                 }
          
          }
    band = new Par(coord,min);
    return band ;
    
    }
    /*
    
    Realizar el calcula de la Posicion al Barril Mas Cercano 
    */
    private static Punto posicionBarril_Cercano(Barco barco ,ArrayList<Barril> listBarril){
        
         
    Barril barrel ;
    double min =30000;
    Punto pos  =null;
                    for(int i=0 ; i<listBarril.size();i++){
          
                         barrel = listBarril.get(i);
                         if(barco.distancia(barrel)<min){
                 
                         min = barco.distancia(barrel);
                         pos = barrel.getCoordenada();
                        }
          
                   }
        
        return pos ;
        }
    /*
    
    Cuenta auxiliar para saber la posicion posible de acuerdo a la Orientacion
    
    */
    
    private static  Punto posOrientacion(int x ,int y ,int orientacion,double distancia){
        
        Punto m = null ;
        if(orientacion==0){
             
             x = x+((int)(distancia/2));
            
            }
        if(orientacion==1 || orientacion==2){
            
            y=y-((int)(distancia/2));
            }
         
        if(orientacion==3){
            
            x=x-((int)(distancia/2));
            }
        if(orientacion==4 || orientacion==5){
            y=y+((int)(distancia/2));}
    
    
        
       m = new Punto(x,y);
        return m;
        
    }
}


/*

Clase Par para poder manipular dos atributos 

*/
class Par{
    
    private Punto coordenada ;
    private double min ;
    public Par(Punto c ,double m){
        
        coordenada = c ;
         min = m ;
        }
    public Punto getCoordenada(){
        
        return coordenada;
        }
        
    public double getMinimo(){
            
            return min ;
    }
    public void setCoordenada(Punto p){
        
        coordenada = p ;
    }
    public void setMin(int m){
            
         min = m ;
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
    public boolean igualCoordenada(int x_sig,int y_sig){
        
         
         return (x==x_sig && y==y_sig);
        
        }
        
    public int esPar_Y(){
         
         int aux = 1;   
            if(y%2==0){
                
            aux=0;
                }
                
   return aux;                
            
            }
            
              
 }
 
class Entidad{
    
    private int id ;
    private String entityType;
    private Punto coordenada ;
    
    
public Entidad(int d ,String type ,int x ,int y ){
        
      id = d;
      entityType = type ;
      coordenada = new Punto (x,y);
        
}
 
public int getId(){
    
    return id ;
    }
public Punto getCoordenada(){
    
    return coordenada;
    }
public int coordenaPar_Y(){
    
    
    return coordenada.esPar_Y();
    
    }    
public String posicionActual(){
        
        String cadena = ""+coordenada.getPunto_X()+"  , "+coordenada.getPunto_Y()+"";
 
   return cadena ;       
}
 
public void setCoordenada(Punto p){
    
    coordenada = p ;
    
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

  
    private int stock_ron ;
    private int  orientacion ;
    private int velocidad ;
    private int controla_barco ;
    private int numDisparo ;

    public Barco(int d,String type ,int x,int y,int arg1,int arg2,int arg3,int arg4){
        super(d,type,x,y);
        orientacion = arg1;
        velocidad = arg2 ;
        stock_ron = arg3;
        controla_barco = arg4 ;
        numDisparo = 0 ;
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
    public int getStock_ron(){
        
        return stock_ron;
        }
 public void setStock_ron(int s){
     
     stock_ron = s;
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
    
    public void cambiarOrientacion(int i){
        
        
           if(orientacion==5){

            orientacion=0;
            }
            else{

               orientacion=orientacion+i;
            }
     
        
        }
        
        public void evitarColision(int orientacionEnemigo){}
            
  
        
    public void setDisparo(int n){
        
        
        if(n==0){numDisparo=0;}
        else{ numDisparo=numDisparo+n;}
        
    }
    public int getNumDisparo(){
        
        return numDisparo ;
        
        }
    
}
class Barril extends Entidad{

 private int ron_disponible;

public Barril(int d ,String type,int x1 ,int y1,int arg1){
    
    super(d,type,x1,y1);
    ron_disponible=arg1 ;
  
 }
 
}
class Mina extends Entidad{
  
    
public Mina(int ide,String type ,int x ,int y){
        
        super(ide,type,x,y);
        
      }
        
 
    }