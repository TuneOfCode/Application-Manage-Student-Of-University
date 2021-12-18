package UI;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
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

public class StatisticalPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tblist;
	private JTextField txtFind;
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
	public StatisticalPanel() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setLayout(null);
		setBounds(200, 68, 1116, 450);
		
		JLabel lblThngKim = new JLabel("Thống kê điểm tích lũy của tất cả sinh viên");
		lblThngKim.setForeground(new Color(0, 128, 128));
		lblThngKim.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblThngKim.setBounds(319, 20, 434, 26);
		add(lblThngKim);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 205, 1014, 235);
		add(scrollPane);
		
		tblist = new JTable();
		tblist.setBackground(new Color(230, 230, 250));
		tblist.setFont(new Font("Tahoma", Font.PLAIN, 12));
		String cols[] = {"Mã sinh viên", "Họ và tên đệm", "Tên", "Mã khoa", "Mã lớp", "Khóa học", "Tổng số tín chỉ"
					,"Tổng điểm hệ 10", "Tổng điểm hệ 4"};
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
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 68, 1014, 2);
		add(separator);
		
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
		btnImport.setBounds(140, 152, 136, 34);
		add(btnImport);
		
		JButton btnExcel = new JButton("Xuất Excel");
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportExcel(tblist);
			}
		});
		btnExcel.setIcon(new ImageIcon("Icon\\excel.png"));
		btnExcel.setForeground(new Color(50, 205, 50));
		btnExcel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExcel.setBounds(286, 152, 136, 34);
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
		btnFind.setBounds(849, 128, 136, 34);
		add(btnFind);
		
		txtFind = new JTextField();
		txtFind.setBounds(545, 130, 282, 33);
		add(txtFind);
		txtFind.setColumns(10);
		
		JButton btnClean = new JButton("Làm mới");
		btnClean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtFind.setText("");
			}
		});
		btnClean.setIcon(new ImageIcon("Icon\\logout-icon-16.png"));
		btnClean.setForeground(new Color(0, 128, 128));
		btnClean.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClean.setBounds(140, 105, 136, 34);
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
		btnSort.setBounds(286, 105, 136, 34);
		add(btnSort);
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
		String query = "SELECT 	s.id, s.firstname, s.lastname,\r\n"
				+ "		d.id, c.id,s.academic_year,\r\n"
				+ "		SUM(sb.credits) as `Tổng số tín chỉ đã học`,\r\n"
				+ "		ROUND(SUM(sc.Coefficient_10 * sb.credits) / SUM(sb.credits), 2),\r\n"
				+ "        ROUND(SUM(sc.Coefficient_4 * sb.credits) / SUM(sb.credits), 2) \r\n"
				+ "FROM department as d\r\n"
				+ "	JOIN class as c on c.department_id = d.id\r\n"
				+ "	JOIN student as s on s.class_id = c.id\r\n"
				+ "	JOIN score as sc on sc.student_id = s.id\r\n"
				+ "    JOIN subject as sb on sc.subject_id = sb.id\r\n"
				+ "GROUP BY s.id, s.firstname, s.lastname, d.id, c.id";
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
				String rows[] = new String[9];
				rows[0] = resultSet.getString(1);
				rows[1] = resultSet.getString(2);
				rows[2] = resultSet.getString(3);
				rows[3] = resultSet.getString(4);
				rows[4] = resultSet.getString(5);
				rows[5] = resultSet.getString(6);
				rows[6] = resultSet.getString(7);
				rows[7] = resultSet.getString(8);
				rows[8] = resultSet.getString(9);
				model.addRow(rows);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultSet = null;
			e.printStackTrace();
		}
	}
	
	public ResultSet find() {
		ResultSet resultSet = null;
		int rows = model.getRowCount(); 
		for(int j = rows - 1; j >=0; j--) {
		   model.removeRow(j); 
		}
		String find = "SELECT 	s.id, s.firstname, s.lastname,\r\n"
				+ "		d.id, c.id,s.academic_year,\r\n"
				+ "		SUM(sb.credits) as `Tổng số tín chỉ đã học`,\r\n"
				+ "		ROUND(SUM(sc.Coefficient_10 * sb.credits) / SUM(sb.credits), 2),\r\n"
				+ "        ROUND(SUM(sc.Coefficient_4 * sb.credits) / SUM(sb.credits), 2) \r\n"
				+ "FROM department as d\r\n"
				+ "	JOIN class as c on c.department_id = d.id\r\n"
				+ "	JOIN student as s on s.class_id = c.id\r\n"
				+ "	JOIN score as sc on sc.student_id = s.id\r\n"
				+ " JOIN subject as sb on sc.subject_id = sb.id\r\n";
		String search = txtFind.getText();
		if (search.length()>0) {
			find = find + " WHERE s.id like '%" + search + "%' "
						+ "	OR s.firstname like '%" + search + "%' "
						+ "	OR s.lastname like '%" + search + "%' "
						+ "	OR d.id like '%" + search + "%' "
						+ " OR c.id like '%" + search + "%' \r\n"
						+ "GROUP BY s.id, s.firstname, s.lastname, d.id, c.id";
		} else {
			rows = model.getRowCount(); 
			for(int j = rows - 1; j >=0; j--) {
			   model.removeRow(j); 
			}
			find = "SELECT 	s.id, s.firstname, s.lastname,\r\n"
					+ "		d.id, c.id,s.academic_year,\r\n"
					+ "		SUM(sb.credits) as `Tổng số tín chỉ đã học`,\r\n"
					+ "		ROUND(SUM(sc.Coefficient_10 * sb.credits) / SUM(sb.credits), 2),\r\n"
					+ "        ROUND(SUM(sc.Coefficient_4 * sb.credits) / SUM(sb.credits), 2) \r\n"
					+ "FROM department as d\r\n"
					+ "	JOIN class as c on c.department_id = d.id\r\n"
					+ "	JOIN student as s on s.class_id = c.id\r\n"
					+ "	JOIN score as sc on sc.student_id = s.id\r\n"
					+ "    JOIN subject as sb on sc.subject_id = sb.id\r\n"
					+ "GROUP BY s.id, s.firstname, s.lastname, d.id, c.id";
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
					XSSFCell cellLName= row.getCell(2);
					XSSFCell cellIdDepartment = row.getCell(3);
					XSSFCell cellIdClass = row.getCell(4);
					XSSFCell cellAcademic = row.getCell(5);
					XSSFCell cellCredits = row.getCell(6);
					XSSFCell cell10 = row.getCell(7);
					XSSFCell cell4 = row.getCell(8);
				
					model.addRow(new Object[] {cellId, cellFName, cellLName, cellIdDepartment, cellIdClass, cellAcademic,
							cellCredits, cell10, cell4});
				
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
	
	public void exportExcel(JTable table) {
		 FileOutputStream fos = null;
		 BufferedOutputStream bos = null;
		 JFileChooser chooser = new JFileChooser();
		 
		 chooser.setDialogTitle("Save Excel Files");
		 FileNameExtensionFilter fnef = new FileNameExtensionFilter("Excel File(*.xls, *.xlsx)", "xls", "xlsx");
		 chooser.setFileFilter(fnef);
		 int excelChooser = chooser.showSaveDialog(chooser);
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Thống kê điểm trung bình tích lũy");

        XSSFRow row = null;
        Cell cell = null;

        row = spreadsheet.createRow((short) 0);
        row.setHeight((short) 300);
       
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Mã sinh viên");
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Họ và tên đệm");
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên sinh viên");
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Mã khoa");
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Mã lớp");
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Khóa học");
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Tổng số tín chỉ tích lũy");
        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Tổng điểm hệ 10 tích lũy");
        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Tổng điểm hệ 4 tích lũy");
      
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
	       				cells = rows.createCell(7, CellType.STRING);
	       				cells.setCellValue(model.getValueAt(i, 7).toString());
	       				cells = rows.createCell(8, CellType.STRING);
	       				cells.setCellValue(model.getValueAt(i, 8).toString());

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
		String query = "SELECT 	s.id, s.firstname, s.lastname,\r\n"
				+ "		d.id, c.id,s.academic_year,\r\n"
				+ "		SUM(sb.credits) as `Tổng số tín chỉ đã học`,\r\n"
				+ "		ROUND(SUM(sc.Coefficient_10 * sb.credits) / SUM(sb.credits), 2),\r\n"
				+ "        ROUND(SUM(sc.Coefficient_4 * sb.credits) / SUM(sb.credits), 2) \r\n"
				+ "FROM department as d\r\n"
				+ "	JOIN class as c on c.department_id = d.id\r\n"
				+ "	JOIN student as s on s.class_id = c.id\r\n"
				+ "	JOIN score as sc on sc.student_id = s.id\r\n"
				+ "    JOIN subject as sb on sc.subject_id = sb.id\r\n"
				+ "GROUP BY s.id, s.firstname, s.lastname, d.id, c.id\r\n"
				+ "ORDER BY s.lastname ASC";
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

