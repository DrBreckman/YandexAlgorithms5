package pack1.taskB;

import java.util.Scanner;

public class TaskB {

    private static final int HOME = 1;
    private static final int VISITING = 2;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String[] data = in.nextLine().split(":");
        int firstMatchTeam1 = Integer.parseInt(data[0]),
                firstMatchTeam2 = Integer.parseInt(data[1]);

        data = in.nextLine().split(":");
        int secondMatchTeam1 = Integer.parseInt(data[0]),
                secondMatchTeam2 = Integer.parseInt(data[1]);

        int where = Integer.parseInt(in.nextLine());
        if (firstMatchTeam1 + secondMatchTeam1 > firstMatchTeam2 + secondMatchTeam2)
            System.out.println(0);
        else {
            int equals = firstMatchTeam2 + secondMatchTeam2 - firstMatchTeam1 - secondMatchTeam1;
            if (where == VISITING && firstMatchTeam1 > secondMatchTeam2 ||
                    where == HOME && firstMatchTeam2 < secondMatchTeam1 + equals){
                System.out.println(equals);
            } else {
                System.out.println(equals + 1);
            }
        }
    }
}

