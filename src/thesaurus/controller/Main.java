package thesaurus.controller;

import java.io.File;
import java.io.PrintWriter;

import thesaurus.model.Lista;

public class Main {

	public File file;
	private Lista listaController = new Lista();
	public Main(File selectedFile) {
		this.file = selectedFile;
	}
	public File inicio() throws Exception {
		try {
			GerarArquivo.gerarBackup(file);
		} catch (Exception e) {
			System.err.println("Erro ao gerar arquivo de backup. Tarcer: " + e.getMessage());
		}
		try {
			GerarArquivo.geradorDeArquivo(listaController.formatarLista(listaController.criarLista(file)), file.getAbsolutePath());
		} catch (Exception e) {
			System.err.println("Erro ao gerar arquivo. Tracer: "+e.getMessage());
			throw new Exception(e);
		}
	    
        return file;
	}
	public File gerarListaParaVisualizacao() {
		File arq = new File("arq");
		try {
            PrintWriter gravarArq = new PrintWriter(arq);
            for (String dado : listaController.formatarLista(listaController.criarLista(file))) {
            	
				gravarArq.printf(dado);
			}
            gravarArq.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arq;
	}
}
