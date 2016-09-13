package scb.recontool.demo;

import scb.recontool.core.ServiceRepository;
import scb.recontool.input.InputBundle;
import scb.recontool.input.InputBundleProcessor;
import scb.recontool.input.InputBundleProcessorFactory;
import scb.recontool.input.ReconInputBundleProcessor;
import scb.recontool.input.TxnInputBundle;
import scb.recontool.input.TxnInputBundleProcessorFactory;
import scb.recontool.input.file.FileInputSource;
import scb.recontool.service.ReconciliationService;
import scb.recontool.service.ReconciliationServiceImpl;
import scb.recontool.txn.ReconTxnBundles;

public class Launcher {

	public static void main(String[] args) {
		//init();
		ReconciliationService impl = new ReconciliationServiceImpl();
		InputBundleProcessor bundleProcessor = new ReconInputBundleProcessor();
		
		reconcile(bundleProcessor, impl, "x.txt", "y.txt");
		reconcile(bundleProcessor, impl, "x2.txt", "y2.txt");
		reconcile(bundleProcessor, impl, "x3.txt", "y3.txt");
		reconcile(bundleProcessor, impl, "x4.txt", "y4.txt");
		reconcile(bundleProcessor, impl, "x5.txt", "y5.txt");
		reconcile(bundleProcessor, impl, "x6.txt", "y6.txt");
	}

	private static void reconcile(InputBundleProcessor bundleProcessor, ReconciliationService impl, String file1, String file2) {
		FileInputSource  first = new  FileInputSource(file1);
		FileInputSource  second = new  FileInputSource(file2);
		InputBundle bundle = TxnInputBundle.get().add(first).add(second);
		ReconTxnBundles  reconTxnBundles = bundleProcessor.process(bundle);
		impl.reconcile(reconTxnBundles).dump();
	}

	/**
	 * Boilerplate code to create a framework-ly like structure and that is extensible and
	 * can be easily weaved into a parallel worker threads processing.
	 * <code>ServiceRepository</code> serves as the precursor for Dependency injection
	 */
	private static void init() {
		ReconciliationService impl = new ReconciliationServiceImpl();
		InputBundleProcessorFactory factory = new TxnInputBundleProcessorFactory();
		InputBundleProcessor bundleProcessor = new ReconInputBundleProcessor();
		factory.register(TxnInputBundle.class, bundleProcessor);
		
		ServiceRepository.INSTANCE.register(ReconciliationService.class, impl);
		ServiceRepository.INSTANCE.register(InputBundleProcessorFactory.class, factory);
		
	}

}
