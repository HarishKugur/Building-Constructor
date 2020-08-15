public class redBlackBuildingNode {

	int ID;
	int count;
	int totalBuildingTime;
	int building_ExecutionTime;
	redBlackBuildingNode left, right, parent;
    Color color;

	public redBlackBuildingNode(int ID, int count, redBlackBuildingNode left, redBlackBuildingNode right,
								redBlackBuildingNode parent, Color color, int totalBuildingTime, int building_ExecutionTime) {
		
		this.ID = ID;
		this.count = count;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.color = color;
		this.totalBuildingTime = totalBuildingTime;
		this.building_ExecutionTime = building_ExecutionTime;
	}
	public redBlackBuildingNode(){}
	public boolean isEmpty(){
		return false;
	}
}
