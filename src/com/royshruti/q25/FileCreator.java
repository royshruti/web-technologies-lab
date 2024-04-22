package com.royshruti.q25;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.Part;

public class FileCreator {

    /**
     * creates a file from collection of parts of the multipart request
     * and saves the created file in uploadDirPath
     * 
     * @param parts         is the collection of parts of the file uploaded via
     *                      multipart request
     * @param uploadDirPath is the Directory path where the uploaded file will be
     *                      stored
     * @return the path of the uploaded file
     */

    String createFile(Collection<Part> parts, final String uploadDirPath) {

        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadDirPath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());

        String fileName = null;
        // Get all the parts from request and write it to the file on server
        for (Part part : parts) {
            fileName = getFileName(part);
            try {

                part.write(uploadDirPath + File.separator + fileName);
            } catch (IOException e) {
                System.err.println("Couldn't write file" + e.getLocalizedMessage());
            }
        }

        String uploadedFilePath = uploadDirPath + File.separator + fileName;
        return uploadedFilePath;
    }

    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= " + contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
