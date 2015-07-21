package com.epam.page_objects.mct.robot;

import com.ggasoftware.uitest.autoit.UAutoItX;
import com.ggasoftware.uitest.utils.WebDriverWrapper;

import static com.ggasoftware.uitest.utils.ReporterNGExt.logBusinessScreenshot;

/**
 * Created by Kirill_Chebulaev on 7/3/2015.
 */
public class AutoIt {
    public static AutoIt autoIt = new AutoIt();

    public void fileSave(String sPath, String title) {
        UAutoItX UIMethods = new UAutoItX();
        try {
            //System.out.println("fileSave started");
            UIMethods.WinWait(title, "", 3);
            UIMethods.WinActivate(title, "");
            WebDriverWrapper.sleep(1);
            if (!"".equals(sPath)) {
                UIMethods.Send(sPath);
                WebDriverWrapper.sleep(2);
            }
            logBusinessScreenshot("fileSave send");
            UIMethods.WinActivate(title, "");
         //   UIMethods.Send("{Down}");
            UIMethods.Send("{Enter}");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        logBusinessScreenshot("fileSave after send");
        WebDriverWrapper.sleep(2);
        //System.out.println("fileSave finished");
    }
}
