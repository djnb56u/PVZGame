package MainGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class imageSplit {
    public void imageSplit(int gradeNum,String path) {

        // 读入大图
        File file = new File(path+"a1.jpg");
        int rows = gradeNum;
        int cols = gradeNum;
        int chunks = rows * cols;
        try(FileInputStream fis = new FileInputStream(file);
        ){
            // 分割成3*3(9)个小图
            System.out.println(file.exists());
            BufferedImage image = ImageIO.read(fis);
            fis.close();
            // 计算每个小图的宽度和高度
            int chunkWidth = image.getWidth() / cols;
            int chunkHeight = image.getHeight() / rows;
            System.out.println("图片的宽度为:" + chunkWidth * rows + "图片的高度为:" + chunkHeight * cols);
            //大图中的一部分
            int count = 0;
            BufferedImage imgs[] = new BufferedImage[chunks];
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    //设置小图的大小和类型
                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
//					//写入图像内容
                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
//					System.out.println("源矩阵第一个角的坐标" + chunkWidth * y + "+" + chunkHeight * x + "源矩阵第二个角的坐标" + chunkWidth * (y + 1) + "+" + chunkHeight * (x + 1));
                    gr.dispose();
                }
            }
            File newPath = new File(path+"test"+gradeNum);
            if (!newPath.exists())
            {
                //如果目录不存在 则创建目录
                newPath.mkdirs();
                System.out.println("make dirs!");
            }
            // 输出小图
            for (int i = 0; i < imgs.length-1; i++) {
                //ImageIO.write(imgs[i], "jpg", new File("C:\\img\\split\\img" + i + ".jpg"));
                String picFileName = path+"test"+gradeNum+"/a1_0" + (i+1)+ ".jpg";
                FileOutputStream fileOutput = new FileOutputStream(picFileName);
                ImageIO.write(imgs[i], "jpg", fileOutput);
                fileOutput.close();
                System.out.println((i+1));
            }
            System.out.println("完成分割！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
