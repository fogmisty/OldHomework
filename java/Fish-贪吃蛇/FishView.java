package Fish;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class FishView implements Observer {
    FishControl control = null;
    FishModel model = null;

    JFrame mainFrame;
    Canvas paintCanvas;
    JLabel labelScore;

    public static final int canvasWidth = 500;
    public static final int canvasHeight = 500;

    public static final int nodeWidth = 10;
    public static final int nodeHeight = 10;
    
    

    public FishView(FishModel model, FishControl control) {
        this.model = model;
        this.control = control;

        mainFrame = new JFrame("Ã∞≥‘”„");

        Container cp = mainFrame.getContentPane();


        paintCanvas = new Canvas();
        paintCanvas.setSize(canvasWidth, canvasHeight);
        paintCanvas.addKeyListener(control);
        cp.add(paintCanvas, BorderLayout.CENTER);

        mainFrame.addKeyListener(control);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    void repaint() {
        Graphics g = paintCanvas.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        g.setColor(Color.WHITE);
        LinkedList na = model.nodeArray;
        Iterator it = na.iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            drawNode(g, n);
        }

        g.setColor(Color.WHITE);
        Node n1 = model.food1;
        Node n2 = model.food2;
        drawNode(g, n1);
        drawNode(g, n2);

    }

    private void drawNode(Graphics g, Node n) {
        g.fillRect(n.x * nodeWidth,
                n.y * nodeHeight,
                nodeWidth ,
                nodeHeight );
    }


    public void update(Observable o, Object arg) {
        repaint();
    }
}
