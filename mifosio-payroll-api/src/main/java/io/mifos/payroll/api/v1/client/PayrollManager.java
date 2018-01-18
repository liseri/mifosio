/*
 * Copyright 2017 The Mifos Initiative.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mifos.payroll.api.v1.client;

import io.mifos.core.api.annotation.ThrowsException;
import io.mifos.core.api.annotation.ThrowsExceptions;
import io.mifos.core.api.util.CustomFeignClientsConfiguration;
import io.mifos.payroll.api.v1.domain.PayrollCollectionHistory;
import io.mifos.payroll.api.v1.domain.PayrollCollectionSheet;
import io.mifos.payroll.api.v1.domain.PayrollConfiguration;
import io.mifos.payroll.api.v1.domain.PayrollPaymentPage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@FeignClient(name="payroll-v1", path="/payroll/v1", configuration = CustomFeignClientsConfiguration.class)
public interface PayrollManager {

  @RequestMapping(
      value = "/customers/{identifier}/payroll",
      method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ThrowsExceptions({
      @ThrowsException(status = HttpStatus.NOT_FOUND, exception = CustomerNotFoundException.class),
      @ThrowsException(status = HttpStatus.BAD_REQUEST, exception = PayrollDistributionValidationException.class)
  })
  void setPayrollConfiguration(@PathVariable(value = "identifier") final String customerIdentifier,
                               @RequestBody @Valid final PayrollConfiguration payrollConfiguration);

  @RequestMapping(
      value = "/customers/{identifier}/payroll",
      method = RequestMethod.GET,
      produces = MediaType.ALL_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ThrowsExceptions({
      @ThrowsException(status = HttpStatus.NOT_FOUND, exception = PayrollConfigurationNotFoundException.class)
  })
  PayrollConfiguration findPayrollConfiguration(@PathVariable(value = "identifier") final String customerIdentifier);

  @RequestMapping(
      value = "/distribution",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @ThrowsExceptions({
      @ThrowsException(status = HttpStatus.BAD_REQUEST, exception = PayrollPaymentValidationException.class),
      @ThrowsException(status = HttpStatus.CONFLICT, exception = PayrollPaymentValidationException.class)
  })
  void distribute(@RequestBody @Valid final PayrollCollectionSheet payrollCollectionSheet);

  @RequestMapping(
      value = "/distribution",
      method = RequestMethod.GET,
      produces = MediaType.ALL_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  List<PayrollCollectionHistory> fetchDistributionHistory();

  @RequestMapping(
      value = "/distribution/{identifier}/payments",
      method = RequestMethod.GET,
      produces = MediaType.ALL_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  PayrollPaymentPage fetchPayments(@PathVariable("identifier") final String identifier,
                                   @RequestParam(value = "pageIndex", required = false) final Integer pageIndex,
                                   @RequestParam(value = "size", required = false) final Integer size,
                                   @RequestParam(value = "sortColumn", required = false) final String sortColumn,
                                   @RequestParam(value = "sortDirection", required = false) final String sortDirection);

}
