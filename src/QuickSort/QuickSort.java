package QuickSort;
import java.util.ArrayList;
import java.util.List;

public class QuickSort {
    public static List<Integer> quickSort(List<Integer> numbers) {
        if (numbers.size() <= 1) {
            return numbers;
        }
        int pivot = numbers.get(0);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) < pivot) {
                left.add(numbers.get(i));
            } else {
                right.add(numbers.get(i));
            }
        }
        List<Integer> sortedLeft = quickSort(left);
        List<Integer> sortedRight = quickSort(right);
        sortedLeft.add(pivot);
        sortedLeft.addAll(sortedRight);
        return sortedLeft;
    }
}
