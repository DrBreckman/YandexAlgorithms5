package pack2.taskE;

import java.util.*;
public class TaskE {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        int n = in.nextInt(),
                a = in.nextInt(),
                b = in.nextInt();
        Berry[] berries = new Berry[n];

        berries[0] = new Berry(a, b, 1, a - b);
        Berry lastBerry = berries[0];
        long max = lastBerry.a;
        for(int i = 1; i < n; i++) {
            a = in.nextInt();
            b = in.nextInt();

            berries[i] =  new Berry(a, b, i + 1, a - b);
            if (berries[i].cool > 0)
                max += berries[i].cool;
        }

        for(int i = 0; i < n; i++){
            long newMax = max - lastBerry.a;
            newMax += (Math.max(lastBerry.cool, 0));
            newMax = newMax + (berries[i].cool > 0 ? berries[i].b : berries[i].a);
            if (newMax > max){
                lastBerry = berries[i];
                max = newMax;
            }
        }

        final Berry lastEatenBerry = lastBerry;
        System.out.println(max);

        var berryList = new ArrayList<>(Arrays.stream(berries)
                .filter(x -> x.a - x.b > 0 && x != lastEatenBerry)
                .map(x -> String.valueOf(x.i))
                .toList());
        berryList.add(String.valueOf(lastBerry.i));
        berryList.addAll(Arrays.stream(berries)
                .filter(x -> x.a - x.b <= 0 && x != lastEatenBerry)
                .map(x -> String.valueOf(x.i))
                .toList());

        System.out.println(String.join(" ", berryList));
    }
    public record Berry(int a, int b, int i, int cool) { }
}

