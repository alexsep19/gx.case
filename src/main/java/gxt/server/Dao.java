package gxt.server;


import java.util.ArrayList;
import java.util.List;

import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.SortInfoBean;

public class Dao {
	private List<Stock> posts;

	  public Dao() {
	    setPosts(Stock.getStocks());
	}

    public StockLoadResultBean getStocks(List<SortInfoBean> sortInfo){
    	return new StockLoadResultBean(Stock.getStocks());
    }
	  
	public List<Stock> getPosts() {
		return posts;
	}

	public void setPosts(List<Stock> posts) {
		this.posts = posts;
	}

}
