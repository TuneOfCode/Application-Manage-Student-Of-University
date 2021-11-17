package UI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import OOP.Department;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.ref.Cleaner.Cleanable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class DepartmentPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtPhone;
	private JTable tblist;
	private JTextField txtFind;
	private DefaultTableModel model;
	private Department d = new Department();
	private Department department [];
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
	public DepartmentPanel() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setLayout(null);
		setBounds(200, 68, 1034, 450);
		
		JLabel btnTitle = new JLabel("Thông tin về khoa");
		btnTitle.setForeground(Color.GREEN);
		btnTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnTitle.setBounds(410, 10, 185, 26);
		add(btnTitle);
		
		JLabel lbId = new JLabel("Mã khoa");
		lbId.setForeground(new Color(46, 139, 87));
		lbId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbId.setBounds(26, 79, 72, 25);
		add(lbId);
		
		JLabel lbName = new JLabel("Tên khoa");
		lbName.setForeground(new Color(46, 139, 87));
		lbName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbName.setBounds(26, 118, 72, 25);
		add(lbName);
		
		JLabel lbPhone = new JLabel("Số điện thoại");
		lbPhone.setForeground(new Color(46, 139, 87));
		lbPhone.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbPhone.setBounds(26, 156, 124, 25);
		add(lbPhone);
		
		txtId = new JTextField();
		txtId.setBounds(187, 80, 110, 28);
		add(txtId);
		txtId.setColumns(10);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(187, 119, 219, 28);
		add(txtName);
		
		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(187, 157, 219, 28);
		add(txtPhone);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 246, 1014, 166);
		add(scrollPane);
		
		tblist = new JTable();
		tblist.setBackground(new Color(230, 230, 250));
		tblist.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		String cols[] = {"Mã khoa", "Tên khoa", "Số điện thoại"};
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
				txtName.setText(model.getValueAt(i, 1).toString());
				txtPhone.setText(model.getValueAt(i, 2).toString());
			}
		});
		
		JButton btnAdd = new JButton("Thêm");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtId.getText().isEmpty() || txtName.getText().isEmpty()
					|| txtPhone.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Các trường không được để trống !", "Lỗi", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						add(d);
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, "Lỗi khi thêm !", "Lỗi", JOptionPane.ERROR_MESSAGE);
						e2.printStackTrace();
					}
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
				if (txtId.getText().isEmpty() || txtName.getText().isEmpty()
						|| txtPhone.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Các trường không được để trống !", "Lỗi", JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							edit(d);
						} catch (Exception e2) {
							// TODO: handle exception
							JOptionPane.showMessageDialog(null, "Lỗi khi sửa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
							e2.printStackTrace();
						}
					}
			}
		});
		btnEdit.setIcon(new ImageIcon("Icon\\Actions-document-edit-icon-16.png"));
		btnEdit.setForeground(new Color(106, 90, 205));
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEdit.setBounds(698, 75, 136, 34);
		add(btnEdit);
		
		JButton btnImport = new JButton("Đọc file");
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
				try {
					delete();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				render(find());
			}
		});
		btnFind.setIcon(new ImageIcon("Icon\\search-icon-24.png"));
		btnFind.setForeground(new Color(255, 0, 255));
		btnFind.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnFind.setBounds(844, 152, 136, 34);
		add(btnFind);
		
		txtFind = new JTextField();
		txtFind.setBounds(552, 154, 282, 27);
		add(txtFind);
		txtFind.setColumns(10);
		
		JButton btnExport = new JButton("Xuất file");
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
	}
	public void clean() {
		txtId.setEditable(true);
		txtId.setText("");
		txtName.setText("");
		txtPhone.setText("");
		txtFind.setText("");
		
		int rows = model.getRowCount(); 
		for(int j = rows - 1; j >=0; j--) {
			   model.removeRow(j); 
			}
		render(view());
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
		String query = "SELECT * FROM department";
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
				String rows[] = new String[3];
				rows[0] = resultSet.getString(1);
				rows[1] = resultSet.getString(2);
				rows[2] = resultSet.getString(3);
				model.addRow(rows);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultSet = null;
			e.printStackTrace();
		}
	}
	
	
	public void add(Department d) throws SQLException {
		d.setID(txtId.getText());
		d.setName(txtName.getText());
		d.setPhone(txtPhone.getText());
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		ResultSet resultSet = null;
		Statement statement =  conn.createStatement();
		resultSet =  statement.executeQuery("SELECT id FROM department");
		int count = 0;
		while (resultSet.next()) {
			if (resultSet.getString(1).equals(d.getID())) {
				count++;
			}
		}
		if (count > 0) {
			JOptionPane.showMessageDialog(null, "Mã khoa không được trùng !", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} else {
			String sql = "INSERT INTO department(id,name,phone) VALUES(?,?,?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, d.getID());
				ps.setString(2, d.getName());
				ps.setString(3, d.getPhone());
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
	
	public void edit(Department d) throws SQLException {
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		String sql = "UPDATE department SET name=?,phone=? WHERE id=?";
		try {
			
			ps = conn.prepareStatement(sql);
			int i = tblist.getSelectedRow();
			d.setName(txtName.getText());
			d.setPhone(txtPhone.getText());
			model.setValueAt(d.getName(), i, 1);
			model.setValueAt(d.getPhone(), i, 2);
			ps.setString(1,d.getName());
			ps.setString(2, d.getPhone());
			ps.setString(3, txtId.getText());
			
			ps.executeUpdate();
			clean();
			JOptionPane.showMessageDialog(null, "Sửa thành công ! ");
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Lỗi khi sửa !", "Lỗi", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
			
	}
	
	public void delete() throws SQLException {
		int i = tblist.getSelectedRow();
		String deleteFK_class = "DELETE FROM class WHERE department_id=?";
		String deleteFK_student = "DELETE FROM student WHERE class_id=(SELECT id FROM class WHERE department_id=?)";
		String deleteFK_score = "DELETE FROM score WHERE student_id IN (SELECT id FROM student WHERE class_id=(SELECT id FROM class WHERE department_id=?))";
		String delete = "DELETE FROM department WHERE id= ? ";
		
		try {
			
			if (i>=0) {
				JOptionPane.showMessageDialog(null, "Đã xóa khoa có mã " + tblist.getValueAt(i, 0));
				//Delete FK_score_student
				ps = conn.prepareStatement(deleteFK_score);
				ps.setString(1, (String)tblist.getValueAt(i, 0));
				ps.executeUpdate();
				//Delete FK_student_class
				ps = conn.prepareStatement(deleteFK_student);
				ps.setString(1, (String)tblist.getValueAt(i, 0));
				ps.executeUpdate();
				// Delete FK_class_department
				ps = conn.prepareStatement(deleteFK_class);
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
		String find = "SELECT * FROM department ";
		String search = txtFind.getText();
		if (search.length()>0) {
			find = find + " WHERE id like '%" + search + "%' "
						+ "	OR name like '%" + search + "%' "
						+ " OR phone like '%" + search + "%'";
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
					XSSFCell cellName = row.getCell(1);
					XSSFCell cellPhone = row.getCell(2);
				
					model.addRow(new Object[] {cellId, cellName, cellPhone});
				
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
				 department = new Department[model.getRowCount()];
					for (int i=0; i<model.getRowCount(); i++) {
						department[i] = new Department();
						department[i].setID(model.getValueAt(i, 0).toString());
						department[i].setName(model.getValueAt(i, 1).toString());
						department[i].setPhone(model.getValueAt(i, 2).toString());
						oos.writeObject(department[i]);
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
        XSSFSheet spreadsheet = workbook.createSheet("Khoa");

        XSSFRow row = null;
        Cell cell = null;

        row = spreadsheet.createRow((short) 0);
        row.setHeight((short) 300);
       
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Mã khoa");
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Tên khoa");
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Số điện thoại");
      
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
		String query = "SELECT * FROM department ORDER BY id ASC";
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
