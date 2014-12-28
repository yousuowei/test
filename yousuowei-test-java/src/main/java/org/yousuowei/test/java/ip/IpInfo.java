/**
 * created at 2009-12-29
 */
package org.yousuowei.test.java.ip;

/**
 * @author malw
 * 
 */
public class IpInfo {
	private Long id;
	private String startIpStr;
	private String endIpStr;
	private String country;
	private String province;
	private String city;
	private String adress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartIpStr() {
		return startIpStr;
	}

	public void setStartIpStr(String startIpStr) {
		this.startIpStr = startIpStr;
	}

	public String getEndIpStr() {
		return endIpStr;
	}

	public void setEndIpStr(String endIpStr) {
		this.endIpStr = endIpStr;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@Override
	public String toString() {
		return "IpInfo [city=" + city + ", country=" + country + ", id=" + id
				+ ", province=" + province + ", adress=" + adress + "]";
	}

}
