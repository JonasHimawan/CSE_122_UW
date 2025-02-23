//Jonas Himawan
//2/20/2025
//CSE 122
//P2: Absurdle
//TA: Chaafen Raees and Afifah Kashif
//The Absurdle class prompts the user with the fun and challenging word guessing game
//known as "Absurdle". First the user has to pick one of the dictionaries given in the files.
//These dictionaries include 'dictionary1.txt','small_dict.txt', and 'dictionary2.txt'.
//After the user picks a dictionary, they have the pick a word size. The user will then 
//have to make a guesses that prunes the entire dictionary that they chose. The user will have
//unlimited tries, but the less tries that they prune the entire dictionary the better.
//Absurdle's goal is to prolong the game for as long as it can. The game ends when the user 
//prunes the entire dictionary.
import java.util.*;
import java.io.*;

public class Absurdle  {
    public static final String GREEN = "🟩";
    public static final String YELLOW = "🟨";
    public static final String GRAY = "⬜";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the game of Absurdle.");

        System.out.print("What dictionary would you like to use? ");
        String dictName = console.next();

        System.out.print("What length word would you like to guess? ");
        int wordLength = console.nextInt();

        List<String> contents = loadFile(new Scanner(new File(dictName)));
        Set<String> words = pruneDictionary(contents, wordLength);
        System.out.println(contents);

        List<String> guessedPatterns = new ArrayList<>();
        while (!isFinished(guessedPatterns)) {
            System.out.print("> ");
            String guess = console.next();
            String pattern = recordGuess(guess, words, wordLength);
            guessedPatterns.add(pattern);
            System.out.println(": " + pattern);
            System.out.println();
        }
        System.out.println("Absurdle " + guessedPatterns.size() + "/∞");
        System.out.println();
        printPatterns(guessedPatterns);
    }


    // Prints out the given list of patterns.
    // - List<String> patterns: list of patterns from the game
    public static void printPatterns(List<String> patterns) {
        for (String pattern : patterns) {
            System.out.println(pattern);
        }
    }


    // Returns true if the game is finished, meaning the user guessed the word. Returns
    // false otherwise.
    // - List<String> patterns: list of patterns from the game
    public static boolean isFinished(List<String> patterns) {
        if (patterns.isEmpty()) {
            return false;
        }
        String lastPattern = patterns.get(patterns.size() - 1);
        return !lastPattern.contains("⬜") && !lastPattern.contains("🟨");
    }


    // Loads the contents of a given file Scanner into a List<String> and returns it.
    // - Scanner dictScan: contains file contents
    public static List<String> loadFile(Scanner dictScan) {
        List<String> contents = new ArrayList<>();
        while (dictScan.hasNext()) {
            contents.add(dictScan.next());
        }
        return contents;
    }


    //Behaviors:
    // - Makes the user's intended dictionary have the user specified word length.
    //Paramters:
    // - List<String> contents: The list of words from the user's intended dictionary.
    // - int wordLength: The user's intended length for the words in the game.
    //Returns:
    // - A Set<String> dictionary that is a set of words from the contents list, that 
    //   match the specified length.
    //Exceptions:
    // - IllegalArgumentException: Thrown if the user's wordLength is less than 1.
    public static Set<String> pruneDictionary(List<String> contents, int wordLength){
        if(wordLength < 1){
            throw new IllegalArgumentException("The word length is too small, it "
            + "cannot be less than 1");
        }
        Set<String> dictionary = new TreeSet<>();
        //Traverses through the list containing the words from the dictionary.
        //Checks which word in the list are the same length as 'wordLength'.
        //Adds those words to a new set 'dicionary'.
        for (String word : contents){
            if(word.length() == wordLength){
                dictionary.add(word);
            }
        }
        return dictionary;
    }


    //Behaviors:
    // - Takes in the user's guess and determines the next set of words that will 
    //   be under consideration.
    // - Returns the color pattern of the user's guess.
    // - Updates the dictionary with the next set of words that will be under consideration.
    //Paramters:
    // - String guess: The word guessed by the user.
    // - Set<String> words: The set of possible words from the dictionary that have already 
    //   been pruned and match the lenth of the user's intended length of the word.
    // - int wordLength: The user's intended length for the words in the game.
    //Returns:
    // - A color-coded String that's either "🟩🟨⬜" representing how the guess 
    //   matches the possible words in the dictionary.
    //Exceptions:
    // - IllegalArgumentException: Thrown if the set of words is empty or if the guess and 
    //   words in the dictionary are not the same length;
    public static String recordGuess(String guess, Set<String> words, int wordLength) {
        if (words.isEmpty() || (guess.length() != wordLength)){
            throw new IllegalArgumentException ("Set of words cannot be empty " 
            + "and guess has to be the correct length");
        }
        String records = "";
        Map <String, Set<String>> nestedCollection = new TreeMap <>();
        
        //Traverses trough the words set, and finds all possible color patterns.
        //Matches the words correlated to each color pattern.
        for (String s : words){
             String patternInDict = patternFor(s, guess);
             if (!nestedCollection.containsKey(patternInDict)){
                nestedCollection.put(patternInDict, new TreeSet<>());
             }
             nestedCollection.get(patternInDict).add(s);
        }

        //Counts the number of words in each possible color set.
        //Choose the set that has the highest count, prolongs the game.
        int count = 0;
        for (String k : nestedCollection.keySet()){
            Set <String> values = nestedCollection.get(k);
            int setSize = values.size();
            if (setSize > count){
                count = setSize;
                records = k;
            }
        }
        // Clears the original set
        words.clear(); 
        //Updates the dicitonary
        words.addAll(nestedCollection.get(records));
        
        //Prints the words left in the dictionary, makes it easier for the user to play.
        System.out.println(" - " + nestedCollection.get(records));
        return records;
       
    }


    //Behaviors:
    // - Helper method that creates a color coded pattern comparing the user's guess to a word.
    //Paramters:
    // - String word: The target word that is being guessed.
    // - String guess: The word guessed by the user.
    //Returns:
    // - A String that is a color coded representation of the match color pattern between the guess
    //   and target.
    public static String patternFor(String word, String guess) {
        String color = "";

        //Creates Array List where each element in the guess is an element in the list
        List<String> guessList = new ArrayList<>();
        for (int i = 0; i < guess.length(); i++){
            String guessChar = "" + guess.charAt(i);
            guessList.add(guessChar);
        }
    
        //Counts the characters from word which haven’t yet been used in an exact or 
        //approximate match. 
        //Correlates each character in the guess to number, through the map 'match'.
        Map<Character, Integer> match = new TreeMap<>();
        for (int i = 0; i < word.length(); i++){
            String charToString = "" + word.charAt(i);
            if (!match.containsKey(word.charAt(i))){
                match.put(word.charAt(i), 1);
            } else {
                match.put(word.charAt(i), match.get(word.charAt(i)) + 1);
            }
        }

        //Translates the guess into colors.
        getColor(word, guess, guessList, match);
      
        //Turns the array list into a string
        for (int i = 0; i< guessList.size(); i++){
            color += guessList.get(i);
        }
        return color;
    }


    //Behaviors:
    // - Helper method that determines the color for each character in a guess based on its
    //   match with the target word.
    // - 🟩🟨⬜, green for exact matches, yellow for same letter but different loction, 
    //   gray for no matches.
    //Paramters:
    // - String word: The target word that is being guessed.
    // - String guess: The word guessed by the user.
    // - List<String> guessList: A list of individual characters from the guess.
    // - Map<Character, Integer> match: A map tracking the count of each character in the 
    //   target word.
    //Returns:
    // - A List<String> of the updated list of characters of the user's guess color coordinated
    //   to either green, yellow, or gray.
    public static List<String> getColor (String word, String guess, List<String> guessList, 
                                        Map<Character, Integer> match){
        //Locates the green squares, exact word and exact location.
        for(int i = 0; i < guessList.size(); i++){
            if (guessList.get(i).equals("" + word.charAt(i))){
                guessList.set(i, GREEN);
                match.put(word.charAt(i), match.get(word.charAt(i)) - 1);
            }
        }

        //Locates the yelllow squares, exact word and different location. 
        for (int i = 0; i < guessList.size(); i++){
            if (!guessList.get(i).equals(GREEN)) { // Skip already matched greens
                char guessChar = guess.charAt(i);
                if (match.containsKey(guessChar) && match.get(guessChar) > 0) {
                    guessList.set(i, YELLOW);
                    match.put(guessChar, match.get(guessChar) - 1); // Reduce count
                }
            }
        }

        //Sets everything in the guessList that is not green or yellow as gray.
        for (int i = 0; i < guessList.size(); i++) {
            if (!guessList.get(i).equals(GREEN) && !guessList.get(i).equals(YELLOW)) {
                guessList.set(i, GRAY);
            }
        }
        
        return guessList;
    }
}