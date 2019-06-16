
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.tree.DefaultTreeModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Josue Rodriguez
 */
public class Com extends UnicastRemoteObject implements ComInterface {

    public String name;
    public DefaultTreeModel model;
    public Vector<ComInterface> clients = new Vector();

    public Com(String name) throws RemoteException {
        this.name = name;
    }

    public Com(String name, DefaultTreeModel model) throws RemoteException {
        this.name = name;
        this.model = model;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public DefaultTreeModel getModel() throws RemoteException {
        return this.model;
    }

    @Override
    public void sendTree() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setModel(DefaultTreeModel modelo) throws RemoteException {
        this.model = modelo;
    }

    @Override
    public void setClient(ComInterface cliente) throws RemoteException {
        clients.add(cliente);
    }
 
    @Override
    public void send(String msg) throws RemoteException {
        /*if(msg.equals("nuevoCliente")){
            Iterator<ComInterface> it = this.clients.iterator();
            while(it.hasNext()){
                ComInterface actual = it.next();
                it.next().setModel(this.model);
                actual.printMessage("Cambios en el arbol, presione refrescar!");
            }
        }*/
    }

    @Override
    public void printMessage(String s) throws RemoteException {
        System.out.println(s);
    }

    @Override
    public void nuevoCliente() throws RemoteException {
        Iterator<ComInterface> it = this.clients.iterator();
        while (it.hasNext()) {
            ComInterface actual = it.next();
            it.next().setModel(this.model);
            actual.printMessage("Cambios en el arbol, presione refrescar!");
        }
    }

    @Override
    public File requestFile(String path) throws RemoteException {
        System.out.println("Desde Com: " + path);
        File retVal = new File(path);
        System.out.println(retVal.exists());
        return retVal;
    }

}
