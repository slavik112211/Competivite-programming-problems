package competivite_programming_problems;

import java.util.ArrayList;
import java.util.Arrays;

public class IntCharSorter
{
    public ArrayList<Element> inputList;
    public IntCharSorter(){
        this.inputList = new ArrayList<Element>();
    }
    public IntCharSorter(ArrayList<Element> inputList){
        this.inputList = inputList;
    }
    
    public static class Element {
        public enum Type {INTEGER, CHAR}
        public int number;
        public char character;
        public Boolean isNumber=false, isCharacter=false;
        
        public Element(int number){
            this.number = number;
            isNumber=true;
        }
        public Element(char character){
            this.character = character;
            isCharacter=true;
        }

        public boolean isComparable(Element other){
            return (this.isNumber && other.isNumber) ||
                   (this.isCharacter && other.isCharacter) ? true : false;
        }

        public String toString(){
            return isNumber ? Integer.toString(number) : ""+character;
        }

        public boolean equals(Object other){
            Element otherEl = (Element) other;
            return ((this.isNumber && otherEl.isNumber && this.number==otherEl.number) ||
                    (this.isCharacter && otherEl.isCharacter && this.character==otherEl.character)) ? true : false;
        }
    }

    public void merge(int leftIndex, int middleIndex, int rightIndex){
        int totalSize = rightIndex-leftIndex+1;
        int i, resultArrayIndex=leftIndex, compareResult;
        Element leftEl1, leftEl2, rightEl1, rightEl2;
        ArrayList<Element> result = new ArrayList<Element>(totalSize);
        Element.Type currentPositionType;
        int iteratorLeft1=leftIndex, iteratorLeft2=leftIndex;
        int iteratorRight1=middleIndex+1, iteratorRight2=middleIndex+1;

        while(resultArrayIndex<=rightIndex){
            leftEl1  = (iteratorLeft1<=middleIndex) ? inputList.get(iteratorLeft1)  : null;
            leftEl2  = (iteratorLeft2<=middleIndex) ? inputList.get(iteratorLeft2)  : null;
            rightEl1 = (iteratorRight1<=rightIndex) ? inputList.get(iteratorRight1) : null;
            rightEl2 = (iteratorRight2<=rightIndex) ? inputList.get(iteratorRight2) : null;

            currentPositionType=determinePositionType(resultArrayIndex);
            if(currentPositionType==Element.Type.INTEGER){
                if(areBothNull(leftEl1, rightEl1)) break;
                if(areNumbers(leftEl1, rightEl1) || oneIsNullAndOtherIsNumber(leftEl1, rightEl1)){
                    compareResult=compareElements(Element.Type.INTEGER, leftEl1, rightEl1);
                    if(compareResult==-1){
                        result.add(leftEl1);
                        iteratorLeft1++;
                    }else{
                        result.add(rightEl1);
                        iteratorRight1++;
                    }
                    resultArrayIndex++;
                }else if(leftIsNumberOrNullAndRightIsCharacter(leftEl1,rightEl1)){
                    iteratorRight1++;
                }else if(leftIsCharacterAndRightIsNumberOrNull(leftEl1,rightEl1)){
                    iteratorLeft1++;
                }else if(areCharacters(leftEl1,rightEl1)){
                    iteratorLeft1++;
                    iteratorRight1++;
                }
            }
            else if(currentPositionType==Element.Type.CHAR){
                if(areBothNull(leftEl2, rightEl2)) break;
                else if(areCharacters(leftEl2,rightEl2) || oneIsNullAndOtherIsCharacter(leftEl2,rightEl2)){
                    compareResult=compareElements(Element.Type.CHAR, leftEl2, rightEl2);
                    if(compareResult==-1){
                        result.add(leftEl2);
                        iteratorLeft2++;
                    }else{
                        result.add(rightEl2);
                        iteratorRight2++;
                    }
                    resultArrayIndex++;
                }else if(leftIsNumberAndRightIsCharacterOrNull(leftEl2,rightEl2)){
                    iteratorLeft2++;
                }else if(leftIsCharacterOrNullAndRightIsNumber(leftEl2,rightEl2)){
                    iteratorRight2++;
                }else if(areNumbers(leftEl2, rightEl2)){
                    iteratorLeft2++;
                    iteratorRight2++;
                }
            }
        }
        for(i=leftIndex;i<=rightIndex;i++){
            inputList.set(i, result.get(i-leftIndex));
        }
    }

    private static boolean areBothNull(Element left, Element right){
        return (left==null && right==null)?true:false;
    }
    private static boolean areNumbers(Element left, Element right){
        return (left!=null && right!=null && left.isNumber && right.isNumber)?true:false;
    }
    private static boolean areCharacters(Element left, Element right){
        return (left!=null && right!=null && left.isCharacter && right.isCharacter)?true:false;
    }
    private static boolean oneIsNullAndOtherIsNumber(Element left, Element right){
        return ((left==null && right!=null && right.isNumber) ||
                (right==null && left!=null && left.isNumber))?true:false;
    }
    private static boolean oneIsNullAndOtherIsCharacter(Element left, Element right){
        return ((left==null && right!=null && right.isCharacter) ||
                (right==null && left!=null && left.isCharacter))?true:false;
    }
    private static boolean leftIsNumberOrNullAndRightIsCharacter(Element left, Element right){
        return ((left==null || left.isNumber) && (right!=null && right.isCharacter))?true:false;
    }
    private static boolean leftIsNumberAndRightIsCharacterOrNull(Element left, Element right){
        return ((left!=null && left.isNumber) && (right==null || right.isCharacter))?true:false;
    }
    private static boolean leftIsCharacterAndRightIsNumberOrNull(Element left, Element right){
        return ((left!=null && left.isCharacter) && (right==null || right.isNumber))?true:false;
    }
    private static boolean leftIsCharacterOrNullAndRightIsNumber(Element left, Element right){
        return ((left==null || left.isCharacter) && (right!=null && right.isNumber))?true:false;
    }
    private static int compareElements(Element.Type type, Element left, Element right){
        int result=0;
        if(left!=null && right==null) result=-1;
        else if(left==null && right!=null) result=1;
        else if((type==Element.Type.INTEGER && left.number<=right.number) ||
                (type==Element.Type.CHAR && left.character<=right.character)) result=-1;
        else result=1;
        return result;
    }
    private Element.Type determinePositionType(int index){
        return inputList.get(index).isNumber ? Element.Type.INTEGER : Element.Type.CHAR;
    }
    
    public void mergeSort(int leftIndex, int rightIndex) {
        if(rightIndex<=leftIndex) return;
        int middleIndex = leftIndex+(rightIndex-leftIndex)/2;
        mergeSort(leftIndex, middleIndex);
        mergeSort(middleIndex+1, rightIndex);
        merge(leftIndex, middleIndex, rightIndex);
    }
    
    public static void main(String[] args){
        Element[] elements = new Element[]{
            new Element('b'), new Element(4), new Element('c'), new Element(8), new Element('d'), new Element(9),
            new Element(2), new Element('a'), new Element(3), new Element(4),new Element('c')};
        IntCharSorter sorter = new IntCharSorter(new ArrayList<Element>(Arrays.asList(elements)));
        sorter.merge(0,5,sorter.inputList.size()-1);
        System.out.println(sorter.inputList);
        //[a, 2, b, 3, c, 4, 4, c, 8, 9, d]
        
        Element[] elements2 = new Element[]{
            new Element(8), new Element('c'), new Element(2), new Element('e'), new Element(3),
            new Element('d'), new Element(4), new Element('b'), new Element('a')};
        sorter.inputList = new ArrayList<Element>(Arrays.asList(elements2));
        sorter.mergeSort(0,sorter.inputList.size()-1);
        System.out.println(sorter.inputList);
        //[2, a, 3, b, 4, c, 8, d, e]
    }
}
