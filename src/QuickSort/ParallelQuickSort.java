package QuickSort;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class ParallelQuickSort extends RecursiveTask<List<Integer>> {

    private List<Integer> numbers;

    public ParallelQuickSort(List<Integer> numbers) {
        this.numbers = numbers;
    }

    private static void swap(List<Integer> numbers, int i, int j) {
        int temp = numbers.get(i);
        numbers.set(i, numbers.get(j));
        numbers.set(j, temp);
    }

    private static int partition(List<Integer> numbers) {
        int pivot = numbers.get(numbers.size() - 1);
        int i = - 1;
        for (int j = 0; j < numbers.size() - 1; j++) {
            if (numbers.get(j) <= pivot) {
                i++;
                swap(numbers, i, j);
            }
        }
        swap(numbers, i + 1, numbers.size() - 1);
        return i + 1;
    }
    
    @Override
    protected List<Integer> compute() {
        if (numbers.size() <= 2) {
            return numbers;
        }
        int pivot = partition(numbers);
        ParallelQuickSort leftTask = new ParallelQuickSort(numbers.subList(0, pivot));
        ParallelQuickSort rightTask = new ParallelQuickSort(numbers.subList(pivot + 1, numbers.size()));

        leftTask.fork();
        rightTask.fork();

        List<Integer> left = leftTask.join();
        List<Integer> right = rightTask.join();

        List<Integer> sortedNumbers = new ArrayList<>(left);
        sortedNumbers.add(numbers.get(pivot));
        sortedNumbers.addAll(right);
        return sortedNumbers;
    }

    public static List<Integer> parallelQuickSort(List<Integer> numbers) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ParallelQuickSort task = new ParallelQuickSort(numbers);
        List<Integer> sortedNumbers = pool.invoke(task);
        return sortedNumbers;
    }
}
