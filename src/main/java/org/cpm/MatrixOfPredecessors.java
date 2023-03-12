package org.cpm;

import java.util.List;


public class MatrixOfPredecessors {
    private int matrix[][];
    private int siz;

    public MatrixOfPredecessors(List<Activity> activities) {
        this.siz = activities.size();
        //String tmpId = "A";
        int pos = 0, index = 0;

        this.matrix = new int[siz][siz];
        for(int i = 0; i < siz; i++ )
        {
            for(int j = 0; j < siz; j++)
            {
                matrix[i][j] = 0;
            }
        }
        for (Activity activity : activities) {
            String precedessors[] = activity.getPredecessor().split(",");
            for(String a: precedessors)
            {
                System.out.print(a + " " + precedessors[0].length() + " " + precedessors[0]);
            }
            System.out.println();
            if (precedessors[0].equals('-')) {
                //matrix[index][pos] = -1;
                System.out.println("stop");
                index++;
                continue;
            }
            for(int i = 0; i < precedessors.length; i++)
            {
                pos = (int)precedessors[i].charAt(0) - (int)'A';
                matrix[index][pos] = 1;
            }
            index++;
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void printMatrix() {
        char activityId = 'A';
        char activityTmp  = 'A';
        for(int i = 0; i < siz; i++)
        {
            if(i == 0)
            {
                for ( int z = 0; z < siz; z++) {
                    char act = (char)(activityTmp + z);
                    System.out.print(act + " ");
                }
                System.out.println();
            }
            for(int j = 0; j < siz; j++)
            {
                if(j == 0)
                {
                    System.out.print(activityId + " " + matrix[i][j]);
                }
                else
                {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            activityId++;
        }
    }
}
