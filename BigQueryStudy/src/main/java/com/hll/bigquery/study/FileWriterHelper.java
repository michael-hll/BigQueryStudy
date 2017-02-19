package com.hll.bigquery.study;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileWriterHelper {
	
	public static boolean writeStringToFile(Path filePath, List<String> lines, boolean isAppend){
		try {
			if(isAppend)
				Files.write(filePath, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
			else
				Files.write(filePath, lines, Charset.forName("UTF-8"));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
