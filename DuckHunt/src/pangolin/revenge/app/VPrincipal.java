/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pangolin.revenge.app;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
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
public class VPrincipal extends JFrame implements WindowListener, Runnable {

    private PanelFondo jpnF;
    private CardLayout cl;
    private JPanel jpnNegro;
    private JLabel jlbCuadro;
    private Serial usb;

    public VPrincipal(Serial usb) {
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
        getContentPane().add(jpnNegro, "black");
        
        ImageIcon ImageIcon = new ImageIcon(getClass().getResource("/pangolin/revenge/res/gfx/icons8-gun-30.png"));
        Image Image = ImageIcon.getImage();
        this.setIconImage(Image);
        this.usb = usb;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);        
        setVisible(true);       
        JOptionPane.showMessageDialog(this, "Dispone de 25 balas en la escopeta.\n"
                + "Cuando se le acaben las balas, acabará la partida\n"
                + "y se mostrará el  resultado de la cacería.\n¡Buena Suerte!", 
                "Instrucciones", JOptionPane.INFORMATION_MESSAGE);
        new Thread(this).start();
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
        jpnNegro.setBounds(0, 0, getWidth(), getHeight());
    }

    @Override
    public void windowDeactivated(WindowEvent e) {}

    public void disparo(Rectangle r) {
        String msg;
        try {
            jlbCuadro.setBounds(r);
            jpnNegro.add(jlbCuadro);
            cl.show(getContentPane(), "black");
        } catch (Exception ex) {
            Logger.getLogger(VPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String gatillo, ac,fa,re;
        int c=0;
        try {
        while (true) {
            gatillo = usb.receiveData();
            System.out.println("" + gatillo); 
            setTitle("");
            if (gatillo.length()!=0) {                     
                    disparo(new Rectangle(jpnF.getPatoX()-25, jpnF.getPatoY()-25, 150,150));
                    Thread.sleep(500);                   
                    cl.show(getContentPane(), "fondo");   
                    setTitle(gatillo);
                    if (gatillo.split("-").length==3) {
                        fa = gatillo.split("-")[0];
                        ac = gatillo.split("-")[1];
                        re = gatillo.split("-")[2];
                        c = Integer.parseInt(ac) + Integer.parseInt(fa) + Integer.parseInt(re);
                        if (c==25) // Fin juego a los 25 tiros
                            break;
                    }
            }            
        }
        JOptionPane.showMessageDialog(this, "Disparos realizados: " + c + "\nDisparos errados:" + fa
                + "\nPatos cazados: " + ac +  "\nMal detectados:" + re, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        } catch (Exception ex) {
          Logger.getLogger(VPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
