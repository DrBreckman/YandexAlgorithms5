package pack3.taskD;

import java.util.*;
public class TaskD {
    public static void main(String[] args){
        System.out.println(solve() ? "YES" : "NO");
    }

    public static boolean solve(){
        Scanner in = new Scanner(System.in);

        int n = in.nextInt(),
                k = in.nextInt();

        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            int t = in.nextInt();
            if (i - map.getOrDefault(t, i - k - 1) <= k)
                return true;
            map.put(t, i);
        }
        return false;
    }
}
