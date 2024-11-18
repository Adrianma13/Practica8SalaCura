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
        canvas.entraCola(1, id);
        try {
            while (sanitarios < 2 || salaInfectada) {
                colaPInfeccion.await();
            }
            sanitarios -= 2;
            canvas.saleCola(1, id);
            canvas.entraConsulta(1, id);
        } finally {
            mutex.unlock();
        }
    }

    public void EntraPacienteN(int id) throws InterruptedException {
        mutex.lock();
        canvas.entraCola(0, id);//tipo 0 ==normal tipo 1==infectado
        try {
            while (sanitarios == 0 || salaInfectada) {
                colaPNormal.await();
            }
            sanitarios--;
            canvas.saleCola(0, id);
            canvas.entraConsulta(0, id);
        } finally {
            mutex.unlock();
        }

    }

    public void SalePacienteI(int id) {
        mutex.lock();
        try {
            sanitarios += 2;
            salaInfectada = true;
            if (sanitarios == 3 && salaInfectada) {
                Limpiador.signal();
            }else
              if (sanitarios >= 2) {
               colaPInfeccion.signal();
            }
            canvas.saleConsulta(1, id);
        } finally {
            mutex.unlock();
        }

    }

    public void SalePacienteN(int id) {
        mutex.lock();
        try {
            sanitarios++;
            if (sanitarios == 3 && salaInfectada) {
                Limpiador.signal();
            }else
             if (sanitarios >= 2) {
               colaPInfeccion.signal();
            }
            canvas.saleConsulta(0, id);
        } finally {
            mutex.unlock();
        }
    }

    public void EntraLimpiador(int id) throws InterruptedException {
        mutex.lock();
        try {
            while (sanitarios != 3 || !salaInfectada) {
                Limpiador.await();
            }
            canvas.entraLimpiador(2, id);
            sanitarios = 0;
        } finally {
            mutex.unlock();
        }
    }

    public void SaleLimpiador(int id) {
        mutex.lock();
        try {
            sanitarios = 3;
            salaInfectada = false;
            canvas.saleLimpiador(2, id);
        } finally {
            mutex.unlock();
        }
    }

}
