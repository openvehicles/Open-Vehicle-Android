/*
    Installation:
     - Save as /store/scripts/lib/scheduled_booster.js
     - Add to /store/scripts/ovmsmain.js: scheduled_booster = require("lib/scheduled_booster");
     - Issue "script reload"
*/

// create config usr value
var init_usrcfg = OvmsConfig.Get("usr","b.init", "no");

if(init_usrcfg == "no"){
    OvmsConfig.Set("usr", "b.init", "yes");           // init usr config
    OvmsConfig.Set("usr", "b.activated", "no");       // schedule time activated     
    OvmsConfig.Set("usr", "b.scheduled", "0515");     // schedule time format 1400 for 14:00 / 2 p.m. o'clock 
    OvmsConfig.Set("usr", "b.week", "no");            // schedule time weekly at Day de/activated
    OvmsConfig.Set("usr", "b.day_start", "1");        // Day1 -> Monday
    OvmsConfig.Set("usr", "b.day_end", "5");          // Day5 -> Friday
    OvmsConfig.Set("usr", "b.ticker", "30");     	  // ticker format 1/10/30/60/300/600/3600 seconds
    OvmsConfig.Set("usr", "b.data", "0,0,0,0,-1,-1"); // Data Array
}

// delete old events
PubSub.unsubscribe(start_day);
PubSub.unsubscribe(end_day);
PubSub.unsubscribe(scheduled_boost);
PubSub.unsubscribe(booster_data);

// get value
var scheduled_act = OvmsConfig.Get("usr","b.activated");
var scheduled_week = OvmsConfig.Get("usr","b.week");
var scheduled_ticker = 'ticker.' + OvmsConfig.Get("usr","b.ticker");

// add one Day for execute on last Day, Day 7 not possible -> set Day 0 for Sunday
if ((OvmsConfig.Get("usr","b.day_end"))<=5) {
    var scheduled_end = OvmsConfig.Get("usr","b.day_end")+1
} else {
    var scheduled_end = 0
};

var ClockEvent = 'clock.' + OvmsConfig.Get("usr","b.scheduled");
var DayStart = 'clock.day' + OvmsConfig.Get("usr","b.day_start");
var DayEnd = 'clock.day' + scheduled_end;

function veh_on() {
    return OvmsMetrics.Value("v.e.on");
}
function charging() {
    return OvmsMetrics.Value("v.c.charging");
}

function start_day() { 
    if (scheduled_week == "yes"){
        OvmsConfig.Set("usr", "b.activated", "yes");
    }
}

function end_day() { 
    OvmsConfig.Set("usr", "b.activated", "no");
}

function scheduled_boost() {

    var scheduled_week = OvmsConfig.Get("usr","b.week");
    
    if((!veh_on() && !charging()) && (OvmsConfig.Get("usr","b.activated") == "yes")) {
        OvmsVehicle.ClimateControl("on");
        /*if (scheduled_week == "no") {
            OvmsConfig.Set("usr", "b.activated", "no");
        }*/
    }
}

function booster_data() {

    var newdata = OvmsConfig.Get("usr", "b.data").split(",");

    if (newdata[0] == 1) {
        // booster scheduled on
        if (newdata[1] == 1) {
            OvmsConfig.Set("usr", "b.activated", "yes");
        }
        // booster scheduled off
        if (newdata[1] == 2) {
            OvmsConfig.Set("usr", "b.activated", "no");
        }
        // boost every day between start/end day on
        if (newdata[2] == 1) {
            OvmsConfig.Set("usr", "b.week", "yes");
        }
        // boost every day between start/end day off
        if (newdata[2] == 2) {
            OvmsConfig.Set("usr", "b.week", "no");
        }
        // booster scheduled time + boost scheduled on
        if (newdata[3]> 0) {
            OvmsConfig.Set("usr", "b.scheduled", newdata[3]);
        }
        // booster start day + boost scheduled on + boost weekly on
        if (newdata[4] > -1) {
            OvmsConfig.Set("usr", "b.day_start", newdata[4]);
        }
        // booster stop day
        if (newdata[5] > -1) {
            OvmsConfig.Set("usr", "b.day_end", newdata[5]);
        }
        OvmsConfig.Set("usr", "b.data", "0,0,0,0,-1,-1");
        OvmsCommand.Exec('script reload');
    }
}

// event creating
PubSub.subscribe(scheduled_ticker,booster_data);

if(scheduled_week == "yes"){
    PubSub.subscribe(DayStart,start_day);
    PubSub.subscribe(DayEnd,end_day);
}

if(scheduled_act == "yes"){
    PubSub.subscribe(ClockEvent,scheduled_boost);
}
