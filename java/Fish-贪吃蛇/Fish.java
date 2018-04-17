package Fish;

public class Fish {
	   public static void main(String[] args) {
	       FishModel model = new FishModel(50,50);
	       FishControl control = new FishControl(model);
	       FishView view = new FishView(model,control);
	       model.addObserver(view);
	      
	       (new Thread(model)).start();
	   }


}
