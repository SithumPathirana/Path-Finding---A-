
package algorithmscwk;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
/**
 *
 * @author DELL
 */
public class PathFind2 {
    public static  int rows = 40;
    public static  int columns = 40;
    
    public static JFrame f1;
    private final JPanel gui1 = new JPanel(new BorderLayout(5, 5));
    public static JButton[][] squares = new JButton[rows][columns];
    public static int[][] buttonWeight = new int[rows][columns];
    public static double[][]  hVal=new double[rows][columns];
    private JPanel board;
    private JPanel options;
    private JButton generatePath;
    private JButton refresh;
    private JButton changeGrid;
    private JTextField strtPointXCoordinate;
    private JTextField strtPointYCoordinate;
    private JTextField endPointXCoordinate;
    private JTextField endPointYCoordinate;
    private   JComboBox distanceMetrics;
             
      static int strtX;
      static int strtY;
      static  int endX;
      static  int endY;
      
      static final int diaganalCost=14;
      static final int vhcost=10;
      
       static long strtTime;
      static long endTime;
      static long totalTime;
    
    
     
      
              int[][] blockedPath = new int[][]{
                  {11,13},{19,13},{11,14},{12,14},{19,14}
                ,{12,15},{13,15},{14,15},{17,15},{18,15},{19,15},
                {13,16},{14,16},{15,16},{16,16},{17,16},{18,16},{19,16},
                {13,17},{14,17},{15,17},{16,17},{17,17},{18,17},{19,17},
                {11,18},{12,18},{13,18},{14,18},{15,18},{16,18},{17,18},{18,18},{19,18},
                {11,19},{12,19},{13,19},{14,19},{15,19},{16,19},{17,19},{18,19},{19,19}
                
        };

    public PathFind2() {
       // setButtonWeight();
   //     abc();
        initializeGUI();

    }

    public void initializeGUI() {
        gui1.setBorder(new EmptyBorder(2, 2, 2, 2));
        board = new JPanel(new GridLayout(rows, columns));
        options = new JPanel(new GridBagLayout());

        options.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        GridBagConstraints c = new GridBagConstraints();

        JLabel xCoordinate = new JLabel("X Coordinate");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 5, 0);
        options.add(xCoordinate, c);
        JLabel yCoordinate = new JLabel("Y Coordinate");
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 5, 0);
        options.add(yCoordinate, c);
        JLabel strtPoint = new JLabel("Starting Point");
        c.gridx = 0;
        c.gridy = 1;
        options.add(strtPoint, c);

        strtPointXCoordinate = new JTextField();
        strtPointXCoordinate.setPreferredSize(new Dimension(50, 25));
        c.gridx = 1;
        c.gridy = 1;

        options.add(strtPointXCoordinate, c);

        strtPointYCoordinate = new JTextField();

        strtPointYCoordinate.setPreferredSize(new Dimension(50, 25));

        c.gridx = 2;
        c.gridy = 1;
        options.add(strtPointYCoordinate, c);

        JLabel endPoint = new JLabel("End Point");
        c.gridx = 0;
        c.gridy = 2;
        options.add(endPoint, c);

        endPointXCoordinate = new JTextField();

        endPointXCoordinate.setPreferredSize(new Dimension(50, 25));

        c.gridx = 1;
        c.gridy = 2;
        options.add(endPointXCoordinate, c);
        endPointYCoordinate = new JTextField();

        endPointYCoordinate.setPreferredSize(new Dimension(50, 25));

        c.gridx = 2;
        c.gridy = 2;
        options.add(endPointYCoordinate, c);
        generatePath = new JButton("Generate Path");
        c.gridx = 1;
        c.gridy = 4;
        options.add(generatePath, c);
        
        changeGrid=new JButton("20*20 Grid");
        c.gridx=0;
        c.gridy=4;
        options.add(changeGrid,c);
        
        refresh=new JButton("Refresh");
        c.gridx=2;
        c.gridy=4;
        options.add(refresh,c);

        JLabel distancelbl = new JLabel("Distance Option");
        c.gridx = 0;
        c.gridy = 3;
        options.add(distancelbl, c);

        String[] distanceOptions = {"Manhattan", "Euclidean", "Chebychev"};
         distanceMetrics = new JComboBox(distanceOptions);
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(5, 30, 5, 10);
        options.add(distanceMetrics, c);

        ButtonClickListner listner = new ButtonClickListner();
        generatePath.addActionListener(listner);
        refresh.addActionListener(listner);
        changeGrid.addActionListener(listner);

        board.setBorder(new LineBorder(Color.BLACK));

        JLabel title = new JLabel("Path Finder");

        gui1.add(board, BorderLayout.CENTER);
        gui1.add(title, BorderLayout.PAGE_START);
        gui1.add(options, BorderLayout.PAGE_END);

        Insets buttonMargin = new Insets(0, 0, 0, 0);

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {

                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(7, 7, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if (Values.values_array2[i][j] == 1) {
                    b.setBackground(Color.WHITE);
                }

                if (Values.values_array2[i][j] == 4) {
                    b.setBackground(Color.decode("#A8A8A8"));
                }

                if (Values.values_array2[i][j] == 3) {
                    b.setBackground(Color.decode("#C8C8C8"));
                }

                if (Values.values_array2[i][j] == 2) {
                    b.setBackground(Color.decode("#D3D3D3"));
                }
                //                
                //                if(r==2){
                //                    b.setBackground(Color.decode("#DCDCDC"));
                //                }
                //                if(r==3){
                //                     b.setBackground(Color.decode("#C0C0C0"));
                //                }
                if (Values.values_array2[i][j] == 5) {
                    b.setBackground(Color.BLACK);
                }
                squares[j][i] = b;
//                ButtonClickListner newlistner=new ButtonClickListner();
                squares[j][i].addActionListener(listner);
            }
        }
        
//        try{
//             Image img = ImageIO.read(getClass().getResource("resources/image.jpg"));
//              
//              
//        }catch(Exception e){
//        
//        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board.add(squares[j][i]);
            }

        }
        
        

    }

    public JComponent getChessBoard() {
        return board;
    }

    public JComponent getGui() {
        return gui1;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                PathFind2 cb
                        = new PathFind2();

                f1 = new JFrame("Find Path");
                f1.setResizable(false);
                f1.setLocationRelativeTo(null);
                f1.add(cb.getGui());
                f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f1.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f1.pack();
                // ensures the minimum size is enforced.
                f1.setMinimumSize(f1.getSize());
                f1.setVisible(true);
                
                f1.setLocationRelativeTo(null);

            }
        };
        SwingUtilities.invokeLater(r);
    }

    private class ButtonClickListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            int tempSX=0;
//            int tempSY=0;
//            int tempEX=0;
//            int tempEY=0;
//             for(int i=0;i<squares.length;i++){
//                           for(int j=0;j<squares[i].length;j++){
//                               if(e.getSource()==squares[j][i]){
//                                   tempSX=j;
//                                   tempSY=i;
//                                   break;
////
//                                  
//                               }
//                           }
//                        }
//             final int SX=tempSX;
//             final int SY=tempSY;
//              
////              EX=tempX;
////              EY=tempY;
//              System.out.println("Start X Coordinate : "+SX);
//              System.out.println("Start Y Coordinate : "+SY);
              
             // System.out.println
                        
                       
            if (e.getSource() == generatePath) {

                String strtXC = strtPointXCoordinate.getText();
                String strtYC = strtPointYCoordinate.getText();
                String endXC = endPointXCoordinate.getText();
                String endYC = endPointYCoordinate.getText();

                if (strtXC.equals("") || strtYC.equals("") || endXC.equals("") || endYC.equals("")) {

                    JOptionPane.showMessageDialog(f1, "Please fill the coordinates before generating the path", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                     strtX = Integer.parseInt(strtPointXCoordinate.getText());
                     strtY = Integer.parseInt(strtPointYCoordinate.getText());
                     endX = Integer.parseInt(endPointXCoordinate.getText());
                     endY = Integer.parseInt(endPointYCoordinate.getText());

                    if ((strtX < 0 || strtX >= rows) || (strtY < 0 || strtY >= rows) || (endX < 0 || endX >= rows) || (endY < 0 || endY >= rows)) {
                        JOptionPane.showMessageDialog(f1, "Coordinates you enterd are invalid", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        
                       

                        squares[strtX][strtY].setBackground(Color.red);
                        squares[endX][endY].setBackground(Color.blue);
                        changeMetrics();
                        strtTime=System.nanoTime();
                         //setManhattanValue();
                       // Algorithm.test(rows,columns,strtX,strtY,endX,endY,blockedPath);
                        Algo2.run(strtX,strtY, endX, endY,blockedPath);
                         endTime=System.nanoTime();
                          totalTime=(endTime-strtTime)/1000000;
                        System.out.println(totalTime);

                    }

                }

                //                
            }
            if(e.getSource()==refresh){
              f1.dispose();
              Runnable r = new Runnable() {

            @Override
            public void run() {
                PathFind2 cb
                        = new PathFind2();

                f1 = new JFrame("Find Path");
                f1.setResizable(true);
                f1.setLocationRelativeTo(null);
                f1.add(cb.getGui());
                f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f1.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f1.pack();
                // ensures the minimum size is enforced.
                f1.setMinimumSize(f1.getSize());
                f1.setVisible(true);
                
                f1.setLocationRelativeTo(null);

            }
        };
        SwingUtilities.invokeLater(r);
             
            }
            
            if(e.getSource()==changeGrid){
                f1.dispose();
                 Runnable r = new Runnable() {

            @Override
            public void run() {
                PathFind cb
                        = new PathFind();

               PathFind. f = new JFrame("Find Path");
                PathFind.f.setResizable(false);
                PathFind.f.setLocationRelativeTo(null);
                PathFind.f.add(cb.getGui());
                PathFind.f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                PathFind.f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                PathFind.f.pack();
                // ensures the minimum size is enforced.
                PathFind.f.setMinimumSize(PathFind.f.getSize());
                PathFind.f.setVisible(true);
                
                PathFind.f.setLocationRelativeTo(null);

            }
        };
        SwingUtilities.invokeLater(r);
            }
            
                  

        }

    }

    private void setButtonWeight() {
        int x = 0, y = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader("test.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                String[] values = line.split(",");

                for (String str : values) {
                    int str_int = Integer.parseInt(str);
                    buttonWeight[x][y] = str_int;
                    y++;

                }
                y = 0;
                x++;

            }
            in.close();

        } catch (Exception e) {
            System.out.println(e);

        }
//
//                  for(int i=0;i<buttonWeight.length;i++){
//                    for(int j=0;j<buttonWeight[i].length;j++){
//                        System.out.print(buttonWeight[j][i]+" ");
//                    }
//                    System.out.println();
//                  }
    }
    
//    private void setManhattanValue(){
//        System.out.println(endX);
//        System.out.println(endY);
//        for(int i=0;i<hVal.length;i++){
//            for(int j=0;j<hVal[i].length;j++){
//                hVal[j][i]=Heuristic.(j, endX, i,endY);
//            }
//        
//        }
//        
//          for(int i=0;i<hVal.length;i++){
//                    for(int j=0;j<hVal[i].length;j++){
//                        System.out.print((int)hVal[j][i]+" ");
//                    }
//                    System.out.println();
//                  }
//        
//        
//    
//    }
//    
//    private void setEuclidValue(){
//        
//          for(int i=0;i<hVal.length;i++){
//            for(int j=0;j<hVal[i].length;j++){
//                hVal[j][i]=Heuristuc.calculateHValueEuclidean(j, endX, i,endY);
//            }
//        
//        }
//          
//          
////          for(int i=0;i<hVal.length;i++){
////                    for(int j=0;j<hVal[i].length;j++){
////                        System.out.print(hVal[j][i]+" ");
////                    }
////                    System.out.println();
////                  }
//        
//    
//    }
//    
//    private void setChebyshevValue(){
//          for(int i=0;i<hVal.length;i++){
//            for(int j=0;j<hVal[i].length;j++){
//                hVal[j][i]=Heuristuc.calculateHValueChebyShev(j, endX, i,endY);
//            }
//        
//        }
//        
//    
//    }
    
    private void changeMetrics(){
      int  index =distanceMetrics.getSelectedIndex();
     
      if(index==0){
         Algo2.metrics='m';
      }
      
      if(index==1){
         Algo2.metrics='e';
      }
      
      if(index==2){
         Algo2.metrics='c';
      }
     
    }
    
}
