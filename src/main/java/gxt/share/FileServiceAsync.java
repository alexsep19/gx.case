package gxt.share;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FileServiceAsync {
  void doTestCopy(String s, AsyncCallback<CopyModel> callback); 
  void doTrueCopy(List<String> atms, AsyncCallback<CopyModel> callback);
}
