package World;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Display.DisplayHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class WorldSimulation {

	private JFrame frame;
	private JTextField txtInsertPop;
	private JTextField textField_1;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		//prova tutto schermo

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorldSimulation window = new WorldSimulation();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WorldSimulation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(700, 180, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("World simulation");
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("WELCOME TO OUR WORLD SIMULATION");
		lblNewLabel.setLocation(0, 11);
		lblNewLabel.setSize(new Dimension(444, 34));
		lblNewLabel.setMaximumSize(new Dimension(210, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Source Serif Pro Light", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setBackground(Color.WHITE);
		frame.getContentPane().add(lblNewLabel);
		
		txtInsertPop = new JTextField();
		txtInsertPop.setText("0");
		txtInsertPop.setBounds(206, 88, 86, 20);
		frame.getContentPane().add(txtInsertPop);
		txtInsertPop.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Population");
		lblNewLabel_1.setForeground(new Color(102, 204, 102));
		lblNewLabel_1.setFont(new Font("Source Serif Pro Semibold", Font.ITALIC, 15));
		lblNewLabel_1.setBounds(96, 85, 89, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
					String insertpop = txtInsertPop.getText();
					String textField = textField_1.getText();
					if(isNumeric(insertpop)&& isNumeric(textField) )
					{
						frame.setVisible(false);
						DisplayHandler display = new DisplayHandler("Evolutionary System",screenSize.width ,screenSize.height-150 );
						display.setNum_cell(Integer.valueOf(insertpop));
						display.SetNumFood(Integer.valueOf(textField));
						display.start();
					}
					
				
			}
		});
		btnNewButton.setFont(new Font("Source Serif Pro Semibold", Font.ITALIC, 11));
		btnNewButton.setBounds(206, 183, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Food");
		lblNewLabel_2.setForeground(new Color(51, 204, 255));
		lblNewLabel_2.setFont(new Font("Source Serif Pro Semibold", Font.ITALIC, 15));
		lblNewLabel_2.setBounds(112, 138, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setText("0");
		textField_1.setBounds(206, 135, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		//Add listener to Reset Button 
		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnNewButton_1) {
					textField_1.setText("0");
					txtInsertPop.setText("0");
				}
			}
		});
		
		frame.getRootPane().setDefaultButton(btnNewButton);		//aggiunge come bottone di default quello di invio, cosi con enter lo preme in automatico
		
		btnNewButton_1.setFont(new Font("Source Serif Pro Semibold", Font.ITALIC, 11));
		btnNewButton_1.setBounds(96, 183, 89, 23);
		frame.getContentPane().add(btnNewButton_1);

				
	
	}
	public boolean isNumeric(String str) { 
		  try {  
			    Double.parseDouble(str);  
			    return true;
			  } catch(NumberFormatException e){  
			    return false;  
			  }  
			}
}
