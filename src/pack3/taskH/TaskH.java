package pack3.taskH;

import java.util.*;
public class TaskH {
    public record Shift(int detX, int detY) { }
    public record LuciferMatchPoint(int x, int y) { }
    public static class LuciferMatch {
        public LuciferMatchPoint first;
        public LuciferMatchPoint second;
        public LuciferMatch(LuciferMatchPoint m1, LuciferMatchPoint m2) {
            first = m1;
            second = m2;
            if (m1.x > m2.x || m1.x == m2.x && m1.y > m2.y){
                LuciferMatchPoint tmp = first;
                first = second;
                second = tmp;
            }
        }
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof LuciferMatch o))
                return false;
            return second.x - first.x == o.second.x - o.first.x && second.y - first.y == o.second.y - o.first.y;
        }
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        LuciferMatch[] pack1 = new LuciferMatch[n],
                pack2 = new LuciferMatch[n];

        for(int i = 0; i < n; i++)
            pack1[i] = new LuciferMatch(new LuciferMatchPoint(in.nextInt(), in.nextInt()), new LuciferMatchPoint(in.nextInt(), in.nextInt()));
        for(int i = 0; i < n; i++)
            pack2[i] = new LuciferMatch(new LuciferMatchPoint(in.nextInt(), in.nextInt()), new LuciferMatchPoint(in.nextInt(), in.nextInt()));

        HashMap<Shift, Integer> hashMap = new HashMap<>();
        for(var match1 : pack1){
            for(var match2 : pack2){
                if (match1.equals(match2)){
                    Shift shift = new Shift(match2.first.x - match1.first.x,match2.first.y - match1.first.y );
                    hashMap.putIfAbsent(shift, 0);
                    hashMap.merge(shift, 1, Integer::sum);
                }
            }
        }

        System.out.println(n - hashMap.values().stream().max(Comparator.comparingInt(x -> x)).orElse(0));
    }
}
