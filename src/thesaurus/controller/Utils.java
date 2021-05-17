/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.controller;


/**
 *
 * @author vinicosta
 */
public class Utils {
    public static String normalizeFiles(String file) {
        return file.trim().replaceAll("\\s{2,}", " ");
    }
    
    public static String calculaCaracteres(String var) {
        String[] letras = var.split("");
        String soma = "";
        for (int cont = 0; cont < letras.length; cont++) {
            if (letras[cont].equals(" ")) {
                soma += " ";
            } else {
                break;
            }
        }
        return soma;
    }
}
