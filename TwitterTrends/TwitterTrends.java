//Jonas Himawan
//2/25/2025
//CSE 122
//C2: TwitterTrends
//TA: Chaafen Raees and Afifah Kashif
//The TwitterTrends class prompts the user to analyze trends from an object's
//collection of other objects. This class allows the user to see the most frequent word
//out of all of the objects and the object with the greatest number of likes.
import java.util.*;
import java.io.*;

public class TwitterTrends {
    private TweetBot bot;

    //Behaviors:
    // - Constructor for the TwitterTrends class.
    // - Initializes the TwitterTrends object that is used to provide data for trend analysis.
    //Parameters:
    // - An object that contains alot of other objects for the TwitterTrends class to
    // analyze.
    public TwitterTrends(TweetBot bot){
        this.bot = bot;
    }

    //Behaviors:
    // - Iterates through all of the objects and breaks down the caption in each object
    // to individual words.
    // - Counts the frequency of each word throughout all of the objects and returns it.
    //Returns:
    // - A string represeting the most frequent word found out of all of the objects.
    public String getMostFrequentWord(){
        ArrayList<String> wordList = new ArrayList<>();
        //Looks through every object and adds the words in every object to 'wordList'.
        for (int i = 0; i < bot.numTweets(); i++){
            Tweet currentTweet = bot.nextTweet();
            Scanner scan = new Scanner(currentTweet.getCaption());

            while (scan.hasNext()){
                String word = scan.next();
                String lowerCaseWord = word.toLowerCase();
                wordList.add(lowerCaseWord);
            }
        }
        //Map that counts each unique word in all of the objects and finds how often 
        //each word is repeated.
        Map<String, Integer> wordCount = new HashMap<>();
        for (int i = 0; i < wordList.size(); i++){
            if (!wordCount.containsKey(wordList.get(i))){
                wordCount.put(wordList.get(i), 1);
            } else {
                wordCount.put(wordList.get(i), wordCount.get(wordList.get(i)) + 1);
            }
        }

        int count = 0;
        String mostCommon = "";
        //Finds the word with the greatest number from the map (most frequently mentoned)
        //out of all of the objects.
        for (String uniqueWord : wordCount.keySet()){ 
            if (wordCount.get(uniqueWord) > count){
                mostCommon = uniqueWord;
                count = wordCount.get(uniqueWord);
            }
        }
        return mostCommon;
    }

    //Behaviors:
    // - Iterates through all of the objects and obtains their number of likes.
    // - Adds all of the likes to a list and identifies the highest number of likes
    // from all of the objects.
    //Returns:
    // - An integer represeting the highest number of likes recieved by an object
    // compaed to all of the other objects.
    public int getMostLikes(){
        ArrayList<Integer> likeList = new ArrayList<>();
        //Looks through every object and finds the number of likes through each object.
        //Adds the likes of each object to "likeList"
        for (int i = 0; i < bot.numTweets(); i++){
            Tweet currTweet = bot.nextTweet();
            int likes = currTweet.getLikes();
            likeList.add(likes);
        }

        int countLikes = 0;
        //Finds the greatest number in the "likeList" which shows the greatest amount of lieks
        //out of all of the objects.
        for (int eachLike : likeList){
            if (eachLike > countLikes){
                countLikes = eachLike;
            } 
        }
        return countLikes;
    }
}