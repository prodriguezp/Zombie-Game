package Pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Principal.PanelJuego;
/**
 * Pantalla Final Game Over. Muestra un mensaje de que has perdido todas las vidas
 * @author Pedro Luis
 *
 */
public class PantallaFinalLose implements Pantallas{
	/** PANEL DE JUEGO**/
	PanelJuego panelJuego;
	

	/** FONDO **/
	private BufferedImage fondo;
	private Image fondoEscalado;

	public PantallaFinalLose(PanelJuego panelJuego) {
		inicializarPantalla(panelJuego);
		
		
		try {
			fondo = ImageIO.read(new File("imagenes/gameover.png"));				
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("PROBLEMAS AL CARGAR LAS IMÃ?GENES. FIN DEL PROGRAMA");
			System.exit(1);
		}
		redimensionar();
	}

	/**
	 * Establece el panel en el que vamos a pintar
	 */
	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelJuego=panel;		
	}

	/**
	 * Metodo que pinta en el panel de juego cada cierto tiempo
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);	
		g.setColor(Color.GRAY);
		g.setFont(new Font("",Font.BOLD,40));
		g.drawString("Pulsa para volver al inicio", 100, panelJuego.getHeight()-100);
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Al pulsar el raton , nos redirige a otra pantalla
	 */
	@Override
	public void pulsarRaton(MouseEvent event) {
		panelJuego.setPantallaEjecuccion(new PantallaInicio(panelJuego));
		
	}

	@Override
	public void mover(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Escala la imagen
	 */
	@Override	
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), BufferedImage.SCALE_SMOOTH);
		
	}
	
	/**
	 * Pinta el fondo reescalado
	 * @param g
	 */
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}


}
