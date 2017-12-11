package gxt.client;

import gxt.share.CopyModel;
import gxt.share.FileService;
import gxt.share.FileServiceAsync;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.event.SubmitEvent;
import com.sencha.gxt.widget.core.client.event.SubmitEvent.SubmitHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

public class PanCopy extends FramedPanel{
	 private static Logger rootLogger = Logger.getLogger("");
	 FileServiceAsync service = GWT.create(FileService.class);
    private static final int PAN_WIDTH = 465;
//    private static final int PAN_HEIGHT = 370;
    ToolButton btHelp = new ToolButton(ToolButton.QUESTION);
    ContentPanel pnCopy = new ContentPanel();
    FormPanel panFile;
    HtmlLayoutContainer contCopy;
    Radio rdHand = new Radio();
    Radio rdFile = new Radio();
    VerticalLayoutContainer contFile;
    FileUploadField file;
    ContentPanel pnProc = new ContentPanel();
	TextArea taInput = new TextArea();
    TextArea taErr = new TextArea();
//    FactAtm fct;

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

  //----------------------- Copy ---------------------------     
    private void fillList(String Copy){
        btCopy.setVisible(false);
//       	taInput.setEnabled(false);
//        if (rdFile.getValue()) {
//        	file.setEnabled(false);
//    		contFile.setEnabled(false);
//        }else taInput.setEnabled(false);
        contCopy.setEnabled(false);
       	listStore.clear();
       	taErr.setValue("");
        btProc.setVisible(false);
		btClearList.setVisible(false);
        service.doTestCopy( Copy, new AsyncCallback<CopyModel>() {
            public void onFailure(Throwable e) {
                Window.alert("Error: " + e.getMessage());
              }
              public void onSuccess(CopyModel results) {
            	  for(String itMess : results.getMess()){
            		taErr.setValue(taErr.getValue()+"\n" + itMess);
            	  }
            	  lstProc.getStore().addAll(results.getModel());
            	  btCopy.setVisible(true);
                  contCopy.setEnabled(true);
                  file.setEnabled(rdFile.getValue());
              	  contFile.setEnabled(rdFile.getValue());
                  taInput.setEnabled(rdHand.getValue());

                if (lstProc.getStore().size() > 0){
                	btProc.setVisible(true);
                	btClearList.setVisible(true);
                }
                }
              } );
    }
     
    ToolButton btCopy = new ToolButton(ToolButton.GEAR, new SelectHandler() {
        @Override public void onSelect(SelectEvent event) {
        	if (rdFile.getValue()){
          	  if (!panFile.isValid()) return;
          	  panFile.submit(); 
        	} else{
           	 String forCopy = taInput.getValue().trim();
             if (forCopy.isEmpty() ||
               	!forCopy.matches("(( *[A-Z0-9]+ +[A-Z0-9]+),|( *[A-Z0-9]+ +[A-Z0-9]+)|[\r\n])*") ) {
               	Window.alert("Ошибки в строке");
               	return;
               }
             fillList(forCopy);
        	}
        	

//		fct.creRcAtm().doTestCopy(forCopy).fire(new Receiver<CopyModelPrx>() {
//			@Override
//			public void onSuccess(CopyModelPrx response) {
//         	  for(String itMess : response.getMess()){
//         		taErr.setValue(taErr.getValue()+"\n" + itMess);
//         	  }
//         	 lstProc.getStore().addAll(response.getModel());
//         	 btCopy.setVisible(true);
//             taInput.setEnabled(true);
//             if (lstProc.getStore().size() > 0){
//             	btProc.setVisible(true);
//             	btClearList.setVisible(true);
//             }
//			}
//            public void onFailure(ServerFailure e) {
//           	  btCopy.setVisible(true);
//              taInput.setEnabled(true);
//              Window.alert("Error: " + e.getMessage());
//	          super.onFailure(e);
//	        }
//		});
           };      });
//----------------------- ClearList ---------------------------     
    ToolButton btClearList = new ToolButton(ToolButton.CLOSE, new SelectHandler() {
		@Override public void onSelect(SelectEvent event) {
           btProc.setVisible(false);
		    btClearList.setVisible(false);
			lstProc.getStore().clear();
		}});
//----------------------- btProc ---------------------------     
    ToolButton btProc = new ToolButton(ToolButton.GEAR, new SelectHandler() {
		@Override public void onSelect(SelectEvent event) {
			btCopy.setVisible(false);
			ArrayList<String> a = new ArrayList<String>();
			a.addAll(lstProc.getStore().getAll());
//			fct.creRcAtm().doTrueCopy(a).fire(new Receiver<CopyModelPrx>() {
//				@Override
//				public void onSuccess(CopyModelPrx response) {
//	              taErr.setValue("");
//	              for(String itMess : response.getMess()){
//	               	taErr.setValue(taErr.getValue()+"\n" + itMess);
//	              }
//	              btProc.setVisible(false);
//	     		  btClearList.setVisible(false);
//	     	 	  lstProc.getStore().clear();
//	      		  btCopy.setVisible(true);
//				}
//	            public void onFailure(ServerFailure e) {
//	              btCopy.setVisible(true);
//	              Window.alert("Error: " + e.getMessage());
//	  	          super.onFailure(e);
//	  	        }
//			});
			
//			ArrayList<String> a = new ArrayList<String>();
//			a.addAll(lstProc.getStore().getAll());
//			service.doTrueCopy( a, new AsyncCallback<CopyModel>() {
//               public void onFailure(Throwable e) {
//        		  btCopy.setVisible(true);
//                 Window.alert("Error: " + e.getMessage());
//               }
//               public void onSuccess(CopyModel results) {
//                 taErr.setValue("");
//                 for(String itMess : results.getMess()){
//                	taErr.setValue(taErr.getValue()+"\n" + itMess);
//                 }
//                 btProc.setVisible(false);
//     		      btClearList.setVisible(false);
//     			  lstProc.getStore().clear();
//      			  btCopy.setVisible(true);
//               }
//		});
		}});

    /**
	 * @return 
	 * begin: btCopy.enable, taInput.enable, btClearList.invis, btProc.invis
	 * btCopy.start: btCopy.invis, taInput.dis, lstProc.clear, process
	 * btCopy.finish: btCopy.vis, taInput.ena, if (lstProc.notEmpty) then btClearList.vis, btProc.vis
	 * btClearList.start: btCopy.invis, btClearList.invis, btProc.invis, lstProc.clear
	 * btClearList.finish: btCopy.vis
	 * btProc.start: btCopy.invis, btClearList.invis, btProc.invis, lstProc.proc
	 * btProc.finish: btCopy.vis
	 */public PanCopy() {
		
//	   fct = fc;
	   addStyleName("margin-10");
	   setHeadingText("Копирование");
//	   setPixelSize(PAN_WIDTH, PAN_HEIGHT);
	   setWidth(PAN_WIDTH);
	   ToolTipConfig configHelp = new ToolTipConfig();
	   configHelp.setTitleHtml("Порядок работы");
	   configHelp.setBodyHtml("<div><ul style=\"list-style: disc; margin: 0px 0px 5px 15px\"><li> В окно 'Копировать' вбить или копипастить пары атм формата: '1111 222,333 444' Первый - откуда копировать, второй - куда </li>"+
			                  "<li>Нажать кн.'Копировать' в заголовке окна(любое количество раз). Корректные атм переместятся в окно 'Обработать'. Некоррктные атм переместятся в окно 'Журнал'</li>"+
			                  "<li>Нажать кн.'Обработать' в заголовке окна 'Обработать'. В окне 'Журнал' появятся сообщения о результате копирования</li>"+
	                          "</ul></div>");
	   configHelp.setCloseable(true);
	   btHelp.setToolTipConfig(configHelp);

	   addTool(btHelp);
	   HtmlLayoutContainer conTop = new HtmlLayoutContainer(getMarkup());
//	   topCont.setBorders(true);
	   
//------------------------
	   pnCopy.setBodyStyle("padding: 3px;");
	   pnCopy.setHeaderVisible(true);
	   pnCopy.setHeadingText("Копировать");
	   btCopy.setTitle("Копировать");
	   pnCopy.addTool(btCopy);
//	   taInput.setAllowBlank(false);
	   taInput.setWidth(200);
	   taInput.setHeight(101);
	   contCopy = new HtmlLayoutContainer(getCopyMarkup());
	   
	   rdHand.setBoxLabel("Руками");
	   contCopy.add( rdHand, new HtmlData(".Hand"));
	   rdFile.setBoxLabel("Файлом");
	   contCopy.add( rdFile, new HtmlData(".File"));
	   ToggleGroup tgCopy = new ToggleGroup();
	   tgCopy.add(rdHand); tgCopy.add(rdFile);
	   rdHand.setValue(true, true);
       rdFile.setValue(false, true);
       
       tgCopy.addValueChangeHandler(new ValueChangeHandler(){
		@Override public void onValueChange(ValueChangeEvent event) {
//			if (rdHand.getValue()){
//				
//			}else{
//				file.setReadOnly(false);
//				taInput.setReadOnly(true);
//			}
			file.setEnabled(rdFile.getValue());
			contFile.setEnabled(rdFile.getValue());
//			taInput.setReadOnly(!rdHand.getValue());
			taInput.setEnabled(rdHand.getValue());

//			rootLogger.log(Level.INFO,  "event = " + ((Radio)event.getValue()).getName());
//		  rootLogger.log(Level.INFO,  "rdHand = " + rdHand.getValue());
//		  rootLogger.log(Level.INFO,  "rdFile = " + rdFile.getValue());
		}
    	   
       });
       contCopy.add( taInput, new HtmlData(".Area"));
       
	   panFile = new FormPanel();
	   panFile.setAction(GWT.getModuleBaseURL()+"srvlUpFile");
//rootLogger.log(Level.INFO,  "GWT.getModuleBaseURL() = " + GWT.getModuleBaseURL());
	   panFile.setEncoding(Encoding.MULTIPART);
	   panFile.setMethod(Method.POST);
	   
	   panFile.addSubmitCompleteHandler(new SubmitCompleteHandler() {
		      public void onSubmitComplete(SubmitCompleteEvent event) {
		        // When the form submission is successfully completed, this event is
		        // fired. Assuming the service returned a response of type text/html,
		        // we can get the result text here (see the FormPanel documentation for
		        // further explanation).
//   	  rootLogger.log(Level.INFO,  "SubmitCompleteHandler");
   	            String result = event.getResults().substring(5).replace("</pre>", "");
   	         if (!result.matches("(( *[A-Z0-9]+ +[A-Z0-9]+),|( *[A-Z0-9]+ +[A-Z0-9]+)|[\r\n])*") ) 
          	      Window.alert("Ошибка формата файла");
   	         else fillList(result);
//   	   	  rootLogger.log(Level.INFO,  "result = "+result);
//		        Window.alert(event.getResults());
		      }
		    });
	   
	   contFile = new VerticalLayoutContainer();
	   panFile.setWidget(contFile);
	   file = new FileUploadField();
	      file.addChangeHandler(new ChangeHandler() {
	        @Override
	        public void onChange(ChangeEvent event) {
	          Info.display("File Changed", "You selected " + file.getValue());
	        }
	      });
//	   file.setName("uploadedfile");
	   file.setEnabled(false);
	   contFile.setEnabled(false);
	   
//	   file.setAllowBlank(false);
//	   contFile.add(taInput);
//       contFile.add(new FieldLabel(file, "Файл"), new VerticalLayoutData(-28, -1));
       contFile.add( file, new VerticalLayoutData(-2, -2));
       contCopy.add( panFile, new HtmlData(".Bro"));
//	   pnCopy.add(taInput);
	   pnCopy.add(contCopy);
//------------------------
  	   pnProc.setBodyStyle("padding: 5px;");
	   pnProc.setHeaderVisible(true);
  	   pnProc.setHeadingText("Обработать");
	   btClearList.setTitle("Стереть");
	   btClearList.setVisible(false);
	   btProc.setTitle("Обработать");
	   btProc.setVisible(false);
	   pnProc.addTool(btProc);
	   pnProc.addTool(btClearList);
 	   lstProc.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
 	   lstProc.setHeight(271);
 	   lstProc.setWidth(130);
	   pnProc.add(lstProc);
//-------------------------		  
	   ContentPanel pnErr = new ContentPanel();
	   pnErr.setBodyStyle("padding: 5px;");
	   pnErr.setHeaderVisible(true);
	   pnErr.setHeadingText("Журнал");
	   taErr.setReadOnly(true);
	   taErr.setWidth(282);
	   taErr.setHeight(100);
	   pnErr.add(taErr);

	   conTop.add(pnCopy, new HtmlData(".pnCopy"));
	   conTop.add(pnProc, new HtmlData(".pnProc"));
	   conTop.add(pnErr, new HtmlData(".pnErr"));
	   
	   setWidget(conTop);
	}

	 private native String getCopyMarkup() /*-{
	    return [ '<table cellpadding=0 cellspacing=5 cols="10">',
	        '<tr><td class=Hand valign="center"></td><td class=Area valign="center"></td></tr>',
	        '<tr><td class=File valign="center"></td><td class=Bro align="left" valign="center"></td></tr>',
	        '</table>'
	    ].join("");
	  }-*/;
	 private native String getMarkup() /*-{
	    return [ '<table cellpadding=0 cellspacing=5 cols="2">',
	        '<tr><td class=pnCopy></td><td class=pnProc valign="top" rowspan=2></td></tr>',
	        '<tr><td class=pnErr></td></tr>',
	        '</table>'
	    ].join("");
	  }-*/;
}

