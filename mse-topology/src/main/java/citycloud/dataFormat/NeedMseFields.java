package citycloud.dataFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wojustme on 2017/1/17.
 */
public class NeedMseFields implements Serializable {

  // ap地址
  private String apMacAddress;
  // 手机Mac
  private String deviceId;
  // 时间戳
  private Date timestamp;

  public NeedMseFields(String apMacAddress, String deviceId, Date timestamp) {
    this.apMacAddress = apMacAddress;
    this.deviceId = deviceId;
    this.timestamp = timestamp;
  }

  public NeedMseFields() {
  }

  public String getApMacAddress() {
    return apMacAddress;
  }

  public void setApMacAddress(String apMacAddress) {
    this.apMacAddress = apMacAddress;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "citycloud.NeedMseFields{" +
        "apMacAddress='" + apMacAddress + '\'' +
        ", deviceId='" + deviceId + '\'' +
        ", timestamp=" + timestamp +
        '}';
  }
}
