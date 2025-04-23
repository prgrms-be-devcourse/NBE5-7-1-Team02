package easy.gc_coffee_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Thumnail {
//    @ManyToOne
//    @JoinColumn(name = "file_id")
//    private File thumbnail;

    @Column(name = "file_id")
    private Long fileId;

    public Thumnail(Long fileId,String type) throws IllegalArgumentException {
        validateType(type);
        this.fileId = fileId;
    }

    private void validateType(String type) throws IllegalArgumentException {
        String[] split = type.split("/");
        if(split.length < 1 || !split[0].equalsIgnoreCase("image")){
            throw new IllegalArgumentException("Invalid type");
        }
    }
}