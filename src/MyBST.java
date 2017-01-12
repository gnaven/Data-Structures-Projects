import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MyBST <T extends Comparable <T>> implements BST {
	private MyTreeNode root;

	//@Override
	public void insert(Line2D line, int i, ArrayList<Line2D> LineCompare) {// TODO Auto-generated method stub
			
			root=MyTreeNode.insert(root, line, i,LineCompare );
		}
	public int QueryPoint(Point2D point1, Point2D point2, ArrayList<Line2D>linestored){
		Line2D QueryLine= new Line2D.Double(point1.getX(), point1.getY(), point2.getX(), point2.getY());
		int x =MyTreeNode.QueryPoint(root, QueryLine,linestored);
		System.out.println("if it prints out -1 it means that there is no intersection");
		System.out.println("They are separated by a line is "+ x);
		return x;
	}
public void ExternalNodeCount(){
	int x =MyTreeNode.countLeaves(root);
	System.out.println("The number of external nodes is "+x);
}
public void AveragePathLength(){
	int y= MyTreeNode.epl(root);
	int x= MyTreeNode.countLeaves(root);
	// average path length isexternal path length divided by number of external nodes
	double avg= y/x;
	System.out.println("The average path length is "+avg);
}
	@Override
	public void delete(Comparable x) {
		if (lookup(x)==true){
		 root= MyTreeNode.delete(root,x);
		}else {
			System.out.println(x+" does not exist in the tree");
		}
	}

	@Override
	public boolean lookup(Comparable x) {
	  return (MyTreeNode.lookup(root,x));
	
	}

	@Override
	public void printPreOrder() {
		MyTreeNode.PreprintOrder(root);
		
	}

	@Override
	public void printInOrder() {
		MyTreeNode.printInOrder(root);
		
	}

	@Override
	public void printPostOrder() {
		MyTreeNode.printPostOrder(root);
		
	}



}

