package gxt.client;

import gxt.client.sms.PanSms;
import gxt.client.sms.ReqFactory;

import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class NavPan extends ContentPanel{
    FlowLayoutContainer playCont = null; 
    VBoxLayoutContainer bc = null;
    String login = "";
    TextButton  bTree;
    TextButton bTools, bSms;
    PanTree panTree = null;
    PanCopy panCopy = null;
    PanSms panSms = null;
//    PanTools panTools = null;
    Viewport viewport = null;
    
//    public void setActive(String Login, String Role){
//	login = Login;
//        bReps.setEnabled(true);
//        if (Role.indexOf("A") >= 0) bTools.setVisible(true);
//        if (panReps == null) panReps = new PanReps(fct, login, viewport);
//	ShowPan(panReps);
//    }
    
	public NavPan(final FlowLayoutContainer cont,  final ReqFactory rf){
	playCont = cont;
	
	setHeadingText("Навигация");
	getHeader().addStyleName("txt_center");
        bc = new VBoxLayoutContainer();
        bc.setPadding(new Padding(1));
        bc.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
        bTree = new TextButton("Tree");
 	    bTree.addSelectHandler( new SelectHandler(){
	         @Override
 	         public void onSelect(SelectEvent event) {
	             if (panTree == null) panTree = new PanTree();
		         ShowPan(panTree);  }});
        bc.add(bTree, new BoxLayoutData(new Margins(0, 0, 1, 0)));

        bSms = new TextButton("Sms");
 	    bSms.addSelectHandler( new SelectHandler(){
	         @Override
 	         public void onSelect(SelectEvent event) {
	             if (panSms == null) panSms = new PanSms(rf);
		         ShowPan(panSms);  }});
        bc.add(bSms, new BoxLayoutData(new Margins(0, 0, 1, 0)));

        bc.add(new TextButton("Файл", new SelectHandler(){
	        @Override
	        public void onSelect(SelectEvent event) {
		           if (panCopy == null) panCopy = new PanCopy();
		           ShowPan(panCopy);
	          }}), new BoxLayoutData(new Margins(0)));
        
//        bTools = new TextButton("Настройки");
//        bTools.setVisible(false);
//        bTools.addSelectHandler(new SelectHandler(){
//  	      @Override
//	     public void onSelect(SelectEvent event) {
//	          if (panTools == null) panTools = new PanTools(fct);
//	       ShowPan(panTools);
//	      }}
//        );
//        bc.add( bTools, new BoxLayoutData(new Margins(0)));
        
        add(bc);
        if (panSms == null) panSms = new PanSms(rf);
        ShowPan(panSms);

    }
    
    private void ShowPan(ContentPanel pan){
      playCont.clear();
	  playCont.add(pan);
    }

}
