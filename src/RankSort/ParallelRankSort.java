package RankSort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelRankSort implements Runnable {
    public List<Integer> numbers;
    List<Integer> sortedNumbers;
    private int i;

    public ParallelRankSort(List<Integer> numbers, List<Integer> sortedNumbers, int i) {
        this.numbers = numbers;
        this.sortedNumbers = sortedNumbers;
        this.i = i;
    }

    @Override
    public void run() {
        int rank = 0;
        for (int j = 0; j < numbers.size(); j++) {
            if (numbers.get(j) < numbers.get(i) || (numbers.get(j) == numbers.get(i) && j < i)) {
                rank++;
            }
        }
        sortedNumbers.set(rank, numbers.get(i));
    }

    public static List<Integer> parallelRankSort(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (int i = 0; i < numbers.size(); i++) {
            ParallelRankSort task = new ParallelRankSort(numbers, sortedNumbers, i);
            executor.submit(task);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        return sortedNumbers;
    }
}
