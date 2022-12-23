
import MainGame.GameJFrame;
import MainGame.LoginJFrame;

public class App {
	public static void main(String[] args)  {
//		//表示程序的启动界面
//		try {
//			//音乐必须是绝对路径
//			AudioClip ac = Applet.newAudioClip((new File("C:\\Users\\10855\\Desktop\\Java\\PvzGame\\src\\Music\\test.wav")).toURL());
//			ac.loop();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//想要开启一个界面，就创建谁的对象就可
		//new LoginJFrame();
		new GameJFrame();
	}
}
