/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                teste(palavra);
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
    
    public static String teste(String palavra){
        String val="";
        if(!palavraMnemonica(palavra).equals("")){
            val = palavraMnemonica(palavra);
        }else{
            val = verificaBaseTEP(palavra);
        }
        
        return val;
    }
    
    public static String palavraMnemonica(String palavra){
        String palavraAlterada = "";
        Map<String, String> sinomimos = retornaSinonimosTEP();
        for (Map.Entry<String, String> entrada : sinomimos.entrySet()) {
           if(palavra.equals(entrada.getKey())){
               palavraAlterada = entrada.getValue();
               break;
           }
        }
       return palavraAlterada; 
    }
    
    public static String verificaBaseTEP(String palavra){
        String palavraAlterada = "";
        Map<String, String> tep = retornarDicionario();
        for (Map.Entry<String, String> entrada : tep.entrySet()) {
           if(entrada.getKey().equals(palavra)){
               if(!palavraMnemonica(entrada.getValue()).equals("") ){
                    palavraAlterada = entrada.getValue();
                    break;  
               }
           }
        }
       return palavraAlterada; 
    }

    public static Map<String, String> retornaSinonimosTEP() {
        Map<String, String> sinomimos = new HashMap<String, String>();
        try {
            File baseTEP = new File(Utils.class.getResource("/thesaurus/arquivos/baseTep.txt").toURI());
            FileInputStream arquivo = new FileInputStream(baseTEP);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader bfr = new BufferedReader(input);
            Map<String, String> sinonimos = new HashMap<String, String>();

            String linha;

            do {
                linha = bfr.readLine();
                if (linha != null) {
                    if (linha.contains("SINONIMO_DE")) {
                        StringTokenizer st = new StringTokenizer(linha);
                        sinonimos.put(st.nextToken(" SINONIMO_DE "),st.nextToken(" SINONIMO_DE "));
                    }
                }
            } while (linha != null);
            bfr.close();
            input.close();
            arquivo.close();
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sinomimos;
    }
    

    public static Map<String, String> retornarDicionario() {
        Map<String, String> dicionario = new HashMap<String, String>();
        try {

            File thesaurusXLS = new File(Utils.class.getResource("/thesaurus/arquivos/thesaurus.xls").toURI());

            Workbook workbook = Workbook.getWorkbook(thesaurusXLS);
            Sheet sheet = workbook.getSheet(0);
            int linhas = sheet.getRows();

            for (int i = 0; i < linhas; i++) {
                if (i > 0) {
                    String chave = sheet.getCell(1, i).getContents().toLowerCase().trim();
                    String valor = sheet.getCell(2, i).getContents().toLowerCase().trim();
                    chave = chave.substring(0, 1).toUpperCase().concat(chave.substring(1));
                    if (valor.equals("")) {
                        valor = chave;
                    } else {
                        valor = valor.substring(0, 1).toUpperCase().concat(valor.substring(1));
                    }
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

    private static String buscaPalavraSinonimo(List<String> sinonimos, List<String> dicionario) {
        String palavraEncontrada = "";
        for (String palavra : dicionario) {
            for (String sinonimo : sinonimos) {
                if (sinonimo.equals(palavra)) {
                    palavraEncontrada = palavra;
                }
            }
        }
        return palavraEncontrada;
    }

    private static void finalizaSessoes(FileInputStream arquivo, InputStreamReader input, BufferedReader bfr)
            throws IOException {
        bfr.close();
        input.close();
        arquivo.close();
    }

}
