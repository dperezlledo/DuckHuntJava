/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pangolin.revenge.app;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author David
 */
public class Prueba {
    private static ArrayList<String> buffer;
    private PanamaHitek_Arduino ino;
    private SerialPortEventListener listener;

    public Prueba() {
        try {
            buffer = new ArrayList();
            ino = new PanamaHitek_Arduino(); 
            listener = new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent arg0) {
                    try {
                        buffer.add(ino.printMessage());
                    } catch (SerialPortException ex) {
                        Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ArduinoException ex) {
                        Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            ino.arduinoRXTX("COM5", 9600, listener);
        } catch (Exception ex) {
            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send(String msg) throws ArduinoException, SerialPortException {
          ino.sendData(msg);
    }
    
    public String receive() throws ArduinoException, SerialPortException {
        String aux=null;
        if (ino.receiveData()!=null) {
            aux = new String(ino.receiveData());
        }
        return aux;
    }
    
    public static void main(String[] args) {
       
        try {
            Prueba p = new Prueba();
            Thread.sleep(2000);
            p.send("Hola");
            System.out.println("Enviado Hola");
            Thread.sleep(2000);
            p.send("Adios");
            System.out.println("Enviado Adios");
            do {
                Thread.sleep(100);
            } while(buffer.size()==0);
            System.out.println("\n=>" + buffer.remove(0));
            do {
                Thread.sleep(100);
            } while(buffer.size()==0);
            System.out.println("\n=>" + buffer.remove(0));
            System.exit(0);
            
        } catch (ArduinoException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
}
