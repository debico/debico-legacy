package br.com.debico.ui.interceptors;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Strings;
import com.jcabi.manifests.Manifests;

public class FooterInterceptor extends HandlerInterceptorAdapter  {

	private static final Logger LOGGER = LoggerFactory.getLogger(FooterInterceptor.class);
	
	private AppVersion appVersion;
	
	public FooterInterceptor() {
		
	}
		
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	
		if (modelAndView != null) {
			modelAndView.addObject("appversion", this.getAppVersion(request.getServletContext()));
			modelAndView.addObject("principal", (request.getUserPrincipal() == null) ? "" : request.getUserPrincipal().getName());
		}
	}
	
	protected AppVersion getAppVersion(ServletContext context) {
		if(appVersion == null) {
			try {
				Manifests.append(context);
				if(Manifests.exists("App-Version")) {
					this.appVersion = new AppVersion(Manifests.read("App-Version"), Manifests.read("Build-Date"));
					this.appVersion.setVersionNews(StringEscapeUtils.unescapeJava(Manifests.read("Version-News")));
				} else {
					this.appVersion = new AppVersion();
				}
			} catch (IOException e) {
				LOGGER.warn("[getAppVersion] Nao foi possivel ler a versao da App do MANIFEST.", e);
			}
		}
		
		return appVersion;
	}
	
	public static class AppVersion {
		private final String version;
		private final String buildNumber;
		private String versionNews;
		
		public AppVersion() {
			this.version = Strings.nullToEmpty(null);
			this.buildNumber = Strings.nullToEmpty(null);
		}
		
		public AppVersion(final String version, final String buildNumber) {
			this.version = version;
			this.buildNumber = buildNumber;
		}
		
		public String getBuildNumber() {
			return buildNumber;
		}
		
		public String getVersion() {
			return version;
		}
		
		public String getVersionNews() {
			return versionNews;
		}
		
		public void setVersionNews(String versionNews) {
			this.versionNews = versionNews;
		}
		
		public String getReleaseVersion() {
			int index = version.indexOf("-");
			index = (index == -1 ? version.indexOf(".Final") : index);
			index = (index == -1 ? version.length() : index);
			
			return version.substring(0, index);
		}
	}

}
