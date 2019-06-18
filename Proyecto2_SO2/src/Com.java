
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Directory master;
    
    public Com(String name) throws RemoteException {
        this.name = name;
    }

    public Com(String name, Directory master) throws RemoteException{
        this.name = name;
        this.master = master;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
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
    public void sendMessage(String msg) throws RemoteException {
        Iterator<ComInterface> it = this.clients.iterator();
        while (it.hasNext()) {
            it.next().print(msg);
        }

    }

    @Override
    public void printMessage(String s) throws RemoteException {
        if(!this.name.equals("server")){
            System.out.println(s);
        }
        Iterator<ComInterface> it = this.clients.iterator();
        while (it.hasNext()) {
            it.next().printMessage(s);
        }
    }

    @Override
    public void nuevoCliente() throws RemoteException {
        Iterator<ComInterface> it = this.clients.iterator();
        while (it.hasNext()) {
            it.next().setMaster(this.master);
            //actual.printMessage("Cambios en el arbol, presione refrescar!");
        }
    }

    @Override
    public File requestFile(String path) throws RemoteException {
        //System.out.println("Desde Com: " + path);
        File retVal = new File(path);
        //System.out.println(retVal.exists());
        
        
        /*BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(retVal));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Com.class.getName()).log(Level.SEVERE, null, ex);
        }
        String linea = "";
        String retorno = "";
        try {
            while((linea = br.readLine())!= null){
                retorno += linea;
                retorno += "\n";
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Com.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return retVal;
    }

    @Override
    public void saveFile(String content, File file) throws RemoteException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(content);
            bw.flush();
            bw.close();
            printMessage(">>Archivo modificado. Estructura Actualizada.");

        } catch (IOException ex) {
            Logger.getLogger(Com.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public Directory pull() throws RemoteException {
        return this.master;
    }

    @Override
    public void remove(String name) throws RemoteException {
        Iterator<ComInterface> it = this.clients.iterator();
        int eraser = -1;
        int deleteMe = -1;
        while (it.hasNext()) {
            eraser++;
            if(it.next().getName().equals(name)){
                deleteMe = eraser;
            }
            //actual.printMessage("Cambios en el arbol, presione refrescar!");
        }
        if(deleteMe != -1){
            this.clients.remove(deleteMe);
        }
        it = this.clients.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getName());
            //actual.printMessage("Cambios en el arbol, presione refrescar!");
        }
    }

    @Override
    public void setMaster(Directory master) throws RemoteException{
        this.master = master;
    }

    @Override
    public void print(String msg) throws RemoteException {
        System.out.println("[Mensaje recibido]: " + msg);
    }

    @Override
    public void push(Directory modified) throws RemoteException {
        this.master = modified;
        int index = 0;
        Iterator<ComInterface> it = this.clients.iterator();
        while (it.hasNext()) {
            it.next().setMaster(this.master);
            this.clients.elementAt(index).print("Cambios la estructura, presione refrescar!");
            index ++;
        }
        
    }

}
