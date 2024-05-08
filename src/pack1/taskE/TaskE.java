package pack1.taskE;

import java.util.Scanner;

public class TaskE {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(),
                k = in.nextInt(),
                d = in.nextInt();
        long ost = n * 10L % k;

        if (ost == 0)
            System.out.println(new StringBuilder().append(n * 10L).repeat("0", d - 1));
        else if (k - ost < 10)
            System.out.println(new StringBuilder().append(n * 10L + (k - ost)).repeat("0", d - 1));
        else
            System.out.println(-1);
    }
}