package World;
import Entities.*;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class World {
	private static Cell max_cell = null;
	private static LinkedList<Cell> cell_array;
	private LinkedList<Food> food_list;
	private int generation_count = 1;
	private int width, height;
	public static int food;
	public static int food_decrement=0;
	public static ArrayList<Integer> stat_cell=new ArrayList<Integer>();
	private Random generator = new Random();
	private LinkedList<Food> zone1=new LinkedList<Food>(); // 10 linked list per gestire le collisioni in modo efficiente
	private LinkedList<Food> zone2=new LinkedList<Food>(); //diviso in parti il campo
	private LinkedList<Food> zone3=new LinkedList<Food>(); 
	private LinkedList<Food> zone4=new LinkedList<Food>();
	private LinkedList<Food> zone5=new LinkedList<Food>();
	private LinkedList<Food> zone6=new LinkedList<Food>();
	private LinkedList<Food> zone7=new LinkedList<Food>();
	private LinkedList<Food> zone8=new LinkedList<Food>();
	private LinkedList<Food> zone9=new LinkedList<Food>();
	private LinkedList<Food> zone10=new LinkedList<Food>();
	private static boolean sight=false;
	
	public World(int pop, int num_food, int width, int height) {
		food = num_food;
		this.width = width;
		this.height = height;
		stat_cell.add(pop);
		cell_array=new LinkedList<Cell>();
		food_list = new LinkedList<Food>();
		int spawn_d=SpawnDistance(pop); // distanza tra ogni cell
		int x_b=0,y_b=0;
		int max = 0;
		for(int i=0;i<pop;i++) { // si iterano tutte le cell e si posizionano sul bordo a distanza di spawn_d
			Cell c = new Cell(x_b,y_b);
			cell_array.add(c);
			if(c.getTired() > max) { // si calcola il massimo per eliminare controlli sulla next generation
				max = c.getTired();
				max_cell = c;
			}
			if (x_b<width-10 && y_b==0)	x_b=Math.min(width-10, x_b+spawn_d); // lato superiore
			else
				if(x_b==width-10 && y_b<height-10)	y_b=Math.min(height-10, y_b+spawn_d); // lato destro
				else
					if(y_b==height-10 && x_b>0)	x_b=Math.max(0, x_b-spawn_d); // lato inferiore
					else
							y_b=Math.max(0, y_b-spawn_d);				//lato sinistro
		}
		max_cell.max = true;
		spawnFood(); // creazione cibo
	}
	
	public void setSpawn() {
		stat_cell.add(cell_array.size()); 
		generation_count++;
		int x_b = 0, y_b = 0;
		int spawn_d = SpawnDistance(cell_array.size());
		LinkedList <Cell> copy_cell = (LinkedList<Cell>) cell_array.clone(); // itero sulla copia per poter cancellare elementi
		for(Cell c: copy_cell) {
			c.age+= 1;
			c.high_son_prob -= 5;
			if(c.age > c.maxage) // morte di vecchiaia
				cell_array.remove(c);
			c.max = false;
			c.resetSon();
			c.setTiredness(c.getTired()); // resetto stanchezza
			c.setX(x_b);
			c.setY(y_b);
			int max = 0;
			if(c.getState()>0 && c.getTired() > max) { // calcolo max_cell
				max = c.getTired();
				max_cell = c;}
			// posiziono le cell lungo i bordi
			if (x_b<width && y_b==0)	x_b=Math.min(width, x_b+spawn_d);
			else
				if(x_b==width && y_b<height)	y_b=Math.min(height, y_b+spawn_d);
				else
					if(y_b==height && x_b>0)	x_b=Math.max(0, x_b-spawn_d);
					else
							y_b=Math.max(0, y_b-spawn_d);
		
			if(c.getState() == 0) { // se non ha mangiato muore
				cell_array.remove(c);
				//Grafici
				for(int i = 0; i < 3;i++) {
					Cell.SUM_GENES[i]-= c.getGenes()[i];
				}
				for(int i = 0; i < c.getDominant().size();i++) {
					Cell.DOMINANT_GENES[c.getDominant().get(i)]--;
				}

				}
			else
				c.ResetState();

		}
		max_cell.max = true;
	}
	
	public void spawnFood() {
		food=Math.max(food-food_decrement,10); // minimo cibo = 10
		if(food<food_list.size()) {
			LinkedList <Food> app=(LinkedList<Food>) food_list.clone();
			for(Food f:app) { // rimuovo cibo in eccesso
				Assign_zone(f).remove(f);
				food_list.remove(f);
				if(food_list.size()==food)
					break;
			}}
		
			
		while(food_list.size()<food) {				//chiamo assegna zona che ritorna la zona dove sei e nel for aggiunge il food alla zona dov'è
			Food f = new Food(width-20,height-20);
			Assign_zone(f).add(f); // assegno il food alla zona di campo corrispondente
			food_list.add(f); // lo aggiungo anche alla lista dei food
		}
	}
	public static void findMax() {
		int max = 0;
		for(Cell c:cell_array) {
			c.max = false;
			if(c.getTiredness()>0 && c.getTiredness() > max) {
				max = c.getTired();
				max_cell = c;
				}
		}
		max_cell.max = true;
	}
	public int SpawnDistance(int pop) {											//Metodo per lo spawn, ritorna la distanza tra una cell e l'altra
		int perimeter=width*2+height*2;
		if (pop!=0) {
			int distance=Math.max(1, perimeter/pop); // calcola la distanza che ogni cell deve avere l'una dall'altra
			return distance;
			}
		return 0;
	}
	
	public LinkedList<Food> Assign_zone(Entity f) {
		// ritorna zona di campo corrispondente
		double x=f.getX();
		double y=f.getY();
		if(y<height/2) {
			if (x<width/5)
				return zone1;
			else
				if (x<2*width/5 && x>=width/5)
					return zone2;
				else
					if (x<3*width/5 && x>=2*width/5)
						return zone3;
					else
						if (x<4*width/5 && x>=3*width/5)
							return zone4;
						else
							return zone5;
		}
		else {
			if (x<width/5)
				return zone6;
			else
				if (x<2*width/5 && x>=width/5)
					return zone7;
				else
					if (x<3*width/5 && x>=2*width/5)
						return zone8;
					else
						if (x<4*width/5 && x>=3*width/5)
							return zone9;
						else
							return zone10;
		}
	}
	
	public void tick(int w,int h) throws InterruptedException { 
		LinkedList<Cell> delete = new LinkedList<Cell>();
		LinkedList<Cell> sons = new LinkedList<Cell>();
		Food deleted=null;
		if(max_cell.getTiredness() <= 0 || food_list.size() == 0) {		// condizioni di fine generazione
			setSpawn();
			spawnFood();
		}
		for(Cell e: cell_array) {
			boolean seen = false;
			if(e.getState() == 10) {
				e.GoToBorder(w, h); // se sono figli, vanno sul bordo senza cercare cibo
				e.walk();
			}
			else 
			if(e.getState()>=2) {
				if(!(e.hasSon())) {  // se non ha mai avuto figli
					int result = generator.nextInt(100);
					e.hadSon();
					if(e.getSonProbability(result)) {
					Cell son = new Cell(Math.min(e.getX()+e.getRadius(),w),Math.min(e.getY()+e.getRadius(),h),e.getGenes());
					son.setState(10);
					sons.add(son);

				}}
				if(!isBorder(e))
					e.GoToBorder(w,h);
			}	
			else {
				if(e.getTiredness() > 0) {
						e.walk(); // rimuove 1 dalla tiredness
						for(Cell cell: cell_array) { // collisioni con altre cellule
							if(cell == e) continue;
							if(e.getSize() - cell.getSize() > 2 && cell.getTiredness() > 0 && !(isBorder(e)) && e.getTiredness() < 2*e.getTired()/3) {
								if(CheckSightSizeCollision(e,cell)) { // se entra nel raggio visivo
									e.SeenTick(cell); // lo rincorre
									if(CheckSizeCollision(e,cell)) { // se si toccano
										// statistiche per grafici
										for(int i = 0; i < 3;i++) {
											Cell.SUM_GENES[i]-= cell.getGenes()[i];
										}
										for(int i = 0; i < cell.getDominant().size();i++) {
											Cell.DOMINANT_GENES[cell.getDominant().get(i)]--;
										}

										if(cell == max_cell) { // se è stata mangiata la max cell, si ricalcola
											cell.setTiredness(0);
											findMax();
										}
										e.UpdateState();
										delete.add(cell);
									}
								}
							}
						
						}
						
						for(Food f : Assign_zone(e)) { // itero solo nella zona di campo corrispondente
							if(seen==false) // se non ha visto nessun cibo
								if(CheckSightCollision(e,f)) {
									seen = true;
									e.SeenTick(f);}
							if(CheckCollision(e,f)) {
								e.UpdateState(); // aggiungo 1 allo state della cell
								food_list.remove(f); // lo rimuovo dalla lista dei food
								deleted=f; // lo salvo per rimuoverlo anche dalla zona di campo corrispondente
								break;
							}
							}
						
						if(!seen)
							e.tick(w, h);
						Assign_zone(e).remove(deleted);
			}}
		}
		for(Cell temp:delete)
			cell_array.remove(temp);
		for(Cell son:sons)
			cell_array.add(son);
	}
	// collisioni
	public boolean CheckSizeCollision(Cell e, Cell f) {
		// differenza tra i centri
		double deltax =  e.getCenterx() - f.getCenterx();
		double deltay =  e.getCentery() - f.getCentery();
		// applico pitagora, se la condizione è vera si sono toccati
		return (deltax * deltax + deltay * deltay) <= (e.getSightRadius() * e.getSightRadius());
	}
	
	public boolean CheckSightSizeCollision(Cell e, Cell f) {
		double deltax =  e.getSightCenterx() - f.getCenterx();
		double deltay =  e.getSightCentery() - f.getCentery();
		return (deltax * deltax + deltay * deltay) <= (e.getSightRadius() * e.getSightRadius());
	}
	
	public boolean CheckSightCollision(Cell e, Food f) {
		double deltax =  e.getSightCenterx() - Math.max(f.getX(), Math.min(e.getSightCenterx(), f.getX() + (f.getWidth())));
		double deltay =  e.getSightCentery() - Math.max(f.getY(), Math.min(e.getSightCentery(), f.getY() + (f.getHeight())));
		return (deltax * deltax + deltay * deltay) <= (e.getSightRadius() * e.getSightRadius());
	}
	
	public boolean CheckCollision(Cell e, Food f) {
		double deltax =  e.getCenterx() - Math.max(f.getX(), Math.min(e.getCenterx(), f.getX() + (f.getWidth())));
		double deltay =  e.getCentery() - Math.max(f.getY(), Math.min(e.getCentery(), f.getY() + (f.getHeight())));
		return (deltax * deltax + deltay * deltay) <= (e.getRadius() * e.getRadius());
	}

	public void render(Graphics g) {
		for(Cell e: cell_array) {
			e.setSight(sight);
			e.render(g);
		}
		for(Food f: food_list) {
			f.render(g);
		}
	}
	
	public Cell ChooseBest() {
		return null;
	}
	
	public void Reset_pop() {
		
	}

	public LinkedList<Cell> getCell_array() {
		return cell_array;
	}

	public void setCell_array(LinkedList<Cell> cell_array) {
		this.cell_array = cell_array;
	}

	public LinkedList<Food> getFood_list() {
		return food_list;
	}

	public void setFood_list(LinkedList<Food> food_list) {
		this.food_list = food_list;
	}

	public int getGeneration_count() {
		return generation_count;
	}

	public void Set_Food_decr(int dec) {food_decrement=dec;}
	
	public boolean isBorder(Cell e) {
		return (e.getX()==0 || e.getY()==0 || e.getX()==width-e.getWidth() || e.getY()==height-e.getHeight());
	}
	public void setGeneration_count(int generation_count) {
		this.generation_count = generation_count;
	}
	public void SetFood(int f) {food=f;}
	public boolean getSight() {return sight;}
	public void setSight(boolean s) {sight=s;}
	public int getFood() {return food;}
}