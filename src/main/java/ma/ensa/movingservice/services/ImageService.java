package ma.ensa.movingservice.services;


import ma.ensa.movingservice.exceptions.RecordNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class ImageService {

    public static String UPLOAD_DIRECTORY =
            System.getProperty("user.dir") + "/uploads";

    public String handleImageUpload(@NotNull MultipartFile file) {

        // prepare the filename
        String  dateString = Long.toString(System.currentTimeMillis()),
                ext = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1],
                fileName = "img-" + dateString + "." + ext;

        Path path = Paths.get(UPLOAD_DIRECTORY, fileName);

        // write the file bytes
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return the file name
        return fileName;
    }



    public void deleteImage(String fileName){
        Path path = Paths.get(UPLOAD_DIRECTORY, fileName);
        path.toFile().delete();
    }

    public byte[] getImageBytes(String filename) throws RecordNotFoundException {

        byte[] bytes;

        Path path = Paths.get(UPLOAD_DIRECTORY, filename);

        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RecordNotFoundException("image not found");
        }

        return bytes;
    }


}