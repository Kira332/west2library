package libraries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {

	public static Connection getConnection() {
		Connection conn=null;
		try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library?characterEncoding=UTF-8","Kira","Kira@20021339");
        } catch (ClassNotFoundException e) { 				
            e.printStackTrace();
        }catch (SQLException e) {							
            e.printStackTrace();
        }
		return conn;
	}

	
	public static void insertauthor(String name,String nationality) {
		String sql="insert into authormessage(name,nationality) values(?,?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn=getConnection();
			pstmt=(PreparedStatement)conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, nationality);
			pstmt.executeUpdate();	
		}catch(SQLException e) {
			 e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static int findauthorid(String authorname){
		String sql = "SELECT id FROM authormessage WHERE `name`=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int authorid =0;
		try{
			conn = getConnection();
			
			pstmt=(PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1,authorname);
			rs = (ResultSet) pstmt.executeQuery();
			while (rs.next()){
			authorid = rs.getInt("id");
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
			pstmt.close();
			conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return authorid;
	}
	
	public static void insertbook(String name,String islend,int authorid) {
		String sql="insert into bookmessage(name,islend,authorid) values(?,?,?)";
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn=getConnection();
			pstmt=(PreparedStatement)conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, islend);
			pstmt.setInt(3, authorid);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			 e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void deletebook(int no) {
		String sql="delete from bookmessage where No=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		try {
			conn=getConnection();
			pstmt=(PreparedStatement)conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			 e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static List<Book> findBookList(){
		String sql = "SELECT b.No,b.name,b.islend,a.name AS authorname,a.nationality AS authornationality FROM bookmessage b,authormessage a WHERE b.authorid=a.id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<Book>();
		try {
			conn = getConnection();
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = (ResultSet) pstmt.executeQuery();
			while (rs.next()){
				int bookNo = rs.getInt("No");
				String bookName = rs.getString("name");
				String bookIslend = rs.getString("islend");
				String bookAuthorName=rs.getString("authorname");
				String bookAuthorNationality = rs.getString("authornationality");
				Book book = new Book(bookNo,bookName,bookIslend,bookAuthorName,bookAuthorNationality);
				bookList.add(book);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
			pstmt.close();
			conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bookList;
	}
	
	public static void selectallbooksname() {
		Scanner scan = new Scanner(System.in);
		System.out.println("�Ƿ�鿴�ݲ�ͼ��Ŀһ��(������&��):");
		if(scan.next().equals("��")) {
			for(Book book:findBookList()) {
				System.out.println("��"+book.bookName+"��");
			}System.out.println("------------------------------------------------------------------------");			
		}else System.out.println("------------------------------------------------------------------------");
	}
	
	public static void selectmessage() {

			Scanner scan = new Scanner(System.in);
			System.out.println("��ѯ�鼮�����Ϣ(������ѯ):");
			String book_name;
			boolean sign=true;
			while(scan.hasNext()) {
				sign=true;
				if((book_name=scan.next()).equals("out")) {
					sign=false;
					System.out.println("------------------------------------------------------------------------");
					break;
				}else {
					for(Book book:findBookList()) {
						if(book.bookName.equals(book_name)) {
							sign=false;
							System.out.println("No:"+book.bookNo+"   name:<"+book.bookName+">"+"   author:["+book.bookAuthorNationality+"]"+book.bookAuthorname+"   islend:"+book.bookislend);
						}
					}
				}
			}
			if(sign) {
				System.out.println("�����޴˲���");
		}
	}
	
	public static void lend() {
		String sql="update `bookmessage`  SET  `islend`='T' where `name`=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			Scanner scan = new Scanner(System.in);
			System.out.println("����ͼ��(ͨ����������):");
			String book_name;
			pstmt=(PreparedStatement)conn.prepareStatement(sql);
			while(scan.hasNext()) {
				boolean sign=true;
				if((book_name=scan.next()).equals("out")) {
					sign=false;
					System.out.println("------------------------------------------------------------------------");
					break;
					
				}else {
					for(Book book:findBookList()) {
						if(book.bookName.equals(book_name)) {
							sign=false;
							if(book.bookislend.equals("T")) {
								System.out.println("[�����ѽ��!]");
							}else {
								pstmt.setString(1, "'"+book_name+"'");
								pstmt.executeUpdate();
								System.out.println("[��¼���ĳɹ�!]");
							}
						}
					}	
				}
				if(sign) {
					System.out.println("=ͼ�����û��ͬ���鼮=");
				}
			}
		}catch(SQLException e) {
			 e.printStackTrace();
		}finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void returnbook() {
		String sql="update `bookmessage`  SET  `islend`='F' where `name`=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			Scanner scan = new Scanner(System.in);
			System.out.println("�黹ͼ��(ͨ�������黹):");
			String book_name;
			pstmt=(PreparedStatement)conn.prepareStatement(sql);
			while(scan.hasNext()) {
				boolean sign=true;
				if((book_name=scan.next()).equals("out")) {
					sign=false;
					System.out.println("------------------------------------------------------------------------");
					break;
					
				}else {
					for(Book book:findBookList()) {
						if(book.bookName.equals(book_name)) {
							sign=false;
							if(book.bookislend.equals("F")) {
								System.out.println("[����δ�����!]");
							}else {
								pstmt.setString(1, "'"+book_name+"'");
								pstmt.executeUpdate();
								System.out.println("[��¼�黹�ɹ�!]");
							}
						}
					}	
				}
				if(sign) {
					System.out.println("=ͼ�����û��ͬ���鼮=");
				}
			}scan.close();
		}catch(SQLException e) {
			 e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		//����鱾��������Ϣ�ķ���
		insertauthor("Ī��","China");
		insertbook("�����","T",findauthorid("Ī��"));//������������״̬������id��
		
		//ɾ���鱾��Ϣ�ķ���(ͨ���鱾���)
		deletebook(1);
		
		//��ͼ����Ϣ���������ݿ�
		/*
		insertauthor("�໪","China");
		insertauthor("���ϴ���","Japan");
		insertauthor("�洨��֮��","Japan");
		insertauthor("���ΰ¶���","England");
		insertauthor("����˾��������","England");
		insertauthor("����������","France");
		insertbook("����","F",findauthorid("�໪"));
		insertbook("�ֵ�","T",findauthorid("�໪"));
		insertbook("��è","T",findauthorid("���ϴ���"));
		insertbook("������","F",findauthorid("�洨��֮��"));
		insertbook("����","F",findauthorid("����������"));
		insertbook("1984","T",findauthorid("���ΰ¶���"));
		insertbook("����������","F",findauthorid("����˾��������"));
		*/
		
		//����̨ʹ�ã�����"out"������ǰ���ܣ�ʹ����һ�����ܣ�֧�ֲ�ѯ�౾ͼ����Ϣ
		System.out.println("=����һ��=");
		System.out.println("1.�鿴���йݲ�ͼ������ 2.��ѯ�鼮�����Ϣ 3.����ͼ��(����) 4.�黹ͼ��(����)");
		System.out.println("------------------------------------------------------------------------");
		selectallbooksname();
		selectmessage();
		lend();
		returnbook();
	}

}
