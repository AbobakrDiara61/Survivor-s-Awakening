import java.util.Random;
import java.util.Scanner;
import java.io.*;
public class Section {
    public static void section(String[] args) {
        Random rand = new Random();
        
        int RandNum = rand.nextInt(11);
        Scanner input = new Scanner(System.in);
        int enterdNum = input.nextInt();
        if(enterdNum == RandNum) {
            System.out.println("cong.");
        } else if(enterdNum < RandNum) {
            System.out.println("Guess Larger one");
        } else {
            System.out.println("Guess Smaller one");
        }
        try {
            PrintWriter output = new PrintWriter(new FileWriter("GuessReport.txt"));
            System.out.println("enter your username");
            String username;
            username = input.nextLine();
            output.println(username);
            input.close();
            output.close();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
