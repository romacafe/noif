package com.mc.noif;

public class FunctionalCountOccurrences implements CountOccurrences {
    
    Constant<Integer> zero = new Constant(0);
    Constant<Integer> negativeOne = new Constant(-1);
    
    @Override
    public int find(int target, int[] arr) {
        return (Integer) B.ool(arr == null || arr.length == 0).branch(
            zero,
            new TheAlgorithm(target, arr)
        );
    }
    
    private class TheAlgorithm implements Function<Integer> {
        int target;
        int[] arr;
        
        public TheAlgorithm(int target, int[] arr) {
            this.target = target;
            this.arr = arr;
        }
        public Integer execute() {
            int left = findLeft(target, arr);
            int right = findRight(target, arr);
            return B.ool(left == -1 && right == -1).branch(
                zero,
                new Range(left, right).size
            );
        }
    }
    
    private int findLeft (final int target, final int[] arr) {
        final Range range = new Range(0, arr.length-1);
        while (range.max - range.min > 1) {
            B.ool(arr[range.mid.execute()] >= target).branch(
                range.moveMax,
                range.moveMin
            );
        }
        return B.ool(arr[range.min] == target).branch(
            new Constant(range.min),
            new Function<Integer>() {
                public Integer execute() {
                    return B.ool(arr[range.max] == target).branch(
                        new Constant(range.max),
                        negativeOne
                    );
                }
            }
        );
    }
    
    private int findRight (final int target, final int[] arr) {
        final Range range = new Range(0, arr.length-1);
        while (range.max - range.min > 1) {
            B.ool(arr[range.mid.execute()] <= target).branch(
                range.moveMin,
                range.moveMax
            );
        }
        return (Integer) B.ool(arr[range.max] == target).branch(
            new Constant(range.max),
            new Function<Integer>() {
                public Integer execute() {
                    return B.ool(arr[range.min] == target).branch(
                        new Constant(range.min),
                        negativeOne
                     );
                };
            }
        );
    }
    
    private static class Range {
        private int min;
        private int max;
        
        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
        
        public Function<Integer> size = new Function<Integer>() {
            public Integer execute() {
                return max - min + 1;
            }
        };
        
        public Function<Integer> mid = new Function<Integer>() {
            public Integer execute() {
                return (min + max) /2;
            }
        };
        
        public Function<Void> moveMax = new Function<Void>() {
            public Void execute() {
                max = mid.execute();
                return null;
            }
        };
        
        public Function<Void> moveMin = new Function<Void>() {
            public Void execute() {
                min = mid.execute();
                return null;
            }
        };
    }
}
