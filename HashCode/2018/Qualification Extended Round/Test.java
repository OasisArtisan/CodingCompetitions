/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hashcode2018;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author OasisArtian
 */
public class Test {
    public static void main(String[] args) throws IOException
    {
        BufferedImage img = new BufferedImage(5,5,BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                img.setRGB(i, j, Color.WHITE.getRGB());
            }
        }
        int blue = Color.BLUE.getRGB();
        int x = 1;
        int y = 4;
        int x1 = y;
        int y1 = img.getHeight()- x - 1;
        img.setRGB(x1, y1, blue);
        Imaging.writeImage(Imaging.scaleImg(50, img), new File("test.png"));
    }
}
