package pack2.taskF;

import java.util.Scanner;
public class TaskF {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++)
            arr[i] = in.nextInt();

        int min = in.nextInt(),
                max = in.nextInt(),
                det = in.nextInt(),
                start = (min / det - (min % det == 0 ? 1 : 0)),
                end = (max / det - (max % det == 0 ? 1 : 0)) - start;

        end = (end >= n ? n - 1 : end);
        start %= n;

        int answer = arr[start];
        for(int i = start; i <= start + end; i++)
            answer = Math.max(answer, i >= n ? arr[i - n] : arr[i]);

        start = n - start;
        start = (start == n ? 0 : start);

        for(int i = start; i >= start - end; i--)
            answer = Math.max(answer, i < 0 ? arr[i + n] : arr[i]);

        System.out.println(answer);
    }
}
