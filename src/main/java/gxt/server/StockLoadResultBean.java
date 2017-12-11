package gxt.server;

import java.util.List;
import com.sencha.gxt.data.shared.loader.ListLoadResultBean;

public class StockLoadResultBean extends ListLoadResultBean<Stock>{
	private static final long serialVersionUID = 2658100424768095095L;
	public StockLoadResultBean() {
	}
	public StockLoadResultBean(List<Stock> list) {
	      super(list);
	    }
  public Integer getVersion() { return 1;}
}
