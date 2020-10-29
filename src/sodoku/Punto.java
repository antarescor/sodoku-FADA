/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sodoku;

/**
 *
 * @author ~Antares~
 */
public class Punto {
    int datoPunto;
    int filaPunto;
    int colPunto;
    int indexCasilla;
    
    public Punto(){
        
    }

    public Punto(int posFila, int posCol, int indexCasillaX){
        filaPunto = posFila;
        colPunto = posCol;
        indexCasilla = indexCasillaX;
    }
    
    public void setDatoPunto(int numeroX){
        datoPunto = numeroX;
    }
    public void setFila(int numeroX){
        filaPunto = numeroX;
    }
    public void setColumna(int numeroX){
        colPunto = numeroX;
    }
    public void setIndexCasilla(int numeroX){
      indexCasilla = numeroX;
    }
    
    public int getDatoPunto(){
        return datoPunto;
    }
    public int getFila(){
        return filaPunto;
    }
    public int getColumna(){
        return colPunto;
    }
    public int getIndexCasilla(){
        return indexCasilla;
    }
    
}
