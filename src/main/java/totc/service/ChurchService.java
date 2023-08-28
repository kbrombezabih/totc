package totc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import totc.controller.model.ChurchData;
import totc.controller.model.ChurchData.ChurchEmployee;
import totc.controller.model.ChurchData.ChurchMember;
import totc.dao.ChurchDao;
import totc.dao.EmployeeDao;
import totc.dao.MemberDao;
import totc.entity.Church;
import totc.entity.Employee;
import totc.entity.Member;

@Service
public class ChurchService {
  
  @Autowired
  private ChurchDao churchDao;
  
  @Autowired
  private EmployeeDao employeeDao;
  
  @Autowired
  private MemberDao memberDao;
  
  @Transactional(readOnly = false)
  public ChurchData saveChurch(ChurchData churchData) {
    
    Long churchId = churchData.getChurchId();
    Church church = findOrCreateChurch(churchId);
    copyChurchFields(church, churchData);
    return new ChurchData(churchDao.save(church));
    
  }

  private void copyChurchFields(Church church, ChurchData churchData) {
    
    church.setChurchId(churchData.getChurchId());
    church.setChurchName(churchData.getChurchName());
    church.setChurchAddress(churchData.getChurchAddress());
    church.setChurchCity(churchData.getChurchCity());
    church.setChurchState(churchData.getChurchState());
    church.setChurchZip(churchData.getChurchZip());
    church.setChurchPhone(churchData.getChurchPhone());
    
  }

  private Church findOrCreateChurch(Long churchId) {
    
    if(Objects.isNull(churchId)) {
      return new Church();
    }
    else {
      return findChurchById(churchId);
    }
  }

  private Church findChurchById(Long churchId) {
    
    return churchDao.findById(churchId).orElseThrow(()-> new NoSuchElementException(
        "Church with ID = " + churchId + " was not found."));
    }
  
  @Transactional(readOnly = false)
  public ChurchEmployee saveEmployee(Long churchId, ChurchEmployee churchEmployee) {
    
    Church church = findChurchById(churchId);
    Long employeeId = churchEmployee.getEmployeeId();
    Employee employee = findOrCreateEmployee(churchId, employeeId);
    
    copyEmployeeFields(employee, churchEmployee);
    employee.setChurch(church);
    church.getEmployees().add(employee);
    Employee dbEmployee = employeeDao.save(employee);
    
    return new ChurchEmployee(dbEmployee);
    
  }

  private void copyEmployeeFields(Employee employee, ChurchEmployee churchEmployee) {
    
    employee.setEmployeeId(churchEmployee.getEmployeeId());
    employee.setEmployeeFirstName(churchEmployee.getEmployeeFirstName());
    employee.setEmployeeLastName(churchEmployee.getEmployeeLastName());
    employee.setEmployeePhone(churchEmployee.getEmployeePhone());
    employee.setEmployeeJobTitle(churchEmployee.getEmployeeJobTitle());
    
  }

  private Employee findOrCreateEmployee(Long churchId, Long employeeId) {
    
    Employee employee;
    
    if(Objects.isNull(employeeId)) {
      employee = new Employee();
    }
    else {
      employee = findEmployeeById(churchId, employeeId);
    }
    return employee;
  }

  private Employee findEmployeeById(Long churchId, Long employeeId) {
    
    Employee employee = employeeDao.findById(employeeId)
        .orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
    
    if(churchId.equals(employee.getChurch().getChurchId())) {
      return employee;
    }
    else {
      throw new IllegalArgumentException("Employee with ID=" + employeeId + 
          " does not match the church id of " + churchId);
    }
  } 
  
  @Transactional
  public List<ChurchData> retrieveAllChurches(){
    
    List<Church> churches = churchDao.findAll();
    List<ChurchData> result = new LinkedList<>();
    
    for(Church church : churches) {
      ChurchData churchData = new ChurchData(church);
      
      churchData.getMembers().clear();
      churchData.getEmployees().clear();
      result.add(churchData);
    }
    return result;
  }
  
  @Transactional(readOnly = true)
  public ChurchData retrieveChurchById(Long churchId) {
    
    ChurchData churchData = new ChurchData(findChurchById(churchId));
    
    return churchData;
  }
  
  public ChurchMember saveMember(Long churchId, ChurchMember churchMember) {
    
    Church church = findChurchById(churchId);
    Long memberId = churchMember.getMemberId();
    Member member = findOrCreatMember(churchId, memberId);
    
    copyMemberFields(member, churchMember, church);
    
    member.getChurches().add(church);
    Member newMember = memberDao.save(member);
    newMember.getChurches().add(church);
    
    return new ChurchMember(newMember);
  }

  private void copyMemberFields(Member member, ChurchMember churchMember, Church church) {
    
    member.setMemberId(churchMember.getMemberId());
    member.setMemberFirstName(churchMember.getMemberFirstName());
    member.setMemberLastName(churchMember.getMemberLastName());
    member.setMemberEmail(churchMember.getMemberEmail());
    church.getMembers().add(member);
    
  }

  private Member findOrCreatMember(Long churchId, Long memberId) {
    Member member;
    
    if(Objects.isNull(memberId)) {
      member = new Member();
    }
    else {
      member = findCustomerById(churchId, memberId);
    }
    return member;
  }

  private Member findCustomerById(Long churchId, Long memberId) {
    
    Member member = memberDao.findById(memberId)
        .orElseThrow(() -> new NoSuchElementException("Member with ID=" + memberId + " was not found."));
    
    if(churchId.equals(((ChurchData) member.getChurches()).getChurchId())) {
      return member;
    }
    else {
      throw new IllegalArgumentException("Member with ID=" + memberId + 
          " does not match the church id of " + churchId);
    }
  }
  
  public void deleteChurchById(Long churchId){
    Church church = findChurchById(churchId);
    churchDao.delete(church);
  }
 
}
