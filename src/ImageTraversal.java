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
    private ArrayList subImages;

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
        setSmallerImageRGBs();
        largerImageRGBs = new int[largerImage.getHeight()][largerImage.getWidth()];
        setLargerImageRGBs();
        addSubImages();
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

    private void addSubImages() throws RasterFormatException {
        subImages = new ArrayList();
        int width = smallerImage.getWidth();
        int height = smallerImage.getHeight();
        for(int i = 0; i < largerImage.getWidth() - width; i++) {
            for (int j = 0; j < largerImage.getHeight() - height; j++) {
                subImages.add(largerImage.getSubimage(i, j, width, height));
            }
        }
    }

    public ArrayList<BufferedImage> getSubImages(){
        return subImages;
    }

    public static void main(String[] args) throws IOException {
        BufferedImage tree = ImageIO.read(new File("src\\Images\\tree.png"));
        BufferedImage village = ImageIO.read(new File("src\\Images\\village.png"));
        ImageTraversal test1 = new ImageTraversal(tree, village);
        System.out.println(test1.getSubImages().size());
    }


}
