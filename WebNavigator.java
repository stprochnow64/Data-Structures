//Annie Flora and Sophia Prochnow 
package web_nav;

// TODO: Choose imports based on DS choice
import java.util.Scanner;
import java.util.*;

public class WebNavigator {

    // Fields
    private String current; // Tracks currently visited site
    private int currentIndex = 0;
    
    private Stack<String> items;
    private Stack<String> poppedItems;

    // Constructor
    WebNavigator () {
        // TODO: Initialize your new fields in constructor
        current = null;
        items = new Stack<String>();
        poppedItems = new Stack<String>();
    }
    
    // Methods
    // [!] YOU DO NOT HAVE TO MODIFY THIS METHOD FOR YOUR SOLUTION
    public boolean getNextUserCommand (Scanner input) {
        String command = input.nextLine();
        String[] parsedCommand = command.split(" ");
        
        // Switch on the command (issued first in input line)
        switch(parsedCommand[0]) {
        case "exit":
            System.out.println("Goodbye!");
            return false;
        case "visit":
            visit(parsedCommand[1]);
            break;
        case "back":
            back();
            break;
        case "forward":
            forw();
            break;
        default:
            System.out.println("[X] Invalid command, try again");
        }
        
        System.out.println("Currently Visiting: " + current);
        
        return true;
    }
    
    /*
     *  Visits the current site, clears the forward history,
     *  and records the visited site in the back history
     */
    public void visit (String site) {    
        while (poppedItems.size() != 0) {
            poppedItems.pop();   
        }
        items.push(site);
        if (items.size() > 1) {
            currentIndex++;
        }
        current = items.get(items.size() - 1);
    }
    
    /*
     *  Changes the current site to the one that was last
     *  visited in the order on which visit was called on it
     */
    public void back () {
        // TODO
        // ???
        if (currentIndex != 0) {
            String temp = items.pop();
            currentIndex--;
            current = items.get(currentIndex);
            poppedItems.push(temp);
        } else {
            return;
        }          
    }
    
    public void forw () {
        if (currentIndex != items.size() && poppedItems.size() != 0) {
            current = poppedItems.pop();
            currentIndex++; 
            items.push(current);
        }else {
            return;
        }
    }
    
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        WebNavigator navi = new WebNavigator();
        
        System.out.println("Welcome to ForneyFox, enter a command from your ForneyFox user manual!");
        while (navi.getNextUserCommand(input)) {}
        System.out.println("Goodbye!");
    }

}