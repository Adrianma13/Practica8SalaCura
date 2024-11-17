/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica8salacura;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class CentroSalud {

    private int sanitarios;
    boolean salaInfectada;
    CanvasCentroSalud canvas;
    private Lock mutex = new ReentrantLock();

    private Condition colaPInfeccion = mutex.newCondition();
    private Condition colaPNormal = mutex.newCondition();
    private Condition Limpiador = mutex.newCondition();
    
    public CentroSalud(CanvasCentroSalud canvas){
        this.canvas=canvas;
        this.salaInfectada = false;
        this.sanitarios = 3;
    }

    public void EntraPacienteI(int id) throws InterruptedException {
        mutex.lock();
        canvas.entraCola(0, id);
        try {
            while (sanitarios < 2 || salaInfectada) {
                colaPInfeccion.await();
            }
            sanitarios -= 2;
        } finally {
            mutex.unlock();
        }
    }

    public void EntraPacienteN() throws InterruptedException {
        mutex.lock();
        try {
            while (sanitarios == 0 || salaInfectada) {
                colaPNormal.await();
            }
            sanitarios--;
        } finally {
            mutex.unlock();
        }

    }

    public void SalePacienteI() {
        mutex.lock();
        try {
            sanitarios += 2;
            salaInfectada = true;
            if (sanitarios == 3 && salaInfectada) {
                Limpiador.signal();
            }
        } finally {
            mutex.unlock();
        }

    }

    public void SalePacienteN() {
        mutex.lock();
        try {
            sanitarios++;
            if (sanitarios == 3 && salaInfectada) {
                Limpiador.signal();
            }
        } finally {
            mutex.unlock();
        }
    }

    public void EntraLimpiador() throws InterruptedException {
        mutex.lock();
        try {
            while (sanitarios != 3 || !salaInfectada) {
                Limpiador.await();
            }
            sanitarios = 0;
        } finally {
            mutex.unlock();
        }
    }

    public void SaleLimpiador() {
        mutex.lock();
        try {
            sanitarios = 3;
            salaInfectada = false;
        } finally {
            mutex.unlock();
        }
    }

}
