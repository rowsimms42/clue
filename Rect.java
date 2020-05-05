public class Rect{
    
    private int xMin, xMax, yMin, yMax;

    public Rect(int x_min, int x_max, int y_min, int y_max){
        xMin = x_min;
        xMax = x_max;
        yMin = y_min;
        yMax = y_max;
    }

    public Boolean isWithIn(int x, int y){
        return (x >= xMin && x <= xMax) && (y >= yMin && y <= yMax);
    }

}