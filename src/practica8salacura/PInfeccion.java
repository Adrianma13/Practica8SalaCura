/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica8salacura;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class PInfeccion implements Runnable {

    CentroSalud cs;

    PInfeccion() {
        
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());

        cs.EntraPacienteI();
        try {
            Thread.sleep((rnd.nextInt(5) + 1) * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PNormal.class.getName()).log(Level.SEVERE, null, ex);
        }
        cs.SalePacienteI();
    }
}
