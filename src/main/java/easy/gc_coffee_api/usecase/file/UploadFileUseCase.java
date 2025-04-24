package easy.gc_coffee_api.usecase.file;

import easy.gc_coffee_api.dto.file.UploadFileResponseDto;
import easy.gc_coffee_api.entity.File;
import easy.gc_coffee_api.repository.FileRepository;
import easy.gc_coffee_api.usecase.file.strategy.StorageStrategy;
import easy.gc_coffee_api.usecase.file.vo.FileUrl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UploadFileUseCase {
    private final FileRepository fileRepository;
    private final StorageStrategy storageStrategy;

    public UploadFileUseCase(
            FileRepository fileRepository,
            @Qualifier("localStorage") StorageStrategy storageStrategy
    ) {
        this.fileRepository = fileRepository;
        this.storageStrategy = storageStrategy;
    }

    public UploadFileResponseDto execute(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일은 업로드할 수 없습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename); // 확장자

        if(extension.isEmpty()) {
            throw new IllegalArgumentException("확장자가 없는 파일로 요청했습니다.");
        }

        String newFileName = UUID.randomUUID() + "." + extension; // 파일명
        String mimeType = file.getContentType();

        if (!isImage(mimeType)) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }

        // 스토리지에 저장
        FileUrl fileUrl = storageStrategy.upload(file, newFileName);

        // DB에 저장
        File savedFile = fileRepository.save(
            new File(mimeType, fileUrl.toString())
        );

        return new UploadFileResponseDto(savedFile.getId(), savedFile.getKey());
    }

    // mimetype 유효성 검사
    private static boolean isImage(String mimeType) {
        return mimeType != null && mimeType.startsWith("image");
    }

    // 확장자 얻기
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return ""; // 확장자 없을 때

        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
