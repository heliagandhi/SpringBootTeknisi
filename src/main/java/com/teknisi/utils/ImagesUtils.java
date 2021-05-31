package com.teknisi.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImagesUtils {

    public String convertImagesBase64(byte[] bytes, String fileType) throws Exception {
        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage originalImage = ImageIO.read(is);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, fileType, outputStream);
        if (fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg")) {
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } else {
            return "";
        }
    }
}