/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.sling.scripting.sightly.apps.wknd.components.byline;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class byline__002e__html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_byline = null;
Object _global_placeholdertemplate = null;
Object _global_hascontent = null;
_global_byline = renderContext.call("use", com.adobe.aem.guides.wknd.core.models.Byline.class.getName(), obj());
_global_placeholdertemplate = renderContext.call("use", "core/wcm/components/commons/v1/templates.html", obj());
_global_hascontent = (!renderContext.getObjectModel().toBoolean(renderContext.getObjectModel().resolveProperty(_global_byline, "empty")));
if (renderContext.getObjectModel().toBoolean(_global_hascontent)) {
    out.write("<div class=\"cmp-byline\">\r\n    <div class=\"cmp-byline__image\">");
    {
        Object var_resourcecontent0 = renderContext.call("includeResource", ".", obj().with("resourceType", "core/wcm/components/image/v2/image"));
        out.write(renderContext.getObjectModel().toString(var_resourcecontent0));
    }
    out.write("</div>\r\n    <h2 class=\"cmp-byline__name\">");
    {
        Object var_1 = renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_byline, "name"), "text");
        out.write(renderContext.getObjectModel().toString(var_1));
    }
    out.write("</h2>\r\n    <p class=\"cmp-byline__occupations\">");
    {
        Object var_2 = renderContext.call("xss", renderContext.call("join", renderContext.getObjectModel().resolveProperty(_global_byline, "occupations"), ", "), "text");
        out.write(renderContext.getObjectModel().toString(var_2));
    }
    out.write("</p>\r\n</div>");
}
out.write("\r\n");
{
    Object var_templatevar3 = renderContext.getObjectModel().resolveProperty(_global_placeholdertemplate, "placeholder");
    {
        boolean var_templateoptions4_field$_isempty = (!renderContext.getObjectModel().toBoolean(_global_hascontent));
        {
            java.util.Map var_templateoptions4 = obj().with("isEmpty", var_templateoptions4_field$_isempty);
            callUnit(out, renderContext, var_templatevar3, var_templateoptions4);
        }
    }
}
out.write("\r\n");


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}

