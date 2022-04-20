package com.adobe.aem.guides.wknd.core.service.reports;

import org.apache.sling.api.SlingHttpServletRequest;

import java.io.IOException;

public interface ReportService
{
    StringBuilder makeReport(SlingHttpServletRequest request) throws IOException;

}
