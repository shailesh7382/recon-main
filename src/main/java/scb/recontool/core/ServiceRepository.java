package scb.recontool.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is Service Repository 
 * to allow for separation of services and
 * invocation dependency.  
 * This should be replaced by proper DI container like guice or spring or pico etc
 */
public enum ServiceRepository {
	
	INSTANCE;
	private Map<Class<?>, Object> repository = new ConcurrentHashMap<Class<?>, Object>();
	private ServiceRepository() {}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getServiceImpl(Class<T> t){
		T instance =  (T) repository.get(t);
		return instance;
	}
	
	public <T> void register(Class<T>  type, T object) {
		repository.put(type, object);
	}
	

	public void reset() {
		repository.clear();
	}
}
