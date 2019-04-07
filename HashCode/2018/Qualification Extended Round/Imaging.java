package hashcode2018;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Imaging {
    
    
    public static void writeImage(BufferedImage img , File file) throws IOException
    {
        ImageIO.write(img, "png", file);
    }
    
    public static BufferedImage scaleImg(int scale, BufferedImage img) {
        BufferedImage after = new BufferedImage(img.getWidth() * scale, img.getHeight() * scale, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < img.getWidth(); x++)
        {
            for (int y = 0; y < img.getHeight(); y++)
            {
                for (int x1 = x * scale; x1 < scale + x * scale; x1++)
                {
                    for (int y1 = y * scale; y1 < scale + y * scale; y1++)
                    {
                        after.setRGB(x1, y1, img.getRGB(x, y));
                    }
                }
            }
        }
        return after;
    }
    public static Color mergeColors(Color c1, Color c2)
    {
        int alpha = c1.getAlpha() + c2.getAlpha();
        if(alpha > 255)
        {
            alpha = 255;
        }
        return new Color(
                (c1.getRed() + c2.getRed()) / 2,
                (c1.getGreen()+ c2.getGreen()) / 2,
                (c1.getBlue()+ c2.getBlue()) / 2,
                alpha);
    }
     public static void outputImages(int[][] grid, Ride[] rides,String file, HashMap<String,Object> stats) throws IOException
    {
        //grid.length = height, grid[0].length = width
        BufferedImage img = new BufferedImage(grid[0].length, grid.length, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                img.setRGB(i, j, Color.WHITE.getRGB());
            }
        }
        Color nonTakenRidesColorPath = new Color(0.5F, 0.5F, 0.5F, 1F);
        for (Ride r : rides)
        {
            if (!r.taken)
            {
                Point start = new Point(r.startIntersection.y, grid.length - r.startIntersection.x - 1);
                Point end = new Point(r.finishIntersection.y, grid.length - r.finishIntersection.x - 1);
                if (start.x > end.x)
                {
                    for (int x = start.x - 1; x > end.x; x--)
                    {
                        img.setRGB(x, start.y, Imaging.mergeColors(new Color(img.getRGB(x, start.y),true), nonTakenRidesColorPath).getRGB());
                    }
                } else
                {

                    for (int x = start.x + 1; x < end.x; x++)
                    {
                        img.setRGB(x, start.y, Imaging.mergeColors(new Color(img.getRGB(x, start.y),true), nonTakenRidesColorPath).getRGB());
                    }
                }
                if (start.y > end.y)
                {
                    for (int y = start.y; y > end.y; y--)
                    {
                        img.setRGB(end.x, y, Imaging.mergeColors(new Color(img.getRGB(end.x, y),true), nonTakenRidesColorPath).getRGB());
                    }
                } else
                {
                    for (int y = start.y; y < end.y; y++)
                    {
                        img.setRGB(end.x, y, Imaging.mergeColors(new Color(img.getRGB(end.x, y),true), nonTakenRidesColorPath).getRGB());
                    }

                }
                g.setColor(Color.BLUE);
                g.fillOval(start.x - 4, start.y - 4, 8, 8);
                g.setColor(Color.RED);
                g.fillOval(end.x - 4, end.y - 4, 8, 8);
            }
        }
        img = Imaging.scaleImg(2, img);
        g = img.createGraphics();
        int x = (int)(img.getWidth() * (4D / 7D));
        int y = (int)(img.getHeight()* (4D / 5D));
        double scale = (Math.sqrt(img.getWidth() * img.getHeight()))/10;
        int marginX = (int) (scale * 0.10F);
        int marginY = (int) (scale * 0.28F);
        int fontSize = (int)(scale * 0.15);
        g.setFont(new Font( "SansSerif", Font.BOLD,fontSize ));
        g.setColor(Color.WHITE);
        g.fillRect(x, y, (int)(scale * 3.5), (int)(scale *1.2));
        g.fillRect(0, 0, (int)(scale * 2.5), (int)(scale * 0.5));
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke((float)scale * 0.03F));
        g.drawRect(x, y, (int)(scale * 3.5), (int)(scale*1.2));
        g.drawRect(0, 0, (int)(scale * 2.5), (int)(scale*0.5));
        g.drawString(file + " (rides missed)", marginX, marginY);
        g.drawString("Score: " + stats.get("SCORE"), x + marginX, y + marginY );
        g.drawString("Rides taken: " + stats.get("RIDES_TAKEN"), x + marginX, y + marginY * 1.5F + fontSize);
        g.drawString("Rides missed: " + stats.get("RIDES_MISSED"), x + marginX, y + marginY * 2F + fontSize * 2);
        g.drawString("Bonuses: " + stats.get("BONUSES"),  x + marginX + (int)(scale*1.5), y + marginY );
        g.drawString("Unused cars: " + stats.get("UNUSED_CARS"),  x + marginX + (int)(scale*1.5), y + marginY * 1.5F + fontSize);
        g.drawString("Avg wasted time: " + Math.round((Double)stats.get("AVG_WASTED_TIME")),  x + marginX + (int)(scale*1.5),y + marginY * 2F + fontSize * 2);
        g.setColor(Color.BLUE);
        g.fillOval(img.getWidth()/2 - marginX * 20, img.getHeight() - marginY, (int)(0.2*scale), (int)(0.2*scale));
        g.drawString("Start",  img.getWidth()/2 - marginX * 17, img.getHeight() - marginY + (int)(0.18*scale));
        g.setColor(Color.RED);
        g.fillOval(img.getWidth()/2 + marginX * 10, img.getHeight() - marginY, (int)(0.2*scale), (int)(0.2*scale));
        g.drawString("End",  img.getWidth()/2 + marginX * 13, img.getHeight() - marginY + (int)(0.18*scale));
        Imaging.writeImage(img, new File("src/in-out/imgs/" + file + "_missed_rides.png"));
    }
}
