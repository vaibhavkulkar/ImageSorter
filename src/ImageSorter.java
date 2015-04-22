import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Locale;

public class ImageSorter {

	public static void main(String[] args) {
		if (args.length <= 1) {
			System.out.println("Please provide directory path");
			return;
		}
		System.out.println("Sorting images from: " + args[0] + " to: "
				+ args[1]);

		File f1 = new File(args[0]);
		if (!f1.exists() || !f1.isDirectory() || f1.listFiles().length <= 0) {
			System.out.println("Source not found or empty: " + args[0]);
			return;
		}
		sortDirectory(args[0], args[1]);
	}

	public static void sortDirectory(String src, String dest) {
		File srcFile = new File(src);

		File[] listFiles = srcFile.listFiles();

		for (File curFile : listFiles) {
			if (!curFile.isDirectory()) {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(curFile.lastModified());
				String relative = cal.get(Calendar.YEAR)
						+ "\\"
						+ cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
								Locale.ENGLISH);
				String absolutePath = dest + "\\" + relative;
				createDestFolder(absolutePath);
				copyFiles(curFile,
						new File(absolutePath + "\\" + curFile.getName()));
			} else {
				sortDirectory(curFile.getPath(), dest);
			}
		}
	}

	public static boolean createDestFolder(String absolutePath) {
		System.out.println(absolutePath);
		File f = new File(absolutePath);
		if (!f.exists()) {
			return (f.mkdirs());
		}
		return true;
	}

	public static void copyFiles(File src, File dest) {
		try {
			Files.move(src.toPath(), dest.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
