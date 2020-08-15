import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Building {
	
	
	public redBlackTree rbTree;
	public MinHeap heap;
	public int jobID;
	ArrayList<String> executedBuildingsOutput;

	public Building()
	{
		this.executedBuildingsOutput = new ArrayList<String>();
		this.heap = new MinHeap();
		this.rbTree = new redBlackTree();
	}
	
	public void WritetoOutput()
	{
		try{
		BufferedWriter buildingsWriter=new BufferedWriter(new FileWriter("output_file.txt"));

		for(int i=0; i<executedBuildingsOutput.size(); i++){
				buildingsWriter.append(executedBuildingsOutput.get(i));
				buildingsWriter.append('\n');
		}
		buildingsWriter.close();
		}

		catch(IOException err)
		{
		err.printStackTrace();
		}
	}

//	public  void NextBuilding(int ID){
//		redBlackBuildingNode newNode=rbTree.insertIntoRedBlackTree(rbTree.NextBuildingInTree(ID), rbTree.root);
//		executedBuildingsOutput.add("("+rbTree.NextBuildingInTree(ID)+","+newNode.building_ExecutionTime+","+newNode.totalBuildingTime+")");
//	}
//
//	public void previousBuilding(int ID){
//		redBlackBuildingNode newNode=rbTree.insertIntoRedBlackTree(rbTree.prevBuildingInTree(ID), rbTree.root);
//		executedBuildingsOutput.add("("+rbTree.prevBuildingInTree(ID)+","+newNode.building_ExecutionTime+","+newNode.totalBuildingTime+")");
//	}

	public void insertBuildingInfo(int buildingNum, int totalBuildingTime, int executionTime) {

		if(rbTree.isBuildingIdPresent(buildingNum, rbTree.root) == 0) {
			rbTree.insertIntoRedBlackTree(buildingNum, totalBuildingTime, executionTime);
			redBlackBuildingNode newNode = rbTree.SearchRBTree(buildingNum, rbTree.root);
			heap.insertIntoMinHeap(totalBuildingTime, executionTime, newNode);
		}
		else {
			executedBuildingsOutput.add("Building - "+ buildingNum + " already exits in the System. It is a duplicate Entry ");
			WritetoOutput();
			System.exit(1);
		}

	}

	public void PrintWayneBuilding(int buildingNum) {
		redBlackBuildingNode newNode = rbTree.SearchRBTree(buildingNum, rbTree.root);
		if(!newNode.isEmpty())
		{
			executedBuildingsOutput.add("(" + rbTree.fetchBuildings(buildingNum) + "," + newNode.building_ExecutionTime + "," + newNode.totalBuildingTime + ")");
		}
		else {
			executedBuildingsOutput.add("(0,0,0)");
		}
	}

	public void printWayneDeletedBuilding(int buildingNum, long completed_time) {
			executedBuildingsOutput.add("("+buildingNum+","+completed_time+")");
	}

	public  void PrintWayneBuilding(int buildingNum1, int buildingNum2) {
		ArrayList<Integer>buildingsList = rbTree.fetchBuildings(buildingNum1, buildingNum2);
		String buildingsListString ="";
		if(buildingsList.size()!=0) {

			for(int i = 0; i < buildingsList.size(); i++) {
				if(buildingsList.get(i) != 0) {
					redBlackBuildingNode newNode = rbTree.SearchRBTree(buildingsList.get(i), rbTree.root);
					buildingsListString += "(" + buildingsList.get(i) + "," + newNode.building_ExecutionTime + "," + newNode.totalBuildingTime + ")";
					if (i != buildingsList.size() - 1) {
						buildingsListString += ",";
					}
				}
			}
			char[] buildingsListArray = buildingsListString.toCharArray();
			if(buildingsListArray[buildingsListArray.length-1] == ','){
				buildingsListString=buildingsListString.substring(0,buildingsListArray.length-1);
			}
			executedBuildingsOutput.add(buildingsListString);
		}
		else {
			buildingsListString+= "(0,0,0)";
			executedBuildingsOutput.add(buildingsListString);
		}
	}

}
