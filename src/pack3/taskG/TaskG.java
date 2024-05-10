package pack3.taskG;

import java.util.*;
public class TaskG {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Set<Point> set = new HashSet<>();
        int n = in.nextInt(),
                max = 2;

        while (n-- > 0)
            set.add(new Point(in.nextInt(), in.nextInt()));

        Set<Point> answer = new HashSet<>();
        var list = set.stream().toList();
        for(int i = 0; i < list.size(); i++){
            Point p = list.get(i);
            for(int j = i + 1; j < list.size(); j++){
                Point p0 = list.get(j);
                Set<Point> tmp = new HashSet<>();
                tmp.add(p0);
                tmp.add(p);

                int curr = 2;

                double rx = (p0.x + p.x) / 2.0,
                        ry = (p0.y + p.y) / 2.0,
                        x1 = rx - p.y + ry,
                        y1 = ry + p.x - rx,
                        x2 = rx + p.y - ry,
                        y2 = ry - p.x + rx;

                if (x1 != (int)x1 || x2 != (int)x2 || y1 != (int)y1 || y2 != (int)y2 )
                    continue;

                Point p3 = new Point((int)x1, (int)y1);
                tmp.add(p3);
                if (set.contains(p3))
                    curr++;

                p3 = new Point((int)x2, (int)y2);
                tmp.add(p3);
                if (set.contains(p3))
                    curr++;

                if (curr > max){
                    max = curr;
                    answer = tmp;
                }
            }
        }

        if (set.size() == 1){
            Point p = set.stream().findAny().get();
            System.out.printf("%d\n%s\n%s\n%s",
                    3,
                    (p.x + 1) + " " + (p.y + 1),
                    (p.x + 2) + " " + p.y,
                    (p.x + 1) + " " + (p.y - 1)
            );
        }else if (max == 2){
            Point p1 = list.get(0),
                    p2 = list.get(1);
            System.out.printf("%d\n%s\n%s",
                    2,
                    (p2.x + p2.y - p1.y) + " " + (p2.y - p2.x + p1.x),
                    (p1.x + p2.y - p1.y) + " " + (p1.y - p2.x + p1.x)
            );
        } else {
            System.out.println(4 - max);
            for(var p : answer)
                if (!set.contains(p))
                    System.out.println(p.x + " " + p.y);
        }
    }

    public record Point(int x, int y) {
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Point))
                return false;
            return x == ((Point)obj).x && y == ((Point)obj).y;
        }
    }
}
