/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica8salacura;

/**
 *
 * @author Adria
 */
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.ArrayList;

class Pacientes {

    private int id, tipo;

    public Pacientes(int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public void setid(int id) {
        this.id = id;

    }

    public void settipo(int tipo) {
        this.tipo = tipo;

    }

    public int getid() {
        return id;
    }

    public int gettipo() {
        return tipo;
    }
}

/**
 *
 * @author usuario
 */
public class CanvasCentroSalud extends Canvas {

    private ArrayList<Pacientes> colaInfectados = new ArrayList();
    private ArrayList<Pacientes> colaNormales = new ArrayList();
    private ArrayList<Pacientes> colaConsulta = new ArrayList();

    Image infectadoimg, normalimg, sanitarioimg, virusimg, limpiadorimg;

    public CanvasCentroSalud(int ancho, int alto) throws InterruptedException {
        super.setSize(ancho, alto);
        super.setBackground(Color.white);

        infectadoimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/Pinfectado.png"));
        normalimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/Pnormal.png"));
        sanitarioimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/sanitario.png"));
        virusimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/Virus.png"));
        limpiadorimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/limpiador.png"));

        MediaTracker dibu = new MediaTracker(this);
        dibu.addImage(infectadoimg, 0);
        dibu.waitForID(0);
        dibu.addImage(normalimg, 1);
        dibu.waitForID(1);
        dibu.addImage(sanitarioimg, 2);
        dibu.waitForID(2);
        dibu.addImage(virusimg, 3);
        dibu.waitForID(3);
        dibu.addImage(virusimg, 4);
        dibu.waitForID(4);
    }

    public synchronized void entraCola(int tipo, int id) {
        if (tipo == 1) {
            colaInfectados.add(new Pacientes(id, tipo));
        } else {
            colaNormales.add(new Pacientes(id, tipo));
        }

        repaint();
    }

    public synchronized void saleCola(int tipo, int id) {
        if (tipo == 1) {
            colaInfectados.remove(new Pacientes(id, tipo));
        } else {
            colaNormales.remove(new Pacientes(id, tipo));
        }

        repaint();
    }

    public synchronized void entraConsulta(int tipo, int id) {
        colaConsulta.add(new Pacientes(id, tipo));

        repaint();
    }

    public synchronized void saleConsulta(int tipo, int id) {
        boolean encontrado = false;
        int i = 0;

        while (!encontrado) {
            if (colaConsulta.get(i).getid() == id) {
                encontrado = true;
            } else {
                i++;
            }
        }
        if (tipo == 1) {
            colaConsulta.add(new Pacientes(-1, 4));
        }
        colaConsulta.remove(i);

        repaint();
    }

    public synchronized void entraLimpiador(int tipo, int id) {
        colaConsulta.add(new Pacientes(id, tipo));

        repaint();
    }

    public synchronized void saleLimpiador(int tipo, int id) {
        boolean encontrado = false;
        int i = 0;

        while (!encontrado) {
            if (colaConsulta.get(i).getid() == id) {
                encontrado = true;
            } else {
                i++;
            }
        }
        colaConsulta.remove(i);
        encontrado = false;
        i = 0;
        while (!encontrado) {
            if (colaConsulta.get(i).getid() == -1) {
                encontrado = true;
            } else {
                i++;
            }
        }
        colaConsulta.remove(i);
        repaint();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        

        Image imagen = createImage(getWidth(), getHeight());
        Font f1 = new Font("Arial", Font.BOLD, 15);
        Graphics og = imagen.getGraphics();
        og.setFont(f1);

        setBackground(Color.lightGray);

        og.setColor(Color.white);
        og.fillRect(10, 10, 865, 150);
        og.fillRect(10, 400, 865, 150);

        for (int i = 0; i < colaInfectados.size(); i++) {
            og.setColor(Color.GREEN);
            og.drawString("" + colaInfectados.get(i).getid(), 92 * i + 15, 24);
            og.drawImage(infectadoimg, 92 * i + 5, 28, 100, 100, this);
        }

        for (int i = 0; i < colaNormales.size(); i++) {
            og.setColor(Color.MAGENTA);
            og.drawString("" + colaNormales.get(i).getid(), 92 * i + 15, 400);
            og.drawImage(normalimg, 92 * i + 5, 400, 100, 100, this);
        }
        for (int i = 0; i < colaConsulta.size(); i++) {
            og.setColor(Color.MAGENTA);
            if (colaConsulta.get(i).gettipo() == 0) {
                og.drawString("" + colaConsulta.get(i).getid(), 200 * i + 387, 240);
                og.drawImage(normalimg, 200 * i + 387, 260, 100, 100, this);
                 og.drawImage(sanitarioimg, 200 * i + 300, 260, 100, 100, this);
            } else if (colaConsulta.get(i).gettipo() == 1) {
                og.drawString("" + colaConsulta.get(i).getid(),  200 * i + 387, 240);
                og.drawImage(infectadoimg, 200 * i + 387, 260, 100, 100, this);
                og.drawImage(sanitarioimg, 200 * i + 300, 260, 100, 100, this);
                og.drawImage(sanitarioimg, 200 * i + 330, 260, 100, 100, this);
            } else if (colaConsulta.get(i).gettipo() == 2) {
                og.drawString("" + colaConsulta.get(i).getid(), 92 * i + 487, 240);
                og.drawImage(limpiadorimg, 92 * i + 487, 260, 100, 100, this);
            } else if (colaConsulta.get(i).gettipo() == 4) {
                og.drawImage(virusimg, 50, 200, 100, 100, this);
            }

        }
        g.drawImage(imagen, 0, 0, this);
    }

}
