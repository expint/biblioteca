package it.online.biblioteca.service;

import java.util.List;

public interface Dao<T> {
	public List<T> readAll();
	public T read(int id);
	public T create(T t);
	public void update(T t);
	public void delete(T t);
	public void clear();

}
