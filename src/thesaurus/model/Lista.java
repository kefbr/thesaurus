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
		if(lista.contains("	 *		Thesausus Gerador Automático.\r\n"))
			throw new Exception("Já existe anotações de Thesaurus neste arquivo.");
		for (String linha : lista) {
			if (Class.isNomeClass(linha)) {
				dadosFormatados.add("/* \r\n"
						+ "	 *		Thesausus Gerador Automático.\r\n"
						+ "	 *		O DESENVOLVEDOR É RESPONSAVEL POR CADA PALAVRA QUE FOI MODIFICADA.\r\n"
						+ "	 *		A FERRAMENTA É UMA FORMA DE AUXILIAR, NÃO NOS RESPOSABILIZAMOS POR ERRO DE PALAVRAS.\r\n"
						+ "	 *		PRESTAR A ATENÇÃO EM PALAVRAS NO PLURAL, E SE O MNEMÔNICO DELA FAZ SENTIDO. \r\n"
						+ "	 * \r\n"
						+ "	 * */\n");
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
			String palavraVerificada = Dicionario.verificaPalavraMnemonicaOuBaseTep(palavra);
			if(palavraVerificada.equals("") || Dicionario.dicionarioMnemomico(palavraVerificada).equals("")) {
				dadosFormatados.add(annotation.retornaTODOThesaurus(linha)+ "\n");
				break;
			}
		}
		int inicio = linha.indexOf("\"");
    	dadosFormatados.add(linha.substring(0, inicio) +"\""+ annotation.retornaAnotacaoAtributoThesaurus(linha) + "\")\n");
	}
	
	private void adicionaLinhaClasse(String linha, List<String> dadosFormatados) {
		Annotation annotation = new Annotation();
		for (String palavra : annotation.retornaAtributoFormatado(linha)) {
			String palavraVerificada = Dicionario.verificaPalavraMnemonicaOuBaseTep(palavra);
			if(palavraVerificada.equals("")) {
				dadosFormatados.add(annotation.retornaTODOThesaurus(linha)+ "\n");
				break;
			}else {
				palavra = palavraVerificada;
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
