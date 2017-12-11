package gxt.client.sms;


import gxt.server.Stock;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;


@ProxyFor(Stock.class)
public interface StockPrx extends ValueProxy {
	  Integer getId();
	  String getName();

}
