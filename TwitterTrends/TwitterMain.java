import java.util.*;
import java.io.*;

public class TwitterMain {
    public static void main(String[] args) throws FileNotFoundException {
        
        // Create and print a list of Tweet objects from the gumball folder

    
        List<Tweet> gumballTweets = scrapeTweets(); 
        for (Tweet tweet : gumballTweets) {
            System.out.println(tweet);
            System.out.println();
        }
        
        TweetBot gumbot = new TweetBot(gumballTweets);

        TwitterTrends trends = new TwitterTrends(gumbot);

        gumbot.reset();
        int size = gumbot.numTweets();
        for (int i = 0; i < size; i++) {
            System.out.println(gumbot.nextTweet());
            System.out.println();
        }

        String mostFrequentWord = trends.getMostFrequentWord();
        System.out.println("Most Frequent Word: " + mostFrequentWord);

        int mostLikes = trends.getMostLikes();
        System.out.println("The tweet with the most likes has " + mostLikes 
        + " amount of likes");
        
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // PROVIDED SETUP CODE ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs a new list with the Tweet objects based on the content.
     * found in the gumball folder and tweet_info file.
     *
     * @return the list of tweets
     */
    public static List<Tweet> scrapeTweets() throws FileNotFoundException {
        List<Tweet> tweets = new ArrayList<>();
        Scanner instagramScraper = new Scanner(new File("gumball/tweet_info.txt"));
        while(instagramScraper.hasNext()) {
            String title = instagramScraper.nextLine();
            String caption = "";
            String date = "";
            while (!hasDate(date) && instagramScraper.hasNextLine()) {
                String line = instagramScraper.nextLine();
                if (hasDate(line)) { // end of tweet in file
                    date += line;
                    instagramScraper.nextLine();
                } else {
                    caption += "\n" + line;
                }
            }
            caption = caption.substring(1); // remove starting \n
            tweets.add(new Tweet("gumball/" + title + ".png", caption, date));
        }
        return tweets;
    }

     /**
     * Checks if the provided String line case-insenseitivly contains 
     * one of the twelve months of the year.
     *
     * @param line  The provided line of text to search through
     *
     * @return boolean result for if at least one month is in provided String
     */
    public static boolean hasDate(String line) {
        line = line.toUpperCase();
        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY",
                           "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER",
                           "NOVEMBER", "DECEMBER"};
        for (String month : months) {
            if (line.contains(month)) {
                return true;
            }
        }
        return false;
    }
}