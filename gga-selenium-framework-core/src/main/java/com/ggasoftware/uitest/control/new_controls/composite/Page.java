package com.ggasoftware.uitest.control.new_controls.composite;

import com.ggasoftware.uitest.control.interfaces.base.IComposite;
import com.ggasoftware.uitest.control.new_controls.base.BaseElement;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class Page extends BaseElement implements IComposite {
    public String url;
    public String title;
    public String uri;
    public static String domain;
    public String getUrl() {
        return url != null ? url : domain + uri;
    }

    public Page() {}
    public Page(String url) {
        this.url = url;
    }
    public Page(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public void open() { getDriver().navigate().to(url); }
    public static void openUrl(String url) {
        new Page(url).open();
    }
    public void refresh() {
        getDriver().navigate().refresh();
    }
}
