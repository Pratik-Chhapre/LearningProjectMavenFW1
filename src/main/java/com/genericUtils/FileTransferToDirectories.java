package com.genericUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.genericUtils.BaseTest.prop;

/**
 * The FileTransferToDirectories is used for to handle the Flies to keep specific location
 * @author Nandini
 */
public class FileTransferToDirectories {
    private static final String SOURCE_FOLDER_BackDate=System.getProperty("user.dir") + "/Execution Status/Extent Reports/"+BasePage.previousDate();

    private static final String SOURCE_FOLDER=System.getProperty("user.dir") + "/Execution Status/Extent Reports/"+BasePage.currentDate();
    private static final String OUTPUT_FOLDER=System.getProperty("user.dir") + "/Execution Status/ScreenShots";

 public static void moveZipFile() throws IOException {
     moveFile(OUTPUT_FOLDER+"/"+prop.getProperty("screenShotsFolder"), System.getProperty("user.dir") + "/Execution Status/CurrentExecutionReport/" +prop.getProperty("screenShotsFolder"));
}
    public static void selectDirectoriesPath(){
        moveFile( SOURCE_FOLDER+"/"+BaseTest.executionReport,System.getProperty("user.dir") + "/Execution Status/CurrentExecutionReport/"+BaseTest.executionReport);
    }
    private static void moveFile(String src, String dest ) {
        Path result = null;
        try {
            result = Files.copy(Paths.get(src), Paths.get(dest));
        } catch (IOException e) {
            try {
                result=Files.copy(Paths.get(SOURCE_FOLDER_BackDate+"/"+BaseTest.executionReport), Paths.get(dest));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Exception while moving file: " + e.getMessage());
        }
        if(result != null) {
            System.out.println("File moved successfully to "+dest);
        }else{
            System.out.println("File movement failed to "+ dest);
        }

    }
    private static void copyFile(File sourceFile, File destFile)
            throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }

    }

}
