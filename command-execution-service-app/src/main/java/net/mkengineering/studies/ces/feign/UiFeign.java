package net.mkengineering.studies.ces.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import net.mkengineering.studies.ui.service.UiServiceInterface;

@FeignClient(name="ui-service-app")
public interface UiFeign extends UiServiceInterface {

}
