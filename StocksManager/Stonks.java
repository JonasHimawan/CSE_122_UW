//Jonas Himawan
//1/23/2025
//CSE 122
//P0: Stonks
//TA: Chaafen Raees and Afifah Kashif
//The stonks class prompts a user to buy, sell, or save the stocks from the "stonks.tsv" file.
//The "stonks.tsv" file contains a list of stock tickers and their prices. 
import java.util.*;
import java.io.*;

public class Stonks {
    public static final String STOCKS_FILE_NAME = "stonks.tsv";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        Scanner fileScan = new Scanner(new File(STOCKS_FILE_NAME));

        int numStocks = Integer.parseInt(fileScan.nextLine());
        String [] stocks = new String [numStocks];
        double [] price = new double [numStocks];
        double [] portfolio = new double [numStocks];
        double budget = 0.0;
        double numShares = 0.0;
        double portTotal = 0.0;
        String choice = "";

        loadFile(fileScan, stocks, price);

        System.out.println("Welcome to the CSE 122 Stocks Simulator!");
        System.out.println("There are " + numStocks + " stocks on the market:");
        //Prints out the stocks in the "stonks.tsv" file and how much each one costs.
        for (int i = 0; i < stocks.length; i++){
                System.out.println(stocks[i] + ": " + price[i]);
        }
        
        while (!choice.equalsIgnoreCase("Q")) {
            System.out.println();
            //All of the user's options throughout the program.
            //User will see this message in the beginning and again each time 
            //they buy, sell, or save.
            System.out.println("Menu: (B)uy, (Se)ll, (S)ave, (Q)uit");
            System.out.print("Enter your choice: ");
            choice = console.nextLine();

            if (choice.equalsIgnoreCase("B")) {
                System.out.print("Enter the stock ticker: ");
                //Stores the stock the user wants to buy as 'stockName'
                String stockName = console.nextLine();
                findStock(stocks, stockName);
                System.out.print("Enter your budget: ");
                //Stores the budget the user has when buying a stock as 'budget'
                budget = Double.parseDouble(console.nextLine());

                buyStock(console, budget, stockName, portfolio, stocks, price);

            } else if (choice.equalsIgnoreCase("Se")) {
                System.out.print("Enter the stock ticker: ");
                //Stores the stock the user wants to sell as 'stockName'
                String stockName = console.nextLine();
                System.out.print("Enter the number of shares to sell: ");
                //stores the number of shares the user wants to sell as 'numShares'
                numShares = Double.parseDouble(console.nextLine());
                double stockPrice = 0.0;
            
                sell(console, numShares, stockName, price, portfolio, stockPrice, stocks);
    
            } else if (choice.equalsIgnoreCase("S")) {
                //File name has to end with '.txt' so user can see the file
                // at the end of the program
                System.out.print("Enter new portfolio file name: ");
                //Stores the file the user wants to save their portfolio in as 'name'
                String name = console.nextLine();

                save(stocks, price, portfolio, name);

            } else if (!choice.equalsIgnoreCase("Q")) {
                //User isn't allowed to type in any other characters besides, 'B','Se','S', and 'Q'
                //upper and closer cases don't matter.
                //Message will be displayed if user doesn't type in these characters
                System.out.println("Invalid choice: " + choice);
                System.out.println("Please try again");
            }
        }

        //Totals the value of the portfolio by multipying it by the price of the stock it contains.
        for (int i = 0; i < portfolio.length; i++){
            portTotal += (portfolio[i] * price[i]);
        }
        //Rounds the total portfolio price to 4 decimal places.
        double scale = Math.pow(10, 4); 
        double rounded = Math.round(portTotal * scale) / scale;
        System.out.println();
        //Prints out the total portfolio value after the user quits the program.
        System.out.println("Your portfolio is currently valued at: $" + rounded);
    }
    
    //Behaviors:
    // - Reads the stock data from the provided file using a Scanner object.
    // - For each subsequent line, the method extracts a stock ticker and its corresponding price.
    // - Stock tickers are stored in the 'stocks' array, prices in the 'price' array.
    //Parameters:
    // - fileScan: A Scanner object used to read the input file.
    // - stocks: A String array where stock tickers will be stored.
    // - price: A double array where stock prices will be stored.
    public static void loadFile(Scanner fileScan, String[] stocks, double[] price){
        int index = 0;
        fileScan.nextLine();
        while(fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            //Stores the stock name and prices to the array 'stocks' and 'price'
            //from the stonks.tsv file.
            stocks[index] = lineScan.next();
            price[index] = lineScan.nextDouble();
            index++;
        }
    }

    //Behaviors:
    // - Searches the 'stocks' array for a ticker that matches the specified 'stockName'.
    // - If the stock is found, no action is taken (just a search).
    // - If the stock is not found, prints a message indicating that the stock is not 
    //   present in the dataset.
    //Parameters:
    // - stocks: A String array containing the list of stock tickers.
    // - stockName: A String representing the ticker of the stock to search for.
    public static void findStock(String[] stocks, String stockName){
        int index = indexOf(stocks, stockName);
        if (index == -1) {
            System.out.println("This stock is not present in dataset");
        } 
    }

    //Behaviors:
    // - Iterates through the 'stocks' array to find the index of the desired 'stockName'.
    // - If the stock is found, the method returns the index of the stock in the array.
    // - If the stock is not found, the method returns -1.
    //Parameters:
    // - stocks: A String array containing the list of stock tickers.
    // - stockName: A String representing the ticker of the stock to search for.
    //Returns:
    // - int: The index of the 'stockName' in the 'stocks' array, or -1 if the stock is not found.
    public static int indexOf(String[] stocks, String stockName) {
        int index = -1;
        for (int i = 0; i < stocks.length; i++) {
            if (stocks[i].equals(stockName)) {
                index = i;
            }
        }
        return index; 
    }
   
    //Behaviors:
    // - Makes sure the user's budget is $5 or greater, in order to buy a stock.
    // - Lets the user buy a stock if they have 5$ or more.
    // - Therefore, user is allowed to buy a stock with 5$.
    // - Displays a message if the budget is only less than $5.
    // - Searches for the intended stock in the 'stocks' array.
    // - Updates the 'portfolio' array with the number of shares purchased.
    // - Deducts the cost of the purchased shares from the user's budget.
    //Parameters:
    // - console: A Scanner object used for reading user input.
    // - budget: A double array containing the user's available budget 
    // - stockName: A String representing the ticker symbol of the stock to be bought.
    // - portfolio: A double array tracking the user's number of shares for each stock.
    // - stocks: A String array containing the list of available stock tickers.
    // - price: A double array containing the prices of each stock.
    public static void buyStock (Scanner console, double budget, String stockName, 
                            double [] portfolio, String [] stocks, double [] price){
        //If the budget is lower than $5, the user won't be able to buy any stock
        if (budget < 5 ){
            System.out.println("Budget must be at least $5");
        } else {
            for(int i = 0; i < stocks.length; i++){
                if (stocks[i].equals(stockName)){
                    //Makes sure the portfolio is updated with the amount of a certain stock.
                    //Not updated with the amount of money of a certain stock.
                    portfolio[i] += (budget/price[i]);
                    System.out.println("You successfully bought " + stockName + ".");
                }
            }
        }
    }

    //Behaviors:
    // - Searches for the specified stock in the 'stocks' array.
    // - Checks if the user has enough shares of the specified stock in their portfolio to complete the sale.
    // - If the user has enough shares, it calculates the total value of the shares being sold and updates the 'portfolio' array.
    // - If the user does not have enough shares, it prints a message indicating the insufficient quantity.
    // - If successfully sold, updates the user's portfolio by reducing the number of shares owned after the sale. 
    //Parameters:
    // - console: A Scanner object used for reading user input.
    // - numShares: A double representing the number of shares the user wants to sell.
    // - stockName: A String representing the ticker symbol of the stock to be sold.
    // - price: A double array containing the prices of each stock.
    // - portfolio: A double array tracking the user's number of shares for each stock.
    // - stocks: A String array containing the list of available stock tickers.
    public static void sell (Scanner console, double numShares, String stockName, double [] price,
                        double [] portfolio, double stockPrice, String [] stocks){
        //The for loop is used to traverse through the stocks array 
        //to try to find the stock the user wants to sell.
        for (int i = 0; i < stocks.length; i++){
            //The first if statement is used to find the price of the stock the user wants to sell.
            if (stocks[i].equals(stockName)){
                //Checks to make sure user has enough of a stock to sell.
                //User cannot sell more than what they have.
                if ((numShares) > (portfolio[i])){
                    System.out.println("You do not have enough shares of " + stockName 
                    + " to sell " + numShares + " shares.");
                } else {
                    stockPrice = price [i];
                    stockPrice *= numShares;
                    portfolio[i] -= (stockPrice/price[i]);
                    System.out.println("You successfully sold "  + numShares +
                    " shares of " + stockName + ".");
                }
            }
        }
    }

    
    //Behaviors:
    // - Creates a file with the specified filename ending with ".txt"
    // - Writes the stock tickers and the corresponding number of shares in the portfolio to the file.
    // - Only stocks with a non-zero quantity in the portfolio are updated to the file.
    //Parameters:
    // - stocks: A String array containing the list of stock tickers.
    // - price: A double array containing the prices of each stock (not used directly in this method, but needed for consistency).
    // - portfolio: A double array containing the number of shares owned for each stock.
    // - fileName: A String representing the name of the file where the portfolio will be saved. 
    public static void save (String[] stocks, double[] price, double [] portfolio, 
                             String fileName) throws FileNotFoundException{
        File outFile = new File(fileName);
        PrintStream out = new PrintStream(outFile);
        for (int i = 0; i < stocks.length; i++) {
            //Fills the new "txt file" with stocks the user has.
            if (portfolio[i] != 0.0){
                out.println(stocks[i] + " " + portfolio[i]);
            }
        }
    }
}
