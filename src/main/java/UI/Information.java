package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JSeparator;

public class Information extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Information frame = new Information();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Information() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Icon\\Actions-help-about-icon-32.png"));
		setTitle("Thông tin phần mềm");
		setBounds(100, 100, 450, 368);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbTitle = new JLabel("THÔNG TIN");
		lbTitle.setForeground(new Color(30, 144, 255));
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbTitle.setBounds(168, 22, 168, 36);
		contentPane.add(lbTitle);
		
		JTextPane txtIntroduction = new JTextPane();
		txtIntroduction.setBackground(new Color(230, 230, 250));
		txtIntroduction.setForeground(new Color(0, 0, 255));
		txtIntroduction.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtIntroduction.setEditable(false);
		txtIntroduction.setText("Người xây dựng phần mềm: Trần Thanh Tú\r\nTrường: Đại học khoa học - Đại học Huế\r\nLớp: Kỹ thuật phần mềm K44\r\nTên phần mềm: Quản lý sinh viên trường đại học\r\nHọc phần: \tJava cơ bản - Nhóm 2\r\nGiảng viên:   Nguyễn Đình Hoa Cương\r\nNội dung: \tBài tập lớn môn Java\r\n\r\nGiới thiệu phần mềm: \r\nĐây là phần mềm quản lý sinh viên trường đại học bao gồm quản lý khoa, quản lý lớp trong các khoa, quản lý sinh viên thuộc các lớp, quản lý học phần mà sinh viên đăng ký và quản lý điểm của từng sinh viên đã đăng ký học phần.\r\n\r\nMã nguồn: https://github.com/TuneOfCode/Application-Manage-Student-Of-University");
		txtIntroduction.setBounds(10, 77, 426, 244);
		contentPane.add(txtIntroduction);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 56, 436, 2);
		contentPane.add(separator);
	}
}
