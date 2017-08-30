package me.nichijou.util;

import me.nichijou.pojo.SourceFileInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by nichijou on 8/25/17.
 */
public class OrgParser {

	public static void parseMeta(BufferedReader br, SourceFileInfo sourceFile) {
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("#+TAGS: ")) {
					sourceFile.setTags(line.substring(7));
				} else if (line.contains("#+TITLE: ")) {
					sourceFile.setTitle(line.substring(8));
				} else if (line.contains("#+DATE: ")) {
					sourceFile.setCreated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz").parse(line.substring(8)));
//				}else if (line.matches("\\ *\\n")){
				} else if (!line.contains("#+")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public static void parseContent(BufferedReader br, SourceFileInfo sourceFile) {
		StringBuilder builder = new StringBuilder();
		try {
			String line;
			while ((line = br.readLine()) != null) {
//				if (Pattern.matches("\\*+\\ ", line)) {
				if (line.matches("\\*+\\ .*")) {
					builder.append(wrap(line, "h" + line.indexOf(' ')));
				} else {
					//todo
					builder.append(wrap(line, "p"));
				}
			}
			sourceFile.setContent(builder.toString().replaceAll("\\[\\[(.+)\\]\\[(.+)\\]\\]", "<a href=\"$1\">$2</a>"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String wrap(String line, String tag, String attribute) {
		return "<" + tag + " " + attribute + ">" + line + "<" + "/" + tag + ">";
	}

	static String wrap(String line, String tag) {
		return "<" + tag + ">" + line + "<" + "/" + tag + ">";
	}
}
