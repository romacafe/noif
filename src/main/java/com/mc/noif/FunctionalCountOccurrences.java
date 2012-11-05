package com.mc.noif;

public class FunctionalCountOccurrences implements CountOccurrences {
    
    Function zero = new Scalar(0);
    Function negativeOne = new Scalar(-1);
    
    @Override
    public int find(int target, int[] arr) {
        return (Integer) B.ool(arr == null || arr.length == 0).branch(
            zero,
            new TheAlgorithm(target, arr)
        );
    }
    
    private class TheAlgorithm implements Function {
        int target;
        int[] arr;
        
        public TheAlgorithm(int target, int[] arr) {
            this.target = target;
            this.arr = arr;
        }
        public Object execute() {
            int left = findLeft(target, arr);
            int right = findRight(target, arr);
            return B.ool(left == -1 && right == -1).branch(
                zero,
                new Result(Range.size(left, right))
            );
        }
    }
    
    private int findLeft (final int target, final int[] arr) {
        final Range range = new Range(0, arr.length-1);
        while (range.max - range.min > 1) {
            B.ool(arr[range.mid()] >= target).branch(
                range.moveMax,
                range.moveMin
            );
        }
        return (Integer) B.ool(arr[range.min] == target).branch(
            new Scalar(range.min),
            new Function() {
                public Object execute() {
                    return B.ool(arr[range.max] == target).branch(
                        new Scalar(range.max),
                        negativeOne
                    );
                }
            }
        );
    }
    
    private int findRight (final int target, final int[] arr) {
        final Range range = new Range(0, arr.length-1);
        while (range.max - range.min > 1) {
            B.ool(arr[range.mid()] <= target).branch(
                range.moveMin,
                range.moveMax
            );
        }
        return (Integer) B.ool(arr[range.max] == target).branch(
            new Scalar(range.max),
            new Function() {
                public Object execute() {
                    return B.ool(arr[range.min] == target).branch(
                        new Scalar(range.min),
                        negativeOne
                     );
                };
            }
        );
    }
    
    
    
    private static class Range {
        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
        int min;
        int max;
        
        int mid() {return (min + max)/2;}
        
        static Function size(final int min, final int max) {
            return new Function() {
                public Object execute() {
                    return max - min + 1;
                }
            };
        }
        
        Function moveMax = new Function() {
            public Object execute() {
                max = mid();
                return null;
            }
        };
        Function moveMin = new Function() {
            public Object execute() {
                min = mid();
                return null;
            }
        };
    }
    
    static interface Function {
        public Object execute();
    }
    
    static class Scalar implements Function {
        final Object val;
        public Scalar(Object val) {
            this.val = val;
        }
        public Object execute() {
            return val;
        }
    }
    
    static class Result implements Function {
        final Function function;
        public Result(Function function) {
            this.function = function;
        }
        public Object execute() {
            return function.execute();
        }
    }
    
    static abstract class B {
        private static final B[] bools = new B[]{new FALSE(), new TRUE()};
        
        abstract Object branch(Function doWhenTrue, Function doWhenFalse);
        
        public static B ool(boolean eval) {
            int boolIndex = getIndex(eval);
            return bools[boolIndex];
        }
        
        private static final int getIndex(boolean eval) {
            //magic method, wish Java allowed casting booleans to ints
            return eval ? 1 : 0;
        }
        
        private static final class TRUE extends B {
            private TRUE() {}
            Object branch(Function doWhenTrue, Function doWhenFalse) {
                return doWhenTrue.execute();
            }
        }
        
        private static final class FALSE extends B {
            private FALSE() {}
            Object branch(Function doWhenTrue, Function doWhenFalse) {
                return doWhenFalse.execute();
            }
        }
    }
}
