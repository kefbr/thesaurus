/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.model;

import static thesaurus.controller.Utils.normalizeFiles;
import static thesaurus.model.Dicionario.dicionarioMnemomico;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author silvfilh
 */
public class Annotation {
	
	public List<String> retornaPalavrasFormatadas(String nomeFormatar) {
        String[] letras = nomeFormatar.split("");
        String atributoFormatado = "";
        String palavraFormatada = "";
        if(nomeFormatar.contains("private")) {
        	atributoFormatado = nomeFormatar.substring(nomeFormatar.lastIndexOf(" "));
        	letras = atributoFormatado.substring(1).split("");
        }

        for (int cont = 0; cont < letras.length; cont++) {
            if (Character.isUpperCase(letras[cont].charAt(0)) && cont > 0) {
                palavraFormatada = palavraFormatada + ";" + letras[cont].charAt(0);
            } else {
                palavraFormatada = palavraFormatada + String.valueOf(letras[cont].charAt(0));
            }
        }
        return Arrays.asList(palavraFormatada.split(";"));
    }

    public String retornaAnotacaoClasseThesaurus(String nomeClasse) {
    	String palavraFormatada = "";
    	for (String palavra : retornaPalavrasFormatadas(Class.retornaNomeClasse(normalizeFiles(nomeClasse)))) {
    		String palavraMnemonico = dicionarioMnemomico(palavra);
    		if(!palavraMnemonico.equals("")) {
    			 palavraFormatada += palavraMnemonico;
    		}else {
    			 palavraFormatada += palavra;
    		}
		}
    	return palavraFormatada;
    }

    public String retornaAnotacaoAtributoThesaurus(String nomeAtributo) {
    	String palavraFormatada = "";
    	for (String palavra : retornaAtributoFormatado(nomeAtributo)) {
    		String palavraMnemonico = dicionarioMnemomico(palavra);
    		if(!palavraMnemonico.equals("")) {
    			palavraFormatada += palavraMnemonico;
    		}else {
    			palavraFormatada += palavra;
    		}
		}
    	return palavraFormatada;
    }

    public String retornaTODOThesaurus(String nomeClasse) {
        return retornaPalavrasNaoEncontradas(retornaPalavrasFormatadas(Class.retornaNomeClasse(normalizeFiles(nomeClasse))));
    }

    public String retornaPalavrasNaoEncontradas(List<String> palavras) {
        String nomeFormatado = "";
        String ajustado = "";
        Boolean ajuste = false;
        for (String palavra : palavras) {
            if (Dicionario.dicionarioMnemomico(palavra).equals(palavra)) {
                nomeFormatado += palavra + " ";
                ajuste = true;
            }
        }
        if (ajuste) {
            
            ajustado = "//TODO ajustar ";
            ajustado += nomeFormatado +"," + " Não encontrado no thesaurus";
        }
        return ajustado;
    }

    public List<String> retornaAtributoFormatado(String nomeFormatar) {
        nomeFormatar = Class.retornaNomeClasse(nomeFormatar);
        String palavraFormatada = nomeFormatar;
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

}
