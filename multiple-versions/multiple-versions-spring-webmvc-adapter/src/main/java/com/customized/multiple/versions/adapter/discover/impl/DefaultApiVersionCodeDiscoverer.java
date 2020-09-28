package com.customized.multiple.versions.adapter.discover.impl;

import com.customized.multiple.versions.adapter.discover.ApiVersionCodeDiscoverer;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Default implementation of the {@link ApiVersionCodeDiscoverer} interface, get api version code
 * named "version" in headers or named "@version" in parameters.
 *
 * @author Tony Mu(tonymu@qq.com)
 * @since 2017-07-11
 */
public class DefaultApiVersionCodeDiscoverer implements ApiVersionCodeDiscoverer {

    /**
     * Get api version code named "version" in headers or named "@version" in parameters.
     *
     * @param request current HTTP request
     * @return api version code named "version" in headers or named "@version" in parameters.
     */
    @Override
    public String getVersionCode(HttpServletRequest request) {
        String version = request.getParameter("version");
        if (!StringUtils.hasText(version)) {
            String versionFromUrl = request.getParameter("@version");//for debug
            if (StringUtils.hasText(versionFromUrl)) {
                version = versionFromUrl;
            }
        }
        return version;
    }
}