package net.engineeringdigest.journalApp.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//POJO Class
@Document(collection = "journal_entries")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
//@Builder
@Data // It contains all the above annotations
@NoArgsConstructor
public class JournalEntry {
    @Id
    private String id;
    @NonNull
    private String title;
    private String content;

//    we have commented below code because now we are using lombok
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String  id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
}
