package pack1.taskG;

import java.util.*;
public class TaskG {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int soldiers = in.nextInt(),
                base = in.nextInt() - soldiers,
                enemy = in.nextInt(),
                move = 1;

        if (base > 0){
            int minSoldiers = getMinSoldiersCount(base + enemy);
            if (soldiers < minSoldiers && soldiers <= enemy)
                move = -1;
            else {
                while(base > soldiers){
                    base -= (soldiers - enemy);
                    move++;
                }

                while(getMinSoldiersCount(enemy + base) > soldiers) {
                    base -= (soldiers - enemy);
                    move++;
                }

                move = Math.min(
                        getMinFightMoves(enemy + base, soldiers) + move,
                        getMinFightMoves(enemy + base - (soldiers - enemy), soldiers) + move + 1
                );
            }
        }
        System.out.println(move);
    }

    public static int getMinSoldiersCount(int enemy) {
        int min = enemy;
        for(int i = enemy; i > 0; i--){
            int cloneEnemy = enemy,
                    cloneSoldiers = i;
            while(cloneSoldiers > 0 && cloneEnemy > 0){
                cloneEnemy -= cloneSoldiers;
                cloneSoldiers -= cloneEnemy;
            }

            if (cloneEnemy > 0)
                break;

            min = Math.min(min, i);
        }
        return min;
    }
    public static int getMinFightMoves(int enemy, int soldiers){
        int move = 0;
        while(enemy > 0){
            enemy -= soldiers;
            soldiers -= enemy;
            move++;
        }
        return move;
    }
}
