import java.util.ArrayList;

public class MinHeap {

    private minHeapBuildingNode WayneArray[];
    int arrayCounter;

    public MinHeap() {
        this.WayneArray = new minHeapBuildingNode[100];
        arrayCounter = 0;
    }


    /* Insert Building Info into Heap
    *  Execution time of the building is used as key for heapification
    * */
    public void insertIntoMinHeap(int total_time, int executionTime, redBlackBuildingNode node) {
        minHeapBuildingNode newBuilding = new minHeapBuildingNode(total_time,executionTime,node);

        if(arrayCounter == WayneArray.length) {
            doubleWayneArray();
        }

        WayneArray[arrayCounter]= newBuilding;
        arrayCounter++;
        
        int newBuildingEntry = arrayCounter - 1;
        int parent = findparent(newBuildingEntry);
        isLeafElement(parent);
        while (parent != newBuildingEntry && WayneArray[newBuildingEntry].currentConstructedTime < WayneArray[parent].currentConstructedTime) {
            swapMinHeapNodes(newBuildingEntry, parent);
            newBuildingEntry = parent;
            parent = findparent(newBuildingEntry);
        }

        constructMinHeapTree();
    }

    //Returns the current size of min heap
    //harish
//    public int getSize()
//    {
//        return arrayCounter;
//    }


    /*Function to double the size of the array
    when the array gets filled completely in the present state*/
    private void doubleWayneArray()
    {
        minHeapBuildingNode tempArray[]=new minHeapBuildingNode[2*arrayCounter];
        if(arrayCounter==WayneArray.length)
        {
            for(int i=0;i<arrayCounter;i++)
                tempArray[i]=WayneArray[i];
        }
        isLeafElement(arrayCounter);
        WayneArray = tempArray;
    }

    private void constructMinHeapTree() {

        for (int i = arrayCounter / 2; i >= 0; i--) {
            minHeapify(i);
        }
    }

   /*extractMin provides the minimum element in the Heap i.e. the root node*/
    public minHeapBuildingNode extractMin() {

        if (arrayCounter == 0) {

            throw new IllegalStateException("There is no node in minHeap. It is empty");

        } else if (arrayCounter == 1) {

            minHeapBuildingNode minimumElement = removeElementFromArray(0);
            isLeafElement(arrayCounter);
            --arrayCounter;

            return minimumElement;
        }

        minHeapBuildingNode min = WayneArray[0];
        minHeapBuildingNode lastItem = removeElementFromArray(arrayCounter - 1);
        isLeafElement(arrayCounter);
        WayneArray[0]=lastItem;

        minHeapify(0);
        --arrayCounter;
        return min;
    }

    private void swapMinHeapNodes(int index, int parent) {
        minHeapBuildingNode temp = WayneArray[parent];
        WayneArray[parent] = WayneArray[index];
        WayneArray[index] = temp;
    }

    public minHeapBuildingNode removeElementFromArray(int elementPosition)
    {
        minHeapBuildingNode heapElement = WayneArray[elementPosition];
        for(int i = elementPosition; i<arrayCounter-1; i++)
        {
            WayneArray[i] = WayneArray[i+1];
        }
        return  heapElement;
    }

    private boolean isLeafElement(int k)
    {
        if (k >= (arrayCounter / 2) && k <= arrayCounter) {
            return true;
        }
        return false;
    }

    /* minHeapify method is called to maintain the property of minHeap
    * after every insertion, deletion and extractMin*/
    private void minHeapify(int index) {
        int leftElement = findLeftInMinHeap(index);
        int rightElement = findRightInMinHeap(index);
        int minElement = -1;
        int tempNode = 0;
        int temp_small_node = -1;


        if (leftElement <= arrayCounter - 1 && WayneArray[leftElement].currentConstructedTime <= WayneArray[index].currentConstructedTime) {

            if(WayneArray[leftElement].currentConstructedTime == WayneArray[index].currentConstructedTime) {

                if (WayneArray[leftElement].redBlackNode.ID < WayneArray[index].redBlackNode.ID) {
                    minElement = leftElement;
                }
                else {
                    minElement = index;
                }
            } else {
                minElement = leftElement;
            }
        }
        else {
            minElement = index;
        }


        if (rightElement <= arrayCounter - 1 && WayneArray[rightElement].currentConstructedTime <= WayneArray[minElement].currentConstructedTime) {
            if(WayneArray[rightElement].currentConstructedTime == WayneArray[minElement].currentConstructedTime) {
                if (WayneArray[rightElement].redBlackNode.ID < WayneArray[minElement].redBlackNode.ID) {
                    minElement = rightElement;
                }
                else {
                    minElement = minElement;
                }
            } else {
                minElement = rightElement;
            }
        } else {
            minElement = minElement;
        }

        if (minElement != index) {
            swapMinHeapNodes(index, minElement);
            minHeapify(minElement);
        }

    }

//    public minHeapBuildingNode getMin() {
//
//        return WayneArray[0];
//    }

    private int findparent(int index) {

//        if (index % 2 == 1) {
//            return index / 2;
//        }

        return (index - 1) / 2;
    }

    public boolean isEmpty() {
        if(arrayCounter == 0)
            return true;
        else
            return false;
    }

    private int findRightInMinHeap(int index) {

        return 2 * index + 2;
    }

    private int findLeftInMinHeap(int index) {

        return 2 * index + 1;
    }




}