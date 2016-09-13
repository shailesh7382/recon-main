package scb.recontool.service;

import scb.recontool.txn.ReconTxnBundles;
import scb.recontool.txn.Transaction;

public interface ReconciliationService {

	ReconciliationReport<Transaction> reconcile(ReconTxnBundles bundles);

}