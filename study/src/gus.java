import java.util.Scanner;
public class gus{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("-----��ȭ��Ϸ-----");
		System.out.println("���ȭ��1.���� 2.ʯͷ 3.����");
		String marks="ȭͷ";
		String marks2="ȭͷ";
		int person=in.nextInt();
		switch(person){
			case 1:
			marks="����";
			break;
			case 2:
			marks="ʯͷ";
			break;
			case 3:
			marks="��";
			break;
		}
		int computer=(int)(Math.random()*3)+1;
		switch(computer){
			case 1:
			marks2="����";
			break;
			case 2:
			marks2="ʯͷ";
			break;
			case 3:
			marks2="��";
			break;
		}
		if(person==computer){
		System.out.println("������ǣ�"+marks+" ���Գ�����"+marks2+"=_=ƽ��");
		}else if(person==1&&computer==3||person==2&&computer==1||person==3&&computer==2){
			System.out.println("������ǣ�"+marks+" ���Գ�����"+marks2+" ^-^��ϲ��Ӯ��");
		}else{
			System.out.println("������ǣ�"+marks+" ���Գ�����"+marks2+" -_-���ź�������");
		}
	}
}
