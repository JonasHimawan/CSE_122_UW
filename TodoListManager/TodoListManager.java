//Jonas Himawan
//1/30/2025
//CSE 122
//C1: TodoListManager
//TA: Chaafen Raees and Afifah Kashif
//The TodoListManager prompts the user to manage their todo list by
//allowing the user to add, mark as done, load, save, and view tasks.
//Additionally, it offers an optional feature to reverse todo list 
//and displays it if the EXENSION_FLAG is true.
import java.util.*;
import java.io.*;

public class TodoListManager {
    public static final boolean EXTENSION_FLAG = true;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        List <String> todos = new ArrayList <>();
        String choice = "";
        //Introducing the TODO List Manager to user:
        System.out.println("Welcome to your TODO List Manager!");
        //Will continue to run unless user presses 'Q';
        while (!choice.equalsIgnoreCase("Q")) {
            System.out.println("What would you like to do?");
            System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs, (S)ave TODOs, (Q)uit? ");
            choice = console.nextLine();

            if (choice.equalsIgnoreCase("A")) {
                addItem(console, todos);
                printTodos(todos);
                reverseOrder(console,todos);
                
            } else if (choice.equalsIgnoreCase("M")) {
                markItemAsDone(console, todos);
                printTodos(todos);
                reverseOrder(console,todos);
            
            } else if (choice.equalsIgnoreCase("L")) {
                loadItems(console,todos);
                printTodos(todos);
                reverseOrder(console,todos);

            } else if (choice.equalsIgnoreCase("S")) {
                saveItems(console, todos);
                printTodos(todos);
                reverseOrder(console,todos);
                

            } else if (!choice.equalsIgnoreCase("Q")) {
                //Will display message if the user makes a choice that doesn't include,
                //The letters 'A', 'M', 'S', 'L', 'Q'in upper or lowercase.
                System.out.println("Unknown input: " + choice);
                printTodos(todos);
                reverseOrder(console,todos);
            }    
        }
    }

    //Behaviors: 
    // - This method prints the current list of todo items. 
    // - If the todo list is empty, it displays a message telling the user to relax.
    // - If there are tasks in the list, it numbers and displays each task.
    //Parameters: 
    // - todos: A string arraylist containing the user's TODO items.
    public static void printTodos(List<String> todos) {
        System.out.println("Today's TODOs:");
        //Makes sure the todos arraylist is not empty.
        if(todos.size() > 0) {
            for (int i = 0; i < todos.size(); i++) {
                System.out.println("  " + (i+1) + ": " + todos.get(i)); 
            }
        } else {
            //The message displayed if the arraylist is empty.
            System.out.println(" You have nothing to do yet today! Relax!");
        }
    }

    //Behaviors: 
    // - prompts the user to enter a new todo item to add to the list.
    // - If the todo list is not empty, it asks the user where in the list to 
    //   add the new task by giving the user a range.
    // - If the user presses Enter without entering an index, the item is added 
    //   to the end of the list.
    // - If the todo list is empty, the new item is added directly to the list.
    //Parameters: 
    // - console: A Scanner object used for reading user input.
    // - todos: A string arraylist containing the user's todo items.
    public static void addItem(Scanner console, List<String> todos) {
        System.out.print("What would you like to add? ");
        String userTodos = console.nextLine();
        //Asks the user where they want their intended TODO to be throughout the list.
        //The list needs to have at least 1 todo in it.
        if (todos.size() > 0 ) {
            
            System.out.print("Where in the list should it be (1-" + (todos.size()+1) + ")?"
            + " (Enter for end): ");
            String input = console.nextLine();
            //If the user enters the "Enter" spacebar.
            //Automatically add their inteded todo at the end of the list.
            if (input.equals("")) {
                todos.add(userTodos);
            } else {
                //Adds the user's intended todo where they want it throughout the list.
                //The number they choose, becomes the spot in the list that thier inteded
                //todo will take.
                int numTodo = Integer.parseInt(input);
                todos.add(numTodo-1, userTodos);
            }
        } else {
            //If the todo list is already empty, such as the start of the program,
            //The todo the user wants to add will automatically be added to the end
            //of the list.
            todos.add(userTodos);
        }
    }

    //Behaviors: 
    // - This method prompts the user to select a todo item from the list to mark as completed.
    // - It removes the selected item from the todo list based on the index provided by the user.
    // - If the list is empty, it informs the user that there are no tasks left to mark as done.
    //Parameters: 
    // - console: A Scanner object used for reading user input.
    // - todos: A string arraylist containing the user's todo items.
    public static void markItemAsDone(Scanner console, List<String> todos) {
        //Makes sure the todo list is not empty.
        if (todos.size() > 0) {
            System.out.print("Which item did you complete (1-" + todos.size() + ")? ");
            int markNum = Integer.parseInt(console.nextLine());
            //Removes the user's completed todo from the todo list.
            //I did the '-1' because indexes of an array list start at 0.
            todos.remove(markNum-1);
        } else {
            //If the todo list is already empty, such as the start of the program,
            //a message will be displayed. So the user is not able to mark anything
            //as completed if the todo list is empty.
            System.out.println("All done! Nothing left to mark as done!");
        }
    }

    //Behaviors: 
    // - This method prompts the user to enter a file name to load todo items.
    // - It clears the current todo list and loads new tasks from the specified file.
    // - If the file is not found, it will throw a FileNotFoundException.
    //Parameters: 
    // - console: A Scanner object used for reading user input.
    // - todos: A string arraylist containing the user's TODO items.
    public static void loadItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        //Asks the user for the file name.
        //Has to be a file from the saved files.
        System.out.print("File name? ");
        String findFileName = console.nextLine();
        File outFile = new File(findFileName);
        Scanner fileInput = new Scanner(outFile);
        //Removing each item in the current todo list.
        while (todos.size() > 0) {
            todos.remove(0);
        }
        //loads the list in the intended file to the todos list.
        while (fileInput.hasNextLine()) {
            todos.add(fileInput.nextLine());
        }

    }

    //Behaviors:
    // - This method prompts the user to enter a file name to save the current todo list.
    // - It writes each todo item from the list to the specified file, with one item per line.
    // - If the file already exists, it will save the current todo list as that 
    //   existing file.
    //Parameters:
    // - console: A Scanner object used for reading user input.
    // - todos: A string arraylist containing the user's TODO items.
    public static void saveItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        //Asks the user for a file name.
        //Has to end in '.txt'
        System.out.print("File name? ");
        String fileName = console.nextLine();
        File outFile = new File(fileName);
        PrintStream out = new PrintStream(outFile);
        //Stores current arrlist 'Todos' in the the user's desired file name.
        for (int i = 0; i < todos.size(); i++) {
                out.println(todos.get(i));
        }
    }

    //Behaviors:
    // - This method provides an optional feature to display the todo list in reverse order.
    // - This method reverses the current todo list and displays the reversed list
    // - If the EXTENSION_FLAG is true and the todo list has more than one item, it asks the user
    //   if they want to see the list in reverse order.
    // - If the user agrees, it prints the todo list in reverse order, starting from the last item.
    // - If the user declines or enters an invalid input, it displays a message and does not reverse the list.
    //Parameters:
    // - console: A Scanner object used for reading user input.
    // - todos: A string arraylist containing the user's TODO items.
    public static void reverseOrder(Scanner console, List <String> todos) {
        if (EXTENSION_FLAG == true) {

            //Can't reverse the list if there is only 1 todo in the list.
            //Can only reverse the list if there are at least 2 todos.
            if (todos.size() > 1) {
                System.out.println();
                System.out.println("Creative extension:");
                System.out.print("Do you want your TODO list in the reverse order? (Y)es or (N)o ");
                String reverseChoice = console.nextLine();
                //User most click 'Y' if they want to reverse the list.
                if (reverseChoice.equalsIgnoreCase("Y")) {
                    System.out.println("This is your TODO List in the reverse order!");
                    if(todos.size() > 0) {
                        int i = 0;
                        int j = todos.size() - 1;
                        while (i < j) {
                        // Swap todos[i] with todos[j]
                        // This while loop reverses the items within the list
                            String reverse = todos.get(i);
                            todos.set(i, todos.get(j));
                            todos.set(j, reverse);
                            i++;
                            j--;
                        }
                        //Prints out the todo list in the reverse order:
                        for (int l = 0; l < todos.size(); l++){
                            System.out.println("  " + (l+1) + ": " + todos.get(l));
                        }
                        System.out.println("I hope you enjoyed your " + todos.size() + 
                        " TODO's in the reverse order!");
                    } else {
                        //Message displayed if user's list is less than 2 todos.
                        System.out.println("Sorry, your todo list is too small!");
                    }
                } else if (reverseChoice.equalsIgnoreCase("N")) {
                    //Message displayed if user doesn't want to reverse their list.
                    System.out.println("It's alright, maybe you can reverse your list" +
                    " next time!");
                } else {
                    //Message displayed if user doesn't choose, 'Y' or 'N', upper or lower cased.
                    System.out.println("Unknown input: " + reverseChoice);
                }
                System.out.println();   
            }
        }
    }
}