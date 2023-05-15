package server.common.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;


@Data
@Introspected
@NoArgsConstructor
public class NumTag {

    @BsonCreator
    @JsonCreator
    public NumTag(
            @JsonProperty("name")
            @BsonProperty("name") String name,
            @JsonProperty("value")
            @BsonProperty("value") Integer value) {
        this.name = name;
        this.value = value;
    }
    // Key value pair
    String name;
    Integer value;

}
