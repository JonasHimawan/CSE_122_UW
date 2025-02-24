//Jonas Himawan
//2/6/2025
//CSE 122
//P1: Music Playlist
//TA: Chaafen Raees and Afifah Kashif
//The MusicPlaylist class prompts the user with a simple music playlist application.
//It allows the user to add songs, play songs, view play history, clear history, 
//and delete specific number of songs either from the begging or end of the history. 
import java.util.*;
import java.io.*;

public class MusicPlaylist {

    public static void main(String[] args) {
        Stack <String> history = new Stack <String> ();
        Queue <String> playlist = new LinkedList <String> ();
        //auxQueue stands for my auxiliary queue.
        Queue <String> auxQueue = new LinkedList <String> ();
        Scanner console = new Scanner(System.in);
        String choice = "";
        
        System.out.println("Welcome to the CSE 122 Music Playlist!");

        while (!choice.equalsIgnoreCase("Q")) {
            //The user's options throughout this playlist application.
            System.out.println("(A) Add song");
            System.out.println("(P) Play song");
            System.out.println("(Pr) Print history");
            System.out.println("(C) Clear history");
            System.out.println("(D) Delete from history");
            System.out.println("(Q) Quit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = console.nextLine();

            //The user's choice will either be A, P, Pr, C, D, or q
            //Lower and upper case both work.
            if (choice.equalsIgnoreCase("A")) {
                addSong(playlist, console);

            } else if (choice.equalsIgnoreCase("P")) {
                playSong(playlist, history);

            } else if (choice.equalsIgnoreCase("PR")) {
                printHist(history, auxQueue);

            } else if (choice.equalsIgnoreCase("C")) {
                clearHist(history);

            } else if (choice.equalsIgnoreCase("D")) {
                delHist(history, auxQueue, console);

            } 
        }
    }

    //Behaviors:
    // - Asks the user to enter a song name. 
    // - Adds the entered song to the end of the playlist queue. 
    // - Confirms successful addition by displaying message. 
    //Parameters:
    // - playlist: The queue representing the playlist where the song will be added. 
    // - console: The Scanner object used to take user input for the song name. 
    public static void addSong(Queue <String> playlist, Scanner console ) {
        //Asks user for the song name.
        System.out.print("Enter song name: ");
        String songName = console.nextLine();
        playlist.add(songName);
        
        //Message when user successfully adds song to the playlist.
        System.out.println("Successfully added " + songName);
        System.out.println();
    }

    //Behaviors:
    // - Removes and plays the first song from the playlist queue.
    // - Adds the played song to the history stack. 
    // - Displays the currently playing song.
    //Parameters:
    // - playlist: The queue representing the playlist from where the songs are played.
    // - history: The stack storing the history of played songs.
    //Exceptions:
    // - IllegalStateException if the playlist is empty when user attempts to play a song.
    public static void playSong(Queue <String> playlist, Stack <String> history) {
        if (playlist.isEmpty() == true){
            throw new IllegalStateException("The playlist cannot be empty!");
        }
        //Removes the first song in the playlist queue.
        //Adds it to end of the history stack.
        String songPlaying = playlist.remove();
        history.push(songPlaying);

        System.out.println("Playing song: " + songPlaying);
        System.out.println();
    }

    //Behaviors:
    // - Transfers the history stack to an auxiliary queue to preserve order. 
    // - Iterates through the auxiliary queue to display previously played songs. 
    // - Restores the history stack to its original state after printing. 
    //Parameters:
    // - history: The stack storing the history of played songs.
    // - auxQueue: An auxiliary queue used to temporarily store history. 
    //Exceptions:
    // - IllegalStateException if the history stack is empty when user attempts to print history.
    public static void printHist(Stack <String> history, Queue <String> auxQueue) {
        //User cannot print the history if the history is empty, which means there hasn't
        //been a song played.
        if (history.isEmpty() == true){
            throw new IllegalStateException("The history cannot be empty!");
        }

        int historySize = history.size();
        //Transfers the history stack to the auxiliary queue.
        //That way history is printed in the correct order.
        s2q(history, auxQueue);
        // This loop goes through the queue, printing each element
        // while temporarily removing and re-adding it to maintain the original order.
        for (int i = 0 ; i < auxQueue.size(); i++){
            String prevPlayed = auxQueue.remove();
            System.out.println("    " + prevPlayed);
            auxQueue.add(prevPlayed);
        }
        //Transfers to auxiliary queue back to the history stack.
        //Making the original history reversed.
        q2s(auxQueue, history); 

        //Reverses the reversed history so it back to normal again.
        s2q(history, auxQueue);
        q2s(auxQueue, history); 
        
        System.out.println();
    }

    //Behaviors:
    // - Removes all songs from the history stack.
    // - Ensures the history is empty after execution.
    //Parameters:
    // - history: The stack storing the history of played songs.
    public static void clearHist(Stack <String> history) {
        //Removes all of the history until the history stack is empty.
        while (history.size() > 0){
            history.pop();
        }
    }

    //Behaviors:
    // - Prompts the user to enter the number of songs to delete.
    // - If the number is positive, deletes from the most recent history (top of the stack).
    // - If the number is negative, deletes from the oldest history (bottom of the stack) by reversing the order temporarily.
    // - Restores the remaining history to its original order after deletion.
    //Parameters:
    // - history: The stack storing the history of played songs.
    // - auxQueue: An auxiliary queue used for reordering when deleting from the oldest history.
    // - console: The Scanner object used to take user input for the number of songs to delete.
    //Exceptions:
    // - IllegalArgumentException if the absolute value of the entered number is greater 
    // than the size of the history stack.
    public static void delHist(Stack <String> history, Queue <String> auxQueue, Scanner console ) {
        System.out.println("A positive number will delete from recent history.");
        System.out.println("A negative number will delete from the beginning of history.");
        System.out.print("Enter number of songs to delete: ");
        
        //User can only enter in a number thats absolute value is within the history size.
        //User is allowed to enter a number that's absolute value is the history size.
        //User can enter 0, but nothing will be deleted.
        //User cannot enter in a number whose absolute value is greater than the history.
        int delNum = Integer.parseInt(console.nextLine());
        if ((Math.abs(delNum)) > history.size() ){
            throw new IllegalArgumentException ("Your number is bigger than the history!");
        }

        //Deletes the song from recent history, in the history stack. If user input is positive.
        //No need to reverse because 'pop' from a stack automatically removes the most recent.
        if (delNum > 0){
            for (int i = 0; i < delNum; i++){
                history.pop();
            }

        //Deletes the song from beginning of history, if the user input is negative.
        //Needs to reverse the history stack. 
        //That way the beginning of the history can be removed.
        } else if (delNum < 0){
            delNum = delNum * -1;
            //Reversing the history stack by transferring it to the auxiliary queue
            //and transferring it back to the history stack.
            s2q(history, auxQueue);
            q2s(auxQueue, history);
            for (int i = 0; i < delNum; i++){
                history.pop();
            }
            //Reverses the reversed history, so it's back to its original.
            s2q(history, auxQueue);
            q2s(auxQueue, history);
        }
    }

    //Behaviors:
    // - Helper method that removes elements from the stack one by one and adds them to the queue.
    // - The last element pushed onto the stack becomes the last element in the queue.
    //Parameters:
    // - s: The stack containing elements to be transferred.
    // - q: The queue where the elements will be stored.
    public static void s2q(Stack<String> s, Queue<String> q) { 
        while (!s.isEmpty()) { 
            q.add(s.pop()); 
        }
    }

    //Behaviors:
    // - Helper method that removes elements from the queue one by one and pushes 
    // them onto the stack.
    // - The first element in the queue becomes the last element in the stack.
    //Parameters:
    // - q: The queue containing elements to be transferred.
    // - s: The stack where the elements will be stored.
    public static void q2s(Queue<String> q, Stack<String> s) { 
        while (!q.isEmpty()) { 
            s.push(q.remove()); 
        }
    }
}