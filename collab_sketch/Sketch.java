import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Sketch Class, PS6
 * @Author Ryan Lee, CS10, Winter 2021
 */

public class Sketch {
    private TreeMap<Integer, Shape> shapeMap; //Map that contains shapes
    static int shapeID; //id of shapes

    public Sketch(){ //instantiates Sketch, starting shapeID at 0 and creating new treemap for shapeMap
        shapeID = 0;
        this.shapeMap = new TreeMap<>();
    }

    public Map<Integer, Shape> getShapeMap() {
        return this.shapeMap;
    } //get function for shapeMap

    public int getShapeId(Point p){ //gets ID for shape
        for (int id : shapeMap.descendingKeySet()) {
            if (shapeMap.get(id).contains(p.x, p.y)) {
                return id; //if the shape is identified, return the id of that shape
            }
        }
        return 0; //if the specified shape is not found, return 0
    }

    public Shape getShape(int id){
        return shapeMap.getOrDefault(id, null);
    } //get function for a specific shape

    public synchronized void addShape(Shape shape) {    //add function for shapes
        shapeMap.put(shapeID, shape);
        shapeID++;
    }

    public synchronized void removeShape(Shape shape) { //remove function for shape
        for(Integer id : shapeMap.descendingKeySet()){
            if(shapeMap.get(id).equals(shape)){ //find that shape, remove it
                shapeMap.remove(id);
                return;
            }
        }
    }

    public ArrayList<Shape> shapeList() {   //creates list of shapes
        ArrayList<Shape> list = new ArrayList<>();
        for(Integer id : shapeMap.navigableKeySet()){
            list.add(shapeMap.get(id));
        }
        return list;
    }

    public synchronized void clear(){ //clear function for the board
        shapeMap.clear();
        shapeID = 0;
    }

    public synchronized void setShapeID(int id, Shape shape){
        shapeMap.put(id, shape);
    }  //sets an id for a shape

    public synchronized void recolorShape(int id, Color color) {
        shapeMap.get(id).setColor(color);
    } //recoloring method

    public synchronized void moveShape(int id, int dx, int dy) {
        shapeMap.get(id).moveBy(dx, dy);
    }  //moves the shape

    @Override
    public String toString(){ //toString method for all the shapes in shapeMap
        String s = "";
        for(Integer id : shapeMap.descendingKeySet()){
            s += id + "," + getShape(id).toString() + " ";
        } 
        return s;
    }

}
