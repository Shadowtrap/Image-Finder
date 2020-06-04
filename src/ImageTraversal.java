import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageTraversal implements Runnable{

    private Thread thread1, thread2, thread3, thread4;
    private BufferedImage largerImage;
    private BufferedImage smallerImage;
    private int[][] smallerImageRGBs;
    private int[][] largerImageRGBs;
    private ArrayList<BufferedImage> subImages;
    private ArrayList<Integer> subImagesX;
    private ArrayList<Integer> subImagesY;
    private ArrayList<Integer> resultX;
    private ArrayList<Integer> resultY;
    private int smallArea;

    public ImageTraversal(BufferedImage image1, BufferedImage image2){
        if(image1.getHeight() * image1.getWidth() <= image2.getHeight() * image2.getWidth()){
            smallerImage = image1;
            largerImage = image2;
        }
        else{
            smallerImage = image2;
            largerImage = image1;
        }

        smallArea = smallerImage.getWidth() * smallerImage.getHeight();

        smallerImageRGBs = setImageRGBs(smallerImage);
        largerImageRGBs = setImageRGBs(largerImage);

        subImages = new ArrayList();
        subImagesX = new ArrayList();
        subImagesY = new ArrayList();

        resultX = new ArrayList();
        resultY = new ArrayList();
    }

    private int[][] setImageRGBs(BufferedImage bI) {
        int[][] bIRGBs = new int[bI.getHeight()][bI.getWidth()];
        for (int i = 0; i < bIRGBs.length; i++) {
            for (int j = 0; j < bIRGBs[0].length; j++) {
                bIRGBs[i][j] = bI.getRGB(j, i);
            }
        }
        return bIRGBs;
    }

    private void setSubImages() {
        int diffWidth = largerImage.getWidth() - smallerImage.getWidth();
        int diffHeight = largerImage.getHeight() - smallerImage.getHeight();
        if(diffWidth == 0){
            diffWidth += 1;
        }
        if(diffHeight == 0){
            diffHeight += 1;
        }
        for(int i = 0; i < diffWidth; i++) {
            for (int j = 0; j < diffHeight; j++) {
                subImages.add(largerImage.getSubimage(i, j, smallerImage.getWidth(), smallerImage.getHeight()));
                subImagesX.add(i);
                subImagesY.add(j);
            }
        }
    }

    private int equalRGBsCount(BufferedImage bI){
        int output = 0;
        for(int i = 0; i < smallerImageRGBs.length; i++){
            for(int j = 0; j < smallerImageRGBs[0].length; j++){
                if(smallerImageRGBs[i][j] == setImageRGBs(bI)[i][j]){
                    output++;
                }
            }
        }
        return output;
    }

    private void compareSmallToSub1(){
        for(int i = 0; i < (int)(subImages.size()*0.25); i++){
            if(equalRGBsCount(subImages.get(i)) >= smallArea * 0.95) {
                resultX.add(subImagesX.get(i));
                resultY.add(subImagesX.get(i));
                System.out.println("(" + subImagesX.get(i) + "," + subImagesY.get(i) + ")");
            }
            System.out.println("SubImage# " + i);
        }
    }

    private void compareSmallToSub2(){
        for(int i = (int)(subImages.size()*0.25); i < (int)(subImages.size()*0.5); i++){
            if(equalRGBsCount(subImages.get(i)) >= smallArea * 0.95) {
                resultX.add(subImagesX.get(i));
                resultY.add(subImagesX.get(i));
                System.out.println("(" + subImagesX.get(i) + "," + subImagesY.get(i) + ")");
            }
            System.out.println("SubImage# " + i);
        }
    }

    private void compareSmallToSub3(){
        for(int i = (int)(subImages.size()*0.5); i < (int)(subImages.size()*0.75); i++){
            if(equalRGBsCount(subImages.get(i)) >= smallArea * 0.95) {
                resultX.add(subImagesX.get(i));
                resultY.add(subImagesX.get(i));
                System.out.println("(" + subImagesX.get(i) + "," + subImagesY.get(i) + ")");
            }
            System.out.println("SubImage# " + i);
        }
    }

    private void compareSmallToSub4(){
        for(int i = (int)(subImages.size()*0.75); i < subImages.size(); i++){
            if(equalRGBsCount(subImages.get(i)) >= smallArea * 0.95) {
                resultX.add(subImagesX.get(i));
                resultY.add(subImagesX.get(i));
                System.out.println("(" + subImagesX.get(i) + "," + subImagesY.get(i) + ")");
            }
            System.out.println("SubImage# " + i);
        }
    }

    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        if(currentThreadName == "one"){
            compareSmallToSub1();
        }
        else if(Thread.currentThread().getName() == "two"){
            compareSmallToSub2();
        }
        else if(currentThreadName == "three"){
            compareSmallToSub3();
        }
        else if(currentThreadName == "four"){
            compareSmallToSub4();
        }
    }

    public void doTask(){
        thread1 = new Thread(this, "one");
        thread2 = new Thread(this, "two");
        thread3 = new Thread(this, "three");
        thread4 = new Thread(this, "four");
        setSubImages();
        System.out.println(subImages.size());
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }

    public static void main(String[] args) throws IOException {
        BufferedImage image1 = ImageIO.read(new File("src\\Images\\village.png"));
        BufferedImage image2 = ImageIO.read(new File("src\\Images\\villageTest.png"));
        ImageTraversal test1 = new ImageTraversal(image1, image2);
        test1.doTask();
    }

}
