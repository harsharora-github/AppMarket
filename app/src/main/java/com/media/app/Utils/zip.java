package com.media.app.Utils;

/**
 * Created by prabeer.kochar on 03-08-2017.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class zip {


    /**
     * Class with Helper Methods to Create Zip File of the Data Collection Foleder
     */



    /**
     * Zips a file at a location and places the resulting zip file at the toLocation
     * @param sourcePath Source Folder
     * @param toLocation Destination Path
     * @return
     */
    public static boolean zipFileAtPath(String sourcePath, String toLocation) {
        final int BUFFER = 2048;
        ZipOutputStream out = null;
        File sourceFile = new File(sourcePath);
        try {
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(toLocation);
            out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            if (sourceFile.isDirectory()) {
                zipSubFolder(out, sourceFile, sourceFile.getParent().length());
            } else {
                byte data[] = new byte[BUFFER];
                FileInputStream fi = new FileInputStream(sourcePath);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(getLastPathComponent(sourcePath));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * Zips a subfolder
     * @param out Output Stream
     * @param folder Folder to be zipped
     * @param basePathLength length of the source folder
     * @throws IOException
     */
    private static void zipSubFolder(ZipOutputStream out, File folder,
                                     int basePathLength) throws IOException {

        final int BUFFER = 2048;
        if (folder != null) {
            File[] fileList = folder.listFiles();
            BufferedInputStream origin;
            for (File file : fileList) {
                if (file != null) {
                    if (file.isDirectory()) {
                        zipSubFolder(out, file, basePathLength);
                    } else {
                        byte data[] = new byte[BUFFER];
                        String unmodifiedFilePath = file.getPath();
                        String relativePath = unmodifiedFilePath
                                .substring(basePathLength);
                        FileInputStream fi = new FileInputStream(unmodifiedFilePath);
                        origin = new BufferedInputStream(fi, BUFFER);
                        ZipEntry entry = new ZipEntry(relativePath);
                        out.putNextEntry(entry);
                        int count;
                        while ((count = origin.read(data, 0, BUFFER)) != -1) {
                            out.write(data, 0, count);
                        }
                        origin.close();
                    }
                }
            }
        }
    }

    /*
     * gets the last path component
     *
     * Example: getLastPathComponent("downloads/example/fileToZip");
     * Result: "fileToZip"
     */
    public static String getLastPathComponent(String filePath) {
        String[] segments = filePath.split(constants.FORWARD_SLASH);
        if (segments.length == 0)
            return constants.EMPTY_STRING;
        String lastPathComponent = segments[segments.length - 1];
        return lastPathComponent;
    }
}


