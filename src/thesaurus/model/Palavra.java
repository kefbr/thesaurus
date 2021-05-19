package thesaurus.model;

import java.util.ArrayList;
import java.util.List;

public class Palavra {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Palavra other = (Palavra) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Palavra> getRelacionamento() {
		return relacionamento;
	}
	public void setRelacionamento(List<Palavra> relacionamento) {
		this.relacionamento = relacionamento;
	}
	
	public Palavra(String nome, List<Palavra> relacionamento) {
		super();
		this.nome = nome;
		this.relacionamento = relacionamento;
	}
	public Palavra(String nome) {
		super();
		this.nome = nome;
	}

	String nome;
	List<Palavra> relacionamento = new ArrayList<Palavra>();
}
