package libraries;

public class TestClass {

	public static void main(String[] args) {
		Library library=new Library();
		
		//����鱾��������Ϣ�ķ���
		library.insertauthor("Ī��","China");
		library.insertbook("�����","T",library.findauthorid("Ī��"));//������������״̬������id��
		
		//ɾ���鱾��Ϣ�ķ���(ͨ���鱾���)
		library.deletebook(1);
				
				//��ͼ����Ϣ���������ݿ�
				/*
				library.insertauthor("�໪","China");
				library.insertauthor("���ϴ���","Japan");
				library.insertauthor("�洨��֮��","Japan");
				library.insertauthor("���ΰ¶���","England");
				library.insertauthor("����˾��������","England");
				library.insertauthor("����������","France");
				library.insertbook("����","F",library.findauthorid("�໪"));
				library.insertbook("�ֵ�","T",library.findauthorid("�໪"));
				library.insertbook("��è","T",library.findauthorid("���ϴ���"));
				library.insertbook("������","F",library.findauthorid("�洨��֮��"));
				library.insertbook("����","F",library.findauthorid("����������"));
				library.insertbook("1984","T",library.findauthorid("���ΰ¶���"));
				library.insertbook("����������","F",library.findauthorid("����˾��������"));
				*/
				
		//����̨ʹ�ã�����"out"������ǰ���ܣ�ʹ����һ�����ܣ�֧�ֲ�ѯ�౾ͼ����Ϣ
		System.out.println("=����һ��=");
		System.out.println("1.�鿴���йݲ�ͼ������ 2.��ѯ�鼮�����Ϣ 3.����ͼ��(����) 4.�黹ͼ��(����)");
		System.out.println("------------------------------------------------------------------------");
		library.selectallbooksname();
		library.selectmessage();
		library.lend();
		library.returnbook();
	}

}
