package Pantallas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import Principal.PanelJuego;

public interface Pantallas{
	
	
	public void inicializarPantalla(PanelJuego panel);
	public void pintarPantalla(Graphics g);
	public void ejecutarFrame();
	
	//Listener
	public void pulsarRaton(MouseEvent event);
	public void mover(MouseEvent event);
	public void redimensionar();
	
	

}
