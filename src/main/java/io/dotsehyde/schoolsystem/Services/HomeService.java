package io.dotsehyde.schoolsystem.Services;

import io.dotsehyde.schoolsystem.Config.Error.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class HomeService {

    private final String path =Paths.get("", "media").toString();
    public Resource getMedia(String filename){
        try {
            Path file = Paths.get(path).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new AppException(404, "Could not find media: "+filename);
            }

        }catch (Exception e){
            throw  new AppException(500, e.getMessage());
        }
    }
}
