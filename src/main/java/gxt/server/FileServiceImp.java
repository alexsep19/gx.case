package gxt.server;

import java.util.ArrayList;
import java.util.List;

import gxt.share.CopyModel;
import gxt.share.FileService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class FileServiceImp extends RemoteServiceServlet implements FileService{
	private static final long serialVersionUID = 861771558274381931L;

	public CopyModel doTestCopy(String s) {
		CopyModel cm = new CopyModel();
//		String ret = "";
		String[] its = s.split(",");
		for(String it: its){
//  		  System.out.println("it = |" + it.trim()+"|");
		  String[] arr = it.trim().split(" +");
//		  System.out.println("arr[0] = |" + arr[0]+"|");
//		  System.out.println("arr[1] = |" + arr[1]+"|");
		  if (arr[0].equals("0")) cm.AddMess(arr[0] + "  ываававы");
//			  ret = ret + "0/" + "/фвавыа" + "]";
		  else {
			  if (cm.isExists(arr[0], arr[1])) cm.AddMess(it + "  дублируется");
			  else cm.AddModel(arr[0].trim(), arr[1].trim()); 
		  }
//			  ret = ret + arr[0].trim() + "/" + arr[1].trim() + "]";
		}
//		System.out.println("FileServiceImp" );
	    return cm;
	}
	
	public CopyModel doTrueCopy(List<String> atms) {
		CopyModel cm = new CopyModel();
		for(String it: atms){
			cm.AddMess(it + " скопирован");
		}
	    return cm;
	}
}
