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

- 课程Project：三种排序的串行与并行比较;
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


## 三、Java 实现


## 四、运行时间


## 五、技术要点



