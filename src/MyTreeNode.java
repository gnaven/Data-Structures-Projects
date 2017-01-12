import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


import javax.sound.sampled.Line;


public class MyTreeNode<T extends Comparable <T>> {
	public T data;
	public MyTreeNode<T> leftChild;
	public MyTreeNode<T> rightChild;
	public MyTreeNode<T> parent;
	
	public MyTreeNode(T data){
		this.data=data;
	}
	
	public static MyTreeNode insert(MyTreeNode node, Line2D line, int i, ArrayList<Line2D> linestored ){
		
		if (node== null){
			MyTreeNode newNode= new MyTreeNode(i);
			
			newNode.data= i;
			return newNode;
		} else {
			// if statements within that determine whether the line intersects and if it does then sends it through both the
			// left and right child
			
			// line.get(x) is the line that needs to be inserted
			//line.get(node.data) is the comparer
			int data= Integer.parseInt(node.data.toString());

				if (intersection(line,linestored.get(data))){
						//call to return points of intersection
					// recursive way to divide the line by interesection points with the comparator
					// this should be in this if statement as intersection means where division will happen
					Point2D lineTemp1= linestored.get(data).getP1();
					Point2D lineTemp2 = linestored.get(data).getP2();
					Point2D Linecheck1= (line.getP1());
					Point2D Linecheck2= (line.getP2());
					
				
					Point2D POI = intersectionPoint(linestored.get(data), line);
					
					String pointCompare1 = ccw(Linecheck1,lineTemp1,lineTemp2);
					String pointCompare2 = ccw(Linecheck2,lineTemp1,lineTemp2);
						Line2D LineSegL = new Line2D.Double();
						Line2D LineSegR = new Line2D.Double();
						if (pointCompare1.equals("CLOCKWISE")){
							LineSegL.setLine(POI, Linecheck2);
							LineSegR.setLine(POI, Linecheck1);
						}else if (pointCompare2.equals("CLOCKWISE")){
							LineSegL.setLine(POI, Linecheck1);
							LineSegR.setLine(POI, Linecheck2);
						}
					node.leftChild= insert(node.leftChild,LineSegL,i, linestored);
					node .rightChild=insert (node.rightChild,LineSegR,i, linestored);
							
				}else {
					
					// method for comparing on which side the line is
					//http://stackoverflow.com/questions/28809563/how-to-retrieve-the-coordinates-of-a-line2d-in-java
					Point2D p1= linestored.get(data).getP1();
					Point2D p2 = linestored.get(data).getP2();;
					Point2D p0 = (line.getP1());
				
					String position = ccw(p0,p1,p2);
									if (position.equals("CLOCKWISE")){
										node.rightChild=insert (node.rightChild,line,i, linestored);
									}else if (position.equals("COUNTERCLOCKWISE")){
										node.leftChild= insert(node.leftChild,line,i, linestored);
									}
				  	  }
		}
		return node;	
	}
	// method for checking which side the line is
	public static String ccw(Point2D p0, Point2D p1, Point2D p2) {
		 double dx1 = p1.getX() - p0.getX();
		 double dy1 = p1.getY() - p0.getY();
		 double dx2 = p2.getX() - p0.getX();
		 double dy2 = p2.getY() - p0.getY();
		 if (dx1*dy2 > dy1*dx2) return "COUNTERCLOCKWISE";
		 else if (dx1*dy2 < dy1*dx2) return "CLOCKWISE";
		 else if ((dx1*dx2 < 0) || (dy1*dy2 < 0)) return "CLOCKWISE";
		 else if ((dx1*dx1+dy1*dy1) < (dx2*dx2+dy2*dy2)) return "COUNTERCLOCKWISE";
		 else return "COLINEAR";
		}
	// Method that checks intersection
	 public static boolean intersection(Line2D line1, Line2D line2) {
		 double x1= line1.getX1();
		 double y1= line1.getY1();
		 double x2= line1.getX2();
		 double y2= line1.getY2();
		 double x3= line2.getX1();
		 double y3= line2.getY1();
		 double x4= line2.getX2();
		 double y4= line2.getY2();
		 
			    double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
			    if (d == 0) return false;
			    
			    double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
			    double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
			    
			    //Point p = new Point(xi,yi);
			   if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return false;
			    if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return false;
			    return true;
			  }
	 // method that finds the intersection points 
	 //http://www.ahristov.com/tutorial/geometry-games/intersection-segments.html
	 public static Point2D intersectionPoint(Line2D line1, Line2D line2) {
		 double x1= line1.getX1();
		 double y1= line1.getY1();
		 double x2= line1.getX2();
		 double y2= line1.getY2();
		 double x3= line2.getX1();
		 double y3= line2.getY1();
		 double x4= line2.getX2();
		 double y4= line2.getY2();
		 
			    double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
			    if (d == 0) return null;
			    
			    double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
			    double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
			    Point2D p= new Point2D.Double(xi, yi);
			
			   if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
			    if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return null;
			    return p;
			  }
	
	 //Method for comparing the query point
	 // two of the query points are made into a line and are taken in as arguments
	 public static int QueryPoint(MyTreeNode node, Line2D line, ArrayList<Line2D> linestored){
		
					
					if (node!= null){
						int data= Integer.parseInt(node.data.toString());
						Point2D p1= linestored.get(data).getP1();
						Point2D p2 = linestored.get(data).getP2();;
						Point2D p0 = (line.getP1());
						String position = ccw(p0,p1,p2);
						if (intersection(line,linestored.get(Integer.parseInt(node.data.toString())))){
							//System.out.println("line in the middle "+ Integer.parseInt(node.data.toString()));
						return Integer.parseInt(node.data.toString());
						}else if ( position.equals("COUNTERCLOCKWISE")){
								if (node.leftChild==null){
									return -1;
								}else{
									return QueryPoint(node.leftChild,line,linestored);
								}
						} if (position.equals("CLOCKWISE")){
							if (node.rightChild== null){
								return -1;
							}else {
							return QueryPoint(node.rightChild,line,linestored);
							}
						}
						}
			
		 return -1;
	 }
// Look up method 

	public static boolean lookup (MyTreeNode node, Comparable x) {
		if (node!= null){
		if (x.equals(node.data)){
			return true;
		}else if ( x.compareTo(node.data)<0){
			if (node.leftChild==null){
				return false;
			}else{
			return lookup(node.leftChild,x);
			}
		} if (x.compareTo(node.data)>0){
			if (node.rightChild== null){
				return false;
			}else {
			return lookup(node.rightChild,x);
			}
		}
		}
		  return false;
  }
	// counts the number of external nodes
	public static int countLeaves(MyTreeNode node){
		  if( node == null )
		    return 0;
		  if( node.leftChild == null || node.rightChild == null ) {
		    return 1;
		  } else {
		    return countLeaves(node.leftChild) + countLeaves(node.rightChild);
		  }
		}
	//method that counts the external path length of the tree
	public static int epl(MyTreeNode node) {
	    if (node==null) return 1;
	    else return epl(node.leftChild) + epl(node.rightChild) ;
	}
	public static void PreprintOrder(MyTreeNode node) {
		if (node == null){
		
		}else {
		System.out.print(node.data+" ");
		if (node.leftChild != null){
			PreprintOrder(node.leftChild);
		}
		if (node.rightChild != null){
			PreprintOrder(node.rightChild);
		}
	}
}
	public static void printInOrder(MyTreeNode node){
		if (node == null){
			
		}else {
			if (node.leftChild != null){
				printInOrder(node.leftChild);
			}
			System.out.print(node.data+" ");
			if (node.rightChild != null){
				printInOrder(node.rightChild);
				
			}
		}
		
	}


	public static void printPostOrder(MyTreeNode node) {
if (node == null){
			
		}else {
			if (node.leftChild != null){
				PreprintOrder(node.leftChild);
				
			}
			if (node.rightChild != null){
				PreprintOrder(node.rightChild);
				System.out.print(node.data+" ");
			}
			
		}
		
		
		
	}
//Question 6
	public static MyTreeNode delete(MyTreeNode node, Comparable x) {
		if (x.compareTo(node.data)<0){
		if ((node.rightChild).equals(null) && (node.leftChild).equals(null)){
			node=null;
			
		}else {
			delete(node.leftChild,x);
		}
		}
		if (x.compareTo(node.data)>0){
			
			if ((x.equals(node.data))){
				
				node=null;
			}else {
				delete(node.rightChild,x);
			}
			
		}
		
		//System.out.println("hello "+node.data);	
		if (x.equals(node.data)){
			
			node=null;
		}
		return node;
	}
  	
		
	
	
}




