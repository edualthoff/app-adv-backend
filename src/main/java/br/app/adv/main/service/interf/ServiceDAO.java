package br.app.adv.main.service.interf;

public interface ServiceDAO<T> {

	T adicionar(T object);
	void remover(T object);
	boolean alterar(T object);
	
}
