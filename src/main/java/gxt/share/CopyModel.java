package gxt.share;

import java.io.Serializable;
import java.util.ArrayList;

public class CopyModel implements Serializable{
	private static final long serialVersionUID = -3290419554421275190L;
	ArrayList<String> mess;// = new ArrayList<String>(); 
	ArrayList<String> model;// = new ArrayList<Model>();

	public CopyModel(){
		model = new ArrayList<String>();
		mess = new ArrayList<String>();
	   }

	public void AddModel(String Id1, String Id2){
//		if (model == null) model = new ArrayList<Model>();
		model.add(Id1 +" "+ Id2);
	   }
	public boolean isExists(String Key1, String Key2){
		for(String it: model){
		  if (it.substring(0, it.indexOf(" ")).equals(Key1)||it.substring(it.indexOf(" ")+1).equals(Key2)||
			  Key1.equals(Key2)) return true;
		}
		return false;
	}
	
	public void AddMess(String Mess){
		mess.add(Mess);
	}
	public ArrayList<String> getMess() {
		return mess;
	}

	public void setMess(ArrayList<String> mess) {
		this.mess = mess;
	}

	public ArrayList<String> getModel() {
		return model;
	}

	public void setModel(ArrayList<String> model) {
		this.model = model;
	}

	
//	public class Model {
//	   String id1;
//	   String id2;
//	   public Model(String Id1, String Id2){
//		 id1 = Id1;
//		 id2 = Id2;
//	   }
//	   
//	   public String getId1() {
//		return id1;
//	   }
//	   public void setId1(String id1) {
//		this.id1 = id1;
//   	   }
//	   public String getId2() {
//		return id2;
//	   }
//	   public void setId2(String id2) {
//		this.id2 = id2;
//       }
//	}
}

//public class CopyModel {
//   String id1;
//   String id2;
//   String name;
//   
//   public CopyModel(String Id1, String Id2, String Name){
//	 id1 = Id1;
//	 id2 = Id2;
//	 name = Name;
//   }
//   
//   public String getId1() {
//	return id1;
//}
//public void setId1(String id1) {
//	this.id1 = id1;
//}
//public String getId2() {
//	return id2;
//}
//public void setId2(String id2) {
//	this.id2 = id2;
//}
//public String getName() {
//	return name;
//}
//public void setName(String name) {
//	this.name = name;
//}
//}
