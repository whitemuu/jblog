package me.nichijou.fetcher;

import me.nichijou.pojo.SourceFileInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by nichijou on 8/25/17.
 */
public class SourceParser {

	static void parseContent(BufferedReader br, SourceFileInfo sourceFile) {
		StringBuilder builder = new StringBuilder();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (Pattern.matches("\\*+\\ ", line)) {
					builder.append(wrap(line, "h" + line.indexOf(' ')));
//				} else if (Pattern.matches("", line)) {
				} else {
					//todo
					builder.append(wrap(line, "p"));
				}
			}
			sourceFile.setContent(builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void parseMeta(BufferedReader br, SourceFileInfo sourceFile) {
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("#+TAGS: ")) {
					sourceFile.setTags(line.substring(7));
					//todo
//				}else if (false){
//				}else if (line.matches("\\ *\\n")){
				} else if (!line.contains("#+")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String wrap(String str, String tag, String attribute) {
		return "<" + tag + " " + attribute + ">" + str + "<" + "tag" + ">";
	}

	static String wrap(String str, String tag) {
		return "<" + tag + ">" + str + "<" + "tag" + ">";
	}
}
