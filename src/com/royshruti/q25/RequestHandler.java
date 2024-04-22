package com.royshruti.q25;

//import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//imports for handling multipart formdata
import java.io.File;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@WebServlet("/q25/questions")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100) // 100 MB

public class RequestHandler extends HttpServlet {
    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadDirPath = applicationPath + File.separator + UPLOAD_DIR;

        FileCreator fileCreator = new FileCreator();
        String uploadFilePath = fileCreator.createFile(request.getParts(), uploadDirPath);

        QuestionRepository qins = new QuestionRepository();
        qins.insert(uploadFilePath);

        // on successful file upload
        response.setStatus(200);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        QuestionRepository qins = new QuestionRepository();
        qins.deleteAll();
    }

}
