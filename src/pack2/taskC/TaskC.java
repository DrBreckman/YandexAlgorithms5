package pack2.taskC;

import java.util.Scanner;
public class TaskC {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int n = in.nextInt(),
                max = in.nextInt(),
                sum = max,
                answer;
        for(int i = 1; i < n; i++){
            int l = in.nextInt();
            sum += l;
            max = Math.max(max, l);
        }
        answer = 2 * max - sum;
        System.out.println(answer > 0 ? answer : sum);
    }
}
