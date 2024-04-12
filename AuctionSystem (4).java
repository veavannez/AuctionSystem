import java.util.Scanner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

public class AuctionSystem {
    private static final Scanner input = new Scanner(System.in);

    public static int findSellerIndex(String sellerId, String[] selid, int ssize) {
        for (int i = 0; i < ssize; i++) {
            if (sellerId.equals(selid[i])) {
                return i; // Found the seller, return their index
            }
        }
        return -1; // Seller ID not found
    }

    // Initialize buyer information arrays
    static int Bmax = 10; // Set the maximum number of buyers
    static String[] Bname = new String[Bmax];
    static String[] Bemad = new String[Bmax];
    static String[] Bhmad = new String[Bmax];
    static String[] Bsmad = new String[Bmax];
    static String[] Buyid = new String[Bmax];
    static String[] BPass = new String[Bmax];
    static int Bsize = 0; // Keep track of the number of buyers

    // Initialize seller information arrays
    static int Smax = 10; // Set the maximum number of sellers
    static String[] Sname = new String[Smax];
    static String[] Semad = new String[Smax];
    static String[] Shome = new String[Smax];
    static String[] Sbank = new String[Smax];
    static String[] Sroute = new String[Smax];
    static String[] Selid = new String[Smax];
    static String[] SPass = new String[Smax];
    static int Ssize = 0; // Keep track of the number of sellers

    // Initialize item information arrays
    static int Imax = 100; // Set the maximum number of items
    static String[] itemTitle = new String[Imax];
    static String[] itemDesc = new String[Imax];
    static double[] startingBid = new double[Imax];
    static double[] currentBid = new double[Imax];
    static double[] bidIncrement = new double[Imax];
    static String[][] startDates = new String[Imax][Bmax];
    static Date[] endDate = new Date[Imax];
    static String[][] endDates = new String[Imax][Bmax];
    static int itemCount = 0;
    static int[][] sellerItemIndices = new int[Smax][Imax]; // 2D array to store item indices for each seller
    static int[] itemCountPerSeller = new int[Smax]; // Keep track of the number of items per seller

    static int[][] buyerBidIndices = new int[Bmax][Imax];
    static int[] bidCountPerBuyer = new int[Bmax];
    static double[][] bidPrices = new double[Imax][Bmax];
    static String[][] bidTimes = new String[Imax][Bmax];
    static String[] itemFeedback = new String[Imax];

    private static int foundIndex = -1;
    static int[] sellerRatings = new int[Imax]; // Declare sellerRatings array
    static String[] sellerComments = new String[Imax]; // Declare sellerComments array
    static int[] buyerRatings = new int[Imax]; // Declare sellerRatings array
    static String[] buyerComments = new String[Imax]; // Declare sellerComments array

    public static void main(String[] args) {
        AuctionSystem AuctionSystem = new AuctionSystem();
        int choice;

        do {
            menu();
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    buyer();
                    break;
                case 2:
                    seller();
                    break;
                case 3:
                    AuctionWin();
                    break;
                case 4:
                    displayFeedback();
                    break;
            }
        } while (choice != 5);

        input.close();
    }

    public static void menu() {
        System.out.println("\n---------------------------------------------");
        System.out.println("                 MAIN MENU");
        System.out.println("---------------------------------------------");
        System.out.println("                [1] Buy");
        System.out.println("                [2] Sell");
        System.out.println("                [3] Auction Winners");
        System.out.println("                [4] Feedback");
        System.out.println("                [5] Exit");
        System.out.println("_____________________________________________");
        System.out.print("Enter your choice: ");
    }

    public static void buyer() {
        // Existing or new buyer
        System.out.println("\n\n---------------------------------------------");
        System.out.println("                    BUYER");
        System.out.println("---------------------------------------------");
        System.out.print("Do you have an existing Account (Y/N): ");
        String Bans = input.next().toLowerCase();
        input.nextLine();

        switch (Bans) {
            case "y": {
                // Existing buyer login
                System.out.println("\n\n---------------------------------------------");
                System.out.println("                BUYER LOGIN");
                System.out.println("---------------------------------------------");
                System.out.print("Enter Buyer ID: ");
                String ID = input.next();

                boolean found = false;
                int foundIndex = -1;
                for (int i = 0; i < Bsize; i++) {
                    if (ID.equals(Buyid[i])) {
                        found = true;
                        foundIndex = i;
                        break;
                    }
                }

                if (found) {
                    System.out.print("Enter Password: ");
                    String password = input.next();

                    if (password.equals(BPass[foundIndex])) {
                        System.out.println("\n---------------------------------------------");
                        System.out.println("       You have successfully logged in.");
                        System.out.println("---------------------------------------------");

                        String Bchoice;
                        do {
                            // Buyer menu options
                            System.out.println("\n---------------------------------------------");
                            System.out.println("                BUYER MENU");
                            System.out.println("---------------------------------------------");
                            System.out.println("                [1] Browse Items");
                            System.out.println("                [2] View My Bids");
                            System.out.println("                [3] Provide Feedback");
                            System.out.println("                [4] Back to Main Menu");
                            System.out.println("_____________________________________________");
                            System.out.print("Enter your choice: ");
                            Bchoice = input.next();

                            switch (Bchoice) {
                                case "1": {

                                    // Browse items logic
                                    System.out.println("\n\n---------------------------------------------");
                                    System.out.println("               BROWSE ITEMS");
                                    System.out.println("---------------------------------------------");
                                    // Get the current date and time
                                    Calendar checkDate = Calendar.getInstance();
                                    boolean itemsFound = false; // To track if any items are found

                                    // Iterate through all sellers and their items
                                    for (int sellerIndex = 0; sellerIndex < Ssize; sellerIndex++) {
                                        int itemsForSeller = itemCountPerSeller[sellerIndex];
                                        if (itemsForSeller > 0) {
                                            for (int i = 0; i < itemsForSeller; i++) {
                                                int itemIndex = sellerItemIndices[sellerIndex][i];
                                                System.out.println("---------------------------------------------");
                                                System.out.println("\t\tItem: " + itemTitle[itemIndex]);
                                                System.out.println("---------------------------------------------");
                                                System.out.println("Seller: " + Sname[sellerIndex]);
                                                System.out.println("Description: " + itemDesc[itemIndex]);
                                                System.out
                                                        .println("\nStart Date: " + startDates[itemIndex][sellerIndex]);
                                                SimpleDateFormat dateFormat = new SimpleDateFormat(
                                                        "MM/dd/yyyy HH:mm:ss");
                                                String formattedEndDate = dateFormat.format(endDate[itemIndex]);
                                                System.out.println("End Date: " + formattedEndDate);

                                                System.out.println("---------------------------------------------");
                                                System.out.println("Starting Bid Price: $" + startingBid[itemIndex]);
                                                System.out.println("Bid Increment: $" + bidIncrement[itemIndex]);
                                                System.out.println("Current Bid Price: $" + currentBid[itemIndex]);
                                                System.out.println("_____________________________________________");

                                                // Allow buyers to bid only if the auction has not ended
                                                if (Bsize > 0) {
                                                    Calendar currentDate = Calendar.getInstance();
                                                    Calendar auctionEndDate = Calendar.getInstance();
                                                    auctionEndDate.setTime(endDate[itemIndex]);

                                                    if (currentDate.before(auctionEndDate)) {
                                                        System.out.print(
                                                                "Do you want to place a bid on this item? (Y/N): ");
                                                        String bidChoice = input.next().trim().toLowerCase();

                                                        if (bidChoice.equals("y")) {
                                                            System.out.print("Enter your bid amount: $");
                                                            double newBid = input.nextDouble();

                                                            double minBidAmount = currentBid[itemIndex] +
                                                                    bidIncrement[itemIndex];

                                                            if (newBid >= minBidAmount) {
                                                                currentBid[itemIndex] = newBid;

                                                                // Store bid information
                                                                bidPrices[itemIndex][foundIndex] = newBid;
                                                                SimpleDateFormat sdf = new SimpleDateFormat(
                                                                        "MM/dd/yyyy HH:mm:ss");
                                                                String currentTime = sdf.format(new Date());
                                                                bidTimes[itemIndex][foundIndex] = currentTime;

                                                                // Store the item index in the buyer's bid list
                                                                buyerBidIndices[foundIndex][bidCountPerBuyer[foundIndex]] = itemIndex;
                                                                bidCountPerBuyer[foundIndex]++;

                                                                System.out.println(
                                                                        "---------------------------------------------");
                                                                System.out.println("Bid placed successfully!");
                                                            } else {
                                                                System.out.println(
                                                                        "---------------------------------------------");
                                                                System.out.println(
                                                                        "Your bid must be at least $" + minBidAmount +
                                                                                " higher than the current bid.");
                                                            }
                                                        }
                                                    } else {
                                                        System.out.println(
                                                                "---------------------------------------------");
                                                        System.out.println(
                                                                "The auction for this item has already ended.");

                                                    }

                                                    itemsFound = true;
                                                }

                                                if (!itemsFound) {
                                                    System.out.println("---------------------------------------------");
                                                    System.out.println("No items available at the moment.");
                                                }
                                            }
                                        }
                                    }
                                    while (!Bchoice.equals("4"))
                                        break;
                                }
                                    break;
                                case "2": {
                                    System.out.println("\n\n---------------------------------------------");
                                    System.out.println("                VIEW MY BIDS");
                                    System.out.println("---------------------------------------------");

                                    // Check if the buyer has placed any bids
                                    if (bidCountPerBuyer[foundIndex] == 0) {
                                        System.out.println("You do not have any bids.");
                                    } else {
                                        // Iterate through the items the buyer has bid on
                                        for (int i = 0; i < bidCountPerBuyer[foundIndex]; i++) {
                                            int itemIndex = buyerBidIndices[foundIndex][i];

                                            System.out.println("Item #" + (i + 1) + ": " + itemTitle[itemIndex]);
                                            System.out.println("---------------------------------------------");
                                            System.out.println("Description: " + itemDesc[itemIndex]);
                                            System.out.println("Start Date: " +
                                                    startDates[itemIndex][sellerItemIndices[foundIndex][i]]);
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                            String formattedEndDate = dateFormat.format(endDate[itemIndex]);
                                            System.out.println("End Date: " + formattedEndDate);
                                            System.out.println("---------------------------------------------");
                                            System.out.println("Starting Bid Price: $" + startingBid[itemIndex]);
                                            System.out.println("Bid Increment: $" + bidIncrement[itemIndex]);
                                            System.out.println("Current Bid Price: $" + currentBid[itemIndex]);
                                            System.out.println("---------------------------------------------");
                                            // Display bid price and time for this item
                                            double bidPrice = bidPrices[itemIndex][foundIndex];
                                            String bidTime = bidTimes[itemIndex][foundIndex];
                                            System.out.println("Your Bid Price: $" + bidPrice);
                                            System.out.println("Bid Time: " + bidTime);
                                            System.out.println("_____________________________________________");

                                            // Check if the auction has ended and the buyer is the highest bidder
                                            if (endDate[itemIndex].before(new Date())
                                                    && bidPrice == currentBid[itemIndex]) {
                                                // Prompt the buyer to pay
                                                System.out.println("Congratulations! You are the highest bidder.");
                                                System.out.println("---------------------------------------------");
                                                System.out.println("Item: " + itemTitle[itemIndex]);
                                                System.out.println("Price: $" + currentBid[itemIndex]);
                                                System.out.println("Seller: " + sellerName[itemIndex]);
                                                System.out.println("Seller ID: " + sellerId[itemIndex]);
                                                System.out.println(
                                                        "Seller Routing Number: " + sellerRoutingNumber[itemIndex]);
                                                System.out.println("Address: " + sellerAddress[itemIndex]);
                                                System.out.println("Email: " + sellerEmail[itemIndex]);
                                                System.out.println("---------------------------------------------");
                                                System.out.println("Please enter your payment information:");
                                                System.out.println("Card number:");
                                                String cardNumber = scanner.nextLine();
                                                System.out.println("Expiration date (MM/YY):");
                                                String expirationDate = scanner.nextLine();
                                                System.out.println("CVV:");
                                                String cvv = scanner.nextLine();
                                                
                                                System.out.println("---------------------------------------------");
                                                System.out.println("Do you want to confirm the purchase? (Y/N)");
                                                boolean paymentSuccessful = Math.random() < 0.5;

                                                String confirmPurchase = scanner.nextLine();

                                                if (confirmPurchase.equalsIgnoreCase("Y")) {
                                                    System.out.println(
                                                                "Payment confirmation has been sent to Seller.");

                                                } else {
                                                    System.out.println("Payment confirmation has been cancelled.");

                                                    if (paymentSuccessful) {
                                                        // Display the receipt to the buyer
                                                        System.out.println("Thank you for your purchase!");
                                                        System.out.println(
                                                                "---------------------------------------------");
                                                        System.out.println("Item: " + itemTitle[itemIndex]);
                                                        System.out.println("Price: $" + currentBid[itemIndex]);
                                                        System.out.println("Seller: " + sellerName[itemIndex]);
                                                        System.out.println("Address: " + sellerAddress[itemIndex]);
                                                        System.out.println("Email: " + sellerEmail[itemIndex]);
                                                

                                                    } else {
                                                        System.out.println("Waiting for Seller to accept order.");
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }

                                case "3": {
                                    feedbackbuyer(foundIndex);
                                    break;
                                }

                                case "4": {
                                    // Return to the main menu
                                    System.out.println("\nReturning to the main menu...");
                                    break;
                                }

                                default: {
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                                }
                            }

                        } while (!Bchoice.equals("4")); // Loop until the buyer chooses to go back to the
                        // main menu
                    } else {
                        System.out.println("---------------------------------------------");
                        System.out.println("            Password is incorrect.");
                        System.out.println("---------------------------------------------");
                    }
                } else {
                    System.out.println("---------------------------------------------");
                    System.out.println("               Buyer ID not found.");
                    System.out.println("---------------------------------------------");
                }
                break;
            }

            case "n": {
                // Buyer sign-up
                System.out.println("\n\n---------------------------------------------");
                System.out.println("                BUYER SIGN-UP");
                System.out.println("---------------------------------------------");
                System.out.print("Enter Name: ");
                Bname[Bsize] = input.next();
                input.nextLine(); // Consume the newline character
                System.out.print("Enter Email address: ");
                Bemad[Bsize] = input.next();
                input.nextLine();
                System.out.print("Enter Home address: ");
                Bhmad[Bsize] = input.next();
                input.nextLine();
                System.out.print("Enter Shipping address: ");
                Bsmad[Bsize] = input.next();
                input.nextLine();

                boolean validBuyerId = false;
                while (!validBuyerId) {
                    System.out.println("---------------------------------------------");
                    System.out.print("Enter Buyer ID: ");
                    String buyerId = input.next();
                    input.nextLine();
                    boolean buyerIdExists = false;
                    for (int i = 0; i < Bsize; i++) {
                        if (Buyid[i].equals(buyerId)) {
                            buyerIdExists = true;
                            break;
                        }
                    }
                    if (buyerIdExists) {
                        System.out.println("Buyer ID already exists. Please enter a different one.");
                    } else {
                        Buyid[Bsize] = buyerId;
                        validBuyerId = true;
                    }
                }

                System.out.print("Enter Password: ");
                BPass[Bsize] = input.next();
                input.nextLine();

                System.out.println("_____________________________________________");
                System.out.println("You have successfully made a buyer account.");
                System.out.println("_____________________________________________");
                Bsize++;
                break;
            }
        }
    }

    public static void seller() {
        // Existing or new seller
        System.out.println("\n\n---------------------------------------------");
        System.out.println("                    SELLER");
        System.out.println("---------------------------------------------");
        System.out.print("Do you have an existing Account (Y/N): ");
        String Sans = input.next().toLowerCase();
        input.nextLine();

        switch (Sans) {
            case "y": {
                // Existing seller login
                System.out.println("\n\n---------------------------------------------");
                System.out.println("                SELLER LOGIN");
                System.out.println("---------------------------------------------");
                System.out.print("Enter Seller ID: ");
                String ID = input.next();

                boolean found = false;
                int foundIndex = -1;
                for (int i = 0; i < Ssize; i++) {
                    if (ID.equals(Selid[i])) {
                        found = true;
                        foundIndex = i;
                        break;
                    }
                }

                if (found) {
                    System.out.print("Enter Password: ");
                    String password = input.next();

                    if (password.equals(SPass[foundIndex])) {
                        System.out.println("\n---------------------------------------------");
                        System.out.println("       You have successfully logged in.");
                        System.out.println("---------------------------------------------");

                        String Schoice;
                        do {
                            // Seller menu options
                            System.out.println("\n---------------------------------------------");
                            System.out.println("                SELLER MENU");
                            System.out.println("---------------------------------------------");
                            System.out.println("                [1] Add Item");
                            System.out.println("                [2] View Items");
                            System.out.println("                [3] Provide Feedback");
                            System.out.println("                [4] Back to Main Menu");
                            System.out.println("_____________________________________________");
                            System.out.print("Enter your choice: ");
                            Schoice = input.next();

                            switch (Schoice) {
                                case "1": {
                                    // Adding an item
                                    String itemans;
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                    // Added this line
                                    do {
                                        System.out.println("\n\n---------------------------------------------");
                                        System.out.println("                   ADD ITEM");
                                        System.out.println("---------------------------------------------");

                                        System.out.print("Enter Item title: ");
                                        itemTitle[itemCount] = input.next().toUpperCase();
                                        input.nextLine(); // Consume the newline character

                                        System.out.print("Enter Item description: ");
                                        itemDesc[itemCount] = input.next();
                                        input.nextLine();

                                        System.out.print("Starting Bid Price: ");
                                        startingBid[itemCount] = input.nextDouble();

                                        System.out.print("Bid Increment: ");
                                        bidIncrement[itemCount] = input.nextDouble();
                                        input.nextLine(); // Consume the newline character

                                        // Program inputs start date automatically
                                        sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                        String currentTime = sdf.format(new Date());
                                        startDates[itemCount][foundIndex] = currentTime;

                                        // Prompt the seller to input the end date
                                        System.out.print("Enter End date of the auction (MM/dd/yyyy HH:mm:ss): ");
                                        String endDateStr = input.nextLine();

                                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                        try {
                                            endDate[itemCount] = df.parse(endDateStr);
                                            // Format the endDate and store it
                                            String formattedEndDate = df.format(endDate[itemCount]);
                                            endDates[itemCount][foundIndex] = formattedEndDate;
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        // Store the item index for the current seller
                                        sellerItemIndices[foundIndex][itemCountPerSeller[foundIndex]] = itemCount;
                                        itemCountPerSeller[foundIndex]++;

                                        System.out.println("_____________________________________________");

                                        System.out
                                                .print("Do you want to put another item for sale (Y/N)? ");
                                        itemans = input.next().toLowerCase();
                                        input.nextLine(); // Consume the newline character

                                        itemCount++;
                                    } while (itemans.equals("y"));

                                    break;
                                }

                                case "2": {
                                    // View items logic
                                    System.out.println("\n\n---------------------------------------------");
                                    System.out.println("                 VIEW ITEMS");
                                    System.out.println("---------------------------------------------");
                                    // Check if the seller has added any items
                                    if (itemCountPerSeller[foundIndex] == 0) {
                                        System.out.println("You have not added any items for sale.");
                                    } else {
                                        for (int i = 0; i < itemCountPerSeller[foundIndex]; i++) {
                                            int itemIndex = sellerItemIndices[foundIndex][i];
                                            System.out.println("Item #" + (i + 1) + ": " + itemTitle[itemIndex]);
                                            System.out.println("---------------------------------------------");
                                            System.out.println("Description: " + itemDesc[itemIndex]);
                                            System.out.println("Start Date: " + startDates[itemIndex][i]);
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                            String formattedEndDate = dateFormat.format(endDate[itemIndex]);
                                            System.out.println("End Date: " + formattedEndDate);

                                            System.out.println("---------------------------------------------");
                                            System.out.println("Starting Bid Price: " + startingBid[itemIndex]);
                                            System.out.println("Bid Increment: " + bidIncrement[itemIndex]);
                                            System.out.println("Current Bid Price: $" + currentBid[itemIndex]);
                                            System.out.println("_____________________________________________");

                                            // Check if the auction has ended and the buyer has paid
                                            if (endDate[itemIndex].before(new Date()) && buyerPaid[itemIndex]) {
                                                // Prompt the seller to accept or reject the payment
                                                System.out.println(
                                                        "The auction has ended and the buyer has paid for this item.");
                                                System.out.println("Do you want to accept the payment? (Y/N)");
                                                String acceptPayment = scanner.nextLine();

                                                if (acceptPayment.equalsIgnoreCase("Y")) {
                                                    // Display transaction confirmation to the seller
                                                    System.out.println("Transaction confirmed.");
                                                    System.out.println("---------------------------------------------");
                                                    System.out.println("Item: " + itemTitle[itemIndex]);
                                                    System.out.println("Price: $" + currentBid[itemIndex]);
                                                    System.out.println("Buyer: " + Bname[itemIndex]);
                                                    System.out.println("Buyer ID: " + Buyid[itemIndex]);
                                                    System.out.println(
                                                            "Buyer Home Address: " + Bhmad[itemIndex]);
                                                    System.out.println("Buyer Shipping Address: " + Bsmad[itemIndex]);
                                                    System.out.println("Email: " + Bemad[itemIndex]);
                                                } else {
                                                    System.out.println("---------------------------------------------");
                                                    System.out.println("Payment rejected.");
                                                }
                                            }
                                        }
                                    }
                                    break;
                                }

                                case "3": {
                                    feedbackseller(foundIndex);
                                    break;
                                }

                                case "4": {
                                    // Return to the main menu
                                    System.out.println("_____________________________________________");
                                    System.out.println("\nReturning to the main menu...");
                                    break;
                                }

                                default: {
                                    System.out.println("_____________________________________________");
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                                }
                            }
                        } while (!Schoice.equals("4")); // Loop until the seller chooses to go back to the
                        // main menu
                    } else {
                        System.out.println("---------------------------------------------");
                        System.out.println("            Password is incorrect.");
                    }
                } else {
                    System.out.println("---------------------------------------------");
                    System.out.println("               Seller ID not found.");
                }
                break;
            }

            case "n": {
                // Seller sign-up
                System.out.println("\n\n---------------------------------------------");
                System.out.println("                SELLER SIGN-UP");
                System.out.println("---------------------------------------------");
                System.out.print("Enter Name: ");
                Sname[Ssize] = input.next();
                input.nextLine(); // Consume the newline character
                System.out.print("Enter Email address: ");
                Semad[Ssize] = input.next();
                input.nextLine();
                System.out.print("Enter home address: ");
                Shome[Ssize] = input.next();
                input.nextLine();
                System.out.print("Enter Bank Account: ");
                Sbank[Ssize] = input.next();
                input.nextLine();
                System.out.print("Enter Routing number: ");
                Sroute[Ssize] = input.next();
                input.nextLine();

                boolean validSellerId = false;
                while (!validSellerId) {
                    System.out.println("---------------------------------------------");
                    System.out.print("Enter Seller ID: ");
                    String sellerId = input.next();
                    input.nextLine();
                    boolean sellerIdExists = false;
                    for (int i = 0; i < Ssize; i++) {
                        if (Selid[i].equals(sellerId)) {
                            sellerIdExists = true;
                            break;
                        }
                    }
                    if (sellerIdExists) {
                        System.out.println("Seller ID already exists. Please enter a different one.");
                    } else {
                        Selid[Ssize] = sellerId;
                        validSellerId = true;
                    }
                }

                System.out.print("Enter Password: ");
                SPass[Ssize] = input.next();
                input.nextLine();

                System.out.println("_____________________________________________");
                System.out.println("You have successfully made a seller account.");
                Ssize++;
                break;
            }
        }
    }

    public static void browseItems(Scanner input, String[] Bname, String[] Buyid, String[] BPass,
            int Bsize, String[] Sname, int Ssize, String[] itemTitle,
            String[] itemDesc, double[] startingBid, double[] currentBid,
            double[] bidIncrement, String[][] startDates, Date[] endDate,
            int itemCount, int[][] sellerItemIndices, int[] itemCountPerSeller,
            int[][] buyerBidIndices, int[] bidCountPerBuyer, double[][] bidPrices,
            String[][] bidTimes, int foundIndex) {
        System.out.println("\n\n---------------------------------------------");
        System.out.println("               BROWSE ITEMS");
        System.out.println("---------------------------------------------");

        boolean itemsFound = false; // To track if any items are found

        // Iterate through all sellers and their items
        for (int sellerIndex = 0; sellerIndex < Ssize; sellerIndex++) {
            int itemsForSeller = itemCountPerSeller[sellerIndex];
            if (itemsForSeller > 0) {
                for (int i = 0; i < itemsForSeller; i++) {
                    int itemIndex = sellerItemIndices[sellerIndex][i];
                    System.out.println(
                            "---------------------------------------------");
                    System.out.println("\t\tItem: " + itemTitle[itemIndex]);
                    System.out.println(
                            "---------------------------------------------");
                    System.out.println("Seller: " + Sname[sellerIndex]);
                    System.out.println("Description: " + itemDesc[itemIndex]);
                    System.out.println("\nStart Date: " + startDates[itemIndex][sellerIndex]);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String formattedEndDate = dateFormat.format(endDate[itemIndex]);
                    System.out.println("End Date: " + formattedEndDate);

                    System.out.println(
                            "---------------------------------------------");
                    System.out.println(
                            "Starting Bid Price: $" + startingBid[itemIndex]);
                    System.out.println(
                            "Bid Increment: $" + bidIncrement[itemIndex]);
                    System.out.println(
                            "Current Bid Price: $" + currentBid[itemIndex]);
                    System.out.println(
                            "_____________________________________________");

                    // Allow buyers to bid only if the auction has not ended
                    if (Bsize > 0) {
                        Calendar currentDate = Calendar.getInstance();
                        Calendar auctionEndDate = Calendar.getInstance();
                        auctionEndDate.setTime(endDate[itemIndex]);

                        if (currentDate.before(auctionEndDate)) {
                            System.out.print("Do you want to place a bid on this item? (Y/N): ");
                            String bidChoice = input.next().trim().toLowerCase();

                            if (bidChoice.equals("y")) {
                                System.out.print("Enter your bid amount: $");
                                double newBid = input.nextDouble();

                                double minBidAmount = currentBid[itemIndex] + bidIncrement[itemIndex];

                                if (newBid >= minBidAmount) {
                                    currentBid[itemIndex] = newBid;

                                    // Store bid information
                                    int buyerBidIndex = bidCountPerBuyer[foundIndex];
                                    buyerBidIndices[foundIndex][buyerBidIndex] = itemIndex;
                                    bidPrices[foundIndex][buyerBidIndex] = newBid;
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                    String currentTime = sdf.format(new Date());
                                    bidTimes[foundIndex][buyerBidIndex] = currentTime;
                                    bidCountPerBuyer[foundIndex]++;
                                    System.out.println("Bid placed successfully!");
                                } else {
                                    System.out.println("Bid amount must be at least $" + minBidAmount);
                                }
                            }
                        } else {
                            System.out.println("This auction has ended.");
                        }
                    }
                    itemsFound = true;
                }
            }
        }

        if (!itemsFound) {
            System.out.println("No items available for bidding.");
        }
    }

    public static void viewBids(int foundIndexn) {
        System.out.println("\n\n---------------------------------------------");
        System.out.println("              VIEW MY BIDS");
        System.out.println("---------------------------------------------");

        int bidCount = bidCountPerBuyer[foundIndex];

        if (bidCount > 0) {
            for (int i = 0; i < bidCount; i++) {
                int itemIndex = buyerBidIndices[foundIndex][i];
                System.out.println("Item: " + itemTitle[itemIndex]);
                System.out.println("Bid Amount: $" + bidPrices[foundIndex][i]);
                System.out.println("Bid Time: " + bidTimes[foundIndex][i]);
                System.out.println(
                        "---------------------------------------------");
            }
        } else {
            System.out.println("You haven't placed any bids yet.");
        }
    }

    public static void AuctionWin() {
        // Auction Winners
        System.out.println("\n\n---------------------------------------------");
        System.out.println("              AUCTION WINNERS");
        System.out.println("---------------------------------------------");

        // Iterate through all items
        Date currentDate = new Date(); // Move this line outside the loop

        for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
            if (currentDate.after(endDate[itemIndex])) {
                // Find and display the winning buyer
                int winningBuyerIndex = -1;
                double highestBid = 0.0;
                for (int buyerIndex = 0; buyerIndex < Bsize; buyerIndex++) {
                    if (bidPrices[itemIndex][buyerIndex] > highestBid) {
                        highestBid = bidPrices[itemIndex][buyerIndex];
                        winningBuyerIndex = buyerIndex;
                    }
                }

                if (winningBuyerIndex != -1) {
                    System.out.println("\t\tWinner: " + Bname[winningBuyerIndex]);
                    System.out.println("_____________________________________________");
                    System.out.println("Item: " + itemTitle[itemIndex]);
                    System.out.println("Description: " + itemDesc[itemIndex]);
                    System.out.println("Seller: " + Sname[findSellerIndex(Selid[itemIndex], Selid, Ssize)]);
                    System.out.println("_____________________________________________");
                    System.out.println("Winning Bid: $" + highestBid);
                    System.out.println("_____________________________________________");
                }
            }
        }
    }

    public static void feedbackbuyer(int foundIndex) {
        if (foundIndex >= 0 && foundIndex < Bsize) {
            System.out.println("\n\n---------------------------------------------");
            System.out.println("                 ITEMS WON");
            System.out.println("---------------------------------------------");

            boolean itemsWon = false;

            for (int i = 0; i < bidCountPerBuyer[foundIndex]; i++) {
                int itemIndex = buyerBidIndices[foundIndex][i];

                // Check if the buyer's bid is the highest for this item
                if (currentBid[itemIndex] == bidPrices[itemIndex][foundIndex]) {
                    System.out.println("Item #" + (i + 1) + ": " + itemTitle[itemIndex]);
                    System.out.println("---------------------------------------------");
                    System.out.println("Description: " + itemDesc[itemIndex]);
                    System.out.println("Start Date: " + startDates[itemIndex][sellerItemIndices[foundIndex][i]]);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String formattedEndDate = dateFormat.format(endDate[itemIndex]);
                    System.out.println("End Date: " + formattedEndDate);
                    System.out.println("---------------------------------------------");
                    System.out.println("Starting Bid Price: $" + startingBid[itemIndex]);
                    System.out.println("Bid Increment: $" + bidIncrement[itemIndex]);
                    System.out.println("Your Winning Bid Price: $" + bidPrices[itemIndex][foundIndex]);
                    System.out.println("_____________________________________________");

                    itemsWon = true;
                }
            }

            // Provide feedback
            if (!itemsWon) {
                System.out.println("You haven't won any items yet.");
            } else {
                System.out.print("Enter rate (1-10): ");
                int rate = input.nextInt();
                input.nextLine(); // Consume the newline character
                System.out.print("Enter your feedback for this item: ");
                String feedback = input.nextLine();

                // Store feedback for the items won
                for (int i = 0; i < bidCountPerBuyer[foundIndex]; i++) {
                    int itemIndex = buyerBidIndices[foundIndex][i];
                    buyerRatings[itemIndex] = rate;
                    buyerComments[itemIndex] = feedback;
                }

                System.out.println("Feedback submitted successfully!");
            }
        } else {
            System.out.println("Buyer ID not found.");
        }
    }

    public static void feedbackseller(int foundIndex) {
        if (foundIndex >= 0 && foundIndex < Bsize) {
            System.out.println("\n\n---------------------------------------------");
            System.out.println("                  ITEMS SOLD");
            System.out.println("---------------------------------------------");

            boolean itemsWon = false;

            for (int i = 0; i < bidCountPerBuyer[foundIndex]; i++) {
                int itemIndex = buyerBidIndices[foundIndex][i];

                // Check if the buyer's bid is the highest for this item
                if (currentBid[itemIndex] == bidPrices[itemIndex][foundIndex]) {
                    System.out.println("Item #" + (i + 1) + ": " + itemTitle[itemIndex]);
                    System.out.println("---------------------------------------------");
                    System.out.println("Description: " + itemDesc[itemIndex]);
                    System.out.println("Start Date: " + startDates[itemIndex][sellerItemIndices[foundIndex][i]]);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String formattedEndDate = dateFormat.format(endDate[itemIndex]);
                    System.out.println("End Date: " + formattedEndDate);
                    System.out.println("---------------------------------------------");
                    System.out.println("Starting Bid Price: $" + startingBid[itemIndex]);
                    System.out.println("Bid Increment: $" + bidIncrement[itemIndex]);
                    System.out.println("Your Winning Bid Price: $" + bidPrices[itemIndex][foundIndex]);
                    System.out.println("_____________________________________________");

                    itemsWon = true;
                }
            }

            // Provide feedback
            if (!itemsWon) {
                System.out.println("You haven't sold any items yet.");
            } else {
                System.out.print("Enter rate (1-10): ");
                int rate = input.nextInt();
                input.nextLine(); // Consume the newline character
                System.out.print("Enter your feedback for this item: ");
                String feedback = input.nextLine();

                // Store feedback for the items won
                for (int i = 0; i < bidCountPerBuyer[foundIndex]; i++) {
                    int itemIndex = buyerBidIndices[foundIndex][i];
                    sellerRatings[itemIndex] = rate;
                    sellerComments[itemIndex] = feedback;
                }

                System.out.println("Feedback submitted successfully!");
            }
        } else {
            System.out.println("Buyer ID not found.");
        }
    }

    public static void displayFeedback() {
        System.out.println("\n\n---------------------------------------------");
        System.out.println("                   FEEDBACK");
        System.out.println("---------------------------------------------");

        boolean feedbackFound = false;

        for (int i = 0; i < itemCount; i++) {
            if (sellerRatings[i] != 0 && sellerComments[i] != null) {
                System.out.println("Item #" + (i + 1) + ": " + itemTitle[i]);
                System.out.println("---------------------------------------------");
                System.out.println("Description: " + itemDesc[i]);
                System.out.println("Seller: ");
                System.out.println("Rating: " + sellerRatings[i]);
                System.out.println("Feedback: " + sellerComments[i]);
                System.out.println("Buyer: ");
                System.out.println("Rating: " + buyerRatings[i]);
                System.out.println("Feedback: " + buyerComments[i]);
                System.out.println("_____________________________________________");

                feedbackFound = true;
            }
        }

        if (!feedbackFound) {
            System.out.println("No feedback available.");
        }
    }

}