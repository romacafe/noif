package com.mc.noif;
import java.util.HashMap;
import java.util.Map;


public class FindRange {
    
    Function zero = new Scalar(0);
    Function negativeOne = new Scalar(-1);
    
    public int find(final int target, final int[] arr) {
        return (Integer) B.ool(isArrayEmpty(arr)).branch(
            zero,
            new Function() {
                public Object execute() {
                    return B.ool(isArrayEmpty(arr)).branch(
                        zero,
                        new Function() {
                            public Object execute() {
                                int left = findLeft(target, arr);
                                int right = findRight(target, arr);
                                return B.ool(left == -1 && right == -1).branch(
                                    zero,
                                    new RangeSize(left, right));
                            }
                        }
                    );
                }
            }
        );
    }

    private boolean isArrayEmpty(final int[] arr) {
        return arr == null || arr.length == 0;
    }
    
    private int findLeft (final int target, final int[] arr) {
        final Range range = new Range(0, arr.length-1);
        while (range.max - range.min > 1) {
            B.ool(arr[range.mid()] == target).branch(
                new Function() {
                    public Object execute() {
                        return range.max = range.mid();
                    }
                },
                new Function() {
                    public Object execute() {
                        return range.min = range.mid();
                    }
                });
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
            B.ool(arr[range.mid()] == target).branch(
                new Function() {
                    public Object execute() {
                        return range.min = range.mid();
                    }
                },
                new Function() {
                    public Object execute() {
                        return range.max = range.mid();
                    };
                }
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
    
    private class Range {
        public Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
        int min;
        int max;
        int mid() {return (min + max)/2;}
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
    
    static class RangeSize implements Function {
        final int left;
        final int right;
        public RangeSize(int left, int right) {
            this.left = left;
            this.right = right;
        }
        public Object execute() {
            return right - left +1;
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
