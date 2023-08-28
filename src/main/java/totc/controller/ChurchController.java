package totc.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import totc.controller.model.ChurchData;
import totc.controller.model.ChurchData.ChurchEmployee;
import totc.service.ChurchService;

@RestController
@RequestMapping("/totc")
@Slf4j
public class ChurchController {
  
  @Autowired
  private ChurchService churchService;
  
  @PostMapping("/church")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ChurchData insertChurch(@RequestBody ChurchData churchData){
    log.info("/Creating church {}", churchData);
    return churchService.saveChurch(churchData);
  }
  
  @GetMapping("/church")
  public List<ChurchData> retrieveAllChurches(@PathVariable Long churchId){
    return churchService.retrieveAllChurches();
  }
  
  
  @GetMapping("/{churchId}")
  public ChurchData retrieveChurchById(@PathVariable Long churchId){
    return churchService.retrieveChurchById(churchId);
  }
  
  @PostMapping("/{churchId}/employee")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ChurchEmployee addChurchEmployee(@PathVariable Long churchId, @RequestBody ChurchEmployee churchEmployee) {
    log.info("/Adding a new employee to the church with ID= {} {}", churchId,churchEmployee);
    return churchService.saveEmployee(churchId, churchEmployee);
  }

}
