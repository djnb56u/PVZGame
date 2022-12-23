package MainGame;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //定义一个变量，记录当前图片展示路径
    String path = "image/girl/4/";

    private int gradeNum = 7;
    String rmpath = path + "test" + gradeNum;
    private JButton btnMusic = new JButton("音乐");
    private AudioClip sound = null;
    private AudioClip mvsound = null;


    //	//定义一个正确的数组判断赢
//	int[][] win = {
//			{1,2,3,4},
//			{5,6,7,8},
//			{9,10,11,12},
//			{13,14,15,0}
//	};
    private int x = 6, y = 6;

    //创建二维数组
    int[][] data = new int[gradeNum][gradeNum];
    //记录空白块在二维数组中的位置
    int[][] win = new int[gradeNum][gradeNum];
    //用来统计步数
    int step = 0;

    //创建选项下面的子选项、
    JMenu chooseGame = new JMenu("选择游戏");
    JMenuItem girl = new JMenuItem("动漫");
    JMenuItem volleyball = new JMenuItem("运动");

    JMenuItem replayItem = new JMenuItem("重新游戏");

    JMenu chooseGrade = new JMenu("选择难度");
    JMenuItem junior = new JMenuItem("初级");
    JMenuItem mid = new JMenuItem("中级");
    JMenuItem senior = new JMenuItem("高级");
    JMenuItem highsenior = new JMenuItem("高高级");
    JMenuItem extreme = new JMenuItem("变态级");

    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem accountItem = new JMenuItem("微信联系");
    JMenuItem AIItem = new JMenuItem("AI一键拼图");
    JMenuItem helpItem = new JMenuItem("使用帮助");

    imageSplit ps = new imageSplit();

    //游戏界面代码
    public GameJFrame() {
        //shift+alt+m 代码段封装成方法
        initJFrame();
        initJMenuBar();
        ps.imageSplit(gradeNum, path);
        //打乱数据实现随机图片
        initData();
        this.setFocusable(true);
        initImageIcon();
        deleteDir.deleteDir(rmpath);
        this.setVisible(true);
    }

    private void initData() {
        // TODO 自动生成的方法存根
        //1.定义一个一维数组
//				int[] tempArr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        //2.打乱数组中数据的顺序
        //遍历数组得到每一个元素，拿到每一个元素跟随机索引上的数据进行交换
//					for (int i = 0; i < tempArr.length; i++) {//获取到随机索引
//						int index = r.nextInt(tempArr.length);
//						//拿着遍历道德数据跟随机索引伤的数据进行交换
//						int temp = tempArr[i];
//						tempArr[i] = tempArr[index];
//						tempArr[index] = temp;
//
//
//					//3.给二位数组添加数据
//					for (int i = 0; i < tempArr.length; i++) {
//						if (tempArr[i] == 0) {
//							x = i / 4;
//							y = i % 4;
//						}
//						data[i / 4][i % 4] = tempArr[i];
//					}
//				}while(!test.isSolvable(data));

        //遍历二维数组
//				for(int i=0;i<data.length;i++) {
//					for(int j=0;j<data[i].length;j++) {
//						System.out.print(data[i][j]+" ");
//					}
//					System.out.println();
//				}
        //初始化二维数组numField
        data = new int[gradeNum][gradeNum];
        for (int i = 0; i < gradeNum; i++) {
            for (int j = 0; j < gradeNum; j++) {
                if (i == gradeNum - 1 && j == gradeNum - 1)
                    break;//最后一个不随机。
                //初始化数组，类似数组的计数。
                data[i][j] = i * gradeNum + j + 1;

                win[i][j] = data[i][j];
            }
            x = gradeNum - 1;
            y = gradeNum - 1;
            win[x][y] = 0;

        }

        //接下来要打乱。暂时用随机的算法，以后可以考虑用人工智能的办法。
        //这个随机算法是每一个和一个随机的位置进行交换。


        //目前测试了一下，这个算法的成功率不是很高，那就采取，不成功就重新随机的办法来弄。
        EightNum test = new EightNum(gradeNum);

        do {
            for (int i = 0; i < gradeNum; i++) {
                for (int j = 0; j < gradeNum; j++) {
                    if (i == gradeNum - 1 && j == gradeNum - 1)
                        break;//最后一个不随机。
                    //随机选中一个位置，和当前位置调换。
                    Random rand = new Random();
                    int x = rand.nextInt(gradeNum - 1);
                    int y = rand.nextInt(gradeNum - 1);
                    if (x == gradeNum - 1 && y == gradeNum - 1)
                        continue;
                    int temp = data[i][j];
                    data[i][j] = data[x][y];
                    data[x][y] = temp;
                }
            }
            //应该不会人品特别差 永远不出来吧hhh
        } while (!test.isSolvable(data));
        x = gradeNum - 1;
        y = gradeNum - 1;
        data[x][y] = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.printf("%d,", data[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    private void initImageIcon() {
        //清空已经出现的所有图片
        this.getContentPane().removeAll();
        if (victory()) {
            //显示胜利图片
            JLabel winjLabel = new JLabel(new ImageIcon("image/victory1.png"));
            winjLabel.setBounds(330, 300, 210, 210);
            this.getContentPane().add(winjLabel);
        }

        //定义一个变量，记录当前图片展示路径

        JLabel stepCount = new JLabel("步数:" + step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);
        btnMusic.setBounds(300, 20, 113, 27);
        this.getContentPane().add(btnMusic);
        btnMusic.addActionListener(this);


        //创建一个图片ImageIcon的对象
        for (int i = 0; i < gradeNum; i++) {
            for (int j = 0; j < gradeNum; j++) {
                int num = data[i][j];
                //创建一个JLable的对象（管理容器）
                JLabel jLabel = new JLabel(new ImageIcon(path + "test" + gradeNum + "/a1_0" + num + ".jpg"));
                //指定图片位置,宽高大小
                jLabel.setBounds((720 / gradeNum) * j + 80, (720 / gradeNum) * i + 50, 720 / gradeNum, 720 / gradeNum);
                //把容器添加到界面中
                //this.add(jLabel1);
                jLabel.setBorder(new BevelBorder(0));
                this.getContentPane().add(jLabel);
            }
        }

        //先加载的图片在上方，后加载的图片在下方
        JLabel backgroud = new JLabel(new ImageIcon("image/ce6wJ7Ht2pCG.jpg"));
        backgroud.setBounds(0, 0, 900, 920);
        this.getContentPane().add(backgroud);
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        //初始化菜单
        //创建整个的菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //创建菜单上面的两个选项的对象（功能、关于我们）
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu AIJMenu = new JMenu("AI");
        JMenu helpJMenu = new JMenu("帮助");

        //将每一个选项下的条目放到选项中
        chooseGame.add(girl);
        chooseGame.add(volleyball);

        chooseGrade.add(junior);
        chooseGrade.add(mid);
        chooseGrade.add(senior);
        chooseGrade.add(highsenior);
        chooseGrade.add(extreme);

        functionJMenu.add(chooseGame);
        functionJMenu.add(chooseGrade);

        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);
        AIJMenu.add(AIItem);
        helpJMenu.add(helpItem);

        //给条目绑定事件
        chooseGame.addActionListener(this);
        girl.addActionListener(this);
        volleyball.addActionListener(this);
        chooseGrade.addActionListener(this);
        junior.addActionListener(this);
        mid.addActionListener(this);
        senior.addActionListener(this);
        highsenior.addActionListener(this);
        extreme.addActionListener(this);

        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);

        accountItem.addActionListener(this);
        AIItem.addActionListener(this);
        helpItem.addActionListener(this);
        //将菜单里的3个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(AIJMenu);
        jMenuBar.add(helpJMenu);
        jMenuBar.add(aboutJMenu);
        //给整合界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        this.setSize(903, 980);
        this.setTitle("拼你个大头鬼 v1.0  ©jingchua");
        //设置界面置顶
        //this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //使用Javafx的
        URL urlSound = this.getClass().getResource("/Music/周杰伦-七里香.wav"); //java一般不支持MP3文件
        sound = Applet.newAudioClip(urlSound);
        Image icon = Toolkit.getDefaultToolkit().getImage("image/iconImage.png");
        this.setIconImage(icon);

        //取消默认的居中放置，只有取消了才能按照坐标轴的形式添加组件
        this.setLayout(null);
        this.addKeyListener(this);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
    }

    //判断data数组中的数据是否和win中相同
    //如果相同则返回true，否则false
    public boolean victory() {
//		for(int i=0;i<data.length;i++) {
//			//i:一次表示二维数组data里面的索引
//			//data[i]：依次表示每一个一维数组
//			for(int j=0;j<data[i].length;j++) {
//				if(data[i][j]!=win[i][j]) {
//					//只要一个数字不一样则返回false
//					return false;
//				}
//			}
//		}
        //循环结束表示遍历比较完毕，全部一样返回true
        //return true;

        for (int i = 0; i < gradeNum; i++) {
            for (int j = 0; j < gradeNum; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO 自动生成的方法存根
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (victory()) {
            return;
        }
        int code = e.getKeyCode();
        if (code == 65) {
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path + "a1.jpg"));
            all.setBounds(80, 50, 720, 720);
            this.getContentPane().add(all);
            JLabel backgroud = new JLabel(new ImageIcon("image/ce6wJ7Ht2pCG.jpg"));
            backgroud.setBounds(0, 0, 900, 880);
            this.getContentPane().add(backgroud);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //对游戏胜利进行判断，如果胜利，此方法需要直接结束，不能再执行下面的移动代码了
        if (victory()) {
            return;
        }
        //对上下左右进行判断
        //左上右下37 38 39 40
        int code = e.getKeyCode();
        URL urlSound = this.getClass().getResource("/Music/s_move.wav"); //java一般不支持MP3文件
        mvsound = Applet.newAudioClip(urlSound);
        mvsound.play();
        if (code == 37) {
            System.out.println("向左移动");

            if (y == 0)
                return;
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
            //调用方法按照最新的数字加载图片
            initImageIcon();

        } else if (code == 38) {
            System.out.println("向上移动");

            if (x == 0)
                return;
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            step++;
            //调用方法按照最新的数字加载图片
            initImageIcon();
        } else if (code == 39) {
            System.out.println("向右移动");
            if (y == gradeNum - 1)
                return;
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
            //调用方法按照最新的数字加载图片
            initImageIcon();

        } else if (code == 40) {
            System.out.println("向下移动");
            if (x == gradeNum - 1)
                return;
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;

            //调用方法按照最新的数字加载图片
            initImageIcon();
        } else if (code == 65) {
            initImageIcon();
        } else if (code == 87) {
            data = win;
            initImageIcon();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == replayItem) {
            //打乱二维数组
            initData();
            //计步器清零
            step = 0;
            //重新加载图片
            initImageIcon();

        } else if (obj == reLoginItem) {
            this.setVisible(false);
            new LoginJFrame();

        } else if (obj == closeItem) {
            System.exit(0);
        } else if (obj == junior) {
            gradeNum = 3;
            //计步器清零
            step = 0;
            initData();
            ps.imageSplit(gradeNum, path);
            initImageIcon();
            String rmpath = path + "test" + gradeNum;
            deleteDir.deleteDir(rmpath);

        } else if (obj == senior) {
            gradeNum = 5;
            //计步器清零
            step = 0;
            initData();
            ps.imageSplit(gradeNum, path);
            initImageIcon();
            String rmpath = path + "test" + gradeNum;
            deleteDir.deleteDir(rmpath);
        } else if (obj == highsenior) {
            gradeNum = 6;
            //计步器清零
            step = 0;
            initData();
            ps.imageSplit(gradeNum, path);
            initImageIcon();
            String rmpath = path + "test" + gradeNum;
            deleteDir.deleteDir(rmpath);
        } else if (obj == extreme) {
            gradeNum = 7;
            //计步器清零
            step = 0;
            initData();
            ps.imageSplit(gradeNum, path);
            initImageIcon();
            String rmpath = path + "test" + gradeNum;
            deleteDir.deleteDir(rmpath);
        } else if (obj == mid) {
            gradeNum = 4;
            //计步器清零
            step = 0;
            initData();
            ps.imageSplit(gradeNum, path);
            initImageIcon();
            String rmpath = path + "test" + gradeNum;
            deleteDir.deleteDir(rmpath);
        } else if (obj == girl) {
            //打乱二维数组
            initData();
            //计步器清零
            step = 0;
            //重新加载图片
            Random r = new Random();
            int r1 = r.nextInt(7) + 1; // 生成[1,3)区间的整数
            String path1 = "image/girl/" + r1 + "/";
            path = path1;
            path1 = "image/girl/" + r1 + "/";

            ps.imageSplit(gradeNum, path);
            initImageIcon();
            String rmpath = path + "test" + gradeNum;
            deleteDir.deleteDir(rmpath);
        } else if (obj == volleyball) {

            //打乱二维数组
            initData();
            //计步器清零
            step = 0;
            //重新加载图片
            Random r = new Random();
            int r1 = r.nextInt(6) + 1; // 生成[1,3)区间的整数
            String path1 = "image/sports/" + r1 + "/";
            path = path1;
            path1 = "image/sports/" + r1 + "/";
            ps.imageSplit(gradeNum, path);
            initImageIcon();
            String rmpath = path + "test" + gradeNum;
            deleteDir.deleteDir(rmpath);
        } else if (obj == accountItem) {
            //创建一个弹窗对象
            JDialog jDialog = new JDialog();
            //创建一个管理图片的容器对象JLabel
            JLabel jLabel = new JLabel(new ImageIcon("image/Account.jpg"));
            jLabel.setBounds(0, 0, 434, 602);
            //把图片添加到弹框中
            jDialog.getContentPane().add(jLabel);
            //给弹窗设置大小
            jDialog.setSize(460, 630);
            //弹窗设置置顶
            jDialog.setAlwaysOnTop(true);
            //让弹窗居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭无法操作下面的界面
            jDialog.setModal(true);
            //让弹框显示出来
            jDialog.setVisible(true);

        } else if (obj == helpItem) {
            JOptionPane jOptionPane = new JOptionPane();
            jOptionPane.showMessageDialog(null, "使用键盘上下左右移动空白块\t\n" +
                    "使用 a 键可以看到预览图\t\n" +
                    "使用 w 键可以一键作弊完成\t\n" +
                    "点击功能可自选难度和功能调试\t", "帮助", JOptionPane.PLAIN_MESSAGE);
            jOptionPane.setVisible(true);
        } else if (obj == AIItem) {
            ImageIcon icon = new ImageIcon("image/jqqd.jpg");	//注意设置图片尺寸，50*50px较适合
            JOptionPane.showMessageDialog(null, "此功能暂未完成！","敬请期待",JOptionPane.WARNING_MESSAGE,icon);
            //该消息框的提示图标会被自定义的图标覆盖掉
        } else if (obj == btnMusic) {
            JButton btn = (JButton) e.getSource(); //事件源指向音乐按钮
            if ("音乐".equals(btn.getText().trim())) {
                sound.loop();
                btn.setText("关闭c");
            } else {
                sound.stop();
                btn.setText("打开a");
            }
            btn.setFocusable(false);
        }


    }


}
