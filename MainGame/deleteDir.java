package MainGame;

import java.io.File;

public class deleteDir {
    public static void deleteDir(String rmpath) {
        File file = new File(rmpath);
        if (!file.exists()) {//判断是否待删除目录是否存在
            System.err.println("The dir are not exists!");
            return;
        }

        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for (String name : content) {
            File temp = new File(rmpath, name);
            if (temp.isDirectory()) {//判断是否是目录
                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
                System.out.println("已删除" + temp);
            } else {
                if (!temp.delete()) {//直接删除文件

                    System.err.println("Failed to delete " + name);
                }
            }
        }
    }
}
