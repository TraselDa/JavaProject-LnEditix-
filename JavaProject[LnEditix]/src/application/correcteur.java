package application;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;

public class correcteur {

	private String html;
	@SuppressWarnings("unused")
	private Document document;
	@SuppressWarnings("unused")
	private ArrayList<TextNode> textNodes;
	@SuppressWarnings("unused")
	private ArrayList<String> dict;
	@SuppressWarnings("unused")
	private boolean bUseEnglish;

	private static char upper(char ch) {

		ch = Character.toLowerCase(ch);

		if ("aàäâ".lastIndexOf(ch) != -1)
			return 'A';
		if ("eéèêë".lastIndexOf(ch) != -1)
			return 'E';
		if ("iïî".lastIndexOf(ch) != -1)
			return 'I';
		if ("oöô".lastIndexOf(ch) != -1)
			return 'O';
		if ("uùûü".lastIndexOf(ch) != -1)
			return 'U';
		if ("cç".lastIndexOf(ch) != -1)
			return 'C';

		return Character.toUpperCase(ch);
	}

	public static String soundex(String str) {
		String trimmed;
		String noVowel;
		String sound;
		ArrayList<Integer> list;

		trimmed = str.trim(); /* retirer les espaces */
		noVowel = "" + trimmed.charAt(0);
		for (int i = 1; i < trimmed.length(); ++i) {

			char ch = upper(trimmed.charAt(i));
			if (" AEIYOUHW".lastIndexOf(ch) == -1) { /*
														 * si ch n'est pas une voyelle ou H ou W ou espace
														 */
				noVowel = noVowel + ch;
			}
		}

		list = new ArrayList<Integer>();
		sound = "" + noVowel.charAt(0);
		int i = 0;
		for (i = 1; i < Math.min(noVowel.length(), 4); ++i) { /* transformer chaque lettre en chiffre phonetique */

			char ch = noVowel.charAt(i);
			Integer num = 0;
			if ("BP".lastIndexOf(ch) != -1)
				num = 1;
			else if ("CKQ".lastIndexOf(ch) != -1)
				num = 2;
			else if ("DT".lastIndexOf(ch) != -1)
				num = 3;
			else if ("L".lastIndexOf(ch) != -1)
				num = 4;
			else if ("MN".lastIndexOf(ch) != -1)
				num = 5;
			else if ("R".lastIndexOf(ch) != -1)
				num = 6;
			else if ("GJ".lastIndexOf(ch) != -1)
				num = 7;
			else if ("XZS".lastIndexOf(ch) != -1)
				num = 8;
			else if ("FV".lastIndexOf(ch) != -1)
				num = 9;
			else
				;

			if (!list.contains(num)) {
				sound = sound + num;
				list.add(num);
			}
		}

		if (i < 4) /* on à traité moins de 4 éléments */
			for (int j = i; j < 4; ++j)
				sound = sound + "0";

		return sound;
	}

	public String getHtml() {
		return html;
	}
}
