package com.urlshortner.serviceManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;

public class ServiceManager {

	private static final String APP_NAME = "";
	private static final String MODULE_NAME = "UrlServicesWeb-0.0.1-SNAPSHOT";
	private static final String DISTINCT_NAME = "";
	static Properties jndiProperties = new Properties();
	private static Map<Class<?>, String> serviceNames = new HashMap<Class<?>, String>(0);
	private static boolean initialized = false;

	private ServiceManager() {

	}

	static {
		init();
	}

	public static void init() {
		try {
			InputStream inputStream = ServiceManager.class.getClassLoader()
					.getResourceAsStream("jboss-ejb-client.properties");
			jndiProperties = new Properties();
			jndiProperties.load(inputStream);
			jndiProperties.put("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
			System.out.println(jndiProperties);
			initialized = true;
			inputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static Object getService(Class<?> clazz) {
		if (!initialized) {
			init();
		}
		return getServiceByJndiName(getGlobalJndiName(clazz));
	}

	private static Object getServiceByJndiName(String serviceName) {
		try {
			return lookupRemoteService(serviceName);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return null;
	}

	private static String getGlobalJndiName(Class<?> clazz) {
		String jndiName = serviceNames.get(clazz);
		if (jndiName == null) {
			if (clazz.getSimpleName().endsWith("Remote")) {
				jndiName = "ejb:" + APP_NAME + "/" + MODULE_NAME + "/" + DISTINCT_NAME + "/"
						+ clazz.getSimpleName().substring(0, (clazz.getSimpleName().lastIndexOf("Remote"))) + "!"
						+ clazz.getName();
			} else if (clazz.getSimpleName().endsWith("Local")) {
				jndiName = "ejb:" + APP_NAME + "/" + MODULE_NAME + "/" + DISTINCT_NAME + "/"
						+ clazz.getSimpleName().substring(0, (clazz.getSimpleName().lastIndexOf("Local"))) + "!"
						+ clazz.getName();
			}
//			ejb:/qr-services//AlertService!com.qr.services.AlertServiceRemote

			System.out.println(jndiName);
			serviceNames.put(clazz, jndiName);
		}

		return jndiName;
	}

	private static Object lookupRemoteService(String remoteServiceName) throws Exception {
		return getInitialContext().lookup(remoteServiceName);
	}

	public static InitialContext getInitialContext() throws Exception {
		return new InitialContext(jndiProperties);
	}
}
