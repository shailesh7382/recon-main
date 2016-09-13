package scb.recontool.input;

import scb.recontool.txn.ReconTxnBundles;

public interface InputBundleProcessor {
	
	/**
	 * To convert collection of input sources to <code>Transaction</code> item sets
	 * @param inputBundle
	 * @return
	 */
	public ReconTxnBundles process(InputBundle inputBundle);
	
	public void validateInputBundle(InputBundle inputBundle);

}
