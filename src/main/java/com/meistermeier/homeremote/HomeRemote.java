package com.meistermeier.homeremote;

import com.google.common.collect.Sets;
import com.meistermeier.homeremote.command.BuildInCommand;
import com.meistermeier.homeremote.command.CommandRegistry;
import com.meistermeier.homeremote.command.netio.NetioPowerSwitch;
import com.meistermeier.homeremote.command.netio.NetioPowerSwitchCommand;
import com.meistermeier.homeremote.command.netio.NetioPowerSwitchControl;
import com.meistermeier.homeremote.command.xbmc.Xbmc;
import com.meistermeier.homeremote.command.xbmc.XbmcCommand;
import com.meistermeier.homeremote.command.xbmc.XbmcControl;
import com.meistermeier.homeremote.control.Control;
import com.meistermeier.homeremote.control.network.NetworkControl;
import com.meistermeier.homeremote.control.xmpp.XmppControl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @author Gerrit Meier
 */
public class HomeRemote {

    private final static Logger LOG = LoggerFactory.getLogger(HomeRemote.class);
    private final Properties properties = new Properties();
    private final CommandRegistry registry = new CommandRegistry();
    private final Set<Control> controlSet = Sets.newHashSet();
    private static HomeRemote homeRemote;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LOG.info("shutting down");
                if (homeRemote != null) {
                    LOG.info("cleaning up registered controls");
                    for (Control control : homeRemote.controlSet) {
                        control.shutdown();
                    }
                }
                LOG.info("shutdown finished.");
            }
        });

        homeRemote = new HomeRemote();
    }

    public HomeRemote() {
        init();
    }

    /**
     * Initializes the whole process. If something should fail the application won't start.
     */
    protected void init() {
        try {
            loadProperties();
            initControls();
            initCommands();
        } catch (Exception e) {
            LOG.error("Something went wrong while initializing HomeRemote", e);
            System.exit(1);
        }
    }

    protected void initCommands() {
        initBuildInCommand();

        boolean initXbmc = Boolean.parseBoolean(properties.getProperty("xbmc.enabled"));
        boolean initElro = Boolean.parseBoolean(properties.getProperty("elro.enabled"));
        boolean initNetio = Boolean.parseBoolean(properties.getProperty("netio.enabled"));

        if (initXbmc) initXbmc();
        if (initElro) initElro();
        if (initNetio) initNetio();
    }

    protected void initControls() {
        boolean initTelnet = Boolean.parseBoolean(properties.getProperty("telnet.enabled"));
        boolean initVoice = Boolean.parseBoolean(properties.getProperty("voice.enabled"));
        boolean initXmpp = Boolean.parseBoolean(properties.getProperty("xmpp.enabled"));

        if (initTelnet) initTelnet();
        if (initVoice) initVoice();
        if (initXmpp) initXmpp();
    }

    protected void initBuildInCommand() {
        registry.register(new BuildInCommand(registry));
    }

    protected void initXbmc() {
        LOG.debug("initializing xbmc");
        String xbmcUrl = properties.getProperty("xbmc.url");
        Xbmc xbmc = new Xbmc(xbmcUrl);
        XbmcControl xbmcControl = new XbmcControl(xbmc);
        XbmcCommand xbmcCommand = new XbmcCommand(xbmcControl);
        registry.register(xbmcCommand);
    }

    protected void initElro() {
        LOG.debug("initializing elro");

        LOG.warn("no elro / 433 Mhz transmitter support");
    }

    protected void initNetio() {
        LOG.debug("initializing netio");
        String host = properties.getProperty("netio.host");
        String port = properties.getProperty("netio.port");
        String user = properties.getProperty("netio.user");
        String password = properties.getProperty("netio.password");

        NetioPowerSwitch netioPowerSwitch = new NetioPowerSwitch(host, Integer.parseInt(port), user, password);
        NetioPowerSwitchControl netioPowerSwitchControl = new NetioPowerSwitchControl(netioPowerSwitch);
        NetioPowerSwitchCommand netioPowerSwitchCommand = new NetioPowerSwitchCommand(netioPowerSwitchControl);
        registry.register(netioPowerSwitchCommand);
    }

    protected void initTelnet() {
        LOG.debug("initializing telnet");
        String telnetPort = properties.getProperty("telnet.port");
        NetworkControl networkControl = new NetworkControl(registry, Integer.parseInt(telnetPort));
        networkControl.start();
        controlSet.add(networkControl);
    }

    protected void initVoice() {
        LOG.debug("initializing voice");

        LOG.warn("voice control not supported");
    }

    protected void initXmpp() {
        LOG.debug("initializing xmpp");
        String xmppUser = properties.getProperty("xmpp.user");
        String xmppPassword = properties.getProperty("xmpp.password");
        String xmppHost = properties.getProperty("xmpp.host");
        String xmppPort = properties.getProperty("xmpp.port");
        String xmppService = properties.getProperty("xmpp.service");
        XmppControl xmppControl = new XmppControl(xmppUser, xmppPassword, xmppHost, Integer.parseInt(xmppPort), xmppService, registry);
        xmppControl.initConnection();
        controlSet.add(xmppControl);
    }

    protected void loadProperties() {
        loadDefaultProperties();
        loadCustomProperties();
    }

    protected void loadCustomProperties() {
        String customPropertyFilePath = properties.getProperty("custom.properties.path");
        if (StringUtils.contains(customPropertyFilePath, "${user.home}")) {
            customPropertyFilePath = StringUtils.replace(customPropertyFilePath, "${user.home}", System.getProperty("user.home"));
        }
        File customerPropertyFile = new File(customPropertyFilePath);

        if (!customerPropertyFile.exists()) {
            LOG.warn("there is no custom property file ({}) to load. " +
                    "You should provide this to customize the application for your needs.", customPropertyFilePath);
            return;
        }

        try {
            properties.load(new FileReader(customerPropertyFile));
        } catch (IOException e) {
            LOG.error("There is a custom property file at {}, but I cannot load it.", customPropertyFilePath, e);
        }
    }

    protected void loadDefaultProperties() {
        try {
            properties.load(HomeRemote.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            LOG.error("could not load or parse application.properties");
            System.exit(1);
        }
    }

}
