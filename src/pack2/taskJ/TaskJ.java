package pack2.taskJ;

import java.util.*;
public class TaskJ {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        String[] tmp = in.nextLine().split(" ");
        int n = Integer.parseInt(tmp[0]),
                m = Integer.parseInt(tmp[1]);

        int[][] mat = new int[n + 2][m + 2];
        for(int i = 1; i <= n; i++) {
            String s = in.nextLine();
            for(int j = 1; j <= m; j++)
                mat[i][j] = s.charAt(j - 1);
        }

        boolean badRect = false;
        int firstI = -1,
                firstJ = -1,
                c = 'a';
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                if (mat[i][j] == '#' && c == 'a'){
                    firstI = i;
                    firstJ = j;
                    c = setRectangle(mat, c, i, j);
                }
                else if (mat[i][j] == '#' && c == 'b')
                    c = setRectangle(mat, c, i, j);
                else if (mat[i][j] == '#')
                    badRect = true;
            }
        }

        if (badRect || c == 'a')
            System.out.println("NO");
        else if (c == 'c'){
            System.out.println("YES");
            printMat(mat);
        } else if (cutRectangle(mat, firstI, firstJ)){
            System.out.println("YES");
            printMat(mat);
        } else
            System.out.println("NO");
    }

    public static int setRectangle(int[][] mat, int c, int i, int j){
        int end = j;
        while(mat[i][end] == '#')
            mat[i][end++] = c;

        while(isGoBelow(mat, i, j, end)){
            for(int k = j; k < end; k++)
                mat[i + 1][k] = c;
            i++;
        }

        return ++c;
    }

    public static boolean isFreeSpaceBelow(int[][] mat, int i, int j, int end){
        return Arrays.stream(Arrays.copyOfRange(mat[i + 1], j, end)).allMatch(x -> x == '#');
    }

    public static boolean isNotFreeSpaceToTheLeft(int[][] mat, int i, int j){
        return Arrays.stream(Arrays.copyOfRange(mat[i + 1], 0, j)).anyMatch(x -> x == '#');
    }

    public static boolean isNotFreeSpaceToTheRight(int[][] mat, int i, int end){
        return Arrays.stream(Arrays.copyOfRange(mat[i + 1], end, mat[0].length)).anyMatch(x -> x == '#');
    }

    public static boolean isGoBelow(int[][] mat, int i, int j, int end){
        return isFreeSpaceBelow(mat, i, j, end) && !(isNotFreeSpaceToTheLeft(mat, i, j) && isNotFreeSpaceToTheRight(mat, i, end));
    }

    public static void printMat(int[][] mat){
        for(int i = 1; i < mat.length - 1; i++){
            for(int j = 1; j < mat[0].length - 1; j++)
                System.out.print((char)mat[i][j]);
            System.out.println();
        }
    }

    public static boolean cutRectangle(int[][] mat, int i, int j){
        mat[i][j] = 'b';
        if (mat[i][j + 1] != 'a' && mat[i + 1][j] != 'a')
            return false;

        if ((mat[i][j + 1] == 'a' && mat[i + 1][j] != 'a') || (mat[i][j + 1] != 'a' && mat[i + 1][j] == 'a'))
            return true;

        while(mat[i][++j] == 'a')
            mat[i][j] = 'b';

        return true;
    }
}
