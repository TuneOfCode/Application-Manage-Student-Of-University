package UI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class Home extends JPanel {

	/**
	 * Create the panel.
	 */
	public Home() {
		setLayout(null);
		setBounds(200, 68, 1034, 450);
		
		JLabel btnTitle = new JLabel("Trang chủ quản lý sinh viên trường đại học");
		btnTitle.setForeground(new Color(255, 69, 0));
		btnTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnTitle.setBounds(288, 10, 418, 35);
		add(btnTitle);

	}

}
