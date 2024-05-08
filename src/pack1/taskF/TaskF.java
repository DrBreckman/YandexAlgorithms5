package pack1.taskF;

import java.util.*;
public class TaskF {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        boolean odd = Math.abs(in.nextInt() % 2) == 1;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1; i < n; i++){
            int a = in.nextInt();
            if (!odd){
                stringBuilder.append('+');
                if (Math.abs(a % 2) == 1)
                    odd = true;
            } else
                stringBuilder.append(Math.abs(a % 2) == 1 ? 'x' : '+');
        }
        System.out.println(stringBuilder);
    }
}


