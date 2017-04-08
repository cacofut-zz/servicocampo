package br.com.servicocampo.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

public class ImageResizerService implements Resizer{
	  
    public byte[] read(InputStream file) throws IOException {
        byte[] buffer = new byte[1024];
 
        InputStream is = file;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
 
        while (is.read(buffer) != -1) {
            out.write(buffer);
        }
        try {
            return out.toByteArray();
        }finally {
            is.close();   
            out.close();            
        }
         
    }
 
    public byte[] read( InputStream arquivo, String file,  double requiredWidth, double requiredHeight) throws IOException {
 
        BufferedImage imagem = ImageIO.read( arquivo );
 
        double originalWidth  = imagem.getWidth();
        double originalHeight = imagem.getHeight();
        double newWidth = 0;
        double newHeight = 0;
        double diff = 0;
 
        if (requiredHeight == 0) {
            requiredHeight = requiredWidth;
        }
 
        if (requiredWidth == 0) {
            requiredWidth = requiredHeight;
        }
 
        if(originalWidth < requiredWidth && originalHeight < requiredHeight){
            return read(arquivo);
        }
 
        if(requiredWidth == 0 && requiredHeight == 0){
            return read(arquivo);
        }
 
        if (originalWidth > originalHeight) {
            diff = originalWidth - originalHeight;
            newWidth = requiredWidth;            
            diff = diff / originalWidth;
            newHeight = newWidth - (newWidth * diff);
        } else if (originalWidth < originalHeight) {
            diff = originalHeight - originalWidth;
            newHeight = requiredHeight;            
            diff = diff / originalHeight;
            newWidth = newHeight - (newHeight * diff);
        } else {
 
            if (requiredHeight > requiredWidth) {
                requiredHeight = requiredWidth;
            } else if (requiredHeight < requiredWidth) {
                requiredWidth = requiredHeight;
            }
 
            newHeight = requiredHeight;
            newWidth = requiredWidth;
        }
 
 
 
        int type = BufferedImage.TYPE_INT_RGB;
        boolean isPng = file.toUpperCase().endsWith("PNG");
 
        if (isPng) {
            type = BufferedImage.BITMASK;
        }
 
 
        BufferedImage new_img = new BufferedImage((int) newWidth, (int) newHeight, type);
        Graphics2D g = new_img.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(imagem, 0, 0, (int) newWidth, (int) newHeight, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
 
        if (isPng) {
            ImageIO.write(new_img, "PNG", out);
        }else{
            ImageIO.write(new_img, "JPG", out);
        }
 
        try {
            return out.toByteArray();
        } finally {
            out.close();
        }
    }
    
    
    
 
}

