package easy.gc_coffee_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Thumnail {

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

    public boolean hasId(){
        return this.fileId != null;
    }
}