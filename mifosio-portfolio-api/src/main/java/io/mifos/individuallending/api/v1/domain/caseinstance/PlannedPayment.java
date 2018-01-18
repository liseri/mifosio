/*
 * Copyright 2017 Kuelap, Inc.
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
package io.mifos.individuallending.api.v1.domain.caseinstance;

import io.mifos.portfolio.api.v1.domain.Payment;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * @author Myrle Krantz
 */
public class PlannedPayment {
  private Payment payment;
  private Map<String, BigDecimal> balances;

  public PlannedPayment(Payment payment, Map<String, BigDecimal> balances) {
    this.payment = payment;
    this.balances = balances;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }

  public Map<String, BigDecimal> getBalances() {
    return balances;
  }

  public void setBalances(Map<String, BigDecimal> balances) {
    this.balances = balances;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlannedPayment that = (PlannedPayment) o;
    return Objects.equals(payment, that.payment) &&
        Objects.equals(balances, that.balances);
  }

  @Override
  public int hashCode() {
    return Objects.hash(payment, balances);
  }

  @Override
  public String toString() {
    return "PlannedPayment{" +
        "payment=" + payment +
        ", balances=" + balances +
        '}';
  }
}
