package thesaurus.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import thesaurus.controller.GerarArquivo;

public class Lista {

	public List<String> criarLista(File arq) {
		return GerarArquivo.importarArquivoParaLista(arq);
	}
	
	public List<String> criarListaBase(File arq) {
		return GerarArquivo.importarBaseParaLista(arq);
	}
	
	public List<String> formatarLista(List<String> lista) throws Exception {
   		List<String> dadosFormatados = new ArrayList<String>();
		if(lista.contains("//TODO Thesaurus Gerador Automatico"))
			throw new Exception("Já existe anotaçoes de Thesaurus neste arquivo.");
//		Boolean imports = true;
		for (String linha : lista) {
			if (Class.isNomeClass(linha)) {
				dadosFormatados.add("//TODO Thesaurus Gerador Automatico \n");
				adicionaLinhaClasse(linha,dadosFormatados);
            } else if(Class.isNomeVariavel(linha)){  
            	adicionaLinhaVariavel(linha,dadosFormatados);
            } else {
            	dadosFormatados.add(linha+"\n");
            }
		}
		return dadosFormatados;
    }
	
	private void adicionaLinhaVariavel(String linha, List<String> dadosFormatados) {
		Annotation annotation = new Annotation();
		for (String palavra : annotation.retornaAtributoFormatado(linha)) {
			if(Dicionario.verificaPalavraMnemonicaOuBaseTep(palavra).equals("")) {
				dadosFormatados.add(annotation.retornaTODOThesaurus(linha)+ "\n");
				break;
			}
					
		}
		int inicio = linha.indexOf("\"");
    	dadosFormatados.add(linha.substring(0, inicio) +"\""+ annotation.retornaAnotacaoAtributoThesaurus(linha) + "\")\n");
	}
	
	private void adicionaLinhaClasse(String linha, List<String> dadosFormatados) {
		Annotation annotation = new Annotation();
		for (String palavra : annotation.retornaPalavrasFormatadas(linha)) {
			if(Dicionario.verificaPalavraMnemonicaOuBaseTep(palavra).equals("")) {
				dadosFormatados.add(annotation.retornaTODOThesaurus(linha)+ "\n");
				break;
			}
					
		}
		int inicio = linha.indexOf("\"");
    	dadosFormatados.add(linha.substring(0, inicio) +"\""+ annotation.retornaAnotacaoClasseThesaurus(linha) + "\")\n");
	}
	
	@SuppressWarnings("unused")
	private boolean adicionarLinhaImports(String linha, List<String>dadosFormatados, Boolean imports) {
		dadosFormatados.add(linha+"\n");
		dadosFormatados.add("import br.com.bradesco.lib.persistmongo.annotation.ThesaurusCollection;\n");
        dadosFormatados.add("import br.com.bradesco.lib.persistmongo.annotation.ThesaurusProperty;\n");
        dadosFormatados.add("import br.com.bradesco.lib.persistmongo.dao.PersistMongoEntidade;\n");
        return false;
	}
}
