package pack2.taskH;

import java.util.*;
public class TaskH {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int removedRow = -1,
                removedColumn = -1,
                n = in.nextInt(),
                m = in.nextInt();

        int[][] mat = new int[n][m];
        for(int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                mat[i][j] = in.nextInt();

        int[] max = new int[] {-1, -1, -1},
                nextMax = new int[] {-1, -1, -1};
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                if(mat[i][j] > max[0]){
                    setMax(nextMax, max[0], max[1], max[2]);
                    setMax(max, mat[i][j], i, j);
                } else if (mat[i][j] > nextMax[0])
                    setMax(nextMax, mat[i][j], i, j);

        if (max[1] == nextMax[1])
            removedRow = max[1];
        else if (max[2] == nextMax[2])
            removedColumn = max[2];
        else  if (getMax(mat, nextMax[1], max[2]) > getMax(mat, max[1], nextMax[2])){
            removedRow = max[1];
            removedColumn = nextMax[2];
        } else {
            removedRow = nextMax[1];
            removedColumn = max[2];
        }

        if (removedColumn == -1 || removedRow == -1){
            setMax(max, -1, -1, -1);
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++)
                    if(mat[i][j] > max[0] && i != removedRow && j != removedColumn)
                        setMax(max, mat[i][j], i, j);

            if (removedRow == -1)
                removedRow = max[1];
            else
                removedColumn = max[2];
        }

        System.out.println((removedRow + 1) + " " + (removedColumn + 1));
    }

    public static int getMax(int[][] mat, int removedRow, int removedColumn){
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < mat.length; i++)
            for(int j = 0; j < mat[0].length; j++)
                if (mat[i][j] > max && i != removedRow && j != removedColumn)
                    max = mat[i][j];
        return max;
    }

    public static void setMax(int[] max, int value, int i, int j){
        max[0] = value;
        max[1] = i;
        max[2] = j;
    }
}
