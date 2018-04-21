
package algorithmscwk;


public class Node {
    
     int x;  // X Coordinate
    int y;//  Y Coordinate 
    int gCost = 0; // G : Movement Cost
    int hCost = 0; // H : Heuristic Cost
    int fCost = 0; // F = Movement Cosst + Heuristic Cost
    int totcost = 0;
    Node parent; // Current node that is being checked

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
}
