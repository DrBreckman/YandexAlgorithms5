package pack3.taskC;

import java.io.*;
import java.util.*;
public class TaskC {
    public static void main(String[] args) throws IOException {
        System.out.println(solve());
    }

    public static int solve() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer, Integer> map = new HashMap<>(200000);
        int n = Integer.parseInt(reader.readLine()),
                max = 1;
        for(String number : reader.readLine().split("\\s")){
            int m = Integer.parseInt(number),
                    k = map.getOrDefault(m, -1);
            if (k == -1)
                map.put(m, 1);
            else {
                map.merge(m, 1, Integer::sum);
                max = Math.max(max, k + 1);
            }
        }

        var e = map.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .toList();
        for(int i = 1; i < e.size(); i++)
            if (e.get(i).getKey() - e.get(i - 1).getKey() <= 1)
                max = Math.max(max, e.get(i).getValue() + e.get(i - 1).getValue());

        return n - max;
    }
}
