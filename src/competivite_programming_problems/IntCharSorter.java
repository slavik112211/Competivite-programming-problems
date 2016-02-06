package competivite_programming_problems;

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
			return isNumber ? Integer.toString(number) : ""+character;
		}
		
		public boolean equals(Object other){
			Element otherEl = (Element) other;
			return ((this.isNumber && otherEl.isNumber && this.number==otherEl.number) ||
					(this.isCharacter && otherEl.isCharacter && this.character==otherEl.character)) ? true : false;
		}
	}
	
	
  public static ArrayList<Element> merge(ArrayList<Element> listA, ArrayList<Element> listB){
	  int totalSize = listA.size()+listB.size();
	  int i, resultArrayIndex=0;
	  Element leftEl1, leftEl2, rightEl1, rightEl2;
	  ArrayList<Element> result = new ArrayList<Element>(totalSize);
	  boolean[] intPositions=new boolean[totalSize];
	  int iteratorLeft1=0, iteratorLeft2=0, iteratorRight1=0, iteratorRight2=0;
	  
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
			  //a. One of the iterators is out of bounds
			  if(leftEl1==null && rightEl1.isCharacter){
				  iteratorRight1++;
			  }else if(leftEl1==null && rightEl1.isNumber){
				  result.add(rightEl1);
				  iteratorRight1++;
				  resultArrayIndex++;
			  } else if(leftEl1.isCharacter && rightEl1==null){
				  iteratorLeft1++;
			  } else if(leftEl1.isNumber && rightEl1==null){
				  result.add(leftEl1);
				  iteratorLeft1++;
				  resultArrayIndex++;
			  }
			//b.both iterators are still within bounds
			  else if(leftEl1.isNumber && rightEl1.isNumber){
				  if(leftEl1.number<=rightEl1.number){
					  result.add(leftEl1);
					  iteratorLeft1++;
				  }else{
					  result.add(rightEl1);
					  iteratorRight1++;
				  }
				  resultArrayIndex++;
			  }else if(leftEl1.isCharacter && rightEl1.isCharacter){
				  iteratorLeft1++;
				  iteratorRight1++;
			  }else if(leftEl1.isNumber && rightEl1.isCharacter){
				  iteratorRight1++;
			  }else if(leftEl1.isCharacter && rightEl1.isNumber){
				  iteratorLeft1++;
			  }
		  }
		  else{ //2. Current position in result[] requires a character
			  
			  //a. One of the iterators is out of bounds
			  if(leftEl2==null && rightEl2.isCharacter){
				  result.add(rightEl2);
				  iteratorRight2++;
				  resultArrayIndex++;
			  }else if(leftEl2==null && rightEl2.isNumber){
				  iteratorRight2++;
			  } else if(leftEl2.isCharacter && rightEl2==null){
				  result.add(leftEl2);
				  iteratorLeft2++;
				  resultArrayIndex++;
			  } else if(leftEl2.isNumber && rightEl2==null){
				  iteratorLeft2++;
			  }
			  //b.both iterators are still within bounds
			  else if(leftEl2.isCharacter && rightEl2.isCharacter){
				  if(leftEl2.character<=rightEl2.character){
					  result.add(leftEl2);
					  iteratorLeft2++;
				  }else{
					  result.add(rightEl2);
					  iteratorRight2++;
				  }
				  resultArrayIndex++;
			  }else if(leftEl2.isNumber && rightEl2.isNumber){
				  iteratorLeft2++;
				  iteratorRight2++;
			  }else if(leftEl2.isNumber && rightEl2.isCharacter){
				  iteratorLeft2++;
			  }else if(leftEl2.isCharacter && rightEl2.isNumber){
				  iteratorRight2++;
			  }
		  }
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
	  
	  ArrayList<Element> listC = IntCharSorter.merge(listA, listB);
	  System.out.println(listC);
	  //[a, 2, b, 3, c, 4, 4, c, 8, 9, d]
	  
  }
}
