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

public class PantallaFinal implements Pantallas{
	
	PanelJuego panelJuego;

	/** FONDO **/
	private BufferedImage fondo;
	private Image fondoEscalado;

	public PantallaFinal(PanelJuego panelJuego) {
		inicializarPantalla(panelJuego);
		
		
		try {
			fondo = ImageIO.read(new File("imagenes/victoria.jpg"));				
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("PROBLEMAS AL CARGAR LAS IMÃ?GENES. FIN DEL PROGRAMA");
			System.exit(1);
		}
		redimensionar();
	}

	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelJuego=panel;		
	}

	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);	
		g.setColor(Color.yellow);
		g.setFont(new Font("",Font.BOLD,50));
		g.drawString("Victoria!!", panelJuego.getWidth()-500, panelJuego.getHeight()-100);
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pulsarRaton(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mover(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redimensionar() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), BufferedImage.SCALE_SMOOTH);
		
	}
	
	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}


}
