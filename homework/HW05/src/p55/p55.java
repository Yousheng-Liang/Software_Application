package p55;

public class p55 {

	public static void main(String[] args) {
		Company cmp = new Company();
		
		Driver dvr1 = new Driver(cmp);
		dvr1.start();
		
		Driver dvr2 = new Driver(cmp);
		dvr2.start();
	}
}
class Company
{
	private int sum = 0;
	public synchronized void add(int a) {
		int tmp = sum;
		System.out.println("�ثe���X�p���B�O"+tmp+"��");
		System.out.println("�Ȩ�"+a+"���F");
		tmp = tmp + a;
		System.out.println("�X�p���B�O"+tmp+"��");
		sum = tmp;
	}
}
class Driver extends Thread
{
	private Company comp;
	public Driver(Company c) {
		comp = c;
	}	
	public void run() {
		for(int i=0;i<3;i++) {
			comp.add(50);
		}
	}
}