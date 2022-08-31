
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

public class DataReaderPanel3 extends JFrame {
	/**
	 * 
	 */
	// private JPanel p;
	private static final long serialVersionUID = 1L;
	private JLabel text;
	private JButton fileInput;
	private int arraySize;
	private DataReader2 dataReader;
	// constructor

	public DataReaderPanel3() {
		
		dataReader = new DataReader2();
		//Had to do a layout...
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		add(BorderLayout.CENTER, new JScrollPane(panel));
		setSize(600,400);
		//basic operators
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		//set button and text
		fileInput = new JButton("Please select a file");
		text = new JLabel();
		//add text to panel
		panel.add(text);
		add(fileInput, BorderLayout.NORTH);
		fileInput.setVisible(true);
		fileInput.addActionListener(new ButtonListener());
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JButton clicked = (JButton) e.getSource();
			String labelOnButton = clicked.getText();
			if (labelOnButton.equals("Please select a file")) {
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

					// reads file
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
					String output = htmlStart;
					String newline = "<br>";
					String status = "";
					int line = 0;
					// Referencing if file lines are profit or loss
					int[] sumStatus = new int[arraySize];
					for (int n = 0; n < arraySize; n++) {
						line++;
						output += "Processing line " + line + ":" + newline;
						output += dataReader.readFile(outputText[n]) + newline;
						status = dataReader.checkSumStatus(dataReader.allSum.get(n));
						if (status.equals("PROFIT")) {
//							sumStatus[n] = 1;
							output += "Sum status: " + "<font size='5' color=green>" + status + "</font>" + newline;
						}

						else if (status.equals("LOSS")) {
//							sumStatus[n] = -1;
							output += "Sum status: " + "<font size='5' color=red>" + status + "</font>" + newline;
						}

						else if (status.equals("BREAKEVEN")) {
//							sumStatus[n] = 0;
							output += "Sum status: " + "<font size='5' color=black>" + status + "</font>" + newline;

						}

					}
					
					
					output += dataReader.readWholeFile();
					//change color here
					status = dataReader.checkSumStatus(dataReader.finalSum);
					if (status.equals("PROFIT")) {
//						sumStatus[n] = 1;
						output += "Sum status: " + "<font size='5' color=green>" + status + "</font>" + newline;
					}

					else if (status.equals("LOSS")) {
//						sumStatus[n] = -1;
						output += "Sum status: " + "<font size='5' color=red>" + status + "</font>" + newline;
					}

					else if (status.equals("BREAKEVEN")) {
//						sumStatus[n] = 0;
						output += "Sum status: " + "<font size='5' color=black>" + status + "</font>" + newline;

					}
					// Printing
//					for (int k = 0; k < arraySize; k++) {
//						if (k + 1 == arraySize) {
//							// last condition
//							input = outputText[k] + " " + htmlEnd;
//							output += input;
//
//						} else if (k == 0) {
//							// first condition
//							input = htmlStart + " " + outputText[k] + " " + newline;
//							output += input;
//						} else {
//							// everything else
//							input = outputText[k] + " " + newline;
//							output += input;
//						}
//					}

					output += htmlEnd;
					text.setText(output);

					countReader.close();
					fileReader.close();

					// I don't know if i actually need another catch here, for now I'll leave it
					// because java yells at me.
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

	public static void main(String[] args) throws IOException {
	// TODO Auto-generated method stub
	  
	  JFrame frame = new JFrame("Data Reader");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.getContentPane().add(new DataReaderPanel3());

      frame.pack();
      frame.setVisible(true);
}

}
