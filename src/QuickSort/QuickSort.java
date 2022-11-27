package QuickSort;
import java.util.ArrayList;
import java.util.List;

public class QuickSort {
    private static void swap(List<Integer> numbers, int i, int j) {
        int temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }

    private static int partition(List<Integer> numbers, int left, int right) {
        int pivot = numbers.get(right);
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (numbers.get(j) <= pivot) {
                i++;
                swap(numbers, i, j);
            }
        }
        swap(numbers, i + 1, right);
        return i + 1;
    }

    private static void _quickSort(List<Integer> numbers, int left, int right) {
        if (left < right) {
            int pivot = partition(numbers, left, right);
            _quickSort(numbers, left, pivot - 1);
            _quickSort(numbers, pivot + 1, right);
        }
    }

    public static List<Integer> quickSort(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        _quickSort(sortedNumbers, 0, sortedNumbers.size() - 1);
        return sortedNumbers;
    }
}
