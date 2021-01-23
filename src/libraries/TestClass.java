package libraries;

public class TestClass {

	public static void main(String[] args) {
		Library library=new Library();
		
		//添加书本，作者信息的方法
		library.insertauthor("莫言","China");
		library.insertbook("红高粱","T",library.findauthorid("莫言"));//（书名，出借状态，作者id）
		
		//删除书本信息的方法(通过书本序号)
		library.deletebook(1);
				
				//将图书信息保存至数据库
				/*
				library.insertauthor("余华","China");
				library.insertauthor("村上春树","Japan");
				library.insertauthor("芥川龙之介","Japan");
				library.insertauthor("乔治奥尔德","England");
				library.insertauthor("阿道司·赫胥黎","England");
				library.insertauthor("阿尔贝加缪","France");
				library.insertbook("活着","F",library.findauthorid("余华"));
				library.insertbook("兄弟","T",library.findauthorid("余华"));
				library.insertbook("弃猫","T",library.findauthorid("村上春树"));
				library.insertbook("罗生门","F",library.findauthorid("芥川龙之介"));
				library.insertbook("鼠疫","F",library.findauthorid("阿尔贝加缪"));
				library.insertbook("1984","T",library.findauthorid("乔治奥尔德"));
				library.insertbook("美丽新世界","F",library.findauthorid("阿道司·赫胥黎"));
				*/
				
		//控制台使用，输入"out"结束当前功能，使用下一个功能，支持查询多本图书信息
		System.out.println("=功能一览=");
		System.out.println("1.查看所有馆藏图书书名 2.查询书籍相关信息 3.借阅图书(更新) 4.归还图书(更新)");
		System.out.println("------------------------------------------------------------------------");
		library.selectallbooksname();
		library.selectmessage();
		library.lend();
		library.returnbook();
	}

}
