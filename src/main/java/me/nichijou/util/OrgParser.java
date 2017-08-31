package me.nichijou.util;

import me.nichijou.pojo.SourceFileInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nichijou on 8/25/17.
 */
public class OrgParser {

	public static void parseMeta(BufferedReader br, SourceFileInfo sourceFile) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			if (line.contains("#+TAGS: ")) {
				sourceFile.setTags(line.substring(7));
			} else if (line.contains("#+TITLE: ")) {
				sourceFile.setTitle(line.substring(8));
			} else if (line.contains("#+DATE: ")) {
				try {
					sourceFile.setCreated(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz").parse(line.substring(8)));
				} catch (ParseException e) {
					//日期格式错误
					sourceFile.setCreated(new Date());
				}
				// todo 没有找到优雅的seek back,方法，所以当前代码读到空行终止，不能消耗掉流中多余的空行，因此org-mode需要按照严格的格式
//				} else if (!line.contains("#+") && !line.equals("")) {
			} else if (!line.contains("#+")) {
				if (sourceFile.getCreated() == null)
					sourceFile.setCreated(new Date());
				break;
			}
		}
	}

	public static void readDesc(BufferedReader br, SourceFileInfo sourceFile) throws IOException {
		char[] desc = new char[100];
		br.read(desc);
		sourceFile.setDescription(new String(desc));
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
