import java.util.List;

public class Control
{
    public static void main(String[] args)
    {
        // Creating FileProcessing object
        FileProcessing fp = new FileProcessing("TransactionData.csv");

        // Printing csv to terminal
        //fp.printToTerminal();
        // Spliting columns up into seperate arrlists
        fp.splitColumn();

        // Retrieve the processed lists
        List<String> UserAccountAge = fp.getUserAccountAge();
        List<String> PaymentMethod = fp.getPaymentMethod();
        List<String> MerchantType = fp.getMerchantType();
        List<String> TransactionRegion = fp.getTransactionRegion();
        List<String> TransactionIsFraudulent = fp.getTransactionIsFraudulent();

        List<String> freqTable = fp.frequencyTable(UserAccountAge, PaymentMethod, 
                                                   MerchantType, TransactionRegion, 
                                                   TransactionIsFraudulent);

        // Printing the frequency table
        System.out.println("\t\t FEATURES \t\t\t\t   LABEL");
        System.out.println("\t\t\t\t\t\t\tYES\tNO");
        for (String row : freqTable)
        {
            System.out.println(row);
        }
    }
}