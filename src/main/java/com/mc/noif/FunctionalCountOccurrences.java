package com.mc.noif;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class FunctionalCountOccurrences implements CountOccurrences {
    
    static final Constant<Integer> zero = new Constant(0);
    static final Constant<Integer> negativeOne = new Constant(-1);
    
    @Override
    public int find(final int target, final int[] arr) {
        return (Integer) B.ool(arr == null || arr.length == 0).branch(
            zero,
            new Function<Integer>() {
                public Integer execute() {
                    int left = findLeft(target, arr);
                    int right = findRight(target, arr);
                    return B.ool(left == -1 && right == -1).branch(
                        zero,
                        new Range(left, right).size
                    );
                }
            }
        );
    }
    
    private int findLeft (final int target, final int[] arr) {
        final Range range = new Range(0, arr.length-1);
        while (range.max - range.min > 1) {
            B.ool(arr[range.mid.execute()] >= target).branch(
                range.moveMax,
                range.moveMin
            );
        }
        return range.minMaxNone(target, arr).execute();
    }
    
    private int findRight (final int target, final int[] arr) {
        final Range range = new Range(0, arr.length-1);
        while (range.max - range.min > 1) {
            B.ool(arr[range.mid.execute()] <= target).branch(
                range.moveMin,
                range.moveMax
            );
        }
        return range.maxMinNone(target, arr).execute();
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
        
        public Function<Integer> minMaxNone(final int target, final int[] arr) {
            return new Function<Integer>() {
                public Integer execute() {
                    return B.ool(arr[min] == target).branch(
                        new Constant(min),
                        new Function<Integer>() {
                            public Integer execute() {
                                return B.ool(arr[max] == target).branch(
                                    new Constant(max),
                                    negativeOne
                                );
                            }
                        }
                    );
                }
            };
        }
        
        public Function<Integer> maxMinNone(final int target, final int[] arr) {
            return new Function<Integer>() {
                public Integer execute() {
                    return B.ool(arr[max] == target).branch(
                        new Constant(max),
                        new Function<Integer>() {
                            public Integer execute() {
                                return B.ool(arr[min] == target).branch(
                                    new Constant(min),
                                    negativeOne
                                );
                            }
                        }
                    );
                }
            };
        }
    }
}
