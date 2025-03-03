//Jonas Himawan
//2/25/2025
//CSE 122
//C2: TwitterTrends
//TA: Chaafen Raees and Afifah Kashif
//The TweetBot class prompts the user with a bot that manages a collection of objects.
//The bot manages objects that are added, removed, resetted. The bot also has access to the
//number of objects and is able to find the next objects.
import java.util.*;
import java.io.*;

public class TweetBot {
    private List<Tweet> tweets;
    private int currentIndex;

    //Behaviors:
    // - Constuctor of the TweetBot class.
    // - Initializes a new TweetBot instance with the provided objects.
    //Parameters:
    // - An object that will be managed by the TweetBot.
    //Exceptions
    // - Throws an IllegalArgumentException if there are no objects.
    public TweetBot(List<Tweet> tweets){
        if (tweets.size() < 1){
            throw new IllegalArgumentException("Must contain at least one Tweet!");
        }
        this.tweets = new ArrayList<>(tweets);
        this.currentIndex = 0;
    }

    //Behaviors: 
    // - Finds the total number of objects in the TweetBot.
    //Returns:
    // - An integer that contains the total number of objects in the TweetBot.
    public int numTweets(){
        return this.tweets.size();
    }

    //Behaviors:
    // - Adds another object to the TweetBot.
    //Parameters:
    // - An object that will be added to the TweetBot.
    public void addTweet(Tweet tweet){
        this.tweets.add(tweet);
    }

    //Behaviors:
    // - Finds the next object and cycles back to the first object once the end is reached.
    // - An index is increased each time the method is called that way when nextTweet is
    // called again, it will be the object after the last object was called.
    //Returns:
    // - Returns the next object from the TweetBot.
    public Tweet nextTweet(){ 
        if (currentIndex == (tweets.size())){
            currentIndex = 0;
        }
        Tweet next = tweets.get(currentIndex);
        currentIndex ++;
        return next;
    }

    //Behaviors:
    // - Removes a specified object out of all of the objects from the Tweetbot.
    // - Adjusts an index so that the 'nextTweet' method works efficiently.
    // - Will not work if there is only one object and that object is being removed.
    //Parameters:
    // - An object from TweetBot that will be removed.
    //Exceptions:
    // - Throws an IllegalStateException if there is only one object and it is being called
    // to be removed.
    public void removeTweet(Tweet tweet){
        if (tweets.size() == 1 && (tweets.contains(tweet))){
            throw new IllegalStateException("That is not a tweet");
        }
        int removeIndex = tweets.indexOf(tweet);
        if (removeIndex != -1) {
            this.tweets.remove(removeIndex);
            if (removeIndex < this.currentIndex) {
                this.currentIndex --;
            } 
        }
    }

    //Behaviors:
    // - Resets the nextTweet order that way the objecs could be called
    // again from the beginning.
    public void reset() {
        this.currentIndex = 0;    
    }
}   