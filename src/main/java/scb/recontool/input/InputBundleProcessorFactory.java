package scb.recontool.input;

public interface InputBundleProcessorFactory {

	void register(Class<?> class1, InputBundleProcessor bundleProcessor);

	void deRegister(Class<?> class1);

}
