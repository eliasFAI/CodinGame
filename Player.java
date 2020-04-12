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
        ArrayList<Barco> listEnemigo = null ;
        ArrayList <Barril> listBarril = null;
        ArrayList <Mina> listMina = null ;
        ArrayList <Entidad> listEsquina = new ArrayList();
        Punto posDisparar ,ms;
        int x4 ,y4 ,x21,y21,x28,y28,posX_Actual,posY_Actual;
        
        boolean band ,avanze,bandEnemigo;
        
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
            listEnemigo = new ArrayList();
            
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
                            agregarEntidades(entityId,entityType,x,y,arg1,arg2,arg3,arg4,listBarco,listEnemigo,listBarril,listMina);
                                       
                   }
                           //System.err.println(""+entityId+" "+entityType+" "+x+" "+y+" "+arg1+" "+arg2+" "+arg3+" "+arg4);
                
            
            
            //System.err.print(listBarco.size());
            
            
            for (int i = 0; i < myShipCount; i++) {

                 // Write an action using System.out.println()
                 // To debug: System.err.println("Debug messages...");
                           
                            
                          barco = listBarco.get(i);
                          // buscar el barco mas cercano enemigo 
                          barcoAdversario = barcoMasCercano(barco ,listEnemigo);
                          //listEnemigo.get(0);
                                                    
                           /* la distancia minima de el barco adversario a al barril mas cercano */
                           Par infoAdv =  distancia_Barril(barcoAdversario,listBarril);
               
                           /*La heuristica corresponde con encontrar la coordenada optima para efectuar el movimiento del barco 
                           Se evalua la distancia al barril mas cercano considerando la distancia del barco adversario al dicho barril */
                           coordSeleccion = heuristica(barco,listBarril,infoAdv);
                           // posicionActual del Barco 
                           posX_Actual = barco.getCoordenada().getPunto_X();
                           posY_Actual = barco.getCoordenada().getPunto_Y();
                           // calculo  para el disparo 
                           int math_Disparo =(int) Math.round(1+(barco.distancia(barcoAdversario)/3));                                   
                           x28 = barcoAdversario.getCoordenada().getPunto_X()+(vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][0]*math_Disparo);
                           y28= barcoAdversario.getCoordenada().getPunto_Y()+(vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][1]*math_Disparo);  
                           
                            System.err.println("velocidad "+barco.getVelocidad());
                            System.err.println("posicion Actual "+barco.posicionActual());
               
          if(coordSeleccion !=null){
                              
                                    //en este if lo que hago es obtener la coordenada del barril mas cercano 
                                    barco.setDisparo(0);   
                                    
                                    int x2 = coordSeleccion.getPunto_X();
                                    int y2 = coordSeleccion.getPunto_Y();   
                                    // identificar si tengo minas Cercana 
                                    band = minasCercana(vecinos,posX_Actual ,posY_Actual,barco.coordenaPar_Y() ,barco.getOrientacion(),listMina);
                                    // verificar si tengo cerca barco Enemigo 
                                    bandEnemigo = verificarBarcoEnemigo(vecinos,barco,barcoAdversario);
                                          if(esquinaCercana(posX_Actual ,posY_Actual)){
                                              
                                            cambiarOrientacion(barco , posX_Actual, posY_Actual);
                                                                                  
                                          System.out.println("MOVE "+x2+" "+y2); // Any valid action, such as "WAIT" or "MOVE x y"
                            
                                            }
                                          else{ 
                
                                                        if(band || bandEnemigo){
                                                            
                                                            
                                                               // puedo disparar 
                                                               if(bandEnemigo ){
                                                                   
                                                                     posDisparar = posDisparo(barco ,barcoAdversario , listBarril);
                                                                       // que hago cuando esty sin barril
                                                                     x4 = posDisparar.getPunto_X();
                                                                     y4 = posDisparar.getPunto_Y();
                                            
                                                                        
                                                                       System.out.println("MOVE "+x2+" "+y2);
                                                                       System.out.println("FIRE "+x4+" "+y4);
                                                                   
                                                                   
                                                                }
                                                                else
                                                                {
                                                                    //cambiar de orientacion 
                                                                barco.cambiarOrientacion(1);
                                                                System.out.println("MOVE "+x2+" "+y2); // Any valid action, such as "WAIT" or "MOVE x y"
                                                       
                                                       
                                                                }
                                                       
                                                       
                    
                                                           }
                                                      else{
                        
                                                      //  boolean bandEnemigo = verificarBarcoEnemigo(vecinos,posX_Actual,posY_Actual,barco.coordenaPar_Y(),barco.getOrientacion(),barcoAdversario.getCoordenada());
                                                        //if(bandEnemigo){
                                                      
                                                      //   posDisparar = posDisparo(barco ,barcoAdversario , listBarril);
                                                         // que hago cuando esty sin barril
                                                      //   x4 = posDisparar.getPunto_X();
                                                      //   y4 = posDisparar.getPunto_Y();
                                            
                                                     //    System.out.println("FIRE "+x4+" "+y4);
                                
                                                     //    }
                                                    //    else{
                                   
                                                       System.out.println("MOVE "+x2+" "+y2); // Any valid action, such as "WAIT" or "MOVE x y"
                                                   //    }    
                        
                                                      }
                 
                                   }
               }
               else{
                   
                  //  x28 = barcoAdversario.getCoordenada().getPunto_X()+(vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][0]*math_Disparo);
                 //   y28= barcoAdversario.getCoordenada().getPunto_Y()+(vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][1]*math_Disparo);
                        // verifico si estoy en un borde 
                        
                      ms = coordenadaOptima(listMina);
                      x21 = ms.getPunto_X();
                      y21 = ms.getPunto_Y();        
       
                                                          // verifico los valores de x,y que podrian ser en caso de que esten en el borde 
                                                          
                                                           if(esquinaCercana(posX_Actual,posY_Actual)){
                                                                 // cambiar la orientacion del barco 
                                                                 cambiarOrientacion(barco , posX_Actual , posY_Actual );
                                                                 // barril mas Cercano si un caso todavia hay barriles disponibles 
                                                                 Punto m = barrilMasCercano(barco,listBarril);
                                                                 
                                                                 if(listBarril.size()!=0){
                                                                     // ir hacia la posicion del Barril mas cercano      
                                                                     int x8 = m.getPunto_X();//???
                                                                     int y8 = m.getPunto_Y();
                                                                    // System.out.println("FIRE "+x28+" "+y28);
                                                                     System.out.println("MOVE "+x8+" "+y8); // Any valid action, such as "WAIT" or "MOVE x y"
                                                                     System.out.println("FIRE "+x28+" "+y28);
                                                                 
                                                                 }
                                                                 else{
                                                                      
                                                                      // si un caso ya no hay barriles 
                                                                     
                                                                          avanze = analizarRonDisponible(listBarco,listEnemigo);
                                                                                if(avanze  && listEnemigo.size()>1){
                                                                                                     
                                                                                           // desplazarme a distancia lejanas del barco enemigo
                                                                                          // moverme de forma aleatoria 
                                                                                          ms = coordenadaOptima(listMina);
                                                                                          x21 = ms.getPunto_X();
                                                                                          y21 = ms.getPunto_Y();
                                                                                          
                                                                                           band = minasCercana(vecinos,posX_Actual,posY_Actual,barco.coordenaPar_Y() ,barco.getOrientacion(),listMina);
                                                                                          
                                                                                          if(band){
                                                                                              
                                                                                              barco.cambiarOrientacion(1);
                                                                                              }
                                                                                         // System.out.println("FIRE "+x28+" "+y28);
                                                                                          System.out.println("MOVE "+x21+" "+y21);
                                                                                          System.out.println("FIRE "+x28+" "+y28);
                                                                                          
                                                                                          }
                                                                                          else{
                                                                                                         
                                                                                          // tengo menos Ron que el Enemigo conviene disparar 
                                                                                          
                                                                                         //x28 = barcoAdversario.getCoordenada().getPunto_X()+(vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][0]*math_Disparo);
                                                                                         //y28= barcoAdversario.getCoordenada().getPunto_Y()+(vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][1]*math_Disparo);
                                                                                         System.out.println("MOVE "+x21+" "+y21);
                                                                                         System.out.println("FIRE "+x28+" "+y28);
                                                                                                         
                                                                                                         
                                                                                         }
                                                                     
                                                                    
                                                                     }
                                                           }
                                                           else{
                                                               //m si no es una esquina en el proximo turno 
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
                                                                                         // System.out.println("MOVE "+x21+" "+y21);
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
                                                                                                    // if((barco.distancia(barcoAdversario)<10) ){
                                                                                                     //int x15 = barcoAdversario.getCoordenada().getPunto_X();
                                                                                                     //int y15= barcoAdversario.getCoordenada().getPunto_Y();
                                                                      
                                                                                                     //System.out.println("FIRE "+x15+" "+y15);
                                                                                                 
                                                                                                    //  }
                                                                                                    //  else{
                                                                                                               avanze = analizarRonDisponible(listBarco,listEnemigo);
                                                                                                               if(avanze && listEnemigo.size()>1 ){
                                                                                                     
                                                                                                                         // desplazarme a distancia lejanas del barco enemigo
                                                                                                                        // moverme de forma aleatoria 
                                                                                                                       ms = coordenadaOptima(listMina);
                                                                                                                       x21 = ms.getPunto_X();
                                                                                                                       y21 = ms.getPunto_Y();
                                                                                                       
                                                                                                                       band = minasCercana(vecinos,posX_Actual ,posY_Actual,barco.coordenaPar_Y() ,barco.getOrientacion(),listMina);
                                                                                          
                                                                                                                       if(band){
                                                                                              
                                                                                                                        barco.cambiarOrientacion(1);
                                                                                                                        }
                                                                                                                       //System.out.println("FIRE "+x28+" "+y28);
                                                                                                                       System.out.println("MOVE "+x21+" "+y21);
                                                                                                                       System.out.println("FIRE "+x28+" "+y28);
                                                                                                                       
                                                                                                                    }
                                                                                                                else{
                                                                                                         
                                                                                                                      // tengo menos Ron que el Enemigo conviene disparar 
                                                                                          
                                                                                                                      x28 = barcoAdversario.getCoordenada().getPunto_X()+vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][0]*math_Disparo;
                                                                                                                      y28= barcoAdversario.getCoordenada().getPunto_Y()+vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][1]*math_Disparo;
                                                                      
                                                                                                                     // System.out.println("FIRE "+x28+" "+y28);
                                                                                                                      System.out.println("MOVE "+x21+" "+y21);
                                                                                                                      System.out.println("FIRE "+x28+" "+y28);
                                                                                                         
                                                                                                         
                                                                                                                }
                                                                                               
                                                                                        
                                                                                              }
                           
                           
                                                                                           }
                                                                             }
                                                                             else{
                                                                                  barco.setDisparo(0);
                                                                                  // Analizar si me conviene disparar o moverme a posicion lejana del Enemigo
                                                                                  avanze = analizarRonDisponible(listBarco,listEnemigo);
                                                                                  // tengo que evaluar si tengo mas ron y si tengo un barco Enemigo 
                                                                                  if(avanze  && listEnemigo.size()>1){
                                                                                      
                                                                                      // conviene en desplazarme a posicion contraria al enemigo 
                                                                                      
                                                                                       // System.out.println("WAIT");
                                                                                  
                                                                                      // desplazarme a distancia lejanas del barco enemigo
                                                                                      // moverme de forma aleatoria 
                                                                                       ms = coordenadaOptima(listMina);
                                                                                       x21 = ms.getPunto_X();
                                                                                       y21 = ms.getPunto_Y();
                                                                                       
                                                                                        band = minasCercana(vecinos,posX_Actual ,posY_Actual,barco.coordenaPar_Y() ,barco.getOrientacion(),listMina);
                                                                                          
                                                                                         if(band){
                                                                                              
                                                                                              barco.cambiarOrientacion(1);
                                                                                          }
                                                                              
                                                                                       System.out.println("MOVE "+x21+" "+y21);
                                                                                       System.out.println("FIRE "+x28+" "+y28);
                                                                                      
                                                                                      }
                                                                                      else{
                                                                                          
                                                                                          // tengo menos Ron que el Enemigo conviene disparar 
                                                                                                                             
                                                                                              x28 = barcoAdversario.getCoordenada().getPunto_X()+vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][0]*math_Disparo;
                                                                                              y28= barcoAdversario.getCoordenada().getPunto_Y()+vecinos[barcoAdversario.coordenaPar_Y()][barcoAdversario.getOrientacion()][1]*math_Disparo;
                                                                      
                                                                                            System.out.println("MOVE "+x21+" "+y21);
                                                                                           System.out.println("FIRE "+x28+" "+y28);
                                                                                          
                                                                                          
                                                                                          }
                                                                                  
                                                                               
                                                                                  
                                                                                            
                                                                             }
                                                               
                                                               
                                                               
                                                               
                                                               }
                    
                       
                               }
                                         
             }
        }
    }
    
    /*
    
    verificar si esty cercano a uno esquina 
    
    */
    public static boolean esquinaCercana(int x ,int y){
        
       return  (x-2==0 || x+2==22 || y-2==0 || y+2==20 || x==0 || x==22 || y==0 || y==20);
        
        }
    
    /*
    Analizar si me conviene atacar a los barcos enemigos 
    Se analiza la cantidad de Ron disponible y si es mayor a la cantidad de Ron disponible del Barco Enemigo
    */
    public static boolean analizarRonDisponible(ArrayList<Barco> listBarco,ArrayList<Barco> listEnemigo){
        
        
        int sumTotalRon_myShip ,sumTotalRon_Enemigo;
        
        sumTotalRon_myShip = sumarRon(listBarco);
        sumTotalRon_Enemigo = sumarRon(listEnemigo);
        
        
        // verfico si la cantidad de Ron disponible en mi Barco es mayor a los disponible por el Enemigo     
        return (sumTotalRon_myShip>sumTotalRon_Enemigo);
    
        
        }
        // sumar Ron total de los Barcos Disponibles 
        private static int  sumarRon(ArrayList<Barco> list){
            
            Barco m =null;
            int sumTotal =0;
            
            for(int i=0;i<list.size();i++){
                
                m = list.get(i);
                sumTotal = sumTotal+m.getStock_ron();
                
                }
            
            return sumTotal ;
            
            }
    
      
      /*
      
      Buscar espacio vacios que esten lejos del enemigo evitando las minas 
      
      */
      public static Punto coordenadaOptima(ArrayList<Mina> listMina)
      {
          
              // desplazarme a distancia lejanas del barco enemigo
             // moverme de forma aleatoria
             boolean band =true ,salir=true;
             Punto pos =null;
             int x =0 ,y=0 ;
         
              while(salir){ 
             
                           x = (int)Math.random()*22;
                           y = (int)Math.random()*20;
                          int j=0;
                          band = true ;
                                   while(j<listMina.size()&&(band)){
             
                                            Mina m = listMina.get(j);
                                           if(m.getCoordenada().igualCoordenada(x,y)){
                 
                                           band = false;
                                           }
                 
                                           j++;
                                   } 
             
             //si band es false significa que esa posicion generado aleatorriamente corresponde a una poscicion de mina
             if(band){
                 
                 salir = false;
                 }
          
         }
         
         pos = new Punto(x,y);
         return pos ;
         
         
     }
      
      
      /*
      
      Agregar Entidades a la lista 
      
      
      
      */
      
      

public static void agregarEntidades(int entityId,String entityType,int x,int y,int arg1,int arg2,int arg3,int arg4,ArrayList <Barco> listBarco,ArrayList<Barco>listEnemigo,ArrayList<Barril> listBarril,ArrayList<Mina> listMina){
    
     
    
    
     /*
     Agregar Entidades a las Listas 
    */ 
      if(entityType.equals("SHIP")){
                   
                 if(arg4==1){    
                     listBarco.add(new Barco(entityId,entityType,x,y,arg1,arg2,arg3,arg4));
                 }
                 else{
                                     
                     listEnemigo.add(new Barco(entityId,entityType,x,y,arg1,arg2,arg3,arg4));
                     }
                    
            }
                
     if(entityType.equals("BARREL")){
                        
             listBarril.add(new Barril(entityId,entityType,x,y,arg1));
                     
    }       
    if(entityType.equals("MINE")){
                        
          listMina.add(new Mina(entityId,entityType,x,y));
     }
                              
  
    
    }
    
    /*
    
    
    */
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
               
               //-----------------------------------------------------------------
                   
         if(x2==0){
                                       
                   if(barco.getOrientacion()==2){  barco.cambiarOrientacion(1);} 
                   if(barco.getOrientacion()==3){  barco.cambiarOrientacion(2);}
                   if(barco.getOrientacion()==4) {barco.cambiarOrientacion(5);}
                  }
                                   
        if(x2==22){
                                   
                  if(barco.getOrientacion()==1){  barco.cambiarOrientacion(2);} 
                  if(barco.getOrientacion()==0){  barco.cambiarOrientacion(5);}
                  if(barco.getOrientacion()==5) {barco.cambiarOrientacion(4);}
                                   
                  }   
                           
       if(y2==0){
                                       
                 if(barco.getOrientacion()==1){  barco.cambiarOrientacion(0);} 
                 if(barco.getOrientacion()==2){  barco.cambiarOrientacion(1);}
                 // if(barco.getOrientacion()==4) {barco.cambiarOrientacion(5);}
                }
                                   
       if(y2==20){
                                   
                if(barco.getOrientacion()==4){  barco.cambiarOrientacion(5);} 
                if(barco.getOrientacion()==5){  barco.cambiarOrientacion(0);}
                 // if(barco.getOrientacion()==5) {barco.cambiarOrientacion(4);}
                                   
               }  
               
               
               
               
               
               
    
    }
    public static boolean verificarBarcoEnemigo(int [][][]vecinos,Barco barco  ,Barco barcoEnemigo){
        
        
       Punto coordAdversario = barcoEnemigo.getCoordenada();    
       int posEnemigo_X = coordAdversario.getPunto_X();
       int posEnemigo_Y=  coordAdversario.getPunto_Y();
       Punto pos = barco.getCoordenada();
       int x = pos.getPunto_X();
       int y = pos.getPunto_Y();
       int r = barco.coordenaPar_Y();
       int orientacion = barco.getOrientacion();
       int velocidad = barco.getVelocidad();
      /* int s = barcoEnemigo.coordenaPar_Y();
       int o_Adv = barcoEnemigo.getOrientacion();
       int aux=0 ;
       if(o_Adv==0){aux = 3;}
       if(o_Adv==1){aux=4;}
       if(o_Adv==2){aux=5;}
       int posX_A = posEnemigo_X+vecinos[s][o_Adv][0];
       int posY_A = posEnemigo_Y+vecinos[s][o_Adv][1];
       int posX_C = posEnemigo_X+vecinos[s][aux][0];
       int posY_C = posEnemigo_Y+vecinos[s][aux][1];*/
       /* int x1 = x+vecinos[r][orientacion][0];
        int y1 = y+vecinos[r][orientacion][1];
        x1 = x1+vecinos[r][orientacion][0];
        y1 = y1+vecinos[r][orientacion][1];*/
        
        x=x+vecinos[r][orientacion][0]*velocidad;
        y=y+vecinos[r][orientacion][1]*velocidad;
        
        return ((x==posEnemigo_X && y==posEnemigo_Y ));
        
        }
     
    public static boolean minasCercana(int [][][] vecinos,int x ,int y ,int r ,int orientacion,ArrayList <Mina> listMina){
        
    
    
      /*   int x1 = x+vecinos[r][orientacion][0];
         int y1 = y+vecinos[r][orientacion][1];
         
         if(y1%2==0){
             r = 0 ;
             }
         else{
             
             r = 1 ;
             
             }     
             
         x1 = x1+vecinos[r][orientacion][0];
         y1 = y1+vecinos[r][orientacion][1];
        */
        x=x+vecinos[r][orientacion][0]*2;
        y=y+vecinos[r][orientacion][1]*2;
        
        
        int i = 0;
        Mina m = null;
        boolean band = false;
        while((i<listMina.size() )&& (band==false)){
            
               m = listMina.get(i);
               if(m.getCoordenada().igualCoordenada(x,y)){
                   
                   
                   band = true ;
                   
                   }
            
            
            i++;
            
            }
        
        
        return band ;
        
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
    
    /*
    
    Realizar la busqueda del barco enemigo mas cercano a mi posicion 
    
    */
  private static Barco barcoMasCercano(Barco b ,ArrayList <Barco> listEnemigo){
      
      double min=2000000 ;
      Barco barcoEnemigo ;
      Barco band=null ;
      
      for(int i=0 ; i<listEnemigo.size();i++){
          
          barcoEnemigo = listEnemigo.get(i);
          
          if(b.distancia(barcoEnemigo)<min){
              
              min = b.distancia(barcoEnemigo);
              band= barcoEnemigo;// el posible mas cercano a my ship 
              
              
              }
          
          
          }
      
      return band ;
      
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