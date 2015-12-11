package SequenceAlignment;

/**
 * Author : mostafa
 * Created: 12/10/15
 * Licence: NONE
 */
public class Hirschberg {
    private static final int matchPoint = 1;
    private static final int mismatchPoint = -1;
    private static final int gapPoint = -1;

    public Hirschberg() {
    }

    public String[] align(final String sequence1,final String sequence2){
        StringBuilder alignedSequence1;
        StringBuilder alignedSequence2;

        if (sequence1.length() == 0) {
            alignedSequence1 = new StringBuilder("");
            alignedSequence2 = new StringBuilder("");
            for (int i = 0; i < sequence2.length(); i++) {
                alignedSequence1.append("_");
                alignedSequence2.append(sequence2.charAt(i));
            }
        }
        else if (sequence2.length() == 0) {
            alignedSequence1 = new StringBuilder("");
            alignedSequence2 = new StringBuilder("");
            for (int i = 0; i < sequence1.length(); i++) {
                alignedSequence1.append(sequence1.charAt(i));
                alignedSequence2.append("_");
            }
        }
        else if (sequence1.length() ==1 || sequence2.length() ==1){
            String[] result = new Needleman().align(sequence1,sequence2);
            alignedSequence1 = new StringBuilder(result[0]);
            alignedSequence2 = new StringBuilder(result[1]);
        }
        else{
            int sequence1MidPoint = sequence1.length()/2;
            int []scoreL = nwLastRow(sequence1.substring(0,sequence1MidPoint),sequence2);
            int []scoreR = nwLastRow(new StringBuilder(sequence1.substring(sequence1MidPoint,sequence1.length()))
                    .reverse().toString(),new StringBuilder(sequence2).reverse().toString());
            int sequence2MidPoint = partition(scoreL,scoreR);

            String[] result1 = align(sequence1.substring(0,sequence1MidPoint),sequence2.substring(0,sequence2MidPoint));
            String[] result2 = align(sequence1.substring(sequence1MidPoint),sequence2.substring(sequence2MidPoint));

            alignedSequence1 = new StringBuilder(result1[0]+result2[0]);
            alignedSequence2 = new StringBuilder(result1[1]+result2[1]);
        }
        return new String[]{alignedSequence1.toString(),alignedSequence2.toString()};
    }

    private int[] nwLastRow(final String sequence1,final String sequence2){
        int row1[], row2[], prevRow[], currentRow[];
        int rowSize = sequence2.length()+1;
        row1 = new int[rowSize];
        row2 = new int[rowSize];

        //filling 1'st row
        for(int i = 1; i< rowSize; i++){
            row1[i] = row1[i-1]+gapPoint;
        }

        prevRow = row1;
        currentRow = row2;

        for(int x = 0; x < sequence1.length();x++){
            int topScore, leftScore, diagonalScore;
            currentRow[0] = prevRow[0]+gapPoint;

            for(int i = 1; i< rowSize; i++){

                diagonalScore = prevRow[i-1];
                diagonalScore += sequence1.charAt(x) == sequence2.charAt(i-1) ? matchPoint : mismatchPoint;
                leftScore = prevRow[i] + gapPoint;
                topScore = currentRow[i-1] + gapPoint;

                currentRow[i] = Math.max(diagonalScore,Math.max(leftScore,topScore));
            }

            // update currentRow and prevRow (Swapping References)
            int temp[] = prevRow;
            prevRow = currentRow;
            currentRow = temp;
        }
        // Previous Row Now Points at the Last Row of the Normal DP Matrix
        return prevRow;
    }

    private int partition(int[] scoreL, int[] scoreR){
        assert scoreL.length == scoreR.length;
        int max = Integer.MIN_VALUE,index = 0;
        for(int i = 0; i < scoreL.length ;i++){
            int sum = (scoreL[i]+scoreR[scoreL.length-i-1]);
            if(max < sum){
                max = sum;
                index = i;
            }
        }
        return index;
    }

    public int getScore(final String sequence1,final String sequence2){
        assert sequence1.length() == sequence2.length();
        int score = 0;
        for (int i = 0 ; i < sequence1.length() ; i++) {
            if (sequence1.charAt(i) == sequence2.charAt(i))
                score += matchPoint;
            else if (sequence1.charAt(i) == '_' || sequence2.charAt(i) == '_')
                score += gapPoint;
            else
                score += mismatchPoint;
        }
        return score;
    }
}
