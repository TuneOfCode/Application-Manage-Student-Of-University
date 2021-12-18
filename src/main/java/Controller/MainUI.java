package Controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuKeyEvent;


import com.mysql.cj.TransactionEventHandler;

import UI.Information;
import UI.ClassroomPanel;
import UI.DepartmentPanel;
import UI.Home;
import UI.ScorePanel;
import UI.StatisticalPanel;
import UI.StudentPanel;
import UI.SubjectPanel;

import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.awt.event.InputEvent;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Toolkit;

public class MainUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Home home;
	private DepartmentPanel dp;
	private ClassroomPanel cp;
	private StudentPanel sp;
	private SubjectPanel sup;
	private ScorePanel scp;
	private StatisticalPanel stp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public MainUI() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Icon\\depart.png"));
		setTitle("Phần mềm quản lý sinh viên trường đại học");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1258, 565);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1244, 35);
		contentPane.add(menuBar);
		
		JMenu mnAll = new JMenu("Chung");
		mnAll.setFont(new Font("Segoe UI", Font.BOLD, 16));
		menuBar.add(mnAll);
		
		JMenuItem mniExit = new JMenuItem("Thoát");
		mniExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});
		
		final JTabbedPane tbMainPanel = new JTabbedPane(JTabbedPane.TOP);
		tbMainPanel.setBounds(200, 68, 1034, 450);
		contentPane.add(tbMainPanel);
		
		JMenuItem mniHome = new JMenuItem("Trang chủ");
		mniHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (home == null) {
					home = new Home();
					ImageIcon icon = new ImageIcon("Icon\\Home-icon-16.png");
					tbMainPanel.addTab("Trang chủ", icon, home, "Trang chủ");
				}
				tbMainPanel.setSelectedComponent(home);
		}
		});
		mniHome.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0));
		mniHome.setIcon(new ImageIcon("Icon\\Home-icon-16.png"));
		mnAll.add(mniHome);
		mniExit.setIcon(new ImageIcon("Icon\\logout-icon-16.png"));
		mnAll.add(mniExit);
		mnAll.setMnemonic(MenuKeyEvent.VK_F);
		mniExit.setAccelerator(KeyStroke.getKeyStroke(MenuKeyEvent.VK_E, MenuKeyEvent.CTRL_MASK));
		
		JMenu mnManage = new JMenu("Quản lý");
		mnManage.setFont(new Font("Segoe UI", Font.BOLD, 16));
		menuBar.add(mnManage);
		
		home = new Home();
		ImageIcon icon = new ImageIcon("Icon\\Home-icon-16.png");
		tbMainPanel.addTab("Trang chủ", icon, home, "Trang chủ");
		tbMainPanel.setSelectedComponent(home);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("Icon\\school.png"));
		lblNewLabel.setBounds(81, 47, 867, 363);
		home.add(lblNewLabel);
		tbMainPanel.setMnemonicAt(0, KeyEvent.VK_1);
		
		JMenuItem mniDepartment = new JMenuItem("Khoa");
		mniDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dp == null) {
					try {
						dp = new DepartmentPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\room.png");
					tbMainPanel.addTab("Quản lý khoa", icon, dp, "Quản lý khoa");
				}
				tbMainPanel.setSelectedComponent(dp);
			}
		});
		mniDepartment.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_DOWN_MASK));
		mniDepartment.setIcon(new ImageIcon("Icon\\room.png"));
		mnManage.add(mniDepartment);
		
		JMenuItem mniClassroom = new JMenuItem("Lớp học");
		mniClassroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cp == null) {
					try {
						cp = new ClassroomPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\classroom.png");
					tbMainPanel.addTab("Quản lý lớp học", icon, cp, "Quản lý lớp học");
				}
				tbMainPanel.setSelectedComponent(cp);
			}
		});
		mniClassroom.setIcon(new ImageIcon("Icon\\classroom.png"));
		mniClassroom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		mnManage.add(mniClassroom);
		
		JMenuItem mniStudent = new JMenuItem("Sinh viên");
		mniStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sp == null) {
					try {
						sp = new StudentPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\10207-man-student-light-skin-tone-icon-16.png");
					tbMainPanel.addTab("Quản lý sinh viên", icon, sp, "Quản lý sinh viên");
				}
				tbMainPanel.setSelectedComponent(sp);
			}
		});
		mniStudent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK));
		mniStudent.setIcon(new ImageIcon("Icon\\10207-man-student-light-skin-tone-icon-16.png"));
		mnManage.add(mniStudent);
		
		JMenuItem mniSubject = new JMenuItem("Học phần");
		mniSubject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sup == null) {
					try {
						sup = new SubjectPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\subject.png");
					tbMainPanel.addTab("Quản lý học phần", icon, sup, "Quản lý học phần");
				}
				tbMainPanel.setSelectedComponent(sup);
			}
		});
		mniSubject.setIcon(new ImageIcon("Icon\\subject.png"));
		mniSubject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.ALT_DOWN_MASK));
		mnManage.add(mniSubject);
		
		JMenuItem mniScore = new JMenuItem("Điểm thi");
		mniScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scp == null) {
					try {
						scp = new ScorePanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\Actions-document-edit-icon-16.png");
					tbMainPanel.addTab("Quản lý điểm thi", icon, scp, "Quản lý điểm thi");
				}
				tbMainPanel.setSelectedComponent(scp);
			}
		});
		mniScore.setIcon(new ImageIcon("Icon\\Actions-document-edit-icon-16.png"));
		mniScore.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.ALT_DOWN_MASK));
		mnManage.add(mniScore);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Thống kê");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (stp == null) {
					try {
						stp = new StatisticalPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\class.png");
					tbMainPanel.addTab("Thống kê điểm trung bình tích lũy", icon, stp, "Thống kê điểm trung bình tích lũy");
				}
				tbMainPanel.setSelectedComponent(stp);	
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon("Icon\\class.png"));
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_DOWN_MASK));
		mnManage.add(mntmNewMenuItem);
		
		JMenu mnOther = new JMenu("Khác");
		mnOther.setFont(new Font("Segoe UI", Font.BOLD, 16));
		menuBar.add(mnOther);
		
		JMenuItem mnInformation = new JMenuItem("Thông tin");
		mnInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Information i = new Information();
				i.setLocationRelativeTo(null);
				i.setVisible(true);
			}
		});
		mnInformation.setIcon(new ImageIcon("Icon\\Actions-help-about-icon-16.png"));
		mnInformation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.ALT_DOWN_MASK));
		mnOther.add(mnInformation);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 31, 1244, 27);
		contentPane.add(toolBar);
		
		JButton btnDepartment = new JButton("Quản lý khoa");
		btnDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dp == null) {
					try {
						dp = new DepartmentPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\room.png");
					tbMainPanel.addTab("Quản lý khoa", icon, dp, "Quản lý khoa");
				}
				tbMainPanel.setSelectedComponent(dp);
			}
		});
		btnDepartment.setIcon(new ImageIcon("Icon\\room.png"));
		btnDepartment.setBackground(new Color(127, 255, 0));
		btnDepartment.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDepartment.setBounds(10, 68, 176, 55);
		contentPane.add(btnDepartment);
		
		JButton btnClass = new JButton("Quản lý lớp học");
		btnClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cp == null) {
					try {
						cp = new ClassroomPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\classroom.png");
					tbMainPanel.addTab("Quản lý lớp học", icon, cp, "Quản lý lớp học");
				}
				tbMainPanel.setSelectedComponent(cp);
			}
		});
		btnClass.setIcon(new ImageIcon("Icon\\classroom.png"));
		btnClass.setBackground(new Color(30, 144, 255));
		btnClass.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClass.setBounds(10, 133, 176, 55);
		contentPane.add(btnClass);
		
		JButton btnStudent = new JButton("Quản lý sinh viên");
		btnStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sp == null) {
					try {
						sp = new StudentPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\10207-man-student-light-skin-tone-icon-16.png");
					tbMainPanel.addTab("Quản lý sinh viên", icon, sp, "Quản lý sinh viên");
				}
				tbMainPanel.setSelectedComponent(sp);
			}
		});
		btnStudent.setBackground(new Color(250, 128, 114));
		btnStudent.setIcon(new ImageIcon("Icon\\10207-man-student-light-skin-tone-icon-24.png"));
		btnStudent.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnStudent.setBounds(10, 198, 176, 55);
		contentPane.add(btnStudent);
		
		JButton btnSubject = new JButton("Quản lý học phần");
		btnSubject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sup == null) {
					try {
						sup = new SubjectPanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\subject.png");
					tbMainPanel.addTab("Quản lý học phần", icon, sup, "Quản lý học phần");
				}
				tbMainPanel.setSelectedComponent(sup);
			}
		});
		btnSubject.setIcon(new ImageIcon("Icon\\subject.png"));
		btnSubject.setBackground(new Color(230, 230, 250));
		btnSubject.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSubject.setBounds(10, 263, 176, 55);
		contentPane.add(btnSubject);
		
		JButton btnScore = new JButton("Quản lý điểm thi");
		btnScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scp == null) {
					try {
						scp = new ScorePanel();
					} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ImageIcon icon = new ImageIcon("Icon\\Actions-document-edit-icon-16.png");
					tbMainPanel.addTab("Quản lý điểm thi", icon, scp, "Quản lý điểm thi");
				}
				tbMainPanel.setSelectedComponent(scp);
			}
		});
		btnScore.setBackground(new Color(250, 235, 215));
		btnScore.setIcon(new ImageIcon("Icon\\score.png"));
		btnScore.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnScore.setBounds(10, 328, 176, 55);
		contentPane.add(btnScore);
		
		JButton btnExit = new JButton("");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int click =  JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình?", "Question", JOptionPane.YES_NO_OPTION);
				if (click == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setIcon(new ImageIcon("Icon\\logout-icon-48.png"));
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExit.setBackground(new Color(255, 69, 0));
		btnExit.setBounds(10, 393, 176, 125);
		contentPane.add(btnExit);
		
		
	}
}
