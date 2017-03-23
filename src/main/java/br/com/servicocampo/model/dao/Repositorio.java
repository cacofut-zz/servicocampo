package br.com.servicocampo.model.dao;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.mysql.fabric.xmlrpc.base.Value;

public interface Repositorio<T> {

	public List<T> list();
	public T save(T objeto);
	public int delete(Long objeto);
	public T update(T objeto);
	public T findOne(long id);

}
