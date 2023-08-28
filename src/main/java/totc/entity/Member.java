package totc.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Member {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;
  private String memberFirstName;
  private String memberLastName;
  private String memberEmail;
  
  @ManyToMany(mappedBy = "members", cascade = CascadeType.PERSIST)
  private Set<Church> churches = new HashSet<>();

}
