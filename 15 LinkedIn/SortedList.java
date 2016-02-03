public class SortedList
{
  public static CNode insertSortedList(CNode start, int n)
  {
    CNode insertedNode = new CNode(n);
    boolean positionFound = false;
    CNode prevNode = start, nextNode;
    
    while(!positionFound){
    	if(prevNode.next==start){ //Either first node (smallest), or last node (largest)
    		positionFound=true;
        	prevNode.next=insertedNode;
        	insertedNode.next=start;
    	}else if(insertedNode.value>=prevNode.value && insertedNode.value<=prevNode.next.value){
        	positionFound=true;
            nextNode = prevNode.next;
            prevNode.next=insertedNode;
            insertedNode.next=nextNode;
        }else{
            prevNode=prevNode.next;
        }
    }
    return insertedNode;
  }

  
  public static void main(String[] args){
	  SortedList sortedList = new SortedList();
	  CNode start = new CNode(1);
	  start.next=start;
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
