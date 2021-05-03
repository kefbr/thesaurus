package thesaurus.controller;

import java.io.File;

import thesaurus.model.Lista;

public class Main {

	public File file;
	public Main(File selectedFile) {
		this.file = selectedFile;
		inicio(file);
	}
	private File inicio(File file) {
		 Lista listaController = new Lista();
		 GerarArquivo.gerarBackup(file, file.getName());
         GerarArquivo.geradorDeArquivo(listaController.formatarLista(listaController.criarLista(file)), file.getName());
         return file;
	}
}
