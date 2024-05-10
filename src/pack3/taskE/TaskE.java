package pack3.taskE;

import java.util.*;

public class TaskE {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        Map<Integer, Set<Integer>> map = new HashMap<>();
        for(int i = 0; i < 3; i++){
            int n = in.nextInt();
            for(int j = 0; j < n; j++){
                int k = in.nextInt();
                map.putIfAbsent(k, new HashSet<>());
                map.get(k).add(i);
            }
        }

        var s = map.entrySet().stream()
                .filter(x -> x.getValue().size() >= 2)
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(x -> String.valueOf(x.getKey()))
                .toList();

        System.out.println(String.join(" ", s));
    }
}
