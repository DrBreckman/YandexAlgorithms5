package pack2.taskB;

import java.util.Scanner;
public class TaskB {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(),
                k = in.nextInt(),
                max = 0;

        int[] prices = new int[n];
        if (n > 1){
            prices[0] = in.nextInt();
            int buy = 0,
                    nextBuy = 1;
            for(int i = 1; i < n; i++){
                prices[i] = in.nextInt();
                if (i - buy > k){
                    buy = nextBuy;
                    nextBuy = buy + 1;
                }

                if (prices[i] <= prices[buy]){
                    buy = i;
                    nextBuy = buy + 1;
                } else {
                    max = Math.max(prices[i] - prices[buy], max);
                    nextBuy = prices[i] <= prices[nextBuy] ? i : nextBuy;
                }
            }
        }
        System.out.println(max);
    }
}
