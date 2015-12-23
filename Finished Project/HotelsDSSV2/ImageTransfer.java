package HotelsDSSV2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.NonWritableChannelException;

public class ImageTransfer {
	static public String imagePath;
	/*AUTHOR: John Sundling*/
	public void copyFiles(File sourceFile, File destinationFile) throws IOException {
		if (!sourceFile.getAbsolutePath().equals(destinationFile.getAbsolutePath())) {
			if (!destinationFile.exists()) {
				destinationFile.createNewFile();
			}

			FileChannel source = null;
			FileChannel destination = null;
			try {
				source = new FileInputStream(sourceFile).getChannel();
				destination = new FileOutputStream(destinationFile).getChannel();

				long count = 0;
				long size = source.size();
				while ((count += destination.transferFrom(source, count, size - count)) < size);

			} catch (NonWritableChannelException e) {
			} finally {
				if (source != null) {
					source.close();
				}
				if (destination != null) {
					destination.close();
				}
			}
		}

	}

	/*AUTHOR: John Sundling*/
	public ImageTransfer(String source) throws IOException {
		File sourceFile = new File(source);
		String localImagePath = "";
		if (source.lastIndexOf("/") > -1) {
			localImagePath = source.substring(source.lastIndexOf("/") + 1, source.length());
		} else {
			localImagePath = source;
		}

		if (localImagePath.lastIndexOf("\\") > -1) {
			localImagePath = localImagePath.substring(localImagePath.lastIndexOf("\\") + 1, localImagePath.length());
		}

		imagePath = Properties.getProperty("imgPath") + localImagePath;
		File destinationFile = new File(Properties.getProperty("imgPath") + localImagePath);
		this.copyFiles(sourceFile, destinationFile);

	}

	/*AUTHOR: John Sundling*/
	public ImageTransfer(String source, String destination) throws IOException {
		File sourceFile = new File(source);

		String localImageName = "";
		if (source.lastIndexOf("/") > -1) {
			localImageName = source.substring(source.lastIndexOf("/") + 1, source.length());
		} else {
			localImageName = source;
		}
		File destinationFile = new File(destination + localImageName);
		this.copyFiles(sourceFile, destinationFile);

	}

	/*AUTHOR: John Sundling*/
	static public boolean createNewImageTransfer(String source) {
		try {
			new ImageTransfer(source);

			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
}
