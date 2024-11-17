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

    Image infectadoimg, normalimg, sanitarioimg, virusimg;

    public CanvasCentroSalud(int ancho, int alto) throws InterruptedException {
        super.setSize(ancho, alto);
        super.setBackground(Color.white);

        infectadoimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/Pinfectado.png"));
        normalimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/Pnormal.png"));
        sanitarioimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/sanitario.png"));
        virusimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagenes/Virus.png"));

        MediaTracker dibu = new MediaTracker(this);
        dibu.addImage(infectadoimg, 0);
        dibu.waitForID(0);
        dibu.addImage(normalimg, 1);
        dibu.waitForID(1);
        dibu.addImage(sanitarioimg, 2);
        dibu.waitForID(2);
        dibu.addImage(virusimg, 3);
        dibu.waitForID(3);
    }

    public synchronized void entraCola(int tipo, int id) {
        if (tipo == 0) {
            colaInfectados.add(new Pacientes(id, tipo));
        } else {
            colaNormales.add(new Pacientes(id, tipo));
        }

        repaint();
    }

    public synchronized void saleCola(int tipo, int id) {
        if (tipo == 0) {
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
        colaConsulta.remove(i);

        repaint();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        int[] posColaPerro = {10, 10, 460, 150};
        int[] posColaGato = {480, 10, 460, 150};

        Image imagen = createImage(getWidth(), getHeight());
        Font f1 = new Font("Arial", Font.BOLD, 15);
        Graphics gbuf = imagen.getGraphics();
        gbuf.setFont(f1);

        setBackground(Color.lightGray);

        //gbuf.drawImage(centrosaludimg, 375, 300, null);
        gbuf.setColor(Color.white);
        gbuf.fillRect(posColaPerro[0], posColaPerro[1], posColaPerro[2], posColaPerro[3]);
        gbuf.fillRect(posColaGato[0], posColaGato[1], posColaGato[2], posColaGato[3]);

        for (int i = 0; i < colaInfectados.size(); i++) {
            gbuf.setColor(Color.GREEN);
            gbuf.drawString(""+colaInfectados.get(i).getid(), 92 * i + 15, 24);
            gbuf.drawImage(infectadoimg, 92 * i + 5, 28, null);
        }

        for (int i = 0; i < colaNormales.size(); i++) {
            gbuf.setColor(Color.MAGENTA);
            gbuf.drawString(""+colaNormales.get(i).getid(), 92 * i + 487, 24);
            gbuf.drawImage(normalimg, 92 * i + 487, 28, null);
        }

        if (colaConsulta.size() > 0) {
            if (colaConsulta.get(0).gettipo()== 0) {
                gbuf.setColor(Color.GREEN);
                gbuf.drawImage(infectadoimg, 430, 200, null);
            } else {
                gbuf.setColor(Color.MAGENTA);
                gbuf.drawImage(normalimg, 440, 200, null);
            }
           // gbuf.drawString(colaConsulta.get(0).getNombre(), 440, 190);
        }

        if (colaConsulta.size() > 1) {
            if (colaConsulta.get(1).gettipo()== 0) {
                gbuf.setColor(Color.GREEN);
                gbuf.drawImage(infectadoimg, 560, 310, null);
            } else {
                gbuf.setColor(Color.MAGENTA);
                gbuf.drawImage(normalimg, 570, 310, null);
            }
           // gbuf.drawString(colaConsulta.get(1).getNombre(), 570, 300);
        }

        if (colaConsulta.size() > 2) {
            if (colaConsulta.get(2).gettipo()== 0) {
                gbuf.setColor(Color.GREEN);
                gbuf.drawImage(infectadoimg, 430, 460, null);
            } else {
                gbuf.setColor(Color.MAGENTA);
                gbuf.drawImage(normalimg, 440, 460, null);
            }
          // gbuf.drawString(colaConsulta.get(2).getNombre(), 440, 450);
        }

        if (colaConsulta.size() > 3) {
            if (colaConsulta.get(3).gettipo()== 0) {
                gbuf.setColor(Color.GREEN);
                gbuf.drawImage(infectadoimg, 290, 310, null);
            } else {
                gbuf.setColor(Color.MAGENTA);
                gbuf.drawImage(normalimg, 300, 310, null);
            }
           // gbuf.drawString(colaConsulta.get(3).getNombre(), 300, 300);
        }

        g.drawImage(imagen, 0, 0, this);
    }

}
