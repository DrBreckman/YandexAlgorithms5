package pack3.taskB;

import java.util.*;
public class TaskB {
    public static void main(String[] args){
        System.out.println(solve() ? "YES" : "NO");
    }

    public static boolean solve(){
        Scanner in = new Scanner(System.in);

        String word1 = in.nextLine(),
                word2 = in.nextLine();

        Map<Character, Integer> map = new HashMap<>();
        for(var c : word1.toCharArray()){
            map.putIfAbsent(c, 0);
            map.merge(c, 1, Integer::sum);
        }

        for(var c : word2.toCharArray()){
            if (!map.containsKey(c))
                return false;
            map.merge(c, -1, Integer::sum);
        }

        return map.entrySet().stream()
                .allMatch(x -> x.getValue() == 0);
    }
}
