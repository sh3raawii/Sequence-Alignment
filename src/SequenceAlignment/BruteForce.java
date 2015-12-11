package SequenceAlignment;
/**
 * Author : mostafa mahmoud
 * Created: 10/26/15
 * Licence: NONE
 */

public class BruteForce {
    public BruteForce() {
    }

    public static final int matchPoint = 1;     //2 matching  characters
    public static final int mismatchPoint = -1; //2 different characters
    public static final int gapPoint = -1;      //A gap and a character

    public String[] align(String subsequence1, String subsequence2){
        if(subsequence1.length() == 0 || subsequence2.length() == 0){
            //Fill the empty sub-sequence with Gaps
            while (subsequence1.length() < subsequence2.length()) {
                subsequence1 += "_";
            }
            while (subsequence2.length() < subsequence1.length()) {
                subsequence2 += "_";
            }
            return new String[]{subsequence1,subsequence2};
        }
        else {
            //No gap insertion
            String[] subsequencesNoGap = align(subsequence1.substring(1),subsequence2.substring(1));
            int noGapScore = getScore(subsequencesNoGap[0],subsequencesNoGap[1]);

            if (noGapScore == subsequencesNoGap[0].length()*matchPoint)
                return  new String[]{subsequence1.charAt(0)+subsequencesNoGap[0],subsequence2.charAt(0)+subsequencesNoGap[1]};

            //insert Gap in first sequence
            String[] subsequence1Gap = align(subsequence1,subsequence2.substring(1));
            int gapScore1 = getScore(subsequence1Gap[0],subsequence1Gap[1]);

            //insert Gap in second sequence
            String[] subsequence2Gap = align(subsequence1.substring(1),subsequence2);
            int gapScore2 = getScore(subsequence2Gap[0],subsequence2Gap[1]);


            if (subsequence1.charAt(0) == subsequence2.charAt(0)) noGapScore += matchPoint;
            else noGapScore += mismatchPoint;

            gapScore1 += gapPoint;
            gapScore2 += gapPoint;

            if(noGapScore > gapScore1 && noGapScore > gapScore2) {
                return new String[]{subsequence1.charAt(0) + subsequencesNoGap[0], subsequence2.charAt(0) + subsequencesNoGap[1]};
            }
            else if(gapScore1 > gapScore2){
                return new String[]{"_"+subsequence1Gap[0],subsequence2.charAt(0)+subsequence1Gap[1]};
            }
            else {
                return new String[]{subsequence1.charAt(0) + subsequence2Gap[0], "_" + subsequence2Gap[1]};
            }
        }
    }

    public int getScore(final String sequence1,final String sequence2){
        int score = 0;
        for (int i = 0 ; i < sequence1.length() ; i++) { // sequence1 & sequence2 have the same length
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