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
	
	public List<String> formatarLista(List<String> lista) throws Exception {
		List<String> dadosFormatados = new ArrayList<String>();
		if(lista.contains("//TODO Thesaurus Gerador Automatico"))
			throw new Exception("Já existe anotaçoes de Thesaurus neste arquivo.");
		Boolean imports = true;
		for (String linha : lista) {
			if (Class.isNomeClass(linha)) {
				dadosFormatados.add("//TODO Thesaurus Gerador Automatico");
                dadosFormatados.add(Annotation.retornaTODOThesaurus(linha)+"\n");
                dadosFormatados.add("@ThesaurusCollection(name=\"" + Annotation.retornaAnotacaoClasseThesaurus(linha) + "\")\n");
                dadosFormatados.add(linha+"\n");
            } else if(Class.isNomeVariavel(linha)){
                dadosFormatados.add(Utils.calculaCaracteres(linha)+Annotation.retornaTODOThesaurus(linha)+"\n");
                dadosFormatados.add(Utils.calculaCaracteres(linha)+"@SerializedName(name=\"" + Annotation.retornaAnotacaoAtributoThesaurus(linha) + "\")\n");
                dadosFormatados.add(linha+"\n\n");
            } else if (linha.contains("import")) {
                dadosFormatados.add(linha+"\n");
                if(imports) {
                	dadosFormatados.add("import br.com.bradesco.lib.persistmongo.annotation.ThesaurusCollection; \n");
                    dadosFormatados.add("import br.com.bradesco.lib.persistmongo.annotation.ThesaurusProperty; \n");
                    dadosFormatados.add("import br.com.bradesco.lib.persistmongo.dao.PersistMongoEntidade; \n");
                    imports = false;
                }
            } else {
            	dadosFormatados.add(linha+"\n");
            }
		}
		return dadosFormatados;
    }
}
