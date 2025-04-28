## OOPMachineLearningProject

### List of classes
- GUI.java
- FileProcessing.java
- Control.java

### What each class does!
### GUI Class <br><br>
The GUI class builds a graphical user interface (GUI) that interacts with transaction data for a <br>
basic fraud prediction system. It allows users to: <br>
- Display a frequency table of transaction data.
- Add a new transaction entry.
- Predict whether a transaction is fraudulent based on selected features.

The GUI has 3 buttons which do the following
- **SHOW TABLE** Reads and displays a frequency table generated from TransactionData.csv.
- **ADD ROW** Opens a new window where users can input new transaction data, save it, and update the frequency table/re train the data.
- **PREDICT** Based on selected attributes (like account age, payment method, etc.), predicts whether a transaction is likely fraudulent or not by analyzing the frequency table.

Important Methods:
- Constructor GUI(String title):
   - Initializes the main window, sets up the layout, creates the components, and registers action listeners.

- actionPerformed(ActionEvent e):
  - Handles button clicks
<br>
<br>

### FileProcessing Class <br><br>
The FileProcessing class reads, manipulates, and analyzes CSV files related to transaction data. <br>
It loads data, separates it into feature columns, appends new rows, prints data, and creates a frequency table <br>
based on the occurrences of combinations of features and their fraud labels.

**Attributes** <br>
  - *_fileName:_* The name (path) of the CSV file. <br>
  - *_fileExample:_* A File object representing the CSV file. <br>
  - *_myScanner:_* A Scanner object used for reading the file. <br>
  - *_values:_* Stores each line from the CSV file as a string. <br>
  - *_Feature & label Columns (stored separately):_* <br>
    - UserAccountAge
    - PaymentMethod
    - MerchantType
    - TransactionRegion
    - TransactionIsFraudulent (label)

**Key Methods** <br>
  - *_Constructor:_* FileProcessing(String fileName): Initializes the file name and creates a File object. <br>
  - *_readFile():_* Reads all lines of the file into the values list. <br>
  - *_printToTerminal():_* Prints every line of the file to the terminal. <br>
  - *_appendRow(String newRow):_* Adds a new row (as a line of text) to the end of the file. <br>
  - *_splitColumn():_* Splits each line of the CSV into separate ArrayLists, one for each column (skips the header). <br>
  - *_frequencyTable(String outputFileName, List... features):_* Generates a new CSV file where each unique combination of features is listed once, along with counts of "yes" and "no" fraud labels. Avoids processing the same row twice by using a HashSet. <br>
  - *_Getters:_* Provide access to each feature ArrayList individually.

### Frequency Table Image
![Frequency Table](freqTable.png)

### Link to video
https://youtu.be/DrBQhHED1hY

### If I had more time
- I would have went on to complete level 4 of the brief.
- I wouldve made a nicer GUI.
- I couldve made more error handling/better code.
