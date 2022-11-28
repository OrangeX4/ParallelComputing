package MergeSort;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {
    private static void merge(List<Integer> numbers, int left, int middle, int right) {
        List<Integer> leftArray = new ArrayList<>(numbers.subList(left, middle + 1));
        List<Integer> rightArray = new ArrayList<>(numbers.subList(middle + 1, right + 1));
        int i = 0;
        int j = 0;
        for (int k = left; k <= right; k++) {
            if (i == leftArray.size()) {
                numbers.set(k, rightArray.get(j));
                j++;
            } else if (j == rightArray.size()) {
                numbers.set(k, leftArray.get(i));
                i++;
            } else if (leftArray.get(i) <= rightArray.get(j)) {
                numbers.set(k, leftArray.get(i));
                i++;
            } else {
                numbers.set(k, rightArray.get(j));
                j++;
            }
        }
    }

    public static void _mergeSort(List<Integer> numbers, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            _mergeSort(numbers, left, middle);
            _mergeSort(numbers, middle + 1, right);
            merge(numbers, left, middle, right);
        }
    }

    public static List<Integer> mergeSort(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        _mergeSort(sortedNumbers, 0, sortedNumbers.size() - 1);
        return sortedNumbers;
    }
}
