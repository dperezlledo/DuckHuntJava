/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pangolin.revenge.app;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author David
 */
public class VPrincipal extends JFrame implements WindowListener  {   
    private PanelFondo jpnF;    
    private CardLayout cl;
    private JPanel jpnNegro;
    private JLabel jlbCuadro;
    
    public VPrincipal() {      
        cl = new CardLayout();
        getContentPane().setLayout(cl); 
        jlbCuadro = new JLabel();
        jlbCuadro.setBackground(Color.WHITE);
        jlbCuadro.setOpaque(true);
        jpnF = new PanelFondo(this);            
        jpnNegro = new JPanel();
        jpnNegro.setBackground(Color.BLACK);        
        jpnNegro.setLayout(null);
        getContentPane().add(jpnF, "fondo");  
        getContentPane().add(jpnNegro,"black");
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new VPrincipal();
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    
    @Override
    public void windowClosed(WindowEvent e) {}
    
    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {
        jpnF.setBackground(getWidth(), getHeight());
        jpnNegro.setBounds(0,0,getWidth(), getHeight());
        System.out.println("Tamaño ventana (" + getWidth() + ", " + getHeight());        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {}

    public void disparo(Rectangle r) {
        try {
            jlbCuadro.setBounds(r);
            jpnNegro.add(jlbCuadro);
            cl.show(getContentPane(), "black");

        } catch (Exception ex) {
            Logger.getLogger(VPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fondo() {
        cl.show(getContentPane(), "fondo");
    }
    
    
    
}
