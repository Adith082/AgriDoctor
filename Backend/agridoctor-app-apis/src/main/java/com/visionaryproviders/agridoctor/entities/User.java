package com.visionaryproviders.agridoctor.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Entity 
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int id;
	  
      private int wallet;
      
      @Column(name = "user_name", nullable = false, length = 100)
      private String name;
      
      private String email, password;
    
      @OneToMany(mappedBy="user",cascade = CascadeType.ALL)
      private List<FeedBack> feedbacks = new ArrayList<>();
      
      
      
}
