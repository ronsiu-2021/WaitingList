/**
 * Class WaitingList. 
 * This class utilize hashtable to store 200 customer data with 
 * nested class structure to achieve data abstraction, implement
 * queue ADT to create a waiting-List system for the employee to handle 
 * customer data and the waiting-list for service 
 * 
 * Author: ronsiu-712
 * Date: 07/22/2020
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Queue;

/**
 * This class is used to read and store the customer data into hashTable, and it
 * also contain nested class CustomerNode to hold the info of customer.
 */
public class WaitingList {

    /** Input csv file name */
    private static final String csvFile = "datasets_Customers.csv";

    /** The delimiter for csv file */
    private static final String delimiter = ",";

    /** Constant of costomer's Id length */
    private static final int USER_ID_LENGTH = 5;

    /** Constant of the digits for costomer's Id between 0-9 */
    private static final int USER_ID_DIGITS = 10;

    /** Constant for Knuth Variant when creating userID */
    private static final int K_VARIANT = 3;

    /** The third column of dataset when adding customers from csv file */
    private static final int THIRD_COL = 2;

    /** The fourth column of dataset when adding customers from csv file */
    private static final int FOURTH_COL = 3;

    /** string for notify the user when finish to load customer data */
    private static final String LOAD_COMPLETE = "Loading customer has completed";

    /** string for welcoming the user */
    private static final String WELCOME = "Hello! Nice to meet you!!!";

    /** string for prompting the user to enter command or exit with no */
    private static final String USER_PROMPT = 
        "Please enter any command | type \"exit\" when finish.";

    /** string for user exiting the program */
    private static final String EXIT_WORD = 
        "Thank you for using our service!";

    /** string for prompting user to enter customer's ID */
    private static final String CUSTOMER_ID_QUERY = 
        "Please enter customer's ID:";

    /** string for prompting user to enter customer's name */
    private static final String CUSTOMER_NAME_QUERY = 
        "Please enter customer's name: ";

    /** string for prompting user to enter customer's phone number */
    private static final String CUSTOMER_PHONE_QUERY = 
        "Please enter customer's phone Number (xxx) xxx-xxxx:";

    /** string for prompting user to enter customer's ID when searching */
    private static final String SEARCH_QUERY = 
        "Please enter customer's ID for searching record: ";

    /** string for prompting user to enter customer's ID when deleting */
    private static final String DELETE_QUERY = 
        "Please enter customer's ID to delete record:";

    /** Message to denote customer record cannot be found */
    private static final String CUSTOMER_NOT_FOUND = 
        "\nCannot find customer record!\n";

    /** Message to denote customer record has been found */
    private static final String CUSTOMER_FOUND = 
        "Customer's record has been found!\n";

    /** Message to denote the completion of registration */
    private static final String REG_COMPLETE = 
        "\nRegistration completed!\n";

    /** Message to denote the waiting-list is empty */
    private static final String EMPTY_LIST = 
        "\nNo next customer in the waitlist!\n";

    /** Prompt the user to enter the command again */
    private static final String TRY_AGAIN = 
        "\n Unrecognised command. Please enter the command again.\n";

    /** string for the first row of csv file */
    private static final String FIRST_ROW_CSV = 
        "CustomerID,Name,Phone_Number,Visited,\n";

    /** The string of command when user want to exit the program */
    private static final String EXIT_CMD = "exit";

    /** The string of command for registering new customer */
    private static final String regCustomer = "reg";

    /** The string of command to search customer record inside the dataSet */
    private static final String searchData = "search";

    /** The string of command to remove customer record from dataSet */
    private static final String removeData = "remove";

    /** The string of command to find customer's position from waiting-list */
    private static final String findFromQueue = "find";

    /** The string of command to add customer to waiting-list */
    private static final String enqueue = "add";

    /** The string of command to dequeue customer from waiting-list */
    private static final String dequeue = "call";

    /** The string of command to show the next customer from waiting-list */
    private static final String findNext = "next";

    /** The string of command to show the number of customer in waiting-list */
    private static final String getWaitingCount = "count";

    /** The string of command to show all usable commands to user */
    private static final String helpFlag = "help";

    /** The string of usage to show instruction to user when user input help */
    private static final String usage = 
        "\n\nEnter:\n\n" 
        + "\"reg\" for for registering new customer to dataSet. \n\n"
        + "\"search\" for searching customer record inside the dataSet. \n\n"
        + "\"remove\" for removing customer record from dataSet. \n\n"
        + "\"find\" for finding customer's position from waiting-list. \n\n"
        + "\"add\" for adding customer to waiting-list.  \n\n"
        + "\"call\" for removing the first customer from the waiting-list. \n\n"   
        + "\"next\" for showing the next customer from waiting-list. \n\n"
        + "\"count\" for showing the number of customer in waiting-list. \n\n"
        + "\"exit\" for exiting the program and save changes to dataSet. \n" 
        
        + "\n\n";  
    /** Random object for generating userID */
    private static final Random rand = new Random();

    /** holds all the customers in the Deque */
    private static Queue<CustomerNode> waitingQueue;

    /** hashTable to holds the customers'record */
    private static Hashtable<Integer, CustomerNode> customerRecord;

    /** Scanner object to get the user Input */
    private static Scanner inputScanner;

    /** hashTable to holds the customers'record */
    private static Iterator<CustomerNode> queueItr;

    /** Class object for WaitingList */
    private static WaitingList waitingList;

    /**
     * Nested Class to contain the information of customers. 
     * Info are coming from the csv file. The class variables are set 
     * to contain the each elements in the input file.
     */
    protected class CustomerNode {
        /** ID of customer */
        String customerID;
        /** Name of Customer */
        String name;
        /** Phone number of Customer */
        String phoneNum;
        /** Number of visit */
        int visit;

        /**
         * Construtor of CustomerNode
         * 
         * @param newCusID customer's ID
         * @param name     customer's name
         * @param phoneNum customer's phone number
         * @param visit    the number of time they've visited
         */
        CustomerNode(String newCusID, String name, String phoneNum, int visit) {
            setCustomerID(newCusID);
            setName(name);
            setPhoneNum(phoneNum);
            setVisit(visit);
        }

        /**
         * get the Customer ID
         * 
         * @return customer's ID
         */
        public String getCustomerID() {
            return customerID;
        }

        /**
         * set the customerID to desire value
         * 
         * @param customerID desire value
         * @return void
         */
        public void setCustomerID(String customerID) {
            this.customerID = customerID;
        }

        /**
         * get the customer's name
         * 
         * @return customer's name
         */
        public String getName() {
            return name;
        }

        /**
         * set name to desire customer name
         * 
         * @param name desire customer name
         * @return void
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * get the customer's phone number
         * 
         * @return customer's phone number
         */
        public String getPhoneNum() {
            return phoneNum;
        }

        /**
         * set phoneNum to desire customer's phone number
         * 
         * @param phoneNum customer's phone number
         */
        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        /**
         * get the customer's number of time they've visited
         * 
         * @return number of time they've visited
         */
        public int getVisit() {
            return visit;
        }

        /**
         * set visit to be number of time customers've visited
         * 
         * @param visit number of time they've visited
         */
        public void setVisit(int visit) {
            this.visit = visit;
        }

    }

    /**
     * Create userID for new customer
     * 
     * @return newly created userID
     */
    private static String generateUserID() {
        String userID = "";
        for (int i = 0; i < USER_ID_LENGTH; i++) {
            userID += rand.nextInt(USER_ID_DIGITS);
        }
        int index = 0;
        for (int i = 0; i < USER_ID_LENGTH; i++) {
            int character = userID.charAt(i);
            index = character * (character + K_VARIANT) % USER_ID_LENGTH;
        }
        userID += Math.abs(index) % USER_ID_LENGTH;
        return userID.substring(0, USER_ID_LENGTH);
    }

    /**
     * Populate the customer data from csvFile to hashTable for the main program
     * 
     * @throws IOException when file cannot be opened to generate hashtable
     * @return void
     */
    private static void populateCustomerRecord() throws IOException {

        BufferedReader buffReader = null;
        try {
            File inFile = new File(csvFile); 
            String filePath = inFile.getAbsolutePath();
            //System.out.println(filePath);
            FileInputStream inputFile = new FileInputStream(filePath);
            buffReader = new BufferedReader(new InputStreamReader(inputFile));
        } catch (IOException except) {
            except.printStackTrace();
        }
        String line = buffReader.readLine(); // skip first line
        line = "";
        CustomerNode customer;
        while ((line = buffReader.readLine()) != null) {
            String[] data = line.trim().split(delimiter); // split string
            // create node object
            customer = waitingList.new CustomerNode(
                data[0], data[1], data[THIRD_COL],
                Integer.parseInt(data[FOURTH_COL]));
            // add the CustomerNode to hashTable
            customerRecord.put(Integer.parseInt(customer.customerID), customer);
        }
        buffReader.close();
        System.out.println(LOAD_COMPLETE);
    }

    /**
     * Handling the events according to user commands
     * 
     * @param userInput input command to perform actions
     * @return void
     */
    private static void userInputEvent(String userInput) {
        switch (userInput) {
            case regCustomer:
                inputScanner.nextLine(); // consume the leftover new line
                System.out.println(CUSTOMER_NAME_QUERY);
                String cusName = inputScanner.nextLine().trim();
                System.out.println(CUSTOMER_PHONE_QUERY);
                String cusPhone = inputScanner.nextLine().trim();

                String newCusID = generateUserID();
                System.out.println("\nYour user ID is is: " + newCusID);
                CustomerNode newCustomer = waitingList.new CustomerNode(
                    newCusID, cusName, cusPhone, 1);
                customerRecord.put(Integer.parseInt(
                    newCustomer.customerID), newCustomer);
                System.out.println(REG_COMPLETE);
                System.out.println();
                break;

            case searchData:
                System.out.println(SEARCH_QUERY);
                String searchID = inputScanner.next();
                System.out.println();
                CustomerNode searchResult = customerRecord.get(
                    Integer.parseInt(searchID));
                if (searchResult == null) {
                    System.out.println(CUSTOMER_NOT_FOUND);
                } else {
                    System.out.println(CUSTOMER_FOUND);
                    System.out.println(
                        "Customer name is: " + searchResult.name + "\n");
                }
                break;

            case removeData:
                System.out.println(DELETE_QUERY);
                String deleteID = inputScanner.next();
                CustomerNode result = customerRecord.remove(
                    Integer.parseInt(deleteID));
                System.out.println(
                    "\nID " + result.customerID + " has been deleted.\n");
                break;

            case findFromQueue:
                System.out.println(SEARCH_QUERY);
                String findID = inputScanner.next();
                System.out.println();
                int waitNumber = 0;
                boolean isFound = false;
                queueItr = waitingQueue.iterator();

                while (queueItr.hasNext()) {
                    CustomerNode cusInList = queueItr.next();
                    if (cusInList.customerID.equals(findID)) {
                        System.out.println("You have " + waitNumber 
                            + " costomers before you \n");
                        isFound = true;
                        break;
                    } else {
                        waitNumber++;
                    }
                }
                if (!isFound) {
                    System.out.println("Costomer with ID " + findID 
                        + " is not in the waiting-list.\n");
                }
                break;

            case enqueue:
                System.out.println(CUSTOMER_ID_QUERY);
                userInput = inputScanner.next();
                CustomerNode cusRecord = customerRecord.get(
                    Integer.parseInt(userInput));
                if (cusRecord == null) {
                    System.out.println(CUSTOMER_NOT_FOUND);
                    break;
                }
                waitingQueue.add(cusRecord);
                cusRecord.visit++;
                System.out.println("\nID " + cusRecord.customerID 
                    + " been added to the waitlist.\n");
                break;

            case dequeue:
                CustomerNode customerNode = waitingQueue.poll();
                if (customerNode == null) {
                    System.out.println("\nNo customer in the waitlist!\n");
                } else {
                    System.out.println(
                            "\nNow serving customer " + customerNode.name 
                            + " (" + customerNode.customerID + ")\n");
                }
                break;

            case findNext:
                System.out.println();
                CustomerNode nextCustomer = waitingQueue.peek();
                if (nextCustomer == null) {
                    System.out.println(EMPTY_LIST);
                } else {
                    System.out.println("\n" + nextCustomer.name 
                        + " (" + nextCustomer.customerID + ")\n");
                }
                break;

            case getWaitingCount:
                System.out.println("\nNumber of customers in the waitlist is " 
                    + waitingQueue.size() + "\n");
                break;

            case helpFlag:
                System.out.println(usage);
                break;

            case EXIT_CMD:
                break;

            default:
                System.out.println(TRY_AGAIN);
        }
    }

    public static void main(String[] args) throws IOException {
        // initialize class object
        waitingList = new WaitingList();
        customerRecord = new Hashtable<>();
        waitingQueue = new LinkedList<>();
        inputScanner = new Scanner(System.in);
        // Load customer data
        populateCustomerRecord();
        //
        System.out.println(WELCOME);
        String userInput;
        do {
            System.out.println(USER_PROMPT);
            userInputEvent(userInput = inputScanner.next());
        } while (!userInput.equalsIgnoreCase(EXIT_CMD));
        // update the dataset.
        FileWriter csvWriter = new FileWriter(csvFile);
        Set<Integer> hashkeys = customerRecord.keySet();
        csvWriter.append(FIRST_ROW_CSV);
        for (Integer keys : hashkeys) {
            CustomerNode cusInRecord = customerRecord.get(keys);
            csvWriter.append(
                cusInRecord.customerID + "," + cusInRecord.name + "," 
                + cusInRecord.phoneNum + "," + cusInRecord.visit + ",\n");
            csvWriter.flush();
        }
        System.out.println(EXIT_WORD);
        csvWriter.close();
        inputScanner.close();
    }
}
