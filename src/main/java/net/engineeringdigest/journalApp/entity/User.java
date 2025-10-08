package net.engineeringdigest.journalApp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//Step 1. A user entity to represent the user data model.
@Document(collection = "users")
@Data
@Builder
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    //Even after writing this annotation auto unique indexing will not be implemented we have to manually add one command in application.properties to do that spring.data.mongodb.auto-index-creation=true
    @NonNull  //--> comes from lombok
    private String userName;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<JournalEntry>();
    private List<String> roles;
}

//Security Steps
//1. A user entity to represent the user data model.
//2. A repository UserRepository to interact with MongoDB.
//3. UserDetailsService implementation to fetch user details.
//4. A configuration SecurityConfig to integrate everything with Spring Security.
