import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PointLocation extends JPanel implements MouseListener, MouseMotionListener {
	
	public PointLocation(){
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		setSize(500,500);
		setVisible(true);
		
	}

	// method to take in the input as arrays and then arrange them to different lines
	static MyBST Tree= new MyBST();
	public static ArrayList<Line2D> Coordinates (ArrayList<Double> coordinates){
		// array list which will store every new line
		ArrayList<Line2D> lines= new ArrayList<Line2D>();
		int j = 0;
		for (int i= 0; i < coordinates.get(0); i++){
			// j is used to jump the array to get co-ordinates for the next line
			
			Line2D newL= new Line2D.Double(coordinates.get(1+j), coordinates.get(2+j), coordinates.get(3+j), coordinates.get(4+j));
			j+=4;
			lines.add(newL);
			
		}
		
		return lines;
	}
	//inserts lines as Binary Tree
	public static void insertion (ArrayList<Line2D> l){
		for (int i =0 ; i <l.size(); i++){
			Tree.insert(l.get(i), i,l);
		}
	}
	public void PointToLine(double x1, double y1, double x2, double y2){
		Line2D line = new Line2D.Double(x1,y1,x2,y2);
		
	}
	public static int QueryCheck(double x1, double y1, double x2, double y2, ArrayList<Line2D> line){
		Point2D point1= new Point2D.Double(x1, y1);
		Point2D point2= new Point2D.Double(x2, y2);
		return Tree.QueryPoint(point1, point2, line);
	}
// method reads the numbers and puts it into a ArrayList
	public static ArrayList<Double>  read() throws FileNotFoundException{
		BufferedReader br = null;
		

		Scanner scan = new Scanner (new File("Input.txt"));
		double numOfLines= scan.nextInt();

		ArrayList<Double>storage=new ArrayList<Double>();
		storage.add(numOfLines);
		
		for (int i =1; i<=numOfLines;i++){
			for (int j =0; j<4; j++){

				storage.add(scan.nextDouble());
			}
		}

		scan.close();
		return storage;
	}
	static ArrayList<Line2D> LineForm = new ArrayList<Line2D>();
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner (new File("Query.txt"));
		ArrayList<Double> Coord= read();

		PointLocation f= new PointLocation();
		JFrame frame= new JFrame();
		frame.setVisible(true);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(f);
		
		LineForm= Coordinates(Coord);
		// calls the insertion to insert lines into a Binary Tree
		insertion(LineForm);
		//Prints the Binary Tree formed in order
		System.out.println("The Binary Tree is printed InOrder");
		Tree.printInOrder();
		System.out.println();
		//calls method to check two query points that the parenthesis takes
		QueryCheck(scan.nextDouble(),scan.nextDouble(),scan.nextDouble(),scan.nextDouble(),LineForm);
		
		
		//calls the method that finds the number of external nodes in the current Tree
		Tree.ExternalNodeCount();
		Tree.AveragePathLength();
		
		read();
}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setFocusable(true);
		ArrayList<Double> drawStore= new ArrayList<Double>();
	
		try {
			drawStore= read();
		System.out.println(getWidth());
		int j=1;
			for (int i=1; i<=drawStore.get(0);i++){

				int x1= (int) ((drawStore.get(j+0))*getWidth());
				int y1= (int) (getHeight()-(drawStore.get(j+1))*getHeight());
				int x2= (int) ((drawStore.get(j+2))*getWidth());
				int y2= (int) (getHeight()-(drawStore.get(j+3))*getHeight());
					
					g.drawLine(x1, y1, x2, y2);
					
			j+=4;	
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		//QueryCheck(0.4,0.2,0.05,0.1,drawStore);
		
	}

static double XD ;
static double YD ;
static double X1D ;
static double Y1D ;
	@Override
	public void mouseClicked(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	double w= getWidth();
	double h= getHeight();
	@Override
	public void mousePressed(MouseEvent e) {
		
		XD=(e.getX()/500.0);
		YD=(getHeight()-e.getY())/500.0;
		Graphics g = getGraphics();
		g.fillOval(e.getX(), e.getY(), 5, 5);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		X1D=e.getX()/500.0;
		Y1D=(getHeight()-e.getY())/500.0;
		
		Graphics g = getGraphics();
		//g.drawLine(X, Y, X1, Y1);
		
		g.fillOval(e.getX(), e.getY(), 5, 5);
		System.out.println("for" + XD+" , "+ YD+" , "+X1D+" ,"+ Y1D);
		QueryCheck(XD,YD,X1D,Y1D,LineForm);
		ArrayList<Double> drawStore= new ArrayList<Double>();
		
		
			try {
				drawStore= read();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//System.out.println(getWidth());
		int j=(QueryCheck(XD,YD,X1D,Y1D,LineForm)*4)+1;
		
			
System.out.println("the line is "+j);
				int x1= (int) ((drawStore.get(j+0))*getWidth());
				int y1= (int) (getHeight()-(drawStore.get(j+1))*getHeight());
				int x2= (int) ((drawStore.get(j+2))*getWidth());
				int y2= (int) (getHeight()-(drawStore.get(j+3))*getHeight());
				g.setColor(Color.BLUE);
				g.drawLine(x1, y1, x2, y2);
					
			
			}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
		
		
	}


