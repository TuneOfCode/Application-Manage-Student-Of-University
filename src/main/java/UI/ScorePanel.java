package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import OOP.Score;
import OOP.Subject;

public class ScorePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtIdSubject;
	private JTextField txtIdStudent;
	private JTextField txtMid;
	private JTable tblist;
	private JTextField txtFind;
	private JTextField txtEnd;
	private Score s = new Score();
	private Score score[];
	private DefaultTableModel model;
	private static String URL = "jdbc:mysql://localhost:3306/managestudentuniversity";
	private static String USER = "root";
	private static String PASSWORD = "";
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private Statement st;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ScorePanel() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setLayout(null);
		setBounds(200, 68, 1034, 421);
		
		JLabel btnTitle = new JLabel("Thông tin về điểm");
		btnTitle.setForeground(new Color(205, 133, 63));
		btnTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnTitle.setBounds(410, 10, 185, 25);
		add(btnTitle);
		
		JLabel lbIdSuject = new JLabel("Mã học phần");
		lbIdSuject.setForeground(new Color(205, 133, 63));
		lbIdSuject.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbIdSuject.setBounds(26, 79, 110, 25);
		add(lbIdSuject);
		
		JLabel lbIdStudent = new JLabel("Mã sinh viên");
		lbIdStudent.setForeground(new Color(205, 133, 63));
		lbIdStudent.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbIdStudent.setBounds(26, 118, 110, 25);
		add(lbIdStudent);
		
		JLabel lbScoreMid = new JLabel("Điểm quá trình");
		lbScoreMid.setForeground(new Color(205, 133, 63));
		lbScoreMid.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbScoreMid.setBounds(26, 156, 124, 25);
		add(lbScoreMid);
		
		txtIdSubject = new JTextField();
		txtIdSubject.setBounds(187, 80, 165, 28);
		add(txtIdSubject);
		txtIdSubject.setColumns(10);
		
		txtIdStudent = new JTextField();
		txtIdStudent.setColumns(10);
		txtIdStudent.setBounds(187, 119, 165, 28);
		add(txtIdStudent);
		
		txtMid = new JTextField();
		txtMid.setColumns(10);
		txtMid.setBounds(187, 157, 165, 28);
		add(txtMid);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 245, 1014, 158);
		add(scrollPane);
		
		tblist = new JTable();
		tblist.setBackground(new Color(230, 230, 250));
		tblist.setFont(new Font("Tahoma", Font.PLAIN, 12));
		String cols[] = {"Mã học phần", "Mã sinh viên", "Điểm quá trình", "Điểm học kỳ", "Điểm hệ 10", "Điểm hệ 4"};
		model = new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		model.setColumnIdentifiers(cols);
		tblist.setModel(model);
		scrollPane.setViewportView(tblist);
		ConnectMySql();
		render(view());
		
		tblist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tblist.getSelectedRow();
				txtIdSubject.setEditable(false);
				txtIdStudent.setEditable(false);
				txtIdSubject.setText(model.getValueAt(i, 0).toString());
				txtIdStudent.setText(model.getValueAt(i, 1).toString());
				txtMid.setText(model.getValueAt(i, 2).toString());
				txtEnd.setText(model.getValueAt(i, 3).toString());
			}
		});
		
		JButton btnAdd = new JButton("Thêm");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
						
						if (txtIdSubject.getText().isEmpty() || txtIdStudent.getText().isEmpty()
								|| txtMid.getText().isEmpty() || txtEnd.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Các trường không được để trống !", "Lỗi", JOptionPane.ERROR_MESSAGE);
							
						} else {
							double score_mid = Double.parseDouble(txtMid.getText());
							double score_end = Double.parseDouble(txtEnd.getText());
							if ((score_mid < 0 || score_mid > 10) 
									|| (score_end < 0  || score_end > 10 )) {
								JOptionPane.showMessageDialog(null, "Điểm số phải từ 0 đến 10!", "Lỗi", JOptionPane.ERROR_MESSAGE);
							} 
							else {
								add(s);
							}
						}
							
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setIcon(new ImageIcon("Icon\\add.png"));
		btnAdd.setForeground(new Color(0, 128, 0));
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setBounds(552, 75, 136, 34);
		add(btnAdd);
		
		JButton btnEdit = new JButton("Sửa");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtIdSubject.getText().isEmpty() || txtIdStudent.getText().isEmpty()
							|| txtMid.getText().isEmpty() || txtEnd.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Các trường không được để trống !", "Lỗi", JOptionPane.ERROR_MESSAGE);
						
					} else {
						double score_mid = Double.parseDouble(txtMid.getText());
						double score_end = Double.parseDouble(txtEnd.getText());
						if ((score_mid < 0 || score_mid > 10) 
								|| (score_end < 0  || score_end > 10 )) {
							JOptionPane.showMessageDialog(null, "Điểm số phải từ 0 đến 10!", "Lỗi", JOptionPane.ERROR_MESSAGE);
						} 
						else {
							edit(s);
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEdit.setIcon(new ImageIcon("Icon\\Actions-document-edit-icon-16.png"));
		btnEdit.setForeground(new Color(106, 90, 205));
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEdit.setBounds(698, 75, 136, 34);
		add(btnEdit);
		
		JButton btnImport = new JButton("Đọc Excel");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ImportExcel();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImport.setIcon(new ImageIcon("Icon\\excel.png"));
		btnImport.setForeground(new Color(46, 139, 87));
		btnImport.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnImport.setBounds(552, 114, 136, 34);
		add(btnImport);
		
		JButton btnDelete = new JButton("Xóa");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnDelete.setIcon(new ImageIcon("Icon\\Actions-edit-delete-icon-16.png"));
		btnDelete.setForeground(new Color(220, 20, 60));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(844, 75, 136, 34);
		add(btnDelete);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 222, 1014, -1);
		add(separator_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(26, 37, 557, -1);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(486, 62, 0, 166);
		add(separator_1);
		
		JButton btnExcel = new JButton("Xuất Excel");
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportExcel(tblist);
			}
		});
		btnExcel.setIcon(new ImageIcon("Icon\\excel.png"));
		btnExcel.setForeground(new Color(50, 205, 50));
		btnExcel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExcel.setBounds(844, 114, 136, 34);
		add(btnExcel);
		
		JButton btnFind = new JButton("Tìm kiếm");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows = model.getRowCount(); 
				for(int j = rows - 1; j >=0; j--) {
				   model.removeRow(j); 
				}
				render(find());
			}
		});
		btnFind.setIcon(new ImageIcon("Icon\\search-icon-24.png"));
		btnFind.setForeground(new Color(255, 0, 255));
		btnFind.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnFind.setBounds(844, 152, 136, 34);
		add(btnFind);
		
		txtFind = new JTextField();
		txtFind.setBounds(698, 154, 136, 33);
		add(txtFind);
		txtFind.setColumns(10);
		
		JButton btnExport = new JButton("Xuất file txt");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					exportFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnExport.setIcon(new ImageIcon("Icon\\export.png"));
		btnExport.setForeground(new Color(112, 128, 144));
		btnExport.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExport.setBounds(698, 114, 136, 34);
		add(btnExport);
		
		JButton btnRender = new JButton("Hiển thị");
		btnRender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows = model.getRowCount(); 
				for(int j = rows - 1; j >=0; j--) {
				   model.removeRow(j); 
				}
				render(view());
				clean();
			}
		});
		btnRender.setIcon(new ImageIcon("Icon\\class.png"));
		btnRender.setForeground(new Color(128, 128, 0));
		btnRender.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRender.setBounds(552, 191, 136, 34);
		add(btnRender);
		
		JButton btnClean = new JButton("Làm mới");
		btnClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clean();
			}
		});
		btnClean.setIcon(new ImageIcon("Icon\\logout-icon-16.png"));
		btnClean.setForeground(new Color(0, 128, 128));
		btnClean.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClean.setBounds(844, 191, 136, 34);
		add(btnClean);
		
		JButton btnSort = new JButton("Sắp xếp (a-z)");
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows = model.getRowCount(); 
				for(int j = rows - 1; j >=0; j--) {
				   model.removeRow(j); 
				}
				render(sort());
			}
		});
		btnSort.setIcon(new ImageIcon("Icon\\subject.png"));
		btnSort.setForeground(new Color(128, 0, 0));
		btnSort.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSort.setBounds(698, 191, 136, 34);
		add(btnSort);
		
		JLabel lblimHcK = new JLabel("Điểm học kỳ");
		lblimHcK.setForeground(new Color(205, 133, 63));
		lblimHcK.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblimHcK.setBounds(26, 191, 124, 25);
		add(lblimHcK);
		
		txtEnd = new JTextField();
		txtEnd.setColumns(10);
		txtEnd.setBounds(187, 192, 165, 28);
		add(txtEnd);
		
		JButton btnImportTxt = new JButton("Đọc file txt");
		btnImportTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ImportTxt();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImportTxt.setIcon(new ImageIcon("Icon\\export.png"));
		btnImportTxt.setForeground(new Color(112, 128, 144));
		btnImportTxt.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnImportTxt.setBounds(552, 152, 136, 34);
		add(btnImportTxt);
	}
	
	public void clean() {
		txtIdSubject.setEditable(true);
		txtIdStudent.setEditable(true);
		txtIdSubject.setText("");
		txtIdStudent.setText("");
		txtMid.setText("");
		txtEnd.setText("");
		txtFind.setText("");
	}
	
	/*----------------------------------------------- CONNECT DATABASE ----------------------------------------------- */
	public void ConnectMySql() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			//System.out.println("connect successfully!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("connect failure!");
			conn.close();
			e.printStackTrace();
		}
	}
	
	public ResultSet view() {
		ResultSet resultSet = null;
		String query = "SELECT * FROM score";
		try {
			st =  conn.createStatement();
			return st.executeQuery(query);
		} catch (SQLException e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		return resultSet;
	}
	
	
	public void render(ResultSet resultSet) {
		try {
			while(resultSet.next()) {
				String rows[] = new String[6];
				rows[0] = resultSet.getString(1);
				rows[1] = resultSet.getString(2);
				rows[2] = resultSet.getString(3);
				rows[3] = resultSet.getString(4);
				rows[4] = resultSet.getString(5);
				rows[5] = resultSet.getString(6);
				model.addRow(rows);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultSet = null;
			e.printStackTrace();
		}
	}
	
	public void add(Score sc) throws SQLException {
		sc.setSubject_ID(txtIdSubject.getText());;
		sc.setStudent_ID(txtIdStudent.getText());
		sc.setScore_Midterm(Double.parseDouble(txtMid.getText()));
		sc.setScore_Endterm(Double.parseDouble(txtEnd.getText()));
		
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		ResultSet resultSet = null;
		Statement statement =  conn.createStatement();
		resultSet =  statement.executeQuery("SELECT subject_id, student_id FROM score");
		int countSS = 0;
		while (resultSet.next()) {
			if (resultSet.getString(1).equals(sc.getSubject_ID()) && resultSet.getString(2).equals(sc.getStudent_ID())) {
				countSS++;
			}
		}
		if (countSS > 0) {
			JOptionPane.showMessageDialog(null, "Mã học phần và Mã sinh viên không được trùng !", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} else {
			resultSet = null;
			statement =  conn.createStatement();
			resultSet =  statement.executeQuery("SELECT id FROM subject");
			int count = 0;
			while (resultSet.next()) {
				if (resultSet.getString(1).equals(sc.getSubject_ID())) {
					count++;
				}
			}
			if (count < 1) {
				JOptionPane.showMessageDialog(null, "Mã học phần không tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else {
				resultSet = null;
				statement =  conn.createStatement();
				resultSet =  statement.executeQuery("SELECT id FROM student");
				count = 0;
				while (resultSet.next()) {
					if (resultSet.getString(1).equals(sc.getStudent_ID())) {
						count++;
					}
				}
				
				if (count < 1) {
					JOptionPane.showMessageDialog(null, "Mã sinh viên không tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
				} else {
					double score_10 = sc.Coefficient_10(sc.getScore_Midterm(), sc.getScore_Endterm());
					double score_4 = sc.Coefficient_4(score_10);
					
					String sql = "INSERT INTO score(subject_id,student_id,score_midterm,score_endterm,Coefficient_10,Coefficient_4) VALUES(?,?,?,?,?,?)";
					try {
						ps = conn.prepareStatement(sql);
						ps.setString(1, sc.getSubject_ID());
						ps.setString(2, sc.getStudent_ID());
						ps.setDouble(3, sc.getScore_Midterm());
						ps.setDouble(4, sc.getScore_Endterm());
						ps.setDouble(5, score_10);
						ps.setDouble(6, score_4);
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "Thêm thành công ! ");
						int rows = model.getRowCount(); 
						for(int j = rows - 1; j >=0; j--) {
						   model.removeRow(j); 
						}
						render(view());
						clean();
					} catch (Exception e) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Lỗi khi thêm !", "Lỗi", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void edit(Score sc) throws SQLException {
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		sc.setSubject_ID(txtIdSubject.getText());;
		sc.setStudent_ID(txtIdStudent.getText());
		sc.setScore_Midterm(Double.parseDouble(txtMid.getText()));
		sc.setScore_Endterm(Double.parseDouble(txtEnd.getText()));
		
		double score_10 = sc.Coefficient_10(sc.getScore_Midterm(), sc.getScore_Endterm());
		double score_4 = sc.Coefficient_4(score_10);
		
			String sql = "UPDATE score SET score_midterm=?,score_endterm=?,Coefficient_10=?,Coefficient_4=? WHERE subject_id=? AND student_id=?";
			try {
				ps = conn.prepareStatement(sql);
				
				int i = tblist.getSelectedRow();
	
				model.setValueAt(sc.getScore_Midterm(), i, 2);
				model.setValueAt(sc.getScore_Endterm(), i, 3);
				model.setValueAt(score_10, i, 4);
				model.setValueAt(score_4, i, 5);
				
				ps.setString(5, txtIdSubject.getText());
				ps.setString(6, txtIdStudent.getText());
				ps.setDouble(1, sc.getScore_Midterm());
				ps.setDouble(2, sc.getScore_Endterm());
				ps.setDouble(3, score_10);
				ps.setDouble(4, score_4);
				
				ps.executeUpdate();
				clean();
				JOptionPane.showMessageDialog(null, "Sửa thành công ! ");
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Lỗi khi sửa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
	}
	
	public void delete() {
		String delete = "DELETE FROM score WHERE subject_id= ? AND student_id= ?";
		try {
			int i = tblist.getSelectedRow();
			if (i>=0) {
				JOptionPane.showMessageDialog(null, "Đã xóa học phần có mã " + tblist.getValueAt(i, 0) 
					+ " và sinh viên có mã " + tblist.getValueAt(i, 1));
				//Delete PK
				ps = conn.prepareStatement(delete);
				ps.setString(1, (String) tblist.getValueAt(i, 0));
				ps.setString(2, (String) tblist.getValueAt(i, 1));
				ps.executeUpdate();
				model.removeRow(i);
				
				clean();
			} 
		} 
		catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Lỗi khi xóa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public ResultSet find() {
		ResultSet resultSet = null;
		int rows = model.getRowCount(); 
		for(int j = rows - 1; j >=0; j--) {
		   model.removeRow(j); 
		}
		String find = "SELECT * FROM score ";
		String search = txtFind.getText();
		if (search.length()>0) {
			find = find + " WHERE subject_id like '%" + search + "%' "
						+ "	OR student_id like '%" + search + "%' "
						+ "	OR score_midterm like '%" + search + "%' "
						+ "	OR score_endterm like '%" + search + "%' "
						+ "	OR Coefficient_10 like '%" + search + "%' "
						+ "	OR Coefficient_4 like '%" + search + "%' ";
		} else {
			rows = model.getRowCount(); 
			for(int j = rows - 1; j >=0; j--) {
			   model.removeRow(j); 
			}
		}
		try {
			st =  conn.createStatement();
			return st.executeQuery(find);
		} catch (SQLException e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		return resultSet;
	}
	
	public void ImportExcel() throws IOException {
		FileInputStream file = null;
		BufferedInputStream bis = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Open Excel Files");
		int excelChooser = chooser.showOpenDialog(null);
		if (excelChooser == JFileChooser.APPROVE_OPTION) {
			try {
				File excelFile = chooser.getSelectedFile();
				file = new FileInputStream(excelFile);
				bis = new BufferedInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(bis);
				XSSFSheet sheet = workbook.getSheetAt(0);

				for (int i=0; i<sheet.getLastRowNum(); i++) {
					XSSFRow row = sheet.getRow(i+1);
					
					XSSFCell cellSubjectId = row.getCell(0);
					XSSFCell cellStudentId = row.getCell(1);
					XSSFCell cellMid = row.getCell(2);
					XSSFCell cellEnd = row.getCell(3);
					XSSFCell cell_10 = row.getCell(4);
					XSSFCell cell_4 = row.getCell(5);
				
					model.addRow(new Object[] {cellSubjectId, cellStudentId, cellMid, cellEnd, cell_10, cell_4});
				
				System.out.println();
				}
				workbook.close();
				file.close();
				JOptionPane.showMessageDialog(null, "Đọc file thành công!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Lỗi khi đọc file!");
			}
		}
		
	}
	
	public void ImportTxt() throws IOException {
		FileInputStream file = null;
		ObjectInputStream ois = null;
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Open Text Files");
		int textChooser = chooser.showOpenDialog(null);
		if (textChooser == JFileChooser.APPROVE_OPTION) {
			try {
				File txtFile = chooser.getSelectedFile();
				file = new FileInputStream(txtFile);
				ois = new ObjectInputStream(file);
				score = new Score[model.getRowCount()];
				ArrayList<Score> List = new ArrayList<Score>();
				for (int i=0; i<model.getRowCount(); i++) {
					score[i] = (Score) ois.readObject();
					List.add(score[i]);
		        }
				
				for (int i=0; i<List.size(); i++) {
					System.out.println(List.get(i));
					model.addRow(new Object[] {List.get(i).getSubject_ID(), List.get(i).getStudent_ID(), 
							List.get(i).getScore_Midterm(),
							List.get(i).getScore_Endterm(), 
							List.get(i).Coefficient_10(List.get(i).getScore_Midterm(), List.get(i).getScore_Endterm()),
							List.get(i).Coefficient_4(List.get(i).Coefficient_10(List.get(i).getScore_Midterm(), List.get(i).getScore_Endterm()))});
				}
				System.out.println();
				System.out.println("----------------------------------------\n");
				file.close();
				ois.close();
				JOptionPane.showMessageDialog(null, "Đọc file thành công!");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Lỗi khi đọc file!");
			}
		}
		
	}
	
	public void exportFile() throws IOException {
		
		
		 FileOutputStream fos = null;
	     ObjectOutputStream oos =null;
		 JFileChooser chooser = new JFileChooser();
		 
		 chooser.setDialogTitle("Save Excel Files");
		 FileNameExtensionFilter fnef = new FileNameExtensionFilter("Text File(*.txt)", "txt");
		 chooser.setFileFilter(fnef);
		 int excelChooser = chooser.showSaveDialog(chooser);
		 if (excelChooser == JFileChooser.APPROVE_OPTION) {
			 try {
				 File file = chooser.getSelectedFile();
				 fos = new FileOutputStream(file + ".txt");
				 oos =  new ObjectOutputStream(fos);
				 score = new Score[model.getRowCount()];
				 for (int i=0; i<model.getRowCount(); i++) {
						 score[i] = new Score();
						 score[i].setSubject_ID((model.getValueAt(i, 0).toString()));
						 score[i].setStudent_ID(model.getValueAt(i, 1).toString());
						 score[i].setScore_Midterm(Double.parseDouble(model.getValueAt(i, 2).toString()));
						 score[i].setScore_Endterm(Double.parseDouble(model.getValueAt(i, 3).toString()));
						 score[i].Coefficient_10(Double.parseDouble(model.getValueAt(i, 2).toString()), Double.parseDouble(model.getValueAt(i, 3).toString()));
						 score[i].Coefficient_4(score[i].Coefficient_10(Double.parseDouble(model.getValueAt(i, 2).toString()), Double.parseDouble(model.getValueAt(i, 3).toString())));
						oos.writeObject(score[i]);
					}
				 oos.flush();
				 JOptionPane.showMessageDialog(null, "Lưu file thành công!"); 
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Lỗi khi lưu file!");
			}
		 }
	}
	
	public void exportExcel(JTable table) {
		 FileOutputStream fos = null;
		 BufferedOutputStream bos = null;
		 JFileChooser chooser = new JFileChooser();
		 
		 chooser.setDialogTitle("Save Excel Files");
		 FileNameExtensionFilter fnef = new FileNameExtensionFilter("Excel File(*.xls, *.xlsx)", "xls", "xlsx");
		 chooser.setFileFilter(fnef);
		 int excelChooser = chooser.showSaveDialog(chooser);
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Điểm thi");

        XSSFRow row = null;
        Cell cell = null;

        row = spreadsheet.createRow((short) 0);
        row.setHeight((short) 300);
       
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Mã học phần");
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã sinh viên");
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Điểm quá trình");
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Điểm học kỳ");
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Điểm hệ 10");
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Điểm hệ 4");
      
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
        
        	try {
    			// thêm excel từ thư viện poi
        		File file = chooser.getSelectedFile();
        		XSSFRow rows = null;
        		Cell cells = null;
        		fos = new FileOutputStream(file + ".xlsx");
  			  	bos = new BufferedOutputStream(fos);
        		//XSSFRow rows = null;
    			DefaultTableModel model = (DefaultTableModel) table.getModel();
    			  for (int i=0; i<table.getRowCount(); i++) {
    				  	rows = spreadsheet.createRow((short) i+1);
 
					 	cells = rows.createCell(0, CellType.STRING);
   				  		cells.setCellValue(model.getValueAt(i, 0).toString());
   				  		cells = rows.createCell(1, CellType.STRING);
       				  	cells.setCellValue(model.getValueAt(i, 1).toString());
   				  		cells = rows.createCell(2, CellType.NUMERIC);
	       				cells.setCellValue(model.getValueAt(i, 2).toString());
	       				cells = rows.createCell(3, CellType.NUMERIC);
	       				cells.setCellValue(model.getValueAt(i, 3).toString());
	       				cells = rows.createCell(4, CellType.NUMERIC);
	       				cells.setCellValue(model.getValueAt(i, 4).toString());
	       				cells = rows.createCell(5, CellType.NUMERIC);
	       				cells.setCellValue(model.getValueAt(i, 5).toString());
    			  }
    			  for (int i = 0; i < model.getColumnCount(); i++) {
    			      spreadsheet.autoSizeColumn((short) i);
    			    }
    			  workbook.write(bos);
    		  
    			  JOptionPane.showMessageDialog(null, "Lưu file thành công!");

   
    			  bos.close();
    			  workbook.close();
    
    			} catch (Exception e) {
    				// TODO: handle exception
    				e.printStackTrace();
    				JOptionPane.showMessageDialog(null, "Lỗi khi lưu file!");
    			}
    		 }
	}
	
	public ResultSet sort() {
		ResultSet resultSet = null;
		String query = "SELECT * FROM score ORDER BY student_id ASC";
		try {
			st =  conn.createStatement();
			return st.executeQuery(query);
		} catch (SQLException e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
		return resultSet;
	}

}
