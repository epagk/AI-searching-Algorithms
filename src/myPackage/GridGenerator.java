package myPackage;
/**
			INTELLIGENCE LAB
	course		: 	COMP 417 - Artificial Intelligence
	authors		:	A. Georgara, A. Vogiatzis
	excercise	:	1st Programming
	term 		: 	Spring 2018-2019
	date 		:   March 2019
*/
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import myPackage.WeightedGraph.Edge;
import myPackage.WeightedGraph.Graph;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.awt.Canvas;

class GridGenerator{
	public static void VisualizeGrid(String frame_name, int N, int M, int [] walls, int [] grass, int start_idx, int terminal_idx ){
		JFrame frame = new JFrame(frame_name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new Drawing(N,M,walls,grass,start_idx,terminal_idx);
		canvas.setSize(M*30,N*30);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}
	public static void VisualizeGrid(String frame_name, int N, int M, int [] walls, int [] grass, int [] steps ,int start_idx, int terminal_idx ){
		JFrame frame = new JFrame(frame_name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new Drawing(N,M,walls,grass, steps, start_idx,terminal_idx);
		canvas.setSize(M*30,N*30);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static LinkedList<Integer> path = new LinkedList<Integer>();

	public static void main(String[] args) {
		String frame = "Random World";
		Grid mygrid;
		GridGenerator grG = new GridGenerator();
		if (args.length<1)
			mygrid = new Grid();
		else if (args[0].equals("-i")){
			mygrid = new Grid(args[1]);
			frame = args[1].split("/")[1];
		}else if (args[0].equals("-d")){
			mygrid = new Grid(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		}else{
			mygrid = new Grid("world_examples/hard_c.world");
			frame = "hard_c.world";
		}
		int N = mygrid.getNumOfRows();
		int M = mygrid.getNumOfColumns();
		VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mygrid.getStartidx(),mygrid.getTerminalidx());
		
	    int choice = grG.printMenu();
	    
	    switch(choice){
	        case 1:
	        	 System.out.println("BFS Algorithm");
	        	 grG.BFSalgorithm(mygrid, N, M, frame);
	        	 break;
	        case 2:
	        	 System.out.println("DFS Algorithm");
	        	 Graph graph = new Graph(N*M);
	        	 graph.createGraph(mygrid, frame);
	        	 break;
	        case 3:
	        	 System.out.println("A* Algorithm");
	        	 grG.AstarPathFinding(mygrid,frame);
	        	 break;
	        case 4:
	        	 System.out.println("LRTA* Algorithm");

	     		int[] goalNode = mygrid.getTerminal();
	     		int[] startNode = mygrid.getStart();
	     		
	     	    int[][] hManh = new int[N][M];
	     	    int[][] H = new int[N][M];
	     	    
	     	    //Initialize H
	     	    for(int i=0;i<N;i++) {
	     	    	for(int j=0;j<M;j++) {
	     	    		H[i][j] = -1;
	     	    	}
	     	    }
	     	    
	     	    //Initialize heuristic h(s)
	     	    for(int i=0;i<N;i++) {
	     	    	for(int j=0;j<M;j++) {
	     	    		hManh[i][j] = Math.abs(i-goalNode[0]) + Math.abs(j-goalNode[1]);
	     	    		Cell c = mygrid.getCell(i,j);
	     	    		
	     	    		
	     	    		
	     	    	}
	     	    }
	     	   
	     	    /*
	     	    
	     	    for(int i=0;i<N;i++) {
	     	    	for(int j=0;j<M;j++) {
	     	    		System.out.printf("Cell: [%d][%d]---> %d\n",i,j,hManh[i][j]);
	     	    	}
	     	    }
	     	    */
	     	    
	     	    int[] current_cell =startNode;
	     	    int[] previous_cell = new int[2];
	     	    previous_cell[0]=-1;
	     	    previous_cell[1]=-1;
	     	    int[][][] result = new int[N][M][4];
	     	    
	     	    for(int i=0;i<N;i++) {
	     	    	for(int j=0;j<M;j++) {
	     	    		for(int k=0;k<4;k++) {
	     	    		result[i][j][k] = -11;
	     	    		}
	     	    	}
	     	    }
	     	    
	     	    //Cell c1 = mygrid.getCell(5, 6);
	     	    //System.out.printf("Cost issssss:%d\n", c1.getCost());
	     	    int count= 0;
	     	    int counter = 0;
	     	    int action=1;
	     	    LinkedList<Integer> path = new LinkedList<Integer>();
	     	    
	     	    int s_help;
	     	    //System.out.printf("1)Now i am at cell: [%d][%d]\n", current_cell[0],current_cell[1]);
	     	    do {
	     	    	//System.out.printf(" [%d][%d]=>\n", current_cell[0],current_cell[1]);
	     	    	if(!mygrid.getCell(current_cell[0],current_cell[1]).isWall()) {
	     	    		count=count+mygrid.getCell(current_cell[0],current_cell[1]).getCost();
	     	    		path.add(current_cell[0]*M+current_cell[1]);
	     	    		counter = counter+1;
	     	    	}
	     	    	if(current_cell[0]==0 && current_cell[1]==0) {
	     	    		System.out.printf("(BEFORE)Before i was at cell: [%d][%d]\n", previous_cell[0],previous_cell[1]);
	     	    	}
	     	    	
	     	    	//System.out.printf("\n(BEFORE)Now i am at cell: [%d][%d]\n", current_cell[0],current_cell[1]);
	     	    	//System.out.printf("(BEFORE)Before i was at cell: [%d][%d]\n", previous_cell[0],previous_cell[1]);
	     	    	action = LRTA(current_cell,previous_cell,mygrid,H,hManh,result,N,M,action);
	     	    	//System.out.printf("Action that i will do: %d\n",action);
	     	    	//System.out.printf("(AFTER)Now i am at cell: [%d][%d]\n", previous_cell[0],previous_cell[1]);
	     	    	//System.out.printf("(AFTER)Now i am at cell: [%d][%d]\n", current_cell[0],current_cell[1]);
	     	    	//previous_cell[0] = current_cell[0];
	     	    	//previous_cell[1] = current_cell[1];
	     	    	//System.out.printf("------->So previous now is: [%d][%d]\n", previous_cell[0],previous_cell[1]);
	     	    	if(action!=56) {
	     	    		s_help = find_next_cell(current_cell,action);
	     		    	current_cell[0] = s_help/10;
	     		    	current_cell[1] = s_help%10;
	     		    	count++;
	     	    	}
	     	    	
	     	    	//System.out.printf("2)Now i am at cell: [%d][%d]\n", current_cell[0],current_cell[1]);
	     	    	//System.out.printf("2)Now i am at cell: [%d][%d]\n", previous_cell[0],previous_cell[1]);
	     	    }while(action != 56);    // && (current_cell[0] != 8 && current_cell[1]!=8) 
	     	    int[] steps = new int[path.size()];
	     	    for(int i=0;i<path.size();i++) {
	     	    	steps[i]=path.get(i);
	     	    }
	     	    
	     	    VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
	     	    System.out.printf("Finished with cost: %d!!!!",count);
	     	   
	     	
	        	 break;
	        default: 
	        	 // Never gets here
	        	 break;
	    }
				
	}
	
	public void AstarPathFinding(Grid mygrid, String frame){
		int[] startPoint = mygrid.getStart();
		int [] endPoint = mygrid.getTerminal();
		
		System.out.println("Start point: ("+startPoint[0]+","+startPoint[1]+")");
		System.out.println("Terminal point: ("+endPoint[0]+","+endPoint[1]+")");
		
		Node initialNode = new Node(startPoint[0], startPoint[1]);
        Node finalNode = new Node(endPoint[0], endPoint[1]);
        int N = mygrid.getNumOfRows();
        int M = mygrid.getNumOfColumns();
        
        AStar aStar = new AStar(N, M, initialNode, finalNode, mygrid);
        
        int[] walls = mygrid.getWalls();
        //int[][] blocksArray = null;
              
        for(int i=0; i < walls.length; i++){
        	aStar.setBlock(walls[i]/M, walls[i]%M);
        }
        
        List<Node> path = aStar.findPath(mygrid);
        
        int steps[] = new int[path.size()];
        int i = 0;
        
        for (Node node : path) {
        	System.out.println(node);
            steps[i] = node.getRow()*M + node.getCol();
            i++;
        }
        System.out.println("Total Cost of path: "+path.get(path.size()-1).getF());
        
        VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
	}
	
	public int printMenu(){
		Scanner sir = new Scanner(System.in);
		
		System.out.println("---------------------------------");
		System.out.println("Press '1' for BFS algorithm");
		System.out.println("Press '2' for DFS algorithm");
		System.out.println("Press '3' for A* algorithm");
		System.out.println("Press '4' for LRTA* algorithm");
		System.out.println("---------------------------------");
		
		int answer = sir.nextInt();

		while(answer!=1 && answer!=2 && answer!=3 && answer!=4 ){
			System.out.println("Invalid Choice.. Try again");
			answer = sir.nextInt();
		}
		
		return answer;
	}
	
	public void BFSalgorithm(Grid mygrid, int N, int M, String frame){
		int[] startNode = mygrid.getStart();
		//System.out.printf("Start at:%d ",s[0]);
		//System.out.printf("Start at:%d ",s[1]);
		int[] goalNode = mygrid.getTerminal();
		
		String[][] colour = new String[N][M]; //if it s been visited
		int[][] weight = new int[N][M];       //cost of the path
		int[][] previous = new int[N][M];     //where we come from(row)
		//int[] previous_column = new int[M]; //where we come from(column)
		
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				colour[i][j] = "white";
				weight[i][j] = 0;
				previous[i][j] = -11;
				//previous_column[j]=-1;
			}
		}
		
		
		colour[startNode[0]][startNode[1]] = "gray";
		weight[startNode[0]][startNode[1]] = 0;
		previous[startNode[0]][startNode[1]] =startNode[0]*10+startNode[1];
		//previous_column[startNode[1]] =startNode[1];
		
		Queue<Integer> queue = new LinkedList<>();
		queue.add(startNode[0]);
		queue.add(startNode[1]);
		
		while(!queue.isEmpty()) {
			int R = queue.remove();
			int C = queue.remove();
			
			if(R==0) {   //if it is in the first row
				if(C==0) { //if it is (0,0)
					if(colour[0][1].equals("white") && !mygrid.getCell(0, 1).isWall()) {
						Cell c = mygrid.getCell(0, 1);
						colour[0][1] = "gray";
						weight[0][1] = weight[0][0] + c.getCost();
						previous[0][1] = 00;
						//previous_column[1] = 0;
						queue.add(0);
						queue.add(1);
					}
					if(colour[1][0].equals("white") && !mygrid.getCell(1, 0).isWall()) {
						Cell c = mygrid.getCell(1, 0);
						colour[1][0] = "gray";
						weight[1][0] = weight[0][0] + c.getCost();
						previous[1][0] = 00;
						//previous_column[0] = 0;
						queue.add(1);
						queue.add(0);
					}
					colour[R][C] = "black";
				}
				else if(C==M-1) { //if it is the last of the 1st row
					if(colour[0][C-1].equals("white") && !mygrid.getCell(0, C-1).isWall()) {
						Cell c = mygrid.getCell(0, C-1);
						colour[0][C-1] = "gray";
						weight[0][C-1] = weight[0][C] + c.getCost();
						previous[0][C-1] = 0+C;
						//previous_column[C-1] = C;
						queue.add(0);
						queue.add(C-1);
					}
					if(colour[1][C].equals("white") && !mygrid.getCell(1, C).isWall()) {
						Cell c = mygrid.getCell(1, C);
						colour[1][C] = "gray";
						weight[1][C] = weight[0][C] + c.getCost();
						previous[1][C] = 0+C;
						//previous_column[C] = C;
						queue.add(1);
						queue.add(C);
					}
					colour[R][C] = "black";
				}
				else {
					if(colour[0][C-1].equals("white") && !mygrid.getCell(0, C-1).isWall()) {
						Cell c = mygrid.getCell(0, C-1);
						colour[0][C-1] = "gray";
						weight[0][C-1] = weight[0][C] + c.getCost();
						previous[0][C-1] = 0+C;
						//previous_column[C-1] = C;
						queue.add(0);
						queue.add(C-1);
					}
					if(colour[1][C].equals("white") && !mygrid.getCell(1, C).isWall()) {
						Cell c = mygrid.getCell(1, C);
						colour[1][C] = "gray";
						weight[1][C] = weight[0][C] + c.getCost();
						previous[1][C] = 0+C;
						//previous_column[C] = C;
						queue.add(1);
						queue.add(C);
					}
					if(colour[0][C+1].equals("white") && !mygrid.getCell(0, C+1).isWall()) {
						Cell c = mygrid.getCell(0, C+1);
						colour[0][C+1] = "gray";
						weight[0][C+1] = weight[0][C] + c.getCost();
						previous[0][C+1] = 0+C;
						//previous_column[C+1] = C;
						queue.add(0);
						queue.add(C+1);
					}
					colour[R][C] = "black";
				}
			}
			else if(C==0) { // if it is in the first column
				if(R==N-1) { // if it is in the last row
					if(colour[R-1][0].equals("white") && !mygrid.getCell(R-1, 0).isWall()) {
						Cell c = mygrid.getCell(R-1, 0);
						colour[R-1][0] = "gray";
						weight[R-1][0] = weight[R][0] + c.getCost();
						previous[R-1][0] = R*10+0;
						//previous_column[0] = 0;
						queue.add(R-1);
						queue.add(0);
					}
					if(colour[R][1].equals("white") && !mygrid.getCell(R, 1).isWall()) {
						Cell c = mygrid.getCell(R, 1);
						colour[R][1] = "gray";
						weight[R][1] = weight[R][0] + c.getCost();
						previous[R][1] = R*10;
						//previous_column[1] = 0;
						queue.add(R);
						queue.add(1);
					}
					colour[R][C] = "black";
				}
				else { 				//everything else {0,0 has already been checked}
					if(colour[R-1][0].equals("white") && !mygrid.getCell(R-1, 0).isWall()) {
						Cell c = mygrid.getCell(R-1, 0);
						colour[R-1][0] = "gray";
						weight[R-1][0] = weight[R][0] + c.getCost();
						previous[R-1][0] = R*10;
						//previous_column[0] = 0;
						queue.add(R-1);
						queue.add(0);
					}
					if(colour[R+1][0].equals("white") && !mygrid.getCell(R+1, 0).isWall()) {
						Cell c = mygrid.getCell(R+1, 0);
						colour[R+1][0] = "gray";
						weight[R+1][0] = weight[R][0] + c.getCost();
						previous[R+1][0] = R*10;
						//previous_column[0] = 0;
						queue.add(R+1);
						queue.add(0);
					}
					if(colour[R][1].equals("white") && !mygrid.getCell(R, 1).isWall()) {
						Cell c = mygrid.getCell(R, 1);
						colour[R][1] = "gray";
						weight[R][1] = weight[R][0] + c.getCost();
						previous[R][1] = R*10;
						//previous_column[1] = 0;
						queue.add(R);
						queue.add(1);
					}
					colour[R][C] = "black";
				}
			}
			else if(R==N-1) {		//if it is the last row
				if(C==M-1) {       // if it is in the last column
					if(colour[R-1][C].equals("white") && !mygrid.getCell(R-1, C).isWall()) {
						Cell c = mygrid.getCell(R-1, C);
						colour[R-1][C] = "gray";
						weight[R-1][C] = weight[R][C] + c.getCost();
						previous[R-1][C] = R*10+C;
						//previous_column[C] = C;
						queue.add(R-1);
						queue.add(C);
					}
					if(colour[R][C-1].equals("white") && !mygrid.getCell(R, C-1).isWall()) {
						Cell c = mygrid.getCell(R, C-1);
						colour[R][C-1] = "gray";
						weight[R][C-1] = weight[R][C] + c.getCost();
						previous[R][C-1] = R*10+C;
						//previous_column[C-1] = C;
						queue.add(R);
						queue.add(C-1);
					}
					colour[R][C] = "black";
				}
				else {		//everything else {N,0 has already been checked}
					if(colour[R-1][C].equals("white") && !mygrid.getCell(R-1, C).isWall()) {
						Cell c = mygrid.getCell(R-1, C);
						colour[R-1][C] = "gray";
						weight[R-1][C] = weight[R][C] + c.getCost();
						previous[R-1][C] = R*10+C;
						//previous_column[C] = C;
						queue.add(R-1);
						queue.add(C);
					}
					if(colour[R][C-1].equals("white") && !mygrid.getCell(R, C-1).isWall()) {
						Cell c = mygrid.getCell(R, C-1);
						colour[R][C-1] = "gray";
						weight[R][C-1] = weight[R][C] + c.getCost();
						previous[R][C-1] = R*10+C;
						//previous_column[C-1] = C;
						queue.add(R);
						queue.add(C-1);
					}
					if(colour[R][C+1].equals("white") && !mygrid.getCell(R, C+1).isWall()) {
						Cell c = mygrid.getCell(R, C+1);
						colour[R][C+1] = "gray";
						weight[R][C+1] = weight[R][C] + c.getCost();
						previous[R][C+1] = R*10+C;
						//previous_column[C+1] = C;
						queue.add(R);
						queue.add(C+1);
					}
					colour[R][C] = "black";
				}
			}
			else if(C==M-1) { 			//if it is in the last column
				if(colour[R-1][C].equals("white") && !mygrid.getCell(R-1, C).isWall()) {
					Cell c = mygrid.getCell(R-1, C);
					colour[R-1][C] = "gray";
					weight[R-1][C] = weight[R][C] + c.getCost();
					previous[R-1][C] = R*10+C;
					//previous_column[C] = C;
					queue.add(R-1);
					queue.add(C);
				}
				if(colour[R+1][C].equals("white") && !mygrid.getCell(R+1, C).isWall()) {
					Cell c = mygrid.getCell(R+1, C);
					colour[R+1][C] = "gray";
					weight[R+1][C] = weight[R][C] + c.getCost();
					previous[R+1][C] = R*10+C;
					//previous_column[C] = C;
					queue.add(R+1);
					queue.add(C);
				}
				if(colour[R][C-1].equals("white") && !mygrid.getCell(R, C-1).isWall()) {
					Cell c = mygrid.getCell(R, C-1);
					colour[R][C-1] = "gray";
					weight[R][C-1] = weight[R][C] + c.getCost();
					previous[R][C-1] = R*10+C;
					//previous_column[C-1] = C;
					queue.add(R);
					queue.add(C-1);
				}
				colour[R][C] = "black";
			}
			else { 		//if it is in the middle
				if(colour[R-1][C].equals("white") && !mygrid.getCell(R-1, C).isWall()) {
					Cell c = mygrid.getCell(R-1, C);
					colour[R-1][C] = "gray";
					weight[R-1][C] = weight[R][C] + c.getCost();
					previous[R-1][C] = R*10+C;
					//previous_column[C] = C;
					queue.add(R-1);
					queue.add(C);
				}
				if(colour[R+1][C].equals("white") && !mygrid.getCell(R+1, C).isWall()) {
					Cell c = mygrid.getCell(R+1, C);
					colour[R+1][C] = "gray";
					weight[R+1][C] = weight[R][C] + c.getCost();
					previous[R+1][C] = R*10+C;
					//previous_column[C] = C;
					queue.add(R+1);
					queue.add(C);
				}
				if(colour[R][C-1].equals("white") && !mygrid.getCell(R, C-1).isWall()) {
					Cell c = mygrid.getCell(R, C-1);
					colour[R][C-1] = "gray";
					weight[R][C-1] = weight[R][C] + c.getCost();
					previous[R][C-1] = R*10+C;
					//previous_column[C-1] = C;
					queue.add(R);
					queue.add(C-1);
				}
				if(colour[R][C+1].equals("white") && !mygrid.getCell(R, C+1).isWall()) {
					Cell c = mygrid.getCell(R, C+1);
					colour[R][C+1] = "gray";
					weight[R][C+1] = weight[R][C] + c.getCost();
					previous[R][C+1] = R*10+C;
					//previous_column[C+1] = C;
					queue.add(R);
					queue.add(C+1);
				}
				colour[R][C] = "black";
			}
			
			
		}
		//for(int i=0;i<N;i++) {
			//for(int j=0;j<M;j++) {
				//System.out.printf("[%d][%d]=====>[%d][%d]\n",i,j,previous_row[i],previous_column[j]);
			//}
		//}
		
		System.out.println("Finish!!!!!");
	
		
		int A=print_path(startNode[0],startNode[1],goalNode[0],goalNode[1],previous,weight,mygrid);
		System.out.printf("\nCost: %d",A);
		int steps[] = new int[path.size()];
		for(int i=0; i < path.size(); i++){
			   steps[i] = path.get(i);
		 }
		VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());
		
	}
	
	
	
	public static int print_path(int s0, int s1, int f0, int f1,int[][] pr,int[][] w,Grid mg) {
		if(s1 == f1 && s0 == f0) {
			//System.out.printf("s0: %d  ,s1:%d   , f0:%d   ,f1:%d\n",s0,s1,f0,f1);
	 		System.out.printf("[%d][%d]->",s0,s1);
	 		path.add(s0*mg.getNumOfColumns()+s1);
	 		Cell c = mg.getCell(s0, s1);
	 		return c.getCost();
	 		//return w[s0][s1];
		}
		else {
			if((f0==-1) | (f1==-1)) {
				System.out.println("There is no path!");
				return 0;
			}
			else {
				if(f0<0 | f1<0) {
					System.out.println("We are fucked!");
				}

				int x = pr[f0][f1]/10;
				int y = pr[f0][f1]%10;
				int all=print_path(s0,s1,x,y,pr,w,mg);
				path.add(f0*mg.getNumOfColumns()+f1);
				System.out.printf("->[%d][%d]",f0,f1);
				Cell c = mg.getCell(f0, f1);
				all = all + c.getCost();
		 		return all;
				
				//all = all+ w[f0][f1];
				//return all;
			}
		}
	}
	
	
public static int LRTA(int[] s,int[] ps, Grid mg,int[][] H,int[][] hManh,int[][][] r,int N,int M,int act) {
		
		Cell c = mg.getCell(s[0], s[1]);
		int[] a = new int[4]; 
		int min;
		int position;
		
		if(c.isWall()) {
			//System.out.println("Found wall\n");
			if(act==0) {
				hManh[s[0]][s[1]] = 20000;
				H[s[0]][s[1]] = 20000;
				r[ps[0]][ps[1]][act] = s[0]*10+s[1];
				ps[0] = s[0];
		    	ps[1] = s[1];
				return 2;
			}
			else if(act==1) {
				hManh[s[0]][s[1]] = 20000;
				H[s[0]][s[1]] = 20000;
				r[ps[0]][ps[1]][act] = s[0]*10+s[1];
				ps[0] = s[0];
		    	ps[1] = s[1];
				return 3;
			}
			else if(act==2) {
				hManh[s[0]][s[1]] = 20000;
				H[s[0]][s[1]] = 20000;
				r[ps[0]][ps[1]][act] = s[0]*10+s[1];
				ps[0] = s[0];
		    	ps[1] = s[1];
				return 0;
			}
			else {
				hManh[s[0]][s[1]] = 20000;
				H[s[0]][s[1]] = 20000;
				r[ps[0]][ps[1]][act] = s[0]*10+s[1];
				ps[0] = s[0];
		    	ps[1] = s[1];
				return 1;
			}
			
		}
		
		
		//if we are at the goal
	    if(c.isTerminal()) {
	    	System.out.println("It is terminal node");
	    	return 56;
	    }
	    else {
	    	if(H[s[0]][s[1]] == -1) {
		    	H[s[0]][s[1]] = hManh[s[0]][s[1]] ;
		    	//System.out.printf("Updated H[%d][%d] with value:%d\n", s[0],s[1],hManh[s[0]][s[1]]);
		    }
	    	
	    	if(ps[0] != -1 && ps[1] != -1) {
	    		r[ps[0]][ps[1]][act] = s[0]*10+s[1];
	    		Cell c1 = mg.getCell(ps[0], ps[1]);
	    		if(!c1.isWall()) {
	    		
	    			a = calculate_minimum_cost(ps,N,M,mg,r,hManh,H);
	    			
	    			for(int i=0;i<4;i++) {
	        			if(a[i]<0) {
	        				a[i] = 200000;
	        			}
	        		}
	    		
	    	/*
	    			System.out.println("\n===========================================\n");
	    			for(int i=0;i<4;i++) {
	    				System.out.printf("Action %d has cost => %d\n", i,a[i]);
	    			}
	    			System.out.println("\n===========================================\n");
	    		
	    	*/	
	    			min = a[0];
	    			position = 0;
	    			for(int i=1;i<4;i++) {
	    				if(a[i]<min) {
	    					min=a[i];
	    					position = i;
	    				}
	    			}
	    			H[ps[0]][ps[1]] = a[position];
	    			//System.out.printf(" Update cell [%d][%d] with value: %d\n", ps[0],ps[1],a[position]);
	    			
	    		}else {
	    			H[ps[0]][ps[1]] = 20000;
	    			//System.out.printf(" (*)Update cell [%d][%d] with value: 20000\n", ps[0],ps[1]);
	    		}
	    		
	    		
	    		
	    	
	    	}
	    	
	    
	    	a = calculate_minimum_cost(s,N,M,mg,r,hManh,H);
	    	
	    	
	    	//for(int i=0;i<4;i++) {
	    		//System.out.printf("Action %d with cost = %d\n", i,a[i]);
	    	//}
	    	
	    	
	    	for(int i=0;i<4;i++) {
    			if(a[i]<0) {
    				a[i] = 200000;
    			}
    		}
	    	/*
	    	System.out.println("\n******************************************\n");
    		for(int i=0;i<4;i++) {
    			System.out.printf("Action %d has cost => %d\n", i,a[i]);
    		}
    		System.out.println("\n******************************************\n");
    		*/
	    	
	    	
	    	min = a[0];
	    	position = 0;
	    	for(int i=1;i<4;i++) {
	    		if(a[i]<min) {
	    			min=a[i];
	    			position = i;
	    		}
	    	}
	    	
	    	//System.out.printf("So i am choosing action %d \n",position );
	    	
	    	//int s_help = find_next_cell(s,position);
	    	//ps[0] = s_help/10;
	    	//ps[1] = s_help%10;
	    	
	    	//System.out.printf("And i will go at cell [%d][%d]  \n",s_help/10,s_help%10 );
	    	
	    	ps[0] = s[0];
	    	ps[1] = s[1];
	    	
	    	return position;
	    
	    }
	}
	
	public static int find_next_cell(int[] s, int position) {
		if(position == 0) { //up
			return ((s[0]-1)*10+s[1]);
		}
		else if(position == 1) { //right
			return (s[0]*10+s[1]+1);
		}
		else if(position == 2) { //down
			return ((s[0]+1)*10+s[1]);
		}
		else { //left
			return (s[0]*10+s[1]-1);
		}
	}
	
	
	public static int[] calculate_minimum_cost(int[] s,int N,int M,Grid mygrid,int[][][] r,int[][] hm,int[][] H) {
		int R = s[0];
		int C = s[1];
		
		int x0,x1,x2,x3;
		
		
		if(R==0) {   //if it is in the first row
			if(C==0) { //if it is (0,0)
				x0 = 100000;
				if(r[R][C][1] == -11) {
					x1 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C+1);
					int help1=r[R][C][1]/10;
					int help2= r[R][C][1]%10;
					if(!c.isWall()) {
						x1 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x1=20000;
					}
				}
				if(r[R][C][2] == -11) {
					x2 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R+1,C);
					int help1= r[R][C][2]/10;
					int help2= r[R][C][2]%10;
					if(!c.isWall()) {
						x2 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x2=20000;
					}
				}
				x3 = 100000;
				int[] help={x0,x1,x2,x3};
				return help;
			}
			else if(C==M-1) { //if it is the last of the 1st row
				x0 = 100000;
				x1 = 100000;
				if(r[R][C][2] == -11) {
					x2 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R+1,C);
					int help1= r[R][C][2]/10;
					int help2= r[R][C][2]%10;
					if(!c.isWall()) {
						x2 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x2=20000;
					}
				}
				if(r[R][C][3] == -11) {
					x3 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C-1);
					int help1= r[R][C][3]/10;
					int help2= r[R][C][3]%10;
					if(!c.isWall()) {
						x3 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x3=20000;
					} 
				}
				
				int[] help={x0,x1,x2,x3};
				return help;
			}
			else {
				x0 = 100000;
				if(r[R][C][1] == -11) {
					x1 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C+1);
					int help1= r[R][C][1]/10;
					int help2= r[R][C][1]%10;
					if(!c.isWall()) {
						x1 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x1=20000;
					} 
				}
				if(r[R][C][2] == -11) {
					x2 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R+1,C);
					int help1= r[R][C][2]/10;
					int help2= r[R][C][2]%10;
					if(!c.isWall()) {
						x2 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x2=20000;
					} 
				}
				if(r[R][C][3] == -11) {
					x3 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C-1);
					int help1= r[R][C][3]/10;
					int help2= r[R][C][3]%10;
					if(!c.isWall()) {
						x3 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x3=20000;
					} 
				}
				
				int[] help={x0,x1,x2,x3};
				return help;
				
			}
		}
		else if(C==0) { // if it is in the first column
			if(R==N-1) { // if it is in the last row
				if(r[R][C][0] == -11) {
					x0 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R-1,C);
					int help1= r[R][C][0]/10;
					int help2=r[R][C][0]%10;
					if(!c.isWall()) {
						x0 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x0=20000;
					}
				}
				if(r[R][C][1] == -11) {
					x1 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C+1);
					int help1= r[R][C][1]/10;
					int help2= r[R][C][1]%10;
					if(!c.isWall()) {
						x1 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x1=20000;
					} 
				}
				x2 =100000;
				x3 =100000;
				
				int[] help={x0,x1,x2,x3};
				return help;
				
			}
			else { 				//everything else {0,0 has already been checked}
				
				if(r[R][C][0] == -11) {
					x0 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R-1,C);
					int help1= r[R][C][0]/10;
					int help2= r[R][C][0]%10;
					if(!c.isWall()) {
						x0 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x0=20000;
					}
				}
				if(r[R][C][1] == -11) {
					x1 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C+1);
					int help1= r[R][C][1]/10;
					int help2= r[R][C][1]%10;
					if(!c.isWall()) {
						x1 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x1=20000;
					} 
				}
				if(r[R][C][2] == -11) {
					x2 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R+1,C);
					int help1= r[R][C][2]/10;
					int help2= r[R][C][2]%10;
					if(!c.isWall()) {
						x2 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x2=20000;
					}
				}
				x3 =100000;
				
				int[] help={x0,x1,x2,x3};
				return help;
			}
		}
		else if(R==N-1) {		//if it is the last row
			if(C==M-1) {       // if it is in the last column
				x2 =100000;
				x1 =100000;
				
				if(r[R][C][0] == -11) {
					x0 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R-1,C);
					int help1= r[R][C][0]/10;
					int help2= r[R][C][0]%10;
					if(!c.isWall()) {
						x0 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x0=20000;
					} 
				}
				if(r[R][C][3] == -11) {
					x3 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C-1);
					int help1= r[R][C][3]/10;
					int help2= r[R][C][3]%10;
					if(!c.isWall()) {
						x3 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x3=20000;
					} 
				}
				
				
				int[] help={x0,x1,x2,x3};
				return help;
				
			}
			else {		//everything else {N,0 has already been checked}
				if(r[R][C][0] == -11) {
					x0 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R-1,C);
					int help1= r[R][C][0]/10;
					int help2= r[R][C][0]%10;
					if(!c.isWall()) {
						x0 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x0=20000;
					} 
				}
				if(r[R][C][1] == -11) {
					x1 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C+1);
					int help1= r[R][C][1]/10;
					int help2=r[R][C][1]%10;
					if(!c.isWall()) {
						x1 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x1=20000;
					} 
				}
				if(r[R][C][3] == -11) {
					x3 =  hm[R][C];
				}
				else {
					Cell c = mygrid.getCell(R,C-1);
					int help1= r[R][C][3]/10;
					int help2= r[R][C][3]%10;
					if(!c.isWall()) {
						x3 =c.getCost()+ H[help1][help2]; //c.getCost()+
					}else {
						x3=20000;
					}
				}
				x2 =100000;
				
				int[] help={x0,x1,x2,x3};
				return help;
				
			}
		}
		else if(C==M-1) { 			//if it is in the last column
			if(r[R][C][0] == -11) {
				x0 =  hm[R][C];
			}
			else {
				Cell c = mygrid.getCell(R-1,C);
				int help1= r[R][C][0]/10;
				int help2= r[R][C][0]%10;
				if(!c.isWall()) {
					x0 =c.getCost()+ H[help1][help2]; //c.getCost()+
				}else {
					x0=20000;
				} 
			}
			if(r[R][C][3] == -11) {
				x3 =  hm[R][C];
			}
			else {
				Cell c = mygrid.getCell(R,C-1);
				int help1= r[R][C][3]/10;
				int help2= r[R][C][3]%10;
				if(!c.isWall()) {
					x3 =c.getCost()+ H[help1][help2]; //c.getCost()+
				}else {
					x3=20000;
				} 
			}
			if(r[R][C][2] == -11) {
				x2 =  hm[R][C];
			}
			else {
				Cell c = mygrid.getCell(R+1,C);
				int help1= r[R][C][2]/10;
				int help2= r[R][C][2]%10;
				if(!c.isWall()) {
					x2 =c.getCost()+ H[help1][help2]; //c.getCost()+
				}else {
					x2=20000;
				} 
			}
			x1 =100000;
			
			int[] help={x0,x1,x2,x3};
			return help;
			
		}
		else { 		//if it is in the middle
			if(r[R][C][0] == -11) {
				x0 =  hm[R][C];
			}
			else {
				Cell c = mygrid.getCell(R-1,C);
				int help1= r[R][C][0]/10;
				int help2= r[R][C][0]%10;
				if(!c.isWall()) {
					x0 =c.getCost()+ H[help1][help2]; //c.getCost()+
				}else {
					x0=20000;
				} 
			}
			if(r[R][C][1] == -11) {
				x1 =  hm[R][C];
			}
			else {
				Cell c = mygrid.getCell(R,C+1);
				int help1= r[R][C][1]/10;
				int help2= r[R][C][1]%10;
				if(!c.isWall()) {
					x1 =c.getCost()+ H[help1][help2]; //c.getCost()+
				}else {
					x1=20000;
				} 
			}
			if(r[R][C][2] == -11) {
				x2 =  hm[R][C];
			}
			else {
				Cell c = mygrid.getCell(R+1,C);
				int help1= r[R][C][2]/10;
				int help2= r[R][C][2]%10;
				if(!c.isWall()) {
					x2 =c.getCost()+ H[help1][help2]; //c.getCost()+
				}else {
					x2=20000;
				} 
			}
			if(r[R][C][3] == -11) {
				x3 =  hm[R][C];
			}
			else {
				Cell c = mygrid.getCell(R,C-1);
				int help1= r[R][C][3]/10;
				int help2= r[R][C][3]%10;
				if(!c.isWall()) {
					x3 =c.getCost()+ H[help1][help2]; //c.getCost()+
				}else {
					x3=20000;
				} 
			}
			
			
			int[] help={x0,x1,x2,x3};
			return help;
	}
	
	
}
	
		
}