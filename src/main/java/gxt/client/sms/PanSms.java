package gxt.client.sms;

import gx.client.panlist.PanList;
import gxt.client.sms.ReqFactory.rcDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.RequestFactoryProxy;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent.ParseErrorHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.error.TitleErrorHandler;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.RowExpander;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class PanSms extends ContentPanel{
    private static final int PAN_WIDTH = 1460;
    private static final int PAN_HEIGHT = 560;
//    private static final int DEL_30DAYS = 1000*60*60*24;
    final String maskTel = "^[0-9]{10}$";
    final String titleTel = "10 цифр";
    TextField txTel = new TextField();
    DateField dfFrom = new DateField();
    DateField dfTo = new DateField();

//    ContentPanel pnFields = new ContentPanel();
//    FramedPanel panMain = new FramedPanel();
    HtmlLayoutContainer conMain = new HtmlLayoutContainer(getMarkup());
    ParseErrorHandler dataValid = new ParseErrorHandler() {
	      @Override
	      public void onParseError(ParseErrorEvent event) {
	        Info.display("Parse Error", event.getErrorValue() + " это не дата");
	      } };
	      
	KeyDownHandler enterKeyHandler =  new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
		    	     if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    	    	 refreshTab();
		           }
				}};
				
	public interface StockProperties extends PropertyAccess<StockPrx> {
//	  @Path("id")
	  ModelKeyProvider<StockPrx> id();
	  ValueProvider<StockPrx, String> name();
	}
	private static final StockProperties props = GWT.create(StockProperties.class);
	 
//-------------------------------------------------------	      
	public PanSms(final ReqFactory rf) {
      addStyleName("margin-10");
	  setHeadingText("Смс по № телефона");
      setWidth(PAN_WIDTH);
      setHeight(PAN_HEIGHT);

//      HorizontalLayoutContainer p = new HorizontalLayoutContainer();
      HtmlLayoutContainer p = new HtmlLayoutContainer(getFieldMarkup());
      
      txTel.setEmptyText("№ телефона");
      txTel.addValidator(new RegExValidator( maskTel, titleTel));
      txTel.setErrorSupport(new TitleErrorHandler(txTel));
      txTel.addKeyDownHandler(enterKeyHandler);
      txTel.setAllowBlank(false);
      txTel.setWidth(100);
      
      Label lbFrom = new Label("с");
      dfFrom.setPropertyEditor(new DateTimePropertyEditor("dd-MM-yyyy"));
      dfFrom.addParseErrorHandler(dataValid);
      dfFrom.setErrorSupport(new TitleErrorHandler(dfFrom));
      dfFrom.addKeyDownHandler(enterKeyHandler);
//      Calendar cFrom = Calendar.getInstance();
//      cFrom.setTime(new Date());
//      cFrom.add(Calendar.MONTH, -1);
      Date d = new Date();
      CalendarUtil.addMonthsToDate(d, -1);
//      d.setTime(new Date().getTime() - DEL_30DAYS);
      dfFrom.setValue(d);
      dfFrom.setWidth(100);
      dfFrom.setAllowBlank(false);
      
      Label lbTo = new Label("по");
      dfTo.setPropertyEditor(new DateTimePropertyEditor("dd-MM-yyyy"));
      dfTo.addParseErrorHandler(dataValid);
      dfTo.setErrorSupport(new TitleErrorHandler(dfTo));
      dfTo.addKeyDownHandler(enterKeyHandler);
      dfTo.setValue(new Date());
      dfTo.setWidth(100);
      dfTo.setAllowBlank(false);
      
      TextButton btFind = new TextButton("Найти", new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
//            Stock s = d.flush();
//            if (d.hasErrors()) {
//              new MessageBox("Please correct the errors before saving.").show();
//              return;
//            }
        	  refreshTab();
          }
        });
      TextButton btCopy = new TextButton("Copy", new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
//            Stock s = d.flush();
//            if (d.hasErrors()) {
//              new MessageBox("Please correct the errors before saving.").show();
//              return;
//            }
        	  refreshTab();
          }
        });
      p.add( txTel, new HtmlData(".fTel"));
      p.add( lbFrom, new HtmlData(".lbF"));
      p.add( dfFrom, new HtmlData(".fF"));
      p.add( lbTo, new HtmlData(".lbT"));
      p.add( dfTo, new HtmlData(".fT"));
      p.add( btFind, new HtmlData(".btFind"));
      p.add( btCopy, new HtmlData(".btCopy"));
      
//      ColumnConfig<StockPrx, String> nameCol = new ColumnConfig<StockPrx, String>(props.name(), 200, "Company");
//      IdentityValueProvider<StockPrx> identity = new IdentityValueProvider<StockPrx>();
//      RowExpander<StockPrx> expander = new RowExpander<StockPrx>(identity, new AbstractCell<StockPrx>() {
//        @Override
//        public void render(Context context, StockPrx value, SafeHtmlBuilder sb) {
//          sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Company:</b>" + value.getName() + "</p>");
//          sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Company:</b>afasdfsdfdfsdf</p>");
//
//        }
//      });
//    List<ColumnConfig<StockPrx, ?>> l = new ArrayList<ColumnConfig<StockPrx, ?>>();
//    l.add(expander);
//    l.add(nameCol);
//
//    RequestFactoryProxy<ListLoadConfig, ListLoadResult<StockPrx>> rfpT = new RequestFactoryProxy<ListLoadConfig, ListLoadResult<StockPrx>>() {
//            	@Override
//	            public void load(ListLoadConfig loadConfig, Receiver<? super ListLoadResult<StockPrx>> receiver) {
//		           rcDate req =  rf.creRequest();
// 		           List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
//		           req.getStocks(sortInfo).to(receiver).fire();
//	}};
//	ListStore<StockPrx> stT = new ListStore<StockPrx>(props.id());
//    final ListLoader<ListLoadConfig, ListLoadResult<StockPrx>> loaderT = new ListLoader<ListLoadConfig, ListLoadResult<StockPrx>>(rfpT);
//    loaderT.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, StockPrx, ListLoadResult<StockPrx>>(stT));
//    loaderT.setRemoteSort(true);
//
//    ColumnModel<StockPrx> cm = new ColumnModel<StockPrx>(l);
//    final Grid<StockPrx> grid = new Grid<StockPrx>(stT, cm);
//    final GridSelectionModel<StockPrx> sm = grid.getSelectionModel();
//    sm.setSelectionMode(SelectionMode.SINGLE);
//    grid.setSelectionModel(sm);
//    grid.setLoader(loaderT);
//    grid.getView().setAutoExpandColumn(nameCol);
//    grid.setBorders(false);
//    grid.getView().setStripeRows(true);
//    grid.getView().setColumnLines(true);
//    expander.initPlugin(grid);
//    grid.setAllowTextSelection(true);
//    grid.getLoader().load();
//    setWidget(grid);
      
      PanList<StockPrx> panSel =  new PanList<StockPrx>(PAN_WIDTH-50, PAN_HEIGHT-50, ""){
    	@Override
  		public void fill(){
    	    
          IdentityValueProvider<StockPrx> identity = new IdentityValueProvider<StockPrx>();
          RowExpander<StockPrx> expander = new RowExpander<StockPrx>(identity, new AbstractCell<StockPrx>() {
              @Override
              public void render(Context context, StockPrx value, SafeHtmlBuilder sb) {
                sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Company:</b>" + value.getName() + "</p>");
                sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Company:</b>afasdfsdfdfsdf</p>");

              }
            });

          ColumnConfig<StockPrx, String> nameCol = new ColumnConfig<StockPrx, String>(props.name(), 200, "Company");
          getCcL().add(expander);
          getCcL().add(nameCol);
          setStT(new ListStore<StockPrx>(props.id()));
          setRfpT(new RequestFactoryProxy<ListLoadConfig, ListLoadResult<StockPrx>>() {
//		 		@Override
//		 		public void load(ListLoadConfig loadConfig, Receiver<? super PostListLoadResultProxy> receiver) {
//		 		  PostRequest req =  rf.post();
//		 		  List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
//		 		  req.getPosts(sortInfo).to(receiver).fire();
//		 	  }

				@Override
				public void load(ListLoadConfig loadConfig, Receiver<? super ListLoadResult<StockPrx>> receiver) {
					rcDate req =  rf.creRequest();
			 		List<SortInfo> sortInfo = createRequestSortInfo(req, loadConfig.getSortInfo());
					req.getStocks(sortInfo).to(receiver).fire();
					
				}});
          
          initValues(false, false, false);
          expander.initPlugin(getG());
//          getG().setAllowTextSelection(true);
          
    	}
      };
      
//      ColumnConfig<Stock, String> nameCol = new ColumnConfig<Stock, String>(props.name(), 200, "Company");
//      List<ColumnConfig<Stock, ?>> l = new ArrayList<ColumnConfig<Stock, ?>>();
//      l.add(expander);
//      l.add(nameCol);
//      ColumnModel<Stock> cm = new ColumnModel<Stock>(l);
//      ListStore<Stock> store = new ListStore<Stock>(props.key());
//      store.addAll(Stock.getStocks());
//      final Grid<Stock> grid = new Grid<Stock>(store, cm);
//      grid.getView().setAutoExpandColumn(nameCol);
//      grid.setBorders(false);
//      grid.getView().setStripeRows(true);
//      grid.getView().setColumnLines(true);
//      p.add( grid, new HtmlData(".pnTab"));
      
//      expander.initPlugin(grid);
//      grid.setAllowTextSelection(true);
      panSel.fill();
//      conMain.add( p, new HtmlData(".pnFields"));
//	  conMain.add( panSel, new HtmlData(".pnTab"));
//	  panMain.setWidget(conMain);
//	  setWidget(conMain);
	  setWidget(panSel.getG());
	}

	void refreshTab(){
		txTel.finishEditing(); dfFrom.finishEditing(); dfTo.finishEditing();
	  if (!txTel.isValid() || !dfFrom.isValid() || !dfTo.isValid()) Window.alert("Исправь красные поля");
	  else Window.alert("go dfFrom = " + dfFrom.getText());
	} 
	
	 private native String getFieldMarkup() /*-{
	    return [ '<table cellpadding=0 cellspacing=5 cols="1">',
	        '<tr><td class=fTel valign="top"/><td class=lbF valign="top"/><td class=fF valign="top"/><td class=lbT valign="top"/><td class=fT valign="top"/><td class=btFind valign="top"/><td class=btCopy valign="top"/></tr>',
	        '<tr><td class=pnTab></td></tr>',
	        '</table>'
	    ].join("");
	  }-*/;

	 private native String getMarkup() /*-{
	    return [ '<table cellpadding=0 cellspacing=5 cols="1">',
	        '<tr><td class=pnFields></td></tr>',
	        '<tr><td class=pnTab></td></tr>',
	        '</table>'
	    ].join("");
	  }-*/;

}
