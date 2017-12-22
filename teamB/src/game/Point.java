package game;

public class Point {
	int Tani =129;
	void Genten(){
		Tani=Tani-1;
		Show();
	}
	void Katen(int item){
		Tani=Tani+item;
		Show();
	}
	void Show(){
		System.out.println(Tani);
	}
}
