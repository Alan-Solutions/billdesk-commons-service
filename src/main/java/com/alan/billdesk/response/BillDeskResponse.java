package com.alan.billdesk.response;

import lombok.Data;
import org.json.simple.JSONObject;


/**
 *
 * @author Sujith Ramanathan
 *
 * This wrapper is used to maintain success or failure scenarios.
 * i.e., <ul><li>If it is success then the body will sent to the upstream.</li>
 * <li>If it's logically / validation failed then the errorJson will be sent to upstream</li>
 * </ul>
 */

@Data
public class BillDeskResponse<T> {

  private String status;

  private T body;

  private JSONObject errorDetails;

  public BillDeskResponse(String status, T body, JSONObject errorDetails) {
    this.status = status;
    this.body = body;
    this.errorDetails = errorDetails;
  }

}
