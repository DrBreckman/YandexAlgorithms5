package pack1.taskA;

import java.util.Scanner;

public class TaskA {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int p = in.nextInt(),
                v = in.nextInt(),
                q = in.nextInt(),
                m = in.nextInt();

        int vStart = p - v,
                vEnd = p + v,
                mStart = q - m,
                mEnd = q + m;

        System.out.println(mStart > vEnd || mEnd < vStart ?
                getTreeByRange(vStart, vEnd) + getTreeByRange(mStart, mEnd) :
                getTreeByRange(Math.min(vStart, mStart), Math.max(vEnd, mEnd)));
    }

    public static int getTreeByRange(int start, int end){
        return end - start + 1;
    }
}
