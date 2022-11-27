package RankSort;

import java.util.ArrayList;
import java.util.List;

public class RankSort {
    public static List<Integer> rankSort(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        for (int i = 0; i < numbers.size(); i++) {
            int rank = 0;
            for (int j = 0; j < numbers.size(); j++) {
                if (numbers.get(j) < numbers.get(i) || (numbers.get(j) == numbers.get(i) && j < i)) {
                    rank++;
                }
            }
            sortedNumbers.set(rank, numbers.get(i));
        }
        return sortedNumbers;
    }
}
