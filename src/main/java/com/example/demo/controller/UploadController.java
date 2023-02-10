package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {

    @Autowired
    HttpSession session;

    @Autowired
    ServletContext application;

    private static final String UPLOAD_DIR = "/uploads/";
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
            + "\\src\\main\\resources\\static\\uploads\\";

    @RequestMapping("/upload")
    public String uploadImage() {

        return "clothes/upload";
    }

    @PostMapping("/doUpload")
    public String doUpload(@RequestParam("fileToUpload") MultipartFile file, RedirectAttributes attributes) {

        System.out.println("do Upload");
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            System.out.println("Please select a file to upload.");

            return "redirect:/";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path path = Paths.get(UPLOAD_DIRECTORY + fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        System.out.println("You successfully uploaded " + fileName + '!');

        return "clothes/upload";
    }

}
