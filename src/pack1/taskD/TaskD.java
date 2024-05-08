package pack1.taskD;

import java.util.*;

public class TaskD {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int[][] chessBoard = new int[10][10];
        for(int i = 1; i <= 8; i++){
            String tmp = in.nextLine();
            for(int j = 1; j <= 8; j++)
                chessBoard[i][j] = tmp.charAt(j - 1);
        }

        for(int i = 1; i <= 8; i++)
            for(int j = 1; j <= 8; j++)
                updateBoard(chessBoard, i, j);

        System.out.println(Arrays.stream(chessBoard)
                .map(x -> Arrays.stream(x).filter(y -> y == '*').count())
                .reduce(Long::sum)
                .orElse(0L).intValue());
    }

    public static void updateBoard(int[][] board, int i, int j){
        if (board[i][j] == 'R' || board[i][j] == 'B'){
            boolean isRook = board[i][j] == 'R';
            int[] detX = isRook ? new int[] { -1, 1, 0, 0 } : new int[] { 1, 1, -1, -1 };
            int[] detY = isRook ? new int[] { 0, 0, 1, -1 } : new int[] { 1, -1, -1, 1 };
            for(int det = 0; det < 4; det++){
                int x = i + detX[det];
                int y = j + detY[det];
                while(board[x][y] == '*' || board[x][y] == '+') {
                    board[x][y] = '+';
                    x += detX[det];
                    y += detY[det];
                }
            }
        }
    }
}

