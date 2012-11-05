package com.mc.noif;


abstract class B {
    private static final B[] bools = new B[]{new FALSE(), new TRUE()};
    
    abstract <T> T branch(Function<T> doWhenTrue, Function<T> doWhenFalse);
    
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
        <T> T branch(Function<T> doWhenTrue, Function<T> doWhenFalse) {
            return doWhenTrue.execute();
        }
    }
    
    private static final class FALSE extends B {
        private FALSE() {}
        <T> T branch(Function<T> doWhenTrue, Function<T> doWhenFalse) {
            return doWhenFalse.execute();
        }
    }
}