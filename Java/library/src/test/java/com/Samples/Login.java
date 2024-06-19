package com.Samples;

import java.io.IOException;

import com.jacob.com.ComFailException;
import com.library.generic.SAPGeneric;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.library.generic.SAPGuiClassName;

public class Login extends SAPGuiClassName {

    public static final String SERVER = "srvsapconexion.dyndns-server.com";
    public static final int PORT = 3200;

    protected ActiveXComponent sapRotWrapper, GUIApp, connection, Session, Obj, SAPGIRD;
    protected Dispatch rotEntry;
    protected Variant scriptingEngine;
    protected Process process;
    public SAPGeneric sapGeneric;

    /**
     * This method is used to get the SAP session object by initializing the SAP GUI and opening a connection to the specified SAP server.
     *
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @BeforeClass
    public void getSapSessionObject() throws InterruptedException {
        ComThread.InitSTA();
        try {
            process = Runtime.getRuntime().exec("C:\\Program Files (x86)\\SAP\\FrontEnd\\SAPgui\\saplogon.exe");
            Thread.sleep(5000);

            sapRotWrapper = new ActiveXComponent("SapROTWr.SapROTWrapper");
            rotEntry = sapRotWrapper.invoke("GetROTEntry", "SAPGUI").toDispatch();
            scriptingEngine = Dispatch.call(rotEntry, "GetScriptingEngine");
            GUIApp = new ActiveXComponent(scriptingEngine.toDispatch());

            ActiveXComponent connection = new ActiveXComponent(
                    GUIApp.invoke("OpenConnectionByConnectionString", "/H/" + SERVER + "/S/" + PORT).toDispatch());

//            ActiveXComponent sapGui = new ActiveXComponent("SapGui.ScriptingCtrl.1");
//            Dispatch session = Dispatch.get(connection, "Children").toDispatch();

            // Instantiate sapGeneric
            sapGeneric = new SAPGeneric(connection);

            // Then set the session
            sapGeneric.setSession(new ActiveXComponent(sapGeneric.getSession().invoke("FindById", "wnd[0]/usr/txtRSYST-BNAME").toDispatch()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ComFailException e) {
            e.printStackTrace();
            System.out.println("Error initializing SAP session: " + e.getMessage());
        }
    }

//    @Test
//    public void LoginSAPGUI() throws InterruptedException {
//        Thread.sleep(2000);
//       // sapGenric.SAPGUISetPasswordField("RSYST-BCODE", "username");
//        //     sapGenric.SAPGUITextFieldSet("RSYST-BNAME", "password");
//        sapGeneric.SAPGUIEnter();
//        System.out.println("entró");
//    }

    @Test
    public void LoginSAPGUI() throws InterruptedException {
        if (sapGeneric != null) {
            Thread.sleep(2000);
            sapGeneric.SAPGUIEnter();
            System.out.println("entró");
        } else {
            System.out.println("SAPGeneric instance not initialized");
        }
    }

    public void exportStorePosTransactions(String storenumber, String dateOfTrans, String filelocation, String filename)
            throws InterruptedException {
        Thread.sleep(5000L);
        sapGeneric.SAPGUIOKCodeFiled("okcd", "/n/posdw/xmlo");
        sapGeneric.SAPGUIEnter();
        Thread.sleep(2000L);
        sapGeneric.SAPGUICTextFieldSendKeys("STORE", storenumber);
        sapGeneric.SAPGUICTextFieldSendKeys("DAY", dateOfTrans);
        sapGeneric.SAPGUIbtnClick("btn[8]");
    }
}
