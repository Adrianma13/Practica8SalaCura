/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica8salacura;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author usuario
 */
public class CentroSalud {

    private int sanitarios = 3;
    boolean salaInfectada = false;
    Lock mutex = new ReentrantLock();

    public void EntraPacienteI() throws InterruptedException {
        mutex.lock();
        while (sanitarios < 2 || salaInfectada) {
            wait();
        }
        sanitarios -= 2;
        mutex.unlock();
    }

    public void EntraPacienteN() throws InterruptedException {
        while (sanitarios == 0 || salaInfectada) {
            wait();
        }
        sanitarios--;

    }

    public void SalePacienteI() {
        mutex.lock();
        sanitarios += 2;
        salaInfectada = true;
        mutex.unlock();

    }

    public void SalePacienteN() {
        mutex.lock();
        sanitarios ++;
        
        mutex.unlock();
    }

    public void EntraLimpiador() {
    }

    public void SaleLimpiador() {
    }

}
