import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import MergeSort.MergeSort;
import QuickSort.QuickSort;
import QuickSort.ParallelQuickSort;

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
        List<Integer> quickSorted = QuickSort.quickSort(numbers);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        // 输出花费时间
        System.out.println("QuickSort - serial: " + duration + " ms");
        writeOutput(outputPath + "order1.txt", quickSorted);
        if (verify) {
            System.out.println("QuickSort - serial: " + quickSorted.equals(sortedNumbers));
        } else {
            System.out.println("QuickSort - serial done.");
        }
        
        // 2. 快速排序 - 并行版
        // System.out.println("QuickSort - parallel: order2.txt");
        // // 统计时间
        // startTime = System.nanoTime();
        // quickSorted = ParallelQuickSort.parallelQuickSort(numbers);
        // endTime = System.nanoTime();
        // duration = (endTime - startTime) / 1000000;
        // // 输出花费时间
        // System.out.println("QuickSort - parallel: " + duration + " ms");
        // writeOutput(outputPath + "order2.txt", quickSorted);
        // if (verify) {
        //     System.out.println("QuickSort - parallel: " + quickSorted.equals(sortedNumbers));
        // } else {
        //     System.out.println("QuickSort - parallel done.");
        // }

        // 5. 归并排序 - 串行版
        System.out.println("MergeSort - serial: order1.txt");
        // 统计时间
        startTime = System.nanoTime();
        List<Integer> mergeSorted = MergeSort.mergeSort(numbers);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        // 输出花费时间
        System.out.println("MergeSort - serial: " + duration + " ms");
        writeOutput(outputPath + "order1.txt", mergeSorted);
        if (verify) {
            System.out.println("MergeSort - serial: " + mergeSorted.equals(sortedNumbers));
        } else {
            System.out.println("MergeSort - serial done.");
        }
    }
    
    public static void main(String[] args) {
        String inputFile = "random.txt";
        // 是否检验排序结果
        boolean verify = true;
        test(inputFile, verify);
    }
}
