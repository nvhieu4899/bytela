package hieu.nv.bytela.model.entity.shortlink;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "short_link")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortLink implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "dest_link")
    private String destinationLink;

}
