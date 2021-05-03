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
        if (linha.contains("public class ")) {
            return true;
        } else {
            return false;
        }

    }

    public static Boolean isNomeVariavel(String linha) {
        if (linha.contains("private") && linha.contains(";")) {
            return true;
        } else {
            return false;
        }
    }

    public static String retornaNomeClasse(String linha) {
        if (linha.contains("class ")) {
            int fim = linha.lastIndexOf(" ");
            return linha.substring(13, fim);
        } else {
            return linha;
        }
    }
    
}
