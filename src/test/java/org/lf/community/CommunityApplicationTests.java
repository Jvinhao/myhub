package org.lf.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() {
        int arr[] = {16,15,1,30,45,23,9};
        quickSort(arr,0,arr.length - 1);
    }

    public static void quickSort(int[] arr, int start, int end) {
        if(start >= end) {
            return;
        }
        int i = start;
        int j = end;
        int key = arr[start];

        while(i < j) {
            while(i < j && arr[j] > key) {
                j--;  //反向寻找
            }
            arr[i] = arr[j];
            while(i < j && arr[i] < key) {
                i++; //正想寻找
            }
            arr[j] = arr[i];
        }
        arr[i] = key;

        for (int n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();

        quickSort(arr,start, i-1);
        quickSort(arr, i + 1, end);

    }
}
