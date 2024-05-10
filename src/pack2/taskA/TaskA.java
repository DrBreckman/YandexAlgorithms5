package pack2.taskA;

import java.util.Scanner;
public class TaskA {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int k = in.nextInt(),
                leftX = in.nextInt(),
                leftY = in.nextInt(),
                rightX = leftX ,
                rightY = leftY;

        for(int i = 1; i < k; i++){
            int x = in.nextInt(),
                    y = in.nextInt();
            leftX = Math.min(leftX, x);
            leftY = Math.min(leftY, y);
            rightX = Math.max(rightX, x);
            rightY = Math.max(rightY, y);
        }
        System.out.printf("%d %d %d %d", leftX, leftY, rightX, rightY);
    }
}