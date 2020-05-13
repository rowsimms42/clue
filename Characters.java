
import java.io.Serializable;

public class Characters implements Serializable{
	
	private String name;
	private int color;
	private int xStarting, yStarting, roomNumOrder; 
	
	public Characters(String name, int color, int x, int y, int roomNumOrder) {
		this.name  = name;
		this.color = color;
		this.xStarting = x;
		this.yStarting = y;
		this.roomNumOrder = roomNumOrder;
	}
	
	public String getName() { return name;}
	public int getColor() {return color;}
	public int getxStarting() {return xStarting;}
	public int getyStarting() {return yStarting;}
	public int getRoomNumOrder() {return roomNumOrder;}
}
