package scb.recontool.reader;

import static org.junit.Assert.*;

import org.junit.Test;

import scb.recontool.input.InputSource;
import scb.recontool.input.InputType;
import scb.recontool.txn.Transaction;
import scb.recontool.txn.TxnBundle;
import scb.recontool.txn.TxnMetaData;

public class FileTransactionBundleReaderTest {

	@Test
	public void testReadInput() {
		InputSource inputSource = new XInputSource();
		TransactionReader<Transaction> reader = new MockReader();
		FileTransactionBundleReader bundleReader = new FileTransactionBundleReader(inputSource, reader);
		TxnBundle<Transaction> bundle  =  bundleReader.readInput();
		assertNotNull(bundle);
		assertEquals(8, bundle.getTransactions().size());
	}
	
	private final class XInputSource implements InputSource {
		@Override
		public String inputDetail() {
			return "x.txt";
		}

		@Override
		public InputType getInputType() {
			return InputType.FILE_NAME;
		}
	}

	private final class MockReader implements TransactionReader<Transaction> {
		@Override
		public Transaction parse(String line, int lineNumber) {
			switch (lineNumber) {
			case 0:
				TxnMetaData txnMetaData = TxnMetaData.getTxnMetaDataBuilder()
						.setSequenceNumber(0)
						.build();
				Transaction txn = Transaction.getTransactionBuilder()
						.setAccountId("1")
						.setTransactionId("1")
						.setPostingDate("19-Jun-2016")
						.setTxnAmount("1000.01")
						.setMetaData(txnMetaData)
						.build();
				return txn;

			default:
				txnMetaData = TxnMetaData.getTxnMetaDataBuilder()
				.setSequenceNumber(lineNumber)
				.build();
				txn = Transaction.getTransactionBuilder()
				.setAccountId("1")
				.setTransactionId("1")
				.setPostingDate("19-Jun-2016")
				.setTxnAmount("1000.01")
				.setMetaData(txnMetaData)
				.build();
				return txn;
			}
		}
	}

}
