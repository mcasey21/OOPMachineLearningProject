import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FileProcessing
{
    // Attributes
    String fileName;
	File fileExample;
	Scanner myScanner;
    ArrayList<String> values = new ArrayList<>();

    // Variables for columns of csv
    // Features
    ArrayList<String> UserAccountAge = new ArrayList<>();
    ArrayList<String> PaymentMethod = new ArrayList<>();
    ArrayList<String> MerchantType = new ArrayList<>();
    ArrayList<String> TransactionRegion = new ArrayList<>();
    // Label
    ArrayList<String> TransactionIsFraudulent = new ArrayList<>();
	
	// Constructor
	public FileProcessing (String fileName)
	{
		this.fileName = fileName;
        fileExample = new File(fileName);	
	}

    // Read file into an ArrayList
    public ArrayList<String> readFile() 
    {
        // Try to scan file
        try 
        {
            // Create scanner object
            myScanner = new Scanner(fileExample);
            while (myScanner.hasNextLine()) 
            {
                values.add(myScanner.nextLine());
            }
            myScanner.close();
        } 
        // Catch error if fileNotFound
        catch (FileNotFoundException e) 
        {
            System.out.println("Runtime error: " + e.getMessage());
        }
        // Return data
        return values;
    }

    // Prints file lines to terminal
    public void printToTerminal()
    {
        // Put file into values variable
        values = readFile();

        // Print out values
        for (String value : values) 
        {
            System.out.println(value);
        }
    }

    // Add new row to csv file
    public void appendRow(String newRow)
    {
        // Try to write to file
        try 
        {
            FileWriter fw = new FileWriter(fileName, true);
            fw.write("\n" + newRow);
            fw.close();
        } 
        catch (IOException ex) 
        {
            System.out.println("Error occured: " + ex.getMessage());
        }
    }

    // Split columns into seperate ArrayLists
    public void splitColumn() 
    {
        // Try to read file
        // FileReader wrapped in BufferReader for more efficient reading
        try (BufferedReader br = new BufferedReader(new FileReader(fileExample))) 
        {
            String line;
            boolean firstLine = true;

            // Read file until line is null
            while ((line = br.readLine()) != null)
            {
                // Split by ',' as its csv file
                String[] columns = line.split(",");

                // Skip first line as it is headers
                if (firstLine) 
                {
                    firstLine = false;
                    continue;
                }

                // Use trim to remove whitespace
                UserAccountAge.add(columns[0].trim());
                PaymentMethod.add(columns[1].trim());
                MerchantType.add(columns[2].trim());
                TransactionRegion.add(columns[3].trim());
                TransactionIsFraudulent.add(columns[4].trim());
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Runtime error: " + e.getMessage());
        }
    }

    // Method to make freq table
    public List<String> frequencyTable(List<String> UserAccountAge, List<String> PaymentMethod, List<String> MerchantType, List<String> TransactionRegion, List<String> TransactionIsFraudulent) 
    {
        // Store freq table in result
        List<String> result = new ArrayList<>();
        // Flag to check rows that have been visited, Set for no dupes
        Set<String> processedRows = new HashSet<>();
        // Size for the loops to avoid out of bounds
        int SIZE = UserAccountAge.size();
        
        for (int i = 0; i < SIZE; i++)
        {
            // Create a key for the current row excluding label
            String rowKey = UserAccountAge.get(i) + "," + PaymentMethod.get(i) + "," + MerchantType.get(i) + "," + TransactionRegion.get(i);
            
            // Skip if row has already been visited
            if (processedRows.contains(rowKey))
            {
                continue;
            }
            
            // Mark this combination as processed
            processedRows.add(rowKey);
            
            // Create a temporary array that represents the features of this row
            List<String> tempArr = new ArrayList<>();
            tempArr.add(UserAccountAge.get(i));
            tempArr.add(PaymentMethod.get(i));
            tempArr.add(MerchantType.get(i));
            tempArr.add(TransactionRegion.get(i));
            
            // Create counts for label
            int yesCount = 0, noCount = 0;
            
            // Check all rows for duplicates of tempArr
            for (int j = 0; j < SIZE; j++)
            {
                if (UserAccountAge.get(j).equals(tempArr.get(0)) && PaymentMethod.get(j).equals(tempArr.get(1)) && MerchantType.get(j).equals(tempArr.get(2)) && TransactionRegion.get(j).equals(tempArr.get(3)))
                {
                    String label = TransactionIsFraudulent.get(j);
                    if (label.equalsIgnoreCase("yes"))
                    {
                        yesCount++;
                    } 
                    else if (label.equalsIgnoreCase("no"))
                    {
                        noCount++;
                    }
                }
            }// End inside loop
            
            // Store the result in the list
            String output = tempArr + "\tYes Count: " + yesCount + "\tNo Count: " + noCount;
            result.add(output);

        }// End outside loop
        return result;
    }// End method

    // Getters to retrieve the ArrayLists
    public ArrayList<String> getUserAccountAge() {
        return UserAccountAge;
    }

    public ArrayList<String> getPaymentMethod() {
        return PaymentMethod;
    }

    public ArrayList<String> getMerchantType() {
        return MerchantType;
    }

    public ArrayList<String> getTransactionRegion() {
        return TransactionRegion;
    }

    public ArrayList<String> getTransactionIsFraudulent() {
        return TransactionIsFraudulent;
    }
}