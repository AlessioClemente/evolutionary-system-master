package Stats;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JButton;

import Display.Display;
import Entities.Cell;
import World.World;


public class StatsHandler implements Runnable {		//Classe che implementa runnable che servirà per far partire il thread che farà i grafici
	private Stats stat_window;						//Oggetto finestra
	private int width, height;
	private String title;
	private Thread thread;
	private BufferStrategy bs;					//Oggetti per il disegno
	private Graphics g;
	
	public StatsHandler(String title, int width, int height) {		//Costruttore dove vengono settate le variabili
		this.width = width;
		this.height = height;
		this.title = title;
	}
	private void init() {
		stat_window = new Stats(title,width,height);	//creazione della finestra per le statistiche
	}
	
	public synchronized void start() { 
		thread = new Thread(this);			//creazione del thread che eseguirà questa classe e avvio
		thread.start();
	}
	public synchronized void stop() {		//metodo per fermare il thread
		try {thread.join();}
		catch (InterruptedException e) {e.printStackTrace();}}

	
	public void DrawGraph(int minx,int maxx,int miny, int maxy) {		//Metodo che disegna i grafici dinamicamente ma non usato nella nostra implementazione
		int deltax=maxx-minx;
		int deltay=maxy-miny;
		int spacex=(int)deltax/18;
		int spacey=(int)deltay/18;
		g.drawLine(spacex*3, maxy-spacey*3, maxx-spacex*3, maxy-spacey*3);
		g.drawString("Generation", maxx-spacex*4, maxy-spacey*4);
		g.drawLine(spacex*3, maxy-spacey*3, spacex*3,spacey*3);	
		g.drawString("Alive", spacex*2, spacex*2);
	}
	
	private void render() {									//Disegno dei grafic, creazione buffer e associati al canvas per disegnare
		bs = stat_window.getCanvas().getBufferStrategy();
		if(bs == null) {
			stat_window.getCanvas().createBufferStrategy(2);
			return;
		}
		g =  bs.getDrawGraphics();						//Creazione dell'oggetto grafico che ci permettere di disegnare sul canvas
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);					
														//Disegno delle linee e struttura ddei grafici, assi e nome sotto essi
		g.drawLine(50, 400, 450, 400);
		g.drawString("Generation", 400, 435);
		g.drawLine(70, 420, 70, 20);	
		g.drawString("Alive", 35, 30);
				
		for(int k=1;k<15;k++) {
			int x=70+(k*25);
			int y=400-(k*25);
			g.drawLine(x, 405, x, 395);					//Trattini sugli assi per indicare step di valori
			g.drawString(Integer.toString(k*5), x, 420);
			g.drawLine(65, y, 75, y);
			g.drawString(Integer.toString(k*25), 40, y);

		}
		
		for(int k=0;k<20;k++) {
			int y=400-(k*20);
			g.drawLine(495, y, 505, y);						
			g.drawString(Integer.toString(k*20), 470, y);

		}
		g.drawLine(500, 420, 500, 20); // verticale
		g.drawLine(480, 400, 880, 400); // orizzontale

		Font font = new Font("Arial",18,18);			//Disegno altro grafico
		g.setFont(font);
		g.setColor(Color.GREEN);
		g.fillRect(520, 415, 15, 15);
		g.drawString("Speed", 540,430);
		int sum = Cell.SUM_GENES[0] / 8;
		g.fillRect(510, (400 - sum), 100, sum);
		g.setColor(Color.RED);
		g.fillRect(600, 415, 15, 15);
		g.drawString("Size", 620,430);
		sum = Cell.SUM_GENES[1] / 8;
		g.fillRect(610, (400 - sum), 100, sum);
		//g.setColor(Color.BLUE);
		g.setColor(Color.YELLOW);
		g.fillRect(660, 415, 15, 15);
		g.drawString("Sight", 680,430);
		sum = Cell.SUM_GENES[2]  / 8;
		g.fillRect(710, (400 - sum), 100, sum);
		
				
		int i=1;
		for(int alive:World.stat_cell) {			//Disegno dei dati nel grafico
			g.setColor(Color.green);
			g.drawOval(70+(i*5)-1, 400-alive, 2, 2);
			i++;
		}
		
		bs.show();
		g.dispose();
		
	}
	
	
	@Override
	public void run() {					
		init();
		int fps = 10;				//Il tutto, disegno ecc dei grafici si eseguirà fps volte al secondo
		double timePerTick = 1000000000 / fps; // 1 secondo fratto fps(in nanosecondi)
		double delta = 0; // tempo passato
		long now;
		long lastTime = System.nanoTime(); // current time

		 while(Stats.running) {
			 now = System.nanoTime();
			 timePerTick=1000000000 / fps;
			 delta += (now - lastTime) / timePerTick; //  tempo passato dall'esecuzione precedente, fratto quanto è il tempo massimo da aspettare
			 lastTime = now;
			 if (delta >= 1) {
				render();
				delta--;}
			 }
		 }
		 
	
}
