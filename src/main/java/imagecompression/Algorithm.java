package imagecompression;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Algorithm {

    private List<Pixel> pixelsList = new ArrayList<>();
    private List<ClusterCenter> clusterList = new ArrayList<>();
    private BufferedImage img;

    private final String photoPath;
    private final String outputPath;
    private final int clusters;
    private final boolean shouldSavePasses;

    public Algorithm(String photoPath, String outputPath, int clusters, boolean shouldSavePasses) {
        this.photoPath = photoPath;
        this.outputPath = outputPath;
        this.clusters = clusters;
        this.shouldSavePasses = shouldSavePasses;
    }

    public void run(){

        readPixels();

        while (true) {
            createClusters();
            addPixelToCluster();
            if (!isAnyClusterEmpty()) {
                break;
            }
            clusterList.clear();
        }

        int passes = 1;
        while (clusterNotSameAsBefore()) {
            System.out.println(passes + " pass");
            optimizeClusters();
            addPixelToCluster();

            if (shouldSavePasses) {
                changeValuesOnImage();
                String name = "pass" + passes;
                String separator = System.getProperty("file.separator");
                int dirIndex = outputPath.lastIndexOf(separator);
                String dirPath = outputPath.substring(0, dirIndex+1);
                System.out.println("dirPath = " + dirPath);
                try {
                    ImageIO.write(img, "jpg", new File(dirPath + name + ".jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            passes++;
        }

        changeValuesOnImage();
        try {
            ImageIO.write(img, "jpg", new File(outputPath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void optimizeClusters() {
        for (int j = 0; j < getClusterList().size(); j++) {
            int count = 0, red = 0, green = 0, blue = 0;
            ClusterCenter cc = getClusterList().get(j);
            for (int i = 0; i < getPixelsList().size(); i++) {
                Pixel pixel = getPixelsList().get(i);
                if (pixel.getCluster().equals(cc)) {
                    count++;
                    red += pixel.getRed();
                    green += pixel.getGreen();
                    blue += pixel.getBlue();
                }

            }
            try {
                red = (int) red / count;
                green = (int) green / count;
                blue = (int) blue / count;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            cc.setValues(red, green, blue);
        }
    }

    public void changeValuesOnImage() {
        for(int i = 0; i < getPixelsList().size(); i++){
            Pixel pixel = getPixelsList().get(i);
            ClusterCenter cc = pixel.getCluster();
            int newPixelValue = (cc.getAlpha() << 24) | (cc.getRed() << 16) | (cc.getGreen() << 8) | cc.getBlue();
            img.setRGB(pixel.getX(), pixel.getY(), newPixelValue);
        }
    }

    public boolean addPixelToCluster() {
        //TODO: set to maximum+1

        double minDistanceToCluster = 0;
        ClusterCenter bestClusterForPixel = null;
        for (int i = 0; i < getPixelsList().size(); i++) {
            Pixel pixel = getPixelsList().get(i);
            try {
                minDistanceToCluster = distaneBetweenPixels(pixel, getClusterList().get(0));
                bestClusterForPixel = getClusterList().get(0);
                for (int j = 1; j < getClusterList().size(); j++) {
                    ClusterCenter cluster = getClusterList().get(j);

                    double distanceBetweenTwo = distaneBetweenPixels(pixel, getClusterList().get(j));
                    if (distanceBetweenTwo < minDistanceToCluster) {
                        bestClusterForPixel = cluster;
                        minDistanceToCluster = distanceBetweenTwo;
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
                return false;
            }
            pixel.setCluster(bestClusterForPixel);
        }

        return true;
    }

    public double distaneBetweenPixels(Pixel pixel, ClusterCenter cluster) {
        double differenceRed = pixel.getRed() - cluster.getRed();
        double differenceGreen = pixel.getGreen() - cluster.getGreen();
        double differenceBlue = pixel.getBlue() - cluster.getBlue();
        return Math.sqrt(Math.pow(differenceRed, 2) + Math.pow(differenceGreen, 2) + Math.pow(differenceBlue, 2));
    }


    public void createClusters() {
        System.out.println("Creating clusters");
        for (int i = 0; i < clusters; i++) {
            clusterList.add(createClusterCenterFromPixel());
        }
    }

    private ClusterCenter createClusterCenterFromPixel() {
        Random random = new Random();
        int randomPixelNumber = random.nextInt(getPixelsList().size() + 1);
        Pixel p = getPixelsList().get(randomPixelNumber);
        ClusterCenter cc = new ClusterCenter(p.getAlpha(), p.getRed(), p.getGreen(), p.getBlue());
        if (!clusterList.contains(cc)) {
            return cc;
        } else {
            return createClusterCenterFromPixel();
        }
    }

    public int size() throws FileNotFoundException {
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

    public void createPixel(int pixelColor, int x, int y) {
        int alpha = (pixelColor >> 24) & 0xff;
        int red = (pixelColor >> 16) & 0xff;
        int green = (pixelColor >> 8) & 0xff;
        int blue = (pixelColor) & 0xff;
        //System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
        getPixelsList().add(new Pixel(alpha, red, green, blue, x, y));
    }

    public void readPixels() {
        try {
            setImg(ImageIO.read(new File(photoPath)));
            int width = getImg().getWidth();
            int height = getImg().getHeight();
            System.out.println("Image resolution: " + height + "x" + width);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = getImg().getRGB(x, y);
                    createPixel(pixel, x, y);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isAnyClusterEmpty() {
        for (int i = 0; i < getClusterList().size(); i++) {
            ClusterCenter cc = getClusterList().get(i);
            int counter = 0, x = 0, y = 0, z = 0;
            for (int j = 0; j < getPixelsList().size(); j++) {
                Pixel p = getPixelsList().get(j);
                if (p.getCluster().equals(cc)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean clusterNotSameAsBefore() {
        for (int i = 0; i < getClusterList().size(); i++){
            ClusterCenter cc = getClusterList().get(i);
            if (cc.getRed() != cc.getOldRed() || cc.getGreen() != cc.getOldGreen() || cc.getBlue() != cc.getOldBlue()) {
                return true;
            }
        }
        return false;
    }

    private List<Pixel> getPixelsList() {
        return pixelsList;
    }

    private void setPixelsList(ArrayList<Pixel> pixelsList) {
        this.pixelsList = pixelsList;
    }

    private List<ClusterCenter> getClusterList() {
        return clusterList;
    }

    private void setClusterList(ArrayList<ClusterCenter> clusterList) {
        this.clusterList = clusterList;
    }

    private BufferedImage getImg() {
        return img;
    }

    private void setImg(BufferedImage img) {
        this.img = img;
    }
}
