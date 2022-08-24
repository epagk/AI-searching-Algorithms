package myPackage;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import myPackage.WeightedGraph.Graph;

public class WeightedGraph {
	
	static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        LinkedList<Edge> [] adjacencylist;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i <vertices ; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        public void addEgde(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist[source].addFirst(edge); //for directed graph
        }
        
        public int existEdge(int source, int destination){
        	//System.out.println("In existEdge! Source: "+source+" /Destination: "+destination);
        	LinkedList<Edge> list = adjacencylist[source];
        	for (int j = 0; j <list.size() ; j++) {
        		if(list.get(j).destination==destination){
        			//System.out.println("Edge Found");
        			return list.get(j).weight;
        		}
            }
        	//System.out.println("Edge Not Found");
        	return -1;
        }

        public void printGraph(){
            for (int i = 0; i < vertices ; i++) {
                LinkedList<Edge> list = adjacencylist[i];
                for (int j = 0; j <list.size() ; j++) {
                    System.out.println("cell-" + i + " is connected to " +
                            list.get(j).destination + " with cost " +  list.get(j).weight);
                }
            }
        }
        
        public void DFS(int startpoint, int goalpoint, int M, Grid mygrid, String frame){

     	   Stack<Integer> stack = new Stack<Integer>(); 
     	   LinkedList<Integer> path = new LinkedList<Integer>(); // This list shows the path we followed to reach Terminal Index
     	   stack.push(startpoint); // First element in stack is the Start Index
     	   int currNode;
     	   String[] colour = new String[vertices];  // flag to keep information if a node has been visited
     	   int totalCost = 0;
     	   
     	   path.add(startpoint);
     	   
     	   for(int i=0; i< vertices; i++){
     		   colour[i] = "white"; // white means Not explored yet!
     	   }
     	   
     	   while(!stack.isEmpty()){
     		   currNode = stack.pop();  // Top element of stack

     		   colour[currNode] = "gray"; // Mark it as visited
     		   int numOfNeighbors = 0;
     		   
     		   int last = path.getLast(); // Last element of path
     		   
     		   //System.out.println("last = "+last+" / currNode = "+currNode+ "edge exist: "+(existEdge(last,currNode)!=-1));
     		   while((existEdge(last,currNode)==-1) && last!=currNode){  // if last element of  path and the node we are about to include are not 
     			   path.removeLast();                                    // connected. We find the last neighbor of currNode in the path. We remove the others.
     			  last = path.getLast();
     		   }
     		   
     		  if(currNode!=last){ path.add(currNode);} // Include a node if it's not already on top. 
     		 

     		   LinkedList<Edge> list = adjacencylist[currNode];
     		   
     		   for (int j = 0; j <list.size() ; j++){  // For every neighbor of current Node
     			   if(colour[list.get(j).destination].equals("white")){  // if they haven't been visited yet 
     				   numOfNeighbors++;    // there are not visited neighbors
     				   if(list.get(j).destination == goalpoint){
     					   //System.out.println("End Point Reached!");
     					   path.add(list.get(j).destination);
     					   int steps[] = new int[path.size()]; int stp = 0;
     					   
     					   for(int i=0; i < path.size(); i++){
     						   System.out.println("Cell ["+ path.get(i)/M + ","+ path.get(i)%M + "]");
     						   totalCost = totalCost + mygrid.getCell(path.get(i)/M, path.get(i)%M).getCost();
     						   steps[stp] = path.get(i); stp++;
     					   }
     					   totalCost = totalCost - mygrid.getCell(goalpoint/M, goalpoint%M).getCost(); // Don't include cost of terminal cell 
     					   System.out.println("Total Cost of path: "+totalCost);
     					   GridGenerator.VisualizeGrid(frame, mygrid.getNumOfRows(), mygrid.getNumOfColumns(), mygrid.getWalls(), mygrid.getGrass(), steps, startpoint, goalpoint);
     					   return;
     				   }else{
     					   stack.push(list.get(j).destination);
     				   }
     			   }
     		   }
     		   if(numOfNeighbors == 0) path.removeLast(); // If a node has no visited neighbors it drives nowhere. We remove it from path.
     	   }
     	   
        }
        
        
        public void createGraph(Grid mygrid, String frame){
        	
        	//Graph g = new Graph(mygrid.getNumOfRows()*mygrid.getNumOfColumns());
        	
    		int [] start = mygrid.getStart();
    		int [] end = mygrid.getTerminal();
    		int N = mygrid.getNumOfRows();
    		int M = mygrid.getNumOfColumns();
    		//String[] colour = new String[N*M]; //if node has been visited
    		int[] weight = new int[N*M];       //cost of the path from start till current node
    		
    		for(int k=0; k<N*M; k++){ // k is in form i*M+j
    			int i = k/M; // i = row
    			int j = k%M; // j = column
    			int cellCost = mygrid.getCell(i, j).getCost();
    			
    			if(!mygrid.getCell(i,j).isWall()){ // If cell hasn't been checked & isn't wall continue
    				char cel = mygrid.getCell(i, j).getCellType();
    				if(i==0){ // Cells belong to first row
    				   // No neighbor above
    				   if(!mygrid.getCell(i+1, j).isWall()){ //down cell if it is not wall
    						   addEgde(k, (i+1)*M+j, cellCost);
    				   }
    				   if(j==0){ // cell (0,0) only down and right neighbor				   
    					   if(!mygrid.getCell(i, j+1).isWall()){ // right cell if it is not wall
    						   addEgde(k, i*M+j+1, cellCost);
    					   }
    				   }else if(j==M-1){ // cell (0,M-1) only left and down neighbor
    					   if(!mygrid.getCell(i, j-1).isWall()){ // left cell if it is not wall
    						   addEgde(k, i*M+j-1, cellCost);
    					   }
    				   }else{ // somewhere in the middle of first row
    					   if(!mygrid.getCell(i, j+1).isWall()){ // right cell if it is not wall
    						   addEgde(k, i*M+j+1, cellCost);
    					   }
    					   if(!mygrid.getCell(i, j-1).isWall()){ // left cell if it is not wall
    						   addEgde(k, i*M+j-1, cellCost);
    					   }
    				   }	
    				  
    				}else if(i==N-1){ // Cells belong to last row
    					// No down neighbor
    					if(!mygrid.getCell(i-1, j).isWall()){ //above cell if it is not wall
    						   addEgde(k, (i-1)*M+j, mygrid.getCell(i, j).getCost());
    				    }
    					if(j==0){ // cell (N-1,0) only above and right neighbor				   
    						   if(!mygrid.getCell(i, j+1).isWall()){ // right cell if it is not wall
    							   addEgde(k, i*M+j+1, mygrid.getCell(i, j).getCost());
    						   }
    					   }else if(j==M-1){ // cell (N-1,M-1) only left and above neighbor
    						   if(!mygrid.getCell(i, j-1).isWall()){ // left cell if it is not wall
    							   addEgde(k, i*M+j-1, mygrid.getCell(i, j).getCost());
    						   }
    					   }else{ // somewhere in the middle of last row
    						   if(!mygrid.getCell(i, j+1).isWall()){ // right cell if it is not wall
    							   addEgde(k, i*M+j+1, mygrid.getCell(i, j).getCost());
    						   }
    						   if(!mygrid.getCell(i, j-1).isWall()){ // left cell if it is not wall
    							   addEgde(k, i*M+j-1, mygrid.getCell(i, j).getCost());
    						   }
    					   }

    				}else if(j==0){ // Cells belong to first column
    					// No left neighbor
    					if(!mygrid.getCell(i, j+1).isWall()){ // right cell if it is not wall
    						   addEgde(k, i*M+j+1, mygrid.getCell(i, j).getCost());
    					}
    					if(!mygrid.getCell(i-1, j).isWall()){ //above cell if it is not wall
    						   addEgde(k, (i-1)*M+j, mygrid.getCell(i, j).getCost());
    				    }
    					if(!mygrid.getCell(i+1, j).isWall()){ //down cell if it is not wall
    						   addEgde(k, (i+1)*M+j, mygrid.getCell(i, j).getCost());
    				    }

    				}else if(j==M-1){ // Cells belong to last column
    					// No right neighbor
    					if(!mygrid.getCell(i, j-1).isWall()){ // left cell if it is not wall
    						   addEgde(k, i*M+j-1, mygrid.getCell(i, j).getCost());
    					}
    					if(!mygrid.getCell(i-1, j).isWall()){ //above cell if it is not wall
    						   addEgde(k, (i-1)*M+j, mygrid.getCell(i, j).getCost());
    				    }
    					if(!mygrid.getCell(i+1, j).isWall()){ //down cell if it is not wall
    						   addEgde(k, (i+1)*M+j, mygrid.getCell(i, j).getCost());
    				    }

    				}else{ // Every other middle cell
    					if(!mygrid.getCell(i+1, j).isWall()){ //down cell if it is not wall
    						   addEgde(k, (i+1)*M+j, mygrid.getCell(i, j).getCost());
    				   }
    				   if(!mygrid.getCell(i-1, j).isWall()){ //above cell if it is not wall
    						   addEgde(k, (i-1)*M+j, mygrid.getCell(i, j).getCost());
    				   }
    				   if(!mygrid.getCell(i, j+1).isWall()){ // right cell if it is not wall
    					   addEgde(k, i*M+j+1, mygrid.getCell(i, j).getCost());
    				   }
    				   if(!mygrid.getCell(i, j-1).isWall()){ // left cell if it is not wall
    					   addEgde(k, i*M+j-1, mygrid.getCell(i, j).getCost());
    				   }

    				} 
    		    }
    		}
    		//g.printGraph();
    		System.out.println("Start point: ("+mygrid.getStartidx()/M+","+mygrid.getStartidx()%M+")");
    		System.out.println("Terminal point: ("+mygrid.getTerminalidx()/M+","+mygrid.getTerminalidx()%M+")");
    		DFS(mygrid.getStartidx(), mygrid.getTerminalidx(), M, mygrid, frame);
    	}
        
    } 

}
