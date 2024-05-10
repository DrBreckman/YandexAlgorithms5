package pack2.taskG;

import java.util.*;
public class TaskG {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int tests = in.nextInt();
        while(tests-- > 0){
            int n = in.nextInt(),
                    min = in.nextInt(),
                    listSize = 1;
            List<String> answer = new ArrayList<>();

            for(int i = 1; i < n; i++){
                int a = in.nextInt();

                if (Math.min(min, a) >= listSize + 1){
                    listSize++;
                    min = Math.min(min, a);
                } else {
                    answer.add(String.valueOf(listSize));
                    listSize = 1;
                    min = a;
                }
            }
            answer.add(String.valueOf(listSize));

            System.out.println(answer.size());
            System.out.println(String.join(" ", answer));
        }
    }
}
