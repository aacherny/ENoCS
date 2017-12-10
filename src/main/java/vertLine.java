package main.java;

        import javax.swing.*;
        import java.awt.*;

public class vertLine extends JPanel {

    public vertLine(){
        setPreferredSize(new Dimension(36, 36));
        setLocation(0,0);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(18, 0, 18, 36);
    }
}
