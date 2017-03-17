package hcu.algorithmanalysis;

import java.util.Arrays;

/**
 * Created by stava on 17.03.2017.
 */

public class ZamanHesaplayici {


    private Watch mWatch;


    public ZamanHesaplayici() {
        mWatch = new Watch();
    }

    public long bubbleSortTime(int[] array) {
        mWatch.start();
        boolean swapped = true;
        int temp;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                }
            }
        }
        return mWatch.getTime();
    }

    public long mergeSortTime(int[] array) {
        mWatch.start();
        mergeSort(array);
        return mWatch.getTime();
    }

    private int[] mergeSort(int[] array) {
        if (array.length <= 1) {
            return array;
        }

        // Split the array in half
        int[] first = new int[array.length / 2];
        int[] second = new int[array.length - first.length];
        System.arraycopy(array, 0, first, 0, first.length);
        System.arraycopy(array, first.length, second, 0, second.length);

        mergeSort(first);
        mergeSort(second);

        merge(first, second, array);
        return array;
    }


    private void merge(int[] first, int[] second, int[] result) {
        int firstArrayIndex = 0;
        int secondArrayIndex = 0;
        int j = 0;

        while (firstArrayIndex < first.length && secondArrayIndex < second.length) {
            if (first[firstArrayIndex] < second[secondArrayIndex]) {
                result[j] = first[firstArrayIndex];
                firstArrayIndex++;
            } else {
                result[j] = second[secondArrayIndex];
                secondArrayIndex++;
            }
            j++;
        }
        System.arraycopy(first, firstArrayIndex, result, j, first.length - firstArrayIndex);
        System.arraycopy(second, secondArrayIndex, result, j, second.length - secondArrayIndex);
    }


    public long quickSortTime(int[] array) {
        mWatch.start();
        Arrays.sort(array);
        return mWatch.getTime();
    }


    public long insertionSortTime(int[] array) {
        mWatch.start();
        for (int i = 1; i < array.length; i++) {
            int currentVal = array[i];
            int j;
            for (j = i; j > 0 && array[j - 1] > currentVal; j--) {
                array[j] = array[j - 1];
            }
            array[j] = currentVal;
        }
        return mWatch.getTime();
    }


    public long selectionSortTime(int[] array) {
        mWatch.start();
        int temp;
        for (int i = 0; i < array.length - 1; i++) {
            int minValIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] <= array[minValIndex]) {
                    minValIndex = j;
                }
            }
            temp = array[minValIndex];
            array[minValIndex] = array[i];
            array[i] = temp;
        }
        return mWatch.getTime();
    }

}
