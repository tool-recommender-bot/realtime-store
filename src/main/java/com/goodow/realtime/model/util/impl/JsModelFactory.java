/*
 * Copyright 2012 Goodow.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.goodow.realtime.model.util.impl;

import com.goodow.realtime.CollaborativeString;
import com.goodow.realtime.Disposable;
import com.goodow.realtime.EventHandler;
import com.goodow.realtime.model.util.ModelFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;

import org.timepedia.exporter.client.ExportClosure;
import org.timepedia.exporter.client.ExportOverlay;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.ExporterUtil;

import java.util.Comparator;
import java.util.logging.Logger;

import elemental.js.json.JsJsonBoolean;
import elemental.js.json.JsJsonNumber;
import elemental.js.json.JsJsonString;
import elemental.json.JsonType;
import elemental.json.JsonValue;

public class JsModelFactory implements ModelFactory, EntryPoint {
  @ExportPackage(ModelFactory.PACKAGE_PREFIX_OVERLAY)
  @ExportClosure
  public interface __ComparatorExportOverlay__ extends ExportOverlay<Comparator<Object>> {
    int compare(Object o1, Object o2);
  }
  @ExportPackage(ModelFactory.PACKAGE_PREFIX_OVERLAY)
  @ExportClosure
  public interface __EventHandlerExportOverlay__ extends ExportOverlay<EventHandler<Disposable>> {
    void handleEvent(Disposable event);
  }

  private static final Logger log = Logger.getLogger(JsModelFactory.class.getName());

  static JavaScriptObject wrap(Object o) {
    if (o instanceof String) {
      return (JavaScriptObject) JsJsonString.create((String) o);
    } else if (o instanceof Number) {
      return (JavaScriptObject) JsJsonNumber.create(((Number) o).doubleValue());
    } else if (o instanceof Boolean) {
      return (JavaScriptObject) JsJsonBoolean.create(((Boolean) o).booleanValue());
    } else if (o instanceof JsonValue) {
      if (JsonType.NULL == ((JsonValue) o).getType()) {
        return null;
      }
      return (JavaScriptObject) o;
    } else {
      return ExporterUtil.wrap(o);
    }
  }

  @Override
  public void onModuleLoad() {
    ExporterUtil.exportAll();
    __jsniOnLoad__();
  }

  @Override
  // @formatter:off
  public native void setText(CollaborativeString str, String text) /*-{
		var dmp = new $wnd.diff_match_patch();
		var text1 = str.@com.goodow.realtime.CollaborativeString::getText()();
		var d = dmp.diff_main(text1, text);
		dmp.diff_cleanupSemantic(d);
		var cursor = 0;
		for ( var i in d) {
			var t = d[i][1], len = t.length;
			switch (d[i][0]) {
			case 0:
				cursor += len;
				break;
			case 1:
				str.@com.goodow.realtime.CollaborativeString::insertString(ILjava/lang/String;)(cursor, t);
				cursor += len;
				break;
			case -1:
				str.@com.goodow.realtime.CollaborativeString::removeRange(II)(cursor, cursor + len);
				break;
			default:
				throw @java.lang.RuntimeException::new(Ljava/lang/String;)("Shouldn't reach here!");
			}
		}
  }-*/;
  // @formatter:on

  // @formatter:off
  private native void __jsniOnLoad__() /*-{
		if ($wnd.gdrOnLoad && typeof $wnd.gdrOnLoad == 'function')
			$wnd.gdrOnLoad();
  }-*/;
  // @formatter:on
}