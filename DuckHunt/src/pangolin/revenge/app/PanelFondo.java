/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pangolin.revenge.app;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import javax.swing.JPanel;

public class PanelFondo extends JPanel implements Runnable, MouseListener {

    public static final int VELOCIDAD = 200;
    private Image background;
    private JLabel jlbPato1, jlbPato2, jlbPato3, jlbAux;
    private VPrincipal principal;
    private int width, height;

    public PanelFondo(VPrincipal principal) {
        this.principal = principal;
        setLayout(null);
        ImageIcon id1 = new ImageIcon(getClass().getResource("/pangolin/revenge/res/gfx/duck1.png"));
        jlbPato1 = new JLabel(id1);
        ImageIcon id2 = new ImageIcon(getClass().getResource("/pangolin/revenge/res/gfx/duck2.png"));
        jlbPato2 = new JLabel(id2);
        ImageIcon id3 = new ImageIcon(getClass().getResource("/pangolin/revenge/res/gfx/duck3.png"));
        jlbPato3 = new JLabel(id3);
        jlbPato1.setBounds(-100, 100, id1.getIconWidth(), id1.getIconHeight());
        jlbPato2.setBounds(1500, 400, id1.getIconWidth(), id1.getIconHeight());
        jlbPato3.setBounds(500, 800, id1.getIconWidth(), id1.getIconHeight());
        this.add(jlbPato1);
        this.add(jlbPato2);
        this.add(jlbPato3);
        this.addMouseListener(this);
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.background != null) {
            g.drawImage(this.background, 0, 0, width, height, null);
        }
        super.paintComponent(g);
    }

    public void setBackground(int width, int height) {
        this.setOpaque(false);
        this.width = width;
        this.height = height;
        Image aux = new ImageIcon(getClass().getResource("/pangolin/revenge/res/gfx/Fondo.jpg")).getImage();
        aux = aux.getScaledInstance(this.width, this.height, java.awt.Image.SCALE_SMOOTH);
        ImageIcon ii = new ImageIcon(aux);
        this.background = ii.getImage();
        repaint();
    }

    @Override
    public void run() {
        int np;
        while (true) {
            np = (int) (Math.random() * 3);

            try {
                switch (np) {
                    case 0:
                        // Pato 1
                        jlbAux = jlbPato1;
                        for (int i = 0; i < 80; i++) {
                            Thread.sleep(VELOCIDAD);
                            jlbPato1.setLocation((int) (jlbPato1.getLocation().getX() + i), (int) (jlbPato1.getLocation().getY()));
                        }
                        jlbPato1.setLocation(-100, (int) ((Math.random() * 500) + 100));

                        break;
                    case 1:
                        jlbAux = jlbPato2;
                        for (int i = 0; i < 80; i++) {
                            Thread.sleep(VELOCIDAD);
                            jlbPato2.setLocation((int) (jlbPato2.getLocation().getX() - i), (int) (jlbPato2.getLocation().getY()));
                        }
                        jlbPato2.setLocation(1500, (int) ((Math.random() * 500) + 100));

                        break;
                    default:
                        //pato 3
                        jlbAux = jlbPato3;
                        for (int i = 0; i < 50; i++) {
                            Thread.sleep(VELOCIDAD);
                            jlbPato3.setLocation((int) (jlbPato3.getLocation().getX()), (int) (jlbPato3.getLocation().getY() - i));
                        }
                        jlbPato3.setLocation((int) ((Math.random() * 1100) + 100), 800);

                        break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(PanelFondo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Rectangle r = new Rectangle(jlbAux.getX() - 25, jlbAux.getY() - 25, jlbAux.getWidth() + 50, jlbAux.getHeight() + 50);
        principal.disparo(r);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        principal.fondo();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
