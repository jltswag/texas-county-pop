import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
/**
 *This program processes the population of the counties in the state of Texas.
 * 
 * @author (Julia Tran) 
 * @version (10/26/2021)
 */
public class Population1
{
    // instance variables - replace the examplpercen;e below with your own
    private Scanner sc;
    private Scanner scan;
    private String[] counties;//stores the name of the counties
    private int[] population;//stroes the population for each county
    private int[] numCon;//number of counties for each leading digit
    private double[] percen;//percent of population for each leading digit
    private double[] digitPop;//stores population for each leading digit
    private Random rand;
    private Canvas graphCanvas;

    /**
     * Constructor for objects of class Population
     */
    public Population1()throws FileNotFoundException
    {

        //Initialize the arrays
        counties = new String[254];
        population = new int[254];
        numCon = new int[9];
        percen = new double[9];
        digitPop = new double[9];
        int digit = 0;
        boolean b = true;
        String s = "";
        int k = 0;
        int total = 0;
        rand = new Random();
        sc = new Scanner(System.in);
        graphCanvas = new Canvas("Bar Graph of Texas County Population", 500,500);
        graphCanvas.setForegroundColor(Color.BLACK);
        graphCanvas.fillRectangle(0,0,500,500);
        population();
        while (b)
        {
            System.out.println("Would you like to look at Texas Population? Enter Yes/No.");

            s = sc.nextLine();
            if (s.equals("Yes"))
            {
                System.out.println("Please choose one of the following.");
                System.out.println("1: Prints the data from the file.");
                System.out.println("2: Finds the total population of Texas.");
                System.out.println("3: Finds a random county and displays its name and population.");
                System.out.println("4: Find counties population with a leading digit,entered by the user.");
                System.out.println("5: Find total number of counties/percentage of population with each leading digit formatted");
                s = sc.nextLine();
                k = Integer.parseInt(s);
                if (k >= 1 && k <= 5)
                {
                    if (k == 1)
                    {
                        population();
                    }
                    else if (k == 2)
                    {
                        findTotal();
                    }
                    else if (k == 3)
                    {
                        randomCounty();
                    }
                    else if (k == 4)
                    {
                        digitCounty();
                    }
                    else if (k == 5)
                    {
                        analysis();
                    }
                }
                else
                {
                    System.out.println("This is an incorrect response");
                    System.out.println("Please pick a number between 1-5");
                }
            }
            else //s = "No"
            {
                b = false;
                System.out.println("Goodbye!");
                System.exit(0);
            }

        }
        System.out.println("I am done"); // can put stop here

    }
    //this method reads the file and populates the arrays
    public void population()throws FileNotFoundException
    {
        File text = new File("TexasCountyPopulation.txt");
        scan = new Scanner(text);
        String line = "";
        String[] s = new String[2];
        int i = 0;
        while (scan.hasNextLine())
        {
            line = scan.nextLine();
            System.out.println(line);
            s = line.split("\t");

            counties[i] = s[0];
            population[i] = Integer.parseInt(s[1]);
            i = i + 1;
        }
        //System.out.println(counties); not necessary, just a checkpoint
    }

    public void findTotal()throws FileNotFoundException
    {
        int total = 0;
        for (int i = 0; i < 254; i++) 
        {
            total = population[i] + total;
        }
        System.out.println("The total population of Texas is --> " + total);
    }
    //k = rand.nextInt(50) gives numbers 0-49
    //k = rand.nextInt(254)
    //System.out.println, counties[k], population[k
    public void randomCounty()throws FileNotFoundException
    {
        int number = rand.nextInt(254);
        System.out.println(counties[number] + ", the population is " + population[number]);
    }
    //turn to string, s = "" + number 
    //digit = s.substring(0,1);
    //d = Integer.parseInt(digit); d-1
    public void digitCounty()throws FileNotFoundException
    {
        System.out.println("Enter a digit 1-9.");
        String reply = "";
        reply = sc.nextLine();
        int digit = Integer.parseInt(reply);
        if (digit <= 9 && digit >= 1)
        {
            for (int i = 0; i < 254; i++)
            {
                String s1 = "" + population[i];
                String digit1 = s1.substring(0,1);
                int d = Integer.parseInt(digit1);
                numCon[d - 1] = numCon[d - 1] + 1;
                digitPop[d - 1] = digitPop[d - 1] + population[i];
                
                //System.out.println(d);
                if (d == digit)
                {
                    System.out.println(counties[i] + " - - - " + population[i]);
                }
                // else
                // {
                // break;
                // }
            }
        }
        else
        {
            System.out.println("The digit must be in the range of 1-9");
            digitCounty();
        }
    }

    public void analysis()throws FileNotFoundException
    {
        for (int i = 0; i < 254; i++)
        {
            String s1 = "" + population[i]; 
            String digit1 = s1.substring(0,1);
            int d1 = Integer.parseInt(digit1);
            numCon[d1 - 1] = numCon[d1 - 1] + 1;
            digitPop[d1 - 1] = digitPop[d1 - 1] + population[i];
        }
        System.out.println("Digit\tCounty #\t   Percent");
        int total = 25145561; 
        for (int i = 0; i < 9; i++)
        {
            double percentage = digitPop[i];
            percentage = (double)Math.round(100*percentage/total*100)/100;
            System.out.println("" + (i+1) + "\t    " + numCon[i] + "\t\t     " + percentage);
        }
        graphCanvas.setVisible(true);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x axis text
        graphCanvas.drawString("Leading Digits", 250, 450);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y axis text
        graphCanvas.drawString("Population", 5, 250);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 1
        graphCanvas.drawString("1", 135, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 2
        graphCanvas.drawString("2", 180, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 3
        graphCanvas.drawString("3", 215, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 4
        graphCanvas.drawString("4", 250, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 5
        graphCanvas.drawString("5", 285, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 6
        graphCanvas.drawString("6", 320, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 7
        graphCanvas.drawString("7", 355, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 8
        graphCanvas.drawString("8", 390, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x label 9
        graphCanvas.drawString("9", 425, 425);
        
        graphCanvas.setForegroundColor(Color.WHITE); //x axis line
        graphCanvas.drawLine(100,400,450,400);
        
        graphCanvas.setForegroundColor(Color.WHITE); // y axis line
        graphCanvas.drawLine(100,400,100,50);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 10
        graphCanvas.drawString("10", 75, 365);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 20
        graphCanvas.drawString("20", 75, 330);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 30
        graphCanvas.drawString("30", 75, 295);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 40
        graphCanvas.drawString("40", 75, 260);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 50
        graphCanvas.drawString("50", 75, 225);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 60
        graphCanvas.drawString("60", 75, 190);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 70
        graphCanvas.drawString("70", 75, 155);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 80
        graphCanvas.drawString("80", 75, 120);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 90
        graphCanvas.drawString("90", 75, 85);
        
        graphCanvas.setForegroundColor(Color.WHITE); //y label 100
        graphCanvas.drawString("100", 75, 50);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 1
        graphCanvas.fillRectangle(120, 120, 30, 280);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 2
        graphCanvas.fillRectangle(170, 270, 27, 130);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 3
        graphCanvas.fillRectangle(210, 265, 24, 135);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 4
        graphCanvas.fillRectangle(245, 300, 21, 100);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 5
        graphCanvas.fillRectangle(280, 350, 21, 50);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 6
        graphCanvas.fillRectangle(315, 350, 21, 50);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 7
        graphCanvas.fillRectangle(350, 340, 21, 60);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 8
        graphCanvas.fillRectangle(385, 365, 21, 35);
        
        graphCanvas.setForegroundColor(Color.BLUE); //l.d.box 9
        graphCanvas.fillRectangle(420, 375, 21, 25);
    }
}