package gxt.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

	public class Stock implements Serializable {
		private static final long serialVersionUID = 8892171172689518194L;
		private Integer id;
	    private String name;
		private static int COUNTER = 0;
		  
    public Stock() {
	    this.id = Integer.valueOf(COUNTER++);
	  }
    
    public Stock(String name) {
        this();
        this.name = name;
      }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public static List<Stock> getStocks() {
	    List<Stock> stocks = new ArrayList<Stock>();

	    stocks.add(new Stock("Apple Inc."));
	    stocks.add(new Stock("Cisco Systems, Inc."));
	    stocks.add(new Stock("Google Inc."));
	    stocks.add(new Stock("Intel Corporation"));
	    return stocks;

	  }

}
