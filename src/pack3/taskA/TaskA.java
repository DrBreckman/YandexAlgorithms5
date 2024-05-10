package pack3.taskA;

import java.util.*;
public class TaskA {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int n = Integer.parseInt(in.nextLine());
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++){
            int k = Integer.parseInt(in.nextLine());
            String[] songs = in.nextLine().split(" ");
            for(String s : songs){
                map.putIfAbsent(s, 0);
                map.merge(s, 1, Integer::sum);
            }
        }

        var songs = map.entrySet().stream()
                .filter(x -> x.getValue() == n)
                .map(Map.Entry::getKey)
                .sorted(Comparator.naturalOrder())
                .toList();

        System.out.println(songs.size());
        System.out.println(String.join(" ", songs));
    }
}
