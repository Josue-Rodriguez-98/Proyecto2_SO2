
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lana
 */
public class Update implements Runnable{
    
    static final int PORT = 4200;
    
    Servidor serverGui;
    
    public Update (Servidor serv){
        serverGui = serv;
    }
    
    public void run(){
        try{
            serverGui.holaa();
            serverGui.holaa();

            
            Registry registry = LocateRegistry.getRegistry(PORT);
            ComInterface server = (ComInterface) registry.lookup("proyecto");
            if (server == null) {
                //PERROR
            }else{
                serverGui.holaa();
                while(true){
                    if(server.getCambio()){
                        serverGui.refrescar();
                        server.setCambio(false);
                        //wait(5000);
                    }
                    //wait(1000);
                }
            }
        }catch(Exception e){
            
        }
    }
    
}
