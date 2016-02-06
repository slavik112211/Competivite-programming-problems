import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import competivite_programming_problems.IntCharSorter;
import competivite_programming_problems.IntCharSorter.Element;

public class IntCharSorterTest {

    ArrayList<Element> listA = new ArrayList<Element>();
    ArrayList<Element> listB = new ArrayList<Element>();
    ArrayList<Element> listC = new ArrayList<Element>();
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
        listA.clear();
        listB.clear();
        listC.clear();
        result.clear();
    }

    @Test
    public void shouldMergeEmptyArrays() {
        listC = sorter.merge(listA, listB);
        result = new ArrayList<Element>();
        assertEquals(listC,result);
    }

    @Test
    public void shouldMergeOneElementArrayWithEmptyArray() {
        listA.add(sampleElements.get(0));
        listC = sorter.merge(listA, listB);
        result.add(sampleElements.get(0));
        assertEquals(listC,result);
    }

    @Test
    public void shouldMergeOneElementArrays1() { //number,number
        listA.add(sampleElements.get(6));
        listB.add(sampleElements.get(4));
        listC = sorter.merge(listA, listB);
        result.add(sampleElements.get(4));
        result.add(sampleElements.get(6));
        assertEquals(listC,result);
    }

    @Test
    public void shouldMergeOneElementArrays2() { //char,number
        listA.add(sampleElements.get(6));
        listB.add(sampleElements.get(2));
        listC = sorter.merge(listA, listB);
        result.add(sampleElements.get(6));
        result.add(sampleElements.get(2));
        assertEquals(listC,result);
    }

    @Test
    public void shouldMergeOneElementArrays3() { //number,char
        listA.add(sampleElements.get(6));
        listB.add(sampleElements.get(1));
        listC = sorter.merge(listA, listB);
        result.add(sampleElements.get(6));
        result.add(sampleElements.get(1));
        assertEquals(listC,result);
    }

    @Test
    public void shouldMergeOneElementArrays4() { //char,char
        listA.add(sampleElements.get(3));
        listB.add(sampleElements.get(2));
        listC = sorter.merge(listA, listB);
        result.add(sampleElements.get(2));
        result.add(sampleElements.get(3));
        assertEquals(listC,result);
    }

    @Test
    public void shouldMergeEvenArrays() {
        Element[] elems1 = new Element[]{
            new Element('b'), new Element(4), new Element('c'), new Element(8),new Element('d')};
        listA=new ArrayList<Element>(Arrays.asList(elems1));

        Element[] elems2 = new Element[]{
            new Element(2), new Element('a'), new Element(3), new Element(4),new Element('c')};
        listB=new ArrayList<Element>(Arrays.asList(elems2));

        Element[] elems3 = new Element[]{
            new Element('a'), new Element(2), new Element('b'),new Element(3), new Element('c'),
            new Element(4), new Element('c'), new Element(4), new Element(8), new Element('d')};
        result=new ArrayList<Element>(Arrays.asList(elems3));

        listC = sorter.merge(listA, listB);
        assertEquals(listC,result);
    }

    @Test
    public void shouldMergeUnevenArrays() {
        Element[] elems1 = new Element[]{
            new Element('b'), new Element(4), new Element('c'), new Element(8), new Element('d'), new Element(9)};
        listA=new ArrayList<Element>(Arrays.asList(elems1));

        Element[] elems2 = new Element[]{
            new Element(2), new Element('a'), new Element(3), new Element(4),new Element('c')};
        listB=new ArrayList<Element>(Arrays.asList(elems2));

        Element[] elems3 = new Element[]{
            new Element('a'), new Element(2), new Element('b'),new Element(3), new Element('c'), new Element(4),
            new Element(4), new Element('c'), new Element(8), new Element(9), new Element('d')};
        result=new ArrayList<Element>(Arrays.asList(elems3));
        listC = sorter.merge(listA, listB);
        assertEquals(listC,result);
    }
}