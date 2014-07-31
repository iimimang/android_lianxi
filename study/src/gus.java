import java.util.Scanner;
public class gus{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("-----猜拳游戏-----");
		System.out.println("请出拳（1.剪刀 2.石头 3.布）");
		String marks="拳头";
		String marks2="拳头";
		int person=in.nextInt();
		switch(person){
			case 1:
			marks="剪刀";
			break;
			case 2:
			marks="石头";
			break;
			case 3:
			marks="布";
			break;
		}
		int computer=(int)(Math.random()*3)+1;
		switch(computer){
			case 1:
			marks2="剪刀";
			break;
			case 2:
			marks2="石头";
			break;
			case 3:
			marks2="布";
			break;
		}
		if(person==computer){
		System.out.println("你出的是："+marks+" 电脑出的是"+marks2+"=_=平局");
		}else if(person==1&&computer==3||person==2&&computer==1||person==3&&computer==2){
			System.out.println("你出的是："+marks+" 电脑出的是"+marks2+" ^-^恭喜你赢了");
		}else{
			System.out.println("你出的是："+marks+" 电脑出的是"+marks2+" -_-很遗憾你输了");
		}
	}
}
