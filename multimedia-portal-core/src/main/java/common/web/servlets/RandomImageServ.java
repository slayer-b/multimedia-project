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

package common.web.servlets;

import common.web.filters.Antispam;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author dima
 */
public class RandomImageServ extends HttpServlet {
    private static final long serialVersionUID = 14099686185952206L;
    //default parameter values
    private static final String IMAGE_DIR_DEFAULT = "WEB-INF/CodeImages";
    private static final int DIGITS_COUNT_DEFAULT = 4;//how many digits will be draw in antibotcode

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext application = getServletConfig().getServletContext();

        File dir = new File(application.getRealPath(IMAGE_DIR_DEFAULT));//directory with images for anti-spam code

        if (dir.isDirectory()) {
            Random random = new Random();
            File[] fileListPattern = dir.listFiles(ONLY_JPEG_IMAGES_FILTER);
            int[] digitInCode = new int[DIGITS_COUNT_DEFAULT];//result array of indexes for file list

            //filling of result array for file list (shuffle of original file list)
            for (int i = 0; i < DIGITS_COUNT_DEFAULT; i++) {
                digitInCode[i] = random.nextInt(fileListPattern.length);
            }

            OutputStream os = response.getOutputStream();

            BufferedImage digitImage = ImageIO.read(fileListPattern[0]);

            BufferedImage antiSpamCodeImage = new BufferedImage(digitImage.getWidth() * DIGITS_COUNT_DEFAULT, digitImage.getHeight(), 4);
            Graphics g = antiSpamCodeImage.getGraphics();

            int tempWidth = 0;// X axial displacement

            //loading and drawing of images for anti-spam code BEGIN
            for (int aDigitInCode : digitInCode) {
                digitImage = ImageIO.read(fileListPattern[aDigitInCode]);
                g.drawImage(digitImage, tempWidth, 0, null);
                tempWidth += digitImage.getWidth();
            }
            //loading and drawing of images for anti-spam code END

            //forming a string representation of graphical anti-spam code BEGIN
            StringBuilder resultCode = new StringBuilder(8);
            for (int aDigitInCode : digitInCode) {
                String fileName = fileListPattern[aDigitInCode].getName();
                resultCode.append(fileName.substring(0, fileName.indexOf('_')));
            }

            Antispam.setCode(request, resultCode.toString());

            //transform BufferedImage to byte[] BEGIN
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(antiSpamCodeImage, "jpg", baos);
            byte[] rez = baos.toByteArray();
            //transform BufferedImage to byte[] END

            os.write(rez, 0, rez.length);
            os.flush();
            os.close();
        }
    }

    private static final FilenameFilter ONLY_JPEG_IMAGES_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return (name.endsWith("jpg"));
        }
    };

} 
