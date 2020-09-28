package com.customized.multiple.versions.adapter.discover;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface to discover api version code in http request.
 *
 * @author Tony Mu(tonymu@qq.com)
 * @since 2017-07-11
 */
public interface ApiVersionCodeDiscoverer {

    /**
     * Return an api version code that can indicate the version of current api.
     *
     * @param request current HTTP request
     * @return an api version code that can indicate the version of current api or {@code null}.
     */
    String getVersionCode(HttpServletRequest request);

}