import javax.swing.JOptionPane;

public class Surface {
	public static void main(String[] args) {
		try {
			//外观；外观包！！！！！
			javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "外观初始化失败");
		}
		new CalcFrame();
	}
}
