package easy.gc_coffee_api.entity;

import easy.gc_coffee_api.util.TypeChecker;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Thumbnail {

    @Column(name = "file_id")
    private Long fileId;

    public Thumbnail(Long fileId, String type) throws IllegalArgumentException {
        validateType(type);
        this.fileId = fileId;
    }

    private void validateType(String type) throws IllegalArgumentException {
        if(!TypeChecker.isImage(type)) {
            throw new IllegalArgumentException("Invalid type");
        }
    }

    public boolean hasId(){
        return this.fileId != null;
    }
}