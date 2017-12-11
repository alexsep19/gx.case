package gxt.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import gxt.share.CopyModel;
import gxt.share.FileService;
import gxt.share.FileServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.logging.client.ConsoleLogHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

public class PanFile extends ContentPanel{
     private static Logger rootLogger = Logger.getLogger("");
     final TextField txDol = new TextField();

  	 FileServiceAsync service = GWT.create(FileService.class);
	 ListStore<String> listStore = new ListStore<String>(new ModelKeyProvider<String>(){
		@Override public String getKey(String item) {
			return item.substring(0, item.indexOf(" "));
		}
	 });
     ListView<String, String> lstProc = new ListView<String, String>(listStore, 
    		 new ValueProvider<String, String>(){
				@Override public String getValue(String m) {
					return m;
				}
				@Override public void setValue(String m, String v) {
				  m = v;
				}
				@Override public String getPath() {return "";}
     });
	 ContentPanel pnCopy = new ContentPanel();
     ContentPanel pnProc = new ContentPanel();
	 TextArea taInput = new TextArea();
     TextArea taErr = new TextArea();
//----------------------- Copy ---------------------------     
     ToolButton btCopy = new ToolButton(ToolButton.GEAR, new SelectHandler() {
         @Override public void onSelect(SelectEvent event) {
         	String forCopy = taInput.getValue().trim();
            if (forCopy.isEmpty() ||
            	!forCopy.matches("(( *[A-Z0-9]+ +[A-Z0-9]+),|( *[A-Z0-9]+ +[A-Z0-9]+)|[\r\n])*") ) {
            	Window.alert("Ошибки в строке");
            	return;
            }
//        	pnCopy.remove(btCopy);
            btCopy.setVisible(false);
        	taInput.setEnabled(false);
        	listStore.clear();
        	taErr.setValue("");
//        	if (pnProc.getWidgetIndex(btProc) >= 0){
//rootLogger.log(Level.INFO,  "pnProc.remove(btProc) = " + pnProc.remove(btProc));
//rootLogger.log(Level.INFO,  "pnProc.remove(btClearList) = " + pnProc.remove(btClearList));
              btProc.setVisible(false);
		      btClearList.setVisible(false);
//        	}
            service.doTestCopy( forCopy, new AsyncCallback<CopyModel>() {
                  public void onFailure(Throwable e) {
                    Window.alert("Error: " + e.getMessage());
                  }
                  public void onSuccess(CopyModel results) {
                	  for(String itMess : results.getMess()){
                		taErr.setValue(taErr.getValue()+"\n" + itMess);
                	  }
                	  lstProc.getStore().addAll(results.getModel());
//                  	if (pnCopy.getWidgetIndex(btCopy) >= 0) pnCopy.addTool(btCopy);
                	  btCopy.setVisible(true);
                    taInput.setEnabled(true);
                    if (lstProc.getStore().size() > 0){
                    	btProc.setVisible(true);
                    	btClearList.setVisible(true);
//                      if (pnProc.getWidgetIndex(btProc) < 0)
//                        pnProc.addTool(btProc);
//        		        pnProc.addTool(btClearList);
//rootLogger.log(Level.INFO,  "pnProc.addTool(btProc) = " + pnProc.getWidgetIndex(btProc.setv));
//rootLogger.log(Level.INFO,  "pnProc.addTool(btClearList) = " + pnProc.getWidgetIndex(btClearList));
                    }
                    }
                  } );
           };
         });
//----------------------- ClearList ---------------------------     
     ToolButton btClearList = new ToolButton(ToolButton.CLOSE, new SelectHandler() {
		@Override public void onSelect(SelectEvent event) {
//			rootLogger.log(Level.INFO,  "pnProc.getWidgetIndex(btProc) = " + pnProc.getWidgetIndex(btProc));
//			rootLogger.log(Level.INFO,  "pnProc.remove(btProc) = " + pnProc.remove(btProc));
//			rootLogger.log(Level.INFO,  "pnProc.remove(btClearList) = " + pnProc.remove(btClearList));
            btProc.setVisible(false);
		    btClearList.setVisible(false);
			lstProc.getStore().clear();
		}});
//     ToolButton btCancel = new ToolButton(ToolButton.CLOSE);
//----------------------- btProc ---------------------------     
     ToolButton btProc = new ToolButton(ToolButton.GEAR, new SelectHandler() {
		@Override public void onSelect(SelectEvent event) {
			btCopy.setVisible(false);
			ArrayList<String> a = new ArrayList<String>();
			a.addAll(lstProc.getStore().getAll());
			service.doTrueCopy( a, new AsyncCallback<CopyModel>() {
                public void onFailure(Throwable e) {
         		  btCopy.setVisible(true);
                  Window.alert("Error: " + e.getMessage());
                }
                public void onSuccess(CopyModel results) {
                  taErr.setValue("");
                  for(String itMess : results.getMess()){
                 	taErr.setValue(taErr.getValue()+"\n" + itMess);
                  }
                  btProc.setVisible(false);
      		      btClearList.setVisible(false);
      			  lstProc.getStore().clear();
       			  btCopy.setVisible(true);
                }
		});
		}});
//----------------------- btProc ---------------------------     
     ToolButton btHelp = new ToolButton(ToolButton.QUESTION);
//     , new SelectHandler() {
//    	 @Override public void onSelect(SelectEvent event) {
//    		 
//    	 }
//     });

	/**
	 * @return 
	 * begin: btCopy.enable, taInput.enable, btClearList.invis, btProc.invis
	 * btCopy.start: btCopy.remove, taInput.dis, lstProc.clear, process
	 * btCopy.finish: btCopy.add, taInput.ena, if (lstProc.notEmpty) then btClearList.add, btProc.add
	 * btClearList.start: btCopy.remove, btClearList.remove, btProc.remove, lstProc.clear
	 * btClearList.finish: btCopy.add
	 * btProc.start: btCopy.remove, btClearList.remove, btProc.remove, lstProc.proc
	 * btProc.finish: btCopy.add
	 */
	public PanFile(){
		  rootLogger.addHandler(new ConsoleLogHandler());
		  getHeader().addStyleName("txt_center");
		  setHeadingText("Файл");
		  addStyleName("margin-10");
		  ToolTipConfig configHelp = new ToolTipConfig();
		  configHelp.setTitleHtml("Порядок работы");
		  configHelp.setBodyHtml("В окно 'Копировать' вбить пары атм");
		  configHelp.setCloseable(true);
		  btHelp.setToolTipConfig(configHelp);
		  addTool(btHelp);
		  setPixelSize(500, 400);
		
//		  VerticalLayoutContainer topCon = new VerticalLayoutContainer();
		  HtmlLayoutContainer conTop = new HtmlLayoutContainer(getMarkup());
		  
//		  pnCopy.setWidth(220);
		  pnCopy.setBodyStyle("padding: 5px;");
		  pnCopy.setHeaderVisible(true);
		  pnCopy.setHeadingText("Копировать");
		  btCopy.setTitle("Копировать");
		  pnCopy.addTool(btCopy);
		  
//		  VerticalLayoutContainer conCopy = new VerticalLayoutContainer();
		  taInput.setAllowBlank(false);
		  taInput.setWidth(251);
		  taInput.setHeight(105);
		  FieldLabel flInput = new FieldLabel(taInput, "формат: '111111 222222,333333 444444'");
		  flInput.setLabelAlign(LabelAlign.TOP);
		  pnCopy.add(flInput);
		  
//		  TextButton btnTest = new TextButton("Проверить");
//		  conCopy.add(btnTest);
//		  pnCopy.add(conCopy);
		  conTop.add(pnCopy, new HtmlData(".pnCopy"));
//------------------------
//		  pnProc.setWidth(150);
		  pnProc.setBodyStyle("padding: 5px;");
		  pnProc.setHeaderVisible(true);
		  pnProc.setHeadingText("Обработать");
		  btClearList.setTitle("Стереть");
		  btClearList.setVisible(false);
		  btProc.setTitle("Обработать");
		  btProc.setVisible(false);
		  pnProc.addTool(btProc);
		  pnProc.addTool(btClearList);
		  
//		  VerticalLayoutContainer conProc = new VerticalLayoutContainer();
		  
//		  listView.setStore(listStore);
		  lstProc.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		  lstProc.setHeight(132);
		  lstProc.setWidth(118);
//		  listStore.setAutoCommit(true);
//  	      listView.setTemplate(getTemplate());
//		  FieldLabel flProc = new FieldLabel(lstProc, "Обработать");
//		  flProc.setLabelAlign(LabelAlign.TOP);
		  pnProc.add(lstProc);

		  conTop.add(pnProc, new HtmlData(".pnProc"));
//-------------------------		  
		  ContentPanel pnErr = new ContentPanel();
//		  pnProc.setWidth(150);
		  pnErr.setBodyStyle("padding: 5px;");
		  pnErr.setHeaderVisible(true);
		  pnErr.setHeadingText("Журнал");
//		  btClear.setTitle("Стереть");
		  
		  taErr.setReadOnly(true);
		  taErr.setWidth(400);
		  taErr.setHeight(100);
//		  taErr.setValue("");
		  pnErr.add(taErr);
		  conTop.add(pnErr, new HtmlData(".pnErr"));
//		  final FileUploadField file = new FileUploadField();
//	      file.setName("uploadedfile");
//	      file.setAllowBlank(false);
//	      topCon.add(new FieldLabel(file, "File"), new VerticalLayoutData(-18, -1));
//	      
//	      TextButton btn = new TextButton("Submit");
//	      btn.addSelectHandler(new SelectHandler() {
//
//	        @Override
//	        public void onSelect(SelectEvent event) {
////	          MessageBox box = new MessageBox("File Upload Example", "Your file was uploaded.");
////	          box.setIcon(MessageBox.ICONS.info());
////	          box.show();
//	        }
//	      });
//	      topCon.add(btn);

	      add(conTop);
		}
	
	private native String getMarkup() /*-{
    return [ '<table cellpadding=0 cellspacing=5 cols="2">',
        '<tr><td class=pnCopy></td><td class=pnProc valign="top"></td></tr>',
        '<tr><td class=pnErr colspan=2></td></tr>',
        '</table>'
    ].join("");
  }-*/;
//	private native String getTemplate() /*-{ 
//	 return ['<tpl for=".">',
//	 '<div class="{style}">{foo.name}</div>', 
//	 '</tpl>', 
//	 '<div class="x-clear"></div>'].join(""); 
//	}-*/;
}
