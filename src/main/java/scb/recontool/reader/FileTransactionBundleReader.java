package scb.recontool.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import scb.recontool.input.InputSource;
import scb.recontool.txn.Transaction;
import scb.recontool.txn.TxnBundle;

public class FileTransactionBundleReader implements TransactionBundleReader<Transaction>
{
	private static final Logger LOGGER = Logger.getLogger(FileTransactionBundleReader.class.getName());
	private TransactionReader<Transaction> reader;
	private InputSource inputSource;
	
	public FileTransactionBundleReader(InputSource inputSource, TransactionReader<Transaction> reader){
		this.inputSource = inputSource;
		this.reader = reader;
	}

	@Override
	public TxnBundle<Transaction> readInput() {
		String line = null;
		BufferedReader bufferedReader = null;
		TxnBundle<Transaction> txnBundle = new TxnBundle<Transaction>(inputSource);
		try {
			String file = this.inputSource.inputDetail();
			URL url = getClass().getClassLoader().getResource(this.inputSource.inputDetail());
			if(url != null){
				file = url.getFile();
			}
			bufferedReader = new LineNumberReader(new FileReader(file));
			int lineNumber = 0;
			while ((line = bufferedReader.readLine()) != null) {
				lineNumber = lineNumber+ 1;
				Transaction txn = reader.parse(line, lineNumber);
				txnBundle.addTransaction(txn);
			}
		} catch (FileNotFoundException e) {
			LOGGER.severe(e.getMessage() + e);
			throw new TxnReadException(e.getMessage());
		} catch (IOException e) {
			LOGGER.severe(e.getMessage() + e);
			throw new TxnReadException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		} 
		
		finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, e.getMessage() + e);
					throw new TxnReadException(e.getMessage());
				}
			}
		}
		
		return txnBundle;
	}

}
