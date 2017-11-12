package net.mkengineering.studies.ces.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import net.mkengineering.studies.cms.api.CmsApi;

@FeignClient(name="communication-message-service")
public interface CommunicationServiceFeign extends CmsApi {

}
