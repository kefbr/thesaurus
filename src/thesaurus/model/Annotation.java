/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesaurus.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import thesaurus.arquivos.Arquivos;

import static thesaurus.controller.Utils.normalizeFiles;
import static thesaurus.model.Dicionario.dicionarioMnemomico;

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
        return dicionarioMnemomico(retornaPalavrasFormatadas(Class.retornaNomeClasse(normalizeFiles(nomeClasse))),  Arquivos.dicionarioXls);
    }

    public String retornaAnotacaoAtributoThesaurus(String nomeAtributo) {
        return dicionarioMnemomico(retornaAtributoFormatado(nomeAtributo), Arquivos.dicionarioXls);
    }

    public String retornaTODOThesaurus(String nomeClasse) {
        return retornaPalavrasNaoEncontradas(retornaPalavrasFormatadas(Class.retornaNomeClasse(normalizeFiles(nomeClasse))), Arquivos.dicionarioXls);
    }

    public String retornaPalavrasNaoEncontradas(List<String> palavras, Map<String, String> dicionario) {
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
            ajustado += nomeFormatado +"," + " N�o encontrado no thesaurus";
        }
        return ajustado;
    }

    public List<String> retornaAtributoFormatado(String nomeFormatar) {
        //nomeFormatar = normalizeFiles(nomeFormatar);
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
