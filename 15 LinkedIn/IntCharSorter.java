package competivite_programming_problems;

import java.util.ArrayList;

public class IntCharSorter
{
    private Integer iteratorLeft1=0, iteratorLeft2=0, iteratorRight1=0, iteratorRight2=0;
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

    public ArrayList<Element> merge(ArrayList<Element> listA, ArrayList<Element> listB){
        int totalSize = listA.size()+listB.size();
        int i, resultArrayIndex=0, compareResult;
        Element leftEl1, leftEl2, rightEl1, rightEl2;
        ArrayList<Element> result = new ArrayList<Element>(totalSize);
        boolean[] intPositions=new boolean[totalSize];
        iteratorLeft1=0; iteratorLeft2=0; iteratorRight1=0; iteratorRight2=0;

        //1. Determine positions of int/char
        for(i=0; i<listA.size();i++){
            intPositions[resultArrayIndex] = listA.get(i).isNumber ? true : false;
            resultArrayIndex++;
        }
        for(i=0; i<listB.size();i++){
            intPositions[resultArrayIndex] = listB.get(i).isNumber ? true : false;
            resultArrayIndex++;
        }

        resultArrayIndex=0;
        while(resultArrayIndex<totalSize){
            leftEl1  = (iteratorLeft1<listA.size())  ? listA.get(iteratorLeft1)  : null;
            leftEl2  = (iteratorLeft2<listA.size())  ? listA.get(iteratorLeft2)  : null;
            rightEl1 = (iteratorRight1<listB.size()) ? listB.get(iteratorRight1) : null;
            rightEl2 = (iteratorRight2<listB.size()) ? listB.get(iteratorRight2) : null;

            //1. Current position in result[] requires a number
            if(intPositions[resultArrayIndex]){
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
            else{ //2. Current position in result[] requires a character
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
        return result;
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
        if(left!=null && right==null){
            result=-1;
        }else if(left==null && right!=null){
            result=1;
        }else if((type==Element.Type.INTEGER && left.number<=right.number) ||
                 (type==Element.Type.CHAR && left.character<=right.character)){
            result=-1;
        }else {
            result=1;
        }
        return result;
    }
    
    public static void main(String[] args){
        ArrayList<Element> listA = new ArrayList<Element>();
        listA.add(new Element('b'));
        listA.add(new Element(4));
        listA.add(new Element('c'));
        listA.add(new Element(8));
        listA.add(new Element('d'));
        listA.add(new Element(9));

        ArrayList<Element> listB = new ArrayList<Element>();
        listB.add(new Element(2));
        listB.add(new Element('a'));
        listB.add(new Element(3));
        listB.add(new Element(4));
        listB.add(new Element('c'));

        IntCharSorter sorter = new IntCharSorter();
        ArrayList<Element> listC = sorter.merge(listA, listB);
        System.out.println(listC);
        //[a, 2, b, 3, c, 4, 4, c, 8, 9, d]

    }
}
