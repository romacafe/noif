package com.mc.noif;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountOccurrencesTest {
    CountOccurrences functional = new FunctionalCountOccurrences();
    CountOccurrences procedural = new ProceduralCountOccurrences();
    
    @Test
    public void testNullArray() throws Exception {
        shouldBe(0, null);
    }
    
    @Test
    public void testEmptyArray() throws Exception {
        shouldBe(0, new int[0]);
    }
    
    @Test
    public void test_4() throws Exception {
        shouldBe(1, array(4));
    }
    
    @Test
    public void test_5() throws Exception {
        shouldBe(0, array(5));
    }
    
    @Test
    public void test_3_5() throws Exception {
        shouldBe(0, array(3, 5));
    }
    
    @Test
    public void test_4_5() throws Exception {
        shouldBe(1, array(4, 5));
    }
    
    @Test
    public void test_445() throws Exception {
        shouldBe(2, array(4,4,5));
    }
    
    @Test
    public void test_344() throws Exception {
        shouldBe(2, array(3,4,4));
    }
    
    @Test
    public void test_444() throws Exception {
        shouldBe(3, array(4,4,4));
    }
    
    @Test
    public void test_555() throws Exception {
        shouldBe(0, array(5,5,5));
    }
    
    @Test
    public void test_skew_right() throws Exception {
        shouldBe(4, array(2,2,4,4,4,4,6,6,8,8,8,9,9,9,9,9,9,9,9,9,9,9,9,9));
    }
    
    @Test
    public void test_skew_left() throws Exception {
        shouldBe(4, array(0,0,0,0,0,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,4,4,4,4,5));
    }
    
    @Test
    public void performance() throws Exception {
        int warmup = 10000;
        int reps   = 100000;
        int[] array = buildTestArray();

        run(array, procedural, warmup);
        run(array, functional, warmup);
        
        long time = System.currentTimeMillis();
        run(array, procedural, reps);
        long latency = System.currentTimeMillis() - time;
        System.out.println("Procedural(ms): " + latency);
        
        time = System.currentTimeMillis();
        run(array, functional, reps);
        latency = System.currentTimeMillis() - time;
        System.out.println("Functional(ms): " + latency);
    }
    

    private void run(int[] array, CountOccurrences counter, int reps) {
        for (int i=0; i<reps; i++) {
            assertEquals(100, counter.find(75, array));
            assertEquals(100, counter.find(50, array));
            assertEquals(100, counter.find(0, array));
        }
    }
    
    private int[] buildTestArray() {
        int[] arr = new int[100*100];
        for (int i=0; i<100; i++) {
            for (int j=0; j<100; j++) {
                int index = (i*100) + j;
                arr[index] = i;
            }
        }
        return arr;
    }

    private void shouldBe(int expected, int[] arr) {
        assertEquals(expected, procedural.find(4, arr));
        assertEquals(expected, functional.find(4, arr));
    }
    
    private int[] array(int... vals) {
        return vals;
    }
}
