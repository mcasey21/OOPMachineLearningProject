import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener{
    JPanel leftPanel, row1, row2, row3, row4, row5;
    JButton showTable, addRow, predict;
    JComboBox<String> f1ComboBox, f2ComboBox, f3ComboBox, f4ComboBox;
    JLabel l1, l2, l3, l4;
    JTextArea outputArea;
    FileProcessing fp;
    File freqFile = new File("FrequencyTable.csv");

    GUI(String title) {
        super(title);
        setSize(800, 400);
        
        // Initialize FileProcessing instance
        fp = new FileProcessing("TransactionData.csv");

        // Components made here ...
        // Combo Boxes
        String[] f1Variables = {"New", "Old"};
        String[] f2Variables = {"CreditCard", "BankTransfer"};
        String[] f3Variables = {"Retail", "DigitalService"};
        String[] f4Variables = {"Domestic", "International"};

        f1ComboBox = new JComboBox<>(f1Variables);
        f2ComboBox = new JComboBox<>(f2Variables);
        f3ComboBox = new JComboBox<>(f3Variables);
        f4ComboBox = new JComboBox<>(f4Variables);

        // Labels
        l1 = new JLabel("UserAccountAge");
        l2 = new JLabel("PaymentMethod");
        l3 = new JLabel("MerchantType");
        l4 = new JLabel("TransactionRegion");

        // Buttons
        showTable = new JButton("show table");
        addRow = new JButton("add row");
        predict = new JButton("predict");

        // Action List
        showTable.addActionListener(this);
        addRow.addActionListener(this);
        predict.addActionListener(this);
        
        // Setting sizes of components
        // Buttons
        Dimension buttonSize = new Dimension(100, 40);
        showTable.setPreferredSize(buttonSize);
        addRow.setPreferredSize(buttonSize);
        predict.setPreferredSize(buttonSize);

        // Combo Boxes
        Dimension comboBoxSize = new Dimension(120, 25);
        f1ComboBox.setPreferredSize(comboBoxSize);
        f2ComboBox.setPreferredSize(comboBoxSize);
        f3ComboBox.setPreferredSize(comboBoxSize);
        f4ComboBox.setPreferredSize(comboBoxSize);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(200, 150));

        // Left side includes all components except output area
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(l1);
        row1.add(f1ComboBox);

        row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(l2);
        row2.add(f2ComboBox);

        row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(l3);
        row3.add(f3ComboBox);

        row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row4.add(l4);
        row4.add(f4ComboBox);

        row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row5.add(showTable);
        row5.add(addRow);
        row5.add(predict);

        leftPanel.add(row1);
        leftPanel.add(row2);
        leftPanel.add(row3);
        leftPanel.add(row4);
        leftPanel.add(row5);

        // Add layout to window
        setLayout(new BorderLayout());
        // Add each panel to different side
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        // Show frame
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == showTable)
        {
            // Delete the old file to prevent duplicates
            if (freqFile.exists())
            {
                freqFile.delete();
            }

            // Recreate FileProcessing to avoid duplicates
            fp = new FileProcessing("TransactionData.csv");
            
            // Use this method to get columns for frequencyTable()
            fp.splitColumn();
            
            // Retrieve the processed lists
            List<String> UserAccountAge = fp.getUserAccountAge();
            List<String> PaymentMethod = fp.getPaymentMethod();
            List<String> MerchantType = fp.getMerchantType();
            List<String> TransactionRegion = fp.getTransactionRegion();
            List<String> TransactionIsFraudulent = fp.getTransactionIsFraudulent();

            fp.frequencyTable("FrequencyTable.csv", UserAccountAge, PaymentMethod, 
                            MerchantType, TransactionRegion, TransactionIsFraudulent);

            // Read that CSV and display it in outputArea
            try (BufferedReader br = new BufferedReader(new FileReader("FrequencyTable.csv")))
            {
                String line;
                // Clear area before writing
                outputArea.setText("");

                // Read CSV
                while ((line = br.readLine()) != null)
                {
                    outputArea.append(line + "\n");
                }
            }
            catch (IOException ex)
            {
                outputArea.setText("Error reading frequency table: " + ex.getMessage());
            }
        }

        if (e.getSource() == addRow)
        {
            if (freqFile.exists())
            {
                freqFile.delete();
            }
            // Clear area before writing
            outputArea.setText("");

            // Create a new frame when add row is clicked
            JFrame addRowFrame = new JFrame("Add Row");
            addRowFrame.setResizable(false); 
            addRowFrame.setSize(300, 300);
            // Center the new frame relative to the main window
            addRowFrame.setLocationRelativeTo(this);

            // Create label combo box
            String[] labelVariables = {"Yes", "No"};
            JComboBox<String> labelComboBox = new JComboBox<>(labelVariables);

            // Create a panel for the new frame
            JPanel panel = new JPanel();
            JPanel row1_2, row2_2, row3_2, row4_2, row5_2, row6_2;
            // Components for new panel
            // Labels
            JLabel label = new JLabel("Enter Data for New Row:");
            JLabel l1_2 = new JLabel("UserAccountAge");
            JLabel l2_2 = new JLabel("PaymentMethod");
            JLabel l3_2 = new JLabel("MerchantType");
            JLabel l4_2 = new JLabel("TransactionRegion");
            // Combo boxes
            String[] f1Variables_2 = {"New", "Old"};
            String[] f2Variables_2 = {"CreditCard", "BankTransfer"};
            String[] f3Variables_2 = {"Retail", "DigitalService"};
            String[] f4Variables_2 = {"Domestic", "International"};
            JComboBox f1ComboBox_2 = new JComboBox<>(f1Variables_2);
            JComboBox f2ComboBox_2 = new JComboBox<>(f2Variables_2);
            JComboBox f3ComboBox_2 = new JComboBox<>(f3Variables_2);
            JComboBox f4ComboBox_2 = new JComboBox<>(f4Variables_2);

            row1_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row1_2.add(l1_2);
            row1_2.add(f1ComboBox_2);

            row2_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row2_2.add(l2_2);
            row2_2.add(f2ComboBox_2);

            row3_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row3_2.add(l3_2);
            row3_2.add(f3ComboBox_2);

            row4_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row4_2.add(l4_2);
            row4_2.add(f4ComboBox_2);

            row5_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row5_2.add(new JLabel("IsFraudulent"));
            row5_2.add(labelComboBox);

            row6_2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton saveButton = new JButton("Save");
            row6_2.add(saveButton);

            // Add components to the panel
            panel.add(label);
            panel.add(row1_2);
            panel.add(row2_2);
            panel.add(row3_2);
            panel.add(row4_2);
            panel.add(row5_2);
            panel.add(row6_2);

            // Action for the saveButton
            saveButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Get object from combo boxes and make them strings
                    String F1choice = (String) f1ComboBox_2.getSelectedItem();
                    String F2choice = (String) f2ComboBox_2.getSelectedItem();
                    String F3choice = (String) f3ComboBox_2.getSelectedItem();
                    String F4choice = (String) f4ComboBox_2.getSelectedItem();
                    String labelChoice = (String) labelComboBox.getSelectedItem();

                    String newRow = F1choice+','+F2choice+','+F3choice+','+F4choice+','+labelChoice;

                    fp.appendRow(newRow);

                    // Display the updated frequency table
                    showTable.doClick();
                    
                    // Close the frame after saving data
                    addRowFrame.dispose();
                }
            });

            // Add the panel to the new frame and make it visible
            addRowFrame.add(panel);
            addRowFrame.setVisible(true);
        }

        if (e.getSource() == predict)
        {
            // Get values from combo boxes
            String userAccountAge = (String) f1ComboBox.getSelectedItem();
            String paymentMethod = (String) f2ComboBox.getSelectedItem();
            String merchantType = (String) f3ComboBox.getSelectedItem();
            String transactionRegion = (String) f4ComboBox.getSelectedItem();
            
            // Create a row from combo box values to search in the frequency table
            String inputtedRow = userAccountAge + "," + paymentMethod + "," + 
                                merchantType + "," + transactionRegion;
            
            // Check if frequency table exists
            if (!freqFile.exists())
            {
                outputArea.setText("Please generate the frequency table first.");
            }

            // Try reading the frequency table file
            try (BufferedReader br = new BufferedReader(new FileReader(freqFile)))
            {
                String line;
                boolean foundMatch = false;
                // Skip header
                br.readLine();
                
                // Search for the inputted row in the frequency table
                while ((line = br.readLine()) != null)
                {
                    // Check if the line matches the input
                    if (line.startsWith(inputtedRow)) 
                    {
                        foundMatch = true;

                        // Split the line to get the yes and no counts
                        String[] parts = line.split(",");

                        // Extract counts into variables
                        int yesCount = Integer.parseInt(parts[4]);
                        int noCount = Integer.parseInt(parts[5]);
                        
                        // Determine which count is higher
                        if (yesCount > noCount)
                        {
                            outputArea.setText("Prediction: Transaction IS fraudulent\n\n" +
                                            "Yes count: " + yesCount + ", No count: " + noCount);
                        } 
                        else if (noCount > yesCount)
                        {
                            outputArea.setText("Prediction: Transaction IS NOT fraudulent\n\n" +
                                            "Yes count: " + yesCount + ", No count: " + noCount);
                        }
                        else
                        {
                            outputArea.setText("Prediction: Undetermined\n\n" +
                                            "Yes count: " + yesCount + ", No count: " + noCount);
                        }
                    } // End if
                } // End while
            } // End try
            catch (IOException ex)
            {
                outputArea.setText("Error reading frequency table: " + ex.getMessage());
            } 
        }
    }
}
