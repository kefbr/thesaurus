/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.model;

import static thesaurus.controller.Utils.teste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import jxl.Sheet;
import jxl.Workbook;
import thesaurus.controller.Utils;

/**
 *
 * @author vinicosta
 */
public class Dicionario {
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

}
