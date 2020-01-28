package Principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import Pantallas.PantallaFinal;
import Pantallas.PantallaInicio;
import Pantallas.PantallaJuego;
import Pantallas.Pantallas;

public class PanelJuego extends JPanel implements Runnable {

	/** PANTALLAS **/
	Pantallas pantallaEjecuccion;

	/** PINICIAL COLOR **/
	Color colorLetraInicio = Color.WHITE;

	// El contructor
	public PanelJuego() {
		pantallaEjecuccion = new PantallaInicio(this);

		// HILO
		new Thread(this).start();

		// L�?STENERS
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {				
				pantallaEjecuccion.pulsarRaton(e);				
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				//pantallaEjecuccion.redimensionar();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				pantallaEjecuccion.mover(e);				
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
	}

	// Método que se llama automáticamente
	@Override
	public void paintComponent(Graphics g) {
		pantallaEjecuccion.pintarPantalla(g);
	}

	@Override
	public void run() {
		while (true) {
			repaint();
			Toolkit.getDefaultToolkit().sync();
			pantallaEjecuccion.ejecutarFrame();
			// Siempre repinto.

		}

	}



	public void setPantallaEjecuccion(Pantallas pantalla) {
		pantallaEjecuccion = pantalla;

	}

}
