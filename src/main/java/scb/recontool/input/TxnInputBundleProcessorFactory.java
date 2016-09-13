package scb.recontool.input;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TxnInputBundleProcessorFactory implements InputBundleProcessorFactory {
	private Map<Class<?>, InputBundleProcessor> map = new ConcurrentHashMap<>();

	@Override
	public void register(Class<?> class1, InputBundleProcessor bundleProcessor) {
		this.map.put(class1, bundleProcessor);
	}

	public InputBundleProcessor getProcessor(Class<?> class1){
		return map.get(class1);
	}
	
	@Override
	public void deRegister(Class<?> class1) {
		this.map.remove(class1);
	}
	
}
