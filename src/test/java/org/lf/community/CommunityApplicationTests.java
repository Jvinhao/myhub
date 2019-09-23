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
        int[] arr = {1,4,2,5,26,7,2,5};
        System.out.println(fun(arr,arr.length,1));
    }

    public int fun(int[] arr, int n, int upordown) {
        int max = 0,min = 0;
        if(upordown == 1) {
            for (int i = 0; i < n; i++) {
                if(max <= arr[i]) {
                    max = arr[i];
                }
            }
            int temp = arr[0];
            arr[0] = max;
            max = temp;
            return arr[0];
        }else if(upordown == 0) {
            for (int i = 0; i < n; i++) {
                if(min >= arr[i]) {
                    min = arr[i];
                }
            }
            int temp = arr[0];
            arr[0] = min;
            min = temp;
            return arr[0];
        }else {
            return 0;
        }
    }

}
