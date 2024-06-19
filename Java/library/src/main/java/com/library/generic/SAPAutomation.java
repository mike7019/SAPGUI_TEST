package com.library.generic;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SAPAutomation {
    public static void main(String[] args) {
        // Cargar la DLL de Jacob
        System.setProperty("jacob.dll.path", "path_to_your_dll/jacob-1.19-x86.dll"); // O jacob-1.19-x64.dll para 64 bits

        // Inicializar SAP GUI Scripting
        ActiveXComponent sapGui = new ActiveXComponent("SapGui.ScriptingCtrl.1");
        try {
            Dispatch gui = sapGui.getObject();
            sapGui.setProperty("Visible", new Variant(true));

            // Conectar al proceso SAP Logon
            Dispatch connection = Dispatch.call(gui, "OpenConnection", "NombreDeTuSistema", true).toDispatch();

            // Esperar a que la conexión esté lista
            Thread.sleep(2000);

            // Obtener la sesión de SAP
            Dispatch session = Dispatch.get(connection, "Children").toDispatch();
            Dispatch item = Dispatch.call(session, "Item", new Variant(0)).toDispatch();

            // Realizar el inicio de sesión
//            Dispatch.call(item, "findById", "wnd[0]/usr/txtRSYST-MANDT").toDispatch().put("text", "Mandante");
//            Dispatch.call(item, "findById", "wnd[0]/usr/txtRSYST-BNAME").toDispatch().put("text", "Usuario");
//            Dispatch.call(item, "findById", "wnd[0]/usr/pwdRSYST-BCODE").toDispatch().put("text", "Contraseña");
//            Dispatch.call(item, "findById", "wnd[0]/usr/txtRSYST-LANGU").toDispatch().put("text", "Idioma");
//            Dispatch.call(item, "findById", "wnd[0]/tbar[0]/btn[0]").toDispatch().invoke("press");

            // Esperar a que el login se complete
            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar SAP Logon si es necesario
            Dispatch.call(sapGui, "Quit");
        }
    }
}
