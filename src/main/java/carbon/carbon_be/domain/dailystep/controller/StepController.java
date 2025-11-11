package carbon.carbon_be.domain.dailystep.controller;

import carbon.carbon_be.domain.dailystep.dto.request.StepSyncRequestDto;
import carbon.carbon_be.domain.dailystep.dto.response.StepSyncResponseDto;
import carbon.carbon_be.domain.dailystep.service.StepService;
import carbon.carbon_be.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class StepController {

   private final StepService stepService;

   @PostMapping("/sync")
   public StepSyncResponseDto syncSteps(@AuthenticationPrincipal CustomUserDetails user,
                                      @RequestBody StepSyncRequestDto req){
      return stepService.syncSteps(user.getId(), req);
   }
}
