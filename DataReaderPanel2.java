
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

public class DataReaderPanel2 extends JFrame implements ActionListener {
	private JLabel text;
	private JTextField textField;
	private JButton fileInput;
	private JButton checkSumStatus;
	private int arraySize;
	private String[] outputString;
	private DataReader2 dataReader;
	private JLabel loss;
	private JLabel profit;
	private JLabel breakeven;

	// constructor
	public DataReaderPanel2() {
		setTitle("Data Reader");
		// for JLabel
		dataReader = new DataReader2();
		text = new JLabel();
		text.setPreferredSize(new Dimension(200, 200));

		loss = new JLabel();
		loss.setPreferredSize(new Dimension(300, 300));
		loss.setForeground(Color.RED);
		
		profit = new JLabel();
		profit.setPreferredSize(new Dimension(300, 300));
		profit.setForeground(Color.GREEN);
		
		breakeven = new JLabel();
		breakeven.setPreferredSize(new Dimension(300, 300));
		breakeven.setForeground(Color.BLACK);
		
		// for JTextField
		textField = new JTextField(20);
		textField.setPreferredSize(new Dimension(330, 330));
		textField.setEditable(false);

		// for JButton
		fileInput = new JButton("Choose File");
		fileInput.setPreferredSize(new Dimension(300, 35));
		checkSumStatus = new JButton("Calculate");
		checkSumStatus.setPreferredSize(new Dimension(300, 35));

		setLayout(new BoxLayout());
		add(fileInput);
		add(checkSumStatus);
		add(text);
		add(loss);
		add(profit);
		add(breakeven);
		add(textField);

		fileInput.addActionListener(this);
		checkSumStatus.addActionListener(this);
		
		setSize(800, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//read the file 
		String status = "";
		
		if (e.getSource() == fileInput) {
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

				while (countFile) {
					if (countReader.hasNextLine()) {
						arraySize++;
						countReader.nextLine();
					} else {
						countFile = false;
					}
				}

				String outputText[] = new String[arraySize];
				BufferedReader fileReader = new BufferedReader(file);

				// input lines into text field
				for (int i = 0; i < arraySize; i++) {
					outputText[i] = fileReader.readLine();
				}

				// output to JTextField
				String htmlStart = "<html>";
				String htmlEnd = "</html>";
				String input = "";
				String newline = "<br>";
				String output = htmlStart + newline;
				int lineNumber = 0;
				String profitResult = htmlStart + "PROFIT LINES" + newline;
				String lossResult = htmlStart + "LOSS LINES" +newline;
				String breakevenResult = htmlStart + "BREAKEVEN LINES" + newline;
				String statusOutput = htmlStart + newline;
				
				for (int k = 0; k < arraySize; k++) {
					lineNumber = k+1;
					output += "Processing line " + lineNumber + ":" + newline;
					input = outputText[k] + " " + newline;
					output += input;
					//process each line
					output += dataReader.readFile(outputText[k]);
					//change color based on status
					//using switch case
					status = dataReader.checkSumStatus(dataReader.allSum.get(k));
					System.out.println(status);
					statusOutput = "Sum Status: " + dataReader.checkSumStatus(dataReader.allSum.get(k)) + newline;
					output += htmlEnd;
					output += newline;
					System.out.println(output);
				}
				
				output += dataReader.readWholeFile();
				//change color here
				status = dataReader.checkSumStatus(dataReader.finalSum);
				System.out.println(status);
				statusOutput += "Sum Status: " + dataReader.checkSumStatus(dataReader.finalSum) + newline + htmlEnd;
				output += htmlEnd;
				text.setText(output);
				
				for(int i = 0; i < dataReader.allSum.size(); i++)
				{
					int line = i+1;
					status = dataReader.checkSumStatus(dataReader.allSum.get(i));
					output = htmlStart + "Sum Status of line " + line + ": " + status + htmlEnd;
					if(status.equals("PROFIT"))
						
					{
						profitResult += "Sum Status of line " + line + ": " + status + newline;
//						profit.setText(output);
					}
					
					else if(status.equals("LOSS"))
					{
						lossResult += "Sum Status of line " + line + ": " + status + newline;
//						loss.setText(output);
					}
					
					else if(status.equals("BREAKEVEN"))
					{
						breakevenResult += "Sum Status of line " + line + ": " + status + newline;
//						breakeven.setText(output);
					}
				}
				
				profitResult += htmlEnd;
				lossResult += htmlEnd;
				breakevenResult += htmlEnd;
				profit.setText(profitResult);
				loss.setText(lossResult);
				breakeven.setText(breakevenResult);
				
				countReader.close();
				fileReader.close();
				

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

			
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DataReaderPanel2 dataReader = new DataReaderPanel2();
	}

}
