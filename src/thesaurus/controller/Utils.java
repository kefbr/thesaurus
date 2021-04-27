/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author vinicosta
 */
public class Utils {

    /**
     *
     * @param nomeFormatar
     * @return
     */
    public static List<String> retornaPalavrasFormatadas(String nomeFormatar) {
        String[] letras = nomeFormatar.split("");
        String palavraFormatada = "";

        for (int cont = 0; cont < letras.length; cont++) {
            if (Character.isUpperCase(letras[cont].charAt(0)) && cont > 0) {
                palavraFormatada = palavraFormatada + ";" + letras[cont].charAt(0);
            } else {
                palavraFormatada = palavraFormatada + String.valueOf(letras[cont].charAt(0));
            }
        }
        return Arrays.asList(palavraFormatada.split(";"));
    }

    public static String retornaAnotacaoClasseThesaurus(String nomeClasse) {
        return dicionarioMnemomico(retornaPalavrasFormatadas(retornaNomeClasse(normalizeFiles(nomeClasse))), retornarDicionario());
    }

    public static String retornaAnotacaoAtributoThesaurus(String nomeAtributo) {
        return dicionarioMnemomico(retornaAtributoFormatado(nomeAtributo), retornarDicionario());
    }

    public static String retornaTODOThesaurus(String nomeClasse) {
        return retornaPalavrasNaoEncontradas(retornaPalavrasFormatadas(retornaNomeClasse(normalizeFiles(nomeClasse))), retornarDicionario());
    }

    public static String retornaPalavrasNaoEncontradas(List<String> palavras, Map<String, String> dicionario) {
        String nomeFormatado = "";
        String ajustado = "";
        Boolean ajuste = false;
        for (String palavra : palavras) {
            if (!dicionario.keySet().contains(palavra)) {
                nomeFormatado += palavra + " ";
                ajuste = true;
            }
        }
        if (ajuste) {
            ajustado = "//TODO ajustar ";
            ajustado += nomeFormatado + " Não encontrado no thesaurus";
        }
        return ajustado;
    }

    /**
     *
     * @param palavras
     * @param dicionario
     * @return
     */
    public static String dicionarioMnemomico(List<String> palavras, Map<String, String> dicionario) {
        String nomeFormatado = "";
        for (String palavra : palavras) {
            if (dicionario.keySet().contains(palavra)) {
                nomeFormatado += dicionario.get(palavra);
            } else {
                nomeFormatado += palavra;
            }
        }
        return nomeFormatado;
    }

    public static String normalizeFiles(String file) {
        return file.trim().replaceAll("\\s{2,}", " ");
    }

    public static Boolean isNomeClass(String linha) {
        if (linha.contains("public class ")) {
            return true;
        } else {
            return false;
        }

    }

    public static Boolean isNomeVariavel(String linha, List<String> tipos) {
            if (linha.contains("private")&&linha.contains(";")) {
                return true;
            }else{
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

    public static Map<String, String> retornarDicionario() {
        Map<String, String> dicionario = new HashMap<String, String>();
        try {
            File dir = new File(System.getProperty("user.home")+"\\Documents\\aqui");
            File arq = new File(dir, "thesaurus.xls");

            Workbook workbook = Workbook.getWorkbook(arq);
                Sheet sheet = workbook.getSheet(0);
                int linhas = sheet.getRows();
                
                for(int i = 0; i < linhas; i++){
                    if(i>0){
                    String chave = sheet.getCell(1, i).getContents().toLowerCase().trim();
                    String valor = sheet.getCell(2, i).getContents().toLowerCase().trim();                   
                    chave = chave.substring(0, 1).toUpperCase().concat(chave.substring(1));
                    if(valor.equals("")){
                      valor = chave;
                    }else{
                      valor = valor.substring(0, 1).toUpperCase().concat(valor.substring(1));  
                    }
                    // Correção
                    dicionario.put(chave, valor);
                    }
                }
                
                workbook.close();

        } catch (Exception e) {
            System.out.println("Erro na execução do parse " + e.getMessage());
        }

        return dicionario;
    }

    public static List<String> retornaAtributoFormatado(String nomeFormatar) {
        nomeFormatar = normalizeFiles(nomeFormatar);
        String[] letras = nomeFormatar.split("");
        String palavraFormatada = "";
        int conta = 0;
        for (int cont = 0; cont < letras.length; cont++) {
            if (letras[cont].equals(" ")) {
                conta++;
            }
            if (!letras[cont].equals(" ") && conta > 1) {
                palavraFormatada = palavraFormatada + letras[cont];
            }

        }
        palavraFormatada = palavraFormatada.substring(0, 1).toUpperCase().concat(palavraFormatada.substring(1));
        String[] letrasFormatadas = palavraFormatada.split("");
        String atributoFormatado = "";
        for (int cont = 0; cont < letrasFormatadas.length; cont++) {
            if (Character.isUpperCase(letrasFormatadas[cont].charAt(0)) && cont > 0) {
                atributoFormatado = atributoFormatado + ";" + letrasFormatadas[cont].charAt(0);
            } else {
                atributoFormatado = atributoFormatado + String.valueOf(letrasFormatadas[cont].charAt(0));
            }

        }

        return Arrays.asList(atributoFormatado.split(";"));
    }

    public static List<String> tiposJava() {
        List<String> tipos = new ArrayList<>();

        tipos.add("int");
        tipos.add("double");

        return tipos;
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

    private static void finalizaSessoes(FileInputStream arquivo, InputStreamReader input, BufferedReader bfr)
            throws IOException {
        bfr.close();
        input.close();
        arquivo.close();
    }

}
