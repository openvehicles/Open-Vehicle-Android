/*
    Installation:
     - Save as /store/scripts/lib/modem_connected.js
     - Add to /store/scripts/ovmsmain.js: modem_connected = require("lib/modem_connected");
     - Issue "script reload"
*/

// create config usr value
var init_usrcfg = OvmsConfig.Get("usr","m.init_1", "no");

if(init_usrcfg == "no"){
    OvmsConfig.Set("usr", "m.init_1", "yes");        // init usr config
    OvmsConfig.Set("usr", "m.ticker_act", "yes");  // ticker activated yes/no
    OvmsConfig.Set("usr", "m.ticker", "600");      // ticker format 1/10/30/60/300/600/3600 seconds
}

// delete old events
PubSub.unsubscribe(usr_modem_check);
PubSub.unsubscribe(usr_wlan_check);

var ticker = 'ticker.' + OvmsConfig.Get("usr","m.ticker");
var ticker_act = OvmsConfig.Get("usr","m.ticker_act");

function usr_modem_check() {
    
    var mobil_sq = OvmsMetrics.Value("m.net.mdm.sq");
    var mobil_network = OvmsMetrics.Value("m.net.mdm.network");
    var mobil_on = OvmsConfig.Get("usr", "m.power_on");

    // reststart modem
    if ((mobil_network == "") | (mobil_sq <= -90) | (mobil_sq == 0)) {
        if (mobil_on== "yes"){
            OvmsCommand.Exec("cellular setstate PowerOffOn");
        } 
    }
}

function usr_wlan_check() { 

    var wifi_sq = OvmsMetrics.Value("m.net.wifi.sq");
    var wifi_network = OvmsMetrics.Value("m.net.wifi.network");

    // power off WLAN
    if ((wifi_sq <= -120) && (wifi_network != "")) {
        OvmsCommand.Exec("power wifi off");
        PubSub.unsubscribe(usr_wlan_check);
    }
}

if(ticker_act == "yes"){
    PubSub.subscribe(ticker,usr_modem_check);
    PubSub.subscribe('ticker.300',usr_wlan_check);
}
