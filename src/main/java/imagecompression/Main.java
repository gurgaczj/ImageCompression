package imagecompression;

import java.io.File;

/**
 * Main class, this is where fun begins
 *
 * @author Jakub Gurgacz
 */
public class Main {
    public static void main(String[] args) {

        String photoPath;
        String outputPath;

        try {
            photoPath = args[0];
            outputPath = args[1];
        } catch (NullPointerException | IndexOutOfBoundsException e){
            System.out.println("You need to provide at least path to original image and output path.");
            return;
        }

        int clusters = 16;
        boolean shouldSavePasses = false;
        try {
            clusters = Integer.parseInt(args[2]);
            shouldSavePasses = Boolean.parseBoolean(args[3]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e){
            // do nothing
        } catch (NumberFormatException e){
            System.out.println("Number of clusters must be an integer.");
            return;
        }

        if (clusters < 4) {
            System.out.println("Number of clusters must be equal to 2 or greater.");
            return;
        }

        File originalImage = new File(photoPath);
        if (!originalImage.exists() && !originalImage.isFile()){
            System.out.println("Path do not point to the original image");
            return;
        }

        Algorithm algorithm = new Algorithm(photoPath, outputPath, clusters, shouldSavePasses);
        algorithm.run();

        System.out.println("Done");
    }
}
