package gxt.client.sms;
//import gxt.client.sms.ReqFactory.PostRequest.PostListLoadResultProxy;
import gxt.server.Dao;
import gxt.server.DaoService;
import gxt.server.StockLoadResultBean;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

public interface ReqFactory extends RequestFactory {
	  rcDate creRequest();
	  
	  @Service(value = Dao.class, locator = DaoService.class)
	  public interface rcDate extends RequestContext {
		  
		@ProxyFor(StockLoadResultBean.class)
		public interface StockLoadResultProxy extends ValueProxy, ListLoadResult<StockPrx> {
		    @Override
		    public List<StockPrx> getData();
		}
		Request<StockLoadResultProxy> getStocks(List<? extends SortInfo> sortInfo);
		  
//	    @ProxyFor(PostListLoadResultBean.class)
//	    public interface PostListLoadResultProxy extends ValueProxy, ListLoadResult<StockPrx> {
//	      @Override
//	      public List<StockPrx> getData();
//	    }
//		  Request<PostListLoadResultProxy> getPosts(List<? extends SortInfo> sortInfo);
	  }
}