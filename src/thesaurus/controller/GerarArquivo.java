/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import org.apache.commons.io.FileUtils;

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
            FileWriter arq = new FileWriter(System.getProperty("user.home")+"\\Documents\\"+nome);
            PrintWriter gravarArq = new PrintWriter(arq);
            for (String dado : dados) {
                gravarArq.printf(dado);
            }
            arq.close();
        }catch(Exception e){
            System.err.println("Erro ao gerar arquivo"+e.getMessage());
        }
        
    }
    
    public static void gerarBackup(File arquivoOriginal, String nome){
        try{
            File arquivoBackup = new File(System.getProperty("user.home")+"\\Documents\\"+"bck_"+nome);
            FileUtils.copyFile(arquivoOriginal, arquivoBackup);
        }catch(Exception e){
             System.err.println("Erro ao gerar arquivo"+e.getMessage());
        }
    }
    
}
