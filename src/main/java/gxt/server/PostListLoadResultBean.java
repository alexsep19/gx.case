package gxt.server;


import java.util.List;

import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class PostListLoadResultBean extends ListLoadResultBean<Stock>{
	public PostListLoadResultBean() {	       
	    }
	    public PostListLoadResultBean(List<Stock> list) {
	      super(list);
	    }
	  
}
