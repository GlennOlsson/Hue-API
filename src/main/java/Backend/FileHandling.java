package Backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandling {
	
	/**
	 * Returns the content of the file as a string, utilizing java.nio
	 * @return the full content of the file
	 */
	public static String getContentOfFile() throws IOException {
		byte[] fileInBytes = Files.readAllBytes(Paths.get(Constants.SETTINGS_FILE_PATH));
		
		return new String(fileInBytes);
	}
	
	/**
	 * Tries to save the content parameter to the file with the file name
	 * @param content the content to save
	 */
	public static void saveToFile(String content){
		try{
			Files.write(Paths.get(Constants.SETTINGS_FILE_PATH), content.getBytes());
			Logger.print("Successfully saved to file");
		}
		catch (Exception e){
			System.err.println("Could not save file");
		}
	}
}
