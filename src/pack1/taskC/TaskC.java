package pack1.taskC;

import java.util.*;

public class TaskC {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        long spaces = 0;
        for(int i = 0; i < n; i++){
            int a = in.nextInt();
            spaces += a / 4;
            spaces += a % 4 != 3 ? a % 4 : 2;
        }
        System.out.println(spaces);
    }
}
