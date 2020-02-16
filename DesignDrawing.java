import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
class DesignDrawing extends JPanel {
    private int margin = 10;
    Graphics2D g2d;
    int dwidth;
    int dheight;
    private final int INIT_DELAY = 200;
    private final int DELAY = 780;
    private final double PRECISION = 160d;
    public static double ZOOM = 0.5;
    Polynomial poly;
    // g2d.drawLine(int x,int y,int x2,int y2);

    public DesignDrawing() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){}
        poly = Main.polynom;
    }

    public int getRand(int max) {
        double ratio = Math.random();
        int x = (int)(ratio*max);
        return x;
    }
    // g2d.drawLine(int x,int y,int x2,int y2);
    public void grapher() throws Exception {
        g2d.setStroke(new BasicStroke(1f));

        int delta = (int)(margin/ZOOM);

        int originX = dwidth/2;
        int originY = dheight/2;

        Polynomial poly = Main.polynom;

        //do graph

        // start
        int domain = dwidth-2*margin;

        int numOfX = domain/delta;

        int range = dheight-2*margin;

        int numOfY = range/delta;

        int currentX = -(numOfX/2);
        // stop
        g2d.setColor(Color.GRAY);
        for (int i = 0; i<=numOfX/2; i++) {

            int topY = margin;
            int botY = dheight-margin;

            int posX = i*delta+originX;
            int negX = originX-i*delta;

            drawLine(posX,topY,posX,botY);
            drawLine(negX,topY,negX,botY);
        }
        for (int i = 0; i<=numOfY/2; i++) {

            int rightX = dwidth-margin;
            int leftX = margin;

            int posY = originY-i*delta;
            int negY = originY+i*delta;

            drawLine(leftX,posY,rightX,posY);
            drawLine(leftX,negY,rightX,negY);   
        }
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.4f));
        drawLine(margin,dheight/2,dwidth-margin,dheight/2);
        drawLine(dwidth/2,margin,dwidth/2,dheight-margin);

        // pixels per 1 unit = delta

        int interval = delta;
        int intervals = delta*(int)PRECISION*numOfX;
        g2d.setColor(Color.RED); // color red

        for (int i = -intervals/2; i<intervals/2; i++) {

            double firstX = i/PRECISION;
            double firstY = poly.solve(firstX);

            double secondX = (i+1)/PRECISION;
            double secondY = poly.solve(secondX);

            int x1 = (int)(firstX*interval)+originX;
            int y1 = originY-(int)(firstY*interval);

            int x2 = (int)(secondX*interval)+originX;
            int y2 = originY-(int)(secondY*interval);
            if (inBounds(x1,y1,x2,y2))
                drawLine(x1,y1,x2,y2);

        }
    }

    public boolean inBounds(int x1, int y1, int x2, int y2) {
        if (x1>margin&&x1<dwidth-margin&&x2>margin&&x2<dwidth-margin&&y1>margin&&y1<dheight-margin&&y2>margin&&y2<dheight-margin) {
            return true;
        }
        return false;
    }

    public void drawLine(int x1, int y1, int x2, int y2) throws Exception {
        g2d.drawLine(x1,y1,x2,y2);
    }

    private void doDrawing(Graphics g) 
    {
        g2d = (Graphics2D) g;
        setBackground(Color.white);
        g2d.setColor(Color.blue );

        int width  = getSize().width;
        int height = getSize().height;
        int min;
        dwidth = width;
        dheight = height;
        if ( height < width )
            min = height;
        else
            min = width;

        margin = min / 25;
        try {
            grapher();
        }
        catch (Exception e) {   
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}