package com.meistermeier.homeremote.xbmc;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Straight forward sending HTTP-Post control messages to XBMC.
 *
 * @author Gerrit Meier
 */
public class XbmcControl {
    private final static Logger LOG = LoggerFactory.getLogger(XbmcControl.class);

    private final Xbmc xbmc;

    public XbmcControl(Xbmc xbmc) {
        this.xbmc = xbmc;
    }

    public String playPause() {
        try {
            StringEntity entity = new StringEntity("{\"jsonrpc\": \"2.0\", \"method\": \"Player.PlayPause\", \"params\": { \"playerid\": 1 }, \"id\": 1}");
            postEntity(entity);
            return "play/pause";
        } catch (IOException e) {
            LOG.error("Could not send message to XBMC.", e);
            return "Error: Could not send message to XBMC";
        }
    }

    public String stopMovie() {
        try {
            StringEntity entity = new StringEntity("{\"jsonrpc\": \"2.0\", \"method\": \"Player.Stop\", \"params\": { \"playerid\": 1 }, \"id\": 1}");
            postEntity(entity);
            return "stop";
        } catch (IOException e) {
            LOG.error("Could not send message to XBMC.", e);
            return "Error: Could not send message to XBMC";
        }
    }

    private void postEntity(StringEntity entity) throws IOException {
        HttpPost post = new HttpPost(xbmc.getUrl());
        post.setHeader("Content-Type", "application/json");
        post.setEntity(entity);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);
        System.out.println(response.getStatusLine());
        InputStream content = response.getEntity().getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
