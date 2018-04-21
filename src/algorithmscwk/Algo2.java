
package algorithmscwk;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import javax.swing.JOptionPane;
 
public class Algo2 {
   public static int VH_hCost = 1;
    public static int D_Cost = 2;
    static int F_Cost;

    static Node[][] grid = new Node[40][40];

    static ArrayList<Integer[]> path = new ArrayList<Integer[]>();

    static PriorityQueue<Node> open;

    static boolean closed[][];

    static int blocked[][];

    static int xStart, yStart;
    static int xEnd, yEnd;
     
    public static char metrics='m';

    public static void setStartCell(int x, int y) {
        xStart = x;
        yStart = y;
    }

    public static void setEndCell(int x, int y) {
        xEnd = x;
        yEnd = y;
    }

    public static void setBlocked(int x, int y) {
        grid[x][y] = null;
    }

    public static void calculateCost(Node current,Node temp){

        if(temp !=null) {
            if(!closed[temp.x][temp.y]) {
                if (open.contains(temp)) {
                    int tempgCost = current.gCost + Values.values_array2[temp.x][temp.y];
                    if(temp.gCost>tempgCost) {
                        temp.gCost = tempgCost;
                        System.out.println(temp.hCost);
                        temp.fCost = temp.gCost;
                        temp.parent = current;
                    }
                }else{
                    temp.parent = current;
                    temp.gCost = current.gCost + Values.values_array2[temp.x][temp.y];
                    System.out.println(temp.hCost);
                    temp.fCost = temp.gCost;
                }
                open.add(temp);

            }
        }
    }

    public static void checking(){
        open.add(grid[xStart][yStart]);
        Node current;
      

        while (true) {
            current = open.poll();
            if (current == null) {
                break;
            }
            if(closed[current.x][current.y]){
                continue;
            }else {
                closed[current.x][current.y] = true;
            }
            if (current.equals(grid[xEnd][yEnd])) {
                return;
            }
            Node t;
            if(current.x-1>=0){
                t = grid[current.x-1][current.y];
                calculateCost(current, t);
                if(current.y-1>=0){
                    t = grid[current.x-1][current.y-1];
                    calculateCost(current, t);
                }
                if(current.y+1<grid[0].length){
                    t = grid[current.x-1][current.y+1];
                    calculateCost(current, t);
                }
            }
            if(current.y-1>=0){
                t = grid[current.x][current.y-1];
                calculateCost(current, t);
            }
            if(current.y+1<grid[0].length){
                t = grid[current.x][current.y+1];
                calculateCost(current, t);
            }
            if(current.x+1<grid.length){
                t = grid[current.x+1][current.y];
                calculateCost(current, t);
                if(current.y-1>=0){
                    t = grid[current.x+1][current.y-1];
                    calculateCost(current, t);
                }
                if(current.y+1<grid[0].length){
                    t = grid[current.x+1][current.y+1];
                    calculateCost(current, t);
                }
            }
        }

    }

    public static void run( int sx, int sy, int ex, int ey,int[][] blocked) {

        // nxn grid
        grid = new Node[40][40];

        // to be blocked at end
        //matrix = new boolean[n][n];
        // no cells are visited at beginning
        closed = new boolean[40][40];

        // priority queue to contain open cells
        // cell with least finalCost to be chosen as head
        open = new PriorityQueue<>(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {

                Node c1 = (Node) o1;
                Node c2 = (Node) o2;

                int cost1 = c1.fCost + c1.hCost;
                int cost2 = c2.fCost + c2.hCost;

                if (cost1 < cost2) {
                    return -1; // this matters for priority
                } else if (cost1 > cost2) {
                    return 1;
                } else {
                    return 0; // equal fCosts : then first in first out
                }
            }
        });

        // calculate path - time start


        // set start position x,y static variables
        setStartCell(sy, sx);

        // set end position x,y static variables
        setEndCell(ey, ex);

        // calculate & assign heuristic costs
        for (int x = 0; x < Values.values_array2.length; x++) { // x
            for (int y = 0; y < Values.values_array2[x].length; y++) { // y

                // create a cell object in the grid with coordinates
                grid[x][y] = new Node(x, y);

                // calculate & assign heuristic cost for cell object

                    // manhattan
                
                  if(metrics=='m'){
                     Heuristic heuristic = new Heuristic();
                    grid[x][y].hCost = (int) heuristic.calManhattan(x,xEnd,y,yEnd);
                  }else if(metrics=='e'){
                     Heuristic heuristic = new Heuristic();
                    grid[x][y].hCost = (int) heuristic.calEuclidean(x,xEnd,y,yEnd);
                  }else if(metrics=='c'){
                     Heuristic heuristic = new Heuristic();
                    grid[x][y].hCost = (int) heuristic.calChebyshev(x,xEnd,y,yEnd);
                  }else{
                      Heuristic h=new Heuristic();
                      grid[x][y].hCost=h.calValue(x, xEnd, y, yEnd);
                  }
                    

            }
        }
        
                for(int i=0;i<blocked.length;++i){
            setBlocked(blocked[i][0], blocked[i][1]);
        }


        checking();

        // trace path ///////////////////////////////////////////////////////////

        if (closed[xEnd][yEnd]) {

            // start from end point
            Node current = grid[xEnd][yEnd];
            System.out.println("Full Cost : "+current.gCost);
            path.add(new Integer[]{(current.x), (current.y)});

            // iterate until the super parent (starting cell) is found
            while (current.parent != null) {
                // add parent of the current node
                path.add(new Integer[]{(current.parent.x), (current.parent.y)});

                // ready for next parent finding
                System.out.println("Path : "+current.hCost + " GC : " +current.gCost);
                current = current.parent;


                PathFind2.squares[current.y][current.x].setBackground(Color.green);
                PathFind2.squares[PathFind2.strtX][PathFind2.strtY].setBackground(Color.red);
                
              
            }

            // calculate path - time end


            System.out.println("");
            System.out.println("Path found!");
            System.out.println("");

        } else {

            // end point has not reached
            // path stopped in middle
            // calculate path - time end

            System.out.println("");
            System.out.println("No possible path!");
            System.out.println("");
            
            
            JOptionPane.showMessageDialog(PathFind.f, "There is no path to reach the destination", "Impossible Path", JOptionPane.WARNING_MESSAGE);
        }
        ////////////////////////////////////////////////////////////////////////

        // display grid & path in CLI - time start


        // display grid ////////////////////////////////////////////////////////
        System.out.println("Grid: ");
        for (int x = 0; x < 40; x++) { // x
            for (int y = 0; y < 40; y++) { // y

                boolean cont = false; // "in path arraylist" status

                for (Integer[] iarr : path) {

                    if (iarr[0] == x && iarr[1] == y) {
                        cont = true;
                    }

                }

                if (x == sy && y == sx) {
                    System.out.print("S  "); // Starting Point
                } else if (x == ey && y == ex) {
                    System.out.print("E  ");  // End Point
                } else if (cont) {
                    System.out.print("X  "); // Path
                } else if (grid[y][x] != null) {
                    System.out.printf("%-3d", 0); // Unvisited Cell
                } else {
                    System.out.print("1  "); // blocked
                }

            }
            System.out.println();

        }

        System.out.println();
        ////////////////////////////////////////////////////////////////////////

      

    }    
}
