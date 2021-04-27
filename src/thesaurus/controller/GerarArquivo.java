/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.controller;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author vinicosta
 */
public class GerarArquivo {
    
    /**
     * 
     * @param dados
     * @param nome 
     */
    public static void geradorDeArquivo(List<String> dados, String nome){
        try{
            FileWriter arq = new FileWriter(System.getProperty("user.home")+"\\Documents\\aqui\\"+nome);
            PrintWriter gravarArq = new PrintWriter(arq);
            for (String dado : dados) {
                gravarArq.printf(dado);
            }
            arq.close();
        }catch(Exception e){
            System.err.println("Erro ao gerar arquivo"+e.getMessage());
        }
        
    }
    
}
