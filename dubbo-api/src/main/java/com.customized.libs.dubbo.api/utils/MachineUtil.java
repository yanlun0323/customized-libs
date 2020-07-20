package com.customized.libs.dubbo.api.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MachineUtil {
	
	/**
	 * 获取序列网卡上第一个IP地址, 外网地址优先返回
	 * @return
	 */
	public static String getFristIPv4() {
		String localip 	= null;	// 本地IP，如果没有配置外网IP则返回它
		String netip 	= null;	// 外网IP
		try{
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			Enumeration<InetAddress> address = null;
			while (!finded && netInterfaces.hasMoreElements()) {
				address = netInterfaces.nextElement().getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if(!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")){
						if (!ip.isSiteLocalAddress()) {// 外网IP
							netip = ip.getHostAddress();
							finded = true;
							break;
						} else if (localip == null) {// 内网IP
							localip = ip.getHostAddress();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}
}
