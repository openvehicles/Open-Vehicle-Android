/*
    Installation:
     - Save as /store/scripts/lib/gps_onoff.js
     - Add to /store/scripts/ovmsmain.js: gps_onoff = require("lib/gps_onoff");
     - Issue "script reload"
*/


// create config usr value
var init_usrcfg = OvmsConfig.Get("usr","gps.init", "no");

if(init_usrcfg == "no"){
    OvmsConfig.Set("usr", "gps.init", "yes");           // init usr config
    OvmsConfig.Set("usr", "gps.on", "yes");             // factor for gps Range 85% of EST Range
    OvmsConfig.Set("usr", "gps.ticker", "60");     	    // ticker format 1/10/30/60/300/600/3600 seconds
    OvmsConfig.Set("usr", "gps.parktime", "300");       // parktime >= in seconds
    OvmsConfig.Set("usr", "gps.counter", "0");          // GPS off time ticker counter 
    OvmsConfig.Set("usr", "gps.counter_value","55");    // check interval for 55 cycle for GPS power on
    OvmsConfig.Set("usr", "gps.counter_add","5");       // add 5 cycle to check interval for GPS power off
    OvmsConfig.Set("usr", "gps.power_switch", "yes");   // create network power up/down event
}

// delete old events
PubSub.unsubscribe(usr_gps_check);

// get value
var gps_ticker = 'ticker.' + OvmsConfig.Get("usr","gps.ticker","60"); 
var gps_power_switch = OvmsConfig.Get("usr","gps.power_switch","no");

function veh_on() {
    return OvmsMetrics.Value("v.e.on");
}
function charging() {
    return OvmsMetrics.Value("v.c.charging");
}

function usr_gps_check() {

    var gps_on = OvmsConfig.Get("usr", "gps.on","yes");

    if(!veh_on() && !charging()) {        
        var gps_parktime = OvmsConfig.Get("usr", "gps.parktime","300");
        var gps_counter = Number(OvmsConfig.Get("usr", "gps.counter","0"));
        var gps_counter_value = Number(OvmsConfig.Get("usr", "gps.counter_value","55"));
        var gps_counter_add = Number(OvmsConfig.Get("usr", "gps.counter_add","5"));
        var me_parktime = OvmsMetrics.Value("v.e.parktime");
        
        if( me_parktime >= gps_parktime ){
            OvmsConfig.Set("usr", "gps.counter", (gps_counter +1));
            if( (gps_on == "no")&&(gps_counter >= gps_counter_value) ){
                OvmsCommand.Exec('cellular gps start');
                OvmsConfig.Set("usr", "gps.on", "yes");
            }else{
                if( (gps_on == "yes")&&(gps_counter >= (gps_counter_value + gps_counter_add)) ){
                    OvmsCommand.Exec('cellular gps stop');
                    OvmsConfig.Set("usr", "gps.on", "no");
                    OvmsConfig.Set("usr", "gps.counter", "0");
                }
            }
        }
    }else{
        if (gps_on == "no") {
            OvmsCommand.Exec('cellular gps start');
            OvmsConfig.Set("usr", "gps.on", "yes");
            OvmsConfig.Set("usr", "gps.counter", "0");
        }
    }
}

// event creating
if(gps_power_switch == "yes"){
    PubSub.subscribe(gps_ticker,usr_gps_check);
}
