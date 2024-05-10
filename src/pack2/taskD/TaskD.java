package pack2.taskD;

import java.util.Arrays;
import java.util.Scanner;
public class TaskD {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] dx = new int[] { 1, -1, 0, 0 },
                dy = new int[] { 0, 0, 1, -1 };

        int[][] mat = new int[10][10];
        for(int k = 0; k < n; k++){
            int i = in.nextInt(),
                    j = in.nextInt();

            for(int d = 0; d < dx.length; d++)
                mat[i + dx[d]][j + dy[d]] -= 1;
            mat[i][j] += 4;
        }

        System.out.println(Arrays.stream(mat)
                .mapToInt(x -> Arrays.stream(x)
                        .filter(y -> y > 0)
                        .reduce(Integer::sum)
                        .orElse(0))
                .reduce(Integer::sum)
                .orElse(0));
    }
}
