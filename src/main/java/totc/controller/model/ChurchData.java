package totc.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import totc.entity.Church;
import totc.entity.Employee;
import totc.entity.Member;

@Data
@NoArgsConstructor
public class ChurchData {
  
  private Long churchId;
  private String churchName;
  private String churchAddress;
  private String churchCity;
  private String churchState;
  private String churchZip;
  private String churchPhone;
  
  private Set<ChurchMember> members = new HashSet<>();
  private Set<ChurchEmployee> employees = new HashSet<>();
  
  public ChurchData(Church church) {
    churchId = church.getChurchId();
    churchName = church.getChurchName();
    churchAddress = church.getChurchAddress();
    churchCity = church.getChurchCity();
    churchState = church.getChurchState();
    churchZip = church.getChurchZip();
    churchPhone = church.getChurchPhone();
    
    for(Member member : church.getMembers()) {
      members.add(new ChurchMember(member));
    }
    for(Employee employee : church.getEmployees()) {
      employees.add(new ChurchEmployee(employee));
    }
    
  }
  
  @Data
  @NoArgsConstructor
  public static class ChurchMember{
    
    private Long memberId;
    private String memberFirstName;
    private String memberLastName;
    private String memberEmail;
    
    public ChurchMember(Member member){
      memberId = member.getMemberId();
      memberFirstName = member.getMemberFirstName();
      memberLastName = member.getMemberLastName();
      memberEmail = member.getMemberEmail();
      
      
    }
  }
  
  @Data
  @NoArgsConstructor
  public static class ChurchEmployee{
    
    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeePhone;
    private String employeeJobTitle;
    
    public ChurchEmployee(Employee employee) {
      employeeId = employee.getEmployeeId();
      employeeFirstName = employee.getEmployeeFirstName();
      employeeLastName = employee.getEmployeeLastName();
      employeePhone = employee.getEmployeePhone();
      employeeJobTitle = employee.getEmployeeJobTitle();
      
    } 
  }

}
