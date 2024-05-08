package pack1.taskH;

import java.io.*;
import java.util.*;

public class TaskH {
    public record Point(double x, double y) { }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);

        double L = in.nextInt(),
                position1 = in.nextInt(),
                u1 = in.nextInt(),
                position2 = in.nextInt(),
                u2 = in.nextInt(),
                halfRange = L / 2.0;

        if (position1 >= halfRange) {
            position1 = L - position1;
            u1 *= -1;
        }

        if (position2 >= halfRange) {
            position2 = L - position2;
            u2 *= -1;
        }

        if (position1 == position2){
            System.out.println("YES");
            System.out.println(0);
            return;
        } else if (u1 == 0 && u2 == 0){
            System.out.println("NO");
            return;
        }

        Point[] points1 = new Point[3];
        points1[0] = new Point(0, position1);
        if (u1 == 0){
            points1[1] = new Point(1, position1);
            points1[2] = new Point(2, position1);
        } else {
            points1[1] = getPoint(L, points1[0].y, u1, points1[0].x);
            points1[2] = getPoint(L, points1[1].y, -u1, points1[1].x);
        }

        Point[] points2 = new Point[3];
        points2[0] = new Point(0, position2);
        if (u2 == 0){
            points2[1] = new Point(1, position2);
            points2[2] = new Point(2, position2);
        } else {
            points2[1] = getPoint(L, points2[0].y, u2, points2[0].x);
            points2[2] = getPoint(L, points2[1].y, -u2, points2[1].x);
        }

        Point[] crossPoints = new Point[4];
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                crossPoints[i * 2 + j] = getCrossPoint(points1[i], points1[i + 1], points2[j], points2[j + 1]);

        System.out.println(Arrays.stream(crossPoints)
                .filter(p -> p.y >= 0 && p.y <= halfRange && p.x >= 0)
                .min(Comparator.comparing(p -> p.x))
                .map(point -> "YES\n" + point.x)
                .orElse("NO"));
    }

    public static Point getPoint(double l, double position, double u, double t0){
        double endPoint = u > 0 ? l / 2.0 : 0;
        return new Point(t0 + Math.abs((endPoint - position) / u),  endPoint);
    }

    public static Point getCrossPoint(Point p1, Point p2, Point p3, Point p4){
        double a1 = p2.y - p1.y,
                b1 = p1.x - p2.x,
                c1 = -p1.x * p2.y + p1.y * p2.x,

                a2 = p4.y - p3.y,
                b2 = p3.x - p4.x,
                c2 = -p3.x * p4.y + p3.y * p4.x,

                x = (b1 * c2 - b2 * c1) / (a1 * b2 - a2 * b1),
                y = (c1 + a1 * x) / -b1;
        return new Point(x, y);
    }
}
