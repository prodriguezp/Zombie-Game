package Pantallas;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.spi.AudioFileReader;

import Principal.PanelJuego;

public class PantallaIntermedia implements Pantallas{
	PanelJuego panel;
	
	BufferedImage fondo;
	Image fondoResize;
	Font titulo;

	
	public PantallaIntermedia(PanelJuego panel) {
		super();
		inicializarPantalla(panel);
		try {
			fondo = ImageIO.read(new File("Imagenes/fondoIntermedio.jpg"));
			fondoResize = fondo.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
			titulo = Font.createFont(Font.TRUETYPE_FONT, new File("Font/ZOMBIE.TTF"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(titulo);
		} catch (Exception e) {
			
			// TODO: handle exception
		}
	}

	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panel = panel;
		
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(fondoResize,0,0,null);
		g.setColor(Color.RED);
		g.setFont(new Font("ZombieA",Font.BOLD,70));
		g.drawString("NIVEL "+PantallaJuego.nivelActual, panel.getWidth()/2-150, panel.getHeight()/2);
	}

	@Override
	public void ejecutarFrame() {
		try {
						
			Clip sonido = AudioSystem.getClip();
			sonido.open(AudioSystem.getAudioInputStream(new File("Sonidos/sonidoEntreRondas.wav")));
			sonido.start();
			
			Thread.sleep(4000);
			
		
			panel.setPantallaEjecuccion(new PantallaJuego(panel));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
		fondoResize = fondo.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
		
	}

}
