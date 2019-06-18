
import java.io.File;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Josue Rodriguez
 */
public class Directory {
    
    public ArrayList<Directory> subdirectorios;
    public ArrayList<File> archivos;
    public String dirName;
    
    public Directory(){
        subdirectorios = new ArrayList<Directory>();
        archivos = new ArrayList<File>();       
    }
    
    public void pushDirectory(Directory dir){
        subdirectorios.add(dir);
    }
    
    public void pushFile(File fil){
        archivos.add(fil);
    }
    
    public void setName(String nam){
        dirName = nam;
    }
    
    public void printDirectories(String mark, Directory dir){
        for (int i = 0; i < dir.subdirectorios.size(); i++) {
            System.out.println(mark+"> " + dir.subdirectorios.get(i).dirName);
            printDirectories(mark+"-", dir.subdirectorios.get(i));
        }
        for (int i = 0; i < dir.archivos.size(); i++) {
            System.out.println(mark+"> " + dir.archivos.get(i).getName());
        }
    }
    
    public void imprimirDirectorios(){
        for (int i = 0; i < subdirectorios.size(); i++) {
            System.out.println(subdirectorios.get(i).dirName);
        }
    }
    
}
