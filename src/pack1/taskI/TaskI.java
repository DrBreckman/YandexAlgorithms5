package pack1.taskI;

import java.io.*;
import java.util.*;

public class TaskI {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);

        int n = Short.parseShort(in.nextLine()),
                year = Short.parseShort(in.nextLine());
        String[] hollyDays = new String[n];
        for(int i = 0; i < n; i++)
            hollyDays[i] = in.nextLine();
        String firstDayOfTheYear = in.nextLine();

        Map<String, Integer> workDays = new HashMap<>();
        for(var d : daysOfTheWeek)
            workDays.put(d, 52);
        workDays.merge(firstDayOfTheYear, 1, Integer::sum);
        if (isYearFrog(year))
            workDays.merge(daysOfTheWeek.get((daysOfTheWeek.indexOf(firstDayOfTheYear) + 1) % 7), 1, Integer::sum);

        int[] generator = dayGenerator(year);
        for(var s : hollyDays)
            workDays.merge(convertToDyaOfTheWeek(s, firstDayOfTheYear, generator), -1, Integer::sum);

        String max = workDays.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey(),
                min = workDays.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();

        System.out.println(max + " " + min);
    }

    public static String convertToDyaOfTheWeek(String hollyDay, String firstDayOfTheWeek, int[] dayGenerator){
        String[] s = hollyDay.split(" ");
        int index = month.indexOf(s[1]),
                dayOfTheWeek = (dayGenerator[index] + Integer.parseInt(s[0])),
                firstDayIndex = daysOfTheWeek.indexOf(firstDayOfTheWeek);
        return daysOfTheWeek.get((dayOfTheWeek - 1 + firstDayIndex) % 7) ;
    }

    public static boolean isYearFrog(int year){
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0 );
    }

    public static int[] dayGenerator(int year){
        int[] monthDays = new int[]{ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (isYearFrog(year))
            monthDays[1] = 29;

        int[] days = new int[monthDays.length];
        for(int i = 1; i < monthDays.length; i++)
            days[i] = (days[i - 1] + monthDays[i - 1]);

        return days;
    }

    public final static List<String> daysOfTheWeek = new ArrayList<>() {{
        add("Monday");
        add("Tuesday");
        add("Wednesday");
        add("Thursday");
        add("Friday");
        add("Saturday");
        add("Sunday");
    }};
    public final static List<String> month = new ArrayList<>() {{
        add("January");
        add("February");
        add("March");
        add("April");
        add("May");
        add("June");
        add("July");
        add("August");
        add("September");
        add("October");
        add("November");
        add("December");
    }};
}

