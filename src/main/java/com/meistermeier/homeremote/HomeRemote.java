package com.meistermeier.homeremote;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
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
 * @author Gerrit Meier
 */
public class HomeRemote extends Thread {

    private final static String MOVIE_PLAY = "movie play";
    private final static String MOVIE_PAUSE = "movie pause";
    private final static String MOVIE_STOP = "movie halligalli";
    private final static Logger LOG = LoggerFactory.getLogger(HomeRemote.class);
    private boolean stopped = false;

    private final Microphone microphone;
    private final Recognizer recognizer;

    public HomeRemote() {
        ConfigurationManager cm = new ConfigurationManager(HomeRemote.class.getResource("/homeremote.config.xml"));
        recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();
        microphone = (Microphone) cm.lookup("microphone");
    }

    public void startListening() {
        setupMicOrDie();
        LOG.debug("ready for your commands");
        this.start();
    }


    private void setupMicOrDie() {
        if (!microphone.startRecording()) {
            LOG.error("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        while (!stopped) {
            LOG.debug("listening");
            Result result = recognizer.recognize();

            if (result != null) {
                String recognizedString = result.getBestFinalResultNoFiller();
                System.out.println(recognizedString);
                try {
                evaluateResult(recognizedString);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                LOG.debug("understood '{}'", recognizedString);
            } else {
                LOG.warn("got nothing as a result");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        HomeRemote homeRemote = new HomeRemote();
        homeRemote.startListening();
    }


    public void evaluateResult(String result) throws Exception {
        switch (result) {
            case MOVIE_PLAY:
                System.out.println("movie play");
                playPause();
                break;
            case MOVIE_PAUSE:
                System.out.println("movie pause");
                playPause();
                break;
            case MOVIE_STOP:
                System.out.println("movie stop");
                stopMovie();
                break;
        }
    }

    public static void playPause() throws Exception {
        StringEntity entity = new StringEntity("{\"jsonrpc\": \"2.0\", \"method\": \"Player.PlayPause\", \"params\": { \"playerid\": 1 }, \"id\": 1}");
        postEntity(entity);
    }

    public static void stopMovie() throws Exception {
        StringEntity entity = new StringEntity("{\"jsonrpc\": \"2.0\", \"method\": \"Player.Stop\", \"params\": { \"playerid\": 1 }, \"id\": 1}");
        postEntity(entity);
    }

    private static void postEntity(StringEntity entity) throws IOException {
        HttpPost post = new HttpPost("http://192.168.178.26:8080/jsonrpc");
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
