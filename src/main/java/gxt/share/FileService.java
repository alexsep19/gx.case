package gxt.share;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface FileService extends RemoteService{
	CopyModel doTestCopy(String s);
	CopyModel doTrueCopy(List<String> atms);
}
