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
		System.out.println("是否查看馆藏图书目一览(输入是&否):");
		if(scan.next().equals("是")) {
			for(Book book:findBookList()) {
				System.out.println("《"+book.bookName+"》");
			}System.out.println("------------------------------------------------------------------------");			
		}else System.out.println("------------------------------------------------------------------------");
	}
	
	public static void selectmessage() {

			Scanner scan = new Scanner(System.in);
			System.out.println("查询书籍相关信息(书名查询):");
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
				System.out.println("本馆无此藏书");
		}
	}
	
	public static void lend() {
		String sql="update `bookmessage`  SET  `islend`='T' where `name`=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			Scanner scan = new Scanner(System.in);
			System.out.println("借阅图书(通过书名借阅):");
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
								System.out.println("[此书已借出!]");
							}else {
								pstmt.setString(1, "'"+book_name+"'");
								pstmt.executeUpdate();
								System.out.println("[记录借阅成功!]");
							}
						}
					}	
				}
				if(sign) {
					System.out.println("=图书馆中没有同名书籍=");
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
			System.out.println("归还图书(通过书名归还):");
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
								System.out.println("[此书未被借出!]");
							}else {
								pstmt.setString(1, "'"+book_name+"'");
								pstmt.executeUpdate();
								System.out.println("[记录归还成功!]");
							}
						}
					}	
				}
				if(sign) {
					System.out.println("=图书馆中没有同名书籍=");
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
		
		//添加书本，作者信息的方法
		insertauthor("莫言","China");
		insertbook("红高粱","T",findauthorid("莫言"));//（书名，出借状态，作者id）
		
		//删除书本信息的方法(通过书本序号)
		deletebook(1);
		
		//将图书信息保存至数据库
		/*
		insertauthor("余华","China");
		insertauthor("村上春树","Japan");
		insertauthor("芥川龙之介","Japan");
		insertauthor("乔治奥尔德","England");
		insertauthor("阿道司·赫胥黎","England");
		insertauthor("阿尔贝加缪","France");
		insertbook("活着","F",findauthorid("余华"));
		insertbook("兄弟","T",findauthorid("余华"));
		insertbook("弃猫","T",findauthorid("村上春树"));
		insertbook("罗生门","F",findauthorid("芥川龙之介"));
		insertbook("鼠疫","F",findauthorid("阿尔贝加缪"));
		insertbook("1984","T",findauthorid("乔治奥尔德"));
		insertbook("美丽新世界","F",findauthorid("阿道司·赫胥黎"));
		*/
		
		//控制台使用，输入"out"结束当前功能，使用下一个功能，支持查询多本图书信息
		System.out.println("=功能一览=");
		System.out.println("1.查看所有馆藏图书书名 2.查询书籍相关信息 3.借阅图书(更新) 4.归还图书(更新)");
		System.out.println("------------------------------------------------------------------------");
		selectallbooksname();
		selectmessage();
		lend();
		returnbook();
	}

}
