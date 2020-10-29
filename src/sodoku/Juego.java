/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sodoku;

import java.util.Vector;

/**
 *
 * @author ~Antares~
 */

public class Juego {
    
    private Punto punto;
    private int stopDeshacer;
    private int stopRehacer;
    private Vector<Punto> deshacer;
    private Vector<Punto> rehacer;
    
    public Juego(){
        punto = new Punto();
        stopDeshacer = 0;
        stopRehacer = 0;
        deshacer = new Vector<Punto>();
        rehacer = new Vector<Punto>();
    }
    
    public void apilarDeshacer(Punto puntoX){
        stopDeshacer++;
        deshacer.add(puntoX);        
    }
    
    public Punto desApilarDeshacer(){
        stopDeshacer--;
        Punto aux = deshacer.get(stopDeshacer);
        deshacer.remove(stopDeshacer);
        return aux;
    }
    
    public boolean esVaciaDeshacer(){
        if(stopDeshacer == 0)
            return true;
        else
            return false;
    }
    
    public void apilarRehacer(Punto puntoX){
        stopRehacer++;
        rehacer.add(puntoX);        
    }
    
    public Punto desApilarRehacer(){
        stopRehacer--;
        Punto aux = rehacer.get(stopRehacer);
        rehacer.remove(stopRehacer);
        return aux;
    }
    
    public void vaciarRehacer(){
        stopRehacer = 0;
        rehacer.removeAllElements();
    }
    
    public boolean esVaciaRehacer(){
        if(stopRehacer == 0)
            return true;
        else
            return false;
    } 
    
    //validaciones
    
    public boolean validarFila(int[][] matrix, int numeroX, int posFila, int posCol){
        
        for (int j = 0; j < matrix.length; j++) {
            if(matrix[posFila][j] == numeroX){
                return false;
            }          
        }
        return true;
    }
    
    public boolean validarColumna(int[][] matrix, int numeroX, int posFila, int posCol){
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][posCol] == numeroX){
                return false;
            }          
        }
        return true;
    }
    
    public boolean validarRegion(int[][] matrix, int numeroX, int posFila, int posCol, int filaRegion, int colRegion){
        int i2 = (posFila/filaRegion)*filaRegion;
        int limiteFila = i2+filaRegion;
        
        int j2 = (posCol/colRegion)*colRegion;
        int limiteCol = j2+colRegion;
        
        for (int i = i2; i < limiteFila; i++) {
            for (int j = j2; j < limiteCol; j++) {
                if(matrix[i][j] == numeroX){
                    return false;
                }
            }                        
        }   
        return  true;
    }
    
    //verificar si que el juego se terminó
    
    public boolean juegoTermino(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] == 0){
                    return false;
                }
            }                        
        } 
        return true;
    }   
  
    
    public int darPista(int[][] matrix,int fila, int col, int filaR, int colR){
        
        System.out.println("\ncasilla recibida"+fila+","+col);
        
        for (int pista = 1; pista <= matrix.length; pista++) { 
            
            boolean siFila = validarFila(matrix, pista, fila, col);
            boolean siCol = validarColumna(matrix, pista, fila, col);
            boolean siRegion = validarRegion(matrix, pista, fila, col, filaR, colR);  
            System.out.println("pista ->"+pista);
            System.out.println("siFila:"+siFila+" siCol:"+siCol+" siReg:"+siRegion);
            if(siFila&&siCol&&siRegion){               
               return pista;               
            }
            
        }       
        return  -1;     // en caso de que en esa casilla no se pueda ningun numero     
    }
    
    //impresion para elcontrol de lo que se hace internamente
    
    public void imprimirDeshacer(){     
        
        for (int i = 0; i < deshacer.size(); i++) {            
            System.out.println("***********************");
            System.out.println("       DH>"+i+"<        ");
            System.out.println("dato: "+deshacer.get(i).datoPunto);
            System.out.println("posM: ("+deshacer.get(i).filaPunto+","+deshacer.get(i).colPunto+")");
            System.out.println("posV: ["+deshacer.get(i).indexCasilla+"]");            
        }
        System.out.println("***********************\n");
    }
    
    public void imprimirRehacer(){     
        
        for (int i = 0; i < rehacer.size(); i++) {            
            System.out.println("***********************");
            System.out.println("       RH>"+i+"<        ");
            System.out.println("dato: "+rehacer.get(i).datoPunto);
            System.out.println("posM: ("+rehacer.get(i).filaPunto+","+rehacer.get(i).colPunto+")");
            System.out.println("posV: ["+rehacer.get(i).indexCasilla+"]");            
        }
        System.out.println("***********************\n");
    }
        
}


/* jugada sugerida sin seleccionar casilla es secuencial;  
public Punto darPistaInteligente(int[][] matrix,int filaR, int colR){
        Punto puntoAux = new Punto();
        int indexC = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                puntoAux = new Punto(i, j, indexC);
                //System.out.println("puntoAux:"+i+","+j);
                if(matrix[i][j] == 0){
                   // System.out.println("if");
                    for (int pista = 1; pista <= matrix.length; pista++) { 
                        //System.out.println("pista:"+pista);
                        if(validarFila(matrix, pista, i, j)&&
                           validarColumna(matrix, pista, i, j)&&
                           validarRegion(matrix, pista, i, j, filaR, colR)){                          
                                
                            puntoAux.setDatoPunto(pista);
                            return puntoAux;
                        }                         
                    }
                }
                indexC++;
            }                        
        }
        puntoAux.setDatoPunto(-1); // en caso de que en esa casilla no se pueda ningun numero
        return  puntoAux;         
    }*/