package thesaurus.controller;

import java.util.List;
import java.util.Map;

import thesaurus.arquivos.Arquivos;
import thesaurus.model.Palavra;

public class Teste {

	public static void main(String[] args) {
		Main.carregarBases();
		String nomeFormatado = "";
		String palavra = "Documentos";
            if (Arquivos.dicionarioXls.containsKey(palavra)) {
                nomeFormatado += Arquivos.dicionarioXls.get(palavra);
            }else if (Arquivos.dicionarioTep.containsKey(palavra.toLowerCase())) {
            	 Map<String, Palavra> sinonimos = Arquivos.dicionarioTep;
                 for (Map.Entry<String, Palavra> sinonimo : sinonimos.entrySet()) {
					if(sinonimo.getKey().equals(palavra.toLowerCase())) {
						List<Palavra> palavrasEncontrada = sinonimo.getValue().getRelacionamento();
						for (Palavra palavraEncontrada : palavrasEncontrada) {
							if(Arquivos.dicionarioXls.containsKey(palavraEncontrada.getNome())) {
								nomeFormatado += Arquivos.dicionarioXls.get(palavraEncontrada.getNome());
								break;
							}
						}
					}
				}
            }else {
            	nomeFormatado += palavra;
            }
            System.out.println(nomeFormatado);
	}

}
