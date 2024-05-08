package pack1.taskJ;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskJ {
    public static void main(String[] args) throws Exception{
        try(BufferedReader reader = new BufferedReader(new FileReader(input))){
            var dwordFormatter = Arrays.stream(reader.readLine()
                            .split(" "))
                    .map(Integer::parseInt)
                    .toList();

            FreeFragment.standardListWidth = dwordFormatter.get(0);
            FreeFragment.standardLineHeight = dwordFormatter.get(1);
            FreeFragment.standardCharWidth = dwordFormatter.get(2);

            FreeFragment.add(0);


            StringBuilder sb = new StringBuilder();
            String tmpStr;
            while((tmpStr = reader.readLine()) != null)
                sb.append(tmpStr.isEmpty() || tmpStr.matches(enterRegex) ? "\n " : tmpStr + " ");

            Matcher matcher = Pattern.compile("\\(image[^(]*\\)|[\\w.,:;!?'-]+|\\n").matcher(sb);
            while(matcher.find()){
                String tmp = sb.substring(matcher.start(), matcher.end());
                if (tmp.isEmpty() || tmp.matches(enterRegex) || tmp.matches("\\n"))
                    FreeFragment.reset();
                else {
                    defineItem(tmp);
                }
            }
        }
    }

    public static int getImgIntegerParam(String str, String param) throws IllegalArgumentException{
        Matcher matcher = Pattern.compile(param + "=(-?\\d+)").matcher(str);
        if (matcher.find())
            return Integer.parseInt(matcher.group(1));
        throw new IllegalArgumentException("Illegal argument");
    }

    public static Layout getImgLayout(String str) throws IllegalArgumentException{
        Matcher matcher = Pattern.compile("layout=(\\w+)").matcher(str);
        if (matcher.find())
            return switch(matcher.group(1)){
                case "embedded" -> Layout.EMBEDDED;
                case "surrounded" -> Layout.SURROUNDED;
                case "floating" -> Layout.FLOATING;

                default -> throw new IllegalArgumentException("Illegal argument");
            };
        throw new IllegalArgumentException("Illegal argument");
    }

    public static void defineItem(String str){
        Consumer<String> consumer = str.matches(imgRegex) ? switch (getImgLayout(str)) {
            case EMBEDDED -> TaskJ::putEmbeddedImg;
            case SURROUNDED -> TaskJ::putSurroundedImg;
            case FLOATING -> TaskJ::putFloatingImg;
        } : TaskJ::putWord;
        consumer.accept(str);
    }

    public static void putWord(String word){
        FreeFragment.putSmth(word.length() * FreeFragment.standardCharWidth, !FreeFragment.empty);
        FreeFragment.lastX = FreeFragment.activeFragment.x;
        FreeFragment.lastY = FreeFragment.activeFragment.y;
    }

    public static void putEmbeddedImg(String img){
        int imgWidth = getImgIntegerParam(img, "width"), imgHeight = getImgIntegerParam(img, "height");
        FreeFragment.putSmth(imgWidth, !FreeFragment.empty);
        FreeFragment.height = Math.max(FreeFragment.height, imgHeight);
        writeImgPosition(FreeFragment.activeFragment.x - imgWidth, FreeFragment.activeFragment.y);
        FreeFragment.lastX = FreeFragment.activeFragment.x;
        FreeFragment.lastY = FreeFragment.activeFragment.y;
    }

    public static void putSurroundedImg(String img){
        int imgWidth = getImgIntegerParam(img, "width"), imgHeight = getImgIntegerParam(img, "height");
        FreeFragment.putSmth(imgWidth, false);
        FreeFragment.empty = true;
        int x = FreeFragment.activeFragment.x - imgWidth;
        BlockingFragment.add(x, FreeFragment.activeFragment.y, imgWidth, imgHeight);
        writeImgPosition(x, FreeFragment.activeFragment.y);
        FreeFragment.lastX = FreeFragment.activeFragment.x;
        FreeFragment.lastY = FreeFragment.activeFragment.y;
    }

    public static void putFloatingImg(String img){
        int width = getImgIntegerParam(img, "width"), height = getImgIntegerParam(img, "height");
        int imgX = FreeFragment.lastX + getImgIntegerParam(img, "dx");
        int imgY = FreeFragment.lastY + getImgIntegerParam(img, "dy");

        if (imgX < 0)
            imgX = 0;
        else if (imgX + width > FreeFragment.standardListWidth)
            imgX = FreeFragment.standardListWidth - width;

        writeImgPosition(imgX, imgY);

        FreeFragment.lastX = imgX + width;
        FreeFragment.lastY = imgY;
    }

    public static void writeImgPosition(int x, int y){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))){
            writer.write(x + " " + y + "\n");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static class FreeFragment extends Fragment{
        public static int lastX = 0;
        private static int lastY = 0;

        public static FreeFragment activeFragment = null;
        public static final NavigableSet<FreeFragment> fragments = new TreeSet<>();
        public static int standardListWidth = 0;
        public static int standardLineHeight = 0;
        public static int standardCharWidth = 0;
        public static int height = 0;
        public static boolean empty = true;
        private FreeFragment(int x, int y, int width, int height) {
            super(x, y, width);
            FreeFragment.height = Math.max(FreeFragment.height, height);
        }

        public static void add(int y){
            add(0, y, standardListWidth, standardLineHeight);
            next();
        }

        public static void add(int x, int y, int width, int height){
            FreeFragment fragment = new FreeFragment(x, y, width, height);
            fragments.add(fragment);
        }

        public static void next(){
            if (fragments.isEmpty())
                create();
            activeFragment = fragments.pollFirst();
            empty = true;
        }

        public static void create(){
            int x = 0, y = activeFragment.y + FreeFragment.height;
            FreeFragment.height = 0;
            BlockingFragment.removeUseless(y);
            for(var blocker : BlockingFragment.fragments){
                add(x, y, blocker.x - x, standardLineHeight);
                x = blocker.x + blocker.width;
            }
            add(x, y, standardListWidth - x, standardLineHeight);
        }

        public static void putSmth(int width, boolean space){
            int fullSpace = width + (space ? standardCharWidth : 0);
            if (fullSpace <= activeFragment.width){
                activeFragment.x += fullSpace;
                activeFragment.width -= fullSpace;
                empty = false;
            } else {
                next();
                putSmth(width, false);
            }
        }

        public static void reset(){
            int max = Math.max(activeFragment.y + FreeFragment.height, BlockingFragment.fragments.stream()
                    .mapToInt(blocker -> blocker.y + blocker.height)
                    .max()
                    .orElse(0));
            fragments.clear();
            BlockingFragment.fragments.clear();
            FreeFragment.height = 0;
            add(max);
            FreeFragment.lastX = FreeFragment.activeFragment.x;
            FreeFragment.lastY = FreeFragment.activeFragment.y;
        }
    }

    public static class BlockingFragment extends Fragment{
        public int height;
        public static final NavigableSet<BlockingFragment> fragments = new TreeSet<>();
        private BlockingFragment(int x, int y, int width, int height) {
            super(x, y, width);
            this.height = height;
        }

        public static void add(int x, int y, int width, int height){
            BlockingFragment blocker = new BlockingFragment(x, y, width, height);
            fragments.add(blocker);
        }
        public static void removeUseless(int y){
            fragments.removeIf(fragment -> fragment.y + fragment.height <= y);
        }
    }

    public static abstract class Fragment implements Comparable<Fragment>{
        public int x;
        public int y;
        public int width;

        public Fragment(int x, int y, int width){
            this.x = x;
            this.y = y;
            this.width = width;
        }
        @Override
        public int compareTo(Fragment o) {
            return x - o.x;
        }
    }

    public enum Layout {
        EMBEDDED,
        SURROUNDED,
        FLOATING
    }

    private static final String input = "input.txt";
    private static final String output = "output.txt";
    private static final String imgRegex = "\\(image[^(]*\\)";
    private static final String enterRegex = "^\\s+";
}
