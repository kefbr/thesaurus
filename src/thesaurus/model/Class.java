/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.model;

/**
 *
 * @author silvfilh
 */
public class Class {
    public static Boolean isNomeClass(String linha) {
        if (linha.contains("@Entity")) {
            return true;
        } else {
            return false;
        }

    }

    public static Boolean isNomeVariavel(String linha) {
        if (linha.contains("@Property")||linha.contains("@Embedded")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String retornaNomeClasse(String linha) {
    	if(linha.indexOf("\"") == -1) {
    		return linha;
    	}
        int inicio = linha.indexOf("\"");
        int fim = linha.lastIndexOf("\"");
        return linha.substring(inicio, fim).replace("\"", "");
    }
    
}
