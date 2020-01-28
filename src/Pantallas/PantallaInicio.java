package Pantallas;

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

import Principal.PanelJuego;

public class PantallaInicio implements Pantallas{
	/**PINICIAL COLOR **/
	Color colorLetraInicio = Color.RED;
	
	PanelJuego panelJuego;
	BufferedImage fondo;
	Image redimensionada;
	
	Font titulo;
	Clip sonido;
	
	public PantallaInicio(PanelJuego panel) {
		inicializarPantalla(panel);
		try {
			fondo = ImageIO.read(new File(("Imagenes/fondoPrincipal.jpg")));
			redimensionada = fondo.getScaledInstance(1420, 838, Image.SCALE_FAST);
			titulo = Font.createFont(Font.TRUETYPE_FONT, new File("Font/ZOMBIE.TTF"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();					
			ge.registerFont(titulo);
			
			
			sonido = AudioSystem.getClip();
			sonido.open(AudioSystem.getAudioInputStream(new File("Sonidos/musicaInicio.wav")));
			sonido.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelJuego = panel;
		
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(redimensionada, 0, 0, null);
		g.setColor(colorLetraInicio);
		g.setFont(new Font("ZombieA",Font.BOLD,40));
		g.drawString("WELCOME TO ZOMBIE GAME", panelJuego.getWidth() / 2 - 260, panelJuego.getHeight() / 2 - 200);
		
	}

	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		colorLetraInicio = colorLetraInicio == Color.RED ? Color.GRAY : Color.RED;
		
	}

	@Override
	public void pulsarRaton(MouseEvent event) {
		sonido.stop();
		panelJuego.setPantallaEjecuccion(new PantallaIntermedia(panelJuego));	
	}

	@Override
	public void mover(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redimensionar() {
		redimensionada = fondo.getScaledInstance(panelJuego.getWidth(),panelJuego.getHeight(), Image.SCALE_FAST);
		
	}

}
