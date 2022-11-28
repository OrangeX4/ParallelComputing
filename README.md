<style>
h1 {
    text-align: center;
}
h2, h3 {
    page-break-after: avoid; 
}
.center {
    margin: 0 auto;
    width: fit-content;
    margin-top: 2em;
    padding-top: 0.5em;
    padding-bottom: 0.5em;
    margin-bottom: 2em;
}
.title {
    font-weight: bold;
    border-top-style: solid;
    border-bottom-style: solid;
}
.newpage {
    page-break-after: always
}
@media print {
    @page {
        margin: 3cm;
    }
}
</style>

<h1 style="margin-top: 4em">
分布式与并行计算实验报告
</h1>

# <h1 class="center title">实验：三种排序的串行与并行比较</h1>

<div class="center">
<h3>院系：人工智能学院</h3>
<h3>姓名：方盛俊</h3>
<h3>学号：201300035</h3>
<h3>邮箱：201300035@smail.nju.edu.cn</h3>
<h3>时间：2022年11月27日</h3>
</div>

<div class="newpage"></div>

<!-- 生成目录 -->

## <h1>目录</h1>

[TOC]

<div class="newpage"></div>

<!-- 文章主体内容 -->

## 一、项目要求

- 课程Project：三种排序的串行与并行比较；
- 任务描述：请分别实现快速排序、枚举排序、归并排序三种排序方法的串行与并行算法。
- 数据集：random.txt，一共有30000个乱序数据，数据范围是[-50000, 50000]，数据间以空格 “ ” 分隔。

**具体要求：**

1. 用 Java 多线程或者 C# 多线程模拟并行处理（推荐用Java）。
2. 说明程序执行方式，记录在 `ReadMe.txt` 中（PS：这里我使用 `README.md` 替代，同时也作为实验报告的源文件）。
3. 读取乱序数据文件 `random.txt`，排序完成后输出排序文件 `order*.txt`。（需提交六份 `order*.txt`，命名为 `order1.txt`，`order2.txt`… 以此类推）
1. 比较各种算法的运行时间，请将运行时间记录在 2*3 的表格中。行分别表示串行、并行，列分别表示快速排序、枚举排序、归并排序。
2. 撰写实验报告，包括并行算法的伪代码、运行时间、技术要点（如性能优化方法）等，结合各自的实验设备（如多核处理器）上的实验结果进行优化，并在实验报告中针对实验结果进行分析（考虑到并行算法多线程在单核处理器中的并行开销，有可能性能会比串行算法下降）。
3. 独立完成实验，杜绝抄袭。


## 二、伪代码

### 2.1 快速排序 - 串行

```python
def partition(data, k, l):
    pivot = data[l]
    i = k - 1
    for j in range(k, l):
        if (data[j] <= pivot):
            i += 1
            swap(data[i], data[j])
    swap(data[i + 1], data[l])
    return i + 1

def quicksort(data, i, j):
    if (i < j):
        r = partition(data, i, j)
        quicksort(data, i, r - 1)
        quicksort(data, r + 1, j)
```

### 2.2 快速排序 - 并行

```python
def partition(data, k, l):
    pivot = data[l]
    i = k - 1
    for j in range(k, l):
        if (data[j] <= pivot):
            i += 1
            swap(data[i], data[j])
    swap(data[i + 1], data[l])
    return i + 1

def para_quicksort(data, i, j):
    if (i < j):
        r = partition(data, i, j)
        task1 = para_quicksort(data, i, r - 1)
        task2 = para_quicksort(data, r + 1, j)
        invoke(task1, task2)
```

### 2.3 枚举排序 - 串行

```python
def ranksort(data):
    n = len(data)
    for i in range(n):
        k = 0
        for j in range(n):
            if (data[i] > data[j]) or (data[i] == data[j] and i > j):
                k += 1
        sorted_data[k] = data[i]
```

### 2.4 枚举排序 - 并行

```python
def para_ranksort(data):
    n = len(data)
    P0 send L to P1, P2, ..., Pn
    for all Pi where 1 <= i <= n para-do:
        k = 0
        for j in range(n):
            if (data[i] > data[j]) or (data[i] == data[j] and i > j):
                k += 1
        sorted_data[k] = data[i]
```

### 2.5 归并排序 - 串行

```python
def merge(data, k, m, l):
    left_data = data[k: m+1]
    right_data = data[m+1: l+1]
    i = 0
    j = 0
    for k in range(left, right + 1):
        if i == len(left_data):
            data[k] = right_data[j]
            j += 1
        elif j == len(right_data):
            data[k] = left_data[i]
            i += 1
        elif left_data[i] <= right_data[j]:
            data[k] = left_data[i]
            i += 1
        else:
            data[k] = right_data[i]
            j += 1


def mergesort(data, i, j):
    if (i < j):
        m = (i + j) // 2
        mergesort(data, i, m)
        mergesort(data, m + 1, j)
        merge(data, i, m, j)
```

### 2.6 归并排序 - 并行

```python
def merge(data, k, m, l):
    left_data = data[k: m+1]
    right_data = data[m+1: l+1]
    i = 0
    j = 0
    for k in range(left, right + 1):
        if i == len(left_data):
            data[k] = right_data[j]
            j += 1
        elif j == len(right_data):
            data[k] = left_data[i]
            i += 1
        elif left_data[i] <= right_data[j]:
            data[k] = left_data[i]
            i += 1
        else:
            data[k] = right_data[i]
            j += 1


def para_mergesort(data, i, j):
    if (i < j):
        m = (i + j) // 2
        task1 = para_mergesort(data, i, m)
        task2 = para_mergesort(data, m + 1, j)
        invoke(task1, task2)
        merge(data, i, m, j)
```


## 三、Java 实现

Java 有着丰富的多线程库，这里我主要用了两种线程池：针对分治任务的 **ForkJoinPool** 与针对非分治任务的 **FixedThreadPool**。

### 3.1 ForkJoinPool 与 RecursiveAction

快速排序与归并排序是分治任务，如果我们手动维护分治任务的线程优先级的话，会十分复杂，我们需要让线程从分治任务线程树的底层（叶子节点）开始，一层一层地执行，直到最后执行到顶层（根节点），想要维护这样一个线程树的优先级，无疑是一个复杂的任务。

Java 为我们设计分治任务提供了一个十分友好的 API，也就是 `ForkJoinPool` 与 `RecursiveAction`。ForkJoinPool 是自 Java7 开始，由 jvm 提供的一个用于并行执行的任务框架。其主旨是将大任务分成若干小任务，之后再并行对这些小任务进行计算，最终汇总这些任务的结果，得到最终的结果。类似于单机版的 MapReduce，也是采用了分治算法，将大的任务拆分到可执行的任务，之后并行执行，最终合并结果集。

`RecursiveAction` 和 `RecursiveTask` 是其中的两种实现方式，其中 `RecursiveAction` 没有返回值，`RecursiveTask` 有返回值。由于我们准备在 **原数组** 上进行分治任务，所以我们就直接采用 `RecursiveAction` 的方式，忽略 `RecursiveTask` 了。

如果我们已经实现了一个串行版本的分治任务，我们可以将其很简单地便修改为并行版本。

以快速排序为例，串行版本的快速排序如下：

```java
public class QuickSort {
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
```

我们可以将其迅速地改为并行版本：

```java
public class ParallelQuickSort extends RecursiveAction {
    public List<Integer> numbers;
    private int left;
    private int right;

    public ParallelQuickSort(List<Integer> numbers, int left, int right) {
        this.numbers = numbers;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int pivot = partition(numbers, left, right);
            ParallelQuickSort leftTask = new ParallelQuickSort(numbers, left, pivot - 1);
            ParallelQuickSort rightTask = new ParallelQuickSort(numbers, pivot + 1, right);
    
            leftTask.fork();
            rightTask.fork();
    
            leftTask.join();
            rightTask.join();
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
```

由于需要重载的 `compute` 方法是没有参数的，因此我们通过 **构造函数** `ParallelQuickSort` 将参数保存在对象的 **成员变量** 里，然后再在 `compute()` 里调用。

在 `compute()` 里，我们通过 `task.fork()` 和 `task.join()` 对任务进行执行。在 `parallelQuickSort()` 里，我们通过 `ForkJoinPool.commonPool()` 创建了一个线程池，接着创建根任务 `task`，最后通过 `pool.invoke(task)` 执行。执行完毕后的结果保存在 `task.numbers` 里。

快速排序是这样，归并排序也是同理，这里就不过多赘述了。

### 3.2 FixedThreadPool 与 ExecutorService

相比于快速排序和归并排序，枚举排序并不是分治任务，因此我们使用更为基础的 `ExecutorService` 与 `FixedThreadPool`。

枚举排序的串行版本十分简单：

```java
public class RankSort {
    public static List<Integer> rankSort(List<Integer> numbers) {
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        for (int i = 0; i < numbers.size(); i++) {
            int rank = 0;
            for (int j = 0; j < numbers.size(); j++) {
                if (numbers.get(j) < numbers.get(i)
                        || (numbers.get(j) == numbers.get(i) && j < i)) {
                    rank++;
                }
            }
            sortedNumbers.set(rank, numbers.get(i));
        }
        return sortedNumbers;
    }
}
```

我们将其修改为并行版本，也就是要将最外层的循环并行化：

```java
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
            if (numbers.get(j) < numbers.get(i)
                    || (numbers.get(j) == numbers.get(i) && j < i)) {
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
```

这里我们依然是要通过 **构造函数** `ParallelRankSort` 将参数保存在对象的 **成员变量** 里，以便重载的 `run()` 方法调用。

为了并行地进行外层循环，我们使用了 `Executors.newFixedThreadPool(8)` 创建了一个固定 8 个线程的（我的电脑的核心数为 8 个）的线程池对应的 `ExecutorService`。

我们通过 `executor.submit(task)` 将任务一个一个地加入到 `ExecutorService` 里，最后通过 `executor.shutdown()` 和 `executor.awaitTermination(60, TimeUnit.SECONDS)` 等待线程执行结束后，返回最后的结果 `sortedNumbers`。


## 四、运行时间


## 五、技术要点



