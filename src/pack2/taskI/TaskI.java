package pack2.taskI;

import java.util.*;
public class TaskI {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(),
                answer = 0,
                k = 0;
        NavigableMap<Integer, Integer> rows = new TreeMap<>(),
                columns = new TreeMap<>();
        for(int i = 1; i <= n; i++)
            rows.put(i, 0);

        for(int i = 0; i < n; i++){
            int x = in.nextInt(),
                    y = in.nextInt();

            rows.merge(x, 1, Integer::sum);

            if (columns.containsKey(y))
                columns.merge(y, 1, Integer::sum);
            else
                columns.put(y, 1);
        }

        Map.Entry<Integer, Integer> left = columns.higherEntry(0),
                right = columns.lowerEntry(n + 1);
        while(!left.getKey().equals(right.getKey())){
            if (left.getValue() > right.getValue()){
                int key = right.getKey(),
                        value = right.getValue(),
                        nextKey = columns.lowerEntry(key).getKey();
                columns.merge(nextKey, value, Integer::sum);
                answer += (Math.abs(key - nextKey) * value);
                right = columns.lowerEntry(key);
            } else {
                int key = left.getKey(),
                        value = left.getValue(),
                        nextKey = columns.higherEntry(key).getKey();
                columns.merge(nextKey, value, Integer::sum);
                answer += (Math.abs(key - nextKey) * value);
                left = columns.higherEntry(key);
            }
        }

        Map.Entry<Integer, Integer> first = rows.higherEntry(0);
        while(first != null){
            k += first.getValue() - 1;
            answer += Math.abs(k);
            first = rows.higherEntry(first.getKey());
        }

        System.out.println(answer);
    }
}
