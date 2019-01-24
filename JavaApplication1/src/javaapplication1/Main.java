/*
    
    Author: Jakub Gurgacz

 * This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package javaapplication1;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Main class, this is where fun begins
 *
 * @author Jakub Gurgacz
 */
public class Main {

    public static ArrayList<Pixel> pixelsList;
    public static ArrayList<ClusterCenter> clusterList;
    public static BufferedImage img;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        File imageBefore = new File("zdj.jpg");
        long sizeBefore = imageBefore.length();

        int numberOfClusters = 16;
        //TODO: clean code
        //TODO: write comments
        pixelsList = new ArrayList<>();

        readPixels();

        clusterList = cluster(numberOfClusters);
        System.out.println("Klastry");
        clusterList.forEach(c -> {
            System.out.println("(" + c.getA() + ", " + c.getX() + ", " + c.getY() 
                    + ", " + c.getZ() + ")");
        });

        while (true) {
            boolean added = addPixelToCluster();
            if (!isClusterEmpty() && added) {
                break;
            } else {
                clusterList.clear();
                cluster(numberOfClusters);
            }
        }

        int i = 1;
        while (clusterNotSameAsBefore()) {
            img = changeValuesOnImage(img);
            String nazwa = "nowe" + String.valueOf(i++);
            try {
                ImageIO.write(img, "jpg", new File(nazwa + ".jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            optimizeClusters();
            addPixelToCluster();

        }

        img = changeValuesOnImage(img);
        try {
            ImageIO.write(img, "jpg", new File("final.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        File imageAfter = new File("final.jpg");
        long sizeAfter = imageAfter.length();
        System.out.println("Stary rozmiar: " + sizeBefore + " bajtów\nNowy rozmiar: " 
                + sizeAfter + " bajtów\nW ilu % skompresowano: " 
                + String.valueOf(100 - (100 * sizeAfter) / sizeBefore) + "%");
    }

    /**
     * Optimizes clusters so they are closer to their pixels concentration.
     */
    public static void optimizeClusters() {
        for (int j = 0; j < clusterList.size(); j++) {
            int count = 0, x = 0, y = 0, z = 0;
            ClusterCenter cc = clusterList.get(j);
            for (int i = 0; i < pixelsList.size(); i++) {
                Pixel pixel = pixelsList.get(i);
                if (pixel.getCluster().equals(cc)) {
                    count++;
                    x += pixel.getRed();
                    y += pixel.getGreen();
                    z += pixel.getBlue();
                }

            }
            try {
                x = (int) x / count;
                y = (int) y / count;
                z = (int) z / count;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            cc.setValues(x, y, z);
        }
    }

    /**
     * Returns image with changed pixel values.
     *
     * @param img image on which we want to apply changes
     * @return image with changed pixel values
     */
    public static BufferedImage changeValuesOnImage(BufferedImage img) {
        pixelsList.forEach(p -> {
            int newPixelValue = (p.getCluster().getA() << 24) | (p.getCluster().getX() << 16) 
                    | (p.getCluster().getY() << 8) | p.getCluster().getZ();
            img.setRGB(p.getX(), p.getY(), newPixelValue);
        });
        return img;
    }

    /**
     * Adds pixel to nearest cluster
     */
    public static boolean addPixelToCluster() {
        //TODO: set to maximum+1
        try {
            double minDistanceToCluster = 0;
            ClusterCenter bestClusterForPixel = null;
            for (int i = 0; i < pixelsList.size(); i++) {
                Pixel pixel = pixelsList.get(i);
                minDistanceToCluster = distaneBetweenPixels(pixel, clusterList.get(0));
                bestClusterForPixel = clusterList.get(0);
                for (int j = 1; j < clusterList.size(); j++) {
                    ClusterCenter cluster = clusterList.get(j);

                    double distanceBetweenTwo = distaneBetweenPixels(pixel, clusterList.get(j));
                    if (distanceBetweenTwo < minDistanceToCluster) {
                        bestClusterForPixel = cluster;
                        minDistanceToCluster = distanceBetweenTwo;
                    }
                }
                pixel.setCluster(bestClusterForPixel);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            return false;
        }
        System.out.println("Klastry ustawione");
        return true;
    }

    /**
     * Returns distance between cluster and pixel
     *
     * @param pixel given pixel
     * @param cluster given cluster
     * @return distance between cluster and pixel
     */
    public static double distaneBetweenPixels(Pixel pixel, ClusterCenter cluster) {
        double differenceRed = pixel.getRed() - cluster.getX();
        double differenceGreen = pixel.getGreen() - cluster.getY();
        double differenceBlue = pixel.getBlue() - cluster.getZ();
        return Math.sqrt(Math.pow(differenceRed, 2) + Math.pow(differenceGreen, 2) + Math.pow(differenceBlue, 2));
    }

    /**
     * The function creates x clusters so that it takes the drawn pixel and
     * copies its values to the new cluster. This protects against the drawing
     * of a cluster that would be too far away from clusters.
     *
     * @param numberOfClusters number of generated clusters
     * @return list of clusters
     */
    public static ArrayList cluster(int numberOfClusters) {
        System.out.println("Klastruje");
        Random random = new Random();
        ArrayList<ClusterCenter> clusterListTemp = new ArrayList<>();
        for (int i = 0; i < numberOfClusters; i++) {
            Pixel p = null;
            int randomized = random.nextInt(pixelsList.size() + 1);
            p = pixelsList.get(randomized);
            if (clusterListTemp.isEmpty()) {
                clusterListTemp.add(new ClusterCenter(p.getAlpha(), p.getRed(), p.getGreen(), p.getBlue()));
                continue;
            }
            ClusterCenter cc = new ClusterCenter(p.getAlpha(), p.getRed(), p.getGreen(), p.getBlue());
            if (!clusterListTemp.contains(cc)) {
                clusterListTemp.add(cc);
            } else {
                numberOfClusters++;
            }
        }
        return clusterListTemp;
    }

    public static int size() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("zdj.jpg"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
                //Writes to this byte array output stream
                byteArrayOutputStream.write(buf, 0, readNum);
                //System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes.length;
    }

    /**
     * Function get color from pixel and creates new Pixel object with given
     * parameters.
     *
     * @param pixelColor color of a pixel
     * @param x x coordinate
     * @param y y coordinate
     */
    public static void createPixel(int pixelColor, int x, int y) {
        int alpha = (pixelColor >> 24) & 0xff;
        int red = (pixelColor >> 16) & 0xff;
        int green = (pixelColor >> 8) & 0xff;
        int blue = (pixelColor) & 0xff;
        //System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
        pixelsList.add(new Pixel(alpha, red, green, blue, x, y));
    }

    /**
     * Reads pixels data from image
     */
    public static void readPixels() {
        img = null;
        try {
            img = ImageIO.read(new File("zdj.jpg"));
            int width = img.getWidth();
            int height = img.getHeight();
            System.out.println("Rozdzielczość: " + height + "x" + width 
                    + " , rozmiar: " + size() + " bajtów");
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = img.getRGB(j, i);
                    createPixel(pixel, j, i);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Checks if cluster is empty, which means that there are no pixels assigned
     * to any cluster
     *
     * @return true - if any cluster is empty, false - if no
     */
    private static boolean isClusterEmpty() {
        for (int i = 0; i < clusterList.size(); i++) {
            ClusterCenter cc = clusterList.get(i);
            int counter = 0, x = 0, y = 0, z = 0;
            for (int j = 0; j < pixelsList.size(); j++) {
                Pixel p = pixelsList.get(j);
                if (p.getCluster().equals(cc)) {
                    counter++;
                }
            }
            System.out.println("Klaster " + i + " ma " + counter + " pixeli");
            if (counter == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there was any changes in cluster rgb values after cluster
     * optimization
     *
     * @return true - if cluster is not the same as before, false - if there was
     * no changes
     */
    private static boolean clusterNotSameAsBefore() {
        boolean result[] = {false};
        clusterList.forEach(c -> {
            if (c.getX() != c.getOldX() || c.getY() != c.getOldY() || c.getZ() != c.getOldZ()) {
                result[0] = true;
                return;
            }
        });
        return result[0];
    }

}
