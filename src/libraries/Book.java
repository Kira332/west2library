package libraries;

public class Book {
	int bookNo;
	String bookName;
	String bookAuthorname;
	String bookislend;
	String bookAuthorNationality;
	Book(){
		
	}
	Book(int No,String name,String islend,String authorname,String authornaionality){
		this.bookNo=No;
		this.bookAuthorname=authorname;
		this.bookName=name;
		this.bookislend=islend;
		this.bookAuthorNationality=authornaionality;
	}
	public int getBookNo() {
		return bookNo;
	}
	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthorname() {
		return bookAuthorname;
	}
	public void setBookAuthorname(String bookAuthorname) {
		this.bookAuthorname = bookAuthorname;
	}
	public String getBookislend() {
		return bookislend;
	}
	public void setBookislend(String bookislend) {
		this.bookislend = bookislend;
	}
	public String getBookAuthorNationality() {
		return bookAuthorNationality;
	}
	public void setBookAuthorNationality(String bookAuthorNationality) {
		this.bookAuthorNationality = bookAuthorNationality;
	}
	
	
	
}
