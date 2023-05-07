package nl.novi.pegasusclinicalmanagementsystem.controllers;

import jakarta.servlet.http.HttpServletRequest;
import nl.novi.pegasusclinicalmanagementsystem.models.FileUploadResponse;
import nl.novi.pegasusclinicalmanagementsystem.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin
public class FileUploadDownloadController  {
    private final FileService service;

    public FileUploadDownloadController(FileService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file){

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        String fileName = service.storeFile(file, url);

        return new FileUploadResponse(fileName, contentType, url );
    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = service.downLoadFile(fileName);

        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

}