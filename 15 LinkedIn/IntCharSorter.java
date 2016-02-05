import java.util.ArrayList;

public class IntCharSorter
{
	public static class Element {
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
			return (isNumber==true) ? Integer.toString(number) : ""+character;
		}
	}
	
	
  public static Element[] merge(Element[] listA, Element[] listB)
  {
	  int totalSize = listA.length+listB.length;
	  int i, resultArrayIndex=0,leftIndex=0,rightIndex=0;
	  Element left,right;
	  Element[] result = new Element[totalSize];
	  ArrayList<Integer> intPositions = new ArrayList<Integer>();
	  ArrayList<Integer> charPositions = new ArrayList<Integer>();
	  int intPositionsIterator=0, charPositionsIterator=0;
	  
	  //1. Determine positions of int/char
	  for(i=0; i<listA.length;i++){
		  if(listA[i].isNumber) intPositions.add(resultArrayIndex);
		  else charPositions.add(resultArrayIndex);
		  resultArrayIndex++;
	  }
	  for(i=0; i<listB.length;i++){
		  if(listB[i].isNumber) intPositions.add(resultArrayIndex);
		  else charPositions.add(resultArrayIndex);
		  resultArrayIndex++;
	  }
	  
	  //2. merge integers
	  for(i=0;i<totalSize;i++){
		  left = (leftIndex<listA.length) ? listA[leftIndex] : new Element(Integer.MAX_VALUE);
		  right = (rightIndex<listB.length) ? listB[rightIndex] : new Element(Integer.MAX_VALUE);

		  if(left.isNumber && right.isNumber){
			  if(left.number<=right.number){
				  result[intPositions.get(intPositionsIterator)]=left;
				  leftIndex++;
			  }else{
				  result[intPositions.get(intPositionsIterator)]=right;
				  rightIndex++;
			  }
			  intPositionsIterator++;
		  } else if(left.isNumber){
			  rightIndex++;
		  } else if(right.isNumber){
			  leftIndex++;
		  } else {
			  leftIndex++;
			  rightIndex++;
		  }
	  }
	  
	  //3. merge chars
	  leftIndex=0;
	  rightIndex=0;
	  for(i=0;i<totalSize;i++){
		  left = (leftIndex<listA.length) ? listA[leftIndex] : new Element(Character.MAX_VALUE);
		  right = (rightIndex<listB.length) ? listB[rightIndex] : new Element(Character.MAX_VALUE);

		  if(left.isCharacter && right.isCharacter){
			  if(left.character<=right.character){
				  result[charPositions.get(charPositionsIterator)]=left;
				  leftIndex++;
			  }else{
				  result[charPositions.get(charPositionsIterator)]=right;
				  rightIndex++;
			  }
			  charPositionsIterator++;
		  } else if(left.isCharacter){
			  rightIndex++;
		  } else if(right.isCharacter){
			  leftIndex++;
		  } else {
			  leftIndex++;
			  rightIndex++;
		  }
	  }
	  
	  return result;
  }

  
  public static void main(String[] args){
	  Element[] listA = new Element[6];
	  listA[0]=new Element('b');
	  listA[1]=new Element(4);
	  listA[2]=new Element('c');
	  listA[3]=new Element(8);
	  listA[4]=new Element('d');
	  listA[5]=new Element(9);
	  
	  Element[] listB = new Element[5];
	  listB[0]=new Element(2);
	  listB[1]=new Element('a');
	  listB[2]=new Element(3);
	  listB[3]=new Element(4);
	  listB[4]=new Element('c');
	  
	  Element[] listC = IntCharSorter.merge(listA, listB);
	  System.out.println(listC);
	  //[a, 2, b, 3, c, 4, 4, c, 8, 9, d]
	  
  }
}
