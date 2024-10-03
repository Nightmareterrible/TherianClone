
import java.awt.*;
import java.awt.geom.Point2D;


class Сharacter extends Pawn {


	public Сharacter(){

	}
	public static void draw(Graphics g) {

	}

}
class Pawn {
	Point2D position = new Point();

	//getters and setters
	public int getX() {//тоже самое что и getCoordinates() только сокращеное написание

		return (int) position.getX();
	}

	public int getY() {//тоже самое что и getCoordinates() только сокращеное написание
		return (int) position.getY();
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
}