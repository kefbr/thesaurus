/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jxl.Sheet;
import jxl.Workbook;
import thesaurus.arquivos.Arquivos;
import thesaurus.controller.Utils;

/**
 *
 * @author vinicosta
 */
public class Dicionario {
	
	static String verificaPalavraMnemonicaOuBaseTep(String palavra){
        String val="";
        String aux="";
        String aux2="";
        aux2 = Dicionario.palavraSinonimo(palavra);
        aux = Dicionario.verificaBaseXls(palavra);
        if(!aux.equals("")){
            return aux;
        }else if(!aux2.equals("")){
            return aux2;
        }
        return val;
    }
	
    public static String dicionarioMnemomico(String palavra){
        String nomeFormatado = "";
        if (Arquivos.dicionarioXls.containsKey(palavra)) {
            nomeFormatado += Arquivos.dicionarioXls.get(palavra);
        }else if (Arquivos.dicionarioTep.containsKey(palavra.toLowerCase())) {
        	 Map<String, Palavra> sinonimos = Arquivos.dicionarioTep;
             for (Map.Entry<String, Palavra> sinonimo : sinonimos.entrySet()) {
				if(sinonimo.getKey().equals(palavra.toLowerCase())) {
					if(Arquivos.dicionarioXls.containsKey(sinonimo.getKey())) {
						nomeFormatado += Arquivos.dicionarioXls.get(sinonimo.getKey());
						break;
					}
					List<Palavra> palavrasEncontrada = sinonimo.getValue().getRelacionamento();
					for (Palavra palavraEncontrada : palavrasEncontrada) {
						if(Arquivos.dicionarioXls.containsKey(palavraEncontrada.getNome().substring(0, 1).toUpperCase().concat(palavraEncontrada.getNome().substring(1)))) {
							nomeFormatado += Arquivos.dicionarioXls.get(palavraEncontrada.getNome().substring(0, 1).toUpperCase().concat(palavraEncontrada.getNome().substring(1)));
							break;
						}
					}
				}
			}
        }else {
        	nomeFormatado += palavra;
        }
        return nomeFormatado;
    }
    
    public static String palavraSinonimo(String palavra){
        String palavraAlterada = "";
        Map<String, Palavra> sinomimos = Arquivos.dicionarioTep;
        for (Map.Entry<String, Palavra> entrada : sinomimos.entrySet()) {
           if(palavra.toLowerCase().equals(entrada.getValue().getNome())){
               palavraAlterada = entrada.getValue().getNome();
               break;
           }
        }
       return palavraAlterada; 
    }
    
    public static String verificaBaseXls(String palavra){
        String palavraAlterada = "";
        Map<String, String> xls = Arquivos.dicionarioXls;
        for (Map.Entry<String, String> entrada : xls.entrySet()) {
           if(entrada.getKey().equals(palavra)){
        	   palavraAlterada = entrada.getValue();
               break;  
           }
        }
       return palavraAlterada; 
    }
    
    public static Map<String, Palavra> retornaSinonimosTEP() {
    	Map<String,Palavra> sinonimos = new HashMap<String,Palavra>();
        try {
            File baseTEP = new File(Utils.class.getResource("/thesaurus/arquivos/tep.txt").toURI());
            FileInputStream arquivo = new FileInputStream(baseTEP);
            InputStreamReader input = new InputStreamReader(arquivo,"UTF-8");
            BufferedReader bfr = new BufferedReader(input);
            String linha;
            do {
                linha = bfr.readLine();
                if (linha != null) {
                    if (linha.contains("SINONIMO_DE")) {
                    	String [] sinonimo = linha.split(" SINONIMO_DE ");
                        if(!sinonimos.containsKey(sinonimo[0]) && !sinonimos.containsKey(sinonimo[1])) {
                        	Palavra palavra1 = new Palavra(sinonimo[0]);
                        	Palavra palavra2 = new Palavra(sinonimo[1]);
                        	palavra1 = plavraESinonimo(palavra1,palavra2);
                        	palavra2 = plavraESinonimo(palavra2,palavra1);
                        	sinonimos.put(sinonimo[0],palavra1);
                        	sinonimos.put(sinonimo[1],palavra2);
                        }else if (!sinonimos.containsKey(sinonimo[0]) && sinonimos.containsKey(sinonimo[1])) {
                        	Palavra palavra1 = new Palavra(sinonimo[0]);
                        	for (String palavraNaLista : sinonimos.keySet()) {
								if(palavraNaLista.equals(sinonimo[1])) {
									plavraESinonimo(sinonimos.get(palavraNaLista),palavra1);
									plavraESinonimo(palavra1,sinonimos.get(palavraNaLista));
									sinonimos.put(sinonimo[0], palavra1);
									break;
								}
							}
                        }else if (sinonimos.containsKey(sinonimo[0]) && !sinonimos.containsKey(sinonimo[1])) {
                        	Palavra palavra1 = new Palavra(sinonimo[1]);
                        	for (String palavraNaLista : sinonimos.keySet()) {
								if(palavraNaLista.equals(sinonimo[0])) {
									plavraESinonimo(sinonimos.get(palavraNaLista),palavra1);
									plavraESinonimo(palavra1,sinonimos.get(palavraNaLista));
									sinonimos.put(sinonimo[1], palavra1);
									break;
								}
							}
                        }else if (sinonimos.containsKey(sinonimo[0]) && sinonimos.containsKey(sinonimo[1])) {
                        	plavraESinonimo(sinonimos.get(sinonimo[0]),sinonimos.get(sinonimo[1]));
                        	plavraESinonimo(sinonimos.get(sinonimo[1]),sinonimos.get(sinonimo[0]));
                        }
                    }
                }
            } while (linha != null);
            bfr.close();
            input.close();
            arquivo.close();
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
		return sinonimos;
    }

	private static Palavra plavraESinonimo(Palavra palavra, Palavra sinonimo) {
		palavra.getRelacionamento().add(sinonimo);
		return palavra;
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
            System.out.println("Erro na execucao do parse " + e.getMessage());
        }
        return dicionario;
    }
    
    @SuppressWarnings("unused")
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
