package com.mc.noif;

import static org.junit.Assert.*;

import org.junit.Test;

public class FindRangeTest {
    FindRange finder = new FindRange();
    
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
    public void test_5_4() throws Exception {
        shouldBe(1, array(5, 4));
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
    public void test_444() throws Exception {
        shouldBe(3, array(4,4,4));
    }
    
    @Test
    public void test_555() throws Exception {
        shouldBe(0, array(555));
    }
    
    @Test
    public void test_22444466888() throws Exception {
        System.out.println("start");
        shouldBe(4, array(2,2,4,4,4,4,6,6,8,8,8));
    }
    
    
    
    
    
    

    private void shouldBe(int expected, int[] arr) {
        assertEquals(expected, finder.find(4, arr));
    }
    
    private int[] array(int... vals) {
        return vals;
    }
}
