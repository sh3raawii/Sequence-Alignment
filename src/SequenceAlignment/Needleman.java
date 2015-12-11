package SequenceAlignment;
/**
 * Author : mostafa
 * Created: 11/8/15
 * Licence: NONE
 */
public class Needleman {
    private static final int matchPoint = 1;
    private static final int mismatchPoint = -1;
    private static final int gapPoint = -1;
    private Cell[][] scoringMatrix;
    private StringBuilder alignedSequence1;
    private StringBuilder alignedSequence2;
    private int bestScore;

    public enum Direction{
     LEFT,TOP,DIAGONAL
    }

    public int getBestScore(){
        return this.bestScore;
    }

    public String[] align(final String sequence1,final String sequence2){
        // create scoring matrix
        scoringMatrix  = new Cell[sequence1.length()+1][sequence2.length()+1];
        scoringMatrix[0][0] = new Cell(0);

        //filling row of index 0
        for(int i = 1; i< scoringMatrix[0].length; i++){
            scoringMatrix[0][i] = new Cell();
            scoringMatrix[0][i].value = scoringMatrix[0][i-1].value+gapPoint;
            scoringMatrix[0][i].direction = Direction.LEFT;
        }

        //filling column of index 0
        for(int i = 1; i< scoringMatrix.length; i++){
            scoringMatrix[i][0] =new Cell();
            scoringMatrix[i][0].value = scoringMatrix[i-1][0].value+gapPoint;
            scoringMatrix[i][0].direction = Direction.TOP;
        }

        int diagonalScore,leftScore,topScore;
        //filling the matrix
        for(int x = 1; x< scoringMatrix.length;x++){
            for(int y = 1; y< scoringMatrix[0].length;y++){
                scoringMatrix[x][y] = new Cell();

                // Calculate Scores
                diagonalScore = scoringMatrix[x-1][y-1].value;
                diagonalScore += sequence1.charAt(x-1) == sequence2.charAt(y-1) ? matchPoint : mismatchPoint;
                leftScore = scoringMatrix[x][y-1].value + gapPoint;
                topScore = scoringMatrix[x-1][y].value + gapPoint;

                //Set Value and direction based on Scores
                scoringMatrix[x][y].value = Math.max(diagonalScore,Math.max(leftScore,topScore));
                if(scoringMatrix[x][y].value == diagonalScore) scoringMatrix[x][y].direction = Direction.DIAGONAL;
                else if(scoringMatrix[x][y].value == leftScore) scoringMatrix[x][y].direction = Direction.LEFT;
                else scoringMatrix[x][y].direction = Direction.TOP;
            }
        }

        bestScore = scoringMatrix[scoringMatrix.length-1][scoringMatrix[0].length-1].value;

        //Tracing / Extracting Aligned Sequences
        //We begin with the most right bottom Cell
        // and iterate backwards by Directions to reach the most left top Cell
        int x = scoringMatrix.length-1,y = scoringMatrix[0].length-1;
        alignedSequence1 = new StringBuilder();
        alignedSequence2 = new StringBuilder();
        while(x!=0 || y!=0){
            switch (scoringMatrix[x][y].direction){
                case DIAGONAL:
                    alignedSequence1.insert(0,sequence1.charAt(--x));
                    alignedSequence2.insert(0,sequence2.charAt(--y));
                    break;
                case LEFT:
                    alignedSequence1.insert(0,"_");
                    alignedSequence2.insert(0,sequence2.charAt(--y));
                    break;
                case TOP:
                    alignedSequence1.insert(0,sequence1.charAt(--x));
                    alignedSequence2.insert(0,"_");
                    break;
            }
        }
        return new String[]{alignedSequence1.toString(),alignedSequence2.toString()};
    }
    private class Cell{
        Cell(){
            value = 0;
        }
        Cell(int value){
            this.value = value;
        }
        public Needleman.Direction direction ;
        public int value;
    }
}

