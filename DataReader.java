
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class DataReader {
	private Scanner fileScan; // For file input
	private String fileName;

	// Constructor with fName parameter
	public DataReader(String fName) {
		this.fileName = fName;
	}

	// Method to read Each line and return as array
	public void readFile() throws IOException {
		String readLine = null;
		int lineNumber = 0;
		int countInt = 0;
		int largest = 0;
		int smallest = 0;
		int prevFinalLargest = 0;
		int prevFinalSmallest = 0;
		int finalLargest = 0;
		int finalSmallest = 0;
		int linesum = 0;
		int finalSum = 0;
		int numberOfLines = 0;
		int largestLine = 0;
		int smallestLine = 0;
		int finalLargestLineCount = 0;
		int finalSmallestLineCount = 0;

		ArrayList<Integer> allLargest = new ArrayList<Integer>();
		ArrayList<Integer> allSmallest = new ArrayList<Integer>();

		// file object
		File theFile = new File(fileName);
		fileScan = new Scanner(theFile);
		fileScan = fileScan.useDelimiter("\n");

		// loop over each line in the file
		while (fileScan.hasNextLine()) {
			numberOfLines++;
			int largestOccurence = 0;
			int smallestOccurence = 0;
			lineNumber++;
			readLine = fileScan.next();
			Scanner line = new Scanner(readLine);
			countInt = 0;
			linesum = 0;
			String[] strArr = readLine.split(" ");
			smallest = largest = Integer.parseInt(strArr[0]);

			// loop over each int in the line selected
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

			
			System.out.println("Processing each line: " + readLine);
			System.out.println("Count of Ints in line " + lineNumber + ": " + countInt);
			System.out.println("Sum of Ints in line " + lineNumber + ": " + linesum);
			System.out.println("Sum status in line " + lineNumber + ":" + checkSumStatus(linesum));
			System.out.println("Largest Int in line " + lineNumber + ": " + largest);
			System.out.println("Smallest Int in line " + lineNumber + ": " + smallest);
			System.out.println("Largest Count:" + largestOccurence);
			System.out.println("Smallest Count:" + smallestOccurence);
			System.out.println("Largest Occured more than once?: " + checkOccurence(largestOccurence));
			System.out.println("Smallest Occured more than once?: " + checkOccurence(smallestOccurence));
			System.out.println(" ");
			finalSum += linesum;
			allLargest.add(largest);
			allSmallest.add(smallest);

		}

		prevFinalLargest = allLargest.get(0);
		prevFinalSmallest = allSmallest.get(0);
		
		for(int i = 0; i < numberOfLines; i++)
		{
			if(allLargest.get(i) > prevFinalLargest)
			{
				finalLargest = allLargest.get(i);
				largestLine = i;
			}
			
			else if(allLargest.get(i) < prevFinalLargest)
			{
				finalLargest = prevFinalLargest;
//				largestLine = 0;
				
			}
			
			else if(allLargest.get(i) == prevFinalLargest)
			{
				finalLargest = prevFinalLargest;
				largestLine = i;
				
			}
			
			finalLargestLineCount = countOccurence(finalLargestLineCount, allLargest.get(i), prevFinalLargest,
			finalLargest);	
			prevFinalLargest = finalLargest;
			
			
			if(allSmallest.get(i) < prevFinalSmallest)
			{
				finalSmallest = allSmallest.get(i);
				smallestLine = i+1;
			}
			
			else if(allSmallest.get(i) > prevFinalSmallest)
			{
				finalSmallest = prevFinalSmallest;
//				smallestLine = smallestLine;
				
			}
			
			else if(allSmallest.get(i) == prevFinalSmallest)
			{
				finalSmallest = prevFinalSmallest;
				smallestLine = i+1;
				
			}
			
			finalSmallestLineCount = countOccurence(finalSmallestLineCount, allSmallest.get(i),
			prevFinalSmallest, finalSmallest);
			prevFinalSmallest = finalSmallest;
		}
	
		System.out.println("Processing the whole file:");
		System.out.println("Number of Lines in the file: " + numberOfLines);
		System.out.println("Total Sum: " + finalSum);
		System.out.println("Total Sum Status: " + checkSumStatus(finalSum));
		System.out.println("Largest at all lines: " + finalLargest);
		System.out.println("Smallest at all times: " + finalSmallest);
		System.out.println("Largest found at line:" + largestLine);
		System.out.println("Smallest found at line: " + smallestLine);
		System.out.println("Final Largest occured in more than one line?: " + checkOccurence(finalLargestLineCount));
		System.out.println("Final Smallest occured in more than one line?: " + checkOccurence(finalSmallestLineCount));

	}


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

	// Method to return smallest
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

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Enter file name");
		Scanner userInput = new Scanner(System.in);
		String fileName = userInput.next();
		DataReader dataReader1 = new DataReader(fileName);
		System.out.println(fileName);
		dataReader1.readFile();
	}

}
