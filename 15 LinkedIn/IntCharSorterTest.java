import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import competivite_programming_problems.IntCharSorter;
import competivite_programming_problems.IntCharSorter.Element;

public class IntCharSorterTest {
    ArrayList<Element> result = new ArrayList<Element>();
    ArrayList<Element> sampleElements = new ArrayList<Element>();
    IntCharSorter sorter;

    public IntCharSorterTest(){
        Element[] elems = new Element[]{
            new Element('a'), new Element('b'), new Element('c'), new Element('d'),
            new Element(1), new Element(2), new Element(3), new Element(4), new Element(8)};
        sampleElements=new ArrayList<Element>(Arrays.asList(elems));
        sorter = new IntCharSorter();
    }

    @Before
    public void setUp(){
        sorter.inputList.clear();
        result.clear();
    }

    //-------------------------------------------------------------
    // 1. Tests for merging of two arrays 
    
    @Test
    public void shouldMergeEmptyArrays() {
        sorter.merge(0,0,sorter.inputList.size()-1);
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldMergeOneElementArrayWithEmptyArray() {
        sorter.inputList.add(sampleElements.get(0));
        sorter.merge(0,0,sorter.inputList.size()-1);
        result.add(sampleElements.get(0));
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldMergeOneElementArrays1() { //number,number
        sorter.inputList.add(sampleElements.get(6));
        sorter.inputList.add(sampleElements.get(4));
        sorter.merge(0,0,sorter.inputList.size()-1);
        result.add(sampleElements.get(4));
        result.add(sampleElements.get(6));
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldMergeOneElementArrays2() { //char,number
        sorter.inputList.add(sampleElements.get(6));
        sorter.inputList.add(sampleElements.get(2));
        sorter.merge(0,0,sorter.inputList.size()-1);
        result.add(sampleElements.get(6));
        result.add(sampleElements.get(2));
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldMergeOneElementArrays3() { //number,char
        sorter.inputList.add(sampleElements.get(6));
        sorter.inputList.add(sampleElements.get(1));
        sorter.merge(0,0,sorter.inputList.size()-1);
        result.add(sampleElements.get(6));
        result.add(sampleElements.get(1));
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldMergeOneElementArrays4() { //char,char
        sorter.inputList.add(sampleElements.get(3));
        sorter.inputList.add(sampleElements.get(2));
        sorter.merge(0,0,sorter.inputList.size()-1);
        result.add(sampleElements.get(2));
        result.add(sampleElements.get(3));
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldMergeEvenArrays() {
        Element[] elems1 = new Element[]{
            new Element('b'), new Element(4), new Element('c'), new Element(8),new Element('d'),
            new Element(2), new Element('a'), new Element(3), new Element(4),new Element('c')};
        sorter.inputList=new ArrayList<Element>(Arrays.asList(elems1));
        sorter.merge(0,4,sorter.inputList.size()-1);

        Element[] elems3 = new Element[]{
            new Element('a'), new Element(2), new Element('b'),new Element(3), new Element('c'),
            new Element(4), new Element('c'), new Element(4), new Element(8), new Element('d')};
        result=new ArrayList<Element>(Arrays.asList(elems3));
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldMergeUnevenArrays() {
        Element[] elements = new Element[]{
            new Element('b'), new Element(4), new Element('c'), new Element(8), new Element('d'), new Element(9),
            new Element(2), new Element('a'), new Element(3), new Element(4),new Element('c')};
        sorter.inputList = new ArrayList<Element>(Arrays.asList(elements));
        sorter.merge(0,5,sorter.inputList.size()-1);
        
        Element[] resultEl = new Element[]{
            new Element('a'), new Element(2), new Element('b'),new Element(3), new Element('c'), new Element(4),
            new Element(4), new Element('c'), new Element(8), new Element(9), new Element('d')};
        result=new ArrayList<Element>(Arrays.asList(resultEl));
        assertEquals(result,sorter.inputList);
    }
    
    //-------------------------------------------------------------
    // 2. Tests for sorting implementation (Complete MergeSort for IntChar mixed arrays)
    
    @Test
    public void shouldSortEmptyArrays() {
        sorter.mergeSort(0,sorter.inputList.size()-1);
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldSortOneElementArrays() {
        sorter.inputList.add(sampleElements.get(0));
        sorter.mergeSort(0,sorter.inputList.size()-1);
        result.add(sampleElements.get(0));
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldSortEvenArrays() {
        Element[] elements = new Element[]{
            new Element(8), new Element('c'), new Element(4), new Element('e'),
            new Element('d'), new Element(3), new Element('b'), new Element('a')};
        sorter.inputList = new ArrayList<Element>(Arrays.asList(elements));
        sorter.mergeSort(0,sorter.inputList.size()-1);
        
        Element[] resultEl = new Element[]{
            new Element(3), new Element('a'), new Element(4),new Element('b'),
            new Element('c'), new Element(8), new Element('d'), new Element('e')};
        result=new ArrayList<Element>(Arrays.asList(resultEl));
        assertEquals(result,sorter.inputList);
    }

    @Test
    public void shouldSortUnevenArrays() {
        Element[] elements = new Element[]{
            new Element(8), new Element('c'), new Element(4), new Element('e'), new Element(2),
            new Element('d'), new Element(3), new Element('b'), new Element('a')};
        sorter.inputList = new ArrayList<Element>(Arrays.asList(elements));
        sorter.mergeSort(0,sorter.inputList.size()-1);
        
        Element[] resultEl = new Element[]{
            new Element(2), new Element('a'), new Element(3),new Element('b'), new Element(4),
            new Element('c'), new Element(8), new Element('d'), new Element('e')};
        result=new ArrayList<Element>(Arrays.asList(resultEl));
        assertEquals(result,sorter.inputList);
    }
}