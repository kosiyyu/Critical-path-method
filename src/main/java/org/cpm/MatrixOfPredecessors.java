package org.cpm;

import java.util.ArrayList;
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
            ///for(String a: precedessors)
            //{
              //  System.out.print(a + " " + precedessors[0].length() + " " + precedessors[0]);
            //}
            //System.out.println();
            if (precedessors[0].trim().equalsIgnoreCase("-")) {
                //matrix[index][pos] = -1;
                //System.out.println("stop");
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
                    if(z == 0)
                        System.out.print("  ");
                    char act = (char)(activityTmp + z);
                    System.out.print(act + " ");
                }
                System.out.println();
            }
            for(int j = 0; j < siz; j++)
            {
                if(j == 0)
                {
                    System.out.print(activityId + " " + matrix[i][j] + " ");
                }
                else
                {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
            activityId++;
        }
    }

    public List<Integer> findNoPredecessorActivities(){
        List<Integer> indexOfFirstActivities = new ArrayList<>();
        for(int i = 0; i < matrix.length; i++ )
        {
            int count = 0;
            for(int j = 0; j < matrix[0].length; j++)
            {
                if(matrix[i][j] == 0)
                    count++;
            }
            if(count == matrix[0].length)
                indexOfFirstActivities.add(i);
        }
        return indexOfFirstActivities;
    }

    public List<Integer> findQuantityOfPredecessorActivities(int row){
        List<Integer> quantityOfPredecessorActivities = new ArrayList<>();
        for(int i = 0; i < matrix[0].length; i++ )
        {
            if(matrix[row][i] == 1)
                quantityOfPredecessorActivities.add(i);
        }
        return quantityOfPredecessorActivities;
    }




}
