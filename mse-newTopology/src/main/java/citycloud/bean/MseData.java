package citycloud.bean;

import java.util.Date;

/**
 * Created by wojustme on 2017/2/8.
 */
public class MseData {
  //客户端是否连接上SSID, true表示连上，false表示断开
  private Boolean associated;
  //该信息产生的时间（用毫秒数来表示）
  private Long timestampMillis;
  //客户端是否被AP探测到,true表示探测到, flase表示没探测到
  private Boolean presence;
  //该AP所属的presence-site分组，可以利用标示门店ID
  private String siteName;
  //客户端是否在范围内
  private Boolean visitor;
  //该信息所属的客户端类型（是否无线客户端）
  private String entity;
  //客户端是否进入设定的范围, true表示进入，false表示离开
  private Boolean closeTo;
  //该MSE服务的唯一标示
  private String mseUdi;
  //接受该JSON信息的服务器名称
  private String subscriptionName;
  //该信息产生的时间
  private Date timestamp;
  //检测到该客户端的AP的base radio mac地址
  private String apMacAddress;
  //客户端的信号强度
  private Integer rssi;
  //客户端的mac地址
  private String deviceId;

  public Boolean getAssociated() {
    return associated;
  }

  public void setAssociated(Boolean associated) {
    this.associated = associated;
  }

  public Long getTimestampMillis() {
    return timestampMillis;
  }

  public void setTimestampMillis(Long timestampMillis) {
    this.timestampMillis = timestampMillis;
  }

  public Boolean getPresence() {
    return presence;
  }

  public void setPresence(Boolean presence) {
    this.presence = presence;
  }

  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  public Boolean getVisitor() {
    return visitor;
  }

  public void setVisitor(Boolean visitor) {
    this.visitor = visitor;
  }

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public Boolean getCloseTo() {
    return closeTo;
  }

  public void setCloseTo(Boolean closeTo) {
    this.closeTo = closeTo;
  }

  public String getMseUdi() {
    return mseUdi;
  }

  public void setMseUdi(String mseUdi) {
    this.mseUdi = mseUdi;
  }

  public String getSubscriptionName() {
    return subscriptionName;
  }

  public void setSubscriptionName(String subscriptionName) {
    this.subscriptionName = subscriptionName;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getApMacAddress() {
    return apMacAddress;
  }

  public void setApMacAddress(String apMacAddress) {
    this.apMacAddress = apMacAddress;
  }

  public Integer getRssi() {
    return rssi;
  }

  public void setRssi(Integer rssi) {
    this.rssi = rssi;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  @Override
  public String toString() {
    return "MseData{" +
        "associated=" + associated +
        ", timestampMillis=" + timestampMillis +
        ", presence=" + presence +
        ", siteName='" + siteName + '\'' +
        ", visitor=" + visitor +
        ", entity='" + entity + '\'' +
        ", closeTo=" + closeTo +
        ", mseUdi='" + mseUdi + '\'' +
        ", subscriptionName='" + subscriptionName + '\'' +
        ", timestamp=" + timestamp +
        ", apMacAddress='" + apMacAddress + '\'' +
        ", rssi=" + rssi +
        ", deviceId='" + deviceId + '\'' +
        '}';
  }
}
