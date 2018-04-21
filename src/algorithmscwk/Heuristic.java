
package algorithmscwk;

import static java.lang.Math.abs;


public class Heuristic {
    
    
       // Calculate the manhaton value
     public double calManhattan(int x1, int x2 ,int y1, int y2){

        double manhattan;

        int finalX = Math.abs(x1-x2);
        int finalY = Math.abs(y1-y2);

        manhattan = finalX + finalY;

        return manhattan;
    }

     //  // Calculate the Euclidean value
    public double calEuclidean(int x1, int x2 ,int y1, int y2){

        double euclidean;

        double finalX = Math.pow(x1-x2,2);
        double finalY = Math.pow(y1-y2,2);

        euclidean = Math.sqrt(finalX+finalY);

        return euclidean;

    }
 
    //  // Calculate the chebychev value
    public double calChebyshev(int x1,int x2, int y1, int y2){

        double chebyshev;

        double finalX = Math.abs(x1-x2);
        double finalY = Math.abs(y1-y2);

        chebyshev = Math.max(finalX,finalY);

        return chebyshev;
    }
    
    public int calValue(int x1,int x2,int y1,int y2){
       
     return 0;
    }
}
