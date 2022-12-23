package MainGame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class LoginJFrame extends JFrame implements KeyListener, MouseListener {
	//创建一个集合存储正确的用户名和密码
//	ArrayList 类是一个可以动态修改的数组，与普通数组的区别就是它是没有固定大小的限制，我们可以添加或删除元素。
//	添加元素到 ArrayList 可以使用 add() 方法:
//	访问 ArrayList 中的元素可以使用 get() 方法：
//	如果要修改 ArrayList 中的元素可以使用 set() 方法：sites.set(2, "Wiki");  第一个参数为索引位置，第二个为要修改的值
//	如果要删除 ArrayList 中的元素可以使用 remove() 方法
//	如果要计算 ArrayList 中的元素数量可以使用 size() 方法：

	static ArrayList<User> userlist = new ArrayList<>();
	static {
		userlist.add(new User("lisi","1234"));
		userlist.add(new User("zhangsan","1234"));
		userlist.add(new User ("jingchua","123"));
		
	}
	//登录界面的代码
	//构造方法
	
	JButton loginButton = new JButton(new ImageIcon("image/login/登录 (1).png"));
	JButton registerButton = new JButton(new ImageIcon("image/login/注册.png"));
	JLabel user = new JLabel("用户名:");
	JLabel passwordtext = new JLabel("密  码:");
	JLabel codetext = new JLabel("验证码:");
	
	JTextField username = new JTextField();
	//JTextField passwordtext = new JTextField();
	JPasswordField password = new JPasswordField();
	JTextField code = new JTextField();
    //正确的验证码
	JLabel rightcode = new JLabel();



	public LoginJFrame() {
		//创建登录界面的同时给这个界面设置一些信息
		//比如宽高，直接展示出来

		//初始化界面

		initJFrame();
		//让当前界面显示出来

		initView();

		this.setVisible(true);

	}

	public void initView() {


		user.setBounds(250,210,100,20);
		
		username.setBounds(300,210,100,20);
		this.getContentPane().add(username);
		
		passwordtext.setBounds(250,230,100,20);
		
		password.setBounds(300,230,100,20);
		this.getContentPane().add(password);
		
		codetext.setBounds(250,250,100,20);
		
		code.setBounds(300,250,50,20);
		this.getContentPane().add(code);
		
		String codeStr = CodeUtil.getCode();
		
		rightcode.setText(codeStr);
		rightcode.setBounds(360,250,50,20);
		this.getContentPane().add(rightcode);
		
		loginButton.setBounds(260,280,50,50);
	
		
		registerButton.setBounds(330,280,80,50);
		//去除按钮的边框去除按钮的背景
		loginButton.setBorderPainted(false);
		registerButton.setBorderPainted(false);
		loginButton.setContentAreaFilled(false);
		registerButton.setContentAreaFilled(false);
		//给注册按钮绑定鼠标事件
        registerButton.addMouseListener(this);
		this.getContentPane().add(registerButton);
		//给登录按钮绑定鼠标事件
        loginButton.addMouseListener(this);

		this.getContentPane().add(loginButton);

		JLabel bk = new JLabel(new ImageIcon("image/login/login.png"));
		bk.setBounds(0, 0,512, 512);
		
		this.getContentPane().add(codetext);
		this.getContentPane().add(user);
		this.getContentPane().add(passwordtext);
		this.getContentPane().add(bk);
		this.setFocusable(true);


	}

	public void initJFrame() {
		this.setSize(550,550);
		this.setTitle("游戏登录");
		//给登录按钮绑定键盘事件
		this.addKeyListener(this);
		this.setLocationRelativeTo(null);//居中
        this.setAlwaysOnTop(true);//置顶
        this.setLayout(null);//取消内部默认布局
		//输入密码或者验证码后可以使用回车键快速登录
		password.addKeyListener(this);
		code.addKeyListener(this);
		this.setDefaultCloseOperation(2);

		
	}
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e)  {
		int code1 = e.getKeyCode();
		if (code1 == 10) {
			System.out.println("按下登录");
			//获取两个文本框输入的内容
			String usernameInput = username.getText();
			String passwordInput = password.getText();
			//获取用户输入的验证码
			String codeInput = code.getText();
			//创建一个user对象
			User userInfo = new User(usernameInput,passwordInput);
			System.out.println("用户输入的用户名为" + usernameInput);
			System.out.println("用户输入的密码为" + passwordInput);

			if (codeInput.length() == 0) {
				showJDialog("验证码不能为空");
			} else if (usernameInput.length() == 0 || passwordInput.length() == 0) {
				//校验用户名和密码是否为空
				System.out.println("用户名或者密码为空");

				//调用showJDialog方法并展示弹框
				showJDialog("用户名或者密码为空");
			}
			else if (!codeInput.equalsIgnoreCase(rightcode.getText())) {
				showJDialog("验证码输入错误");
			} else if (contains(userInfo)) {
				System.out.println("用户名和密码正确可以开始玩游戏了");
				//关闭当前登录界面
				this.setVisible(false);
				//打开游戏的主界面
				//需要把当前登录的用户名传递给游戏界面
				new GameJFrame();
			} else {
				System.out.println("用户名或密码错误");
				showJDialog("用户名或密码错误");
			}
		} else if (e.getSource() == registerButton) {
			System.out.println("点击了注册按钮");
		} else if (e.getSource() == rightcode) {
			System.out.println("更换验证码");
			//获取一个新的验证码
			String code = CodeUtil.getCode();
			rightcode.setText(code);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource() == loginButton) {
			System.out.println("点击了登录");
			//获取两个文本框输入的内容
			String usernameInput = username.getText();
			String passwordInput = password.getText();
			//获取用户输入的验证码
			String codeInput = code.getText();
			//创建一个user对象
			User userInfo = new User(usernameInput,passwordInput);
			System.out.println("用户输入的用户名为" + usernameInput);
	        System.out.println("用户输入的密码为" + passwordInput);
	        
	        if (codeInput.length() == 0) {
                showJDialog("验证码不能为空");
            } else if (usernameInput.length() == 0 || passwordInput.length() == 0) {
                //校验用户名和密码是否为空
                System.out.println("用户名或者密码为空");

                //调用showJDialog方法并展示弹框
                showJDialog("用户名或者密码为空");
            }
            else if (!codeInput.equalsIgnoreCase(rightcode.getText())) {
                showJDialog("验证码输入错误");
            } else if (contains(userInfo)) {
                System.out.println("用户名和密码正确可以开始玩游戏了");
                //关闭当前登录界面
                this.setVisible(false);
                //打开游戏的主界面
                //需要把当前登录的用户名传递给游戏界面
				new GameJFrame();

			} else {
                System.out.println("用户名或密码错误");
                showJDialog("用户名或密码错误");
            }
        } else if (e.getSource() == registerButton) {
            System.out.println("点击了注册按钮");
        } else if (e.getSource() == rightcode) {
            System.out.println("更换验证码");
            //获取一个新的验证码
            String code = CodeUtil.getCode();
            rightcode.setText(code);
        }
		
	}

	 public void showJDialog(String content) {
         //创建一个弹框对象
         JDialog jDialog = new JDialog();
         //给弹框设置大小
         jDialog.setSize(200, 150);
         //让弹框置顶
         jDialog.setAlwaysOnTop(true);
         //让弹框居中
         jDialog.setLocationRelativeTo(null);
         //弹框不关闭永远无法操作下面的界面
         jDialog.setModal(true);

         //创建Jlabel对象管理文字并添加到弹框当中
         JLabel warning = new JLabel(content);
         warning.setBounds(0, 0, 200, 150);
         jDialog.getContentPane().add(warning);

         //让弹框展示出来
         jDialog.setVisible(true);
 }

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource() == loginButton) {
            loginButton.setIcon(new ImageIcon("image/login/登录 (2).png"));
        } else if (e.getSource() == registerButton) {
            registerButton.setIcon(new ImageIcon("image/login/注册 (1).png"));
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		if (e.getSource() == loginButton) {
            loginButton.setIcon(new ImageIcon("image/login/登录 (1).png"));
        } else if (e.getSource() == registerButton) {
            registerButton.setIcon(new ImageIcon("image/login/注册.png"));
        }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	} 
	
	
	//判断用户在集合中是否存在
	 public boolean contains(User userInput){
	       for (int i = 0; i < userlist.size(); i++) {
	            User rightUser = userlist.get(i);
	            if(userInput.getUsername().equals(rightUser.getUsername()) && userInput.getPassword().equals(rightUser.getPassword())){
	                //有相同的代表存在，返回true，后面的不需要再比了
	                return true;
	            }
	        }
	        //循环结束之后还没有找到就表示不存在
	        return false;
	    }





}
