/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.uitest.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.Cookie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Set;

/**
 * Utility class for file checking.
 *
 * @author Belin Yury
 * @author Belousov Andrey
 */
public final class FileUtil {

    private FileUtil(){}

    /**
     * Gets default directory(path) for downloaded files
     *
     * @return Default directory(path) for downloaded files
     */
    public static String getDownloadPath() {
        return System.getProperty("user.home") + "\\Downloads\\";
    }

    /**
     * Tests whether the file denoted by this abstract pathname is a normal
     * file.  A file is <em>normal</em> if it is not a directory and, in
     * addition, satisfies other system-dependent criteria.  Any non-directory
     * file created by a Java application is guaranteed to be a normal file.
     *
     * @param pathAndFileName - name of file and FULL path for it
     *
     * @return
     * - true if file exists and
     * - false if file absence default directory(path) for downloaded files
     * - true if and only if the file denoted by this abstract pathname exists and is a normal file;
     * - false otherwise
     */
    public static boolean isFileExist(String pathAndFileName){
        ReporterNGExt.logTechnical(String.format("isFileExist: %s", pathAndFileName));
        File findFile = new File(pathAndFileName);
        return findFile.isFile();
    }

    /**
     * Check file download from url.
     *
     * @param downloadUrl     - url of file to download
     * @param outputFilePath  - file path for output
     * @throws  Exception - exception
     */
    public static void downloadFile(String downloadUrl, String outputFilePath) throws Exception {

        ReporterNGExt.logAction("","", String.format("Download file form url: %s", downloadUrl));

        CookieStore cookieStore = seleniumCookiesToCookieStore();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setCookieStore(cookieStore);
        HttpGet httpGet = new HttpGet(downloadUrl);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            File outputFile = new File(outputFilePath);
            InputStream inputStream = entity.getContent();
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }
            fileOutputStream.close();
            ReporterNGExt.logTechnical(String.format("downloadFile: %d bytes. %s", outputFile.length(), entity.getContentType()));
        }
        else {
            ReporterNGExt.logFailedScreenshot(ReporterNGExt.BUSINESS_LEVEL, "Download failed!");
        }
    }

    /**
     * Get Cookie from WebDriver browser session.
     *
     * @return cookieStore from WebDriver browser session.
     */
    private static CookieStore seleniumCookiesToCookieStore() {

        Set<Cookie> seleniumCookies = WebDriverWrapper.getDriver().manage().getCookies();
        CookieStore cookieStore = new BasicCookieStore();

        for(Cookie seleniumCookie : seleniumCookies){
            BasicClientCookie basicClientCookie =
                    new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            basicClientCookie.setDomain(seleniumCookie.getDomain());
            basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
            basicClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie(basicClientCookie);
        }

        return cookieStore;
    }

}
