package org.stone.Helper.algorithm;

/**
 * <bubble sort algorithm>
 * 
 * @author wu_firefox@163.com
 * @since 2020-02-22
 *
 */
public class BubbleSort {
	public static int[] bubbling(int[] array) {
		int len = array.length;
		if (array == null || len == 0) {
			return array;
		}
		int temp = 0;
		int lastSwapIndex = 0;// 最后一次交换位置索引
		int needSortIndex = len - 1;// 实际需要对比的区间[0-needSortIndex],[needSortIndex-len]区间已排序,无需再进行没必要的对比排序
		boolean isSwap;// 一次对比下来是否有交换,没交换表示数据本身已经是有序的了
		// 第一层for循环表示"冒泡次数",一次冒泡,只能将一个最大或者最小数据冒泡到最上面
		for (int i = 0; i < len - 1; i++) {
			isSwap = false;
			if (i > 0) {
				needSortIndex = lastSwapIndex;
			}
			// 第二层for循环表示每次冒泡需要两两对比的元素
			for (int j = 0; j < needSortIndex; j++) {
				if (array[j] > array[j + 1]) {
					temp = array[j + 1];
					array[j + 1] = array[j];
					array[j] = temp;
					lastSwapIndex = j + 1;
					isSwap = true;
				}
			}
			if (!isSwap) {
				break;
			}
		}
		return array;
	}
}
