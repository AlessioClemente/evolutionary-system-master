package Display;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Stats.StatsHandler;
import World.World;

public class Display {

	private JFrame frame;
	private Canvas canvas; // oggetto su cui disegnare
	private String title;
	private int width,height;
	private boolean stop=false;
	private int fps=60;
	private static boolean sight=false;

	public JTextField foodLostText; 
	
	

	public static void setSight(boolean sight) {
		Display.sight = sight;
	}

	public boolean GetStop() {return stop;}
	
	public JFrame getFrame() {return frame;}
	
	public int getFps() {return fps;}
	
	public boolean isSight() {return sight;}


	public Display(String title, int width, int height,int num_food) {
		this.title = title;
		this.width = width;
		this.height = height;
		createDisplay();
	}
	
	private void createDisplay() {
		this.frame = new JFrame(title);
		frame.setSize(width, height); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		canvas = new Canvas(); 
		canvas.setPreferredSize(new Dimension(width,height));
		
		
		Panel p = new Panel();
        //p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p.setLayout(new FlowLayout());
        p.setSize(100, 100);
        p.setLocation(300, 0);
        JButton btnStop =new JButton("STOP");
        btnStop.setForeground(Color.red);
        JButton btnApply =new JButton("APPLY");
        btnApply.setForeground(Color.red);
        JButton btnsight = new JButton("Sight");
        JLabel foodLabel=new JLabel("Food : ");
        JTextField foodBox=new JTextField(3);
        JLabel foodLoss=new JLabel("Food Loss : ");
        foodLostText=new JTextField(3);
        JButton stats =new JButton("Statistic");
        JButton speedup =new JButton("Speed Up");
        JButton speeddw =new JButton("Speed Down");
        foodBox.setText("0");
        stats.setForeground(Color.red);
        stats.setBackground(Color.pink);
        //p.add(Box.createVerticalStrut(10));
        p.add(foodLabel);
        p.add(foodLostText);
        p.add(foodLoss);
        p.add(foodBox);
        p.add(btnApply);
        p.add(btnsight);
        p.add(speeddw);
        p.add(speedup);
        //p.add(Box.createVerticalStrut(10));
       
        p.add(btnStop);
        p.add(stats);
        p.setBackground(Color.GRAY);
        
        
        btnStop.addActionListener( e-> {stop= stop==true ? false:true;});// lambda che stoppa/fa partire l'esecuzione
        speeddw.addActionListener(e->{fps= fps<=10 ? fps:fps-10;}); // non va sotto i 10 fps
        speedup.addActionListener(e->{fps+=10;});
        btnsight.addActionListener(e-> {sight= sight==true ? false:true;});
        btnApply.addActionListener(e-> {
        		// setta le variabili statiche di World quando si va su Apply
				World.food=Integer.valueOf(foodLostText.getText()); 
				World.food_decrement=Integer.valueOf(foodBox.getText());
				foodLostText.setText(String.valueOf(World.food));
				
			});
        stats.addActionListener(e->{
        	StatsHandler statistic = new StatsHandler("Statistic",1000 ,500);
        	statistic.start(); // inizia il calcolo delle statistiche
});
        
        
        frame.add(p, BorderLayout.NORTH); 
		frame.add(canvas, BorderLayout.CENTER); 
		frame.pack(); 
		
		
		
	}

	public Canvas getCanvas() {return canvas;}
}
