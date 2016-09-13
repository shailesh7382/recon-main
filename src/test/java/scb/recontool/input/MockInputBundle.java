package scb.recontool.input;

import java.util.Arrays;
import java.util.List;

import scb.recontool.input.file.FileInputSource;

public class MockInputBundle implements InputBundle {

	@Override
	public List<InputSource> getSources() {
		return Arrays.asList(new FileInputSource("x.txt"), new FileInputSource("y.txt"));
	}

}
