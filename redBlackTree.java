
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


class Empty extends redBlackBuildingNode {
	public Empty() {
		super();
		this.color = Color.black;
	}
	public boolean isEmpty(){
		return true;
	}	
}

public class redBlackTree {
	redBlackBuildingNode root = new Empty();

	/*Fetch all the building Numbers between num1 and num2*/
	public ArrayList<Integer> fetchBuildings(int num1, int num2){
		  ArrayList<Integer> rbNodesList = new ArrayList<Integer>();
		  fetchAllBuildings( root, num1, num2, rbNodesList);
		  int total = 0;
		  return rbNodesList;
	}

	public int fetchBuildings(int num) {
		  ArrayList<Integer> rbNodesList = new ArrayList<Integer>();
		  fetchSingleBuilding( root, num, rbNodesList);
		  int total = 0;
		  if(rbNodesList.size()==0)
		  {
			  return 0;
		  }
		  else
			return rbNodesList.get(0);
	}

	/*To fetch all the buildings in the range between num1 and num2*/
	private void fetchAllBuildings(redBlackBuildingNode root, int num1, int num2, ArrayList<Integer> rbNodesList){
        if (root == null) {
            return;
        }
        if (root.ID > num1) {
            fetchAllBuildings(root.left, num1, num2, rbNodesList);
        }
        if (root.ID >= num1 && root.ID <= num2) {
            rbNodesList.add(root.ID);
        }
        if (root.ID < num2) {
            fetchAllBuildings(root.right, num1, num2, rbNodesList);
        }
    
	}

	/*To fetch the building info that matches buildingNum*/
	private void fetchSingleBuilding(redBlackBuildingNode root, int buildingNum, ArrayList<Integer> rbNodesList){
        if (root == null) {
            return;
        }
        if (root.ID > buildingNum) {
            fetchSingleBuilding(root.left, buildingNum, rbNodesList);
        }
        if (root.ID == buildingNum) {
            rbNodesList.add(root.ID);
        }
        if (root.ID < buildingNum) {
            fetchSingleBuilding(root.right, buildingNum, rbNodesList);
        }
    
	}

	/* Fetch min node from redBlackTree*/
	private redBlackBuildingNode fetchMinNode(redBlackBuildingNode root){
		redBlackBuildingNode current = root;
		while(!current.left.isEmpty()){
			current = current.left;	
		}
		return current;
	}
	
	/* Fetch max node from redBlackTree*/
	public redBlackBuildingNode fetchMaxNode(redBlackBuildingNode root){
		redBlackBuildingNode current = root;
		while(!current.right.isEmpty()){
			current = current.right;
		}
		return current;
	}
	

	/*To search building in RB Tree*/
	public redBlackBuildingNode SearchRBTree(int buildingNum, redBlackBuildingNode root) {
		while(!root.isEmpty()){
			if(root.ID == buildingNum){
				return root;
			}
		    else if(root.ID > buildingNum){
			    root = root.left;
		    }
			else{
				root = root.right;
			}
		}
		return root;
	}

	public int isBuildingIdPresent(int buildingNum, redBlackBuildingNode root){
		while(!root.isEmpty()) {
			if(root.ID == buildingNum) {
				return 1;
			}
			else if(root.ID > buildingNum) {
				root = root.left;
			}
			else{
				root = root.right;
			}
		}
		return 0;
	}

	//Prints triplet of the of the least building num that is greater than to a given building num.
	public int prevBuildingInTree(int num){
		redBlackBuildingNode current = SearchRBTree(num,root);
		if(current.isEmpty()){
			redBlackBuildingNode tmp = this.root;
			redBlackBuildingNode last = new Empty();
			while(!tmp.isEmpty()){
				last = tmp;
				if(num > tmp.ID){
					tmp = tmp.right;
				}
				else{ 
					tmp = tmp.left;
				}
			}
	
	      if(last.ID < num){
				return last.ID;
			}
	       else{
				redBlackBuildingNode parent = last;
				redBlackBuildingNode child = tmp;
				while(!parent.isEmpty() && parent.left == child){
					child = parent;
					parent = parent.parent;
				}
				return parent.ID;
			}
			
		}
		else{
	       if(!current.left.isEmpty()){
				redBlackBuildingNode max_node = fetchMaxNode(current.left);
				return max_node.ID;
			}
	       else{
				redBlackBuildingNode parent = current.parent;
				while(!parent.isEmpty() && parent.left == current){
					current = parent;
					parent = parent.parent;
				}
				return parent.ID;
			}
		}
	}

   /*insertIntoRedBlackTree : To insert each building info into red black tree*/
	public redBlackBuildingNode insertIntoRedBlackTree(int ID, int total_time, int executionTime){
		redBlackBuildingNode tempNode = this.root;
		redBlackBuildingNode last = tempNode;
		while(!tempNode.isEmpty()){
			last = tempNode;
			if(ID > tempNode.ID){
				tempNode = tempNode.right;
			}
			else{ 
				tempNode = tempNode.left;
			}
		}
		Empty Empty = new Empty();
		redBlackBuildingNode newNode = new redBlackBuildingNode(ID,0,Empty,Empty,Empty,Color.red,total_time,executionTime);
		newNode.parent = last;
		if(last.isEmpty()){
			this.root = newNode;
		}
		if(last.ID > ID){
			last.left = newNode;
		}
		else{
			last.right = newNode;
		}
		return newNode;
	}

	/*leftRotateNode method : rotate the node in left direction to fix the red black tree voilation */
	private void leftRotateNode(redBlackBuildingNode node){
		redBlackBuildingNode tempNode = node.right;
		node.right = tempNode.left;
		if(!tempNode.left.isEmpty()){
			tempNode.left.parent = node;
		}
		if(node.parent.isEmpty()){
			this.root = tempNode;
			tempNode.parent = node.parent;
		}
		else if(node == node.parent.left){
			node.parent.left = tempNode;
			tempNode.parent = node.parent;
		}
		else{
			node.parent.right = tempNode;
			tempNode.parent = node.parent;
		}
		tempNode.left = node;
		node.parent = tempNode;
			
	}

	/*rightRotateNode method : rotate the node in right direction to fix the red black tree voilation */
	private void rightRotateNode(redBlackBuildingNode node) {
		redBlackBuildingNode tempNode = node.left;
		node.left = tempNode.right;
		if(!tempNode.right.isEmpty()){
			tempNode.right.parent = node;
		}
		if(node.parent.isEmpty()){
			this.root = tempNode;
			tempNode.parent = node.parent;
		}
		else if(node == node.parent.left){
			node.parent.left = tempNode;
			tempNode.parent = node.parent;
		}
		else{
			node.parent.right = tempNode;
			tempNode.parent = node.parent;
		}
		tempNode.right = node;
		node.parent = tempNode;
	}

	
	/*swapRedBlackNode method swaps two red black while building the tree*/
	private void swapRedBlackNodes(redBlackBuildingNode node1, redBlackBuildingNode node2) {
		if(node1.parent.isEmpty()){
			this.root = node2;
		}
		else if(node1 == node1.parent.left){
			node1.parent.left = node2;
		}
		else{
			node1.parent.right = node2;
		}
		node2.parent = node1.parent;
	}

	/*When building gets constructed completely then remove that node from red black tree*/
	public void deleteRedBlackNode(redBlackBuildingNode node) {
		redBlackBuildingNode tempNode = node;
		redBlackBuildingNode tempNode_dup = tempNode;
		redBlackBuildingNode newNode;
		tempNode_dup.color = tempNode.color;
		if(node.left.isEmpty()) {
			newNode = node.right;
			swapRedBlackNodes(node,node.right);
		}
		else if(node.right.isEmpty()) {
			newNode= node.left;
			swapRedBlackNodes(node,node.left);
		}
		else{
			tempNode = fetchMinNode(node.right);
		    newNode = tempNode.right;
		    tempNode_dup.color = tempNode.color;
			swapRedBlackNodes(tempNode,tempNode.right);
			swapRedBlackNodes(node,tempNode);
			tempNode.left = node.left;
			tempNode.left.parent = tempNode;
			tempNode.right = node.right;
			tempNode.right.parent = tempNode;
			tempNode.color = node.color;
		}	
		if(tempNode_dup.color == Color.black){
			buildRedBlackTree(newNode);
		}
	}

	/*After deleting the any node from the redblack tree - buildRedBlackTree method
	* constructs the tree by checking the redblack tree properties*/
	public void buildRedBlackTree(redBlackBuildingNode node) {

		while(node != this.root && node.color == Color.black){
			/*Now node is in left subtree*/
			if(node == node.parent.left){
				redBlackBuildingNode tempNode = node.parent.right;

				/*When both the children of a node are red*/
				if(tempNode.color == Color.red){
					tempNode.color = Color.black;
					node.parent.color = Color.red;
					leftRotateNode(node.parent);
					tempNode = node.parent.right;
				}
				if(tempNode.left.color == Color.black  &&  tempNode.right.color == Color.black){
					/*Case : when children are red nodes and sibling node is black*/
					tempNode.color = Color.red;
					node = node.parent;
				}
				else{
					/*Case : Sibling node is Black, Left child of Sibling is Red and Right Child is Black*/
					if(tempNode.right.color == Color.black){
													
						tempNode.left.color = Color.black;
						tempNode.color = Color.red;
						rightRotateNode(tempNode);
						tempNode = node.parent.right;
					}

					/*Case : Sibling node is Black and right child is Black*/
					tempNode.color = node.parent.color;
					node.parent.color = Color.black;
					tempNode.right.color = Color.black;
					leftRotateNode(node.parent);
					node = this.root;
						
				}
			}

			/*Case : If the node is in right subtree*/
			else if(node == node.parent.right){
				redBlackBuildingNode tempNode = node.parent.left;
				if(tempNode.color == Color.red){
					tempNode.color = Color.black;
					node.parent.color = Color.red;
					rightRotateNode(node.parent);
					tempNode = node.parent.left;
				}
				if(tempNode.left.color == Color.black  &&  tempNode.right.color == Color.black){
					tempNode.color = Color.red;
					node = node.parent;
				}
				else{ 
					if(tempNode.left.color == Color.black){
						tempNode.right.color = Color.black;
						tempNode.color = Color.red;
						leftRotateNode(tempNode);
						tempNode = node.parent.left;
					}
					tempNode.color = node.parent.color;
					node.parent.color = Color.black;
					tempNode.left.color = Color.black;
					rightRotateNode(node.parent);
					node = this.root;
				}
						
			}
					
		}
			node.color = Color.black;
	}

}

	
