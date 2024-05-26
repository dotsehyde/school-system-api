package io.dotsehyde.schoolsystem.Controllers;

import io.dotsehyde.schoolsystem.Services.HomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @Autowired
    private HomeService homeService;
    public HomeController(HomeService homeService){
        this.homeService = homeService;
    }

    @GetMapping("/media/{filename:.+}")
    public ResponseEntity<Resource> getMedia(@PathVariable String filename){
        Resource file = homeService.getMedia(filename);
      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"")
              .body(file);
    }
}
