import java.util.Scanner;
import SequenceAlignment.BruteForce;
import SequenceAlignment.Needleman;
import SequenceAlignment.Hirschberg;
/**
 * Author : mostafa
 * Created: 12/5/15
 * Licence: NONE
 */
public class Main {
    public static boolean RUN_BRUTE_FORCE = false;
    public static boolean RUN_NEEDLEMAN = true;
    public static boolean RUN_HIRSCHBERG = true;

    public static void main (String args[]){
        String sequence1 , sequence2;
        String[] results;
        long startTime,endTime;

        switch(args.length){
            case 2:
                sequence1 = args[0];
                sequence2 = args[1];
                break;
            default:
                System.out.println("Invalid number of arguments\nPlease enter 2 valid sequences to proceed.");
                Scanner scanner = new Scanner(System.in);
                sequence1 = scanner.next();
                sequence2 = scanner.next();
                break;
        }

        sequence1 = sequence1.toUpperCase();
        sequence2 = sequence2.toUpperCase();

        System.out.println("---------------------------------");
        System.out.println("Sequence 1 length: "+sequence1.length());
        System.out.println("Sequence 2 length: "+sequence2.length());
        System.out.println("---------------------------------");

        //Trying Needleman
        if(RUN_NEEDLEMAN) {
            Needleman needleman = new Needleman();
            startTime = System.currentTimeMillis();
            results = needleman.align(sequence1, sequence2);
            endTime = System.currentTimeMillis();
            System.out.println("DP Results:");
            printResult(results[0], results[1], needleman.getBestScore(),calcSimilarity(results[0],results[1]), endTime - startTime);
        }

        //Trying Hirschberg
        if(RUN_HIRSCHBERG){
            Hirschberg hirschberg = new Hirschberg();
            startTime = System.currentTimeMillis();
            results = hirschberg.align(sequence1,sequence2);
            endTime = System.currentTimeMillis();
            System.out.println("Hirschberg Results:");
            printResult(results[0], results[1], hirschberg.getScore(results[0],results[1]),
                    calcSimilarity(results[0],results[1]), endTime - startTime);
        }

        //Trying Brute Force
        if(RUN_BRUTE_FORCE) {
            BruteForce bf = new BruteForce();
            startTime = System.currentTimeMillis();
            results = bf.align(sequence1, sequence2);
            endTime = System.currentTimeMillis();
            System.out.println("BF Results:");
            printResult(results[0], results[1], bf.getScore(results[0], results[1]),calcSimilarity(results[0],results[1]), endTime - startTime);
        }
    }

    private static double calcSimilarity(final String sequence1, final String sequence2){
        int matchesNumber = 0;
        assert sequence1.length() == sequence2.length();
        for(int i = 0 ; i < sequence1.length() ; i++){
            if (sequence1.charAt(i) == sequence2.charAt(i))
                matchesNumber++;
        }
        return matchesNumber/ (double) sequence1.length();
    }

    private static void printResult(final String sequence1,final String sequence2,final int score,final double similarity,final long elapsedTime){
        System.out.println("Sequence 1: "+ sequence1);
        System.out.println("Sequence 2: "+ sequence2);
        System.out.println("Score : "+ score);
        System.out.println("Similarity : "+ similarity);
        System.out.println("Elapsed Time: "+ elapsedTime);
        System.out.println("---------------------------------");
    }
}
