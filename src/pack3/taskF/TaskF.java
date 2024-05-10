package pack3.taskF;

import java.util.*;

public class TaskF {

    public enum DictionaryWordStatus {
        ABSENT,
        CAN_BE,
        PRESENT
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String[] dictionaryWords = in.nextLine().split(" ");
        Map<String, DictionaryWordStatus> dictionary = new HashMap<>();
        for(var s : dictionaryWords){
            StringBuilder sb = new StringBuilder();
            for(var c : s.toCharArray()){
                sb.append(c);
                DictionaryWordStatus status = dictionary.getOrDefault(sb.toString(), DictionaryWordStatus.ABSENT);
                if (status == DictionaryWordStatus.ABSENT)
                    dictionary.put(sb.toString(), DictionaryWordStatus.CAN_BE);
                else if (status == DictionaryWordStatus.PRESENT)
                    break;
            }
            dictionary.put(sb.toString(), DictionaryWordStatus.PRESENT);
        }

        String[] words = in.nextLine().split(" ");
        List<String> list = new ArrayList<>();
        for(var w : words)
            list.add(getShortWord(dictionary, w));

        System.out.println(String.join(" ", list));
    }

    public static String getShortWord(Map<String, DictionaryWordStatus> dictionary, String word){
        StringBuilder sb = new StringBuilder();
        for(var c : word.toCharArray()){
            sb.append(c);
            DictionaryWordStatus status = dictionary.getOrDefault(sb.toString(), DictionaryWordStatus.ABSENT);
            if (status == DictionaryWordStatus.ABSENT)
                return word;
            else if (status == DictionaryWordStatus.PRESENT)
                return sb.toString();
        }
        return word;
    }
}
