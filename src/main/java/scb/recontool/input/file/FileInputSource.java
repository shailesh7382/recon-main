package scb.recontool.input.file;

import scb.recontool.input.InputSource;
import scb.recontool.input.InputType;

public class FileInputSource implements InputSource{
	
	private String fileName;

	public FileInputSource(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public InputType getInputType() {
		return InputType.FILE_NAME;
	}

	@Override
	public String inputDetail() {
		return fileName;
	}

}
