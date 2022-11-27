import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import QuickSort.QuickSort;
import QuickSort.ParallelQuickSort;
import RankSort.RankSort;
import RankSort.ParallelRankSort;
import MergeSort.MergeSort;
import MergeSort.ParallelMergeSort;

class Test {

    public static List<Integer> readInput(String file) {
        // Read input from "../input/random.txt"
        String path = new File(file).getAbsolutePath();
        List<Integer> numbers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                String[] split = line.split(" ");
                for (String s : split) {
                    numbers.add(Integer.parseInt(s));
                }
            }
            return numbers;
        } catch (Exception e) {
            e.printStackTrace();
            return numbers;
        }
    }

    public static void writeOutput(String file, List<Integer> numbers) {
        // Write output to "../output/random/order*.txt"
        String path = new File(file).getAbsolutePath();
        String content = "";
        for (int i = 0; i < numbers.size(); i++) {
            content += numbers.get(i) + " ";
        }
        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void test(String inputFile, boolean verify) {
        String inputPath = "./input/";
        String inputFileNoExtension = inputFile.substring(0, inputFile.lastIndexOf("."));
        String outputPath = "./output/" + inputFileNoExtension + "/";
        List<Integer> numbers = readInput(inputPath + inputFile);
        if (numbers.size() == 0) {
            // 尝试不同的 Input 文件夹
            inputPath = "../input/";
            numbers = readInput(inputPath + inputFile);
        }
        System.out.println("size of " + inputFile + ": " + numbers.size());

        // 排序基准
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        if (verify) {
            sortedNumbers.sort(Integer::compareTo);
        }
        
        // 开始测试不同的算法
        // 1. 快速排序 - 串行版
        System.out.println("QuickSort - serial: order1.txt");
        // 统计时间
        long startTime = System.nanoTime();
        List<Integer> sorted = QuickSort.quickSort(numbers);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        // 输出花费时间
        System.out.println("QuickSort - serial: " + duration + " ms");
        writeOutput(outputPath + "order1.txt", sorted);
        if (verify) {
            System.out.println("QuickSort - serial: " + sorted.equals(sortedNumbers));
        } else {
            System.out.println("QuickSort - serial done.");
        }
        
        // 2. 快速排序 - 并行版
        System.out.println("QuickSort - parallel: order2.txt");
        // 统计时间
        startTime = System.nanoTime();
        sorted = ParallelQuickSort.parallelQuickSort(numbers);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        // 输出花费时间
        System.out.println("QuickSort - parallel: " + duration + " ms");
        writeOutput(outputPath + "order2.txt", sorted);
        if (verify) {
            System.out.println("QuickSort - parallel: " + sorted.equals(sortedNumbers));
        } else {
            System.out.println("QuickSort - parallel done.");
        }
        
        // 3. 枚举排序 - 串行版
        System.out.println("RankSort - serial: order3.txt");
        // 统计时间
        startTime = System.nanoTime();
        sorted = RankSort.rankSort(numbers);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        // 输出花费时间
        System.out.println("RankSort - serial: " + duration + " ms");
        writeOutput(outputPath + "order3.txt", sorted);
        if (verify) {
            System.out.println("RankSort - serial: " + sorted.equals(sortedNumbers));
        } else {
            System.out.println("RankSort - serial done.");
        }

        // 4. 枚举 排序 - 并行版
        System.out.println("RankSort - parallel: order4.txt");
        // 统计时间
        startTime = System.nanoTime();
        sorted = ParallelRankSort.parallelRankSort(numbers);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        // 输出花费时间
        System.out.println("RankSort - parallel: " + duration + " ms");
        writeOutput(outputPath + "order4.txt", sorted);
        if (verify) {
            System.out.println("RankSort - parallel: " + sorted.equals(sortedNumbers));
        } else {
            System.out.println("RankSort - parallel done.");
        }
        
        // 5. 归并排序 - 串行版
        System.out.println("MergeSort - serial: order5.txt");
        // 统计时间
        startTime = System.nanoTime();
        sorted = MergeSort.mergeSort(numbers);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        // 输出花费时间
        System.out.println("MergeSort - serial: " + duration + " ms");
        writeOutput(outputPath + "order5.txt", sorted);
        if (verify) {
            System.out.println("MergeSort - serial: " + sorted.equals(sortedNumbers));
        } else {
            System.out.println("MergeSort - serial done.");
        }

        // 6. 归并排序 - 并行版
        System.out.println("MergeSort - parallel: order6.txt");
        // 统计时间
        startTime = System.nanoTime();
        sorted = ParallelMergeSort.parallelMergeSort(numbers);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        // 输出花费时间
        System.out.println("MergeSort - parallel: " + duration + " ms");
        writeOutput(outputPath + "order6.txt", sorted);
        if (verify) {
            System.out.println("MergeSort - parallel: " + sorted.equals(sortedNumbers));
        } else {
            System.out.println("MergeSort - parallel done.");
        }
    }
    
    public static void main(String[] args) {
        String inputFile = "random.txt";
        // 是否检验排序结果
        boolean verify = true;
        test(inputFile, verify);
    }
}
