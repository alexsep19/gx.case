package gxt.client;

import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

public class PanTree extends ContentPanel{
	
	public PanTree(){
	  getHeader().addStyleName("txt_center");
	  setHeadingText("Tree");
	  addStyleName("margin-10");
	  setPixelSize(300, 400);
	
	  VerticalLayoutContainer con = new VerticalLayoutContainer();
      add(con);

      
      
	}
}
