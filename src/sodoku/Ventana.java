/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sodoku;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author ~Antares~
 */
public class Ventana extends JFrame implements ActionListener{
    
    private JLabel logo;
    private JButton jugar, salir, acerca, examinar, cancelar;
    private Container contenedor;
    private JFileChooser abrir;
    
    private VentanaSodoku sodoku;
    
    
    public Ventana(){
        super("Sodoku");        

        logo = new JLabel();
        logo.setForeground(Color.red);
        logo.setIcon(new ImageIcon ("src/sodoku/Imagen/sudoku.png"));
        logo.setBounds(150, 0, 500, 200);        
        
        jugar = new JButton("Jugar");
        jugar.addActionListener(this);
        jugar.setBounds(250, 200, 300, 40);
        jugar.setFont(new Font("Arial", Font.BOLD, 16));
        
        salir = new JButton("Salir");
        salir.addActionListener(this);
        salir.setBounds(250, 260, 300, 40);
        salir.setFont(new Font("Arial", Font.BOLD, 16));
        
        acerca = new JButton("Acerca");
        acerca.addActionListener(this);
        acerca.setBounds(250, 320, 300, 40);
        acerca.setFont(new Font("Arial", Font.BOLD, 16));        
        
        contenedor = getContentPane();
        contenedor.setLayout(null);
        
        contenedor.add(logo);
        contenedor.add(jugar);
        contenedor.add(salir);
        contenedor.add(acerca);
        
        setVisible(true);
        setSize(800, 440);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    public String convertirAString(File archivo){
        String linea = "";
        String texto = "";        
        try{
            FileReader archivos = new FileReader(archivo);
            BufferedReader lee = new BufferedReader(archivos);

            while((linea=lee.readLine())!=null)
            {
             texto+= linea+"\n";
            }
            lee.close();
        }
        catch(IOException ex){
                JOptionPane.showMessageDialog(null,ex+"" +
                       "\nNo se ha encontrado el archivo",
                       "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
        }   
        return texto;
    }
    
    public boolean validarArchivo(String texto){     
        int fila = Integer.parseInt(texto.substring(0, 1));
        int col  = Integer.parseInt(texto.substring(2, 3));
        int tamaño = fila*col;
        
        String textoMatriz = texto.substring(4);
        textoMatriz = textoMatriz.replace(" ", "").replace("\n", "");
        if (textoMatriz.length()== tamaño*tamaño)
            return true;
        else 
            return false;
    }
    
    public void leerArchivo(){    
            
            abrir = new JFileChooser();
            abrir.showOpenDialog(this);
            String texto = "";
            
            File archivo = abrir.getSelectedFile(); 
            
            if(archivo!=null){
                texto = convertirAString(archivo);
                if (validarArchivo(texto)){
                    sodoku = new VentanaSodoku(texto);
                    sodoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);
                } 
                else{
                    JOptionPane.showMessageDialog(null,"" +
                       "\nEl archivo no cumple con los lineamientos\nPor favor seleccione otro",
                       "ERROR!!!",JOptionPane.ERROR_MESSAGE);
                }   
            }
    }
        
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(jugar)){
            
            salir.setVisible(false);
            acerca.setVisible(false);    
            
            jugar.setEnabled(false);
            jugar.setText("Por favor seleccione el archivo fuente...");              
            jugar.setFont(new Font("Arial", Font.BOLD, 24));
            jugar.setBounds(150, 200, 500, 40);
            
            examinar = new JButton("Examinar...");
            examinar.addActionListener(this);
            examinar.setBounds(250, 260, 300, 40);
            examinar.setFont(new Font("Arial", Font.BOLD, 16));
            
            cancelar = new JButton("Cancelar");
            cancelar.addActionListener(this);
            cancelar.setBounds(250, 320, 300, 40);         
            cancelar.setFont(new Font("Arial", Font.BOLD, 16));
            
            contenedor.add(examinar);
            contenedor.add(cancelar);
        }
                   
        
        if (e.getSource().equals(salir)){
            System.exit(0);
        }
        
        if (e.getSource().equals(acerca)){
            JOptionPane.showMessageDialog(null, null, null, WIDTH, new ImageIcon("src/sodoku/Imagen/sudoku.png")); //new ImageIcon("src/sodoku/Imagen/sudoku.png")
        }
        
        if (e.getSource().equals(cancelar)){
            
            jugar.setEnabled(true);
            jugar.setText("Jugar");              
            jugar.setBounds(250, 200, 300, 40);
            jugar.setFont(new Font("Arial", Font.BOLD, 16));
            
            salir.setVisible(true);
            acerca.setVisible(true);
            
            examinar.setVisible(false);
            cancelar.setVisible(false);
        }
        
        if (e.getSource().equals(examinar)) {
            leerArchivo();
        }        
    }    
}
