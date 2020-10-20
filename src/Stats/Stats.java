package Stats;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;


public class Stats {			//Classe per la finestra dei grafici
	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width,height;
	public static boolean running=true;
	
	public Stats(String title, int width, int height) {
		this.title = title;
		this.width = width;						//Inizializza i valori e chiama la funzione per creare la finestra
		this.height = height;
		running=true;
		createDisplay();
	}
	
	private void createDisplay() {
		this.frame = new JFrame(title);		// Crea il frame e setta le varie caratteristiche
		frame.setSize(width, height); 
		frame.setResizable(false);
		frame.setVisible(true);
		canvas = new Canvas(); 
		canvas.setPreferredSize(new Dimension(width,height));
		frame.add(canvas);
		frame.pack(); 
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
				running=false;
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
	}
	
	public Canvas getCanvas() {return canvas;}
	
}
