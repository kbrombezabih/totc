package totc.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Church {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  
  private Long churchId;
  @EqualsAndHashCode.Exclude
  private String churchName;
  @EqualsAndHashCode.Exclude
  private String churchAddress;
  @EqualsAndHashCode.Exclude
  private String churchCity;
  @EqualsAndHashCode.Exclude
  private String churchState;
  @EqualsAndHashCode.Exclude
  private String churchZip;
  @EqualsAndHashCode.Exclude
  private String churchPhone;
  
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "church_member", 
        joinColumns = @JoinColumn(name = "church_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id"))
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Member> members = new HashSet<>();
  
  @OneToMany(mappedBy = "church", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Employee> employees = new HashSet<>();
}
