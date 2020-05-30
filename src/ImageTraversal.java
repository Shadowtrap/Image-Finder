import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageTraversal {

    private BufferedImage largerImage;
    private BufferedImage smallerImage;
    private int[][] smallerImageRGBs;
    private int[][] largerImageRGBs;
    private ArrayList<BufferedImage> subImages;
    private ArrayList<Integer> subImagesX;
    private ArrayList<Integer> subImagesY;
    private ArrayList<Integer> resultX;
    private ArrayList<Integer> resultY;

    public ImageTraversal(BufferedImage image1, BufferedImage image2){
        if(image1.getHeight() * image1.getWidth() <= image2.getHeight() * image2.getWidth()){
            smallerImage = image1;
            largerImage = image2;
        }
        else{
            smallerImage = image2;
            largerImage = image1;
        }
        smallerImageRGBs = new int[smallerImage.getHeight()][smallerImage.getWidth()];
        largerImageRGBs = new int[largerImage.getHeight()][largerImage.getWidth()];
    }

    private void setSmallerImageRGBs(){
        for(int i = 0; i < smallerImage.getHeight(); i++) {
            for (int j = 0; j < smallerImage.getWidth(); j++) {
                smallerImageRGBs[i][j] = smallerImage.getRGB(j, i);
            }
        }
    }

    private void setLargerImageRGBs(){
        for(int i = 0; i < largerImage.getHeight(); i++){
            for(int j = 0; j < largerImage.getWidth(); j++){
                largerImageRGBs[i][j] = largerImage.getRGB(j, i);
            }
        }
    }

    public int[][] getSmallerRGBs(){
        return smallerImageRGBs;
    }

    public int[][] getLargerRGBs(){
        return largerImageRGBs;
    }

    public void print2DArray(int array[][]){
        for(int[] i: array){
            for(int j : i){
                System.out.print(j + ",");
            }
            System.out.println("");
        }
    }

    private void setSubImages() throws RasterFormatException {
        subImages = new ArrayList();
        subImagesX = new ArrayList();
        subImagesY = new ArrayList();
        int width = smallerImage.getWidth();
        int height = smallerImage.getHeight();
        for(int i = 0; i < largerImage.getWidth() - width; i++) {
            for (int j = 0; j < largerImage.getHeight() - height; j++) {
                subImages.add(largerImage.getSubimage(i, j, width, height));
                subImagesX.add(i);
                subImagesY.add(j);
            }
        }
    }

    public ArrayList<BufferedImage> getSubImages(){
        return subImages;
    }

    private int[][] getImageRGBs(BufferedImage bI){
        int[][] bIRGBs = new int[bI.getHeight()][bI.getWidth()];
        for(int i = 0; i < bI.getHeight(); i++) {
            for (int j = 0; j < bI.getWidth(); j++) {
                bIRGBs[i][j] = bI.getRGB(j, i);
            }
        }
        return bIRGBs;
    }

    private boolean[][] compareRGBs(BufferedImage bI){
        boolean[][] comparisons = new boolean[smallerImage.getHeight()][smallerImage.getWidth()];
        for(int i = 0; i < smallerImageRGBs.length; i++){
            for(int j = 0; j < smallerImageRGBs[0].length; j++){
                comparisons[i][j] = smallerImageRGBs[i][j] == getImageRGBs(bI)[i][j];
            }
        }
        return comparisons;
    }

    private int numberOfTrue(boolean[][] RGBComparisons){
        int output = 0;
        for(boolean[] i: RGBComparisons){
            for(boolean j : i){
                if(j){
                    output++;
                }
            }
        }
        return output;
    }

    private void compareSmallToSub(){
        resultX = new ArrayList();
        resultY = new ArrayList();
        for(int i = 0; i < subImages.size(); i++){
            boolean[][] currSubImageComparison = compareRGBs(subImages.get(i));
            if(numberOfTrue(currSubImageComparison) == subImages.get(i).getWidth() + subImages.get(i).getHeight() - 100) {
                resultX.add(subImagesX.get(i));
                resultY.add(subImagesX.get(i));
                System.out.println("(" + subImagesX.get(i) + "," + subImagesY.get(i) + ")");
            }
        }
    }

    public void doTask(){
        setSmallerImageRGBs();
        setLargerImageRGBs();
        setSubImages();
        System.out.println("Starting Comparisons");
        compareSmallToSub();
        System.out.println(resultX.size());
    }

    public static void main(String[] args) throws IOException {
        BufferedImage tree = ImageIO.read(new File("src\\Images\\tree.png"));
        BufferedImage village = ImageIO.read(new File("src\\Images\\village.png"));
        ImageTraversal test1 = new ImageTraversal(tree, village);
        test1.doTask();
    }

}
