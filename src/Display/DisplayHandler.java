package Display;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


import World.World;


public class DisplayHandler implements Runnable {
	private Display display;
	private int width, height;
	private String title;
	private Thread thread;
	private boolean running = false;
	private World world;
	private int num_cell=0;
	private int num_food=0;
	private static boolean sightCell=false;

	public int getNum_cell() {
		return num_cell;
	}
	public void setNum_cell(int num_cell) {
		this.num_cell = num_cell;
	}
	public void SetNumFood(int n) {
		num_food=n;
	}

	
	
	private BufferStrategy bs;
	private Graphics g;
	
	public DisplayHandler(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	private void init() {
		display = new Display(title,width,height,num_food);

	}
	
	public synchronized void start() { // synchronized means that thread can access 1 object at a time 
		running = true;
		world = new World(num_cell,num_food,width,height); 
		thread = new Thread(this);
		thread.start();; // starts run method
	}
	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	 
	private void tick() throws InterruptedException {
		world.tick(width,height);
	}
	
	
	
	
	private void render() {
 		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(2); // disegna su 2 buffer prima di arrivare al canvas
			return;
		}
		g =  bs.getDrawGraphics();
	
		
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);	// sfondo nero
		
		g.setColor(Color.blue);
		g.fillRect(0, 0, width, 10);	//Disegno cornice
		g.fillRect(0, 0, 10, height);
		g.fillRect(0, height-10, width, 10);
		g.fillRect(width-10, 0, 10, height);
		
		
		g.setColor(Color.white);
		world.render(g); // disegna tutte le entity
		
		bs.show();
		g.dispose(); // libera le risorse usate dall'oggetto graphics, da fare a fine disegno
		
	}
	
	
	@Override
	public void run() {
		init();
		int fps = display.getFps();
		double timePerTick = 1000000000 / fps; // 1 secondo fratto fps(in nanosecondi)
		double delta = 0; // tempo passato
		long now;
		long lastTime = System.nanoTime(); // current time

		 while(running) {
			 now = System.nanoTime();
			 timePerTick=1000000000 / display.getFps();
			 delta += (now - lastTime) / timePerTick; //  tempo passato dall'esecuzione precedente, fratto quanto è il tempo massimo da aspettare
			 lastTime = now;
			 if (delta >= 1) {
				 if (display.GetStop()==false) { // se l'esecuzione è in corso

					world.setSight(display.isSight());
					
					display.foodLostText.setText(String.valueOf(World.food));
					 display.getFrame().setTitle("Evolutionary System  Generation: " + world.getGeneration_count() +
								" Alive: " + world.getCell_array().size() + " Food: " + world.getFood_list().size()+
								" FPS: "+display.getFps());
					 try {
						tick();
						render();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					 }
				 delta--;
			 }
		 }
		stop();
	}	
}
