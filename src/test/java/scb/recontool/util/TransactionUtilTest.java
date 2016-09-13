package scb.recontool.util;

import static org.junit.Assert.*;

import org.junit.Test;

import scb.recontool.service.MatchType;
import scb.recontool.txn.Transaction;
import scb.recontool.txn.TxnError;
import scb.recontool.txn.TxnMetaData;

public class TransactionUtilTest {

	@Test
	public void testMatchingEqual() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("3-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.02")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("3-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("100.02")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.EXACT_MATCH, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingEqualButError() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).addError(TxnError.AMOUNT_MORE_THAN_2_SCALE).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("3-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.020")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("3-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("100.02")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.DIFFERENT, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingEqual2Date() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("3-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.02")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("100.02")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.WEAK_MATCH, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingEqual2Date2() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.02")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("6-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("100.02")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.WEAK_MATCH, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingAmount() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.00")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("100.02")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.DIFFERENT, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingAmount2_Imp_Warning() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.00")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("100.0")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.WEAK_MATCH, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingAmount2_Imp_Warning2() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.00")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("100")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.WEAK_MATCH, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingAmount2_Imp_Warning3() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.00")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("0100")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.WEAK_MATCH, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingAmount2_Imp_Warning4() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.00")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("0100")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.WEAK_MATCH, TransactionUtil.matching(txn1, txn2));
	}
	
	@Test
	public void testMatchingAccount() {
		TxnMetaData txnMetaData1 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		TxnMetaData txnMetaData2 = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1).build();
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId("11")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("m1")
				.setTxnAmount("100.00")
				.setMetaData(txnMetaData1)
				.build();
		Transaction txn2 = Transaction.getTransactionBuilder().setAccountId("12")
				.setPostingDate("4-Jun-2016")
				.setTransactionId("n1")
				.setTxnAmount("100.00")
				.setMetaData(txnMetaData2)
				.build();
		assertEquals(MatchType.DIFFERENT, TransactionUtil.matching(txn1, txn2));
	}

}
