package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import World.World;

public class Cell extends Entity{

	public boolean max = false;							//Indica se è la cellula con più energia o no
	public static int[] DOMINANT_GENES = new int[3];	
	private ArrayList<Integer> dominant_gene = new ArrayList<Integer>();	//Array usato per i grafici
	public static int[] SUM_GENES = new int[3];
	private static final int NUM_GENES=3;		//numero geni cioè posiz nell array
	private int[] genes;						// Array dove sono contenuti i 3 geni(Velocità,Grandezza;Raggio Visivo)
	private static final int MAX_GENE=9;		//costante che dice i geni che valore massimo possono avere,qui vanno da 0 a 10
	private static final int WIDTH=10;
	private static final int HEIGHT=10;
	public int age = 1;						
	public int high_son_prob = 100;				//proprietà di fare figli
	public int maxage = 10;
	private int width;
	private int height;
	private int count_step;						//conta i passi serve per il cammino random
	private Random generator=new Random();
	private int direction;
	private int state; 							// morta, viva , o riproduce
	private int tiredness = 1500; 				// stanchezza che decrementa
	private int tired = 1500; 					// stanchezza rimane fissa
	private int sightRadius;
	private boolean hasSon = false;
	private Color color = new Color(255,255,255);
	private int col1 = 0;						//colori r,g,b
	private int col2 = 0;
	private int col3 = 0;

	private static boolean sight=false;

	public Cell(double x, double y) {			//Costruttore per creare una cella all'inizio della simulazione, cioè senza un padre che si sia riprodotto
		super(x, y);					//coordinate da disegnare
		this.x = x;
		this.y = y;
		genes=new int[NUM_GENES];
		for(int i=0;i<NUM_GENES;i++)				//Assegna patrimonio genetico random
			genes[i]=generator.nextInt(MAX_GENE) + 1;
		CalculateTiredness();						//Chiama funzione per calcolare quanta energia avrà, in base ai geni
		CalculateSize();							//Calcola la grandezza in base al suo attributo
		CalculateSight();							//Calcola il raggio visivo in base al suo attributo
		SUM_GENES[0]+= genes[0];					//Somma dei geni per le statistiche
		SUM_GENES[1]+= genes[1];
		SUM_GENES[2]+= genes[2];
		CalculateDominants();
	}
	public Cell(double x, double y, int[] genes) {			//Costruttore che crea cella da un padre che si riproduce, quindi ereditando i geni
		super(x,y);
		this.x = x;
		this.y = y;
		this.genes = genes;
		ChangeGenes();									//Funzione che fa cambiare un gene random
		CalculateTiredness();
		CalculateSize();
		CalculateSight();
		SUM_GENES[0]+= genes[0];
		SUM_GENES[1]+= genes[1];
		SUM_GENES[2]+= genes[2];
		CalculateDominants();
		
		
	}

	public void CalculateDominants() {
		/* Istruzioni per calcolare il colore specifico in base al patrimonio genetico della cella */
		if (this.genes[1] >= this.genes[0] && this.genes[1]>= this.genes[2]) { // GRANDEZZA MAGGIORE ROSSI
			DOMINANT_GENES[1]++;
			dominant_gene.add(1);
			col1=224;
			col2=31;
			col3=37;
			col1=col1-10*this.genes[0];
			col1=col1-10*this.genes[2];
		}
		if (this.genes[0] >= this.genes[1] && this.genes[0]>= this.genes[2]) { //VELOCITA MAGGIORE VERDI
			DOMINANT_GENES[0]++;
			dominant_gene.add(0);
			col1=127;
			col2=255;
			col3=0;
			col2=col2-10*this.genes[1];
			col2=col2-10*this.genes[2];
		}
		if (this.genes[2] >= this.genes[1] && this.genes[2]>= this.genes[0]) { //RAGGIO VISIVO MAGGIORE BLU
			DOMINANT_GENES[2]++;
			dominant_gene.add(2);
			col1=0;
			col2=98;
			col3=225;
		    col3=col3-10*this.genes[1];
			col3=col3-10*this.genes[0];
		}
		this.color= new Color(col1,col2,col3);				//assegnazione colore 

	}

	public void ChangeGenes() {				//Istruzioni per la mutazione di un gene random
		int maxi=0;
		int max=0;
		int i=0;
		for(i=0;i<genes.length;i++) {
			if(genes[i]>max) {
				max=genes[i];
				maxi=i;}
		}
		i=0;
		int gene_to_change=generator.nextInt(3);
			if(gene_to_change != maxi) {
				genes[gene_to_change]=Math.min(Math.max(1,genes[gene_to_change]+generator.nextInt(3)-1),5);
			}
	}
	
	
	public void CalculateSight() {
		sightRadius = width + (genes[2] * 16);
		
	}
	public void CalculateTiredness() {
		tiredness = (int) (tiredness - genes[0] * 50 - genes[1] * 30 - genes[2] * 30);
		tired = tiredness;
	}
	public void CalculateSize() {
		width = WIDTH + genes[1] * 2;
		height = HEIGHT + genes[1] * 2;

	}
	
	public boolean getSonProbability(int result) {
		return result < high_son_prob;
	}
	
	public void SeenTick(Entity f) { //Metodo per far andare addosso al cibo la cell che lo trova nel suo raggio visivo

		if(x == f.getX() && y < f.getY())
			y= Math.min(y+(genes[0] * 0.5), f.getY());
		else if(x == f.getX() && y > f.getY())
			y =Math.max(y-(genes[0] * 0.5), f.getY());

		if(x < f.getX()) x= Math.min(x+(genes[0] * 0.5), f.getX());
		else if(x > f.getX()) x=Math.max(x-(genes[0] * 0.5), f.getX());

		if(y < f.getY()) y= Math.min(y+(genes[0] * 0.5), f.getY());
		else if(y > f.getY()) y=Math.max(y-(genes[0] * 0.5), f.getY());
		if(y == f.getY() &&x < f.getX())
            x= Math.min(x+(genes[0] * 0.5), f.getX());
        else if(y == f.getY() &&x < f.getX())
            x =Math.max(x-(genes[0] * 0.5), f.getX());
	}
	
	@Override
	public void tick(int ws,int hs) {

		int w = ws - width - 10;				//sottraggo le misure della palla
		int h = hs - height - 10;

		// Gestite le collisioni con il bordo
		if(x<=10) direction=4;		
		if(y<=10) direction=6;		
		if(x>=w) direction=3;
		if(y>=h) direction=1;
		
		int old_dir = direction;
		count_step++;
		if (count_step%(generator.nextInt(100) + 15) == 0) {				//ogni random passi cambia direzione
			direction=generator.nextInt(8);}
		
		if((old_dir==0 && direction==7) || (old_dir==7 && direction==0))		//Condizioni per cui non va nelle direzioni da cui viene
			direction=generator.nextInt(8);
		else
			if((old_dir==1 && direction==6) || (old_dir==6 && direction==1))
				direction=generator.nextInt(8);
			else
				if((old_dir==2 && direction==5) || (old_dir==5 && direction==2))
					direction=generator.nextInt(8);
				else
					if((old_dir==3 && direction==4) || (old_dir==4 && direction==3))
						direction=generator.nextInt(8);
		
		switch(direction) {			//Muove la cellula in base alla direzione
		case 0:
			x-=genes[0] * 0.5;
			y-=genes[0] * 0.5;							//prova di direzione random prendo un numero a caso da 0 a 7 che indica le 8 posizioni in cui si può muovere
			break;										//su_sx - su - su_dx - sx - dx - giu_sx - giu - giu_dx
		case 1:									
			y-=genes[0] * 0.5;						
			break;
		case 2:
			x+=genes[0] * 0.5;
			y-=genes[0] * 0.5;
			break;
		case 3:
			x-=genes[0] * 0.5;
			break;
		case 4:
			x+=genes[0] * 0.5;
			break;
		case 5:
			x-=genes[0] * 0.5;
			y+=genes[0] * 0.5;
			break;
		case 6:
			y+=genes[0] * 0.5;
			break;
		case 7:
			y+=genes[0] * 0.5;
			x+=genes[0] * 0.5;
			break;
		}
		
	
	}
	public ArrayList<Integer> getDominant() {
		return dominant_gene;
	}

	public int getTiredness() {
		return tiredness;
	}
	
	
	@Override
	public void render(Graphics g) {	

		if(tiredness<=0) {						//quando finisce energia
			if(age>=maxage)						//quando l'età supera una soglia
				g.setColor(Color.DARK_GRAY);	//setto il colore 
		}	
		else
			g.setColor(color);
		g.fillOval((int)x, (int)y, width, height);			//Disegno cellula
		if(sight==true)										
			g.drawOval((int)getSightx(),(int)getSighty(),sightRadius ,sightRadius);		//disegno raggio visivo
	}
	public int[] getGenes() {
		return this.genes;
	}
	public void hadSon() {
		hasSon = true;
	}
	public void resetSon() {
		hasSon = false;
	}
	public void setState(int x) {
		state = x;
	}
	public boolean hasSon() {
		return hasSon;
	}
	public void setColor(Color col) {
	color = col;
	}
	public int getSize() { return genes[1];}
	public double getSightx() {
		return x - (sightRadius/2) + getRadius();
	}
	public double getSighty() {
		return y - (sightRadius/2) + getRadius();
	}
	public double getSightCenterx() {
		return getSightx() + sightRadius / 2;
	}
	public double getSightCentery() {
		return getSighty() + sightRadius / 2;
	}
	public int getSightRadius() {
		return sightRadius / 2;
	}
	public int getState() {
		return state;
	}
	public void ResetState() {
		state=0;
	}
	public void UpdateState() {
		state++; // Viene chiamato quando una Cell mangia
	}
	public int getRadius() {
		return this.width / 2;
	}
	public void setTiredness(int x) {
		tiredness = x;
	}

	public void walk() {
		tiredness--;
	}
	public int getTired() {
		return tired;
	}

	public void setTired(int tired) {
		this.tired = tired;
	}
	public double getCenterx() {
		return x + this.getRadius();
	}
	public double getCentery() {
		return y + this.getRadius();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void GoToBorder(int ws,int hs) {		//Metodo che fa andare al bordo le cellule
		int w = ws - width;					//sottraggo le misure della palla
		int h = hs - height;
		if(x>w-x && w-x<y && w-x<h-y)				//lato destro piu vicino degli altri
			x=Math.min(w, x+(genes[0] * 0.5));
		else
			if(x<w-x && x<y && x<h-y)
				x=Math.max(0, x-(genes[0] * 0.5));
			else
				if(y<w-x && x>y && y<h-y)
					y=Math.max(0, y-(genes[0] * 0.5));
				else
					y=Math.min(h, y+(genes[0] * 0.5));
		if((x == 0 || x == w || y == 0 || y == h) )
			this.tiredness = 0;
			if(max)
				World.findMax();

				
	}
	
	public void setSight(boolean s) {sight=s;}
	
}
