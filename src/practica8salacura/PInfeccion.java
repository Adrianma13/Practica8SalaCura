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
    int id;

    public PInfeccion(CentroSalud cs,int id) {
        this.cs=cs;
        this.id=id;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());

        try {
            cs.EntraPacienteI(id);
            System.out.println("Entra el Paciente infeccioso en la sala");
        } catch (InterruptedException ex) {
            Logger.getLogger(PInfeccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Thread.sleep((rnd.nextInt(5) + 1) * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PNormal.class.getName()).log(Level.SEVERE, null, ex);
        }
        cs.SalePacienteI(id);
        System.out.println("Sale el Paciente infeccioso en la sala");
    }
}
