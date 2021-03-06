/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pangolin.revenge.app;

import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class VCalibracion extends javax.swing.JFrame implements Runnable {
    private Serial usb;
    
    /**
     * Creates new form VCalibracion
     */
    public VCalibracion() {
        initComponents();
        setTitle("Calibración de los colores");
        ImageIcon ImageIcon = new ImageIcon(getClass().getResource("/pangolin/revenge/res/gfx/icons8-gun-30.png"));
        Image Image = ImageIcon.getImage();
        this.setIconImage(Image);
        setExtendedState(JFrame.MAXIMIZED_BOTH);        
        new Thread(this).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        String msg;
        
        try {            
            Thread.sleep(2000);
            usb = new Serial();
            do {
                JOptionPane.showMessageDialog(this, "Realice durante 5 segundos, pequeños circulos sobre el color"
                        + " NEGRO a una distancia entre 5 y 10 cm", "Calibrando color NEGRO", JOptionPane.WARNING_MESSAGE);                
                usb.sendData(1); //Enviar mensaje a Arduino
                Thread.sleep(6000); // Esperamos a que arduino coja datos
               
                JOptionPane.showMessageDialog(this, "Ahora realice la misma operación, sobre el color"
                        + " BLANCO a una distancia entre 5 y 10 cm", "Calibrando color BLANCO", JOptionPane.WARNING_MESSAGE);
                usb.sendData(2); //Enviar mensaje a Arduino
                Thread.sleep(6000);
                msg=usb.receiveData();
                System.out.println(""+ msg);
                if (msg.equals("CER")) { // No se calibró correctamente
                    JOptionPane.showMessageDialog(this, "Calibración incorrecta, se repetirá el proceso", "Error", JOptionPane.ERROR_MESSAGE);
                }             
            } while (msg.equals("CER"));
           
            JOptionPane.showMessageDialog(this, "La calibración se ha realizado de forma correcta. \nPulse "
                    + "Aceptar para empezar el juego. \nResultado " + msg, "Inicio del Juego", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            VPrincipal vp = new VPrincipal(usb);
            //System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(VCalibracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
