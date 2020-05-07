import java.awt.Color;
import java.io.Serializable;

public class Characters implements Serializable{
	
	private String name;
	private int color;
	private int xStarting, yStarting;
	//private XYPoint startingPoint;
	
	

	public Characters(String name, int color, int x, int y) {
		this.name  = name;
		this.color = color;
		this.xStarting = x;
		this.yStarting = y;
	}
	
	public String getName() { return name;}
	public int getColor() {return color;}
	public int getxStarting() {return xStarting;}
	public int getyStarting() {return yStarting;}
	//public XYPoint getStartingPoint( ) {return startingPoint;}
}
