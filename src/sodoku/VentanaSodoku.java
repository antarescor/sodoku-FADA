/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sodoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.Delayed;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author ~Antares~
 */
public class VentanaSodoku extends JFrame implements ActionListener, FocusListener{
    
    private Container contenedor;
    private JPanel panelControles;
    private JPanel margenTablero;
    
    private JButton deshacer;
    private JButton rehacer;
    private JButton salir;
    private JButton pista;
    
    private JTextArea info;  
    
    private String matriz;
    private int fila;
    private int col;
    private int tamaño;
    private int[][] matrix;
    
    private Vector<JTextField> vectorCasillas;
   
    private Punto punto;
    private Punto puntoCasillaAux;
    
    private Juego juego;
    
    public VentanaSodoku(String texto){
        
        super ("Sodoku");    
        
        juego = new Juego();
        
        crearMatriz(texto);         
        crearTablero();        
        setVisible(true);
        setLocationRelativeTo(null);  
    }
    
    public void initPanelControles(int limite){         
        
        deshacer = new JButton(); 
        deshacer.setEnabled(false);
        deshacer.setBounds(10, 30, 120, 40);  
        ImageIcon des = new ImageIcon("src/sodoku/Imagen/deshacer.png");
        ImageIcon iconoDes = new ImageIcon(des.getImage().getScaledInstance(deshacer.getWidth(), deshacer.getHeight(), Image.SCALE_DEFAULT));
        deshacer.setIcon(iconoDes);
        
        rehacer = new JButton();
        rehacer.setEnabled(false);
        rehacer.setBounds(140, 30, 120, 40);
        ImageIcon re = new ImageIcon("src/sodoku/Imagen/rehacer.png");
        ImageIcon iconoRe = new ImageIcon(re.getImage().getScaledInstance(rehacer.getWidth(), rehacer.getHeight(), Image.SCALE_DEFAULT));
        rehacer.setIcon(iconoRe);
        
        pista =new JButton("Jugada sugerida");
        pista.setFont(new Font("Arial", Font.BOLD, 14));
        pista.setBounds(10, 80, 250, 40);
        
        salir =new JButton("Salir");
        salir.setFont(new Font("Arial", Font.BOLD, 14));
        salir.setBounds(10, 130, 250, 40);
        
        info =  new JTextArea ();
        info.setFont(new Font("Arial", Font.BOLD, 22));
        info.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        info.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        info.setOpaque(false);
        info.setBounds(10, 200, 250, 80);
        
        deshacer.addActionListener(this);
        rehacer.addActionListener(this);
        salir.addActionListener(this);
        pista.addActionListener(this);
        
        panelControles = new JPanel();
        panelControles.setLayout(null);
        panelControles.setBorder(BorderFactory.createTitledBorder
                (null,"Controles",TitledBorder.CENTER,0,null, Color.BLUE));
        panelControles.setBounds(limite+10, 0, 270, limite);    
        
        
        panelControles.add(deshacer);
        panelControles.add(rehacer);          
        panelControles.add(salir);
        panelControles.add(pista);
        panelControles.add(info);
        contenedor.add(panelControles);
        setSize(limite+20+270, limite+40);
        
    }
    
    public void crearMatriz(String texto){
        fila = Integer.parseInt(texto.substring(0, 1));
        col  = Integer.parseInt(texto.substring(2, 3));
        tamaño = fila*col;      
        matriz = texto.substring(4);
        matriz = matriz.replace(" ", "").replace("\n", "");
        
        matrix = new int[tamaño][tamaño];
        System.out.println(matriz);
        int index = 0;
        for(int i = 0; i<tamaño; i++){
            for(int j = 0; j<tamaño; j++){
                
                if(matriz.charAt(index) == '-' ){
                    matrix[i][j] = 0;                    
                }
                else{
                    String x = ""+matriz.charAt(index);
                    matrix[i][j] = Integer.parseInt(x);
                }                
                index++;
                //System.out.print(matrix[i][j]);
            } 
            //System.out.println();
        }        
    }
    
    public void crearTablero(){   
        
        margenTablero = new JPanel();
        margenTablero.setBackground(Color.BLACK); 
        margenTablero.setLayout(null);         
        
        contenedor = getContentPane();
        contenedor.setLayout(null);
        contenedor.add(margenTablero);
        
        int tamañoCasilla = 60; //pixeles
        int espCasillas = 2; //pixeles
        int espRegiones = 6; // pixeles
        int acumEspX = espRegiones; // acumulara los espacios
        int acumEspY = espRegiones; // acumulara los espacios
        int acumCol = 1; //Contará para las regiones 
        int acumFil = 1;
        int margenX = 0;
        int indexVectorName = 0;
        
        vectorCasillas = new Vector<JTextField>(tamaño);
        
        for(int i = 0; i<tamaño; i++){
            for(int j = 0; j<tamaño; j++){               
                JTextField casilla = new JTextField();
                casilla.setFont(new Font("Arial", Font.BOLD, 40));
                casilla.setHorizontalAlignment(JTextField.CENTER);
                casilla.addActionListener(this);
                casilla.addFocusListener(this);
                casilla.setName("#"+indexVectorName+"$"+i+"%"+j+"&");
                casilla.setBounds(acumEspX, acumEspY, tamañoCasilla, tamañoCasilla);
                margenTablero.add(casilla);               
                vectorCasillas.add(casilla);
                indexVectorName++;
                if(matrix[i][j] == 0){
                    casilla.setText("");                    
                    casilla.setForeground(Color.BLUE);
                }
                else{
                    casilla.setText(""+matrix[i][j]);
                    casilla.setEditable(false); 
                    casilla.setFocusable(false);
                    //casilla.setEnabled(false);
                }  
                
                if (acumCol%col == 0){
                    acumEspX += espRegiones+tamañoCasilla;
                }
                else{
                    acumEspX += espCasillas+tamañoCasilla;
                }
                acumCol++;                          
            }
            acumCol = 1;
            margenX = acumEspX;
            acumEspX = espRegiones;
            if (acumFil%fila == 0){
                   acumEspY += espRegiones+tamañoCasilla;
               }
               else{
                   acumEspY += espCasillas+tamañoCasilla;
               }
               acumFil++;           
        }
        margenTablero.setBounds(0, 0, margenX, acumEspY);
        initPanelControles(acumEspY);
    }
    
    public void generarPosicion(ActionEvent e){
        
        String aux = e.toString();
        //System.out.println(aux); 
        
        String indexVec = aux.substring(aux.indexOf("#")+1, aux.indexOf("$"));
        String fila = aux.substring(aux.indexOf("$")+1, aux.indexOf("%"));
        String col = aux.substring(aux.indexOf("%")+1, aux.indexOf("&"));      
        
        
        int indexVector = Integer.parseInt(indexVec);
        int posFila = Integer.parseInt(fila);
        int posCol = Integer.parseInt(col);  
     
        
        punto = new Punto(posFila, posCol, indexVector);
        
        System.out.print("\ncasilla -> ");
        System.out.print("posVector: "+punto.getIndexCasilla());
        System.out.println("posTablero: ("+punto.getFila()+","+punto.getColumna()+")");

    }
    
    public boolean validarCasilla(){     
        
        try {
            int valor = Integer.parseInt(vectorCasillas.get(punto.getIndexCasilla()).getText());
            int posFila = punto.getFila();
            int posCol = punto.getColumna();
            
            if (valor >= 1 && valor <= tamaño){
                
                String mensaje = "";
                boolean boleano = true;
                
                if (!juego.validarFila(matrix, valor, posFila, posCol)){
                    mensaje += "*** Repite en fila\n";
                    boleano = boleano&&false;                    
                }
                if (!juego.validarColumna(matrix, valor, posFila, posCol)){
                    mensaje += "*** Repite en columna\n";
                    boleano = boleano&&false;
                }
                if (!juego.validarRegion(matrix, valor, posFila, posCol, fila, col)){                     
                    mensaje += "*** Repite en region\n";
                    boleano = boleano&&false;
                }      
                info.setForeground(Color.red);
                info.setText(mensaje);
                
                if(!boleano){
                    vectorCasillas.get(punto.getIndexCasilla()).setBackground(Color.red);
                    vectorCasillas.get(punto.getIndexCasilla()).setText("");
                }
                    
                return boleano; 
            }
            else{
               info.setForeground(Color.red);
               info.setText("*** Ingrese numeros *** \n*** dentro del rango ***");
               Toolkit.getDefaultToolkit().beep();
               vectorCasillas.get(punto.getIndexCasilla()).setBackground(Color.red);
               vectorCasillas.get(punto.getIndexCasilla()).setText("");               
               return false;
            }
        } catch (NumberFormatException e){
            Toolkit.getDefaultToolkit().beep();
            info.setForeground(Color.red);
            info.setText("**** Entrada Invalida ****");
            vectorCasillas.get(punto.getIndexCasilla()).setBackground(Color.red);
            vectorCasillas.get(punto.getIndexCasilla()).setText("");            
            return false;
        }
        
    } 
    
    public void imprimirMatrix(){
        System.out.println("\n");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]);
            }
             System.out.println();
        }
        System.out.println("\n\n");
    }
    
    public void actualizarTablero(){
        int index = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] != 0)
                    vectorCasillas.get(index).setText(""+matrix[i][j]);
                else
                    vectorCasillas.get(index).setText("");
                index++;
            }
        }
        
    }
    
    public void validarFinDeJuego(){        
        if (juego.juegoTermino(matrix)){
           Toolkit.getDefaultToolkit().beep();
           JOptionPane.showMessageDialog(this, "¡FELICIDADES HAS GANADO!", "Sodoku", 1);
            for (int i = 0; i < vectorCasillas.size(); i++) {
                vectorCasillas.get(i).setEditable(false);
                vectorCasillas.get(i).setFocusable(false);
                Color verde = new Color(9, 201, 2);
                info.setForeground(verde);
                info.setText("     ¡¡¡ HAS GANADO !!!");
                deshacer.setEnabled(false);
                rehacer.setEnabled(false);
                pista.setEnabled(false);
            }
       }
    }
         

    @Override
    public void actionPerformed(ActionEvent e) {
       
       if (e.getSource().equals(deshacer)){
           
            punto = juego.desApilarDeshacer();
            matrix[punto.getFila()][punto.getColumna()] = punto.getDatoPunto();
            int valorActual = Integer.parseInt(vectorCasillas.get(punto.getIndexCasilla()).getText());
            punto.setDatoPunto(valorActual);
            juego.apilarRehacer(punto);
            
            if (juego.esVaciaDeshacer()){
                deshacer.setEnabled(false);
                info.setForeground(Color.red);
                info.setText("**** No se puede ****\n**** deshacer mas ****");
            }

            rehacer.setEnabled(true);
                    
           juego.imprimirDeshacer();
           juego.imprimirRehacer();
           imprimirMatrix();
           
       }
       if (e.getSource().equals(rehacer)){           
            punto = juego.desApilarRehacer();   
            int valorActual = matrix[punto.getFila()][punto.getColumna()];
            matrix[punto.getFila()][punto.getColumna()] = punto.getDatoPunto();
            punto.setDatoPunto(valorActual);
            juego.apilarDeshacer(punto);

            if (juego.esVaciaRehacer()){
                 rehacer.setEnabled(false);
                 info.setForeground(Color.red);
                 info.setText("**** No se puede ****\n**** rehacer mas****");
            }
            deshacer.setEnabled(true);
            juego.imprimirDeshacer();
            juego.imprimirRehacer();
            imprimirMatrix();
       }
       
       if (e.getSource().equals(pista)){  
           
           int posFila = puntoCasillaAux.getFila();
           int posCol = puntoCasillaAux.getColumna();
           
           int pista = juego.darPista(matrix, posFila, posCol, fila, col);
           if( pista == -1){                
                info.setText("no hay numero posible");
            }
            else{           
                info.setForeground(Color.MAGENTA);
                info.setText("*** Usa "+pista+" ***");
                vectorCasillas.get(puntoCasillaAux.getIndexCasilla()).setBackground(Color.MAGENTA);
                vectorCasillas.get(puntoCasillaAux.getIndexCasilla()).requestFocus();
            }
       }
       
       
       
       if (e.getSource().equals(salir)){
            System.exit(0);
       }
       if(e.getSource().getClass().getName() == "javax.swing.JTextField"){
           generarPosicion(e);
           if(validarCasilla()){
               deshacer.setEnabled(true);
               Color verde = new Color(9, 201, 2);
               info.setForeground(verde);
               info.setText("    ***** Guardado *****");                
               vectorCasillas.get(punto.getIndexCasilla()).setBackground(Color.WHITE);
               
               punto.setDatoPunto(matrix[punto.getFila()][punto.getColumna()]); //dato anterior               
               matrix[punto.getFila()][punto.getColumna()] = Integer.parseInt(vectorCasillas.get(punto.getIndexCasilla()).getText()); //guarda la jugada en la matrix
               
               juego.apilarDeshacer(punto);
               juego.vaciarRehacer();
               rehacer.setEnabled(false);
               
               juego.imprimirDeshacer();
               
               imprimirMatrix();
           }
           else{               
               Toolkit.getDefaultToolkit().beep();
           }
       }
       actualizarTablero();
       validarFinDeJuego();                       
    } 
    
    @Override
    public void focusLost(FocusEvent e) {        
        if(e.getSource().getClass().getName().equals("javax.swing.JTextField")){
            e.getComponent().setBackground(Color.white);
            actualizarTablero();
            info.setForeground(Color.blue);
            info.setText("**** Presiona enter ****\n****  para guardar  ****");
        }        
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource().getClass().getName().equals("javax.swing.JTextField")){
            String aux = e.getComponent().getName();
            
            String indexVec = aux.substring(aux.indexOf("#")+1, aux.indexOf("$"));
            String filaX = aux.substring(aux.indexOf("$")+1, aux.indexOf("%"));
            String colX = aux.substring(aux.indexOf("%")+1, aux.indexOf("&"));  
            
            int indexVector = Integer.parseInt(indexVec);
            int posFila = Integer.parseInt(filaX);
            int posCol = Integer.parseInt(colX);
            
            puntoCasillaAux = new Punto(posFila, posCol, indexVector);
            
            System.out.println("-> ("+posFila+","+posCol+") "+indexVector); 
        }
    }

  
}    
       
       
       
    


















    
/* esta es la de pista inteliente que recorre la matriz codigo que me gusta mas
           Punto puntoAux = new Punto();
           puntoAux = juego.darPistaInteligente(matrix, fila, col);
           
           if (puntoAux.getDatoPunto() == -1){
               info.setForeground(Color.red);
               info.setText("no hay numero posible");
           }
           else{
               info.setForeground(Color.MAGENTA);
               info.setText("*** Usa "+puntoAux.getDatoPunto()+" ***");
               vectorCasillas.get(puntoAux.getIndexCasilla()).setBackground(Color.MAGENTA);
               vectorCasillas.get(puntoAux.getIndexCasilla()).requestFocus();               
           }
           */
