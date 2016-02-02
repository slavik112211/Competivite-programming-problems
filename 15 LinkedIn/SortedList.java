// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
// CNode CLASS IS DEFINED BY DEFAULT
// CLASS BEGINS, THIS CLASS IS REQUIRED
public class SortedList
{
  //METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
  public static CNode insertSortedList(CNode start, int n)
  {
    CNode insertedNode = new CNode(n);
    boolean positionFound = false;
    CNode prevNode = start;
    CNode nextNode;
    if(insertedNode.value<=prevNode.value){
        positionFound = true;
        prevNode=null;
    }
    
    while(!positionFound){
        if(insertedNode.value>=prevNode.value &&
        	(prevNode.next==null || (prevNode.next!=null && insertedNode.value<=prevNode.next.value))){
        	positionFound=true;
        	break;
        }else{
            prevNode=prevNode.next;
        }
    }
    if(prevNode==null){
        insertedNode.next=start;
    }else{
        nextNode = prevNode.next;
        prevNode.next=insertedNode;
        insertedNode.next=nextNode;
        
    }
    return insertedNode;
  }

  
  public static void main(String[] args){
	  SortedList sortedList = new SortedList();
	  CNode start = new CNode(1);
	  sortedList.insertSortedList(start, 3);
	  sortedList.insertSortedList(start, 4);
	  sortedList.insertSortedList(start, 5);
	  sortedList.insertSortedList(start, 0);
	  sortedList.insertSortedList(start, 2);
	  
  }
}

class CNode {
    public int value;
    public CNode next;
    public CNode(int value){
        this.value = value;
    }
} 
