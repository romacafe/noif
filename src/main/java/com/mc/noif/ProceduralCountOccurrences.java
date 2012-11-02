package com.mc.noif;

public class ProceduralCountOccurrences implements CountOccurrences {
    public int find(int target, int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        int left = findLeft(target, arr);
        int right = findRight(target, arr);
        
        if (left == -1 && right == -1) return 0;
        
        return right - left + 1;
    }
    
    private int findLeft (int target, int[] arr) {
        int min = 0;
        int max = arr.length-1;
        while (max - min > 1) {
            int mid = (min+max)/2;
            if (arr[mid] >= target) max = mid;
            else min = mid;
        }
        if (arr[min] == target) return min;
        if (arr[max] == target) return max;
        return -1;
    }
    
    private int findRight (int target, int[] arr) {
        int min = 0;
        int max = arr.length-1;
        while (max - min > 1) {
            int mid = (min+max)/2;
            if (arr[mid] <= target) min = mid;
            else max = mid;
        }
        if (arr[max] == target) return max;
        if (arr[min] == target) return min;
        return -1;
    }
}
