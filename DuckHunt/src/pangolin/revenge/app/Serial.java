/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pangolin.revenge.app;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Serial {

    private ArrayList<String> buffer;
    private PanamaHitek_Arduino ino;
    private SerialPortEventListener listener;

    public Serial() {
        String msg = null;
        try {
            buffer = new ArrayList();
            ino = new PanamaHitek_Arduino();
            listener = new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent arg0) {                    
                        try {
                            buffer.add(ino.printMessage());
                        } catch (SerialPortException ex) {
                            Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ArduinoException ex) {
                            Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, ex);
                        }                    
                }
            };
            ino.arduinoRXTX("COM5", 9600, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendData(int msg) {
        try {
            ino.sendData(msg+"");
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String receiveData() {
        try {
            while (buffer.isEmpty()) {
                Thread.sleep(100);
            }            
            String primero =buffer.remove(0);
            buffer.clear();
            return primero;
        } catch (Exception ex) {
            Logger.getLogger(Serial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}