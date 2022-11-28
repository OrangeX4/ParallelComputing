package QuickSort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends RecursiveAction {
    private static final int THRESHOLD = 1024;

    public List<Integer> numbers;
    private int left;
    private int right;

    public ParallelQuickSort(List<Integer> numbers, int left, int right) {
        this.numbers = numbers;
        this.left = left;
        this.right = right;
    }

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

    @Override
    protected void compute() {
        if (right - left > THRESHOLD) {
            int pivot = partition(numbers, left, right);
            ParallelQuickSort leftTask = new ParallelQuickSort(numbers, left, pivot - 1);
            ParallelQuickSort rightTask = new ParallelQuickSort(numbers, pivot + 1, right);
    
            leftTask.fork();
            rightTask.fork();
    
            leftTask.join();
            rightTask.join();
        } else {
            QuickSort._quickSort(numbers, left, right);
        }
    }

    public static List<Integer> parallelQuickSort(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ParallelQuickSort task = new ParallelQuickSort(sortedNumbers, 0, sortedNumbers.size() - 1);
        pool.invoke(task);
        return task.numbers;
    }
}
