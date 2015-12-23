package HotelsDSSV2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class Properties {
	static private String filename = "config.properties";
	static public final Charset charset = Charset.forName("UTF-8");

	/* AUTHOR: John Sundling */
	// read data from a file and return the content in form of a string
	static private String readProperties(String source) {
		BufferedReader reader = null;
		String fileContent = "";

		try {
			reader = Files.newBufferedReader(Paths.get(source), charset);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent = fileContent + line + "\n";
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return fileContent;
	}

	/* AUTHOR: John Sundling */
	// write new data to a text file
	static private void writeProperties(String source, String listedProperties) {
		BufferedWriter writer = null;

		try {
			writer = Files.newBufferedWriter(Paths.get(source), charset);
			writer.write(listedProperties, 0, listedProperties.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/* AUTHOR: John Sundling */
	// creates a so called property which is a string that look like this
	// "property=value"
	// and the write it to a text file
	static public void addProperty(String property, String value) {
		if (Files.notExists(Paths.get(filename))) {
			System.out.println("NOTICE: [Properties('" + new Object() {
			}.getClass().getEnclosingMethod().getName()
					+ "')]! No existing config file creating new default config.properties");
			try {
				Files.createFile(Paths.get(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (!propertyExists(property)) {
			String propertyList = readProperties(filename);
			propertyList = propertyList + property + "=" + value;
			writeProperties(filename, propertyList);
		} else {
			System.out.println("ERROR: [Properties('" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "')]! property ['" + property + "'] already exist");
		}

	}

	/* AUTHOR: John Sundling */
	// Turn an array to type string to a string
	static private String toStringList(String[] stringArray) {
		String arrayToString = "";
		for (int i = 0; i < stringArray.length; i++) {
			if (!stringArray[i].equals("")) {
				arrayToString = arrayToString + stringArray[i] + "\n";
			}
		}
		return arrayToString;
	}

	/* AUTHOR: John Sundling */
	// locates on which line a certain property is located and return an int
	static private int findRowNumberOfProperty(String property) {
		String[] properties = readProperties(filename).split("\n");
		for (int i = 0; i < properties.length; i++) {
			if (properties[i].indexOf(property) > -1) {
				return i;

			}
		}
		return -1;
	}

	/* AUTHOR: John Sundling */
	// returns the value a property
	static public String getProperty(String property) {
		if (configExists()) {
			String[] properties = readProperties(filename).split("\n");
			if (propertyExists(property)) {
				try {
					return properties[findRowNumberOfProperty(property)].split("=")[1];
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("ERROR: [Properties('" + new Object() {
					}.getClass().getEnclosingMethod().getName() + "')]! property " + property
							+ " doens't contain a value");
				}
				return "";
			} else {
				System.out.println("ERROR: [Properties('" + new Object() {
				}.getClass().getEnclosingMethod().getName() + "')]! property " + property + " do not exist");
				return "";
			}
		} else {
			System.out.println("ERROR: [Properties('" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "')]! file " + filename + " do not exist");
			return "";
		}
	}

	/* AUTHOR: John Sundling */
	// removes a property
	static public void deleteProperty(String property) {
		if (configExists()) {
			if (propertyExists(property)) {
				String[] properties = readProperties(filename).split("\n");
				properties[findRowNumberOfProperty(property)] = "";
				writeProperties(filename, toStringList(properties));
			} else {
				System.out.println("ERROR: [Properties('" + new Object() {
				}.getClass().getEnclosingMethod().getName() + "')]! property " + property + " do not exist");
			}
		} else {
			System.out.println("ERROR: [Properties('" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "')]! file " + filename + " do not exist");
		}
	}

	/* AUTHOR: John Sundling */
	// changes the value of an existing property
	static public void changeProperty(String property, String newValue) {
		if (configExists()) {
			if (propertyExists(property)) {
				String[] properties = readProperties(filename).split("\n");

				properties[findRowNumberOfProperty(property)] = property + "=" + newValue;
				writeProperties(filename, toStringList(properties));

			} else {
				System.out.println("ERROR: [Properties('" + new Object() {
				}.getClass().getEnclosingMethod().getName() + "')]! could not write to property " + property);
			}
		} else {
			System.out.println("ERROR: [Properties('" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "')]! file " + filename + " do not exist");
		}
	}

	/* AUTHOR: John Sundling */
	// boolean method the tells us if a property exist or not
	static public boolean propertyExists(String property) {
		if (configExists()) {
			if (findRowNumberOfProperty(property) >= 0) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println("ERROR: [Properties('" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "')]! file " + filename + " do not exist");
			return false;
		}
	}

	/* AUTHOR: John Sundling */
	// creates an alternative config file specified by the user
	static public void createCustomConfigFile(Path directory, String fileName) {

		if (Files.notExists(Paths.get(directory + "/" + fileName))) {
			String pathBuilder = "";
			// System.out.println(directory.toString().split("/").length);
			for (int i = 0; i < directory.toString().split("/").length; i++) {
				pathBuilder = pathBuilder + directory.toString().split("/")[i].toString() + "/";
				if (Files.notExists(Paths.get(pathBuilder))) {
					new File(pathBuilder).mkdir();
				}
			}

			try {
				if (0 < directory.toString().length()) {
					Files.createFile(Paths.get(directory + "/" + fileName));
					filename = directory + "/" + fileName;
				} else {
					Files.createFile(Paths.get(fileName));
					filename = fileName;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("ERROR: [Properties('" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "')]! file '" + fileName + "' already exist in directory '"
					+ directory + "'");

		}
	}

	/* AUTHOR: John Sundling */
	// loads custom config files
	static public void loadCutomConfigFile(String pathName) {
		if (Files.exists(Paths.get(pathName))) {
			filename = pathName;
		} else {
			System.out.println("ERROR: [Properties('" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "')]! could not find " + pathName);
		}
	}

	/* AUTHOR: John Sundling */
	// check if the current loaded config file exists
	static public boolean configExists() {
		if (Files.exists(Paths.get(filename))) {
			return true;
		} else {
			return false;
		}

	}
}
