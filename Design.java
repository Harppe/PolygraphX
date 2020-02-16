import java.awt.*;
import javax.swing.border.TitledBorder;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
public class Design extends JFrame implements ChangeListener {
    public static final String[] digits = {"0","1","2","3","4","5","6","7","8","9"};

    public Design() {
        getEquation();
        initUI();
    }

    public void getEquation() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("polynomial.txt")));
            reader.readLine();
            reader.readLine();
            reader.readLine();
            String currentLine = reader.readLine();

            StringTokenizer st = new StringTokenizer(currentLine," ");
            Polynomial poly = new Polynomial();
            while (st.hasMoreTokens()) {
                float coeff = Float.parseFloat(st.nextToken());
                float power = Float.parseFloat(st.nextToken());
                poly.addTerm(coeff,power);
            }
            Main.polynom=poly;

            currentLine = reader.readLine();

        }
        catch (Exception e) {
            System.out.println(e);   
        }
    }

    private void initUI() {
        setSize(900, 450);
        setLayout(new GridLayout(1,0));
        setTitle("PolygraphX");
        TitledBorder border = new TitledBorder(Main.polynom.toString());
        border.setTitlePosition(TitledBorder.TOP);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JSlider zoom = new JSlider(JSlider.HORIZONTAL,1,5,1);
        zoom.addChangeListener(this);
        zoom.setMajorTickSpacing(10);
        zoom.setMinorTickSpacing(1);
        zoom.setPaintTicks(true);
        zoom.setBorder(BorderFactory.createEmptyBorder(10,10,10,0));
        DesignDrawing dd = new DesignDrawing();
        zoom.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JPanel menu = new JPanel();
        menu.add(zoom);
        menu.setLayout(new GridLayout(2,2));
        menu.setBorder(border);
        add(dd);
        add(menu);
    }

    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider)e.getSource();
        if (!slider.getValueIsAdjusting()) {
            double zoom = slider.getValue();
            DesignDrawing.ZOOM = zoom/2;
        }
        repaint();  
    }

    public Polynomial stringToPoly(String expression) {
        Polynomial poly = new Polynomial();

        return poly;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {

                    Design ex = new Design();
                    ex.setVisible(true);
                }
            });
    }
}