package gxt.client;

//import gxt.shared.FieldVerifier;
import gxt.client.sms.ReqFactory;

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Status;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class startPoint implements EntryPoint {
    private static Logger rootLogger = Logger.getLogger("");
    FlowLayoutContainer playCont = new FlowLayoutContainer();
    BorderLayoutData westData;
    String role = "?";
    
    @Override
    public void onModuleLoad() {
	  final ReqFactory rf = GWT.create(ReqFactory.class);
  	  rf.initialize(new SimpleEventBus());

//	rootLogger.addHandler(new ConsoleLogHandler());
//	ToolBar botBar = new ToolBar();
	final Status userState = new Status();
	userState.setWidth(450);
	
	Viewport viewport = new Viewport();
	final BorderLayoutContainer blc = new BorderLayoutContainer();
	playCont.getScrollSupport().setScrollMode(ScrollMode.AUTO);
	blc.setCenterWidget(playCont);

	westData = new BorderLayoutData(.20);
	westData.setMargins(new Margins(0, 5, 5, 5));
	westData.setCollapsible(true);
	westData.setSplit(true);
	westData.setCollapseMini(true);
	final NavPan navPanel = new NavPan(playCont,  rf);
	blc.setWestWidget(navPanel, westData);
	
	BorderLayoutData southData = new BorderLayoutData(25);
	southData.setMargins(new Margins(0));
	southData.setCollapsible(false);
	southData.setSplit(false);
	southData.setCollapseMini(false);

	viewport.setWidget(blc);
	RootPanel.get().add(viewport);
    }
}
