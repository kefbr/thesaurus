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
		Boolean imports = true;
		for (String linha : lista) {
			if (Class.isNomeClass(linha)) {
                dadosFormatados.add(Annotation.retornaTODOThesaurus(linha)+"\n");
                dadosFormatados.add("@ThesaurusCollection(name=\" " + Annotation.retornaAnotacaoClasseThesaurus(linha) + "\")\n");
                dadosFormatados.add(linha+"\n");
            } else if(Class.isNomeVariavel(linha)){
                dadosFormatados.add(Utils.calculaCaracteres(linha)+Annotation.retornaTODOThesaurus(linha)+"\n");
                dadosFormatados.add(Utils.calculaCaracteres(linha)+"@ThesaurusProperty(name=\" " + Annotation.retornaAnotacaoAtributoThesaurus(linha) + "\")\n");
                dadosFormatados.add(linha+"\n\n");
            } else if (linha.contains("import")) {
                dadosFormatados.add(linha+"\n");
                if(imports) {
                	dadosFormatados.add("import br.com.bradesco.lib.persistmongo.annotation.ThesaurusCollection;");
                    dadosFormatados.add("import br.com.bradesco.lib.persistmongo.annotation.ThesaurusProperty;");
                    dadosFormatados.add("import br.com.bradesco.lib.persistmongo.dao.PersistMongoEntidade;");
                    imports = false;
                }
            } else {
            	dadosFormatados.add(linha+"\n");
            }
		}
		return dadosFormatados;
    }
}
