/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcgui;

import controller.Controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Model;
import view.View;

/**
 *
 * @author profe
 */
public class MVCGUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        // TODO code application logic here        
         new Controller(new Model(),new View());
    }
    
}
