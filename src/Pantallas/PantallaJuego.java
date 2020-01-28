package Pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import Principal.PanelJuego;
import Principal.Sprite;
/**
 * Pantalla principal del juego donde se va a desarrolar todo el movimiento del mismo
 * @author Pedro Luis
 *
 */
public class PantallaJuego implements Pantallas {
	static int nivelActual = 1;

	/** SPRITES **/
	
	/* VALORES */
	int zombiesPorNivel = 5;
	int zombiesDerrotados = 0;
	int vidaPlayer = 5;
	
	
	static int ALTO_PLAYER = 110;
	static int ANCHO_PLAYER = 58;
	static int ALTO_ICON = 110;
	static int ANCHO_ICON= 58;
	
	
	/* PLAYER */
	Sprite player;
	
	/* KUNAI */
	Sprite kunai;

	/* GENERADOR DE ZOMBIES, SANGRE Y ZOMBIES*/
	ArrayList<Sprite> generadores = new ArrayList<Sprite>();
	ArrayList<Sprite> zombies = new ArrayList<Sprite>();
	ArrayList<Sprite> zombiesSangre = new ArrayList<Sprite>();
	
	

	/** PANEL JUEGO **/
	PanelJuego panelJuego;

	/** IMAGENES **/
	BufferedImage fondo;
	BufferedImage imgSangre;
	Image fondoResize;	
	Image fondoUI;
	Image borderUI;

	/** ICONOS **/
	Image iconZombie;
	Image iconCorazon;

	/** FUENTES **/
	Font titulo;

	
	
	/**
	 * Constructor para incializar la pantalla y todas las imagenes
	 * @param panel
	 */
	public PantallaJuego(PanelJuego panel) {
		inicializarPantalla(panel);

		try {
			BufferedImage zombieIMG = ImageIO.read(new File("Imagenes/iconZombie.png"));
			BufferedImage corazonIMG = ImageIO.read(new File("Imagenes/vida.png"));
			BufferedImage uiIMG = ImageIO.read(new File("Imagenes/fondoUI.png"));
			BufferedImage uiBorderIMG = ImageIO.read(new File("Imagenes/borderUI.jpg"));
			BufferedImage playerIMG = ImageIO.read(new File("Imagenes/player.png"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			BufferedImage grassIMG = ImageIO.read(new File("Imagenes/grass.png"));

			
			
			fondo = ImageIO.read(new File("Imagenes/fondo - copia.png"));
			fondoResize = fondo.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH);
			imgSangre = ImageIO.read(new File("Imagenes/sangre.png"));						
			iconZombie = zombieIMG.getScaledInstance(ALTO_ICON, ANCHO_ICON, Image.SCALE_SMOOTH);
			iconCorazon = corazonIMG.getScaledInstance(ALTO_ICON, ANCHO_ICON, Image.SCALE_SMOOTH);
			fondoUI = uiIMG.getScaledInstance(panelJuego.getWidth() - 60, panelJuego.getHeight() - 60,Image.SCALE_SMOOTH);
			borderUI = uiBorderIMG.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
			player = new Sprite(0, panelJuego.getHeight() / 2 - ALTO_PLAYER / 2, ANCHO_PLAYER, ALTO_PLAYER, 0, 0,playerIMG, true);
			titulo = Font.createFont(Font.TRUETYPE_FONT, new File("Font/ZOMBIE.TTF"));			
			ge.registerFont(titulo);
			
			for (int i = 0; i < nivelActual * 2; i++) {
				generadores.add(new Sprite(panelJuego.getWidth() - 100, (i + 1) * 65, 100, 100, 0, 0, grassIMG, true));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 * Establece el panel en el que vamos a pintar
	 */	
	@Override
	public void inicializarPantalla(PanelJuego panel) {
		this.panelJuego = panel;
	}

	/**
	 * Metodo que pinta en el panel de juego cada cierto tiempo
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(fondoResize, 0, 0, null);
		player.pintarEnMundo(g);

		for (int i = 0; i < zombiesSangre.size(); i++) {
			zombiesSangre.get(i).pintarEnMundo(g);
		}

		for (int i = 0; i < zombies.size(); i++) {
			zombies.get(i).pintarEnMundo(g);
		}
		for (int i = 0; i < generadores.size(); i++) {
			generadores.get(i).pintarEnMundo(g);
		}

		if (kunai != null) {
			kunai.pintarEnMundo(g);
		}

		pintarUI(g);

		g.dispose();

	}
	/**
	 * Metodo que pinta por pantalla la Interzaf (GUI) 
	 * @param g
	 */
	private void pintarUI(Graphics g) {

		g.drawImage(borderUI, 0, 500 + ALTO_PLAYER, null);
		g.drawImage(fondoUI, 30, 500 + ALTO_PLAYER + 30, null);
		g.setColor(Color.RED);
		g.setFont(new Font("ZombieA", Font.BOLD, 20));

		g.drawImage(iconZombie, 50, 650, null);
		g.drawString("x" + zombiesDerrotados, 150, 750);
		g.drawString("Nivel " + nivelActual, 65, 760);

		for (int i = 0; i < vidaPlayer; i++) {
			g.drawImage(iconCorazon, 250 * (i + 1), 650, null);
		}

	}
    
	
	/**
	 * Metodo que se ejecuta cada tiempo , y hace todas las funcionalidades de los Sprites
	 */
	@Override
	public void ejecutarFrame() {

		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (zombiesDerrotados < (nivelActual * zombiesPorNivel)) {
			generarZombieAleatorio();
		}

		moverSprite();
		comprobarColisiones();
		comprobrarZombiesDerrotados();
		comprobarVidas();

	}
	/**
	 * Comprueba el numero de vidas, si es 0 , pasamos a la pantalla final de partida perdida
	 */
	private void comprobarVidas() {
		if (vidaPlayer == 0) {
			panelJuego.setPantallaEjecuccion(new PantallaFinalLose(panelJuego));
		}
	}

	/**
	 * Comprueba si el numero de zombies derrotados , son los quedeberian ser para ir al siguiente nivel, y tambn comprueba si estas en la ronda final
	 */
	private void comprobrarZombiesDerrotados() {
		if (zombiesDerrotados == (nivelActual * zombiesPorNivel)) {
			nivelActual++;

			if (nivelActual <= 3) {
				panelJuego.setPantallaEjecuccion(new PantallaIntermedia(panelJuego));
			} else {
				panelJuego.setPantallaEjecuccion(new PantallaFinal(panelJuego));
			}

		}

	}
	/**
	 * Genera zombies aleatorios en generadores aleatoriamente
	 */
	private void generarZombieAleatorio() {
		int aleat = (int) (Math.random() * 55 / nivelActual);

		int aleatGenerador = (int) (Math.random() * generadores.size());

		int aleatGeneroZombie = (int) (Math.random() * 2 + 1);

		if (aleat == 1) {
			try {
				BufferedImage imgZombie = ImageIO.read(new File("Imagenes/zombie" + aleatGeneroZombie + "-.png"));
				zombies.add(new Sprite(generadores.get(aleatGenerador).getPosX(),
						generadores.get(aleatGenerador).getPosY() - 30, 60, 90, -7, 0, imgZombie, true));
				
				new Thread() {
					/**
					 * Realiza un sonido al aparecer un zombie
					 */
					@Override
					public void run() {
						Clip sonido;
						try {
							sonido = AudioSystem.getClip();
							sonido.open(AudioSystem.getAudioInputStream(new File("Sonidos/spawnZombie.wav")));			
							sonido.start();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}.start();

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}
 
	/**
	 * Comprueba colisiones entre el kunai y los zombies
	 */
	public void comprobarColisiones() {
		if (kunai != null && (kunai.getPosX() >= panelJuego.getWidth())) {
			kunai = null;
			System.out.println("sale");
		}

		for (int i = 0; i < zombies.size() && kunai != null; i++) {
			if (kunai.colisiona(zombies.get(i)) && zombies.get(i).isLive()) {
				kunai = null;
				zombies.get(i).setVelX(0);
				zombies.get(i).setVelY(0);
				generarSangre(zombies.get(i));
				zombies.remove(i);
				zombiesDerrotados++;
				
				new Thread() {
					@Override
					public void run() {
						Clip sonido;
						try {
							sonido = AudioSystem.getClip();
							sonido.open(AudioSystem.getAudioInputStream(new File("Sonidos/deadZombie.wav")));			
							sonido.start();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}.start();

			}
		}
		for (int i = 0; i < zombies.size(); i++) {
			if (zombies.get(i).getPosX() + 60 < 0) {
				System.out.println("Zombie ataca");
				zombies.remove(i);
				vidaPlayer--;
			}

		}
	}
	/**
	 * Genera sprites de sangre 
	 * @param zombie
	 */
	private void generarSangre(Sprite zombie) {

		zombiesSangre.add(new Sprite(zombie.getPosX(), zombie.getPosY(), 70, 70, 0, 0, imgSangre, true));
	}

	/**
	 * Actualiza la posicion del kunai y de los zombies
	 */
	private void moverSprite() {
		if (kunai != null) {
			kunai.setPosX(kunai.getPosX() + kunai.getVelX());
		}

		for (int i = 0; i < nivelActual * 2; i++) {
			generadores.get(i).setPosX(panelJuego.getWidth() - 100);
			generadores.get(i).setPosY((i + 1) * 65);
		}

		for (int i = 0; i < zombies.size(); i++) {
			zombies.get(i).aplicarVelocidad();
		}
	}
	
	/**
	 * Genera un kunai que va hacia X positiva
	 */
	@Override
	public void pulsarRaton(MouseEvent event) {
		if (kunai == null) {
			BufferedImage imgKunai;
			try {
				imgKunai = ImageIO.read(new File("Imagenes/kunai.png"));
				kunai = new Sprite(player.getPosX(), player.getPosY() + player.getAlto() / 2, 50, 30, 50, 0, imgKunai,
						true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * Listener para mover al personaje verticalmente
	 */
	@Override
	public void mover(MouseEvent event) {
		player.setPosY(Math.min(event.getY(), 500));

	}
	
	/**
	 * Redimensionar fondo
	 */
	@Override
	public void redimensionar() {
		fondoResize = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

}
