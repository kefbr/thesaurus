package thesaurus.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import thesaurus.controller.GerarArquivo;
import thesaurus.controller.Utils;

public class Lista {

	public List<String> criarLista(File arq) {
		return GerarArquivo.importarArquivoParaLista(arq);
	}
	
	public List<String> criarListaBase(File arq) {
		return GerarArquivo.importarBaseParaLista(arq);
	}
	
	public List<String> formatarLista(List<String> lista) {
		List<String> dadosFormatados = new ArrayList<String>();
		for (String linha : lista) {
			if (Class.isNomeClass(linha)) {
                dadosFormatados.add(Annotation.retornaTODOThesaurus(linha)+"\n");
                dadosFormatados.add("@ThesaurusMapper(\"t" + Annotation.retornaAnotacaoClasseThesaurus(linha) + "\")\n");
                dadosFormatados.add(linha+"\n");
            } else if(Class.isNomeVariavel(linha)){
                dadosFormatados.add(Utils.calculaCaracteres(linha)+Annotation.retornaTODOThesaurus(linha)+"\n");
                dadosFormatados.add(Utils.calculaCaracteres(linha)+"@ThesaurusMapper(\"" + Annotation.retornaAnotacaoAtributoThesaurus(linha) + "\")\n");
                dadosFormatados.add(linha+"\n\n");
            }
            else {
                dadosFormatados.add(linha+"\n");
            }
		}
		return dadosFormatados;
    }
}
