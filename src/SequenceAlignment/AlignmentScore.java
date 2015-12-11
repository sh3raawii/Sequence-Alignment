package SequenceAlignment;

/**
 * Author : mostafa
 * Created: 12/5/15
 * Licence: NONE
 */
/* Dynamic Programming at Linear Space
   Space Complexity O(n)
   Time Complexity O(n*m)
   Calculates the Score only
*/
public class AlignmentScore {
    private static final int matchPoint = 1;
    private static final int mismatchPoint = -1;
    private static final int gapPoint = -1;
    private int column1[], column2[], prevColumn[], currentColumn[];
    private int columnSize;
    private int bestScore;

    public AlignmentScore(final String sequence1,final String sequence2) {
        this.bestScore = getBestScore(sequence1,sequence2);
    }

    public int getBestScore() {
        return bestScore;
    }


    public int getBestScore(final String sequence1,final String sequence2){

        this.columnSize = sequence1.length()+1;
        column1 = new int[columnSize];
        column2 = new int[columnSize];

        //filling 1'st column
        for(int i = 1; i< columnSize; i++){
            column1[i] = column1[i-1]+gapPoint;
        }

        prevColumn = column1;
        currentColumn = column2;

        for(int x = 0; x < sequence2.length();x++){
            int topScore, leftScore, diagonalScore;
            currentColumn[0] = prevColumn[0]+gapPoint;

            for(int i = 1; i< columnSize; i++){
                diagonalScore = prevColumn[i-1];
                diagonalScore += sequence1.charAt(i-1) == sequence2.charAt(x) ? matchPoint : mismatchPoint;
                leftScore = prevColumn[i] + gapPoint;
                topScore = currentColumn[i-1] + gapPoint;

                currentColumn[i] = Math.max(diagonalScore,Math.max(leftScore,topScore));
            }

            // update currentColumn and prevColumn (Swapping References)
            int temp[] = prevColumn;
            prevColumn = currentColumn;
            currentColumn = temp;
        }
        // Previous Column Now Points at the Last Column of the Normal DP Matrix
        bestScore = prevColumn[column1.length-1];
        return bestScore;
    }
}
