/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    public static void geradorDeArquivo(List<String> dados, String path){
        try{
            OutputStream os = new FileOutputStream(path);
            PrintWriter gravarArq = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
            for (String dado : dados) {
                gravarArq.printf(dado);
            }
            gravarArq.close();
        }catch(Exception e){
            System.err.println("Erro ao gerar arquivo"+e.getMessage());
        }
        
    }
    
    public static void gerarBackup(File arquivoOriginal){
        try{
            File arquivoBackup = new File(arquivoOriginal.getParent()+"\\bkp_"+ arquivoOriginal.getName());
            FileUtils.copyFile(arquivoOriginal, arquivoBackup);
        }catch(Exception e){
             System.err.println("Erro ao gerar arquivo"+e.getMessage());
        }
    }
    
    public static List<String> importarArquivoParaLista(File file){
    	List<String> lista = new ArrayList<String>();
		try {
			BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String linha = "";
			while (true) {
				if (linha != null) {
						lista.add(linha);
				} else
					break;
				linha = buffRead.readLine();
			}
			buffRead.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
    }
    
    public static List<String> importarBaseParaLista(File file){
    	List<String> lista = new ArrayList<String>();
		try {
			BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String linha = "";
			while (true) {
				if (linha != null) {
					if(linha.contains("SINONIMO_DE"))
						lista.add(linha);
				} else
					break;
				linha = buffRead.readLine();
			}
			buffRead.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
    }
}
