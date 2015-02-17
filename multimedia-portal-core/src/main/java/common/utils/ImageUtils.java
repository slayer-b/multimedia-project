/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package common.utils;

import common.utils.image.BufferedImageHolder;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * @author demchuck.dima@gmail.com
 */
public class ImageUtils {

    private ImageUtils() {
    }

    /**
     * Считает отношение новой высоты к старой
     *
     * @param origin оригинальная ширина картинки
     * @param scaled требуемая ширина
     * @return возвращает промасштабирование
     */
    public static double getScaling(int origin, int scaled) {

        if (scaled >= origin || scaled <= 0) {
            return 1d;
        }
        return ((double) scaled) / (double) origin;
    }

    /**
     * Считает отношение новой высоты к старой
     *
     * @param scaling scaling
     * @param image   picture
     * @return возвращает промасштабирование
     */
    public static Dimension getDimmension(double scaling, BufferedImage image) {
        return new Dimension((int) (scaling * image.getWidth()), (int) (scaling * image.getHeight()));
    }

    /**
     * resize input image to new dimension(only smaller) and save it to file
     *
     * @param image        input image for scaling
     * @param scale_factor desired value of width in result image
     * @param rez          writes resulting image to a file
     */
    public static void saveScaledImageWidth(BufferedImage image, double scale_factor, File rez)
            throws IOException {
        FileOutputStream fos = null;
        try {
            File parent = rez.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IOException("Failed to create parent folder ["
                        + parent.getAbsolutePath() + "].");
            }
            fos = new FileOutputStream(rez);
            saveScaledImageWidth(image, scale_factor, fos);
            fos.close();
        } catch (IOException e) {
            throw new IOException("Failed to save scaled image to given file", e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * resize input image to new dimension(only smaller) and save it to file
     *
     * @param image        input image for scaling
     * @param scale_factor factor for scaling image
     * @param rez          writes resulting image to a file
     */
    private static void saveScaledImageWidth(BufferedImage image, double scale_factor, OutputStream rez)
            throws IOException {
        if (scale_factor == 1d) {
            writeImage(image, 1f, rez);
        } else {
            int width = (int) (scale_factor * image.getWidth());
            int height = (int) (scale_factor * image.getHeight());
            int type = image.getType();
            BufferedImage scaledImage;
            if (BufferedImage.TYPE_CUSTOM == type) {
                scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            } else {
                scaledImage = new BufferedImage(width, height, type);
            }

            Graphics2D g2 = scaledImage.createGraphics();

            g2.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            writeImage(scaledImage, 1f, rez);

            g2.dispose();
        }
    }

    /**
     * resize input image to new dimension(only smaller) into rez parameter
     *
     * @param image input image for scaling
     * @param rez   resulting image. must have required width and height
     */
    public static void getScaledImageDimension(BufferedImage image, BufferedImage rez) {
        Graphics2D g2 = rez.createGraphics();
        if (rez.getHeight() > image.getHeight() || rez.getWidth() > image.getWidth()) {
            //rez image is bigger no resize
            g2.drawImage(image, 0, 0, null);
            return;
        }
        //1-st getting first side to resize (width or height)
        double scaleFactor;
        if (getScaling(image.getHeight(), rez.getHeight()) > getScaling(image.getWidth(), rez.getWidth())) {
            //resize height
            scaleFactor = getScaling(image.getHeight(), rez.getHeight());
            int width = (int) (scaleFactor * image.getWidth());
            //cut width
            int x = (rez.getWidth() - width) / 2;
            g2.drawImage(image.getScaledInstance(width, rez.getHeight(), Image.SCALE_SMOOTH), x, 0, null);
        } else {
            //resize width
            scaleFactor = getScaling(image.getWidth(), rez.getWidth());
            int height = (int) (scaleFactor * image.getHeight());
            //cut height
            int y = (rez.getHeight() - height) / 2;
            g2.drawImage(image.getScaledInstance(rez.getWidth(), height, Image.SCALE_SMOOTH), 0, y, null);
        }
        g2.dispose();
    }

    /**
     * resize input image to new dimension(only smaller) into rez parameter
     *
     * @param image input image for scaling
     * @param rez   resulting image. must have required width and height
     */
    private static void getScaledImageDimension(BufferedImage image, int new_width, int new_height, OutputStream rez)
            throws IOException {
        BufferedImage dst = new BufferedImage(new_width, new_height, BufferedImage.TYPE_3BYTE_BGR);
        getScaledImageDimension(image, dst);
        writeImage(dst, 1f, rez);
    }

    /**
     * resize input image to new dimension(only smaller) into rez parameter
     * @param src_file input image for scaling
     * @param rez   resulting image. must have required width and height
     */
    public static void getScaledImageDimension(File src_file, int new_width, int new_height, OutputStream rez)
            throws IOException {
        BufferedImage src = ImageIO.read(src_file);
        getScaledImageDimension(src, new_width, new_height, rez);
    }

    public static ImageWriter getImageWriter() {
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
        if (iter.hasNext()) {
            return iter.next();
        } else {
            return null;
        }
    }

    public static ImageReader getImageReader(ImageInputStream is) {
        Iterator<ImageReader> iterator = ImageIO.getImageReaders(is);
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public static void writeImage(BufferedImage image, float quality, OutputStream out)
            throws IOException {
        ImageWriter writer = getImageWriter();
        if (writer == null) {
            return;
        }
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);
        ImageWriteParam param = writer.getDefaultWriteParam();
        //JPEGImageWriteParam param = new JPEGImageWriteParam(Locale.getDefault());
        //param.setOptimizeHuffmanTables(true);
        if (quality >= 0f) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            //param.setCompressionMode(ImageWriteParam.MODE_COPY_FROM_METADATA);
            //param.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
            //param.setTilingMode(ImageWriteParam.MODE_DISABLED);
        }
        writer.write(null, new IIOImage(image, null, null), param);
    }

    public static void writeImage(BufferedImage image, float quality, File out)
            throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(out);
            writeImage(image, quality, fos);
        } catch (IOException e) {
            throw new IOException("Failed to write image into file.", e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * closes iis
     *
     * @return image from given stream or null if no readers found
     */
    public static BufferedImageHolder readImage(ImageInputStream iis)
            throws IOException {
        ImageReader reader = getImageReader(iis);
        if (reader == null) {
            iis.close();
            return null;
        }
        reader.setInput(iis, true, true);
        ImageReadParam param = reader.getDefaultReadParam();
        BufferedImage bi;
        try {
            bi = reader.read(0, param);
        } finally {
            reader.dispose();
            iis.close();
        }
        return new BufferedImageHolder(bi, reader.getFormatName());
    }

    /**
     * @return image from given stream or null if no readers found
     */
    public static BufferedImageHolder readImage(File in)
            throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        BufferedImageHolder bih = readImage(iis);
        if (bih != null) {
            bih.setFile(in);
        }
        return bih;
    }

    /**
     * @return image from given stream or null if no readers found
     */
    public static BufferedImageHolder readImage(MultipartFile in)
            throws IOException {
        InputStream is = in.getInputStream();
        ImageInputStream iis = ImageIO.createImageInputStream(is);
        BufferedImageHolder bih = readImage(iis);
        is.close();
        if (bih != null) {
            bih.setMultipart(in);
        }
        return bih;
    }

    /**
     * @param src images to draw, they must be resized to an appropriate size
     * @param dst image where given images will be drawn
     */
    public static void draw4on1(BufferedImage[] src, BufferedImage dst) {
        Graphics2D g2 = dst.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, dst.getWidth(), dst.getHeight());
        int dxi;
        int dyi = 0;

        int x0 = dst.getWidth() - 5;
        int y0 = 5;
        int x = x0;
        int y = y0;
        for (int i = 0; i < src.length; i++) {
            if (i % 2 == 0) {
                dxi = -10;
            } else {
                dxi = 0;
            }
            //g2.draw3DRect(dx - 1 , dy-tmp_bi.getHeight() - 1, tmp_bi.getWidth() + 1 , tmp_bi.getHeight() + 1, true);
            g2.drawImage(src[i], x - src[i].getWidth() + dxi, y + dyi, null);
            g2.drawString("#" + i, x - src[i].getWidth() + dxi, y + dyi + 20);
            //g2.rotate(Math.toRadians(4));
            y = y + src[i].getHeight() / 2;
            if (y > dst.getHeight() - src[i].getHeight()) {
                y = y0;
                if (dyi == 0) {
                    dyi = 10;
                } else {
                    dyi = 0;
                }
                if (x < src[i].getWidth()) {
                    x = dst.getWidth();
                }
                x = x - src[i].getWidth() / 2;
            }
        }
        g2.setColor(Color.gray);
        g2.drawRect(0, 0, dst.getWidth() - 1, dst.getHeight() - 1);
        g2.dispose();
    }

}
