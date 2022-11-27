package MergeSort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends RecursiveAction {
    public List<Integer> numbers;
    private int left;
    private int right;

    public ParallelMergeSort(List<Integer> numbers, int left, int right) {
        this.numbers = numbers;
        this.left = left;
        this.right = right;
    }

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

    @Override
    protected void compute() {
        if (left < right) {
            int middle = (left + right) / 2;
            
            ParallelMergeSort leftTask = new ParallelMergeSort(numbers, left, middle);
            ParallelMergeSort rightTask = new ParallelMergeSort(numbers, middle + 1, right);
            
            leftTask.fork();
            rightTask.fork();
            
            leftTask.join();
            rightTask.join();

            merge(numbers, left, middle, right);
        }
    }

    public static List<Integer> parallelMergeSort(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ParallelMergeSort task = new ParallelMergeSort(sortedNumbers, 0, sortedNumbers.size() - 1);
        pool.invoke(task);
        return task.numbers;
    }
}
