import SequenceAlignment.Hirschberg;
import SequenceAlignment.Needleman;

/**
 * Author : mostafa
 * Created: 12/11/15
 * Licence: NONE
 */
public class Test {
    private static final int MAX_SEQUENCE_LENGTH = 50;
    private static final int ENGLISH_ALPHABET_SIZE = 26;
    private Needleman needleman;
    private Hirschberg hirschberg;

    public Test(){
        needleman = new Needleman();
        hirschberg = new Hirschberg();
    }

    public static void main (String args[]){
        Test test = new Test();
        test.start();
    }

    public void start(){
        int counter = 0;
        while (true){
            // Refresh every 100 Sequence
            if(counter %100 == 0){
            System.out.print("\r" + counter + " Sequences have been tested so far.");
            }

            String[] sequences = generateRandomPairOfSequences();
            String[] needlemanResults = needleman.align(sequences[0],sequences[1]);
            String[] hirschbergResults = hirschberg.align(sequences[0],sequences[1]);
            int needlemanScore = needleman.getBestScore();
            int hirschbergScore = hirschberg.getScore(hirschbergResults[0],hirschbergResults[1]);
            if (needlemanScore != hirschbergScore){
                System.out.println("MISMATCH FOUND !");
                System.out.println("Needleman Score: " + needlemanScore);
                System.out.println("Sequence1 : "+needlemanResults[0]);
                System.out.println("Sequence2 : "+needlemanResults[1]);
                System.out.println("Hirschberg Score: " + hirschbergScore);
                System.out.println("Sequence1 : "+hirschbergResults[0]);
                System.out.println("Sequence1 : "+hirschbergResults[1]);
                break;
            }
            else counter++;
        }
    }

    private String[] generateRandomPairOfSequences(){
        return new String[] {generateRandomSequence(),generateRandomSequence()};
    }

    private String generateRandomSequence(){
        StringBuilder sequence = new StringBuilder();
        int sequenceLength = (int)(MAX_SEQUENCE_LENGTH*Math.random());
        for(int i = 0; i < MAX_SEQUENCE_LENGTH; i++){
            sequence.append(((int)(Math.random()*ENGLISH_ALPHABET_SIZE) + 'a'));
        }
        return sequence.toString();
    }
}
