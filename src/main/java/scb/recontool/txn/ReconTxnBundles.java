package scb.recontool.txn;

import java.util.ArrayList;
import java.util.List;

public class ReconTxnBundles {
	
	List<TxnBundle<Transaction>> reconMap = new ArrayList<TxnBundle<Transaction>>();
	
	public void add(TxnBundle<Transaction> bundle) {
		this.reconMap.add(bundle);
	}

	public TxnBundle<Transaction> getBundle(int i) {
		if(i < 2){
			return reconMap.get(i);
		}
		else{
			throw new IllegalArgumentException("More than two Input sources for reconciliation not supported");
		}
		
	}

}
