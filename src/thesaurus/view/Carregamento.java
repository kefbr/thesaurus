package thesaurus.view;

import thesaurus.arquivos.Arquivos;
import thesaurus.model.Dicionario;

public class Carregamento extends Thread {
    
    @SuppressWarnings("unused")
	private static Runnable t1 = new Runnable() {
        public void run() {
            try{
            	synchronized(this){
            		TelaPrincipal.progressBar.setValue(15);
                	Arquivos.dicionarioXls = Dicionario.retornarDicionario();
                	TelaPrincipal.progressBar.setValue(50);
                	Arquivos.dicionarioTep = Dicionario.retornaSinonimosTEP();
                	TelaPrincipal.progressBar.setValue(100);
                	TelaPrincipal.telaCarregamento.setVisible(false);
                	this.notify();
            	}
            } catch (Exception e){}

        }
    };

	@Override
	public void run() {
		synchronized(this){
			TelaPrincipal.progressBar.setValue(15);
	    	Arquivos.dicionarioXls = Dicionario.retornarDicionario();
	    	TelaPrincipal.progressBar.setValue(50);
	    	Arquivos.dicionarioTep = Dicionario.retornaSinonimosTEP();
	    	TelaPrincipal.progressBar.setValue(100);
	    	TelaPrincipal.telaCarregamento.dispose();
		}
		// TODO Auto-generated method stub
		
	}
}
