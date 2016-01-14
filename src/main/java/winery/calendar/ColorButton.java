package winery.calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JButton;

public class ColorButton extends JButton {

    private int numberOfEvents;
    private Color firstEvent;
    private Color secondEvent;
    private Color thirdEvent;

    public ColorButton(String text) {
        setText(text);
        firstEvent = Color.WHITE;
        secondEvent = Color.WHITE;
        thirdEvent = Color.WHITE;
        numberOfEvents = 0;
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(firstEvent);
        g.fillRect(0, 0, getWidth(), getHeight() / 3);
        
        g.setColor(secondEvent);
        g.fillRect(0, getHeight() / 3, getWidth(), getHeight() / 3);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 2 *(getHeight() / 3), getWidth(), getHeight() / 3);
        g.setColor(thirdEvent);
        g.drawString("  ...", 0, getHeight() - 5);
        
        
        g.setColor(Color.BLACK);
        g.drawString(getText(), getWidth() / 2, getHeight() / 2);
        
        
    }

    public void addEvent() {
    	if (numberOfEvents == 0) {
    		numberOfEvents++;
    		firstEvent = Color.BLUE;
    		this.repaint();
    	}
    	else if (numberOfEvents == 1) {
    		numberOfEvents++;
    		secondEvent = Color.RED;
    		this.repaint();
    	}
    	else {
    		numberOfEvents++;
    		thirdEvent = Color.BLACK;
    		this.repaint();
    	}
    }
    

}
