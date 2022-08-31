
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.JFrame;

import javax.swing.JFrame;

public class DataReader2 {
	public ArrayList<Integer> allLargest = new ArrayList<Integer>();
	public ArrayList<Integer> allSmallest = new ArrayList<Integer>();
	public ArrayList<Integer> allSum = new ArrayList<Integer>();
	public int finalSum;
	private int finalLargest = 0;
	private int largestLine = 0;
	private int finalLargestLineCount = 0;
	private int finalSmallestLineCount = 0;
	private int finalSmallest = 0;
	private int prevFinalLargest = 0;
	private int prevFinalSmallest = 0;
	private int smallestLine = 0;

	// Method to read Each line and return as array
	public String readFile(String inputStr) throws IOException {
		int lineNumber = 0;
		int countInt = 0;
		int largest = 0;
		int smallest = 0;
		int linesum = 0;
		int largestOccurence = 0;
		int smallestOccurence = 0;
		String output = "";
		String newline = "<br>";

		try (Scanner line = new Scanner(inputStr)) {
			while (line.hasNextInt()) {
				int readInt = line.nextInt();
				countInt++;
				linesum += readInt;
				int prevLargest = largest;
				largest = findLargest(readInt, largest);
				largestOccurence = countOccurence(largestOccurence, readInt, prevLargest, largest);
				int prevSmallest = smallest;
				smallest = findSmallest(readInt, smallest);
				smallestOccurence = countOccurence(smallestOccurence, readInt, prevSmallest, smallest);

			}
		}
		finalSum += linesum;
//		output += "Processing each line: " + inputStr + newline;
		output += "Count of Ints in line " + ": " + countInt + newline;
		output += "Sum of Ints in line " + ": " + linesum + newline;
		output += "Largest Int in line " + ": " + largest + newline;
		output += "Smallest Int in line " + ": " + smallest + newline;
		output += "Largest Count:" + largestOccurence + newline;
		output += "Smallest Count:" + smallestOccurence + newline;
		output += "Largest Occured more than once?: " + checkOccurence(largestOccurence) + newline;
		output += "Smallest Occured more than once?: " + checkOccurence(smallestOccurence) + newline;
		allLargest.add(largest);
		allSmallest.add(smallest);
		allSum.add(linesum);
		return output;
	}

	
	public String readWholeFile() {
		String newline = "<br>";
		String output = "Processing the whole file: " + newline;
		int prevFinalLargest = 0;
		int prevFinalSmallest = 0;
		int finalLargest = 0;
		int largestLine = 0;
		int finalLargestLineCount = 0;
		int finalSmallestLineCount = 0;
		int finalSmallest = 0;
		int smallestLine = 0;
		prevFinalLargest = allLargest.get(0);
		prevFinalSmallest = allSmallest.get(0);

		for (int i = 0; i < this.allLargest.size(); i++) {
			if (allLargest.get(i) > prevFinalLargest) {
				finalLargest = allLargest.get(i);
				largestLine = i;
			}

			else if (allLargest.get(i) < prevFinalLargest) {
				finalLargest = prevFinalLargest;
			}

			else if (allLargest.get(i) == prevFinalLargest) {
				finalLargest = prevFinalLargest;
				largestLine = i;
			}

			finalLargestLineCount = countOccurence(finalLargestLineCount, allLargest.get(i), prevFinalLargest,
					finalLargest);
			prevFinalLargest = finalLargest;

			if (allSmallest.get(i) < prevFinalSmallest) {
				finalSmallest = allSmallest.get(i);
				smallestLine = i + 1;
			}

			else if (allSmallest.get(i) > prevFinalSmallest) {
				finalSmallest = prevFinalSmallest;
			}

			else if (allSmallest.get(i) == prevFinalSmallest) {
				finalSmallest = prevFinalSmallest;
				smallestLine = i + 1;
			}

			finalSmallestLineCount = countOccurence(finalSmallestLineCount, allSmallest.get(i), prevFinalSmallest,
					finalSmallest);
			prevFinalSmallest = finalSmallest;
		}

		output += "Number of Lines in the file: " + allLargest.size() + newline;
		output += "Total Sum: " + finalSum + newline;
		output += "Largest of all lines: " + finalLargest + newline;
		output += "Smallest of all lines: " + finalSmallest + newline;
		output += "Largest found at line: " + largestLine + newline;
		output += "Smallest found at line: " + smallestLine + newline;
		output += "Final Largest occured in more than one line?: " + checkOccurence(finalLargestLineCount) + newline;
		output += "Final Smallest occured in more than one line?: " + checkOccurence(finalSmallestLineCount) + newline;
		return output;
	}


	//Method to count Occurence of largest or smallest
	public int countOccurence(int occurence, int newNumber, int prev, int newEst) {
		int count = occurence;

		if (newNumber == prev) {
			count++;
		}

		else if ((newNumber != prev && newNumber > prev && newEst > prev)) {
			count = 0;
			count++;
		}

		else if (newNumber != prev && newNumber < prev && newEst < prev) {
			count = 0;
			count++;
		}

		return count;
	}

	//Method to check if there is more than one occurence
	public boolean checkOccurence(int occurence) {
		boolean result = false;
		if (occurence > 1) {
			result = true;
		}

		else if (occurence <= 1) {
			result = false;
		}

		return result;
	}

	//Method to find largest
	public int findLargest(int newInt, int orginalLargest) {
		int largerInt = 0;
		if (newInt > orginalLargest) {
			largerInt = newInt;
		}

		else if (newInt < orginalLargest) {
			largerInt = orginalLargest;
		}

		else if (newInt == orginalLargest) {
			largerInt = orginalLargest;
		}

		return largerInt;
	}

	// Method to find smallest
	public int findSmallest(int newInt, int originalSmallest) {
		int smallerInt = 0;
		if (newInt > originalSmallest) {
			smallerInt = originalSmallest;
		}

		else if (newInt < originalSmallest) {
			smallerInt = newInt;
		}

		else if (newInt == originalSmallest) {
			smallerInt = newInt;
		}
		return smallerInt;
	}

	//Method to check sum status - BREAKEVEN, PROFIT, LOSS
	public String checkSumStatus(int sumInt) {
		String result = null;
		if (sumInt == 0) {
			result = "BREAKEVEN";
		} else if (sumInt > 0) {
			result = "PROFIT";
		} else if (sumInt < 0) {
			result = "LOSS";
		}
		return result;
	}


}
