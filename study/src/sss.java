
class user_dianchi {

	double power = 1.0 ;
	double hour_power = 0.1;
	public boolean  user(double d){
		if(d<this.power){
			this.power=this.power - d ;
			System.out.println("����"+this.power+"�ĵ���");
			return true;	
		}else{
			System.out.println("û����");
			return false;	
		}
	
	}
	
	public  void add(int hour ){
		if(this.power<1.){
			
			this.power =this.power + hour*this.hour_power ;
		}else{
			
			System.out.println("����Ѿ����˲���Ҫ��磡");
		}
			
	}	
}
 



public class sss {
	  
	public static void main(String[] args ){
		
		//int a=(int) (Math.random()*5)+1;
		//System.out.println(a);
		user_dianchi aa=new user_dianchi();
		aa.user(1);
		
	}
}