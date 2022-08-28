package lol;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;


public class DataReaderPanel extends JPanel{
	/**
	 * 
	 */
	//private JPanel p;
	private static final long serialVersionUID = 1L;
	private JLabel text;
	private JButton fileInput;
	private int arraySize;
	
	//constructor
	
	public DataReaderPanel() {
		setLayout(null);
		
		fileInput = new JButton("Please select a file");
		fileInput.addActionListener(new ButtonListener());
		//creating JTextArea
		text = new JLabel();
		
		//formating
		setPreferredSize(new Dimension(400, 400));
		
		Dimension buttonSize = fileInput.getPreferredSize();
		fileInput.setBounds(100, 50, 200, buttonSize.height);
		fileInput.setVisible(true);
		
		text.setBounds(150, 150, 100, 100);
		text.setVisible(true);
		
		add(text);
		add(fileInput);
	}
	
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			JButton clicked = (JButton) e.getSource();
			String labelOnButton = clicked.getText();
			if(labelOnButton.equals("Please select a file")) {
				// of File System View, and a path
				JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				 
				// Open the save dialog
				
				j.showSaveDialog(null);
		
				FileReader file;
				File sameFile;
				
				
				try {
					
					file = new FileReader(j.getSelectedFile().getPath());
					sameFile = new File(j.getSelectedFile().getPath());
					
					
					System.out.println(sameFile.toString());
					
				boolean countFile = true;
				
				Scanner countReader = new Scanner(sameFile);
				
				arraySize = 0;
				
				// why does it not continue from here
				
				
				while(countFile) {
					if(countReader.hasNextLine()) {
					arraySize++;
					countReader.nextLine();
					}
					else {
						countFile = false;
					}
				}
				
				String outputText[] = new String [arraySize];
				BufferedReader fileReader = new BufferedReader(file);
				
				// input lines into text field
				for( int i = 0; i < arraySize; i++) {
					outputText[i] = fileReader.readLine();	
				}
					
				//output to JTextField
				String htmlStart = "<html>";
				String htmlEnd = "</html>";
				String input = "";
				String output = "";
				String newline = "<br>";
				
				for(int k = 0; k < arraySize; k++) {
					if(k+1 == arraySize) {
						//last condition
						input = outputText[k] + " " + htmlEnd;
						output += input;
						
					}
					else if(k == 0){
						// first condition
						input =  htmlStart + " " + outputText[k] + " " + newline;
						output += input;
					}
					else {
						// everything else
						input = outputText[k] + " " + newline;
						output += input;
					}
				}
				text.setText(output);
				
				countReader.close();
				fileReader.close();
				
				//I don't know if i actually need another catch here, for now I'll leave it because java yells at me.
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
	        }
		}
		
	}

}
