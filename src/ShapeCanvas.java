import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

class ShapeCanvas extends Canvas {

    private static ShapeCanvas ourInstance = new ShapeCanvas(GlobalConfig.CANVAS_WIDTH, GlobalConfig.CANVAS_HEIGHT);
    public static ShapeCanvas getInstance() {
        return ourInstance;
    }

    int widthScreenInPixels = 1920;
    int heighScreenInPixels = 1080;
    double diagonalScreeninInches = 15.0;
    boolean shapetype = true;

    ArrayList<Shape> shapeQueue = new ArrayList<>();
    ArrayList<Shape> shapes = new ArrayList<>();

    ShapeCanvas(int width, int height) {
        super(width, height);
        this.getGraphicsContext2D().setFill(GlobalConfig.DEFAULT_COLOR);
        setUpMouseListener();
        this.setVisible(true);
    }
    private void setUpMouseListener() {
        this.setOnMouseClicked(event -> {

            if (shapetype) {
                Circle newCircle = new Circle(new Point2D(event.getX(), event.getY()));
                drawShape(newCircle);
                shapeQueue.add(newCircle);
            } else {
                Rectangle newRectangle = new Rectangle(new Point2D(event.getX(), event.getY()));
                drawShape(newRectangle);
                shapeQueue.add(newRectangle);
            }
        });
    }
    // the queue was added to avoid simultaneous use of the ArrayList "shapes" by both the user using the mouse,
    // and the scheduled job Physics
    void emptyQueue() {
        if (shapeQueue.size() > 0) {
            shapes.addAll(shapeQueue);
            shapeQueue.removeAll(shapeQueue);
        }
    }
    void drawShape(Shape someShape) {
        this.getGraphicsContext2D().setFill(Color.GREEN);
        getGraphicsContext2D().fillPolygon(someShape.getXs(), someShape.getYs(), someShape.vertexOffsets.length);

    }
    void erase() {
        GraphicsContext graphicsController = this.getGraphicsContext2D();
        graphicsController.setFill(Color.WHITE);
        graphicsController.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
