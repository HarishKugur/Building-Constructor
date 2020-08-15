public class minHeapBuildingNode {

int currentConstructedTime;
int totalBuildingTime;
redBlackBuildingNode redBlackNode;

public minHeapBuildingNode(int totalBuildingTime, int executionTime, redBlackBuildingNode redBlackNode)
{
    currentConstructedTime = executionTime;
    this.redBlackNode = redBlackNode;
    this.totalBuildingTime = totalBuildingTime;
}

public minHeapBuildingNode()
{   
}

}
