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

import OOP.Classroom;
import OOP.Student;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;

public class StudentPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtFName;
	private JComboBox cbbGender;
	private JTextField txtAcademic;
	private JTable tblist;
	private JTextField txtFind;
	private JTextField txtAddress;
	private JTextField txtLName;
	private JTextField txtIdClass;
	private Student s = new Student();
	private Student student[];
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
	public StudentPanel() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setLayout(null);
		setBounds(200, 68, 1034, 444);
		JLabel btnTitle = new JLabel("Thông tin về sinh viên");
		btnTitle.setForeground(new Color(255, 69, 0));
		btnTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnTitle.setBounds(410, 10, 232, 34);
		add(btnTitle);
		
		JLabel lbId = new JLabel("Mã sinh viên");
		lbId.setForeground(Color.RED);
		lbId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbId.setBounds(26, 61, 111, 25);
		add(lbId);
		
		JLabel lbFName = new JLabel("Họ và tên đệm");
		lbFName.setForeground(Color.RED);
		lbFName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbFName.setBounds(26, 96, 124, 25);
		add(lbFName);
		
		JLabel lbAcademic = new JLabel("Khóa học");
		lbAcademic.setForeground(Color.RED);
		lbAcademic.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbAcademic.setBounds(26, 199, 124, 25);
		add(lbAcademic);
		
		txtId = new JTextField();
		txtId.setBounds(187, 62, 153, 22);
		add(txtId);
		txtId.setColumns(10);
		
		txtFName = new JTextField();
		txtFName.setColumns(10);
		txtFName.setBounds(187, 100, 153, 22);
		add(txtFName);
		
		txtAcademic = new JTextField();
		txtAcademic.setColumns(10);
		txtAcademic.setBounds(187, 200, 153, 22);
		add(txtAcademic);
		
		cbbGender = new JComboBox();
		cbbGender.setFont(new Font("Tahoma", Font.BOLD, 12));
		cbbGender.setModel(new DefaultComboBoxModel(new String[] {"Nam", "Nữ"}));
		cbbGender.setBounds(187, 169, 153, 22);
		add(cbbGender);
		
		JLabel lbGender = new JLabel("Giới tính");
		lbGender.setForeground(Color.RED);
		lbGender.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbGender.setBounds(26, 164, 124, 25);
		add(lbGender);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 302, 1014, 112);
		add(scrollPane);
		
		tblist = new JTable();
		tblist.setBackground(new Color(230, 230, 250));
		tblist.setFont(new Font("Tahoma", Font.PLAIN, 12));
		String cols[] = {"Mã sinh viên", "Họ và tên đệm", "Tên sinh viên", "Giới tính", "Khóa học", "Địa chỉ", "Mã lớp"};
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
				txtId.setEditable(false);
				txtId.setText(model.getValueAt(i, 0).toString());
				txtFName.setText(model.getValueAt(i, 1).toString());
				txtLName.setText(model.getValueAt(i, 2).toString());
				cbbGender.setSelectedItem(model.getValueAt(i, 3));
				txtAcademic.setText(model.getValueAt(i, 4).toString());
				txtAddress.setText(model.getValueAt(i, 5).toString());
				txtIdClass.setText(model.getValueAt(i, 6).toString());
			}
		});
		
		JButton btnAdd = new JButton("Thêm");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtId.getText().isEmpty() || txtFName.getText().isEmpty()
							|| txtLName.getText().isEmpty() || txtAddress.getText().isEmpty()
							|| txtIdClass.getText().isEmpty() || txtAcademic.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Các trường không được để trống !", "Lỗi", JOptionPane.ERROR_MESSAGE);
						} else {
							add(s);
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
					edit(s);
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
		
		JSeparator separator = new JSeparator();
		separator.setBounds(26, 46, 948, -10);
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
		
		JLabel lbAddress = new JLabel("Địa chỉ");
		lbAddress.setForeground(Color.RED);
		lbAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbAddress.setBounds(26, 234, 124, 25);
		add(lbAddress);
		
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(187, 232, 153, 22);
		add(txtAddress);
		
		JLabel tbLName = new JLabel("Tên sinh viên");
		tbLName.setForeground(Color.RED);
		tbLName.setFont(new Font("Tahoma", Font.BOLD, 15));
		tbLName.setBounds(26, 131, 124, 25);
		add(tbLName);
		
		txtLName = new JTextField();
		txtLName.setColumns(10);
		txtLName.setBounds(187, 131, 153, 22);
		add(txtLName);
		
		JLabel lbIdClass = new JLabel("Mã lớp");
		lbIdClass.setForeground(Color.RED);
		lbIdClass.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbIdClass.setBounds(26, 267, 124, 25);
		add(lbIdClass);
		
		txtIdClass = new JTextField();
		txtIdClass.setColumns(10);
		txtIdClass.setBounds(187, 270, 153, 22);
		add(txtIdClass);
		
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
		txtId.setEditable(true);
		txtId.setText("");
		txtFName.setText("");
		txtLName.setText("");
		txtAddress.setText("");
		txtIdClass.setText("");
		txtAcademic.setText("");
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
		String query = "SELECT * FROM student";
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
				String rows[] = new String[7];
				rows[0] = resultSet.getString(1);
				rows[1] = resultSet.getString(2);
				rows[2] = resultSet.getString(3);
				rows[3] = resultSet.getString(4);
				rows[4] = resultSet.getString(5);
				rows[5] = resultSet.getString(6);
				rows[6] = resultSet.getString(7);
				model.addRow(rows);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultSet = null;
			e.printStackTrace();
		}
	}
	
	public void add(Student s) throws SQLException {
		s.setID(txtId.getText());
		s.setFirstname(txtFName.getText());
		s.setLastname(txtLName.getText());
		String data = "";
		if (cbbGender.getSelectedIndex() != -1) {
			data = "" + cbbGender.getItemAt(cbbGender.getSelectedIndex());
		}
		s.setGender(data);
		s.setAcadamic_year(Integer.parseInt(txtAcademic.getText()));
		s.setAddress(txtAddress.getText());
		s.setClass_ID(txtIdClass.getText());
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		ResultSet resultSet = null;
		Statement statement =  conn.createStatement();
		resultSet =  statement.executeQuery("SELECT id FROM student");
		int count = 0;
		while (resultSet.next()) {
			if (resultSet.getString(1).equals(s.getID())) {
				count++;
			}
		}
		if (count > 0) {
			JOptionPane.showMessageDialog(null, "Mã sinh viên không được trùng !", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} else {
			resultSet = null;
			statement =  conn.createStatement();
			resultSet =  statement.executeQuery("SELECT id FROM class");
			count = 0;
			while (resultSet.next()) {
				if (resultSet.getString(1).equals(s.getClass_ID())) {
					count++;
				}
			}
			
			if (count < 1) {
				JOptionPane.showMessageDialog(null, "Mã lớp không tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			} else {
				String sql = "INSERT INTO student(id,firstname,lastname,gender,academic_year,address,class_id) "
						+ "VALUES(?,?,?,?,?,?,?)";
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, s.getID());
					ps.setString(2, s.getFirstname());
					ps.setString(3, s.getLastname());
					ps.setString(4, s.getGender());
					ps.setInt(5, s.getAcadamic_year());
					ps.setString(6, s.getAddress());
					ps.setString(7, s.getClass_ID());
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
	
	public void edit(Student s) throws SQLException {
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		s.setID(txtId.getText());
		s.setFirstname(txtFName.getText());
		s.setLastname(txtLName.getText());
		String data = "";
		if (cbbGender.getSelectedIndex() != -1) {
			data = "" + cbbGender.getItemAt(cbbGender.getSelectedIndex());
		}
		s.setGender(data);
		s.setAcadamic_year(Integer.parseInt(txtAcademic.getText()));
		s.setAddress(txtAddress.getText());
		s.setClass_ID(txtIdClass.getText());
		
		ResultSet resultSet = null;
		st =  conn.createStatement();
		resultSet =  st.executeQuery("SELECT id FROM class");
		int count = 0;
		while (resultSet.next()) {
			if (resultSet.getString(1).equals(s.getClass_ID())) {
				count++;
			}
		}
		
		if (count < 1) {
			JOptionPane.showMessageDialog(null, "Mã lớp không tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} else {
			String sql = "UPDATE student SET firstname=?,lastname=?,gender=?,academic_year=?,address=?,class_id=? WHERE id=?";
			try {
				
				ps = conn.prepareStatement(sql);
				
				int i = tblist.getSelectedRow();
				model.setValueAt(s.getID(), i, 0);
				model.setValueAt(s.getFirstname(), i, 1);
				model.setValueAt(s.getLastname(), i, 2);
				model.setValueAt(s.getGender(), i, 3);
				model.setValueAt(s.getAcadamic_year(), i, 4);
				model.setValueAt(s.getAddress(), i, 5);
				model.setValueAt(s.getClass_ID(), i, 6);
				
				ps.setString(7, txtId.getText());
				ps.setString(1, s.getFirstname());
				ps.setString(2, s.getLastname());
				ps.setString(3, s.getGender());
				ps.setInt(4, s.getAcadamic_year());
				ps.setString(5, s.getAddress());
				ps.setString(6, s.getClass_ID());
				
				ps.executeUpdate();
				clean();
				JOptionPane.showMessageDialog(null, "Sửa thành công ! ");
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Lỗi khi sửa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}	
	}
	
	public void delete() {
		String deleteFK_score = "DELETE FROM score WHERE student_id= ?";
		String delete = "DELETE FROM student WHERE id= ? ";
		try {
			int i = tblist.getSelectedRow();
			if (i>=0) {
				JOptionPane.showMessageDialog(null, "Đã xóa sinh viên có mã " + tblist.getValueAt(i, 0));
				//Delete FK
				ps = conn.prepareStatement(deleteFK_score);
				ps.setString(1, (String) tblist.getValueAt(i, 0));
				ps.executeUpdate();
				// Delete PK
				ps = conn.prepareStatement(delete);
				ps.setString(1, (String) tblist.getValueAt(i, 0));
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
		String find = "SELECT * FROM student ";
		String search = txtFind.getText();
		if (search.length()>0) {
			find = find + " WHERE id like '%" + search + "%' "
						+ "	OR firstname like '%" + search + "%' "
						+ "	OR lastname like '%" + search + "%' "
						+ "	OR gender like '%" + search + "%' "
						+ "	OR academic_year like '%" + search + "%' "
						+ "	OR address like '%" + search + "%' "
						+ "	OR class_id like '%" + search + "%' ";
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
					
					XSSFCell cellId = row.getCell(0);
					XSSFCell cellFName = row.getCell(1);
					XSSFCell cellLName = row.getCell(2);
					XSSFCell cellGender = row.getCell(3);
					XSSFCell cellAcademicYear = row.getCell(4);
					XSSFCell cellAddress = row.getCell(5);
					XSSFCell cellClassId = row.getCell(6);
				
					model.addRow(new Object[] {cellId, cellFName, cellLName, cellGender, cellAcademicYear, cellAddress, cellClassId});
				
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
				student = new Student[model.getRowCount()];
				ArrayList<Student> List = new ArrayList<Student>();
				for (int i=0; i<model.getRowCount(); i++) {
					student[i] = (Student) ois.readObject();
					List.add(student[i]);
		        }
				
				for (int i=0; i<List.size(); i++) {
					System.out.println(List.get(i));
					model.addRow(new Object[] {List.get(i).getID(), List.get(i).getFirstname(), 
						List.get(i).getLastname(), List.get(i).getGender(),
						List.get(i).getAcadamic_year(), List.get(i).getAddress(),
						List.get(i).getClass_ID()});
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
				 student = new Student[model.getRowCount()];
				 for (int i=0; i<model.getRowCount(); i++) {
					 	student[i] = new Student();
					 	student[i].setID(model.getValueAt(i, 0).toString());
					 	student[i].setFirstname(model.getValueAt(i, 1).toString());
					 	student[i].setLastname(model.getValueAt(i, 2).toString());
					 	student[i].setGender(model.getValueAt(i, 3).toString());
					 	student[i].setAcadamic_year(Integer.parseInt(model.getValueAt(i, 4).toString()));
					 	student[i].setAddress(model.getValueAt(i, 5).toString());
					 	student[i].setClass_ID(model.getValueAt(i, 6).toString());
						oos.writeObject(student[i]);
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
        XSSFSheet spreadsheet = workbook.createSheet("Sinh viên");

        XSSFRow row = null;
        Cell cell = null;

        row = spreadsheet.createRow((short) 0);
        row.setHeight((short) 300);
       
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Mã sinh viên");
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Họ và tên đệm");
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên của sinh viên");
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Giới tính");
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Khóa đào tạo");
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Địa chỉ");
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Mã lớp");
      
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
   				  		cells = rows.createCell(2, CellType.STRING);
	       				cells.setCellValue(model.getValueAt(i, 2).toString());
	       				cells = rows.createCell(3, CellType.STRING);
	       				cells.setCellValue(model.getValueAt(i, 3).toString());
	       				cells = rows.createCell(4, CellType.STRING);
	       				cells.setCellValue(model.getValueAt(i, 4).toString());
	       				cells = rows.createCell(5, CellType.STRING);
	       				cells.setCellValue(model.getValueAt(i, 5).toString());
	       				cells = rows.createCell(6, CellType.STRING);
	       				cells.setCellValue(model.getValueAt(i, 6).toString());
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
		String query = "SELECT * FROM student ORDER BY lastname ASC";
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
